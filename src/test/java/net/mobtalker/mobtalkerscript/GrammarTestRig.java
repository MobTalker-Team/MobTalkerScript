package net.mobtalker.mobtalkerscript;

import java.io.*;
import java.nio.file.*;
import java.util.Set;
import java.util.logging.*;

import net.mobtalker.mobtalkerscript.util.logging.MtsLog;
import net.mobtalker.mobtalkerscript.v3.*;
import net.mobtalker.mobtalkerscript.v3.compiler.*;
import net.mobtalker.mobtalkerscript.v3.compiler.antlr.*;
import net.mobtalker.mobtalkerscript.v3.compiler.antlr.generated.*;
import net.mobtalker.mobtalkerscript.v3.compiler.antlr.generated.Mts3Parser.EmptyStmtContext;
import net.mobtalker.mobtalkerscript.v3.value.MtsClosure;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.PredictionMode;
import org.antlr.v4.runtime.tree.*;

import com.google.common.base.Charsets;
import com.google.common.collect.ImmutableSet;

public class GrammarTestRig
{
    private static Mts3Parser getParser( CharStream stream )
    {
        Mts3Lexer lexer = new Mts3Lexer( stream );
        
        Mts3Parser parser = new Mts3Parser( new UnbufferedTokenStream( lexer, 100 ) );
        parser.removeErrorListeners();
        parser.addErrorListener( new MtsAntlrErrorListener() );
        parser.setErrorHandler( new MtsErrorStrategy() );
        parser.getInterpreter().setPredictionMode( PredictionMode.SLL );
        
        return parser;
    }
    
    public static void main( String[] args ) throws Exception
    {
        String path;
        if ( ( args != null ) && ( args.length > 0 ) )
        {
            path = args[0];
        }
        else
        {
            System.out.print( "Enter file path: " );
            
            BufferedReader reader = new BufferedReader( new InputStreamReader( System.in ) );
            path = reader.readLine();
        }
        
        Mts3Parser parser = getParser( new ANTLRFileStream( path ) );
        Mts3Parser.ChunkContext chunk;
        try
        {
            chunk = parser.chunk();
        }
        catch ( MtsSyntaxError ex )
        {
            throw new MtsSyntaxError( ex.getSourceName(), ex.getSourcePosition(), ex.getOriginalMessage() );
        }
        
        MtsLog.setLogger( Logger.getLogger( "MTS" ), true );
        MtsLog.EngineLog.setLevel( Level.ALL );
        
//        new LuaBaseListener()
//        {
//            @Override
//            public void enterNameAndArgs( NameAndArgsContext ctx )
//            {
//                for ( ParserRuleContext parent = ctx; ( parent != null ) && !( parent instanceof BlockContext ); parent = parent.getParent() )
//                {
//                    System.out.print( parent.getClass().getSimpleName().replace( "Context", "" ) + " -> " );
//                }
//                System.out.println();
//            }
//
//            @Override
//            public void visitTerminal( TerminalNode node )
//            {
////                System.out.println( node.getText() );
//            }
//        }.visit( chunk );
        
        new TreeCleaner().visit( chunk );
        chunk.inspect( parser );
        
        MtsCompiler compiler = new MtsCompiler( path, 0, 0 );
        compiler.visit( chunk );
        MtsFunctionPrototype prototype = compiler.compile();
        
        try (
            Writer writer = Files.newBufferedWriter( Paths.get( "D:\\test" ), Charsets.UTF_8,
                                                     StandardOpenOption.CREATE,
                                                     StandardOpenOption.TRUNCATE_EXISTING ) )
        {
            new FunctionTextWriter().write( prototype, writer );
        }
        
        new MtsClosure( prototype, new MtsGlobals() ).call();
    }
    
    /**
     * Removes some clutter from the tree for nicer display
     */
    private static final class TreeCleaner extends Mts3BaseListener
    {
        private static final Set<Integer> IgnoredTokens = ImmutableSet.of( Mts3Lexer.SEMICOLON,
                                                                           Mts3Lexer.COMMA,
                                                                           Mts3Lexer.COLON,
                                                                           Mts3Lexer.DOT,
                                                                           Mts3Lexer.CURLY_OPEN,
                                                                           Mts3Lexer.CURLY_CLOSE,
                                                                           Mts3Lexer.ROUND_OPEN,
                                                                           Mts3Lexer.ROUND_CLOSE,
                                                                           Mts3Lexer.SQUARE_OPEN,
                                                                           Mts3Lexer.SQUARE_CLOSE );
        
        @Override
        public void visitChildren( RuleNode node )
        {
            int n = node.getChildCount();
            for ( int i = 0; i < n; i++ )
            {
                ParseTree c = node.getChild( i );
                if ( node instanceof ParserRuleContext )
                {
                    if ( ( ( c instanceof TerminalNode ) && shouldRemoveToken( (TerminalNode) c ) )
                         || ( c instanceof EmptyStmtContext ) )
                    {
                        ( (ParserRuleContext) node ).children.remove( i-- );
                        n--;
                        continue;
                    }
                }
                
                visit( c );
            }
        }
        
        private boolean shouldRemoveToken( TerminalNode c )
        {
            return IgnoredTokens.contains( c.getSymbol().getType() );
        }
    }
}
