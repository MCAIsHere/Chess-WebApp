package com.example.chess.Controller;
import com.example.chess.AppData;
import com.example.chess.Model.Match;
import com.example.chess.Model.Pieces.*;
import com.example.chess.Model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.Console;
import java.time.Duration;
import java.util.ArrayList;
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
        List<User> users = match.getUsers();
        List<Duration> timers = match.getTimers();

        String username_one = users.get(0).getUsername();
        String username_two = users.get(1).getUsername();
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

        Piece[][] board = match.getBoard();
        int row = Character.getNumericValue(Position.charAt(1));
        int col = Position.charAt(0) - 'A' + 1;

        if (board != null){
            /*
            System.out.println(row);
            System.out.println(col);
            System.out.println(board[row][col].getMoves(row,col,board));
            */
            return board[row][col].getMoves(row,col,board);
        }

        return null;
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

        match.Move(Source,Destination);

        Piece[][] board = match.getBoard();
        return board;
    }
}
