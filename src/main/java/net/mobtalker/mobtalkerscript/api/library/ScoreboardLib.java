/*
 * Copyright (C) 2013-2014 Chimaine
 *
 * This program is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package net.mobtalker.mobtalkerscript.api.library;

import static net.mobtalker.mobtalkerscript.v2.MtsCheck.*;
import static net.mobtalker.mobtalkerscript.v2.value.MtsValue.*;

import java.util.List;

import net.mobtalker.mobtalkerscript.api.*;
import net.mobtalker.mobtalkerscript.v2.BadArgumentException;
import net.mobtalker.mobtalkerscript.v2.value.*;
import net.mobtalker.mobtalkerscript.v2.value.userdata.MtsNativeFunction;

import com.google.common.base.Strings;

public class ScoreboardLib extends AbstractUnifiedLib<IScoreboardLibLogic>
{
    public ScoreboardLib( IScoreboardLibLogic logic )
    {
        super( logic );
    }
    
    // ========================================
    // Objectives
    
    @MtsNativeFunction
    public void setDisplayedObjective( MtsValue argSlotName, MtsValue argObjectiveName )
    {
        String slot = checkString( argSlotName, 0 );
        String objective = checkString( argObjectiveName, 1 );
        
        if ( !_logic.checkObjectiveDisplaySlot( slot ) )
            throw new BadArgumentException( 0, "invalid display slot '%s'", slot );
        if ( !_logic.hasObjective( objective ) )
            throw new BadArgumentException( 1, "unknown objective '%s'", objective );
        
        _logic.setDisplayedObjective( slot, objective );
    }
    
    @MtsNativeFunction
    public void clearDisplayedObjective( MtsValue argSlotName )
    {
        String slot = checkString( argSlotName, 0 );
        
        if ( !_logic.checkObjectiveDisplaySlot( slot ) )
            throw new BadArgumentException( 0, "invalid display slot '%s'", slot );
        
        _logic.clearDisplayedObjective( slot );
    }
    
    @MtsNativeFunction
    public MtsTable getObjectives()
    {
        List<ScoreboardObjectiveInfo> objectives = _logic.getObjectives();
        
        MtsTable result = new MtsTable( 0, objectives.size() );
        for ( ScoreboardObjectiveInfo objective : objectives )
        {
            result.set( valueOf( objective.Name ), valueOf( objective.Criteria ) );
        }
        
        return result;
    }
    
    @MtsNativeFunction
    public MtsBoolean hasObjective( MtsValue argName )
    {
        return valueOf( _logic.hasObjective( checkString( argName, 0 ) ) );
    }
    
    @MtsNativeFunction
    public void addObjective( MtsValue argName, MtsValue argCriteraName, MtsValue argDisplayName )
    {
        String name = checkString( argName, 0 );
        String criteria = checkString( argCriteraName, 1 );
        String displayName = checkString( argDisplayName, 2, null );
        
        if ( Strings.isNullOrEmpty( name ) )
            throw new BadArgumentException( 0, "name is empty" );
        if ( name.length() > 16 )
            throw new BadArgumentException( 0, "name must be no longer than 16 characters" );
        if ( _logic.hasObjective( name ) )
            throw new BadArgumentException( 0, "objective '%s' already exists", name );
        if ( !_logic.hasCriteria( criteria ) )
            throw new BadArgumentException( 1, "unknown criteria '%s'", criteria );
        if ( !Strings.isNullOrEmpty( displayName ) && ( displayName.length() > 32 ) )
            throw new BadArgumentException( 2, "display name must be no longer than 32 characters" );
        
        _logic.addObjective( name, criteria, displayName );
    }
    
    @MtsNativeFunction
    public void removeObjective( MtsValue argName )
    {
        String name = checkString( argName, 0 );
        
        if ( !_logic.hasObjective( name ) )
            throw new BadArgumentException( 0, "unknown objective '%s'", name );
        
        _logic.removeObjective( name );
    }
    
    // ========================================
    // Players & Scores
    
    @MtsNativeFunction
    public MtsTable getPlayers()
    {
        List<String> names = _logic.getPlayerNames();
        
        MtsTable result = new MtsTable( names.size(), 0 );
        for ( String name : names )
        {
            result.add( valueOf( name ) );
        }
        
        return result;
    }
    
    @MtsNativeFunction
    public MtsTable getScores( MtsValue argPlayerName )
    {
        List<ScoreboardScoreInfo> scores = _logic.getPlayerScores( checkString( argPlayerName, 0 ) );
        
        MtsTable result = new MtsTable( 0, scores.size() );
        for ( ScoreboardScoreInfo score : scores )
        {
            result.set( valueOf( score.Objective.Name ), valueOf( score.Score ) );
        }
        
        return result;
    }
    
    @MtsNativeFunction
    public MtsNumber getScore( MtsValue argPlayerName, MtsValue argObjectiveName )
    {
        String player = checkString( argPlayerName, 0 );
        String objective = checkString( argObjectiveName, 1 );
        
        if ( !_logic.hasObjective( objective ) )
            throw new BadArgumentException( 1, "unknown objective '%s'", objective );
        
        return valueOf( _logic.getPlayerScore( player, objective ).Score );
    }
    
    @MtsNativeFunction
    public void setScore( MtsValue argPlayerName, MtsValue argObjectiveName, MtsValue argValue )
    {
        String objective = checkString( argObjectiveName, 1 );
        if ( !_logic.hasObjective( objective ) )
            throw new BadArgumentException( 1, "unknown objective '%s'", objective );
        
        _logic.setPlayerScore( checkString( argPlayerName, 0 ),
                               objective,
                               checkInteger( argValue, 2 ) );
    }
    
    @MtsNativeFunction
    public MtsNumber increaseScore( MtsValue argPlayerName, MtsValue argObjectiveName, MtsValue argValue )
    {
        String objective = checkString( argObjectiveName, 1 );
        if ( !_logic.hasObjective( objective ) )
            throw new BadArgumentException( 1, "unknown objective '%s'", objective );
        
        return valueOf( _logic.increasePlayerScore( checkString( argPlayerName, 0 ),
                                                    objective,
                                                    checkIntegerWithMinimum( argValue, 2, 0, 1 ) ) );
    }
    
    @MtsNativeFunction
    public MtsNumber decreaseScore( MtsValue argPlayerName, MtsValue argObjectiveName, MtsValue argValue )
    {
        String objective = checkString( argObjectiveName, 1 );
        if ( !_logic.hasObjective( objective ) )
            throw new BadArgumentException( 1, "unknown objective '%s'", objective );
        
        return valueOf( _logic.decreasePlayerScore( checkString( argPlayerName, 0 ),
                                                    objective,
                                                    checkIntegerWithMinimum( argValue, 2, 0, 1 ) ) );
    }
    
    // ========================================
    // Teams
    
    @MtsNativeFunction
    public MtsTable getTeams()
    {
        List<ScoreboardTeamInfo> teams = _logic.getTeams();
        
        MtsTable result = new MtsTable( 0, teams.size() );
        for ( ScoreboardTeamInfo team : teams )
        {
            MtsTable info = new MtsTable( 0, 3 );
            info.set( "color", valueOf( team.Color ) );
            info.set( "friendlyfire", valueOf( team.FriendlyFire ) );
            info.set( "seeFriendlyInvisibles", valueOf( team.CanSeeInvisibleMembers ) );
            result.set( team.Name, info );
        }
        
        return result;
    }
    
    @MtsNativeFunction
    public MtsTable getTeamMembers( MtsValue argTeamName )
    {
        String teamName = checkString( argTeamName, 0 );
        
        if ( !_logic.hasTeam( teamName ) )
            throw new BadArgumentException( 0, "unknown team '%s'", teamName );
        
        List<String> players = _logic.getTeamMembers( teamName );
        
        MtsTable result = new MtsTable( players.size(), 0 );
        for ( String player : players )
        {
            result.add( valueOf( player ) );
        }
        
        return result;
    }
    
    @MtsNativeFunction
    public void addTeam( MtsValue argTeamName, MtsValue argDisplayName )
    {
        String name = checkString( argTeamName, 0 );
        String displayName = checkString( argDisplayName, 1, null );
        
        if ( _logic.hasTeam( name ) )
            throw new BadArgumentException( 0, "team '%s' already exists", name );
        if ( Strings.isNullOrEmpty( name ) )
            throw new BadArgumentException( 0, "empty team name" );
        if ( name.length() > 16 )
            throw new BadArgumentException( 0, "team name is too long", name );
        if ( ( displayName != null ) && ( displayName.length() > 32 ) )
            throw new BadArgumentException( 1, "display name is too long", displayName );
        
        _logic.addTeam( name, displayName );
    }
    
    @MtsNativeFunction
    public void removeTeam( MtsValue argTeamName )
    {
        String name = checkString( argTeamName, 0 );
        
        if ( !_logic.hasTeam( name ) )
            throw new BadArgumentException( 0, "unknown team '%s'", name );
        
        _logic.removeTeam( name );
    }
    
    @MtsNativeFunction
    public void addTeamMember( MtsValue argTeamName, MtsValue argPlayerName )
    {
        String teamName = checkString( argTeamName, 0 );
        
        if ( !_logic.hasTeam( teamName ) )
            throw new BadArgumentException( 0, "unknown team '%s'", teamName );
        
        _logic.addTeamMember( teamName, checkString( argPlayerName, 1 ) );
    }
    
    public void removeTeamMember( MtsValue argTeamName, MtsValue argPlayerName )
    {
        String teamName = checkString( argTeamName, 0 );
        
        if ( !_logic.hasTeam( teamName ) )
            throw new BadArgumentException( 0, "unknown team '%s'", teamName );
        
        _logic.removeTeamMember( teamName, checkString( argPlayerName, 1 ) );
    }
    
}
