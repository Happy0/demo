package demo.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Team
{

    public List<Player> keepers;
    public List<Player> defence;
    public List<Player> midfield;
    public List<Player> attack;
    public List<Player> subs;
    public String week;
    public int weekScore;

    public String getFormation() {
        return formation;
    }

    public void setFormation(String formation) {
        this.formation = formation;
    }

    public String formation;

    public int getWeekScore() {

        int teamScore = 0;
        int the_week = Integer.parseInt(this.week);
        for (Player p : allPlayers())
        {
            PlayerHistory ph = p.getHistorySingleWeek(the_week + 1);
            p.setWeekScore(ph.getTotalScore());
            if (!p.isOnBench()) {
                teamScore += ph.getTotalScore();
            }
        }

        return teamScore;
    }

    public int getAlgorithmScore() {
        int teamScore = 0;
        for (Player p : allPlayers())
        {
            if (!p.isOnBench()) {
                teamScore += p.getAlgorithmScore();
            }
        }
        return teamScore;
    }

    public void setWeekScore(int weekScore) {
        this.weekScore = weekScore;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public double getTotalValue()
    {
        return totalValue;
    }

    public void setTotalValue(double totalValue)
    {
        this.totalValue = totalValue;
    }

    public double totalValue;

    public Team()
    {
        this.keepers = new ArrayList<>();
        this.defence = new ArrayList<>();
        this.midfield = new ArrayList<>();
        this.attack = new ArrayList<>();
        this.subs = new ArrayList<>();
        totalValue = getTotalCost();
    }

    public Team(List<Player> keepers, List<Player> defence, List<Player> midfield, List<Player> attack, String week)
    {
        this.defence = defence;
        this.attack = attack;
        this.midfield = midfield;
        this.keepers = keepers;
        totalValue = getTotalCost();
        this.week = week;
        weekScore = getWeekScore();
    }

    public List<Player> getKeepers() {
        return keepers;
    }

    public void setKeepers(List<Player> keepers) {
        this.keepers = keepers;
    }

    public List<Player> getDefence() {
        return defence;
    }

    public void setDefence(List<Player> defence) {
        this.defence = defence;
    }

    public List<Player> getMidfield() {
        return midfield;
    }

    public void setMidfield(List<Player> midfield) {
        this.midfield = midfield;
    }

    public List<Player> getAttack() {
        return attack;
    }

    public void setSubs() {
        this.subs = getSubs();
    }

    public void setAttack(List<Player> attack) {
        this.attack = attack;
    }
    public List<Player> getSubs()
    {
        return allPlayers().stream()
                .filter(p -> p.isOnBench()).collect(Collectors.toList());
    }

    public List<Player> allPlayers()
    {
        List<Player> allPlayers = new ArrayList<>();
        allPlayers.addAll(keepers);
        allPlayers.addAll(defence);
        allPlayers.addAll(midfield);
        allPlayers.addAll(attack);

        return allPlayers;
    }

    public boolean clubCountSatisfied()
    {
        Map<String,Integer> clubCount = new HashMap<>(20);
        List<Player> players = allPlayers();
        boolean satisfied = true;

        for (Player player : players)
        {
            if(clubCount.containsKey(player.getClub()))
            {
                int newClubCount = clubCount.get(player.getClub()) + 1;
                if (newClubCount > 3)
                {
                    satisfied = false;
                    break;
                }
                else
                {
                    clubCount.put(player.getClub(), newClubCount);
                }
            }
            else
            {
                clubCount.put(player.getClub(), 1);
            }
        }
        return satisfied;
    }

    public double getTotalCost()
    {
        int totalCost = 0;
        List<Player> players = allPlayers();

        for (Player player : players)
        {
            totalCost += player.getPrice();
        }

        return totalCost / 10.0;
    }

    public boolean costSatisfied()
    {
        int totalCost = 0;
        List<Player> players = allPlayers();

        for (Player player : players)
        {
            totalCost += player.getPrice();
        }

        return totalCost <= 1000;

    }

    public boolean meetsRules()
    {
        return getKeepers().size() == 2 && getDefence().size() == 5 &&
                getMidfield().size() == 5 && getAttack().size() ==3 &&
                clubCountSatisfied() && costSatisfied();
    }
}
