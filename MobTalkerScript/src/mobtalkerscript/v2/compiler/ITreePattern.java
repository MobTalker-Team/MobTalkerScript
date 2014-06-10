package mobtalkerscript.v2.compiler;

import org.antlr.v4.runtime.*;

public interface ITreePattern
{
    
    boolean matches( ParserRuleContext ctx );
    
    <T extends ParserRuleContext> T get( ParserRuleContext ctx );
    
}