package com.example.chess.Model.Pieces;

import com.example.chess.Model.ChessLogic;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Bishop extends Piece{

    public Bishop(String color){
        super(color);
    }
    public Bishop(Bishop original) {
        super(original.color);
        // Copy subclass-specific fields here if needed
    }
    @Override
    public Piece copy() {
        return new Bishop(this);
    }
    @Override
    public String getType(){
        return "Bishop";
    }

    @Override
    public List<String> getMoves(int row, int column, Piece[][] Board, boolean checkproof){
        List<String> result = new ArrayList<>();

        // Up-Left
        for(int i = 1; Inside_Board(row+i,column-i); i++){
            if (Board[row+i][column-i] != null){
                if (!Board[row+i][column-i].color.equals(this.color)) result.add(ChessLogic.Chess_Language(row+i, column-i));
                break;
            }
            result.add(ChessLogic.Chess_Language(row+i, column-i));
        }

        // Up-Right
        for(int i = 1; Inside_Board(row+i,column+i); i++){
            if (Board[row+i][column+i] != null){
                if (!Board[row+i][column+i].color.equals(this.color)) result.add(ChessLogic.Chess_Language(row+i, column+i));
                break;
            }
            result.add(ChessLogic.Chess_Language(row+i, column+i));
        }

        // Down-Left
        for(int i = 1; Inside_Board(row-i,column-i); i++){
            if (Board[row-i][column-i] != null){
                if (!Board[row-i][column-i].color.equals(this.color)) result.add(ChessLogic.Chess_Language(row-i, column-i));
                break;
            }
            result.add(ChessLogic.Chess_Language(row-i, column-i));
        }

        // Down-Right
        for(int i = 1; Inside_Board(row-i,column+i); i++){
            if (Board[row-i][column+i] != null){
                if (!Board[row-i][column+i].color.equals(this.color)) result.add(ChessLogic.Chess_Language(row-i, column+i));
                break;
            }
            result.add(ChessLogic.Chess_Language(row-i, column+i));
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
