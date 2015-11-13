package demo;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import demo.model.Player;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Component
public class PlayerDataLoader {

    ObjectMapper mapper = new ObjectMapper();

    private List<Player> fullPlayerList = null;

    public List<Player> loadPlayerData() throws IOException {

        List<Player> players = new ArrayList<>();

        InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream("players.json");
        JsonNode jsonNode = mapper.readTree(stream);

        JsonNode  playerNodes = jsonNode.get("players");

        for (JsonNode playerNode : playerNodes)
        {
            Player player = mapper.treeToValue(playerNode, Player.class);
            players.add(player);
        }

        return players;
    }

    public List<Player> getFullPlayerList() throws IOException {
        if (fullPlayerList == null) {
            fullPlayerList = loadPlayerData();
        }
        return fullPlayerList;
    }
}
