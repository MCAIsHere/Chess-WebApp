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
        List<User> lista = user.getFriends();
        List<String> lista_sortata = new ArrayList<>();
        for (User i : lista){
            lista_sortata.add(i.getUsername());
        }
        lista_sortata.sort(null);
        model.addAttribute("Friends", lista_sortata);
        return "profile";
    }
}
