/* Copyright (C) 2013-2019 Chimaine, MobTalker2
 *
 * All Rights Reserved */
package net.mobtalker.mobtalkerscript.v3.compiler.antlr;

import net.mobtalker.mobtalkerscript.v3.compiler.MtsSyntaxError;
import net.mobtalker.mobtalkerscript.v3.compiler.antlr.generated.Mts3Lexer;
import org.antlr.v4.runtime.ANTLRErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;

import java.nio.file.Paths;

public class MtsLexerErrorListener
        implements ANTLRErrorListener<Integer>
{
    @Override
    public <T extends Integer> void syntaxError( Recognizer<T, ?> recognizer, T offendingSymbol,
                                                 int line, int charPositionInLine,
                                                 String msg, RecognitionException e )
    {
        throw transformSyntaxError( (Mts3Lexer) recognizer, line, charPositionInLine, msg );
    }
    
    // ========================================
    
    private static MtsSyntaxError transformSyntaxError( Mts3Lexer recognizer,
                                                        int line, int charPositionInLine, String msg )
    {
        return new MtsSyntaxError( Paths.get( recognizer.getSourceName() ).getFileName().toString(),
                                   line, charPositionInLine, msg );
    }
}
