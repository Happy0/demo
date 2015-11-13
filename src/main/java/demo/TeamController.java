package demo;

import demo.model.Player;
import demo.model.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
public class TeamController {

    private TeamService teamService;

    @Autowired
    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @RequestMapping(value = "/optimal/{week}")
    @ResponseBody
    public Team getOptimalTeam(@PathVariable(value = "week") String week) throws IOException {
        TeamCalculator teamCalculator = new TeamCalculator();
        return teamCalculator.generateBestTeam(week);
    }

    @RequestMapping(value = "/players")
    public List<Player> allPlayers() throws IOException {
        return teamService.playerDataLoader.getFullPlayerList();
    }
}
