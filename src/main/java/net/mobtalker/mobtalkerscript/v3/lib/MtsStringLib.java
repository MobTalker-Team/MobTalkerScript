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
package net.mobtalker.mobtalkerscript.v3.lib;

import static net.mobtalker.mobtalkerscript.v3.MtsCheck.*;
import static net.mobtalker.mobtalkerscript.v3.value.MtsValue.*;

import java.util.regex.*;

import net.mobtalker.mobtalkerscript.v3.value.*;
import net.mobtalker.mobtalkerscript.v3.value.userdata.MtsNativeFunction;

import org.apache.commons.lang3.StringUtils;

public class MtsStringLib
{
    @MtsNativeFunction
    public static MtsValue find( MtsValue argString, MtsValue argPattern, MtsValue argStart, MtsValue argPlain )
    {
        String s = checkString( argString, 0 );
        String patternStr = checkString( argPattern, 1 );
        int start = checkInteger( argStart, 2, 1 );
        boolean plain = argPlain.isTrue();
        
        if ( start > -1 )
        {
            start--;
        }
        
        Pattern pattern = Pattern.compile( patternStr, plain ? Pattern.LITERAL : 0 );
        Matcher matcher = pattern.matcher( s );
        
        if ( matcher.find( start ) )
        {
            return MtsVarargs.of( MtsNumber.of( matcher.start() ),
                                  MtsNumber.of( matcher.end() ) );
        }
        else
        {
            return Nil;
        }
    }
    
    @MtsNativeFunction
    public static MtsString join( MtsVarargs args )
    {
        if ( ( args.isNil() ) || ( args.count() < 2 ) )
            return MtsString.Empty;
        
        if ( args.count() == 2 )
            return args.get( 1 ).toMtsString();
        
        String sep = args.get( 0 ).toMtsString().toJava();
        
        StringBuilder sb = new StringBuilder();
        
        int limit = args.count() - 1;
        for ( int i = 1; i < limit; i++ )
        {
            MtsValue value = args.get( i );
            sb.append( value.toMtsString().toJava() )
              .append( sep );
        }
        sb.append( args.get( limit ).toMtsString().toJava() );
        
        return MtsString.of( sb.toString() );
    }
    
    @MtsNativeFunction
    public static MtsString format( MtsVarargs args )
    {
        String s = checkString( args, 0 );
        
        Object[] format = new Object[args.count() - 1];
        for ( int i = 0; i < format.length; i++ )
        {
            format[i] = args.get( i + 1 ).toJava();
        }
        
        return MtsString.of( String.format( s, format ) );
    }
    
    @MtsNativeFunction
    public static MtsString lower( MtsValue argString )
    {
        return MtsString.of( checkString( argString, 0 ).toLowerCase() );
    }
    
    @MtsNativeFunction
    public static MtsFunction match( MtsValue argString, MtsValue argPattern )
    {
        String s = checkString( argString, 0 );
        String patternStr = checkString( argPattern, 1 );
        
        Pattern pattern = Pattern.compile( patternStr );
        Matcher matcher = pattern.matcher( s );
        
        return new MatchIterator( matcher, 0 );
    }
    
    @MtsNativeFunction
    public static MtsString padLeft( MtsValue argString, MtsValue argFill, MtsValue argLength )
    {
        return MtsString.of( StringUtils.leftPad( checkString( argString, 0 ),
                                                  checkInteger( argLength, 2 ),
                                                  checkString( argFill, 1 ) ) );
    }
    
    @MtsNativeFunction
    public static MtsString padRight( MtsValue argString, MtsValue argFill, MtsValue argLength )
    {
        return MtsString.of( StringUtils.rightPad( checkString( argString, 0 ),
                                                   checkInteger( argLength, 2 ),
                                                   checkString( argFill, 1 ) ) );
    }
    
    @MtsNativeFunction
    public static MtsString repeat( MtsValue argString, MtsValue argTimes, MtsValue argSeparator )
    {
        return MtsString.of( StringUtils.repeat( checkString( argString, 0 ),
                                                 checkString( argSeparator, 2, "" ),
                                                 checkIntegerWithMinimum( argTimes, 1, 0 ) ) );
    }
    
    @MtsNativeFunction
    public static MtsVarargs replace( MtsValue argString, MtsValue argPattern, MtsValue argReplacement, MtsValue argN )
    {
        String s = checkString( argString, 0 );
        String patternStr = checkString( argPattern, 1 );
        String replacementStr = checkString( argReplacement, 2 ); // TODO Can be table or function
        int maxN = checkInteger( argN, 3, Integer.MAX_VALUE );
        
        Pattern pattern = Pattern.compile( patternStr );
        Matcher matcher = pattern.matcher( s );
        
        boolean result = matcher.find();
        if ( !result )
            return MtsVarargs.of( argString, MtsNumber.Zero );
        
        int count = 0;
        StringBuffer sb = new StringBuffer();
        do
        {
            count++;
            matcher.appendReplacement( sb, replacementStr );
            result = matcher.find();
        }
        while ( result && ( count < maxN ) );
        matcher.appendTail( sb );
        
        return MtsVarargs.of( MtsString.of( sb.toString() ), MtsNumber.of( count ) );
    }
    
    @MtsNativeFunction
    public static MtsString reverse( MtsValue argString )
    {
        return MtsString.of( StringUtils.reverse( checkString( argString, 0 ) ) );
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
            return MtsString.of( StringUtils.substring( s, from ) );
        else
            return MtsString.of( StringUtils.substring( s, from, to ) );
        
    }
    
    @MtsNativeFunction
    public static MtsString upper( MtsValue argString )
    {
        return MtsString.of( checkString( argString, 0 ).toUpperCase() );
    }
    
    // ========================================
    
    private static final class MatchIterator extends MtsFunction
    {
        private final Matcher _matcher;
        private int _start;
        
        // ========================================
        
        public MatchIterator( Matcher matcher, int start )
        {
            _matcher = matcher;
            _start = start;
        }
        
        // ========================================
        
        @Override
        public MtsVarargs call( MtsVarargs args )
        {
            if ( !_matcher.find( _start++ ) )
                return MtsVarargs.Empty;
            
            if ( _matcher.groupCount() == 0 )
                return MtsVarargs.of( MtsString.of( _matcher.group() ) );
            
            MtsString[] groups = new MtsString[_matcher.groupCount() - 1];
            for ( int i = 0; i < groups.length; ++i )
            {
                groups[i] = MtsString.of( _matcher.group( i + 1 ) );
            }
            
            return MtsVarargs.of( groups );
        }
    }
}
