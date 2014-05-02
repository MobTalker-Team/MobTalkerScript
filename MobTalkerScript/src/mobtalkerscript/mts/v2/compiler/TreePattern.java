package mobtalkerscript.mts.v2.compiler;

import java.util.*;

import org.antlr.v4.runtime.*;

import com.google.common.collect.*;

public class TreePattern implements ITreePattern
{
    private final ArrayList<Class<? extends ParserRuleContext>> _pattern;
    
    // ========================================
    
    public TreePattern( Class<? extends ParserRuleContext>... pattern )
    {
        _pattern = Lists.newArrayList( pattern );
        _pattern.trimToSize();
    }
    
    // ========================================
    
    @Override
    public boolean matches( ParserRuleContext ctx )
    {
        ParserRuleContext cur = ctx.getParent();
        
        for ( Class<? extends ParserRuleContext> element : _pattern )
        {
            if ( ( cur == null ) || ( !element.isAssignableFrom( cur.getClass() ) ) )
            {
//                printFailureMsg( ctx );
                return false;
            }
            
            cur = cur.getParent();
        }
        
        return true;
    }
    
    @Override
    @SuppressWarnings( "unchecked" )
    public <T extends ParserRuleContext> T get( ParserRuleContext ctx )
    {
        ParserRuleContext result = ctx.getParent();
        for ( int i = 1; i < _pattern.size(); i++ )
        {
            result = ctx.getParent();
        }
        
        return (T) result;
    }
    
    // ========================================
    
    protected void printFailureMsg( ParserRuleContext ctx )
    {
        StringBuilder s = new StringBuilder( "Pattern [" );
        
        for ( int i = 0; i < _pattern.size(); i++ )
        {
            Class<? extends ParserRuleContext> clazz = _pattern.get( i );
            s.append( clazz.getSimpleName() );
            s.append( ( i < ( _pattern.size() - 1 ) ) ? ", " : "] did not match [" );
        }
        
        ParserRuleContext cur = ctx;
        for ( /* int i = 0 */;; /* i++ */)
        {
            s.append( cur.getClass().getSimpleName() );
            cur = cur.getParent();
            
            if ( ( cur != null ) /* && ( i < ( _pattern.size() - 1 ) ) */)
            {
                s.append( ", " );
            }
            else
            {
                s.append( "]" );
                break;
            }
        }
        
        System.out.println( s.toString() );
    }
    
}
