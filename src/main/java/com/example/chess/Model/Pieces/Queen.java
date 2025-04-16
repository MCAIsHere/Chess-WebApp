package com.example.chess.Model.Pieces;

import java.util.ArrayList;
import java.util.List;

public class Queen extends Piece{

    public Queen(String color){
        super(color);
    }
    @Override
    public String getType(){
        return "Queen";
    }
    @Override
    public List<String> getMoves(int row, int column, Piece[][] Board){
        List<String> result = new ArrayList<>();

        // Rook

        for (int i = row + 1; i <= 8; i++){
            if (Board[i][column] != null){
                if (!Board[i][column].color.equals(this.color)) result.add(Chess_Language(i, column));
                break;
            }
            result.add(Chess_Language(i, column));
        }
        for (int i = row - 1; i >= 1; i--){
            if (Board[i][column] != null){
                if (!Board[i][column].color.equals(this.color)) result.add(Chess_Language(i, column));
                break;
            }
            result.add(Chess_Language(i, column));
        }
        for (int i = column + 1; i <= 8; i++){
            if (Board[row][i] != null){
                if (!Board[row][i].color.equals(this.color)) result.add(Chess_Language(row, i));
                break;
            }
            result.add(Chess_Language(row, i));
        }
        for (int i = column - 1; i >= 1; i--){
            if (Board[row][i] != null){
                if (!Board[row][i].color.equals(this.color)) result.add(Chess_Language(row, i));
                break;
            }
            result.add(Chess_Language(row, i));
        }

        // Bishop

        for(int i = 1; Inside_Board(row+i,column-i); i++){
            if (Board[row+i][column-i] != null){
                if (!Board[row+i][column-i].color.equals(this.color)) result.add(Chess_Language(row+i, column-i));
                break;
            }
            result.add(Chess_Language(row+i, column-i));
        }
        for(int i = 1; Inside_Board(row+i,column+i); i++){
            if (Board[row+i][column+i] != null){
                if (!Board[row+i][column+i].color.equals(this.color)) result.add(Chess_Language(row+i, column+i));
                break;
            }
            result.add(Chess_Language(row+i, column+i));
        }
        for(int i = 1; Inside_Board(row-i,column-i); i++){
            if (Board[row-i][column-i] != null){
                if (!Board[row-i][column-i].color.equals(this.color)) result.add(Chess_Language(row-i, column-i));
                break;
            }
            result.add(Chess_Language(row-i, column-i));
        }
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
