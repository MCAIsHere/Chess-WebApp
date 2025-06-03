package com.example.chess.Model;
import com.example.chess.Model.Pieces.*;
import java.time.Duration;
import java.util.*;
import static java.lang.Math.abs;

public class Match {
    private String Id;
    private String player1_id;
    private Duration player1_time;
    private String player2_id;
    private Duration player2_time;
    private boolean player1_draw_accept;
    private boolean player2_draw_accept;
    private LinkedList<String> Istoric;
    private Piece[][] Board;
    private String game_type;
    private String turn;

    public Match(String Id, String player1_id, String player2_id, Integer time, String type){
        this.Id = Id;
        this.player1_id = player1_id;
        this.player1_time = Duration.ofMinutes(time);
        this.player2_id = player2_id;
        this.player2_time = Duration.ofMinutes(time);
        this.player1_draw_accept = false;
        this.player2_draw_accept = false;
        this.Istoric = new LinkedList<>();
        this.Board = new Piece[9][9];
        this.game_type = type;
        this.turn = "White";

        Board[1][1] = new Rook("White");
        Board[1][2] = new Knight("White");
        Board[1][3] = new Bishop("White");
        Board[1][4] = new Queen("White");
        Board[1][5] = new King("White");
        Board[1][6] = new Bishop("White");
        Board[1][7] = new Knight("White");
        Board[1][8] = new Rook("White");
        for (int i = 1; i <= 8; i++){
            Board[2][i] = new Pawn("White");
        }

        for (int i = 1; i <= 8; i++){
            Board[7][i] = new Pawn("Black");
        }
        Board[8][1] = new Rook("Black");
        Board[8][2] = new Knight("Black");
        Board[8][3] = new Bishop("Black");
        Board[8][4] = new Queen("Black");
        Board[8][5] = new King("Black");
        Board[8][6] = new Bishop("Black");
        Board[8][7] = new Knight("Black");
        Board[8][8] = new Rook("Black");
    }

    public Match(String Id, String player1_id, Duration player1_time, String player2_id, Duration player2_time,
                 boolean player1_draw_accept, boolean player2_draw_accept, LinkedList<String> Istoric,  Piece[][] Board, String game_type, String turn){
        this.Id = Id;
        this.player1_id = player1_id;
        this.player1_time = player1_time;
        this.player2_id = player2_id;
        this.player2_time = player2_time;
        this.player1_draw_accept = player1_draw_accept;
        this.player2_draw_accept = player2_draw_accept;
        this.Istoric = Istoric;
        this.Board = Board;
        this.game_type = game_type;
        this.turn = turn;

    }

    public Piece[][] getBoard() {return Board;}
    public String getId(){return Id;}
    public List<String> getUsers() {
        return Arrays.asList(player1_id, player2_id);
    }
    public List<Duration> getTimers(){
        return Arrays.asList(player1_time,player2_time);
    }
    public LinkedList<String> getIstoric(){
        return Istoric;
    }
    public String getTurn() {
        return turn;
    }
    public String getGame_type() { return game_type; }
    public void setTurn() {if (turn.equals("White")){turn = "Black";}else{turn = "White";}}
    public List<Boolean> getDraw_offers() {return Arrays.asList(player1_draw_accept,player2_draw_accept); }

