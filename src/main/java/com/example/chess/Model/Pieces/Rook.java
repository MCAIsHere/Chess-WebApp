package com.example.chess.Model.Pieces;

import java.util.ArrayList;
import java.util.List;

public class Rook extends Piece{

    public Rook(String color){
        super(color);
    }

    @Override
    public String getType(){
        return "Rook";
    }

    @Override
    public List<String> getMoves(int row, int column, Piece[][] Board){
        List<String> result = new ArrayList<>();

        // Up
        for (int i = row + 1; i <= 8; i++){
            if (Board[i][column] != null){
                if (!Board[i][column].color.equals(this.color)) result.add(Chess_Language(i, column));
                break;
            }
            result.add(Chess_Language(i, column));
        }

        // Down
        for (int i = row - 1; i >= 1; i--){
            if (Board[i][column] != null){
                if (!Board[i][column].color.equals(this.color)) result.add(Chess_Language(i, column));
                break;
            }
            result.add(Chess_Language(i, column));
        }

        // Left
        for (int i = column + 1; i <= 8; i++){
            if (Board[row][i] != null){
                if (!Board[row][i].color.equals(this.color)) result.add(Chess_Language(row, i));
                break;
            }
            result.add(Chess_Language(row, i));
        }

        // Right
        for (int i = column - 1; i >= 1; i--){
            if (Board[row][i] != null){
                if (!Board[row][i].color.equals(this.color)) result.add(Chess_Language(row, i));
                break;
            }
            result.add(Chess_Language(row, i));
        }

        return result;
    }
}
