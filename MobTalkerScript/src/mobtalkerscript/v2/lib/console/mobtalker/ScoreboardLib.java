package mobtalkerscript.v2.lib.console.mobtalker;

import static mobtalkerscript.util.MtsCheck.*;
import static mobtalkerscript.v2.value.MtsValue.*;

import java.util.*;
import java.util.Map.Entry;

import mobtalkerscript.v2.*;
import mobtalkerscript.v2.lib.console.mobtalker.scoreboard.*;
import mobtalkerscript.v2.lib.console.mobtalker.scoreboard.DummyScoreboard.DummyObjective;
import mobtalkerscript.v2.lib.console.mobtalker.scoreboard.DummyScoreboard.DummyScore;
import mobtalkerscript.v2.lib.console.mobtalker.scoreboard.DummyScoreboard.DummyTeam;
import mobtalkerscript.v2.lib.console.mobtalker.scoreboard.DummyScoreboard.IDummyCriteria;
import mobtalkerscript.v2.value.*;
import mobtalkerscript.v2.value.userdata.*;

import com.google.common.base.*;

public class ScoreboardLib
{
    private final DummyScoreboard _scoreboard;
    
    // ========================================
    
    public ScoreboardLib()
    {
        _scoreboard = new DummyScoreboard();
    }
    
    // ========================================
    
    private DummyScoreboard getScoreboard()
    {
        return _scoreboard;
    }
    
    private DummyObjective getObjective( String name, boolean throwOnReadonly )
    {
        DummyObjective objective = getScoreboard().getObjective( name );
        
        if ( objective == null )
            throw new BadArgumentException( "unknown objective '%s'", name );
        else if ( throwOnReadonly && objective.getCriteria().isReadOnly() )
            throw new BadArgumentException( "objective '%s' is read-only", name );
        else
            return objective;
    }
    
    // ========================================
    // Objectives
    
    @MtsNativeFunction
    public void setDisplayedObjective( MtsValue argSlotName, MtsValue argObjectiveName )
    {
        String slotName = checkString( argSlotName, 0 );
        
        DummyScoreboard scoreboard = getScoreboard();
        int slot = DummyScoreboard.getObjectiveDisplaySlotNumber( slotName );
        
        if ( slot < 0 )
            throw new BadArgumentException( 0, "unknown display slot '%s'", slotName );
        
        String objectiveName = checkString( argObjectiveName, 1 );
        DummyObjective objective = getObjective( objectiveName, false );
        
        scoreboard.setDisplayedObjective( slot, objective );
    }
    
    @MtsNativeFunction
    public void clearDisplayedObjective( MtsValue argSlotName )
    {
        String slotName = checkString( argSlotName, 0 );
        
        DummyScoreboard scoreboard = getScoreboard();
        int slot = DummyScoreboard.getObjectiveDisplaySlotNumber( slotName );
        
        if ( slot < 0 )
            throw new BadArgumentException( 0, "unknown display slot '%s'", slotName );
        
        scoreboard.setDisplayedObjective( slot, null );
    }
    
    @MtsNativeFunction
    public MtsTable getObjectives()
    {
        Collection<DummyObjective> objectives = getScoreboard().getScoreObjectives();
        
        MtsTable result = new MtsTable( 0, objectives.size() );
        for ( DummyObjective objective : objectives )
        {
            String name = objective.getName();
            String criteria = objective.getCriteria().getName();
            
            result.set( valueOf( name ), valueOf( criteria ) );
        }
        
        return result;
    }
    
    @MtsNativeFunction
    public MtsBoolean hasObjective( MtsValue argName )
    {
        String name = checkString( argName, 0 );
        return valueOf( getScoreboard().getObjective( name ) != null );
    }
    
