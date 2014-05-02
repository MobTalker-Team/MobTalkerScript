package mobtalkerscript.mts.v2.compiler;

import java.util.*;

import org.antlr.v4.runtime.*;

import com.google.common.collect.*;

public class TreeMultiPattern implements ITreePattern
{
    private final List<ITreePattern> _patterns;
    
    // ========================================
    
    public TreeMultiPattern( ITreePattern... patterns )
    {
        _patterns = Lists.newArrayList( patterns );
    }
    
    // ========================================
    
    @Override
    public boolean matches( ParserRuleContext ctx )
    {
        for ( ITreePattern pattern : _patterns )
        {
            if ( pattern.matches( ctx ) )
                return true;
        }
        
        return false;
    }
    
    @Override
    public <T extends ParserRuleContext> T get( ParserRuleContext ctx )
    {
        throw new UnsupportedOperationException();
    }
}
