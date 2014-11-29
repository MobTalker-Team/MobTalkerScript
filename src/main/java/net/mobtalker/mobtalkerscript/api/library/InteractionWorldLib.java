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

import static net.mobtalker.mobtalkerscript.v3.MtsCheck.*;
import static net.mobtalker.mobtalkerscript.v3.value.MtsValue.*;

import java.util.Map;

import net.mobtalker.mobtalkerscript.api.IWorldInfo;
import net.mobtalker.mobtalkerscript.v3.value.*;
import net.mobtalker.mobtalkerscript.v3.value.userdata.MtsNativeFunction;

public class InteractionWorldLib
{
    private IInteractionWorldLibLogic _logic;
    
    // ========================================
    
    public InteractionWorldLib( IInteractionWorldLibLogic logic )
    {
        _logic = logic;
    }
    
    // ========================================
    
    public IInteractionWorldLibLogic getLogic()
    {
        return _logic;
    }
    
    // ========================================
    
    @MtsNativeFunction
    public MtsTable getWorlds()
    {
        Map<Integer, IWorldInfo> worlds = _logic.getWorlds();
        
        MtsTable t = new MtsTable( 0, worlds.size() );
        for ( IWorldInfo world : worlds.values() )
        {
            MtsTable info = new MtsTable( 0, 2 );
            info.set( "name", valueOf( world.getName() ) );
            info.set( "type", valueOf( world.getType() ) );
            t.set( valueOf( world.getID() ), info );
        }
        
        return t;
    }
    
    @MtsNativeFunction
    public MtsVarArgs getDifficulty()
    {
        return MtsVarArgs.of( valueOf( _logic.getDifficulty() ),
                              valueOf( _logic.isHardcore() ) );
    }
    
    @MtsNativeFunction
    public MtsBoolean getGameRule( MtsValue argRuleName )
    {
        return valueOf( _logic.getGameRule( checkString( argRuleName, 0 ) ) );
    }
    
    // ========================================
    
    @MtsNativeFunction
    public MtsNumber getID()
    {
        return valueOf( _logic.getWorld().getID() );
    }
    
    @MtsNativeFunction
    public MtsString getName()
    {
        return valueOf( _logic.getWorld().getName() );
    }
    
    @MtsNativeFunction
    public MtsString getType()
    {
        return valueOf( _logic.getWorld().getType() );
    }
    
    @MtsNativeFunction
    public MtsNumber getTime()
    {
        return valueOf( _logic.getWorld().getTime() );
    }
    
    @MtsNativeFunction
    public MtsNumber getMoonphase()
    {
        return valueOf( _logic.getWorld().getMoonphase() );
    }
    
    @MtsNativeFunction
    public MtsBoolean isDaytime()
    {
        return valueOf( _logic.getWorld().isDaytime() );
    }
    
    // ========================================
    
    @MtsNativeFunction
    public MtsString getBiome()
    {
        return valueOf( _logic.getBiome() );
    }
    
    @MtsNativeFunction
    public MtsVarArgs getClimate()
    {
        return MtsVarArgs.of( valueOf( _logic.getTemperature() ),
                              valueOf( _logic.getHumidity() ) );
    }
    
    @MtsNativeFunction
    public MtsVarArgs getWeather()
    {
        return MtsVarArgs.of( valueOf( _logic.getWeather() ),
                              valueOf( _logic.isThundering() ) );
    }
}
