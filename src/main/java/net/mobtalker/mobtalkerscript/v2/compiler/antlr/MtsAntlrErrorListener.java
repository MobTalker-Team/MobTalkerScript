/*
 * Copyright (C) 2013-2014 Chimaine
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package net.mobtalker.mobtalkerscript.v2.compiler.antlr;

import java.nio.file.Paths;

import net.mobtalker.mobtalkerscript.v2.compiler.MtsSyntaxError;

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
