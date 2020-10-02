/*
 * SPDX-FileCopyrightText: 2013-2020 Chimaine, MobTalkerScript contributors
 *
 * SPDX-License-Identifier: LGPL-3.0-or-later
 */
package net.mobtalker.mobtalkerscript.v3.value;

import java.util.Collections;
import java.util.Iterator;

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
        return Collections.singleton(_value).iterator();
    }
    
    // ========================================
    
    @Override
    public String toString()
    {
        return super.toString() + '[' + _value.toString() + ']';
    }
}
