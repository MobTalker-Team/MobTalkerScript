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
package net.mobtalker.mobtalkerscript.standalone.lib;

import net.mobtalker.mobtalkerscript.api.*;
import net.mobtalker.mobtalkerscript.api.library.ICreatureLibLogic;
import net.mobtalker.mobtalkerscript.standalone.DummyCreature;

public class ConsoleCreatureLibLogic implements ICreatureLibLogic
{
    private final DummyCreature _creature;
    
    // ========================================
    
    public ConsoleCreatureLibLogic( DummyCreature creature )
    {
        _creature = creature;
    }
    
    // ========================================
    
    @Override
    public void setName( String name )
    {
        _creature.setName( name );
    }
    
    @Override
    public boolean isValidItem( String name )
    {
        // TODO
        return true;
    }
    
    @Override
    public void setEquipment( EquipmentSlot slot, ItemInfo itemInfo )
    {
        // TODO
    }
    
    @Override
    public void setReaction( String key, EntityReaction reaction )
    {
        // TODO
    }
    
    @Override
    public EntityReaction getReaction( String key )
    {
        // TODO
        return EntityReaction.NEUTRAL;
    }
    
    @Override
    public void follow( String key )
    {
        // TODO Auto-generated method stub
    }
    
    @Override
    public void stopFollowing()
    {
        // TODO
    }
}
