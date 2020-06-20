/*
 * Copyright (C) 2013-2020 Chimaine, MobTalkerScript contributors
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
