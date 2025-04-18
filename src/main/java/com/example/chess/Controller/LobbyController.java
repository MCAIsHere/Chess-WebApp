package com.example.chess.Controller;
import com.example.chess.AppData;
import com.example.chess.Model.Match;
import com.example.chess.Model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/")
public class LobbyController {
    private final AppData appData;

    public LobbyController(AppData appData) {
        this.appData = appData;
    }

    @GetMapping("/lobby")
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
        return "lobby";
    }

    @PostMapping("/create")
    public RedirectView create(@RequestParam("create_time") String create_time,
                               @RequestParam("create_type") String create_type,
                               @RequestParam(value = "friendly", defaultValue = "0") String friendly){
        boolean matchtype = friendly.equals("1");
        Integer time = Integer.parseInt(create_time);
        System.out.println("Users ID: ");
        System.out.println(appData.Users.get(0).getId());
        System.out.println(appData.Users.get(1).getId());
        System.out.println(appData.Users.get(2).getId());
        System.out.println("---------------------");
        Match new_match = new Match("a",appData.Users.get(0),appData.Users.get(1), time, create_type, matchtype);
        appData.Matches.add(new_match);
        return new RedirectView("/game/" + new_match.getId());
    }
}
