/*
 * SPDX-FileCopyrightText: 2013-2020 Chimaine, MobTalkerScript contributors
 *
 * SPDX-License-Identifier: LGPL-3.0-or-later
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
