package com.example.chess;
import com.example.chess.Model.Match;
import com.example.chess.Model.Pieces.*;
import com.example.chess.Model.Pieces.Piece;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.sql.*;
import java.time.Duration;
import java.util.LinkedList;
import java.util.List;

public class DatabaseService{
    private static DatabaseService instance = null;
    private DatabaseService() {}

    public static DatabaseService getInstance() {
        if (instance == null) {
            instance = new DatabaseService();
        }
        return instance;
    }

    public void Update(Match match){
        String sql = "UPDATE `match` " +
                "SET player1_time = ?, " +
                "    player2_time = ?, " +
                "    player1_draw_accept = ?, " +
                "    player2_draw_accept = ?, " +
                "    Istoric = ?, " +
                "    Board = ?, " +
                "    turn = ? " +
                "WHERE id = ?";

        try{
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://127.0.0.1:3306/chess_schema",
                    "root",
                    "ch3cooh1f"
            );

            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setLong(1, match.getTimers().get(0).toMillis());
            statement.setLong(2, match.getTimers().get(1).toMillis());
            statement.setBoolean(3, match.getDraw_offers().get(0));
            statement.setBoolean(4, match.getDraw_offers().get(1));
            statement.setString(5, String.valueOf(match.getIstoric()));

            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(match.getBoard());
            statement.setString(6, json);

            statement.setString(7, match.getTurn());
            statement.setString(8, match.getId());

            statement.executeUpdate();

            // CSV ACTION
            AuditService.getInstance().logAction("Match " + match.getId() + " updated.");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
    public void Read(List<Match> Matches) {
        String sql = "SELECT * FROM `match`";

        try{
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://127.0.0.1:3306/chess_schema",
                    "root",
                    "ch3cooh1f"
            );

            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                String Id = rs.getString("id");
                String player1_id = rs.getString("player1_id");

                long player1TimeSeconds = rs.getLong("player1_time");
                Duration player1Time = Duration.ofMillis(player1TimeSeconds);

                String player2_id = rs.getString("player2_id");

                long player2TimeSeconds = rs.getLong("player2_time");
                Duration player2Time = Duration.ofMillis(player2TimeSeconds);

                boolean player1_draw_accept = rs.getBoolean("player1_draw_accept");
                boolean player2_draw_accept = rs.getBoolean("player2_draw_accept");

                //
                String istoric_json = rs.getString("istoric");
                String fixedJson = istoric_json.replaceAll("([^\\[\\],\\s]+)", "\"$1\"");
                ObjectMapper istoric_mapper = new ObjectMapper();
                LinkedList<String> istoric = istoric_mapper.readValue(fixedJson, new TypeReference<LinkedList<String>>() {});
                //

                //
                String board_json = rs.getString("board");

                ObjectMapper board_mapper = new ObjectMapper();
                JsonNode root = board_mapper.readTree(board_json);

                Piece[][] board = new Piece[9][9];

                for (int row = 0; row < 9; row++) {
                    JsonNode rowNode = root.get(row);
                    for (int col = 0; col < 9; col++) {
                        JsonNode pieceNode = rowNode.get(col);
                        if (pieceNode != null && !pieceNode.isNull()) {
                            String type = pieceNode.get("type").asText();
                            String color = pieceNode.get("color").asText();

                            switch (type) {
                                case "Pawn" -> {
                                    boolean enPassantable = pieceNode.has("en_passantable") && pieceNode.get("en_passantable").asBoolean();
                                    board[row][col] = new Pawn(color, enPassantable);
                                }
                                case "Rook" -> board[row][col] = new Rook(color);
                                case "Knight" -> board[row][col] = new Knight(color);
                                case "Bishop" -> board[row][col] = new Bishop(color);
                                case "Queen" -> board[row][col] = new Queen(color);
                                case "King" -> board[row][col] = new King(color);
                            }
                        }
                    }
                }
                //

                String game_type = rs.getString("game_type");
                String turn = rs.getString("turn");

                Matches.add(new Match(Id, player1_id, player1Time, player2_id, player2Time,
                        player1_draw_accept, player2_draw_accept, istoric, board, game_type, turn));
                // CSV ACTION
                AuditService.getInstance().logAction("Match " + Id + " read.");
            }
        } catch (SQLException | JsonProcessingException e) {
            e.printStackTrace();
        }
    }
    public void Create(Match match) {
        String sql = "INSERT INTO `match` (id, player1_id, player1_time, player2_id, player2_time, " +
                "player1_draw_accept, player2_draw_accept, Istoric, Board, game_type, turn) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try{
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://127.0.0.1:3306/chess_schema",
                    "root",
                    "ch3cooh1f"
            );

            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, match.getId());
            statement.setInt(2, Integer.parseInt(match.getUsers().get(0))); // Assuming String ID
            statement.setLong(3, match.getTimers().get(0).toMillis());
            statement.setInt(4, Integer.parseInt(match.getUsers().get(1)));
            statement.setLong(5, match.getTimers().get(1).toMillis());
            statement.setBoolean(6, false);
            statement.setBoolean(7, false);
            statement.setString(8, null);

            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(match.getBoard());
            statement.setString(9, json);

            statement.setString(10, match.getGame_type());
            statement.setString(11, "White");

            statement.executeUpdate();

            // CSV ACTION
            AuditService.getInstance().logAction("Match " + match.getId() + " created.");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
    public void Delete(Match match) {
        String sql = "DELETE FROM `match` WHERE id = ?";
        try{
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://127.0.0.1:3306/chess_schema",
                    "root",
                    "ch3cooh1f"
            );

            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, match.getId());
            statement.executeUpdate();

            // CSV ACTION
            AuditService.getInstance().logAction("Match " + match.getId() + " deleted.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
