package demo.model;

/**
 * @author Stephen Lavery (stephen.lavery@sas.com)
 */
public class Fixture
{
    private final String playerTeam;
    private final String oppTeam;
    private final boolean isHome;

    public int getCaptainScore()
    {
        return captainScore;
    }

    private int captainScore;

    public Fixture(String playerTeam, String oppTeam, boolean isHome)
    {
        this.playerTeam = playerTeam;
        this.oppTeam = oppTeam;
        this.isHome = isHome;
        captainScore = calculateCaptainScore();
    }

    private int calculateCaptainScore()
    {
        return 100;
    }


}
