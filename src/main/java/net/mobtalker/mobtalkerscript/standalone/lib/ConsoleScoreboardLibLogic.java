/*
 * Copyright (C) 2013-2015 Chimaine
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
package net.mobtalker.mobtalkerscript.standalone.lib;

import java.util.*;
import java.util.Map.Entry;

import net.mobtalker.mobtalkerscript.api.*;
import net.mobtalker.mobtalkerscript.api.library.IScoreboardLibLogic;
import net.mobtalker.mobtalkerscript.standalone.*;
import net.mobtalker.mobtalkerscript.standalone.DummyScoreboard.DummyObjective;
import net.mobtalker.mobtalkerscript.standalone.DummyScoreboard.DummyScore;
import net.mobtalker.mobtalkerscript.standalone.DummyScoreboard.DummyTeam;
import net.mobtalker.mobtalkerscript.standalone.DummyScoreboard.IDummyCriteria;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;

public class ConsoleScoreboardLibLogic implements IScoreboardLibLogic
{
    private final DummyScoreboard _scoreboard;

    // ========================================

    public ConsoleScoreboardLibLogic()
    {
        _scoreboard = new DummyScoreboard();
    }

    // ========================================

    private DummyObjective getObjective( String name, boolean throwOnReadonly )
    {
        DummyObjective objective = _scoreboard.getObjective( name );
        return objective;
    }

    // ========================================
    // Objectives

    @Override
    public boolean checkObjectiveDisplaySlot( String slot )
    {
        return DummyScoreboard.getObjectiveDisplaySlotNumber( slot ) > -1;
    }
    
    @Override
    public void setDisplayedObjective( String slotName, String objectiveName )
    {
        int slot = DummyScoreboard.getObjectiveDisplaySlotNumber( slotName );
        DummyObjective objective = getObjective( objectiveName, false );
        _scoreboard.setDisplayedObjective( slot, objective );
    }
    
    @Override
    public void clearDisplayedObjective( String slotName )
    {
        int slot = DummyScoreboard.getObjectiveDisplaySlotNumber( slotName );
        _scoreboard.setDisplayedObjective( slot, null );
    }

    @Override
    public List<ScoreboardObjectiveInfo> getObjectives()
    {
        Collection<DummyObjective> objectives = _scoreboard.getScoreObjectives();

        List<ScoreboardObjectiveInfo> result = Lists.newArrayListWithExpectedSize( objectives.size() );
        for ( DummyObjective objective : objectives )
        {
            String name = objective.getName();
            String criteria = objective.getCriteria().getName();

            result.add( new ScoreboardObjectiveInfo( name, criteria ) );
        }

        return result;
    }

    @Override
    public boolean hasCriteria( String criteria )
    {
        return IDummyCriteria.CRITERIA.get( criteria ) != null;
    }

    @Override
    public boolean hasObjective( String name )
    {
        return _scoreboard.getObjective( name ) != null;
    }

    @Override
    public void addObjective( String name, String criteriaName, String displayName )
    {
        IDummyCriteria criteria = IDummyCriteria.CRITERIA.get( criteriaName );

        if ( !Strings.isNullOrEmpty( displayName ) )
        {
            _scoreboard.addObjective( name, criteria ).setDisplayName( displayName );
        }
        else
        {
            _scoreboard.addObjective( name, criteria );
        }
    }

    @Override
    public void removeObjective( String name )
    {
        DummyObjective objective = getObjective( name, false );
        _scoreboard.removeObjective( objective );
    }

    // ========================================
    // Players

    @Override
    public List<String> getPlayerNames()
    {
        return Lists.newArrayList( _scoreboard.getPlayerNames() );
    }

    @Override
    public List<ScoreboardScoreInfo> getPlayerScores( String playerName )
    {
        Map<DummyObjective, DummyScore> scores = _scoreboard.getPlayerScores( playerName );
        List<ScoreboardScoreInfo> results = Lists.newArrayListWithExpectedSize( scores.size() );

        for ( Entry<DummyObjective, DummyScore> entry : scores.entrySet() )
        {
            DummyObjective objective = entry.getKey();
            DummyScore score = entry.getValue();

            results.add( new ScoreboardScoreInfo( playerName,
                                                  new ScoreboardObjectiveInfo( objective.getName(),
                                                                               objective.getCriteria().getName() ),
                                                                               score.getScorePoints() ) );
        }

        return results;
    }

    @Override
    public ScoreboardScoreInfo getPlayerScore( String playerName, String objectiveName )
    {
        DummyObjective objective = getObjective( objectiveName, false );
        DummyScore score = _scoreboard.getPlayerScore( playerName, objective );

        return new ScoreboardScoreInfo( playerName,
                                        new ScoreboardObjectiveInfo( objective.getName(),
                                                                     objective.getCriteria().getName() ),
                                                                     score.getScorePoints() );
    }

    @Override
    public void setPlayerScore( String playerName, String objectiveName, int value )
    {
        DummyObjective objective = getObjective( objectiveName, false );
        DummyScore score = _scoreboard.getPlayerScore( playerName, objective );
        score.setScorePoints( value );
    }

    @Override
    public int increasePlayerScore( String playerName, String objectiveName, int value )
    {
        DummyObjective objective = getObjective( objectiveName, false );
        DummyScore score = _scoreboard.getPlayerScore( playerName, objective );
        score.setScorePoints( score.getScorePoints() + value );
        return score.getScorePoints();
    }

    @Override
    public int decreasePlayerScore( String playerName, String objectiveName, int value )
    {
        DummyObjective objective = getObjective( objectiveName, false );
        DummyScore score = _scoreboard.getPlayerScore( playerName, objective );
        score.setScorePoints( score.getScorePoints() - value );
        return score.getScorePoints();
    }

    // ========================================
    // Teams

    @Override
    public boolean hasTeam( String teamName )
    {
        return _scoreboard.getTeam( teamName ) != null;
    }

    @Override
    public List<ScoreboardTeamInfo> getTeams()
    {
        Collection<DummyTeam> teams = _scoreboard.getTeams();
        List<ScoreboardTeamInfo> results = Lists.newArrayListWithExpectedSize( teams.size() );

        for ( DummyTeam team : teams )
        {
            String name = team.getName();
            String color = team.getColor();
            boolean friendlyFire = team.isFriendlyFireEnabled();
            boolean seeFriendlyInvisibles = team.canSeeFriendlyInvisibles();

            results.add( new ScoreboardTeamInfo( name, color, friendlyFire, seeFriendlyInvisibles ) );
        }

        return results;
    }

    @Override
    public List<String> getTeamMembers( String teamName )
    {
        return Lists.newArrayList( _scoreboard.getTeam( teamName ).getMembershipCollection() );
    }

    @Override
    public void addTeam( String teamName, String displayName )
    {
        DummyTeam team = _scoreboard.createTeam( teamName );
        if ( ( displayName != null ) && !displayName.isEmpty() )
        {
            team.setDisplayName( displayName );
        }
    }

    @Override
    public void removeTeam( String teamName )
    {
        DummyTeam team = _scoreboard.getTeam( teamName );
        _scoreboard.removeTeam( team );
    }

    @Override
    public void addTeamMember( String teamName, String player )
    {
        DummyTeam team = _scoreboard.getTeam( teamName );
        _scoreboard.addTeamMember( player, team );
    }

    @Override
    public void removeTeamMember( String teamName, String player )
    {
        DummyTeam team = _scoreboard.getTeam( teamName );
        _scoreboard.removeTeamMember( player, team );
    }
}