    @MtsNativeFunction
    public void addObjective( MtsValue argName, MtsValue argCriteraName, MtsValue argDisplayName )
    {
        String name = checkString( argName, 0 );
        String criteriaName = checkString( argCriteraName, 1 );
        
        DummyScoreboard scoreboard = getScoreboard();
        
        if ( Strings.isNullOrEmpty( name ) )
            throw new BadArgumentException( "name is empty" );
        if ( name.length() > 16 )
            throw new BadArgumentException( "name must be no longer than 16 characters" );
        if ( scoreboard.getObjective( name ) != null )
            throw new BadArgumentException( "objective '%s' already exists", name );
        
        IDummyCriteria criteria = IDummyCriteria.CRITERIA.get( criteriaName );
        if ( criteria == null )
            throw new BadArgumentException( "unknown criteria '%s'", criteriaName );
        
        String displayName = checkString( argDisplayName, 2, null );
        if ( !Strings.isNullOrEmpty( displayName ) )
        {
            if ( displayName.length() > 32 )
                throw new BadArgumentException( "display name must be no longer than 32 characters" );
            
            scoreboard.addObjective( name, criteria ).setDisplayName( displayName );
        }
        else
        {
            scoreboard.addObjective( name, criteria );
        }
    }
    
    @MtsNativeFunction
    public void removeObjective( MtsValue argName )
    {
        String name = checkString( argName, 0 );
        
        DummyScoreboard scoreboard = getScoreboard();
        
        DummyObjective objective = getObjective( name, false );
        if ( objective == null )
            throw new BadArgumentException( "unknown objective '%s'", name );
        
        scoreboard.removeObjective( objective );
    }
    
    // ========================================
    // Players
    
    @MtsNativeFunction
    public MtsTable getPlayers()
    {
        Collection<String> playerNames = getScoreboard().getPlayerNames();
        
        MtsTable result = new MtsTable( playerNames.size(), 0 );
        for ( String name : playerNames )
        {
            result.add( valueOf( name ) );
        }
        
        return result;
    }
    
    @MtsNativeFunction
    public MtsTable getScores( MtsValue argPlayerName )
    {
        String playerName = checkString( argPlayerName, 0 );
        
        Map<DummyObjective, DummyScore> scores = getScoreboard().getPlayerScores( playerName );
        
        MtsTable result = new MtsTable( 0, scores.size() );
        for ( Entry<DummyObjective, DummyScore> entry : scores.entrySet() )
        {
            DummyObjective objective = entry.getKey();
            DummyScore score = entry.getValue();
            
            result.set( valueOf( objective.getName() ), valueOf( score.getScorePoints() ) );
        }
        
        return result;
    }
    
    private DummyScore getScoreForPlayer( String playerName, String objectiveName )
    {
        DummyScoreboard scoreboard = getScoreboard();
        
        DummyObjective objective = getObjective( objectiveName, false );
        if ( objective == null )
            throw new BadArgumentException( "unknown objective '%s'", objectiveName );
        
        DummyScore score = scoreboard.getPlayerScore( playerName, objective );
        return score;
    }
    
    @MtsNativeFunction
    public MtsNumber getScore( MtsValue argPlayerName, MtsValue argObjectiveName )
    {
        String playerName = checkString( argPlayerName, 0 );
        String objectiveName = checkString( argObjectiveName, 1 );
        
        DummyScore score = getScoreForPlayer( playerName, objectiveName );
        return valueOf( score.getScorePoints() );
    }
    
    @MtsNativeFunction
    public void setScore( MtsValue argPlayerName, MtsValue argObjectiveName, MtsValue argValue )
    {
        String playerName = checkString( argPlayerName, 0 );
        String objectiveName = checkString( argObjectiveName, 1 );
        int value = checkInteger( argValue, 2 );
        
        DummyScore score = getScoreForPlayer( playerName, objectiveName );
        score.setScorePoints( value );
    }
    
    @MtsNativeFunction
    public MtsNumber incrementScore( MtsValue argPlayerName, MtsValue argObjectiveName, MtsValue argValue )
    {
        String playerName = checkString( argPlayerName, 0 );
        String objectiveName = checkString( argObjectiveName, 1 );
        int value = checkIntegerWithMinimum( argValue, 2, 0 );
        
        DummyScore score = getScoreForPlayer( playerName, objectiveName );
        score.setScorePoints( score.getScorePoints() + value );
        
        return valueOf( score.getScorePoints() );
    }
    
