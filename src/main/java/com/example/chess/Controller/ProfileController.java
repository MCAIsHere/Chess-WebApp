package com.example.chess.Controller;
import com.example.chess.AppData;
import com.example.chess.Model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/")
public class ProfileController {
    private final AppData appData;
    public ProfileController(AppData appData) {
        this.appData = appData;
    }
    @GetMapping("/profile")
    public String Index(Model model) {
        User user = appData.Users.get(0);
        model.addAttribute("Username", user.getUsername());
        model.addAttribute("Data_inregistrarii", "" + user.getData_Inregistrarii().getDayOfMonth() + "." + user.getData_Inregistrarii().getMonthValue() + "." + user.getData_Inregistrarii().getYear());
        System.out.println("" + user.getData_Inregistrarii().getDayOfMonth() + user.getData_Inregistrarii().getMonthValue() + user.getData_Inregistrarii().getYear());
        model.addAttribute("Rating", user.getRating());
        model.addAttribute("Games_played", user.getMatches_Played());
        model.addAttribute("Win_rate", user.getWin_Rate().intValue());
        model.addAttribute("Best_rating", user.getMax_Rating());

        List<String> lista = user.getFriends_id();
        List<String> lista_sortata = new ArrayList<>();
        for (String id : lista){
            User aux = null;
            for (User j : appData.Users){
                if (j.getId().equals(id)) aux = j;
            }
            lista_sortata.add(aux.getUsername());
        }
        lista_sortata.sort(null);
        model.addAttribute("Friends", lista_sortata);
        return "profile";
    }
}
