package com.example.chess.Model;
import java.time.LocalDate;
import com.example.chess.Model.User;

public class FriendLink {
    private User user1;
    private User user2;
    private LocalDate data_initiala;

    public FriendLink(User user1, User user2, LocalDate data_initiala){
        this.user1 = user1;
        this.user2 = user2;
        this.data_initiala = data_initiala;
    }
}
