package com.example.chess;
import com.example.chess.Model.FriendLink;
import com.example.chess.Model.Match;
import com.example.chess.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AppData {
    public List<Match> Matches = new ArrayList<>();
    public List<User> Users = new ArrayList<>();
    public List<FriendLink> FriendLinks = new ArrayList<>();

    @Autowired
    public AppData(List<Match> Matches, List<User> Users, List<FriendLink> FriendLinks) {
        this.Matches = Matches;
        this.Users = Users;
        this.FriendLinks = FriendLinks;

        Users.add(new User("1", "Catalin", new Byte[]{1, 2, 3}));
        Users.add(new User("2", "Popa", new Byte[]{4, 5, 6}));
        Users.add(new User("3", "Rares", new Byte[]{7, 8, 9}));
        Users.get(0).AddFriend(Users.get(1));
        Users.get(0).AddFriend(Users.get(2));
    }

    public Match getMatchById(String id) {
        for (Match i : Matches){
            if (i.getId().equals(id)) return i;
        }
        return null;
    }
}