package com.example.chess.Model;
import ch.qos.logback.core.joran.sanity.Pair;
import com.example.chess.Model.Pieces.*;

import java.time.Duration;
import java.util.*;

public class Match {
    private String Id;
    private User player1;
    private Duration player1_time;
    private User player2;
    private Duration player2_time;
    private Integer win_condition;
    private Boolean Game_over;
    private User winner;
    private LinkedList<String> Istoric;
    private Piece[][] Board;
    private String game_type;
    private Boolean matchtype;

    public Match(String Id, User player1, User player2, Integer time, String type, boolean matchtype){
        this.Id = Id;
        this.player1 = player1;
        this.player1_time = Duration.ofMinutes(time);
        this.player2 = player2;
        this.player2_time = Duration.ofMinutes(time);
        this.win_condition = 0;
        this.Game_over = false;
        this.winner = null;
        this.Istoric = new LinkedList<>();
        this.Board = new Piece[9][9];
        this.game_type = type;
        this.matchtype = matchtype;

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

    public Piece[][] getBoard() {return Board;}
    public String getId(){return Id;}
    public List<User> getUsers() {
        return Arrays.asList(player1, player2);
    }
    public List<Duration> getTimers(){
        return Arrays.asList(player1_time,player2_time);
    }
    public LinkedList<String> getIstoric(){
        return Istoric;
    }

    public void Move(String Position_a, String Position_b){
        List<Integer> source_coordonates = Piece.Numeral_Language(Position_a);
        List<Integer> destination_coordonates = Piece.Numeral_Language(Position_b);

        String Notation = "";
        // Send the info to Istoric beforehand
        // If a Pawn.
        /*
        if (Board[source_coordonates.get(0)][source_coordonates.get(1)].getType().equals("Pawn")){
            // .simply moves
            if (Board[destination_coordonates.get(0)][destination_coordonates.get(1)] == null){
                Notation = Piece.Chess_Language(destination_coordonates.get(0),destination_coordonates.get(1));
            }
            // .captures
            else{
                Notation = ('a' + source_coordonates.get(1) - 1) + "x" + Piece.Chess_Language(destination_coordonates.get(0),destination_coordonates.get(1));
            }
        }
        */

        System.out.println(source_coordonates.get(0));
        System.out.println(source_coordonates.get(1));
        System.out.println(destination_coordonates.get(0));
        System.out.println(destination_coordonates.get(1));
        // Move the Piece
        Board[destination_coordonates.get(0)][destination_coordonates.get(1)] = Board[source_coordonates.get(0)][source_coordonates.get(1)];
        Board[source_coordonates.get(0)][source_coordonates.get(1)] = null;

        // Check for Checkmates
        // Check for Draws
    }

    public boolean is_Checked(String color){
        List<String> Squares_covered = new ArrayList<>();
        String King_position = new String();
        for (int i = 1; i <= 8; i++) {
            for (int j = 1; j <= 8; j++) {
                if (Board[i][j] != null && Board[i][j].getType().equals("King") && Board[i][j].getColor().equals(color)) {
                    King_position = Piece.Chess_Language(i,j);
                } else if (Board[i][j] != null && !Board[i][j].getColor().equals(color)) {
                    Squares_covered.addAll(Board[i][j].getMoves(i,j,Board));
                }
            }
        }
        return Squares_covered.contains(King_position);
    }
}
