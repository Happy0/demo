package demo;

import demo.model.Formation;
import demo.model.Player;
import demo.model.Team;
import demo.utils.SlidingWindowIterator;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class TeamCalculator
{
    private final PlayerDataLoader playerDataLoader;
    private final SimplePlayerScoreCalculator scoreCalculator;


    public TeamCalculator()
    {
        playerDataLoader = new PlayerDataLoader();
        scoreCalculator = new SimplePlayerScoreCalculator();
    }

    /**
     * Return the algorithm's guess at the best team for a given week.
     * @param week The fixture week
     * @param formation An optional formation. If none is given, we return the formation which gives the best score
     * @return
     * @throws IOException
     */
    public Team generateBestTeam(String week, Optional<Formation> formation) throws IOException {
        //Score players and sort based on score
        List<Player> players =  playerDataLoader.getFullPlayerList();
        int maxWeek = players.get(1).getMaxWeek();
        int currentWeek = Integer.parseInt(week);
        if (currentWeek>maxWeek)
        {
            currentWeek =maxWeek;
        }
        else
        {

        }
        for (Player player: players)
        {
            player.setAlgorithmScore(scoreCalculator.getPlayerScore(player, currentWeek));
        }

        List<Player> keepers = getPlayersOfPosition(players, 1);
        List<Player> defenders = getPlayersOfPosition(players, 2);
        List<Player> midFielders = getPlayersOfPosition(players, 3);
        List<Player> attackers = getPlayersOfPosition(players, 4);

        // Sort each bucket in best ranked player to least best ranked feature
        Arrays.asList(keepers, defenders, midFielders, attackers)
                .stream()
                .forEach(Collections::sort);

        return buildTeam(keepers, defenders, midFielders, attackers, Integer.toString(currentWeek));
    }

    private Team buildTeam(List<Player> keepers, List<Player> defenders, List<Player> midFielders, List<Player> attackers, String week) {
        int numKeepers = 2; // 1 sub
        int numDefenders = 5; // 2 sub
        int numMidfielders = 5; // 1 sub
        int numStrikers = 3;

        int keeperSubs = 1;
        int defenderSubs = 2;
        int midFielderSubs = 1;

        Iterator<List<Player>> keepersWindow = new SlidingWindowIterator<>(keepers, numKeepers);
        Iterator<List<Player>> defendersWindow = new SlidingWindowIterator<>(defenders, numDefenders);
        Iterator<List<Player>> midFieldersWindow = new SlidingWindowIterator<>(midFielders, numMidfielders);
        Iterator<List<Player>> strikersWindow = new SlidingWindowIterator<>(attackers, numStrikers);

        List<Player> candidateKeepers = keepersWindow.next();
        List<Player> candidateDefenders = defendersWindow.next();
        List<Player> candidateMidFielders = midFieldersWindow.next();
        List<Player> candidateStrikers = strikersWindow.next();

        boolean foundTeam = false;
        Team team = new Team(candidateKeepers, candidateDefenders, candidateMidFielders, candidateStrikers, week);

        int dropFrom = 0;
        while (!foundTeam)
        {
            switch (dropFrom) {
                case 0:
                    candidateKeepers = keepersWindow.next();
                    break;
                case 1:
                    candidateDefenders = defendersWindow.next();
                    break;
                case 2:
                    candidateMidFielders = midFieldersWindow.next();
                    break;
                case 3:
                    candidateStrikers = strikersWindow.next();
                    break;
            }

            if (dropFrom == 3)
            {
                dropFrom = 0;
            }
            else
            {
                dropFrom++;
            }

            team = new Team(candidateKeepers, candidateDefenders, candidateMidFielders, candidateStrikers, week);
            foundTeam = team.clubCountSatisfied() && team.costSatisfied();
        }

        setSubs(team.getKeepers(), keeperSubs);
        setSubs(team.getDefence(), defenderSubs);
        setSubs(team.getMidfield(), midFielderSubs);

        team.setSubs();

        // TODO: hardcoded for now
        team.setFormation("343");

        return team;
    }

    private void setSubs(List<Player> players, int numSubs)
    {
        List<Player> subs = players.subList(players.size() - numSubs - 1, players.size() - 1);
        subs.forEach(player -> player.setOnBench(true));

    }

    public boolean tryTeam(List<Player> keepers, List<Player> defenders, List<Player> midFielders, List<Player> attackers, String week)
    {
        Team teamAttempt = new Team(keepers, defenders, midFielders, attackers, week);
        return teamAttempt.clubCountSatisfied() && teamAttempt.costSatisfied();
    }

    private List<Player> getPlayersOfPosition(List<Player> players, int position)
    {
        return players.stream().filter(player -> player.getPosition() == position).collect(Collectors.toList());
    }

}
