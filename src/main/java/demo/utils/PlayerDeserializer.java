package demo.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import demo.model.Club;
import demo.model.Fixture;
import demo.model.Player;
import demo.model.PlayerHistory;

import java.io.IOException;
import java.util.Map;

/**
 * Created by mtlgma on 12/11/2015.
 */
public class PlayerDeserializer extends JsonDeserializer<Player>
{

    @Override
    public Player deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException
    {
        Club club = new Club();
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);

        Player player = new Player();
        player.setClub(node.get("team_name").asText());
        player.setForename(node.get("first_name").asText());
        player.setSurname(node.get("second_name").asText());
        player.setMinutesPlayed(node.get("minutes").asInt());
        player.setOnBench(false);
        player.setPointsPerGame(node.get("points_per_game").asInt());
        player.setPrice(node.get("now_cost").asInt());
        player.setTotalScore(node.get("total_points").asInt());
        player.setPosition(node.get("element_type").asInt());

        String chance = node.get("chance_of_playing_next_round").asText();
        try
        {
            int chancePlaying =Integer.parseInt(chance);
            player.setChanceOfPlaying(chancePlaying);
        }
        catch (NumberFormatException ne)
        {
            player.setChanceOfPlaying(100);
        }

        JsonNode fixture_history = node.get("fixture_history");
        getPlayerFixtureHistory(fixture_history, player, club);

        JsonNode nextFixture = node.get("fixtures");
        player.setNextFixture(getNextFixture(nextFixture, player, club));

        return player;
    }

    private Fixture getNextFixture(JsonNode futureFixtures, Player player, Club club)
    {
        JsonNode allFixture = futureFixtures.get("all");

        JsonNode fixture_node = allFixture.get(0);

        String teamFixture = fixture_node.get(2).asText();

        return new Fixture(player.getClub(), teamFixture.substring(0, teamFixture.indexOf("(")-1).trim(), teamFixture.substring(teamFixture.indexOf("(")+1,teamFixture
                .indexOf("(")+2).equals("H"),12);

    }


    private void getPlayerFixtureHistory(JsonNode fixture_history, Player player, Club club)
    {
        JsonNode fixture_history_all = fixture_history.get("all");
        Map<Integer, PlayerHistory> map = player.getPlayerHistoryMap();

        for (int i = 0; i < fixture_history_all.size(); i++)
        {
            PlayerHistory playerHistory = new PlayerHistory();
            JsonNode fixture_node = fixture_history_all.get(i);

            if (fixture_node != null)
            {
                JsonNode weekNode = fixture_node.get(1);
                if (weekNode != null)
                {
                    int weekNumber = weekNode.asInt();

                    String teamFixture = fixture_node.get(2).asText();
                    playerHistory.setFixture(new Fixture(player.getClub(), club.getLongName(teamFixture.substring(0,
                            3)), teamFixture.substring(4,5).equals("H"),i));


                    JsonNode minsNode = fixture_node.get(3);
                    int minsPlayed = minsNode == null ? 0 : minsNode.asInt();

                    JsonNode priceNode = fixture_node.get(18);
                    double price = minsNode == null ? 0 : priceNode.asDouble();

                    JsonNode scoreNode = fixture_node.get(19);
                    int totalScore = scoreNode == null ? 0 : scoreNode.asInt();

                    playerHistory.setMinutesPlayed(minsPlayed);
                    playerHistory.setTotalScore(totalScore);
                    playerHistory.setValue(price);
                    map.put(weekNumber, playerHistory);
                }
            }
        }
    }
}
