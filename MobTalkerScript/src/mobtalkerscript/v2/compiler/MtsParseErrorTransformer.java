package mobtalkerscript.v2.compiler;

import java.util.regex.*;

import mobtalkerscript.v2.compiler.antlr.*;

import org.antlr.v4.runtime.*;

/**
 */
public class MtsParseErrorTransformer
{
    
    // ========================================
    
    private static final Pattern missingPattern = Pattern.compile( "missing " //
                                                                   + "'(.+?)'"
                                                                   + " at "
                                                                   + "'(.+?)'" );
    private static final Pattern mismatchPattern = Pattern.compile( "mismatched input " //
                                                                    + "'(.+?)'"
                                                                    + " expecting "
                                                                    + "'(.+?)'" );
    private static final Pattern noAlternativePattern = Pattern.compile( "no viable alternative at input " //
                                                                         + "'(.+?)'" );
    
    // ========================================
    
    public static MtsSyntaxError transformSyntaxError( MtsParser parser,
                                                       Token offendingToken,
                                                       int line,
                                                       int charPositionInLine,
                                                       String msg )
    {
        Matcher missingMatcher = missingPattern.matcher( msg );
        if ( missingMatcher.matches() && missingMatcher.group( 1 ).equals( ";" ) )
            return new MtsSyntaxError( offendingToken.getTokenSource().getSourceName(),
                                       line,
                                       charPositionInLine,
                                       "missing ';'" );
        
        Matcher mismatchMatcher = mismatchPattern.matcher( msg );
        if ( mismatchMatcher.matches() && mismatchMatcher.group( 2 ).equals( ";" ) )
            return new MtsSyntaxError( offendingToken.getTokenSource().getSourceName(),
                                       line,
                                       charPositionInLine,
                                       "missing ';'" );
        
        Matcher noAlternativeMatcher = noAlternativePattern.matcher( msg );
        if ( noAlternativeMatcher.matches() && noAlternativeMatcher.group( 1 ).startsWith( "if" ) )
            return new MtsSyntaxError( offendingToken.getTokenSource().getSourceName(),
                                       line,
                                       charPositionInLine,
                                       msg + ", are you missing a 'then'?" );
        
        if ( "=".equals( offendingToken.getText() ) )
            return new MtsSyntaxError( offendingToken.getTokenSource().getSourceName(),
                                       line,
                                       charPositionInLine,
                                       "tried to assign to a function or label, are you missing an '$'?" );
        
        return new MtsSyntaxError( offendingToken.getTokenSource().getSourceName(),
                                   line,
                                   charPositionInLine,
                                   msg );
    }
}