    @MtsNativeFunction
    public MtsNumber decrementScore( MtsValue argPlayerName, MtsValue argObjectiveName, MtsValue argValue )
    {
        String playerName = checkString( argPlayerName, 0 );
        String objectiveName = checkString( argObjectiveName, 1 );
        int value = checkIntegerWithMinimum( argValue, 2, 0 );
        
        DummyScore score = getScoreForPlayer( playerName, objectiveName );
        score.setScorePoints( score.getScorePoints() - value );
        
        return valueOf( score.getScorePoints() );
    }
    
    // ========================================
    // Teams
    
    @MtsNativeFunction
    public MtsTable getTeams()
    {
        Collection<DummyTeam> teams = getScoreboard().getTeams();
        
        MtsTable result = new MtsTable( 0, teams.size() );
        
        for ( DummyTeam team : teams )
        {
            String name = team.getName();
            String color = team.getColor();
            boolean friendlyFire = team.isFriendlyFireEnabled();
            boolean seeFriendlyInvisibles = team.canSeeFriendlyInvisibles();
            
            MtsTable options = new MtsTable( 0, 0 );
            options.set( "color", valueOf( color ) );
            options.set( "friendlyfire", valueOf( friendlyFire ) );
            options.set( "seeFriendlyInvisibles", valueOf( seeFriendlyInvisibles ) );
            
            result.set( name, options );
        }
        
        return result;
    }
    
    @MtsNativeFunction
    public MtsTable getTeamMembers( MtsValue argTeamName )
    {
        String teamName = checkString( argTeamName, 0 );
        
        DummyTeam team = getScoreboard().getTeam( teamName );
        if ( team == null )
            throw new BadArgumentException( "unknown team '%s'", teamName );
        
        Collection<String> members = team.getMembershipCollection();
        
        MtsTable result = new MtsTable( members.size(), 0 );
        for ( String member : members )
        {
            result.add( valueOf( member ) );
        }
        
        return result;
    }
    
    @MtsNativeFunction
    public void addTeam( MtsValue argTeamName, MtsValue argDisplayName )
    {
        String teamName = checkString( argTeamName, 0 );
        String displayName = checkString( argDisplayName, 1, null );
        
        DummyScoreboard scoreboard = getScoreboard();
        
        if ( scoreboard.getTeam( teamName ) != null )
            throw new BadArgumentException( "team '%s' already exists", teamName );
        if ( Strings.isNullOrEmpty( teamName ) )
            throw new BadArgumentException( "empty team name" );
        if ( teamName.length() > 16 )
            throw new BadArgumentException( "team name is too long", teamName );
        if ( ( displayName != null ) && ( displayName.length() > 32 ) )
            throw new BadArgumentException( "display name is too long", displayName );
        
        DummyTeam team = scoreboard.createTeam( teamName );
        if ( ( displayName != null ) && !displayName.isEmpty() )
        {
            team.setDisplayName( displayName );
        }
    }
    
    @MtsNativeFunction
    public void removeTeam( MtsValue argTeamName )
    {
        String teamName = checkString( argTeamName, 0 );
        
        DummyScoreboard scoreboard = getScoreboard();
        
        DummyTeam team = scoreboard.getTeam( teamName );
        if ( team == null )
            throw new BadArgumentException( "unknown team '%s'", teamName );
        
        scoreboard.removeTeam( team );
    }
    
    @MtsNativeFunction
    public void addTeamMember( MtsValue argTeamName, MtsValue argPlayerName )
    {
        String teamName = checkString( argTeamName, 0 );
        String playerName = checkString( argPlayerName, 1 );
        
        DummyScoreboard scoreboard = getScoreboard();
        
        DummyTeam team = scoreboard.getTeam( teamName );
        if ( team == null )
            throw new BadArgumentException( "unknown team '%s'", teamName );
        
        scoreboard.addTeamMember( playerName, team );
    }
    
    public void removeTeamMember( MtsValue argTeamName, MtsValue argPlayerName )
    {
        String teamName = checkString( argTeamName, 0 );
        String playerName = checkString( argPlayerName, 1 );
        
        DummyScoreboard scoreboard = getScoreboard();
        
        DummyTeam team = scoreboard.getTeam( teamName );
        if ( team == null )
            throw new BadArgumentException( "unknown team '%s'", teamName );
        
        scoreboard.removeTeamMember( playerName, team );
    }
    
}
