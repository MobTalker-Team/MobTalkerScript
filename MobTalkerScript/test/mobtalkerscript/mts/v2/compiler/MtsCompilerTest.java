package mobtalkerscript.mts.v2.compiler;

import mobtalkerscript.mts.v2.*;
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
        
        compiler.loadConstant( MtsValue.valueOf( 1 ) );
        compiler.store( "C" );
        
        compiler.load( "C" );
        compiler.store( "B" );
        
        MtsFunctionPrototype p = compiler.compile();
    }
    
}
