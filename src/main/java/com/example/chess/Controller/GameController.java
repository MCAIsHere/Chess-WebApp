package com.example.chess.Controller;
import com.example.chess.AppData;
import com.example.chess.DatabaseService;
import com.example.chess.Model.ChessLogic;
import com.example.chess.Model.Match;
import com.example.chess.Model.Pieces.*;
import com.example.chess.Model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.LinkedList;
import java.util.List;
@Controller
@RequestMapping("/")
public class GameController {
    private final AppData appData;

    public GameController(AppData appData) {
        this.appData = appData;
    }

    @GetMapping("/game/{id}")
    public String Index(Model model, @PathVariable("id") String id) {
        Match match = appData.getMatchById(id);
        List<String> users_id = match.getUsers();
        List<Duration> timers = match.getTimers();

        String username_one = null;
        String username_two = null;
        for (User user : appData.Users){
            if (user.getId().equals(users_id.get(0))) username_one = user.getUsername();
            else if (user.getId().equals(users_id.get(1))) username_two = user.getUsername();
        }
        long minutes_one = timers.get(0).toMinutes();
        long minutes_two = timers.get(1).toMinutes();
        long seconds_one = timers.get(0).toSeconds() - minutes_one*60;
        long seconds_two = timers.get(1).toSeconds() - minutes_two*60;

        Piece[][] board = match.getBoard();
        String[][] Pieces_color = new String[9][9];
        String[][] Pieces_type = new String[9][9];
        for (int i = 1; i <= 8; i++){
            for (int j = 1; j <= 8; j++){
                if (board[i][j] != null){
                    Pieces_color[i][j] = board[i][j].getColor();
                    Pieces_type[i][j] = board[i][j].getType();
                }else{
                    Pieces_color[i][j] = null;
                    Pieces_type[i][j] = null;
                }
            }
        }

        model.addAttribute("Username_one", username_one);
        model.addAttribute("Username_two", username_two);
        model.addAttribute("Minutes_one", minutes_one);
        model.addAttribute("Seconds_one", seconds_one);
        model.addAttribute("Minutes_two", minutes_two);
        model.addAttribute("Seconds_two", seconds_two);
        model.addAttribute("Statistics", match.getIstoric());
        model.addAttribute("Pieces_Colors", Pieces_color);
        model.addAttribute("Pieces_Types", Pieces_type);
        return "game";
    }

    @PostMapping("/displayMoves")
    @ResponseBody
    public List<String> displayMoves(@RequestParam String Id, @RequestParam String Position) {
        Match match = null;
        for (Match i : appData.Matches) {
            if (i.getId().equals(Id)) {
                match = i;
                break;
            }
        }
        if (match == null) return null;

        Piece[][] board = match.getBoard();
        int row = Character.getNumericValue(Position.charAt(1));
        int col = Position.charAt(0) - 'A' + 1;

        if (board == null){
            System.out.println("WTF??");
            return null;
        }
        if (board[row][col] == null) return null;
        if (board[row][col].getColor().equals(match.getTurn())) return board[row][col].getMoves(row,col,board, true);
        else return null;
    }

    @PostMapping("/PieceMoves")
    @ResponseBody
    public Piece[][] PieceMoves(@RequestParam String Id, @RequestParam String Source, @RequestParam String Destination){
        Match match = null;
        for (Match i : appData.Matches) {
            if (i.getId().equals(Id)) {
                match = i;
                break;
            }
        }
        if (match == null) return null;
        match.Create_Move_Annotation(Source,Destination);
        match.Move(Source,Destination);

        Piece[][] board = match.getBoard();

        return board;
    }

    @PostMapping("/CheckPromotion")
    @ResponseBody
    public boolean CheckPromotion(@RequestParam String Id, @RequestParam String Destination){
        Match match = null;
        for (Match i : appData.Matches) {
            if (i.getId().equals(Id)) {
                match = i;
                break;
            }
        }
        if (match == null) return false;
        Piece[][] board = match.getBoard();
        List<Integer> destination_coordonates = ChessLogic.Numeral_Language(Destination);

        Integer i = destination_coordonates.get(0);
        Integer j = destination_coordonates.get(1);

        if (board[i][j] != null){
            if (board[i][j].getType().equals("Pawn") && (i == 1 || i == 8)){
                return true;
            }
        }

        match.setTurn();
        return false;
    }

