package demo;

import demo.model.Fixture;
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

        Team bestTeam = buildTeam(keepers, defenders, midFielders, attackers, Integer.toString(currentWeek), formation);

        setCaptain(bestTeam, currentWeek);

        return bestTeam;

    }

    private void setCaptain(Team team, int currentWeek)
    {
        Player captain = team.allPlayers().get(0);
        double bestScore = 0;
        for (Player player: team.allPlayers()) {

            if (!player.isOnBench()) {
                Fixture captainFixture;
                if (currentWeek == player.getMaxWeek())
                {
                    captainFixture = player.getNextFixture();
                }
                else
                {
                    captainFixture = player.getPlayerHistoryMap().get(currentWeek +1).getFixture();
                }
                if (captainFixture.getCaptainScore() > bestScore) {
                    captain = player;
                    bestScore = captainFixture.getCaptainScore();
                }
            }
        }
        captain.setCaptain(true);
    }

    private Team buildTeam(List<Player> keepers, List<Player> defenders, List<Player> midFielders, List<Player> attackers, String week,
                           Optional<Formation> formation) {

        EnumMap<Formation, Team> formationTeamEnumMap = buildOptimalTeamCompliments(keepers, defenders, midFielders, attackers, week);

        // Default to the best formation
        return formation.map(form -> formationTeamEnumMap.get(form)).orElse(bestTeamFormation(formationTeamEnumMap.values()));
    }

    private Team bestTeamFormation(Collection<Team> teams)
    {
        Iterator<Team> teamIterator = teams.iterator();
        Team current = teamIterator.next();
        int currentScore = current.getWeekScore();

        while (teamIterator.hasNext())
        {
            Team compareTeam = teamIterator.next();

            if (compareTeam.getWeekScore() > currentScore)
            {
                current = compareTeam;
                currentScore = compareTeam.getWeekScore();
            }
        }

        return current;
    }

    private EnumMap<Formation, Team> buildOptimalTeamCompliments(List<Player> keepers, List<Player> defenders,
                                                                 List<Player> midFielders,
                                                                 List<Player> attackers,
                                                                 String week)
    {

        EnumMap<Formation, Team> teams = new EnumMap(Formation.class);
        for (Formation formation : Formation.values())
        {
            List<Player> keepersClone = keepers.stream().map(ply -> new Player(ply)).collect(Collectors.toList());
            List<Player> defendersClone = defenders.stream().map(ply -> new Player(ply)).collect(Collectors.toList());
            List<Player> midFieldersClone = midFielders.stream().map(ply -> new Player(ply)).collect(Collectors.toList());
            List<Player> attackersClone = attackers.stream().map(ply -> new Player(ply)).collect(Collectors.toList());

            int numKeepers = formation.getNumKeepers(); // 1 sub
            int numDefenders = formation.getNumDefenders(); // 2 sub
            int numMidfielders = formation.getNumMidfielders(); // 1 sub
            int numStrikers = formation.getNumAttackers();

            int keeperSubs = 1;
            int defenderSubs = formation.getDefenderSubs();
            int midFielderSubs = formation.getMidfielderSubs();
            int strikerSubs = formation.getAttackerSubs();

            Iterator<List<Player>> keepersWindow = new SlidingWindowIterator<>(keepersClone, numKeepers);
            Iterator<List<Player>> defendersWindow = new SlidingWindowIterator<>(defendersClone, numDefenders);
            Iterator<List<Player>> midFieldersWindow = new SlidingWindowIterator<>(midFieldersClone, numMidfielders);
            Iterator<List<Player>> strikersWindow = new SlidingWindowIterator<>(attackersClone, numStrikers);

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
            setSubs(team.getAttack(), strikerSubs);

            team.setSubs();

            team.setFormation(formation.toString());

            teams.put(formation, team);
        }

        return teams;
    }

    private void setSubs(List<Player> players, int numSubs)
    {
        if (numSubs == 0)
        {
            return;
        }

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
