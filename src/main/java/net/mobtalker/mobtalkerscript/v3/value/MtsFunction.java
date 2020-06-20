/*
 * Copyright (C) 2013-2020 Chimaine, MobTalkerScript contributors
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
package net.mobtalker.mobtalkerscript.v3.value;

public abstract class MtsFunction extends MtsValue
{
    @Override
    public abstract MtsVarargs call( MtsVarargs args );
    
    // ========================================
    
    @Override
    public boolean isFunction()
    {
        return true;
    }
    
    @Override
    public MtsFunction asFunction()
    {
        return this;
    }
    
    @Override
    public MtsType getType()
    {
        return MtsType.FUNCTION;
    }
}
