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
package net.mobtalker.mobtalkerscript.standalone;

import java.util.*;

import com.google.common.collect.*;

public class DummyScoreboard
{
    public static int getObjectiveDisplaySlotNumber( String slotName )
    {
        if ( slotName.equalsIgnoreCase( "list" ) )
            return 0;
        if ( slotName.equalsIgnoreCase( "sidebar" ) )
            return 1;
        if ( slotName.equalsIgnoreCase( "belowName" ) )
            return 2;
        
        return -1;
    }
    
    // ========================================
    
    private final Map<String, DummyObjective> _objectives;
    private final Map<String, Map<DummyObjective, DummyScore>> _playersScores;
    private final Map<String, DummyTeam> _teams;
    
    // ========================================
    
    public DummyScoreboard()
    {
        _objectives = Maps.newHashMap();
        _playersScores = Maps.newHashMap();
        _teams = Maps.newHashMap();
    }
    
    // ========================================
    
    public DummyObjective addObjective( String name, IDummyCriteria criteria )
    {
        DummyObjective objective = new DummyObjective( name, criteria );
        _objectives.put( name, objective );
        
        return objective;
    }
    
    public void removeObjective( DummyObjective objective )
    {
        _objectives.remove( objective.getName() );
        
        for ( Map<DummyObjective, DummyScore> scores : _playersScores.values() )
        {
            scores.remove( objective );
        }
    }
    
    public DummyObjective getObjective( String name )
    {
        return _objectives.get( name );
    }
    
    public Collection<DummyObjective> getScoreObjectives()
    {
        return _objectives.values();
    }
    
    public void setDisplayedObjective( int slot, DummyObjective objective )
    {
        // NOOP
    }
    
    // ========================================
    
    public Set<String> getPlayerNames()
    {
        return _playersScores.keySet();
    }
    
    public DummyScore getPlayerScore( String playerName, DummyObjective objective )
    {
        Map<DummyObjective, DummyScore> scores = _playersScores.get( playerName );
        DummyScore score = scores.get( objective );
        
        if ( score == null )
        {
            score = new DummyScore();
            scores.put( objective, score );
        }
        
        return score;
    }
    
    public Map<DummyObjective, DummyScore> getPlayerScores( String playerName )
    {
        return _playersScores.get( playerName );
    }
    
    // ========================================
    
    public Collection<DummyTeam> getTeams()
    {
        return _teams.values();
    }
    
    public DummyTeam getTeam( String name )
    {
        return _teams.get( name );
    }
    
    public DummyTeam createTeam( String name )
    {
        DummyTeam team = new DummyTeam( name );
        _teams.put( name, team );
        
        return team;
    }
    
    public void removeTeam( DummyTeam name )
    {
        _teams.remove( name );
    }
    
    public void addTeamMember( String name, DummyTeam team )
    {
        team.getMembershipCollection().add( name );
    }
    
    public void removeTeamMember( String name, DummyTeam team )
    {
        team.getMembershipCollection().remove( name );
    }
    
    // ========================================
    
    public static class DummyObjective
    {
        private final String _name;
        private final IDummyCriteria _criteria;
        private String _displayName;
        
        // ========================================
        
        public DummyObjective( String name, IDummyCriteria criteria )
        {
            _name = name;
            _displayName = name;
            _criteria = criteria;
        }
        
        // ========================================
        
        public String getName()
        {
            return _name;
        }
        
        public IDummyCriteria getCriteria()
        {
            return _criteria;
        }
        
        public String getDisplayName()
        {
            return _displayName;
        }
        
        public void setDisplayName( String displayName )
        {
            _displayName = displayName;
        }
    }
    
    // ========================================
    
    public interface IDummyCriteria
    {
        Map<String, IDummyCriteria> CRITERIA = Maps.newHashMap();
        
        IDummyCriteria generic = new BaseCriteria( "dummy" );
        IDummyCriteria deathCount = new BaseCriteria( "deathCount" );
        IDummyCriteria playerKillCount = new BaseCriteria( "playerKillCount" );
        IDummyCriteria totalKillCount = new BaseCriteria( "totalKillCount" );
        IDummyCriteria health = new BaseCriteria( "health" );
        
        String getName();
        
        boolean isReadOnly();
    }
    
    public static class BaseCriteria implements IDummyCriteria
    {
        private final String _name;
        private final boolean _readonly;
        
        // ========================================
        
        public BaseCriteria( String name )
        {
            this( name, false );
        }
        
        public BaseCriteria( String name, boolean readonly )
        {
            _name = name;
            _readonly = readonly;
            
            IDummyCriteria.CRITERIA.put( name, this );
        }
        
        // ========================================
        
        @Override
        public String getName()
        {
            return _name;
        }
        
        @Override
        public boolean isReadOnly()
        {
            return _readonly;
        }
    }
    
    // ========================================
    
    public static class DummyScore
    {
        private int _score;
        
        // ========================================
        
        public int getScorePoints()
        {
            return _score;
        }
        
        public void setScorePoints( int score )
        {
            _score = score;
        }
    }
    
    // ========================================
    
    public static class DummyTeam
    {
        private final String _name;
        private String _displayName;
        private boolean _friendlyFire;
        private boolean _seeFriendlyInvisible;
        
        private final Set<String> _members;
        
        // ========================================
        
        public DummyTeam( String name )
        {
            _name = name;
            _displayName = name;
            
            _members = Sets.newHashSet();
        }
        
        // ========================================
        
        public String getName()
        {
            return _name;
        }
        
        public String getColor()
        {
            return "white";
        }
        
        public String getDisplayName()
        {
            return _displayName;
        }
        
        public void setDisplayName( String name )
        {
            _displayName = name;
        }
        
        public boolean isFriendlyFireEnabled()
        {
            return _friendlyFire;
        }
        
        public boolean canSeeFriendlyInvisibles()
        {
            return _seeFriendlyInvisible;
        }
        
        public Collection<String> getMembershipCollection()
        {
            return _members;
        }
    }
}
