/*
 * SPDX-FileCopyrightText: 2013-2020 Chimaine, MobTalkerScript contributors
 *
 * SPDX-License-Identifier: LGPL-3.0-or-later
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
        while ( ( i > 0 ) && ( s.charAt( i - 1 ) == '\\' ) );
        
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
