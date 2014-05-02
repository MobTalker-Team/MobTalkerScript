package mobtalkerscript.mts.v2.compiler.antlr;

import java.nio.file.*;

import mobtalkerscript.mts.v2.compiler.*;

import org.antlr.v4.runtime.*;

public class MtsAntlrErrorListener extends BaseErrorListener
{
    @Override
    public void syntaxError( Recognizer<?, ?> recognizer,
                             Object offendingSymbol,
                             int line,
                             int charPositionInLine,
                             String msg,
                             RecognitionException e )
    {
        throw transformSyntaxError( (MtsParser) recognizer,
                                    (Token) offendingSymbol,
                                    line,
                                    charPositionInLine,
                                    msg );
    }
    
    // ========================================
    
//    private static final Pattern missingPattern = Pattern.compile( "missing " //
//                                                                   + "'(.+?)'"
//                                                                   + " at "
//                                                                   + "'(.+?)'" );
//    private static final Pattern mismatchPattern = Pattern.compile( "mismatched input " //
//                                                                    + "'(.+?)'"
//                                                                    + " expecting "
//                                                                    + "'(.+?)'" );
//    private static final Pattern noAlternativePattern = Pattern.compile( "no viable alternative at input " //
//                                                                         + "'(.+?)'" );
    
    // ========================================
    
    private static MtsSyntaxError transformSyntaxError( MtsParser recognizer,
                                                        Token offendingToken,
                                                        int line,
                                                        int charPositionInLine,
                                                        String msg )
    {
//        Matcher missingMatcher = missingPattern.matcher( msg );
//        if ( missingMatcher.matches() && missingMatcher.group( 1 ).equals( ";" ) )
//            return new MtsSyntaxError( offendingToken.getTokenSource().getSourceName(),
//                                       line,
//                                       charPositionInLine,
//                                       "missing ';'" );
//        
//        Matcher mismatchMatcher = mismatchPattern.matcher( msg );
//        if ( mismatchMatcher.matches() && mismatchMatcher.group( 2 ).equals( ";" ) )
//            return new MtsSyntaxError( offendingToken.getTokenSource().getSourceName(),
//                                       line,
//                                       charPositionInLine,
//                                       "missing ';'" );
//        
//        Matcher noAlternativeMatcher = noAlternativePattern.matcher( msg );
//        if ( noAlternativeMatcher.matches() && noAlternativeMatcher.group( 1 ).startsWith( "if" ) )
//            return new MtsSyntaxError( offendingToken.getTokenSource().getSourceName(),
//                                       line,
//                                       charPositionInLine,
//                                       msg + ", are you missing a 'then'?" );
//        
//        if ( "=".equals( offendingToken.getText() ) )
//            return new MtsSyntaxError( offendingToken.getTokenSource().getSourceName(),
//                                       line,
//                                       charPositionInLine,
//                                       "tried to assign to a function or label, are you missing an '$'?" );
        
        String source = Paths.get( offendingToken.getTokenSource().getSourceName() ).getFileName().toString();
        
        return new MtsSyntaxError( source,
                                   line,
                                   charPositionInLine,
                                   msg );
    }
}
