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
            return (player.getMinutesPlayed() * player.getPointsPerGame() * 10) / player.getPrice() * player.getChanceOfPlaying();
        }
        else
        {
            PlayerHistory playerHistory = player.getHistoryToWeek(week);
            int chancePlaying;
            if (week == player.getMaxWeek())
            {
                chancePlaying = player.getChanceOfPlaying();
            }
            else
            {
                PlayerHistory futureHistory = player.getHistoryToWeek(week + 1);
                if (futureHistory.getMinutesPlayed() > 0)
                {
                    chancePlaying = 100;
                }
                else
                {
                    chancePlaying=0;
                }
            }

            return (playerHistory.getMinutesPlayed() * (playerHistory.getTotalScore() / week) * 10) / playerHistory
                    .getValue() * chancePlaying;
        }
    }
}
