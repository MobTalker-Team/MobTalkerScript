/*
 * SPDX-FileCopyrightText: 2013-2020 Chimaine, MobTalkerScript contributors
 *
 * SPDX-License-Identifier: LGPL-3.0-or-later
 */
package net.mobtalker.mobtalkerscript;

import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import net.mobtalker.mobtalkerscript.v3.MtsFunctionPrototype;
import net.mobtalker.mobtalkerscript.v3.compiler.*;
import net.mobtalker.mobtalkerscript.v3.compiler.antlr.generated.*;
import net.mobtalker.mobtalkerscript.v3.compiler.antlr.generated.Mts3Parser.EmptyStmtContext;
import net.mobtalker.mobtalkerscript.v3.serialization.*;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

public class GrammarTestRig
{
    public static void main( String[] args ) throws Exception
    {
        String path = "Creeper.script";
        MtsFunctionPrototype prototype = null;
        
//        for ( int round = 0; round < 5; round++ )
//        {
//            int runs = 1000;
//            List<Long> runtimes = new ArrayList<>( runs * 2 );
//            long min = Long.MAX_VALUE, max = Long.MIN_VALUE, all = 0;
//            for ( int i = 0; i < runs; i++ )
//            {
//                Stopwatch stopwatch = Stopwatch.createStarted();
        
        Mts3Parser parser = MtsCompiler.getParser( new ANTLRFileStream( "D:\\Projects\\Java\\Minecraft\\Forge 10.14\\MobTalker2\\run\\mobtalker2\\scripts\\MobTalker2_TestSuite\\"
                                                                        + path ) );
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

        FunctionTextWriter.writeChunk( prototype, Paths.get( "D:\\test_text" ), StandardCharsets.UTF_8,
                                       StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING );

        FunctionBinaryWriter.writeChunk( prototype, Paths.get( "D:\\test_binary" ),
                                         StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING );

        prototype = FunctionTextReader.readChunk( Paths.get( "D:\\test_text" ), StandardCharsets.UTF_8 );

        prototype = FunctionBinaryReader.readChunk( Paths.get( "D:\\test_binary" ) );

        new TreeCleaner().visit( chunk );

        System.out.println( chunk.toStringTree( parser ) );

//                long elapsed = stopwatch.stop().elapsed( TimeUnit.MICROSECONDS );
//                runtimes.add( elapsed );
//                min = elapsed < min ? elapsed : min;
//                max = elapsed > max ? elapsed : max;
//                all += elapsed;
//            }

//            System.out.printf( "Runs: %d, Min: %.2f, Max: %.2f, Avg: %.2f, Sum: %.2f\n",
//                               runs, ( min / 1000.0 ), ( max / 1000.0 ), ( avg( runtimes ) / 1000.0 ), ( all / 1000.0 ) );
//        }

//        new MtsClosure( prototype, new MtsGlobals() ).call();
    }

    private static double avg( Collection<Long> c )
    {
        int sum = 0;
        for ( long i : c )
            sum += i;
        return (double) sum / (double) c.size();
    }

    /**
     * Removes some clutter from the tree for nicer display
     */
    private static final class TreeCleaner extends Mts3BaseListener
    {
        private static final Set<Integer> IgnoredTokens = Collections.unmodifiableSet( Stream.of(Mts3Lexer.SEMICOLON,
                                                                                                 Mts3Lexer.COMMA,
                                                                                                 Mts3Lexer.COLON,
                                                                                                 Mts3Lexer.DOT,
                                                                                                 Mts3Lexer.CURLY_OPEN,
                                                                                                 Mts3Lexer.CURLY_CLOSE,
                                                                                                 Mts3Lexer.ROUND_OPEN,
                                                                                                 Mts3Lexer.ROUND_CLOSE,
                                                                                                 Mts3Lexer.SQUARE_OPEN,
                                                                                                 Mts3Lexer.SQUARE_CLOSE
                                                                                                ).collect( Collectors.toSet() ) );
        
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
    
    // ========================================
    
    // private constructor to prevent instantiation
    private GrammarTestRig() {}
}
