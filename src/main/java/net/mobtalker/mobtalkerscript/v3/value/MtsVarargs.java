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

public abstract class MtsVarargs extends MtsValue implements Iterable<MtsValue>
{
    public static final MtsVarargs Empty = new EmptyVarargs();
    
    // ========================================
    
    public static MtsVarargs of( MtsValue value )
    {
        return new SingletonVarargs( value );
    }
    
    public static MtsVarargs of( MtsValue... values )
    {
        return new EvaluatedVarargs( Arrays.asList( values ) );
    }
    
    public static MtsVarargs of( List<MtsValue> values )
    {
        return new EvaluatedVarargs( values );
    }
    
    public static MtsVarargs of( MtsValue first, MtsVarargs rest )
    {
        return new EvaluatedVarargs( first, rest );
    }
    
    public static MtsVarargs of( MtsValue[] values, MtsVarargs tail )
    {
        return new VarargsWithTail( values, tail );
    }
    
    // ========================================
    
    public MtsVarargs subArgs( int start )
    {
        return subArgs( start, count() );
    }
    
    public abstract MtsVarargs subArgs( int start, int end );
    
    // ========================================
    
    /**
     * Returns the value at index <code>i</code> (zero based) or <code>nil</code>, if the index does not exist.
     */
    @Override
    public abstract MtsValue get( int i );
    
    public abstract int count();
    
    public abstract boolean isEmpty();
    
    // ========================================
    
    public abstract MtsValue[] toArray();
    
    // ========================================
    
    @Override
    public boolean isVarArgs()
    {
        return true;
    }
    
    @Override
    public MtsVarargs asVarArgs()
    {
        return this;
    }
    
    @Override
    public MtsType getType()
    {
        return MtsType.VARARGS;
    }
    
    // ========================================
    
    @Override
    public String toString()
    {
        return getType().getName();
    }
}
