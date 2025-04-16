package com.example.chess.Model.Pieces;

import java.util.ArrayList;
import java.util.List;

public class Bishop extends Piece{

    public Bishop(String color){
        super(color);
    }

    @Override
    public String getType(){
        return "Bishop";
    }

    @Override
    public List<String> getMoves(int row, int column, Piece[][] Board){
        List<String> result = new ArrayList<>();

        // Up-Left
        for(int i = 1; Inside_Board(row+i,column-i); i++){
            if (Board[row+i][column-i] != null){
                if (!Board[row+i][column-i].color.equals(this.color)) result.add(Chess_Language(row+i, column-i));
                break;
            }
            result.add(Chess_Language(row+i, column-i));
        }

        // Up-Right
        for(int i = 1; Inside_Board(row+i,column+i); i++){
            if (Board[row+i][column+i] != null){
                if (!Board[row+i][column+i].color.equals(this.color)) result.add(Chess_Language(row+i, column+i));
                break;
            }
            result.add(Chess_Language(row+i, column+i));
        }

        // Down-Left
        for(int i = 1; Inside_Board(row-i,column-i); i++){
            if (Board[row-i][column-i] != null){
                if (!Board[row-i][column-i].color.equals(this.color)) result.add(Chess_Language(row-i, column-i));
                break;
            }
            result.add(Chess_Language(row-i, column-i));
        }

        // Down-Right
        for(int i = 1; Inside_Board(row-i,column+i); i++){
            if (Board[row-i][column+i] != null){
                if (!Board[row-i][column+i].color.equals(this.color)) result.add(Chess_Language(row-i, column+i));
                break;
            }
            result.add(Chess_Language(row-i, column+i));
        }

        return result;
    }
}
