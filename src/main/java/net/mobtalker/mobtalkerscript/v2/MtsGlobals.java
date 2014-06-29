package net.mobtalker.mobtalkerscript.v2;

import static net.mobtalker.mobtalkerscript.v2.value.userdata.MtsNatives.*;

import java.io.*;

import net.mobtalker.mobtalkerscript.v2.lib.*;
import net.mobtalker.mobtalkerscript.v2.value.*;

public class MtsGlobals extends MtsTable
{
    public PrintStream out = System.out;
    public BufferedReader in = new BufferedReader( new InputStreamReader( System.in ) );
    public PrintStream err = System.err;
    
    // ========================================
    
    public static final MtsValue VERSION = valueOf( "@VERSION@" );
    
    // ========================================
    
    public MtsGlobals()
    {
        super( 0, 1024 );
        
        set( "_VERSION", VERSION );
        
        createLibrary( new MtsBaseLib( this ), this );
        set( "Math", createLibrary( MtsMathLib.class ) );
        set( "Table", createLibrary( MtsTableLib.class ) );
        
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
