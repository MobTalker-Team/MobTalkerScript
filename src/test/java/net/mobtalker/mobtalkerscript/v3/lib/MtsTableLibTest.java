package net.mobtalker.mobtalkerscript.v3.lib;

import static net.mobtalker.mobtalkerscript.v3.value.MtsValue.*;
import static org.junit.Assert.*;
import net.mobtalker.mobtalkerscript.v3.value.*;

import org.junit.Test;

public class MtsTableLibTest
{
    
    @Test
    public void testRandom()
    {
        assertEquals( Nil, MtsTableLib.random( MtsVarargs.Empty ) );
        
        MtsString a = MtsString.of( "A" );
        MtsString b = MtsString.of( "B" );
        MtsString c = MtsString.of( "C" );
        MtsString d = MtsString.of( "D" );
        
        for ( int i = 0; i < 1e6; i++ )
        {
            MtsValue result = MtsTableLib.random( MtsVarargs.of( a, b, c, d ) );
            assertTrue( "Got " + result.toString(),
                        result.equals( a ) || result.equals( b ) || result.equals( c ) || result.equals( d ) );
        }
        
        for ( int i = 0; i < 1e6; i++ )
        {
            MtsTable t = new MtsTable();
            t.set( MtsNumber.of( 1 ), a );
            t.set( MtsNumber.of( 2 ), b );
            t.set( MtsNumber.of( 3 ), c );
            t.set( MtsNumber.of( 4 ), d );
            
            MtsValue result = MtsTableLib.random( MtsVarargs.of( t ) );
            assertTrue( "Got " + result.toString(),
                        result.equals( a ) || result.equals( b ) || result.equals( c ) || result.equals( d ) );
        }
    }
    
}
