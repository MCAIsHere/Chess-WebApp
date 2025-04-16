package com.example.chess.Model.Pieces;
import java.util.ArrayList;
import java.util.List;

public class King extends Piece{

    public King(String color){
        super(color);
    }

    @Override
    public String getType(){
        return "King";
    }

    @Override
    public List<String> getMoves(int row, int column, Piece[][] Board){
        List<String> result = new ArrayList<>();

        int[][] moves = {
                {1, -1}, {1, 0}, {1, 1},
                {0, -1},         {0, 1},
                {-1, -1}, {-1, 0}, {-1, 1}
        };

        for (int[] i : moves){
            if (Inside_Board(row+i[0], column+i[1]) && (Board[row+i[0]][column+i[1]] == null || (Board[row+i[0]][column+i[1]] != null && !Board[row+i[0]][column+i[1]].color.equals(this.color)))) {
                result.add(Chess_Language(row+i[0], column+i[1]));
            }
        }

        // Castling
        if (!this.has_moved){
            if (this.color.equals("Black")){
                // Queen side
                if (Board[8][1] != null && Board[8][1].getType().equals("Rook") && !Board[8][1].has_moved &&
                     Board[8][2] == null && Board[8][3] == null && Board[8][4] == null){
                    result.add(Chess_Language(8, 3));
                }

                // King side
                if (Board[8][8] != null && Board[8][8].getType().equals("Rook") && !Board[8][8].has_moved &&
                    Board[8][6] == null && Board[8][7] == null){
                        result.add(Chess_Language(8, 7));
                }
            }else{
                // Queen side
                if (Board[1][1] != null && Board[1][1].getType().equals("Rook") && !Board[1][1].has_moved &&
                    Board[1][2] == null && Board[1][3] == null && Board[1][4] == null){
                    result.add(Chess_Language(1, 3));
                }

                // King side
                if (Board[1][8] != null && Board[1][8].getType().equals("Rook") && !Board[1][8].has_moved &&
                    Board[1][6] == null && Board[1][7] == null){
                    result.add(Chess_Language(1, 7));
                }
            }
        }
        return result;
    }
}
