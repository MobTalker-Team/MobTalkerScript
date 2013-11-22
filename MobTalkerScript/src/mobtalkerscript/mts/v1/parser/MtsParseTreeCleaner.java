package mobtalkerscript.mts.v1.parser;

import java.util.*;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

import com.google.common.collect.*;

/**
 * Removes unnecessary elements like braces from the parse tree.
 * 
 * @author Tobsen
 */
public class MtsParseTreeCleaner extends MtsBaseVisitor<Void>
{
    
    private static final Set<String> _cleanableTokens = Sets.newHashSet( "(",
                                                                         ")",
                                                                         "[",
                                                                         "]",
                                                                         "{",
                                                                         "}",
                                                                         ".",
                                                                         ",",
                                                                         "=",
                                                                         "$",
                                                                         "<EOF>",
                                                                         "end",
                                                                         "label",
                                                                         "return",
                                                                         "option" );
    
    @Override
    public Void visit( ParseTree tree )
    {
        ParserRuleContext ctx = (ParserRuleContext) tree;
        
        for ( int i = 0; i < tree.getChildCount(); i++ )
        {
            ParseTree child = tree.getChild( i );
            
            if ( child instanceof TerminalNode )
            {
                TerminalNode node = (TerminalNode) child;
                
                if ( _cleanableTokens.contains( node.getText() ) )
                {
                    ctx.children.remove( i );
                    i--;
                }
            }
            else
            {
                visit( child );
            }
        }
        
        return super.visit( tree );
    }
}