    public void Move(String Position_a, String Position_b){
        List<Integer> source_coordonates = ChessLogic.Numeral_Language(Position_a);
        List<Integer> destination_coordonates = ChessLogic.Numeral_Language(Position_b);

        // If the move it's an en_passant
        if (Board[source_coordonates.get(0)][source_coordonates.get(1)].getType().equals("Pawn")){
            if (ChessLogic.is_EnPassant(source_coordonates.get(0),source_coordonates.get(1), destination_coordonates.get(0), destination_coordonates.get(1), Board)){
                Board[source_coordonates.get(0)][destination_coordonates.get(1)] = null;
            }
        }

        // Remove En_passantable status from the rest
        for (int i = 1; i <= 8; i++) {
            for (int j = 1; j <= 8; j++) {
                if (Board[i][j] != null) {
                    if (Board[i][j].getType().equals("Pawn") && ((Pawn) Board[i][j]).isEn_passantable()) {
                        ((Pawn) Board[i][j]).loseEn_passant();
                    }
                }
            }
        }

        // Set En_passantable status
        if (Board[source_coordonates.get(0)][source_coordonates.get(1)].getType().equals("Pawn") &&
            abs(source_coordonates.get(0)-destination_coordonates.get(0)) == 2)
            ((Pawn) Board[source_coordonates.get(0)][source_coordonates.get(1)]).setEn_passant();

        // Set Has_moved status
        Board[source_coordonates.get(0)][source_coordonates.get(1)].Piece_has_moved();

        // If the move it's a castle
        if (Board[source_coordonates.get(0)][source_coordonates.get(1)].getType().equals("King")){
            if (ChessLogic.is_Short_Castle(Position_a,Position_b)){
                Board[source_coordonates.get(0)][6] = Board[source_coordonates.get(0)][8];
                Board[source_coordonates.get(0)][8] = null;
            }else if (ChessLogic.is_Long_Castle(Position_a,Position_b)){
                Board[source_coordonates.get(0)][4] = Board[source_coordonates.get(0)][1];
                Board[source_coordonates.get(0)][1] = null;
            }
        }

        // Actual move
        Board[destination_coordonates.get(0)][destination_coordonates.get(1)] = Board[source_coordonates.get(0)][source_coordonates.get(1)];
        Board[source_coordonates.get(0)][source_coordonates.get(1)] = null;

        // Check for Check
        if (ChessLogic.isCheck(getTurn(), Board)){
            Istoric.set(Istoric.size() - 1, Istoric.get(Istoric.size() - 1) + "+");

            // Check for Checkmate
            if(ChessLogic.isCheckmate(getTurn(), Board)){
                Istoric.set(Istoric.size() - 1, Istoric.get(Istoric.size() - 1).replace("+", "#"));
                if (getTurn().equals("White")) {
                    Istoric.set(Istoric.size() - 1, Istoric.get(Istoric.size() - 1) + " (White won)");
                }
                else Istoric.set(Istoric.size() - 1, Istoric.get(Istoric.size() - 1) + " (Black won)");
            }
        }
        // Check for Draw
        if (ChessLogic.is_Draw(getTurn(), Board, Istoric)){
            Istoric.set(Istoric.size() - 1, Istoric.get(Istoric.size() - 1) + " (DRAW)");
        }
    }

    public void Create_Move_Annotation(String Position_a, String Position_b){
        List<Integer> source_coordonates = ChessLogic.Numeral_Language(Position_a);
        List<Integer> destination_coordonates = ChessLogic.Numeral_Language(Position_b);
        String Notation = "";

        switch (Board[source_coordonates.get(0)][source_coordonates.get(1)].getType()){
            case "Pawn":
                if (Board[destination_coordonates.get(0)][destination_coordonates.get(1)] != null ||
                        ChessLogic.is_EnPassant(source_coordonates.get(0),source_coordonates.get(1), destination_coordonates.get(0), destination_coordonates.get(1), Board)){
                    Notation += Character.toLowerCase(Position_a.charAt(0)) + "x";
                }
                Notation += Position_b.toLowerCase();
                break;
            case "Knight":
                Notation += "N";
                if (Board[destination_coordonates.get(0)][destination_coordonates.get(1)] != null){
                    Notation += "x";
                }
                Notation += Position_b.toLowerCase();
                break;
            case "Bishop":
                Notation += "B";
                if (Board[destination_coordonates.get(0)][destination_coordonates.get(1)] != null){
                    Notation += "x";
                }
                Notation += Position_b.toLowerCase();
                break;
            case "Rook":
                Notation += "R";
                if (Board[destination_coordonates.get(0)][destination_coordonates.get(1)] != null){
                    Notation += "x";
                }
                Notation += Position_b.toLowerCase();
                break;
            case "Queen":
                Notation += "Q";
                if (Board[destination_coordonates.get(0)][destination_coordonates.get(1)] != null){
                    Notation += "x";
                }
                Notation += Position_b.toLowerCase();
                break;
            case "King":
                Notation += "K";
                if (Board[destination_coordonates.get(0)][destination_coordonates.get(1)] != null){
                    Notation += "x";
                }
                Notation += Position_b.toLowerCase();
                if (ChessLogic.is_Short_Castle(Position_a, Position_b)) Notation = "O-O";
                if (ChessLogic.is_Long_Castle(Position_a, Position_b)) Notation = "O-O-O";
                break;
        }
        Istoric.add(Notation);
    }
}
