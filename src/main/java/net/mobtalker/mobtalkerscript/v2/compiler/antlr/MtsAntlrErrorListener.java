package mobtalkerscript.v2.compiler.antlr;

import java.nio.file.*;

import mobtalkerscript.v2.compiler.*;

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
    
    private static MtsSyntaxError transformSyntaxError( MtsParser recognizer,
                                                        Token offendingToken,
                                                        int line,
                                                        int charPositionInLine,
                                                        String msg )
    {
        String source = Paths.get( offendingToken.getTokenSource().getSourceName() ).getFileName().toString();
//        String source = Paths.get( offendingToken.getTokenSource().getSourceName() ).toString();
        
        return new MtsSyntaxError( source,
                                   line,
                                   charPositionInLine,
                                   msg );
    }
}
