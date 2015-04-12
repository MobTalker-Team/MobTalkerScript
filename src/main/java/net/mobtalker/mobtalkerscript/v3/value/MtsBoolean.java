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

public class MtsBoolean extends MtsValue
{
    public static final MtsBoolean True = new MtsBoolean( true );
    public static final MtsBoolean False = new MtsBoolean( false );

    // ========================================

    public static MtsBoolean of( boolean b )
    {
        return b ? True : False;
    }

    // ========================================

    public static MtsBoolean parse( String s )
    {
        return of( Boolean.parseBoolean( s ) );
    }

    public static MtsBoolean parse( MtsString s )
    {
        return parse( s.toJava() );
    }

    public static MtsBoolean parse( MtsNumber n )
    {
        int i = (int) n.toJavaDouble();
        return of( i != 0.0D );
    }

    // ========================================

    private final boolean _value;
    private final Boolean _obj;

    private final String _string;
    private final MtsString _stringValue;

    // ========================================

    /* package */MtsBoolean( boolean value )
    {
        _value = value;
        _obj = Boolean.valueOf( value );

        _string = Boolean.toString( value );
        _stringValue = MtsString.of( _string );
    }

    // ========================================

    public MtsBoolean or( MtsBoolean other )
    {
        return of( _value || other._value );
    }

    public MtsBoolean and( MtsBoolean other )
    {
        return of( _value && other._value );
    }

    public MtsBoolean not()
    {
        return of( !_value );
    }

    // ========================================

    @Override
    public MtsBoolean asBoolean()
    {
        return this;
    }

    // ========================================

    @Override
    public boolean isBoolean()
    {
        return true;
    }

    @Override
    public MtsType getType()
    {
        return MtsType.BOOLEAN;
    }

    // ========================================

    @Override
    public MtsString toMtsString()
    {
        return _stringValue;
    }

    @Override
    public MtsBoolean toMtsBoolean()
    {
        return this;
    }

    // ========================================

    @Override
    public boolean isTrue()
    {
        return _value;
    }

    @Override
    public Boolean toJava()
    {
        return _obj;
    }

    @Override
    public String toString()
    {
        return _string;
    }

    @Override
    public int hashCode()
    {
        return _obj.hashCode();
    }

    @Override
    public boolean equals( Object o )
    {
        return o == this;
    }
}
