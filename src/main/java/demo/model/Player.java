package demo.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import demo.utils.PlayerDeserializer;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.SynchronousQueue;

/**
 * Created by mtldds on 12/11/2015.
 */
@JsonDeserialize(using = PlayerDeserializer.class)
public class Player implements Comparable<Player> {

    private String surname;
    private String forename;
    private String club;
    private int position;
    private int minutesPlayed;
    private int pointsPerGame;
    private int price;
    private int totalScore;
    private int chanceOfPlaying;
    private int weekScore;
    private double algorithmScore;
    private boolean onBench;
    private Map<Integer, PlayerHistory> playerHistoryMap = new HashMap();
    private boolean captain;

    public Fixture getNextFixture()
    {
        return nextFixture;
    }

    public void setNextFixture(Fixture nextFixture)
    {
        this.nextFixture = nextFixture;
    }

    private Fixture nextFixture;

    public Player() {
        this.captain = false;
    }

    //Copy constructor
    public Player(Player player) {
        this.surname = player.surname;
        this.forename = player.forename;
        this.club = player.club;
        this.position = player.position;
        this.minutesPlayed = player.minutesPlayed;
        this.pointsPerGame = player.pointsPerGame;
        this.price = player.price;
        this.totalScore = player.totalScore;
        this.chanceOfPlaying = player.chanceOfPlaying;
        this.weekScore = player.weekScore;
        this.algorithmScore = player.algorithmScore;
        this.onBench = player.onBench;
        this.playerHistoryMap = player.playerHistoryMap;
        this.captain = player.captain;
        this.nextFixture = player.getNextFixture();
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getForename() {
        return forename;
    }

    public void setForename(String forename) {
        this.forename = forename;
    }

    public String getClub() {
        return club;
    }

    public void setClub(String club) {
        this.club = club;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getMinutesPlayed() {
        return minutesPlayed;
    }

    public void setMinutesPlayed(int minutesPlayed) {
        this.minutesPlayed = minutesPlayed;
    }

    public int getPointsPerGame() {
        return pointsPerGame;
    }

    public void setPointsPerGame(int pointsPerGame) {
        this.pointsPerGame = pointsPerGame;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public double getAlgorithmScore() {
        return algorithmScore;
    }

    public void setAlgorithmScore(double algorithmScore) {
        this.algorithmScore = algorithmScore;
    }

    public int getChanceOfPlaying() {
        return chanceOfPlaying;
    }

    public void setChanceOfPlaying(int chanceOfPlaying) {
        this.chanceOfPlaying = chanceOfPlaying;
    }

    public int getWeekScore() {
        return weekScore;
    }

    public void setWeekScore(int weekScore) {
        this.weekScore = weekScore;
    }

    public boolean isOnBench() {
        return onBench;
    }

    public void setOnBench(boolean onBench) {
        this.onBench = onBench;
    }

    public boolean isCaptain() {
        return captain;
    }

    public void setCaptain(boolean captain) {
        this.captain = captain;
    }

    @Override
    public int compareTo(Player p) {
        if (this.getAlgorithmScore() < p.algorithmScore) {
            return 1;
        } else if (this.getAlgorithmScore() > p.algorithmScore) {
            return -1;
        } else if (this.totalScore < p.totalScore) {
            return 1;
        } else {
            return -1;
        }
    }

    public String toString() {
        return this.getForename() + " " + this.getSurname() + "(" + this.getAlgorithmScore() + ")";
    }

    public Map<Integer, PlayerHistory> getPlayerHistoryMap() {
        return playerHistoryMap;
    }

    public int getWeekScore(int weekNo) {
        int week = (weekNo > playerHistoryMap.size()) ? playerHistoryMap.size() : weekNo;
        return playerHistoryMap.get(week).getTotalScore();
    }

    public PlayerHistory getHistorySingleWeek(int weekNo) {
        PlayerHistory playerHistoryToWeek = new PlayerHistory();


        if (playerHistoryMap.containsKey(weekNo)) {
            return playerHistoryMap.get(weekNo);
        } else {
            return playerHistoryToWeek;
        }

    }

    public int getMaxWeek() {
        int maxWeek = 1;
        for (int i = 1; i < 41; i++) {
            if (playerHistoryMap.containsKey(i)) {
                maxWeek = i;
            } else {
                break;
            }
        }
        return maxWeek;
    }

    public PlayerHistory getHistoryToWeek(int weekNo) {

        PlayerHistory playerHistoryToWeek = new PlayerHistory();

        int r_minsPlayed = 0;
        double r_maxPrice = 0;
        int r_totalScore = 0;

        int weekToStopAt = (weekNo > playerHistoryMap.size()) ? playerHistoryMap.size() : weekNo;

        for (int i = 1; i < weekToStopAt; i++) {
            if (playerHistoryMap.containsKey(i)) {
                PlayerHistory playerHistory = playerHistoryMap.get(i);

                r_minsPlayed += playerHistory.getMinutesPlayed();
                r_totalScore += playerHistory.getTotalScore();

                if (r_maxPrice < playerHistory.getValue()) {
                    r_maxPrice = playerHistory.getValue();
                }
            }

        }

        playerHistoryToWeek.setMinutesPlayed(r_minsPlayed);
        playerHistoryToWeek.setTotalScore(r_totalScore);
        playerHistoryToWeek.setValue(r_maxPrice);

        return playerHistoryToWeek;
    }


}
