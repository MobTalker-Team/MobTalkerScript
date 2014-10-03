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
package net.mobtalker.mobtalkerscript.v2.lib;

import static net.mobtalker.mobtalkerscript.v2.MtsCheck.*;
import static net.mobtalker.mobtalkerscript.v2.value.MtsValue.*;

import java.util.regex.*;

import net.mobtalker.mobtalkerscript.v2.value.*;
import net.mobtalker.mobtalkerscript.v2.value.userdata.MtsNativeFunction;

import org.apache.commons.lang3.StringUtils;

public class MtsStringLib
{
    @MtsNativeFunction
    public static MtsValue find( MtsValue argString, MtsValue argPattern, MtsValue argStart, MtsValue argPlain )
    {
        String s = checkString( argString, 0 );
        String patternStr = checkString( argPattern, 1 );
        int start = checkInteger( argStart, 2, 1 );
        boolean plain = isTrue( argPlain );
        
        if ( start > -1 )
        {
            start--;
        }
        
        Pattern pattern = Pattern.compile( patternStr, plain ? Pattern.LITERAL : 0 );
        Matcher matcher = pattern.matcher( s );
        
        if ( matcher.find( start ) )
        {
            return MtsVarArgs.of( valueOf( matcher.start() ),
                                  valueOf( matcher.end() ) );
        }
        else
        {
            return NIL;
        }
    }
    
    @MtsNativeFunction
    public static MtsString format( MtsVarArgs args )
    {
        String s = checkString( args, 0 );
        MtsValue[] argsFormat = args.subArgs( 1 ).toArray();
        
        Object[] format = new Object[argsFormat.length];
        for ( int i = 0; i < argsFormat.length; i++ )
        {
            format[i] = argsFormat[i].toJava();
        }
        
        return valueOf( String.format( s, format ) );
    }
    
    @MtsNativeFunction
    public static MtsString lower( MtsValue argString )
    {
        return valueOf( checkString( argString, 0 ).toLowerCase() );
    }
    
    @MtsNativeFunction
    public static MtsString reverse( MtsValue argString )
    {
        return valueOf( StringUtils.reverse( checkString( argString, 0 ) ) );
    }
    
    @MtsNativeFunction
    public static MtsString sub( MtsValue argString, MtsValue argFrom, MtsValue argTo )
    {
        String s = checkString( argString, 0 );
        int from = checkInteger( argFrom, 1, 1 );
        int to = checkInteger( argTo, 2, -1 );
        
        if ( from > -1 )
        {
            from--;
        }
        
        if ( to == -1 )
            return valueOf( StringUtils.substring( s, from ) );
        else
            return valueOf( StringUtils.substring( s, from, to ) );

    }
    
    @MtsNativeFunction
    public static MtsString upper( MtsValue argString )
    {
        return valueOf( checkString( argString, 0 ).toUpperCase() );
    }
}
