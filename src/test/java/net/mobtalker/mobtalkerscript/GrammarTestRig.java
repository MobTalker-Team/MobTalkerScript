package net.mobtalker.mobtalkerscript;

import java.io.OutputStream;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.logging.*;

import net.mobtalker.mobtalkerscript.util.logging.MtsLog;
import net.mobtalker.mobtalkerscript.v3.MtsFunctionPrototype;
import net.mobtalker.mobtalkerscript.v3.compiler.*;
import net.mobtalker.mobtalkerscript.v3.compiler.antlr.*;
import net.mobtalker.mobtalkerscript.v3.compiler.antlr.generated.*;
import net.mobtalker.mobtalkerscript.v3.compiler.antlr.generated.Mts3Parser.EmptyStmtContext;
import net.mobtalker.mobtalkerscript.v3.serialization.FunctionBinaryWriter;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.PredictionMode;
import org.antlr.v4.runtime.tree.*;

import com.google.common.base.Stopwatch;
import com.google.common.collect.ImmutableSet;

public class GrammarTestRig
{
    public static void main( String[] args ) throws Exception
    {
        MtsLog.setLogger( Logger.getLogger( "MTS" ), true );
        MtsLog.EngineLog.setLevel( Level.WARNING );
        
//        System.out.print( "Select test: " );
//
//        BufferedReader reader = new BufferedReader( new InputStreamReader( System.in ) );
//        String path = reader.readLine();
        String path = "constructs.lua";
        MtsFunctionPrototype prototype;
        
        List<Long> runtimes = new ArrayList<>( 100 );
        long min = Long.MAX_VALUE, max = Long.MIN_VALUE;
        for ( int i = 0; i < 10; i++ )
        {
            Stopwatch stopwatch = Stopwatch.createStarted();
            
//            Mts3Parser parser = getParser( new ANTLRFileStream( "src/test/resources/scripts/" + path ) );
            Mts3Parser parser = getParser( new ANTLRFileStream( "d:\\Users\\Tobsen\\Desktop\\lua-5.2.2-tests\\api.lua" ) );
            Mts3Parser.ChunkContext chunk;
            try
            {
                chunk = parser.chunk();
            }
            catch ( MtsSyntaxError ex )
            {
                throw new MtsSyntaxError( ex.getSourceName(), ex.getSourcePosition(), ex.getOriginalMessage() );
            }
            
            MtsCompiler compiler = new MtsCompiler( path, 0, 0 );
            compiler.visit( chunk );
            prototype = compiler.compile();
            
            try (
                OutputStream stream = Files.newOutputStream( Paths.get( "D:\\test2" ),
                                                             StandardOpenOption.CREATE,
                                                             StandardOpenOption.TRUNCATE_EXISTING ) )
            {
                new FunctionBinaryWriter().write( prototype, stream );
            }
            
//            try (
//                Writer writer = Files.newBufferedWriter( Paths.get( "D:\\test" ), Charsets.UTF_8,
//                                                         StandardOpenOption.CREATE,
//                                                         StandardOpenOption.TRUNCATE_EXISTING ) )
//            {
//                new FunctionTextWriter().write( prototype, writer );
//            }
            
//            try (
//                Reader readable = Files.newBufferedReader( Paths.get( "D:\\test" ), Charsets.UTF_8 ) )
//            {
//                prototype = new FunctionTextReader().read( readable );
//            }
            
//        new TreeCleaner().visit( chunk );
//        chunk.inspect( parser );
            
            long elapsed = stopwatch.stop().elapsed( TimeUnit.MICROSECONDS );
            runtimes.add( elapsed );
            min = elapsed < min ? elapsed : min;
            max = elapsed > max ? elapsed : max;
        }
        
        System.out.println( min + ", " + avg( runtimes ) + ", " + max );
        
//        new MtsClosure( prototype, new MtsGlobals() ).call();
    }
    
    private static double avg( Collection<Long> c )
    {
        int sum = 0;
        for ( long i : c )
            sum += i;
        return (double) sum / (double) c.size();
    }
    
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
