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
    private List<String> Friends_id;

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
        this.Friends_id = new ArrayList<>();
    }

    public String getId() {return Id;}
    public String getUsername() {return Username;}
    public List<String> getFriends_id(){
        return Friends_id;
    }
    public LocalDate getData_Inregistrarii() {return Data_Inregistrarii;}
    public Integer getMax_Rating() {return Max_Rating;}
    public Integer getRating() {return Rating;}
    public Double getWin_Rate() {return Win_Rate;}
    public Integer getMatches_Played() {return Matches_Played;}
    public void addFriend_id(String user_id){
        Friends_id.add(user_id);
    }
}
