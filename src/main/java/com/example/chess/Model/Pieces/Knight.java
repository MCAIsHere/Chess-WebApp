package com.example.chess.Model.Pieces;

import java.util.ArrayList;
import java.util.List;

public class Knight extends Piece{

    public Knight(String color){super(color);}

    @Override
    public String getType(){
        return "Knight";
    }

    @Override
    public List<String> getMoves(int row, int column, Piece[][] Board){
        List<String> result = new ArrayList<>();

        int[][] moves = {
                {2, -1}, {2, 1}, {-2, -1}, {-2, 1},
                {1, -2}, {1, 2}, {-1, -2}, {-1, 2}
        };

        for (int[] i : moves){
            if (Inside_Board(row+i[0], column+i[1]) && (Board[row+i[0]][column+i[1]] == null || (Board[row+i[0]][column+i[1]] != null && !Board[row+i[0]][column+i[1]].color.equals(this.color)))) {
                result.add(Chess_Language(row+i[0], column+i[1]));
            }
        }
        return result;
    }
}
