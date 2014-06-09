package mobtalkerscript.v2;

import static mobtalkerscript.v2.value.userdata.MtsNatives.*;

import java.io.*;

import mobtalkerscript.v2.lib.*;
import mobtalkerscript.v2.value.*;

public class MtsGlobals extends MtsTable
{
    public PrintStream out = System.out;
    public BufferedReader in = new BufferedReader( new InputStreamReader( System.in ) );
    public PrintStream err = System.err;
    
    // ========================================
    
    public static final MtsValue VERSION = valueOf( "2.0.1-alpha" );
    
    // ========================================
    
    public MtsGlobals()
    {
        super( 0, 1024 );
        
        set( "_VERSION", VERSION );
        
        createLibrary( new MtsBaseLib( this ), this );
        set( "Math", createLibrary( MtsMathLib.class ), false );
        set( "Table", createLibrary( MtsTableLib.class ), false );
        
        createStringLib();
        
        PackageLib = new MtsPackageLib( this );
        createLibrary( PackageLib, this );
    }
    
    private void createStringLib()
    {
        MtsTable lib = createLibrary( MtsStringLib.class );
        MtsTable mt = new MtsTable( 0, 2 );
        mt.set( __INDEX, lib );
        mt.set( __METATABLE, TRUE );
        MtsType.STRING.setMetaTable( mt );
    }
    
    // ========================================
    
    public final MtsPackageLib PackageLib;
    
    // ========================================
    
    public void loadLibrary( MtsValue lib )
    {
        lib.call( EMPTY_STRING, this );
    }
    
    // ========================================
    
}
