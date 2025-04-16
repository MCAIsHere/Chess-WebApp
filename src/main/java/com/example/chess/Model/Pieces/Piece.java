package com.example.chess.Model.Pieces;

import java.util.ArrayList;
import java.util.List;

public abstract class Piece {
    protected String color;
    protected Boolean has_moved;

    public Piece(String color){
        this.color = color;
        this.has_moved = false;
    }
    public abstract String getType();
    public String getColor(){return color;}
    public abstract List<String> getMoves(int row, int column, Piece[][] Board);

    public static String Chess_Language(int row, int column) {
        char columnChar = (char) ('A' + (column - 1));
        return columnChar + Integer.toString(row);
    }
    public static List<Integer> Numeral_Language(String position){
        List<Integer> result = new ArrayList<>();
        result.add(position.charAt(1) - '0');
        result.add(position.charAt(0) - 'A' + 1);
        return result;
    }
    protected boolean Inside_Board(int row, int col){
        return row >= 1 && row <= 8 && col >= 1 && col <= 8;
    }
}
