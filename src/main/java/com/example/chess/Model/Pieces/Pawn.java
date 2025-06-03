package com.example.chess.Model.Pieces;

import com.example.chess.Model.ChessLogic;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Pawn extends Piece{
    private boolean En_passantable;
    public Pawn(String color){
        super(color);
        En_passantable = false;
    }
    public Pawn(Pawn original) {
        super(original.color);
        this.En_passantable = original.En_passantable;
        // Copy subclass-specific fields here if needed
    }

    public Pawn(String color, Boolean En_passantable){
        super(color);
        this.En_passantable = En_passantable;
    }

    @Override
    public Piece copy() {
        return new Pawn(this);
    }

    public boolean isEn_passantable(){return En_passantable;}

    public void setEn_passant(){ En_passantable = true; }
    public void loseEn_passant(){ En_passantable = false; }
    @Override
    public String getType(){
        return "Pawn";
    }

    @Override
    public List<String> getMoves(int row, int column, Piece[][] Board, boolean checkproof){
        List<String> result = new ArrayList<>();
        int direction = (color.equals("Black")) ? -1 : 1;

        // Foward
        if (Inside_Board(row + direction, column) && Board[row + direction][column] == null) {
            result.add(ChessLogic.Chess_Language(row + direction, column));
        }
        // Double Foward
        if (Inside_Board(row + direction*2, column) && !has_moved && Board[row + direction*2][column] == null){
            result.add(ChessLogic.Chess_Language(row + direction*2, column));
        }


        // Left Capture
        if (Inside_Board(row + direction, column - 1)){
            if (Board[row + direction][column-1] != null && !Board[row + direction][column-1].color.equals(this.color)){
                result.add(ChessLogic.Chess_Language(row + direction, column - 1));
            }
            // En passant (Left)
            else if (Board[row][column - 1] != null && Board[row][column-1].getType().equals("Pawn")
                    && ((Pawn) Board[row][column-1]).isEn_passantable() && !Board[row][column - 1].getColor().equals(Board[row][column].getColor())){
                result.add(ChessLogic.Chess_Language(row + direction, column - 1));
            }
        }

        // Right Capture
        if (Inside_Board(row + direction, column + 1)){
            if (Board[row + direction][column+1] != null && !Board[row + direction][column+1].color.equals(this.color)){
                result.add(ChessLogic.Chess_Language(row + direction, column + 1));
            }
            // En passant (Right)
            else if(Board[row][column + 1] != null && Board[row][column+1].getType().equals("Pawn")
                    && ((Pawn) Board[row][column+1]).isEn_passantable() && !Board[row][column + 1].getColor().equals(Board[row][column].getColor())){
                result.add(ChessLogic.Chess_Language(row + direction, column + 1));
            }
        }

        if (checkproof){
            Iterator<String> iterator = result.iterator();
            while (iterator.hasNext()) {
                String move = iterator.next();

                Piece[][] clonedBoard = new Piece[9][9];
                for (int i = 1; i <= 8; i++) {
                    for (int j = 1; j <= 8; j++) {
                        if (Board[i][j] != null) {
                            clonedBoard[i][j] = Board[i][j].copy(); // Polymorphic deep copy
                        } else {
                            clonedBoard[i][j] = null;
                        }
                    }
                }

                List<Integer> toCoords = ChessLogic.Numeral_Language(move);

                if (clonedBoard[row][column] != null){
                    // Handle en passant
                    if (clonedBoard[row][column].getType().equals("Pawn")) {
                        if (ChessLogic.is_EnPassant(row, column, toCoords.get(0), toCoords.get(1), clonedBoard)) {
                            clonedBoard[row][column] = null;
                        }
                    }
                }

                // Apply the move
                clonedBoard[toCoords.get(0)][toCoords.get(1)] = clonedBoard[row][column];
                clonedBoard[row][column] = null;

                if (color.equals("Black") && ChessLogic.isCheck("White", clonedBoard) ||
                        color.equals("White") && ChessLogic.isCheck("Black", clonedBoard)) {
                    iterator.remove(); // Safely remove from list
                }
            }
        }

        return result;
    }
}
