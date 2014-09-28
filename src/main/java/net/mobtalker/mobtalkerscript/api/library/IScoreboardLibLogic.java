package net.mobtalker.mobtalkerscript.api.library;

import java.util.List;

import net.mobtalker.mobtalkerscript.api.*;

public interface IScoreboardLibLogic
{
    boolean checkObjectiveDisplaySlot( String slot );
    
    void setDisplayedObjective( String slot, String objective );
    
    void clearDisplayedObjective( String slot );
    
    // ========================================
    
    List<ScoreboardObjectiveInfo> getObjectives();
    
    boolean hasObjective( String name );
    
    boolean hasCriteria( String criteria );
    
    void addObjective( String name, String criteria, String displayName );
    
    void removeObjective( String name );
    
    // ========================================
    
    List<String> getPlayerNames();
    
    List<ScoreboardScoreInfo> getPlayerScores( String playerName );
    
    ScoreboardScoreInfo getPlayerScore( String player, String objective );
    
    void setPlayerScore( String player, String objective, int value );
    
    int increasePlayerScore( String player, String objective, int value );
    
    int decreasePlayerScore( String player, String objective, int value );
    
    // ========================================
    
    List<ScoreboardTeamInfo> getTeams();
    
    boolean hasTeam( String teamName );
    
    List<String> getTeamMembers( String teamName );
    
    void addTeam( String name, String displayName );
    
    void removeTeam( String name );
    
    void addTeamMember( String teamName, String player );
    
    void removeTeamMember( String teamName, String player );
}
