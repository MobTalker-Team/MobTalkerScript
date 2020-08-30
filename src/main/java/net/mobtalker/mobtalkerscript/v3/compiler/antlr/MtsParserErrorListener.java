/*
 * SPDX-FileCopyrightText: 2013-2020 Chimaine, MobTalkerScript contributors
 *
 * SPDX-License-Identifier: LGPL-3.0-or-later
 */
package net.mobtalker.mobtalkerscript.v3.compiler.antlr;

import net.mobtalker.mobtalkerscript.v3.compiler.MtsSyntaxError;
import net.mobtalker.mobtalkerscript.v3.compiler.antlr.generated.Mts3Parser;
import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.Token;

import java.nio.file.Paths;

public class MtsParserErrorListener
        extends BaseErrorListener
{
    @Override
    public <T extends Token> void syntaxError( Recognizer<T, ?> recognizer, T offendingSymbol,
                                               int line, int charPositionInLine,
                                               String msg, RecognitionException e )
    {
        throw transformSyntaxError( (Mts3Parser) recognizer, (Token) offendingSymbol,
                                    line, charPositionInLine, msg );
    }
    
    // ========================================
    
    private static MtsSyntaxError transformSyntaxError( Mts3Parser recognizer, Token offendingToken,
                                                        int line, int charPositionInLine, String msg )
    {
        // TODO: Make dead code errors more obvious.
        
        return new MtsSyntaxError( Paths.get( offendingToken.getTokenSource().getSourceName() ).getFileName().toString(),
                                   line, charPositionInLine, msg );
    }
}
