/*
 * Copyright (C) 2013-2014 Chimaine
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package net.mobtalker.mobtalkerscript.v2.lib.console.mobtalker;

import static net.mobtalker.mobtalkerscript.v2.MtsCheck.*;
import static net.mobtalker.mobtalkerscript.v2.value.MtsValue.*;

import java.util.Random;

import net.mobtalker.mobtalkerscript.v2.lib.console.mobtalker.InteractionPlayerLib.DummyPlayer;
import net.mobtalker.mobtalkerscript.v2.value.*;
import net.mobtalker.mobtalkerscript.v2.value.userdata.MtsNativeFunction;

public class InteractionPlayerLib extends AbstractConsoleEntityLib<DummyPlayer>
{
    private final DummyPlayer _player;
    
    // ========================================
    
    public InteractionPlayerLib()
    {
        _player = new DummyPlayer( "Chimaine" );
    }
    
    // ========================================
    
    @Override
    protected DummyPlayer getEntity()
    {
        return _player;
    }
    
    // ========================================
    
    @MtsNativeFunction
    public MtsNumber getArmor()
    {
        return ZERO;
    }
    
    // ========================================
    
    @MtsNativeFunction
    public MtsVarArgs getExperience()
    {
        return MtsVarArgs.of( valueOf( 12 ), valueOf( 0.26F ), valueOf( 515 ) );
    }
    
    @MtsNativeFunction
    public void giveExperience( MtsValue arg1 )
    {
        checkInteger( arg1, 0 );
    }
    
    // ========================================
    
    @MtsNativeFunction
    public MtsVarArgs getHeldItem()
    {
        return MtsVarArgs.of( valueOf( "item.mobtalker2:talker" ), valueOf( 0 ), valueOf( 1 ) );
    }
    
    @MtsNativeFunction
    public MtsNumber getItemCount( MtsValue arg1, MtsValue arg2 )
    {
        checkString( arg1, 0 );
        checkInteger( arg2, 1, -1 );
        
        return valueOf( new Random().nextInt( 65 ) );
    }
    
    @MtsNativeFunction
    public MtsBoolean takeItems( MtsValue arg1, MtsValue arg2, MtsValue arg3 )
    {
        checkString( arg1, 0 );
        checkInteger( arg2, 1, 1 );
        checkInteger( arg3, 2, -1 );
        
        return TRUE;
    }
    
    @MtsNativeFunction
    public MtsBoolean giveItems( MtsValue arg1, MtsValue arg2, MtsValue arg3 )
    {
        checkString( arg1, 0 );
        checkInteger( arg2, 1, 1 );
        checkInteger( arg3, 2, -1 );
        
        return TRUE;
    }
    
    private static final MtsValue KEY_ITEM_NAME = valueOf( "name" );
    private static final MtsValue KEY_ITEM_COUNT = valueOf( "count" );
    private static final MtsValue KEY_ITEM_META = valueOf( "meta" );
    
    @MtsNativeFunction
    public MtsTable getInventory()
    {
        MtsTable t = new MtsTable( 32, 0 );
        
        {
            MtsTable info = new MtsTable( 0, 3 );
            info.set( KEY_ITEM_NAME, valueOf( "tile.flower" ) );
            info.set( KEY_ITEM_COUNT, valueOf( 1 ) );
            info.set( KEY_ITEM_META, valueOf( 0 ) );
            t.add( info );
        }
        {
            MtsTable info = new MtsTable( 0, 3 );
            info.set( KEY_ITEM_NAME, valueOf( "item.diamond" ) );
            info.set( KEY_ITEM_COUNT, valueOf( 42 ) );
            info.set( KEY_ITEM_META, valueOf( 0 ) );
            t.add( info );
        }
        {
            MtsTable info = new MtsTable( 0, 3 );
            info.set( KEY_ITEM_NAME, valueOf( "tile.cloth" ) );
            info.set( KEY_ITEM_COUNT, valueOf( 1 ) );
            info.set( KEY_ITEM_META, valueOf( 14 ) );
            t.add( info );
        }
        
        return t;
    }
    
    // ========================================
    
    @MtsNativeFunction
    public MtsString getGameMode()
    {
        return valueOf( getEntity().getGameMode() );
    }
    
    // ========================================
    
    public static class DummyPlayer extends AbstractConsoleEntityLib.DummyEntity
    {
        private String _gameMode;
        
        // ========================================
        
        public DummyPlayer( String name )
        {
            super( name, 20.0F, 20.0F );
            _gameMode = "adventure";
        }
        
        // ========================================
        
        public String getGameMode()
        {
            return _gameMode;
        }
        
        public void setGameMode( String gameMode )
        {
            _gameMode = gameMode;
        }
    }
}
