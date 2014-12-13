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
package net.mobtalker.mobtalkerscript.v3.compiler;

import java.util.regex.*;

import net.mobtalker.mobtalkerscript.v3.value.MtsString;

public class StringInterpolator
{
    /*
     * This has to be always the same as in the grammar file.
     */
    private static final Pattern _varNamePattern = Pattern.compile( "[a-zA-Z_][a-zA-Z_0-9]*" );
    
    // ========================================
    
    private final MtsCompiler c;
    
    // ========================================
    
    public StringInterpolator( MtsCompiler compiler )
    {
        c = compiler;
    }
    
    // ========================================
    
    public void interpolate( String s )
    {
        Matcher varNameMatcher = null;
        
        int nParts = 0;
        int start = 0;
        int end = 0;
        for ( ;; )
        {
            // Find next start token
            start = findStart( s, end );
            if ( start < 0 )
                break;
            
            // Split and load the string up to the token
            String part = s.substring( end, start );
            if ( part.length() > 0 )
            {
                nParts++;
                c.loadConstant( MtsString.of( part ) );
            }
            
            nParts++;
            
            // What kind of inline expression do we have?
            if ( s.charAt( start + 1 ) == '{' )
            {
                start = start + 2;
                end = findEnd( s, start );
                if ( end < 0 )
                    throw new MtsSyntaxError( c.getSourceName(),
                                              c.getSourcePosition().offsetColoum( start ),
                                              "expected '}'" );
                
                c.loadStringExpression( s.substring( start, end ) );
            }
            else
            {
                start = start + 1;
                if ( varNameMatcher == null )
                    varNameMatcher = _varNamePattern.matcher( s );
                if ( !varNameMatcher.find( start ) )
                    throw new MtsSyntaxError( c.getSourceName(),
                                              c.getSourcePosition().offsetColoum( start ),
                                              "expected identifier" );
                end = varNameMatcher.end();
                
                c.loadVariable( s.substring( start, end ) );
            }
        }
        
        if ( end == 0 )
        {
            c.loadConstant( MtsString.of( s ) );
        }
        else if ( end < s.length() )
        {
            c.loadConstant( MtsString.of( s.substring( end ) ) );
            c.concatStrings( nParts + 1 );
        }
        else
        {
            c.concatStrings( nParts );
        }
    }
    
    private static int findStart( String s, int from )
    {
        int i;
        do
        {
            i = s.indexOf( '$', from );
        }
        while ( ( i > 0 ) && ( s.charAt( i - 1 ) != '\\' ) );
        
        return i;
    }
    
    private static int findEnd( String s, int from )
    {
        int i;
        do
        {
            i = s.indexOf( '}', from );
        }
        while ( ( i > 0 ) && ( s.charAt( i - 1 ) != '\\' ) );
        
        return i;
    }
    
}
