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
