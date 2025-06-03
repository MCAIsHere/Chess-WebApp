package com.example.chess.Model.Pieces;

import com.example.chess.Model.ChessLogic;

import java.util.ArrayList;
import java.util.List;

public abstract class Piece {
    protected String color;
    protected Boolean has_moved;

    public Piece(String color){
        this.color = color;
        this.has_moved = false;
    }
    public abstract Piece copy();
    public abstract String getType();
    public abstract List<String> getMoves(int row, int column, Piece[][] Board, boolean checkproof);
    public String getColor(){return color;}
    public static boolean Inside_Board(int row, int col){
        return row >= 1 && row <= 8 && col >= 1 && col <= 8;
    }
    public void Piece_has_moved(){ has_moved = true; }
}
