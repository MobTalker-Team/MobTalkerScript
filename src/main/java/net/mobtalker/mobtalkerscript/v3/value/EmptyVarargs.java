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
package net.mobtalker.mobtalkerscript.v3.value;

import java.util.Iterator;

import com.google.common.collect.Iterators;

/* package */class EmptyVarargs extends MtsVarargs
{
    @Override
    public MtsVarargs subArgs( int start, int end )
    {
        return this;
    }
    
    @Override
    public MtsValue get( int i )
    {
        return MtsValue.Nil;
    }
    
    @Override
    public int count()
    {
        return 0;
    }
    
    @Override
    public boolean isEmpty()
    {
        return true;
    }
    
    @Override
    public MtsValue[] toArray()
    {
        return new MtsValue[0];
    }
    
    @Override
    public Iterator<MtsValue> iterator()
    {
        return Iterators.emptyIterator();
    }
}
