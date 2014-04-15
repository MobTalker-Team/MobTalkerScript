package mobtalkerscript.mts.v2.compiler;

import java.io.*;

import mobtalkerscript.mts.v2.*;
import mobtalkerscript.mts.v2.compiler.antlr.*;
import mobtalkerscript.mts.v2.compiler.antlr.MtsParser.ChunkContext;
import mobtalkerscript.mts.v2.instruction.*;
import mobtalkerscript.mts.v2.value.*;

import org.antlr.v4.runtime.*;
import org.junit.*;

public class MtsCompilerTest
{
    
    @Test
    public void antlr() throws IOException
    {
//        ANTLRInputStream stream = new ANTLRInputStream( "a, b = 4, 2; " //
//                                                        + "local c = { 1, 2, y = \"abc\", [\"z\"] = b }; "
//                                                        + "function d( e, f ) c[e] = f; return 1; end "
//                                                        + "x = d(\"b\", 2); "
//                                                        + "c.a = a ^ b; "
//                                                        + "return 10 + c.a + c.b; " );
//        ANTLRInputStream stream = new ANTLRInputStream( "local i, x = 1, \"bar\"; while ( i <= 10 ) do print( \"i: \" .. i ); i = i + 1; x = toString( i ); end return x;" );
//        ANTLRInputStream stream = new ANTLRInputStream( "local a = \"bar\"; for i = 10, 1, -1 do local b = i * 2; print( a .. \": \" .. b ); end" );
        ANTLRInputStream stream = new ANTLRInputStream( "local t = { 10, 9, 8, 7, 6, 5, 4, 3, 2, 1 };"
                                                        + "for i, v in next, t do print( i .. \": \" .. v ); end" );
        
//        ANTLRInputStream stream = new ANTLRFileStream( "D:\\MobTalker2\\lua-5.2.2-tests\\locals.lua" );
        
        MtsLexer lexer = new MtsLexer( stream );
        lexer.setTokenFactory( new CommonTokenFactory( false ) );
        TokenStream tokens = new UnbufferedTokenStream<Token>( lexer );
        
        MtsParser parser = new MtsParser( tokens );
        
        ChunkContext chunk = parser.chunk();
        
        System.out.println( chunk.toStringTree( parser ) );
        
        AntlrMtsCompiler compiler = new AntlrMtsCompiler( chunk );
        
        MtsFunctionPrototype p = compiler.compile();
        
        MtsTable _G = new MtsGlobals();
        
        for ( MtsInstruction instr : p.getInstructions() )
        {
            System.out.println( instr );
        }
        
        MtsClosure closure = new MtsClosure( p, _G );
        MtsValue result = closure.call();
        
        System.out.println( result );
    }
    
    @Test
    public void test1()
    {
//        AntlrMtsCompiler compiler = 
//        
//        compiler.declareLocal( "a" );
//        compiler.declareLocal( "C" );
//        compiler.declareLocal( "b" );
//        
//        compiler.enterBlock();
//        compiler.declareLocal( "b" );
//        compiler.loadConstant( valueOf( 2 ) );
//        compiler.storeVariable( "b" );
//        compiler.exitBlock();
//        
//        compiler.loadConstant( valueOf( 1 ) );
//        compiler.storeVariable( "C" );
//        
//        compiler.loadConstant( valueOf( 2 ) );
//        compiler.storeVariable( "a" );
//        
//        compiler.loadVariable( "a" );
//        compiler.loadConstant( valueOf( 1 ) );
//        compiler.binaryOperation( "+" );
//        
//        compiler.enterConditionalBlock( "and" );
//        
//        compiler.loadConstant( valueOf( 10 ) );
//        compiler.loadVariable( "C" );
//        compiler.binaryOperation( "-" );
//        compiler.loadConstant( valueOf( 0 ) );
//        compiler.logicOperation( ">" );
//        
//        compiler.endConditionalBlock();
//        
//        compiler.storeVariable( "result" );
//        compiler.loadVariable( "result" );
//        
//        compiler.returnFunction( 1 );
//        
//        MtsFunctionPrototype p = compiler.compile();
//        
//        MtsClosure closure = new MtsClosure( p, new FrameValue[] { new FrameValue( new MtsTable() ) } );
//        
//        MtsValue result = closure.call();
//        
//        System.out.println( result );
    }
    
    @Test
    public void test2()
    {
//        AntlrMtsCompiler compiler = new AntlrMtsCompiler( "test", 0, 0 );
//        
//        compiler.declareLocal( "i" );
//        compiler.loadConstant( valueOf( 0 ) );
//        compiler.storeVariable( "i" );
//        
//        compiler.beginWhileCondition();
////        compiler.load( "i" );
////        compiler.loadConstant( valueOf( 1000000 ) );
////        compiler.logicOperation( "<" );
//        compiler.loadConstant( valueOf( true ) );
//        
//        compiler.exitWhileBody();
//        compiler.loadVariable( "i" );
//        compiler.loadConstant( valueOf( 1 ) );
//        compiler.binaryOperation( "+" );
//        compiler.storeVariable( "i" );
//        
//        compiler.endWhile();
//        
//        compiler.loadVariable( "i" );
//        compiler.returnFunction( 1 );
//        
//        MtsFunctionPrototype p = compiler.compile();
//        
//        MtsClosure closure = new MtsClosure( p, new MtsTable() );
//        
//        MtsValue result = closure.call();
//        
//        System.out.println( result );
    }
    
}
