package net.mobtalker.mobtalkerscript.api.library;

import net.mobtalker.mobtalkerscript.v2.value.*;
import net.mobtalker.mobtalkerscript.v2.value.userdata.MtsNativeFunction;

public interface IScoreboardLib
{
    @MtsNativeFunction
    void setDisplayedObjective( MtsValue argSlotName, MtsValue argObjectiveName );
    
    @MtsNativeFunction
    void clearDisplayedObjective( MtsValue argSlotName );
    
    // ========================================
    
    @MtsNativeFunction
    MtsTable getObjectives();
    
    @MtsNativeFunction
    MtsBoolean hasObjective( MtsValue argName );
    
    @MtsNativeFunction
    void addObjective( MtsValue argName, MtsValue argCriteraName, MtsValue argDisplayName );
    
    @MtsNativeFunction
    void removeObjective( MtsValue argName );
    
    // ========================================
    
    @MtsNativeFunction
    MtsTable getPlayers();
    
    // ========================================
    
    @MtsNativeFunction
    MtsTable getScores( MtsValue argPlayerName );
    
    @MtsNativeFunction
    MtsNumber getScore( MtsValue argPlayerName, MtsValue argObjectiveName );
    
    @MtsNativeFunction
    void setScore( MtsValue argPlayerName, MtsValue argObjectiveName, MtsValue argValue );
    
    @MtsNativeFunction
    MtsNumber increaseScore( MtsValue argPlayerName, MtsValue argObjectiveName, MtsValue argValue );
    
    @MtsNativeFunction
    MtsNumber decreaseScore( MtsValue argPlayerName, MtsValue argObjectiveName, MtsValue argValue );
    
    // ========================================
    
    @MtsNativeFunction
    MtsTable getTeams();
    
    @MtsNativeFunction
    MtsTable getTeamMembers( MtsValue argTeamName );
    
    @MtsNativeFunction
    void addTeam( MtsValue argTeamName, MtsValue argDisplayName );
    
    @MtsNativeFunction
    void removeTeam( MtsValue argTeamName );
    
    @MtsNativeFunction
    void addTeamMember( MtsValue argTeamName, MtsValue argPlayerName );
    
    @MtsNativeFunction
    void removeTeamMember( MtsValue argTeamName, MtsValue argPlayerName );
}