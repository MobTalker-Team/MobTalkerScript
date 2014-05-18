package mobtalkerscript.mts.v2;

import static mobtalkerscript.mts.v2.value.userdata.MtsNatives.*;

import java.io.*;

import mobtalkerscript.mts.v2.lib.*;
import mobtalkerscript.mts.v2.value.*;

public class MtsGlobals extends MtsTable
{
    public PrintStream out = System.out;
    public BufferedReader in = new BufferedReader( new InputStreamReader( System.in ) );
    public PrintStream err = System.err;
    
    // ========================================
    
    // ========================================
    
    public MtsGlobals()
    {
        super( 16, 1024 );
        
        loadLibrary( new MtsBaseLib() );
        
        set( "Math", createLibrary( MtsMathLib.class ), false );
        set( "Table", createLibrary( MtsTableLib.class ), false );
        
        MtsTable stringLib = createLibrary( MtsStringLib.class );
        MtsTable stringMT = new MtsTable( 0, 1 );
        stringMT.set( __INDEX, stringLib );
        MtsType.STRING.setMetaTable( stringMT );
    }
    
    // ========================================
    
    public void loadLibrary( MtsValue lib )
    {
        lib.call( EMPTY_STRING, this );
    }
}
