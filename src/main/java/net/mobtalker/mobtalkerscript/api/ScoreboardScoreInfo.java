package net.mobtalker.mobtalkerscript.api;

public class ScoreboardScoreInfo
{
    public final String Player;
    public final ScoreboardObjectiveInfo Objective;
    public final int Score;
    
    // ========================================
    
    public ScoreboardScoreInfo( String player, ScoreboardObjectiveInfo objective, int score )
    {
        Player = player;
        Objective = objective;
        Score = score;
    }
}
