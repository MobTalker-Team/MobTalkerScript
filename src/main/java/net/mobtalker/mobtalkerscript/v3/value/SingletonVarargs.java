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

/* package */class SingletonVarargs extends MtsVarargs
{
    private final MtsValue _value;
    
    // ========================================
    
    /* package */public SingletonVarargs( MtsValue value )
    {
        _value = value;
    }
    
    // ========================================
    
    @Override
    public MtsVarargs subArgs( int start, int end )
    {
        if ( ( start > 0 ) || ( end <= start ) )
            return Empty;
        
        return this;
    }
    
    // ========================================
    
    /**
     * Returns the value at index <code>i</code> (zero based) or <code>nil</code>, if the index does not exist.
     */
    @Override
    public MtsValue get( int i )
    {
        if ( i < 0 )
            throw new IndexOutOfBoundsException();
        if ( i > 0 )
            return Nil;
        
        return _value;
    }
    
    @Override
    public int count()
    {
        return 1;
    }
    
    @Override
    public boolean isEmpty()
    {
        return false;
    }
    
    // ========================================
    
    @Override
    public MtsValue[] toArray()
    {
        return new MtsValue[] { _value };
    }
    
    // ========================================
    
    @Override
    public Iterator<MtsValue> iterator()
    {
        return Iterators.singletonIterator( _value );
    }
    
    // ========================================
    
    @Override
    public String toString()
    {
        return super.toString() + '[' + _value.toString() + ']';
    }
}
