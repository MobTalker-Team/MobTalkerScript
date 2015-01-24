/*
 * Copyright (C) 2013-2015 Chimaine
 * 
 * This program is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package net.mobtalker.mobtalkerscript;

import java.nio.file.*;
import java.util.*;

import net.mobtalker.mobtalkerscript.v3.MtsFunctionPrototype;
import net.mobtalker.mobtalkerscript.v3.compiler.*;
import net.mobtalker.mobtalkerscript.v3.compiler.antlr.generated.*;
import net.mobtalker.mobtalkerscript.v3.compiler.antlr.generated.Mts3Parser.EmptyStmtContext;
import net.mobtalker.mobtalkerscript.v3.serialization.*;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

import com.google.common.base.Charsets;
import com.google.common.collect.ImmutableSet;

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
        
        FunctionTextWriter.writeChunk( prototype, Paths.get( "D:\\test_text" ), Charsets.UTF_8,
                                       StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING );
        
        FunctionBinaryWriter.writeChunk( prototype, Paths.get( "D:\\test_binary" ),
                                         StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING );
        
        prototype = FunctionTextReader.readChunk( Paths.get( "D:\\test_text" ), Charsets.UTF_8 );
        
        prototype = FunctionBinaryReader.readChunk( Paths.get( "D:\\test_binary" ) );
        
        new TreeCleaner().visit( chunk );
        chunk.inspect( parser );
        
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
