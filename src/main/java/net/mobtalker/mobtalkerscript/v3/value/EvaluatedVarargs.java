/*
 * SPDX-FileCopyrightText: 2013-2020 Chimaine, MobTalkerScript contributors
 *
 * SPDX-License-Identifier: LGPL-3.0-or-later
 */
package net.mobtalker.mobtalkerscript.v3.value;

import java.util.*;

/* package */class EvaluatedVarargs extends MtsVarargs
{
    private final List<MtsValue> _values;
    
    // ========================================
    
    /* package */EvaluatedVarargs( MtsValue[] values )
    {
        this( Arrays.asList( values ) );
    }
    
    /* package */EvaluatedVarargs( List<MtsValue> values )
    {
        _values = values;
    }
    
    /* package */EvaluatedVarargs( MtsValue first, MtsVarargs rest )
    {
        List<MtsValue> values = new ArrayList<>( rest.count() + 1 );
        values.add( first );
        
        if ( rest instanceof EvaluatedVarargs )
        {
            values.addAll( ( (EvaluatedVarargs) rest )._values );
        }
        else if ( rest.count() > 0 )
        {
            for ( MtsValue value : rest )
            {
                values.add( value );
            }
        }
        
        _values = values;
    }
    
    // ========================================
    
    @Override
    public MtsVarargs subArgs( int start, int end )
    {
        if ( _values.size() < start )
            return Empty;
        
        end = Math.min( end, _values.size() );
        if ( end <= start )
            return Empty;
        
        return new EvaluatedVarargs( _values.subList( start, end ) );
    }
    
    // ========================================
    
    /**
     * Returns the value at index <code>i</code> (zero based) or <code>nil</code>, if the index does not exist.
     */
    @Override
    public MtsValue get( int i )
    {
        if ( ( i < 0 ) || ( count() <= i ) )
            return Nil;
        
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
