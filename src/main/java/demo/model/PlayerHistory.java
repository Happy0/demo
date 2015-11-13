package demo.model;

public class PlayerHistory
{
    private int minutesPlayed;

    public int getMinutesPlayed() {
        return minutesPlayed;
    }

    public void setMinutesPlayed(int minutesPlayed) {
        this.minutesPlayed = minutesPlayed;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public Fixture getFixture()
    {
        return fixture;
    }

    public void setFixture(Fixture fixture)
    {
        this.fixture = fixture;
    }

    private Fixture fixture;
    private int totalScore;
    private double value;

}
