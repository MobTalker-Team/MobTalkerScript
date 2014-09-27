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

import static net.mobtalker.mobtalkerscript.v2.value.MtsValue.*;
import net.mobtalker.mobtalkerscript.v2.lib.console.mobtalker.AbstractConsoleEntityLib.DummyEntity;
import net.mobtalker.mobtalkerscript.v2.value.*;
import net.mobtalker.mobtalkerscript.v2.value.userdata.MtsNativeFunction;

public abstract class AbstractConsoleEntityLib<T extends DummyEntity>
{
    protected AbstractConsoleEntityLib()
    {}
    
    // ========================================
    
    protected abstract T getEntity();
    
    // ========================================
    
    @MtsNativeFunction
    public MtsString getName()
    {
        String name = getEntity().getName();
        return valueOf( name );
    }
    
    @MtsNativeFunction
    public MtsVarArgs getHealth()
    {
        float health = getEntity().getHealth();
        float maxHealth = getEntity().getMaxHealth();
        return MtsVarArgs.of( valueOf( health ), valueOf( maxHealth ) );
    }
    
//    @MtsNativeFunction
    // public void hurt( MtsValue arg1, MtsValue arg2, MtsValue arg3 )
    // {
    // float amount = (float) checkNumber( arg1, 0 );
    // boolean isMagic = MtsValue.isTrue( arg2 );
    // boolean bypassArmor = MtsValue.isTrue( arg3 );
    //
    // if ( amount <= 0.0F )
    // throw new BadArgumentException( 0, "damage value must be greater than zero" );
    //
    // T player = getEntity();
    //
    // DamageSource source = new MobTalkerDamageSource( null, "mob", isMagic, bypassArmor );
    // player.attackEntityFrom( source, amount );
    // }
    
    // ========================================
    
    @MtsNativeFunction
    public MtsBoolean isRiding()
    {
        return FALSE;
    }
    
    // ========================================
    
    @MtsNativeFunction
    public MtsTable getEffects()
    {
        // NOOP
        return new MtsTable( 0, 0 );
    }
    
    @MtsNativeFunction
    public MtsBoolean applyEffect( MtsValue arg1, MtsValue arg2, MtsValue arg3 )
    {
        // NOOP
        return TRUE;
    }
    
    @MtsNativeFunction
    public MtsBoolean removeEffect( MtsValue arg1 )
    {
        // NOOP
        return TRUE;
    }
    
    // ========================================
    
    public static class DummyEntity
    {
        private String _name;
        private float _health;
        private float _maxHealth;
        
        // ========================================
        
        public DummyEntity( String name, float health, float maxHealth )
        {
            _name = name;
            _health = health;
            _maxHealth = maxHealth;
        }
        
        // ========================================
        
        public String getName()
        {
            return _name;
        }
        
        public void setName( String name )
        {
            _name = name;
        }
        
        public float getHealth()
        {
            return _health;
        }
        
        public void setHealth( float health )
        {
            _health = health;
        }
        
        public float getMaxHealth()
        {
            return _maxHealth;
        }
        
        public void setMaxHealth( float maxHealth )
        {
            _maxHealth = maxHealth;
        }
    }
}
