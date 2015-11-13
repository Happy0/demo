package demo;

import demo.model.Formation;
import demo.model.Player;
import demo.model.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
public class TeamController {

    private TeamService teamService;

    @Autowired
    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @RequestMapping(value = "/optimal/{week}")
    @ResponseBody
    public Team getOptimalTeam(@PathVariable(value = "week") String week,
                               @RequestParam(value = "formation", required = false) String formation) throws IOException {
        TeamCalculator teamCalculator = new TeamCalculator();
        Optional<String> formationOpt = Optional.ofNullable(formation);
        Optional<Formation> optionalFormation = formationOpt.map(opt -> Formation.fromString(opt));

        // Defaults to the best formation if the client did not supply a formation
        return teamCalculator.generateBestTeam(week, optionalFormation);
    }

    @RequestMapping(value = "/players")
    public List<Player> allPlayers() throws IOException {
        return teamService.playerDataLoader.getFullPlayerList();
    }
}
