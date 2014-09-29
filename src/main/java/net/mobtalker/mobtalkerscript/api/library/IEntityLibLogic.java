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

import java.util.List;

import net.mobtalker.mobtalkerscript.api.*;

public interface IEntityLibLogic
{
    String getName();
    
    // ========================================
    
    double getHealth();
    
    double getMaxHealth();
    
    // ========================================
    
    WorldPosition getPosition();
    
    // ========================================
    
    boolean isRiding();
    
    // ========================================
    
    List<EffectInfo> getEffects();
    
    boolean isValidEffect( String name );
    
    boolean applyEffect( String name, int duration, int amplifier );
    
    boolean removeEffect( String name );
    
    void removeAllEffects();
    
    // ========================================
    
    EquipmentInfo getEquipment();
    
    ItemInfo getEquipment( EquipmentSlot slot );
}
