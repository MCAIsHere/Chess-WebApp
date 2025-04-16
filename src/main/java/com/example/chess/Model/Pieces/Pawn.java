package com.example.chess.Model.Pieces;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends Piece{
    private boolean En_passantable;
    public Pawn(String color){
        super(color);
        En_passantable = false;
    }

    public boolean isEn_passantable(){return this.En_passantable;}
    @Override
    public String getType(){
        return "Pawn";
    }

    @Override
    public List<String> getMoves(int row, int column, Piece[][] Board){
        List<String> result = new ArrayList<>();
        int direction = (color.equals("Black")) ? -1 : 1;

        // Foward
        if (Inside_Board(row + direction, column) && Board[row + direction][column] == null) {
            result.add(Chess_Language(row + direction, column));

            // Double Foward
            if (!has_moved && Board[row + direction*2][column] == null){
                result.add(Chess_Language(row + direction*2, column));
            }
        }

        // Left Capture
        if (Inside_Board(row + direction, column - 1)){
            if (Board[row + direction][column-1] != null && !Board[row + direction][column-1].color.equals(this.color)){
                result.add(Chess_Language(row + direction, column - 1));
            }
            // En passant (Left)
            else if (Board[row][column - 1] != null && Board[row][column-1].getType().equals("Pawn") && ((Pawn) Board[row][column-1]).isEn_passantable()){
                result.add(Chess_Language(row + direction, column - 1));
            }
        }

        // Right Capture
        if (Inside_Board(row + direction, column + 1)){
            if (Board[row + direction][column+1] != null && !Board[row + direction][column+1].color.equals(this.color)){
                result.add(Chess_Language(row + direction, column + 1));
            }
            // En passant (Right)
            else if(Board[row][column + 1] != null && Board[row][column+1].getType().equals("Pawn") && ((Pawn) Board[row][column+1]).isEn_passantable()){
                result.add(Chess_Language(row + direction, column + 1));
            }
        }
        return result;
    }
}
