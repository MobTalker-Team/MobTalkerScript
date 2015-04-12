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
package net.mobtalker.mobtalkerscript.standalone;

import net.mobtalker.mobtalkerscript.api.WorldPosition;

public class DummyEntity
{
    private String _name;
    private float _health;
    private float _maxHealth;
    private WorldPosition _position;

    // ========================================

    public DummyEntity( String name, float health, float maxHealth, WorldPosition position )
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

    public WorldPosition getPosition()
    {
        return _position;
    }

    public void setPosition( WorldPosition position )
    {
        _position = position;
    }
}