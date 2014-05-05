package mobtalkerscript.mts.v2.compiler;

import java.io.*;
import java.util.logging.*;

import mobtalkerscript.mts.v2.*;
import mobtalkerscript.mts.v2.lib.*;
import mobtalkerscript.mts.v2.lib.mobtalker.*;
import mobtalkerscript.mts.v2.value.*;
import mobtalkerscript.util.logging.*;

import org.junit.*;

public class MtsCompilerTest
{
    
    @BeforeClass
    public static void setUp() throws SecurityException, IOException
    {
        Logger logger = Logger.getLogger( "MTS" );
        logger.setLevel( Level.FINER );
        
        MtsLog.setLogger( logger, true );
    }
    
    @Test
    public void antlr() throws Exception
    {
        MtsGlobals _ENV = new MtsGlobals();
        _ENV.loadLibrary( new ConsoleCommandLib() );
        
        _ENV.loadLibrary( new MobTalkerConsoleInteractionLib( "Console", 0 ) );
        _ENV.loadLibrary( new MobTalkerConsoleCharacterLib() );
        
        MtsFunctionPrototype p;
        p = _ENV.loadFile( "C:\\Users\\Tobsen\\Desktop\\MtsExampleScript.txt" );
        
        System.out.println( p.toString( true, true ) );
        
        MtsClosure closure = new MtsClosure( p, _ENV );
        MtsValue result = closure.call();
        
        System.out.println( result );
    }
}
