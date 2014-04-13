package mobtalkerscript.mts.v2;

import java.io.*;

import mobtalkerscript.mts.v2.lib.*;
import mobtalkerscript.mts.v2.value.*;

public class MtsGlobals extends MtsTable
{
    
    public PrintStream out = System.out;
    public PrintStream err = System.err;
    
    // ========================================
    
    public MtsGlobals()
    {
        super( 16, 1024 );
        
        loadLibrary( new MtsBaseLib() );
    }
    
    // ========================================
    
    public void loadLibrary( MtsValue lib )
    {
        lib.call( EMPTY_STRING, this );
    }
    
    // ========================================
    
}
