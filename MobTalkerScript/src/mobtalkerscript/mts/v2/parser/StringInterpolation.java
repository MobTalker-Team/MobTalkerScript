package mobtalkerscript.mts.v2.parser;

import java.util.regex.*;

import mobtalkerscript.misl.v1.instruction.*;
import mobtalkerscript.mts.v2.value.*;

import org.apache.commons.lang3.*;

public class StringInterpolation
{
    private static final Pattern _pattern = Pattern.compile( "(?<!\\\\)\\$([_a-zA-Z]+[_a-zA-Z0-9]*)" );
    
    // ========================================
    
    public static void interpolateString( MtsToMislCompiler compiler, String str )
    {
        if ( str.isEmpty() )
        {
            compiler.addInstr( new InstrPush( MtsString.EMPTY_STRING ) );
            return;
        }
        
        Matcher matcher = _pattern.matcher( str );
        int start = 0;
        int parts = 0;
        
        while ( matcher.find() )
        {
            if ( matcher.start() > 0 )
            {
                // Split string
                String subStr = unescapeStringLiteral( str.substring( start, matcher.start() ) );
                compiler.addInstr( new InstrPush( subStr ) );
                
                parts++;
            }
            
            // Load variable
            String varName = matcher.group( 1 );
            compiler.addInstr( new InstrLoad( varName ) );
            
            parts++;
            
            start = matcher.end();
        }
        
        if ( start < str.length() )
        {
            String subStr = unescapeStringLiteral( str.substring( start ) );
            compiler.addInstr( new InstrPush( subStr ) );
        }
        
        if ( parts > 1 )
        {
            compiler.addInstr( new InstrConcat( parts ) );
        }
    }
    
    private static String unescapeStringLiteral( String str )
    {
        return StringUtils.replaceEachRepeatedly( str, //
                                                  new String[] { "\\\\", "\\\"", "\\$" },
                                                  new String[] { "\\", "\"", "$" } );
    }
    
}
