package mobtalkerscript.mts.v2.compiler;

import java.util.*;

import mobtalkerscript.mts.v2.compiler.antlr.*;
import mobtalkerscript.mts.v2.compiler.antlr.MtsParser.CallArgsContext;

import org.antlr.v4.runtime.*;

import com.google.common.collect.*;

public class CallPattern
{
    private final ArrayList<Class<? extends ParserRuleContext>> _pattern;
    
    // ========================================
    
    public CallPattern( Class<? extends ParserRuleContext>... pattern )
    {
        _pattern = Lists.newArrayList( pattern );
        _pattern.trimToSize();
    }
    
    // ========================================
    
    public boolean matches( CallArgsContext ctx )
    {
        ParserRuleContext cur = ctx.getParent();
        
        for ( Class<? extends ParserRuleContext> element : _pattern )
        {
            if ( cur == null )
                return false;
            if ( !cur.getClass().equals( element ) )
                return false;
            
            cur = cur.getParent();
        }
        
        return true;
    }
    
    // ========================================
    
}
