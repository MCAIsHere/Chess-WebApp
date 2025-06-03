package com.example.chess;
import com.example.chess.Model.FriendLink;
import com.example.chess.Model.Match;
import com.example.chess.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.*;

@Service
public class AppData {
    public List<Match> Matches;
    public List<User> Users;
    public List<FriendLink> FriendLinks;

    @Autowired
    public AppData(List<Match> Matches, List<User> Users, List<FriendLink> FriendLinks) {
        this.Matches = Matches;
        this.Users = Users;
        this.FriendLinks = FriendLinks;

        DatabaseService.getInstance().Read(Matches);

        Users.add(new User("1", "Catalin", new Byte[]{1, 2, 3}));
        Users.add(new User("2", "Popa", new Byte[]{4, 5, 6}));
        Users.add(new User("3", "Rares", new Byte[]{7, 8, 9}));
        Users.get(0).addFriend_id(Users.get(1).getId());
        Users.get(0).addFriend_id(Users.get(2).getId());
    }

    public Match getMatchById(String id) {
        for (Match i : Matches){
            if (i.getId().equals(id)) return i;
        }
        return null;
    }
    public void removeMatchById(String id){
        Iterator<Match> iterator = Matches.iterator();
        while (iterator.hasNext()) {
            Match i = iterator.next();
            if (i.getId().equals(id)) {
                iterator.remove();
                break;
            }
        }
    }
}