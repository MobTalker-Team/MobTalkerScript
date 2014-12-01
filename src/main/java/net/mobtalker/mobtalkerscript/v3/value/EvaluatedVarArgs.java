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
package net.mobtalker.mobtalkerscript.v3.value;

import java.util.*;

import com.google.common.collect.Lists;

/* package */class EvaluatedVarArgs extends MtsVarArgs
{
    private final List<MtsValue> _values;
    
    // ========================================
    
    /* package */public EvaluatedVarArgs()
    {
        _values = Collections.emptyList();
    }
    
    public EvaluatedVarArgs( MtsValue[] values )
    {
        this( Arrays.asList( values ) );
    }
    
    public EvaluatedVarArgs( List<MtsValue> values )
    {
        _values = values;
    }
    
    public EvaluatedVarArgs( MtsValue first, EvaluatedVarArgs rest )
    {
        ArrayList<MtsValue> values = Lists.newArrayListWithCapacity( rest.count() + 1 );
        values.add( first );
        values.addAll( rest._values );
        
        _values = values;
    }
    
    // ========================================
    
    @Override
    public EvaluatedVarArgs subArgs( int start, int end )
    {
        if ( _values.size() < start )
            return EMPTY_VARARGS;
        
        end = Math.min( end, _values.size() );
        if ( end <= start )
            return EMPTY_VARARGS;
        
        return new EvaluatedVarArgs( _values.subList( start, end ) );
    }
    
    // ========================================
    
    /**
     * Returns the value at index <code>i</code> (zero based) or <code>nil</code>, if the index does not exist.
     */
    @Override
    public MtsValue get( int i )
    {
        if ( ( i < 0 ) || ( count() <= i ) )
            return NIL;
        
        return _values.get( i );
    }
    
    @Override
    public int count()
    {
        return _values.size();
    }
    
    @Override
    public boolean isEmpty()
    {
        return _values.isEmpty();
    }
    
    // ========================================
    
    @Override
    public MtsValue[] toArray()
    {
        return (MtsValue[]) _values.toArray();
    }
    
    // ========================================
    
    @Override
    public Iterator<MtsValue> iterator()
    {
        return _values.iterator();
    }
    
    // ========================================
    
    @Override
    public String toString()
    {
        return super.toString() + _values.toString();
    }
}
