package com.example.chess.Model;
import java.time.LocalDate;
import com.example.chess.Model.User;

public class FriendLink {
    private String user1_id;
    private String user2_id;
    private LocalDate data_initiala;

    public FriendLink(String user1_id, String user2_id, LocalDate data_initiala){
        this.user1_id = user1_id;
        this.user2_id = user2_id;
        this.data_initiala = data_initiala;
    }
}
