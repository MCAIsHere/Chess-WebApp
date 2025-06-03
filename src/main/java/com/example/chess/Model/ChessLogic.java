package com.example.chess.Model;

import com.example.chess.Model.Pieces.Pawn;
import com.example.chess.Model.Pieces.Piece;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public final class ChessLogic {
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

    public static boolean isCheck(String color, Piece[][] Board) {
        List<String> squaresCovered = new ArrayList<>();
        String kingPosition = "";

        for (int i = 1; i <= 8; i++) {
            for (int j = 1; j <= 8; j++) {
                if (Board[i][j] != null) {
                    if (Board[i][j].getType().equals("King") && !Board[i][j].getColor().equals(color)) {
                        kingPosition = ChessLogic.Chess_Language(i, j);
                    } else if (Board[i][j].getColor().equals(color)) {
                        squaresCovered.addAll(Board[i][j].getMoves(i, j, Board, false));
                    }
                }
            }
        }
        return squaresCovered.contains(kingPosition);
    }
    public static boolean is_Short_Castle(String Position_a, String Position_b){
        return (Math.abs(Position_a.charAt(0) - Position_b.charAt(0)) == 2 && Position_a.charAt(0) < Position_b.charAt(0));
    }
    public static boolean is_Long_Castle(String Position_a, String Position_b){
        return (Math.abs(Position_a.charAt(0) - Position_b.charAt(0)) == 2 && Position_a.charAt(0) > Position_b.charAt(0));
    }
    public static boolean is_EnPassant(int s_row, int s_column, int d_row, int d_column, Piece[][] Board){
        if (s_column == d_column) return false;
        return (Board[d_row][d_column] == null && Board[s_row][d_column].getType().equals("Pawn")
                && ((Pawn)Board[s_row][d_column]).isEn_passantable());
    }
    public static boolean isCheckmate(String color, Piece[][] Board) {
        for (int i = 1; i <= 8; i++) {
            for (int j = 1; j <= 8; j++) {
                if (Board[i][j] != null){
                    if (!Board[i][j].getColor().equals(color)){
                        List<String> legalMoves = Board[i][j].getMoves(i, j, Board, true);  // true = checkproof

                        if (!legalMoves.isEmpty()) {
                            return false;  // Found a legal move => not checkmate
                        }
                    }
                }
            }
        }

        System.out.println("Checkmate!!");
        return true;
    }
    public static boolean is_Draw(String color, Piece[][] Board, LinkedList<String> Istoric){
        // 1. Stalemate
        if (noLegalMoves(color, Board)){
            return true;
        }

        // 2. Insufficient Material
        if (isInsufficientMaterial(Board)) {
            return true;
        }

        // 3. Fifty-Move Rule
        // ...

        // 4. Threefold Repetition
        if (isThreefoldRepetition(Istoric)) {
            return true;
        }

        return false;
    }
    private static boolean noLegalMoves(String color, Piece[][] Board) {
        for (int i = 1; i <= 8; i++) {
            for (int j = 1; j <= 8; j++) {
                if (Board[i][j] != null && Board[i][j].getColor().equals(color)) {
                    if (!Board[i][j].getMoves(i, j, Board, true).isEmpty()) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
    private static boolean isInsufficientMaterial(Piece[][] Board) {
        List<String> pieces = new ArrayList<>();

        for (int i = 1; i <= 8; i++) {
            for (int j = 1; j <= 8; j++) {
                if (Board[i][j] != null && !Board[i][j].getType().equals("King")) {
                    pieces.add(Board[i][j].getType() + "-" + Board[i][j].getColor());
                }
            }
        }

        // Common insufficient material cases
        if (pieces.isEmpty()) return true; // King vs King
        if (pieces.size() == 1 && (pieces.get(0).startsWith("Bishop") || pieces.get(0).startsWith("Knight"))) return true;

        return false;
    }
    private static boolean isThreefoldRepetition(LinkedList<String> Istoric) {
        if (Istoric.size() < 18) return false; // Need at least 3x6 = 18 half-moves

        // Get the last 6 half-moves (3 full moves)
        List<String> lastSix = Istoric.subList(Istoric.size() - 6, Istoric.size());

        int matches = 0;

        for (int i = 0; i <= Istoric.size() - 6; i++) {
            List<String> candidate = Istoric.subList(i, i + 6);
            if (candidate.equals(lastSix)) {
                matches++;
            }
        }

        return matches >= 3;
    }
}
