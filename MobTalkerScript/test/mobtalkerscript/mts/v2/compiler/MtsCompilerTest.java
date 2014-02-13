package mobtalkerscript.mts.v2.compiler;

import static mobtalkerscript.mts.v2.value.MtsValue.*;
import mobtalkerscript.mts.v2.*;
import mobtalkerscript.mts.v2.instruction.*;
import mobtalkerscript.mts.v2.value.*;

import org.junit.*;

public class MtsCompilerTest
{
    
    @Test
    public void test()
    {
        MtsCompiler compiler = new MtsCompiler( "test" );
        
        compiler.declareLocal( "a" );
        compiler.declareLocal( "C" );
        compiler.declareLocal( "b" );
        
        compiler.loadConstant( valueOf( 1 ) );
        compiler.store( "C" );
        
        compiler.loadConstant( valueOf( 2 ) );
        compiler.store( "a" );
        
        compiler.load( "a" );
        compiler.loadConstant( valueOf( 1 ) );
        compiler.binaryOperation( "+" );
        
        compiler.logicOperation( "and" );
        
        compiler.loadConstant( valueOf( 10 ) );
        compiler.load( "C" );
        compiler.binaryOperation( "-" );
        compiler.loadConstant( valueOf( 0 ) );
        compiler.logicOperation( ">" );
        
        compiler.setJumpTarget();
        compiler.addInstr( new InstrReturn( 1 ) );
        
        MtsFunctionPrototype p = compiler.compile();
        
        MtsClosure closure = new MtsClosure( p, new FrameValue[] { new FrameValue( new MtsTable() ) } );
        
        MtsValue result = closure.call();
        
        System.out.println( result );
    }
    
}
