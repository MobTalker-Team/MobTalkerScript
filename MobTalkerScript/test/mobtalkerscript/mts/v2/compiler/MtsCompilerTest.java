package mobtalkerscript.mts.v2.compiler;

import static mobtalkerscript.mts.v2.value.MtsValue.*;
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
    public void antlr()
    {
        ANTLRInputStream stream = new ANTLRInputStream( "local a, b = 4, 2;" //
                                                        + "c = a ^ b;"
                                                        + "return 10 + c;" );
        MtsLexer lexer = new MtsLexer( stream );
        lexer.setTokenFactory( new CommonTokenFactory( false ) );
        TokenStream tokens = new UnbufferedTokenStream<Token>( lexer );
        
        MtsParser parser = new MtsParser( tokens );
        
        ChunkContext chunk = parser.chunk();
        
        MtsCompiler compiler = new MtsCompiler( chunk );
        
        MtsFunctionPrototype p = compiler.compile();
        MtsClosure closure = new MtsClosure( p, new MtsTable() );
        MtsValue result = closure.call();
        
        System.out.println( result );
    }
    
    @Test
    public void test1()
    {
        MtsCompiler compiler = new MtsCompiler( "test", 0, 0 );
        
        compiler.declareLocal( "a" );
        compiler.declareLocal( "C" );
        compiler.declareLocal( "b" );
        
        compiler.enterBlock();
        compiler.declareLocal( "b" );
        compiler.loadConstant( valueOf( 2 ) );
        compiler.store( "b" );
        compiler.leaveBlock();
        
        compiler.loadConstant( valueOf( 1 ) );
        compiler.store( "C" );
        
        compiler.loadConstant( valueOf( 2 ) );
        compiler.store( "a" );
        
        compiler.load( "a" );
        compiler.loadConstant( valueOf( 1 ) );
        compiler.binaryOperation( "+" );
        
        compiler.beginConditionalBlock( "and" );
        
        compiler.loadConstant( valueOf( 10 ) );
        compiler.load( "C" );
        compiler.binaryOperation( "-" );
        compiler.loadConstant( valueOf( 0 ) );
        compiler.logicOperation( ">" );
        
        compiler.endConditionalBlock();
        
        compiler.store( "result" );
        compiler.load( "result" );
        
        compiler.returnFunction( 1 );
        
        MtsFunctionPrototype p = compiler.compile();
        
        MtsClosure closure = new MtsClosure( p, new FrameValue[] { new FrameValue( new MtsTable() ) } );
        
        MtsValue result = closure.call();
        
        System.out.println( result );
    }
    
    @Test
    public void test2()
    {
        MtsCompiler compiler = new MtsCompiler( "test", 0, 0 );
        
        compiler.declareLocal( "i" );
        compiler.loadConstant( valueOf( 0 ) );
        compiler.store( "i" );
        
        compiler.beginWhileCondition();
//        compiler.load( "i" );
//        compiler.loadConstant( valueOf( 1000000 ) );
//        compiler.logicOperation( "<" );
        compiler.loadConstant( valueOf( true ) );
        
        compiler.beginWhileBody();
        compiler.load( "i" );
        compiler.loadConstant( valueOf( 1 ) );
        compiler.binaryOperation( "+" );
        compiler.store( "i" );
        
        compiler.endWhile();
        
        compiler.load( "i" );
        compiler.returnFunction( 1 );
        
        MtsFunctionPrototype p = compiler.compile();
        
        MtsClosure closure = new MtsClosure( p, new MtsTable() );
        
        MtsValue result = closure.call();
        
        System.out.println( result );
    }
    
}
