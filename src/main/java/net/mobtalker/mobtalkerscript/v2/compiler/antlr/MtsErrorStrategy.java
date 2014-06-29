package net.mobtalker.mobtalkerscript.v2.compiler.antlr;

import org.antlr.v4.runtime.*;

public class MtsErrorStrategy extends BailErrorStrategy
{
    
    @Override
    public Token recoverInline( Parser recognizer ) throws RecognitionException
    {
        InputMismatchException ex = new InputMismatchException( recognizer );
        for ( ParserRuleContext context = recognizer.getContext(); context != null; context = context.getParent() )
        {
            context.exception = ex;
        }
        
        throw ex;
    }
    
}
