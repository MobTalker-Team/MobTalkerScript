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
package net.mobtalker.mobtalkerscript.api;

import static net.mobtalker.mobtalkerscript.v3.value.MtsValue.*;
import net.mobtalker.mobtalkerscript.v3.value.MtsString;

public class ScriptApiConstants
{
    public static final MtsString KEY_ITEM_NAME = valueOf( "name" );
    public static final MtsString KEY_ITEM_COUNT = valueOf( "count" );
    public static final MtsString KEY_ITEM_META = valueOf( "meta" );
    
    public static final MtsString KEY_EFFECT_NAME = valueOf( "name" );
    public static final MtsString KEY_EFFECT_DURATION = valueOf( "duration" );
    public static final MtsString KEY_EFFECT_AMPLIFIER = valueOf( "amplifier" );
    
    public static final MtsString KEY_SCOREBOARD_TEAM_COLOR = valueOf( "color" );
    public static final MtsString KEY_SCOREBOARD_TEAM_FRIENDLYFIRE = valueOf( "friendlyFire" );
    public static final MtsString KEY_SCOREBOARD_TEAM_SEEFIRENDLYINVISIBLES = valueOf( "seeFriendlyInvisibles" );

    public static final String ENTITYKEY_PLAYER = "player";
    public static final String ENTITYKEY_PREFIX_TYPE = "type:";
    
    // ========================================
    
    private ScriptApiConstants()
    {}
}
