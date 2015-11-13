package demo;

import demo.model.Player;
import demo.model.PlayerHistory;

public class SimplePlayerScoreCalculator implements PlayerScore
{
    @Override
    public double getPlayerScore(Player player, final int week)
    {
        if (week == 0)
        {
            return (player.getMinutesPlayed() * player.getPointsPerGame() * 10) / player.getPrice();
        }
        else
        {
            PlayerHistory playerHistory = player.getHistoryToWeek(week);
            return (playerHistory.getMinutesPlayed() * (playerHistory.getTotalScore()/ week) * 10) / playerHistory.getValue();
        }
    }
}
