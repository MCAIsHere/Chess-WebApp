package com.example.chess.Model.Pieces;
import com.example.chess.Model.ChessLogic;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class King extends Piece{

    public King(String color){
        super(color);
    }
    public King(King original) {
        super(original.color);
        // Copy subclass-specific fields here if needed
    }
    @Override
    public Piece copy() {
        return new King(this);
    }
    @Override
    public String getType(){
        return "King";
    }

    @Override
    public List<String> getMoves(int row, int column, Piece[][] Board, boolean checkproof){
        List<String> result = new ArrayList<>();

        int[][] moves = {
                {1, -1}, {1, 0}, {1, 1},
                {0, -1},         {0, 1},
                {-1, -1}, {-1, 0}, {-1, 1}
        };

        for (int[] i : moves){
            if (Inside_Board(row+i[0], column+i[1]) && (Board[row+i[0]][column+i[1]] == null ||
                    (Board[row+i[0]][column+i[1]] != null && !Board[row+i[0]][column+i[1]].color.equals(this.color)))) {
                result.add(ChessLogic.Chess_Language(row+i[0], column+i[1]));
            }
        }

        // Castling
        if (!has_moved && checkproof){
            if (color.equals("Black") && !ChessLogic.isCheck("White", Board)){
                // Queen side
                if (Board[8][1] != null && Board[8][1].getType().equals("Rook") && !Board[8][1].has_moved &&
                     Board[8][2] == null && Board[8][3] == null && Board[8][4] == null){
                    result.add(ChessLogic.Chess_Language(8, 3));
                }

                // King side
                if (Board[8][8] != null && Board[8][8].getType().equals("Rook") && !Board[8][8].has_moved &&
                    Board[8][6] == null && Board[8][7] == null){
                        result.add(ChessLogic.Chess_Language(8, 7));
                }
            }else if (color.equals("White") && !ChessLogic.isCheck("Black", Board)){
                // Queen side
                if (Board[1][1] != null && Board[1][1].getType().equals("Rook") && !Board[1][1].has_moved &&
                    Board[1][2] == null && Board[1][3] == null && Board[1][4] == null){
                    result.add(ChessLogic.Chess_Language(1, 3));
                }

                // King side
                if (Board[1][8] != null && Board[1][8].getType().equals("Rook") && !Board[1][8].has_moved &&
                    Board[1][6] == null && Board[1][7] == null){
                    result.add(ChessLogic.Chess_Language(1, 7));
                }
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
                String Position_a = ChessLogic.Chess_Language(row, column);

                if (clonedBoard[row][column] != null){
                    // Handle castling
                    if (clonedBoard[row][column].getType().equals("King")) {
                        if (ChessLogic.is_Short_Castle(Position_a, move)) {
                            clonedBoard[row][6] = Board[row][8];
                            clonedBoard[row][8] = null;
                        } else if (ChessLogic.is_Long_Castle(Position_a, move)) {
                            clonedBoard[row][4] = Board[row][1];
                            clonedBoard[row][1] = null;
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
