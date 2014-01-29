package mobtalkerscript;

import static mobtalkerscript.mts.v2.value.MtsValue.*;

import java.util.*;

import mobtalkerscript.mts.v2.*;
import mobtalkerscript.mts.v2.instruction.*;
import mobtalkerscript.mts.v2.value.*;

import com.google.common.collect.*;

public class Test
{
    
    public static void main( String[] args )
    {
        Test2();
    }
    
    private static void Test1()
    {
        ArrayList<MtsInstruction> instrs = Lists.newArrayList( new InstrLoadL( 0 ), //
                                                               new InstrLoadC( 0 ), //
                                                               new InstrLoadC( 1 ),
                                                               new InstrDiv(),
                                                               new InstrMul(),
                                                               new InstrReturn( 1 ) );
        ArrayList<MtsValue> constants = Lists.<MtsValue> newArrayList( valueOf( 5 ), //
                                                                       valueOf( 2 ) );
        ArrayList<ExternalDescription> externals = Lists.<ExternalDescription> newArrayList();
        
        MtsFunctionPrototype p = new MtsFunctionPrototype( instrs,
                                                             3,
                                                             1,
                                                             constants,
                                                             externals,
                                                             null,
                                                             null,
                                                             -1,
                                                             -1 );
        
        MtsClosure closure = new MtsClosure( p, Lists.<External> newArrayList() );
        
        MtsValue result = closure.call( valueOf( "2" ) );
        System.out.println( result );
    }
    
    private static void Test2()
    {
        ArrayList<MtsInstruction> instrs = Lists.newArrayList( new InstrLoadC( 0 ), //
                                                               new InstrStoreL( 0 ),
                                                               new InstrLoadL( 0 ),
                                                               new InstrLoadC( 1 ),
                                                               new InstrLT(),
                                                               new InstrTest( 3 ),
                                                               new InstrIncrL( 0 ),
                                                               new MtsJumpInstruction( -5 ),
                                                               new InstrLoadL( 0 ),
                                                               new InstrReturn( 1 ) );
        ArrayList<MtsValue> constants = Lists.<MtsValue> newArrayList( valueOf( 1 ), //
                                                                       valueOf( 10 ) );
        ArrayList<ExternalDescription> externals = Lists.<ExternalDescription> newArrayList();
        
        MtsFunctionPrototype p = new MtsFunctionPrototype( instrs,
                                                             3,
                                                             1,
                                                             constants,
                                                             externals,
                                                             null,
                                                             null,
                                                             -1,
                                                             -1 );
        
        MtsClosure closure = new MtsClosure( p, Lists.<External> newArrayList() );
        
        MtsValue result = closure.call();
        System.out.println( result );
    }
}
