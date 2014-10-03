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

import static net.mobtalker.mobtalkerscript.api.ScriptApiCheck.*;
import static net.mobtalker.mobtalkerscript.v2.MtsCheck.*;
import static net.mobtalker.mobtalkerscript.v2.value.MtsValue.*;
import net.mobtalker.mobtalkerscript.api.*;
import net.mobtalker.mobtalkerscript.v2.BadArgumentException;
import net.mobtalker.mobtalkerscript.v2.value.MtsValue;
import net.mobtalker.mobtalkerscript.v2.value.userdata.MtsNativeFunction;

public class CreatureLib extends AbstractUnifiedLib<ICreatureLibLogic>
{
    public CreatureLib( ICreatureLibLogic logic )
    {
        super( logic );
    }
    
    // ========================================
    
    @MtsNativeFunction
    public void setName( MtsValue argName )
    {
        _logic.setName( checkString( argName, 0 ) );
    }
    
    // ========================================
    
    @MtsNativeFunction
    public void setEquipment( MtsValue argSlot, MtsValue argItemName, MtsValue argMeta )
    {
        String slotName = checkString( argSlot, 0 );
        EquipmentSlot slot = EquipmentSlot.forName( slotName );
        if ( slot == null )
            throw new BadArgumentException( 0, "'%s' is not a valid equipment slot", slotName );
        
        String itemName = checkString( argItemName, 1 );
        if ( !_logic.isValidItem( itemName ) )
            throw new BadArgumentException( 1, "unknown item name '%s'", itemName );
        
        int meta = checkIntegerWithMinimum( argMeta, 2, 0, 0 );
        
        _logic.setEquipment( slot, new ItemInfo( itemName, meta ) );
    }
    
    // ========================================
    
    @MtsNativeFunction
    public MtsValue getReaction( MtsValue argKey )
    {
        return valueOf( _logic.getReaction( checkString( argKey, 0 ) ).getName() );
    }
    
    @MtsNativeFunction
    public void setReaction( MtsValue argKey, MtsValue argReaction )
    {
        _logic.setReaction( checkString( argKey, 0 ),
                            checkReaction( argReaction, 1 ) );
    }
    
    // ========================================
    
    @MtsNativeFunction
    public void follow( MtsValue argKey )
    {
        _logic.follow( checkString( argKey, 0 ) );
    }
    
    @MtsNativeFunction
    public void stopFollowing()
    {
        _logic.stopFollowing();
    }
}
