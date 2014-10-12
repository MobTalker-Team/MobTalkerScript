package net.mobtalker.mobtalkerscript;

import java.util.logging.Logger;

import net.mobtalker.mobtalkerscript.util.logging.MtsLog;
import net.mobtalker.mobtalkerscript.v2.*;
import net.mobtalker.mobtalkerscript.v2.compiler.MtsCompiler;
import net.mobtalker.mobtalkerscript.v2.value.MtsClosure;

import org.junit.*;

public class TestScripts
{
    @BeforeClass
    public static void setUp()
    {
        MtsLog.setLogger( Logger.getLogger( "MTS" ), true );
//        MtsLog.CompilerLog.setLevel( Level.OFF );
//        MtsLog.EngineLog.setLevel( Level.OFF );
    }
    
    private void run( String path ) throws Exception
    {
        MtsGlobals _G = new MtsGlobals();
        MtsFunctionPrototype prototype = MtsCompiler.loadFile( path );
        new MtsClosure( prototype, _G ).call();
    }
    
    @Test
    public void testCalls() throws Exception
    {
        run( "src/test/resources/scripts/test.calls.script" );
    }
    
    @Test
    public void testConstructs() throws Exception
    {
        run( "src/test/resources/scripts/test.constructs.script" );
    }
    
    @Test
    public void testMath() throws Exception
    {
        run( "src/test/resources/scripts/test.math.script" );
    }
    
}
