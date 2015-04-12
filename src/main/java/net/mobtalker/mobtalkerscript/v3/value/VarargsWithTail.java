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

/* package */class VarargsWithTail extends MtsVarargs
{
    private final MtsValue[] _values;
    private final MtsVarargs _tail;

    // ========================================

    /* package */VarargsWithTail( MtsValue[] values, MtsVarargs tail )
    {
        _values = values;
        _tail = tail;
    }

    // ========================================

    @Override
    public int count()
    {
        return _values.length + _tail.count();
    }

    @Override
    public boolean isEmpty()
    {
        return ( _values.length == 0 ) && _tail.isEmpty();
    }

    @Override
    public MtsValue get( int i )
    {
        return i < _values.length ? _values[i] : _tail.get( i - _values.length );
    }

    @Override
    public MtsVarargs subArgs( int start, int end )
    {
        if ( count() < start )
            return Empty;

        end = Math.min( end, count() );
        if ( end <= start )
            return Empty;

        // TODO A bit naive approach. This could be optimized just a tiny bit.

        MtsValue[] values = new MtsValue[end - start];
        for ( int i = 0; i < values.length; ++i )
        {
            values[i] = get( start + i );
        }

        return MtsVarargs.of( values );
    }

    @Override
    public MtsValue[] toArray()
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<MtsValue> iterator()
    {
        throw new UnsupportedOperationException();
    }

}
