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

import static net.mobtalker.mobtalkerscript.api.ScriptApiConstants.*;
import static net.mobtalker.mobtalkerscript.v3.MtsCheck.*;
import net.mobtalker.mobtalkerscript.api.*;
import net.mobtalker.mobtalkerscript.v3.MtsArgumentException;
import net.mobtalker.mobtalkerscript.v3.value.*;
import net.mobtalker.mobtalkerscript.v3.value.userdata.MtsNativeFunction;

public final class PlayerLib extends AbstractUnifiedLib<IPlayerLibLogic>
{
    public PlayerLib( IPlayerLibLogic logic )
    {
        super( logic );
    }
    
    // ========================================
    
    @MtsNativeFunction
    public MtsString getGameMode()
    {
        return MtsString.of( _logic.getGameMode() );
    }
    
    // ========================================
    
    @MtsNativeFunction
    public MtsNumber getArmor()
    {
        return MtsNumber.of( _logic.getArmor() );
    }
    
    // ========================================
    
    @MtsNativeFunction
    public MtsVarargs getExperience()
    {
        return MtsVarargs.of( MtsNumber.of( _logic.getExperienceLevel() ),
                              MtsNumber.of( _logic.getExperience() ),
                              MtsNumber.of( _logic.getExperienceRequired() ) );
    }
    
    @MtsNativeFunction
    public void giveExperience( MtsValue argAmount )
    {
        _logic.giveExperience( checkInteger( argAmount, 0 ) );
    }
    
    @MtsNativeFunction
    public MtsBoolean takeExperience( MtsValue argAmount )
    {
        return MtsBoolean.of( _logic.takeExperience( checkInteger( argAmount, 0 ) ) );
    }
    
    // ========================================
    
    @MtsNativeFunction
    public MtsNumber countItem( MtsValue argItemName, MtsValue argItemMeta )
    {
        String name = checkString( argItemName, 0 );
        if ( !_logic.isValidItem( name ) )
            throw new MtsArgumentException( 0, "unknown item name '%s'", name );
        
        return MtsNumber.of( _logic.getItemCount( name,
                                                  checkInteger( argItemMeta, 1, -1 ) ) );
    }
    
    @MtsNativeFunction
    public MtsBoolean giveItem( MtsValue argItemName, MtsValue argItemCount, MtsValue argItemMeta )
    {
        String name = checkString( argItemName, 0 );
        if ( !_logic.isValidItem( name ) )
            throw new MtsArgumentException( 0, "unknown item name '%s'", name );
        
        return MtsBoolean.of( _logic.giveItems( name,
                                                checkIntegerWithMinimum( argItemCount, 1, 1, 1 ),
                                                checkIntegerWithMinimum( argItemMeta, 2, 0, -1 ) ) );
    }
    
    @MtsNativeFunction
    public MtsBoolean takeItem( MtsValue argItemName, MtsValue argItemCount, MtsValue argItemMeta )
    {
        String name = checkString( argItemName, 0 );
        if ( !_logic.isValidItem( name ) )
            throw new MtsArgumentException( 0, "unknown item name '%s'", name );
        
        return MtsBoolean.of( _logic.takeItems( name,
                                                checkIntegerWithMinimum( argItemCount, 1, 1, 1 ),
                                                checkIntegerWithMinimum( argItemMeta, 2, 0, -1 ) ) );
    }
    
    @MtsNativeFunction
    public MtsTable getInventory()
    {
        InventoryInfo inventory = _logic.getInventory();
        
        MtsTable t = new MtsTable( inventory.count(), 0 );
        for ( ItemInfo item : inventory.getItems() )
        {
            MtsTable info = new MtsTable( 0, 3 );
            info.set( KEY_ITEM_NAME, MtsString.of( item.Name ) );
            info.set( KEY_ITEM_META, MtsNumber.of( item.Meta ) );
            info.set( KEY_ITEM_COUNT, MtsNumber.of( inventory.getAmount( item ) ) );
            t.list().add( info );
        }
        
        return t;
    }
}