    @PostMapping("/ChoosePromotion")
    @ResponseBody
    public Piece[][] CheckPromotion(@RequestParam String Id, @RequestParam String Destination, @RequestParam String Piece_name){
        Match match = null;
        for (Match i : appData.Matches) {
            if (i.getId().equals(Id)) {
                match = i;
                break;
            }
        }
        if (match == null) return null;

        Piece[][] board = match.getBoard();
        LinkedList<String> Istoric = match.getIstoric();
        String lastMove = Istoric.get(Istoric.size() - 1);
        List<Integer> destination_coordonates = ChessLogic.Numeral_Language(Destination);
        Integer i = destination_coordonates.get(0);
        Integer j = destination_coordonates.get(1);

        String equals_piece = null;
        switch (Piece_name){
            case "QUEEN":
                board[i][j] = new Queen(board[i][j].getColor());
                equals_piece = "=Q";
                break;
            case "ROOK":
                board[i][j] = new Rook(board[i][j].getColor());
                equals_piece = "=R";
                break;
            case "BISHOP":
                board[i][j] = new Bishop(board[i][j].getColor());
                equals_piece = "=B";
                break;
            case "KNIGHT":
                board[i][j] = new Knight(board[i][j].getColor());
                equals_piece = "=K";
                break;
        }

        if (lastMove.endsWith("+") || lastMove.endsWith("#")) {
            lastMove = lastMove.substring(0, lastMove.length() - 1);
        }

        if (ChessLogic.isCheck(match.getTurn(), board)){
            Istoric.set(Istoric.size() - 1, lastMove + equals_piece + "+");

            // Check for Checkmate
            if(ChessLogic.isCheckmate(match.getTurn(), board)){
                Istoric.set(Istoric.size() - 1, Istoric.get(Istoric.size() - 1).replace("+", "#"));

                if (match.getTurn().equals("White")) {
                    Istoric.set(Istoric.size() - 1, Istoric.get(Istoric.size() - 1) + " (White won)");
                }
                else Istoric.set(Istoric.size() - 1, Istoric.get(Istoric.size() - 1) + " (Black won)");

                // EXTRA DELETION OR SMTH
                DatabaseService.getInstance().Delete(match);
                appData.Matches.remove(match);
            }
        }else{
            Istoric.set(Istoric.size() - 1, lastMove + equals_piece);
        }
        // Check for Draw
        if (ChessLogic.is_Draw(match.getTurn(), board, Istoric)){
            Istoric.set(Istoric.size() - 1, Istoric.get(Istoric.size() - 1) + " (DRAW)");

            // EXTRA DELETION OR SMTH
            DatabaseService.getInstance().Delete(match);
            appData.Matches.remove(match);
        }

        match.setTurn();
        return board;
    }

    @PostMapping("/UpdateStatitics")
    @ResponseBody
    public String UpdateStatistics(@RequestParam String Id){
        Match match = null;
        for (Match i : appData.Matches) {
            if (i.getId().equals(Id)) {
                match = i;
                break;
            }
        }
        if (match == null) return null;

        return match.getIstoric().getLast();
    }

    @PostMapping("/UpdateDatabase")
    @ResponseBody
    public void UpdateDatabase(@RequestParam String Id){
        Match match = null;
        for (Match i : appData.Matches) {
            if (i.getId().equals(Id)) {
                match = i;
                break;
            }
        }
        if (match == null){
            System.out.println("Match not found.");
            return;
        }

        String turn = (match.getTurn().equals("White")) ? "Black" : "White";
        if (ChessLogic.isCheckmate(turn, match.getBoard()) || ChessLogic.is_Draw(turn, match.getBoard(), match.getIstoric())){
            DatabaseService.getInstance().Delete(match);
            appData.removeMatchById(match.getId());
        }
        else DatabaseService.getInstance().Update(match);
    }
}
