package mobtalkerscript.mts.v2.parser;

import mobtalkerscript.mts.v2.parser.MtsParser.*;

import org.antlr.v4.runtime.*;

public class MtsCommandRewriter extends MtsBaseVisitor<Void>
{
    
    @Override
    public Void visitCommandSay( CommandSayContext commandCtx )
    {
        CallExprContext callCtx = new CallExprContext();
        callCtx.parent = commandCtx.parent;
        
        FunctionCallContext fCallCtx = new FunctionCallContext( callCtx );
        
        CommonToken fiToken = new CommonToken( MtsParser.Identifier );
        fiToken.setText( "DisplayText" );
        
        fCallCtx.FunctionName = fiToken;
        
        // Character Arg
        if ( commandCtx.Character == null )
        {
            ExprContext exprCtx = new ExprContext();
            exprCtx.parent = fCallCtx;
            
            LiteralExprContext litExprCtx = new LiteralExprContext();
            litExprCtx.parent = exprCtx;
            
            NullLiteralContext nullCtx = new NullLiteralContext( litExprCtx );
            litExprCtx.addChild( nullCtx );
            
            fCallCtx.addChild( exprCtx );
        }
        else
        {
            fCallCtx.addChild( commandCtx.Character );
        }
        
        // Text Arg
        fCallCtx.addChild( commandCtx.Text );
        
        fCallCtx.ArgumentExprs = fCallCtx.getRuleContexts( ExprContext.class );
        
        return null;
    }
    
}
