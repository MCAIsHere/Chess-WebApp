package com.example.chess.Controller;
import com.example.chess.AppData;
import com.example.chess.AuditService;
import com.example.chess.DatabaseService;
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
import java.util.UUID;

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
        return "lobby";
    }

    @PostMapping("/create")
    public RedirectView create(@RequestParam("create_time") String create_time,
                               @RequestParam("create_type") String create_type){
        Integer time = Integer.parseInt(create_time);


        String uniqueID = UUID.randomUUID().toString();
        Match new_match = new Match(uniqueID,appData.Users.get(0).getId(),appData.Users.get(1).getId(), time, create_type);
        appData.Matches.add(new_match);

        // CREATE IN DB
        DatabaseService.getInstance().Create(new_match);

        return new RedirectView("/game/" + new_match.getId());
    }

    @PostMapping("/find")
    public RedirectView find(@RequestParam("matchId") String matchId){
        Match match = appData.Matches.stream()
                .filter(m -> m.getId().equals(matchId))
                .findFirst()
                .orElse(null);

        if (match == null) return null;
        else{
            // CSV ACTION
            AuditService.getInstance().logAction("Match " + matchId + " read.");

            return new RedirectView("/game/" + matchId);
        }
    }
}
