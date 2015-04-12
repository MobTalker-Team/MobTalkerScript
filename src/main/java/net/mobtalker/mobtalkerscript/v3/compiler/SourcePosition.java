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
package net.mobtalker.mobtalkerscript.v3.compiler;

public class SourcePosition
{
    public static final SourcePosition ZERO = new SourcePosition( 0, 0 );

    // ========================================

    public final int Line;
    public final int Coloum;

    // ========================================

    public SourcePosition( int line, int coloum )
    {
        Line = line;
        Coloum = coloum;
    }

    // ========================================

    public SourcePosition offsetColoum( int offset )
    {
        return new SourcePosition( Line, Coloum + offset );
    }

    // ========================================

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = ( prime * result ) + Coloum;
        result = ( prime * result ) + Line;
        return result;
    }

    @Override
    public boolean equals( Object obj )
    {
        if ( this == obj )
            return true;
        if ( obj == null )
            return false;
        if ( !( obj instanceof SourcePosition ) )
            return false;
        SourcePosition other = (SourcePosition) obj;
        if ( Coloum != other.Coloum )
            return false;
        if ( Line != other.Line )
            return false;
        return true;
    }

    public boolean equals( int line, int col )
    {
        return ( Line == line ) && ( Coloum == col );
    }

    // ========================================

    @Override
    public String toString()
    {
        return Line + ":" + Coloum;
    }
}
