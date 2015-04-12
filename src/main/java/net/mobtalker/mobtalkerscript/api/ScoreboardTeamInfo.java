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
package net.mobtalker.mobtalkerscript.api;

public class ScoreboardTeamInfo
{
    public final String Name;
    public final String Color;
    public final boolean FriendlyFire;
    public final boolean CanSeeInvisibleMembers;
    
    // ========================================
    
    public ScoreboardTeamInfo( String name, String color, boolean friendlyFire, boolean canSeeInvisibleMembers )
    {
        Name = name;
        Color = color;
        FriendlyFire = friendlyFire;
        CanSeeInvisibleMembers = canSeeInvisibleMembers;
    }
}
