package com.example.chess.Model;
import java.time.LocalDate;
import java.util.*;

public class User {
    private String Id;
    private String Username;

    private Byte[] ProfilePict;
    private LocalDate Data_Inregistrarii;

    private Integer Max_Rating;
    private Integer Rating;
    private Double Win_Rate;

    private Integer Matches_Won;
    private Integer Matches_Lost;
    private Integer Matches_Played;
    private List<Match> Match_History;
    private List<User> Friends;

    public User(String Id, String Username, Byte[] ProfilePict) {
        this.Id = Id;
        this.Username = Username;
        this.ProfilePict = ProfilePict;
        this.Data_Inregistrarii = LocalDate.now();
        this.Max_Rating = 400;
        this.Rating = 400;
        this.Win_Rate = 0.0;
        this.Matches_Won = 0;
        this.Matches_Lost = 0;
        this.Matches_Played = 0;
        this.Match_History = new ArrayList<>();
        this.Friends = new ArrayList<>();
    }

    public String getId() {return Id;}
    public String getUsername() {return Username;}
}
