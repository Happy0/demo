package demo;

import demo.model.Player;
import demo.model.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class TeamService
{
    @Autowired
    PlayerDataLoader playerDataLoader;

    private final static String[] teams = {"Arsenal", "Liverpool", "Chelsea", "Manchester United", "Manchester City"};
    private final static String[] forename = {"Bob", "John", "Steve", "Jim", "Frank"};
    private final static String[] surname = {"Jones", "Smith", "Brown", "Fred", "Moore"};

    private Random random = new Random();

    public Player getRandomPlayer(List<Player> players) {
        int index = random.nextInt(players.size());
        return players.get(index);
    }

    public Team getOptimalTeam() throws IOException {
        List<Player> getFullPlayerList = playerDataLoader.getFullPlayerList();

        List<Player> playerList = new ArrayList<>();

        List<Player> allKeepers = getFullPlayerList.stream()
                .filter(p -> p.getPosition() == 1).collect(Collectors.toList());

        Player randomKeeper = getRandomPlayer(allKeepers);
        randomKeeper.setOnBench(false);
        Player randomKeeperBench = getRandomPlayer(allKeepers);
        randomKeeperBench.setOnBench(true);

        // keeper
        playerList.add(randomKeeper);
        playerList.add(randomKeeperBench);

        List<Player> allDefenders = getFullPlayerList.stream()
                .filter(p -> p.getPosition() == 2).collect(Collectors.toList());

        // defender
        for (int i=0; i<3; i++) {
            Player randomPlayer = getRandomPlayer(allDefenders);
            randomPlayer.setOnBench(false);
            playerList.add(randomPlayer);
        }

        Player randomDefenderBenchOne = getRandomPlayer(allDefenders);
        randomDefenderBenchOne.setOnBench(true);
        playerList.add(randomDefenderBenchOne);
        Player randomDefenderBenchTwo = getRandomPlayer(allDefenders);
        randomDefenderBenchTwo.setOnBench(true);
        playerList.add(randomDefenderBenchTwo);

        List<Player> allMidfielders = getFullPlayerList.stream()
                .filter(p -> p.getPosition() == 3).collect(Collectors.toList());

        // midfield
        for (int i=0; i<=3; i++) {
            Player randomPlayer = getRandomPlayer(allMidfielders);
            randomPlayer.setOnBench(false);
            playerList.add(randomPlayer);
        }

        Player randomMidfielderBench = getRandomPlayer(allMidfielders);
        randomMidfielderBench.setOnBench(true);
        playerList.add(randomMidfielderBench);

        List<Player> allAttackers = getFullPlayerList.stream()
                .filter(p -> p.getPosition() == 4).collect(Collectors.toList());

        // attack
        for (int i=0; i<3; i++) {
            Player randomPlayer = getRandomPlayer(allAttackers);
            randomPlayer.setOnBench(false);
            playerList.add(randomPlayer);
        }

        List<Player> keepers = playerList.stream()
                .filter(p -> p.getPosition() == 1).collect(Collectors.toList());

        List<Player> defence = playerList.stream()
                .filter(p -> p.getPosition() == 2).collect(Collectors.toList());

        List<Player> midfield = playerList.stream()
                .filter(p -> p.getPosition() == 3).collect(Collectors.toList());

        List<Player> attack = playerList.stream()
                .filter(p -> p.getPosition() == 4).collect(Collectors.toList());

        String week = "20";
        return new Team(keepers, defence, midfield, attack, week);
    }
}