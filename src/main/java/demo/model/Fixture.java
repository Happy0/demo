package demo.model;

/**
 * @author Stephen Lavery (stephen.lavery@sas.com)
 */
public class Fixture
{
    private final String playerTeam;
    private final String oppTeam;
    private final boolean isHome;

    public double getCaptainScore()
    {
        return captainScore;
    }

    private double captainScore;

    public Fixture(String playerTeam, String oppTeam, boolean isHome, int weekNo)
    {
        this.playerTeam = playerTeam;
        this.oppTeam = oppTeam;
        this.isHome = isHome;
        captainScore = calculateCaptainScore(weekNo);
    }

    private double calculateCaptainScore(int weekNo)
    {
        Club club = new Club();
        double oppRank = club.getRank(oppTeam,weekNo);
        if (isHome) { oppRank = oppRank * 1.5; }
        return oppRank - club.getRank(playerTeam, weekNo);
    }


}
