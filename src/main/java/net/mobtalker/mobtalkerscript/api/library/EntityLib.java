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

import static net.mobtalker.mobtalkerscript.api.ScriptApiConstants.*;
import static net.mobtalker.mobtalkerscript.v2.MtsCheck.*;
import static net.mobtalker.mobtalkerscript.v2.value.MtsValue.*;

import java.util.List;

import net.mobtalker.mobtalkerscript.api.*;
import net.mobtalker.mobtalkerscript.v2.*;
import net.mobtalker.mobtalkerscript.v2.value.*;
import net.mobtalker.mobtalkerscript.v2.value.userdata.MtsNativeFunction;

public class EntityLib extends AbstractUnifiedLib<IEntityLibLogic>
{
    public EntityLib( IEntityLibLogic logic )
    {
        super( logic );
    }
    
    // ========================================
    
    @MtsNativeFunction
    public MtsString getName()
    {
        return valueOf( _logic.getName() );
    }
    
    @MtsNativeFunction
    public MtsVarArgs getHealth()
    {
        return MtsVarArgs.of( valueOf( _logic.getHealth() ),
                              valueOf( _logic.getMaxHealth() ) );
    }
    
    @MtsNativeFunction
    public MtsVarArgs getPosition()
    {
        WorldPosition position = _logic.getPosition();
        return MtsVarArgs.of( valueOf( position.X ), valueOf( position.Y ), valueOf( position.Z ) );
    }
    
    // ========================================
    
    @MtsNativeFunction
    public MtsBoolean isRiding()
    {
        return valueOf( _logic.isRiding() );
    }
    
    // ========================================
    
    @MtsNativeFunction
    public MtsTable getEffects()
    {
        List<EffectInfo> effects = _logic.getEffects();
        
        MtsTable t = new MtsTable( effects.size(), 0 );
        if ( effects.isEmpty() )
            return t;
        
        for ( EffectInfo effect : effects )
        {
            MtsTable effectTable = new MtsTable( 0, 3 );
            effectTable.set( KEY_EFFECT_NAME, valueOf( effect.Name ) );
            effectTable.set( KEY_EFFECT_DURATION, valueOf( effect.Duration ) );
            effectTable.set( KEY_EFFECT_AMPLIFIER, valueOf( effect.Amplifier ) );
            t.add( effectTable );
        }
        
        return t;
    }
    
    @MtsNativeFunction
    public MtsBoolean applyEffect( MtsValue argEffect, MtsValue argDuration, MtsValue argAmplifier )
    {
        String name = checkString( argEffect, 0 );
        if ( !_logic.isValidEffect( name ) )
            throw new BadArgumentException( 0, "'%s' is not a valid potion effect", name );
        
        return valueOf( _logic.applyEffect( name,
                                            checkIntegerWithMinimum( argDuration, 1, 0 ),
                                            checkIntegerWithMinimum( argAmplifier, 2, 0, 0 ) ) );
    }
    
    @MtsNativeFunction
    public MtsBoolean removeEffect( MtsValue argEffect )
    {
        String name = checkString( argEffect, 0 );
        if ( !_logic.isValidEffect( name ) )
            throw new BadArgumentException( 0, "'%s' is not a valid potion effect", name );
        
        return valueOf( _logic.removeEffect( name ) );
    }
    
    @MtsNativeFunction
    public void removeAllEffects()
    {
        _logic.removeAllEffects();
    }
    
    // ========================================
    
    @MtsNativeFunction
    public MtsValue getEquipment( MtsValue argSlot )
    {
        if ( argSlot.isNil() )
        {
            EquipmentInfo equipment = _logic.getEquipment();
            
            MtsTable t = new MtsTable( 0, equipment.count() );
            for ( EquipmentSlot slot : equipment.getSlots() )
            {
                ItemInfo item = equipment.getItem( slot );
                MtsTable info = new MtsTable( 0, 2 );
                info.set( KEY_ITEM_NAME, valueOf( item.Name ) );
                info.set( KEY_ITEM_META, valueOf( item.Meta ) );
                t.set( slot.getName(), info );
            }
            
            return t;
        }
        else
        {
            String slotName = MtsCheck.checkString( argSlot, 0 );
            EquipmentSlot slot = EquipmentSlot.forName( slotName );
            if ( slot == null )
                throw new BadArgumentException( 0, "'%s' is not a valid equipment slot", slotName );
            
            ItemInfo item = _logic.getEquipment( slot );
            return MtsVarArgs.of( valueOf( item.Name ), valueOf( item.Meta ) );
        }
    }
}
