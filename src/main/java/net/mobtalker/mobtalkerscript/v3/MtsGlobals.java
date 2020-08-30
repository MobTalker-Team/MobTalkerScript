/*
 * SPDX-FileCopyrightText: 2013-2020 Chimaine, MobTalkerScript contributors
 *
 * SPDX-License-Identifier: LGPL-3.0-or-later
 */
package net.mobtalker.mobtalkerscript.v3;

import static net.mobtalker.mobtalkerscript.v3.value.MtsMetaMethods.*;
import static net.mobtalker.mobtalkerscript.v3.value.userdata.MtsNatives.*;

import java.io.*;

import net.mobtalker.mobtalkerscript.v3.lib.*;
import net.mobtalker.mobtalkerscript.v3.value.*;

public class MtsGlobals extends MtsTable
{
    public PrintStream out;
    public BufferedReader in;
    public PrintStream err;
    
    // ========================================
    
    public static final MtsString VERSION = MtsString.of( "3.0.1-beta" );
    public static final int VERSION_MAJOR = 3;
    public static final int VERSION_MINOR = 0;
    
    // ========================================
    
    public MtsGlobals()
    {
        this( System.out, System.in, System.err );
    }
    
    public MtsGlobals( PrintStream out, InputStream in, PrintStream err )
    {
        super( 0, 1024 );
        
        this.out = out;
        this.in = new BufferedReader( new InputStreamReader( in ) );
        this.err = err;
        
        set( "_VERSION", VERSION );
        
        createLibrary( this, new MtsBaseLib( this ) );
        set( "Math", createLibrary( MtsMathLib.class ) );
        set( "Table", createLibrary( MtsTableLib.class ) );
        
        set( "String", createLibrary( MtsStringLib.class ) );
        createStringLib();
        
        PackageLib = new MtsPackageLib( this );
        createLibrary( this, PackageLib );
    }
    
    private void createStringLib()
    {
        MtsTable lib = createLibrary( MtsStringLib.class );
        MtsTable mt = new MtsTable( 0, 2 );
        mt.set( __index, lib );
        mt.set( __metatable, MtsBoolean.True );
        MtsType.STRING.setMetaTable( mt );
    }
    
    // ========================================
    
    public final MtsPackageLib PackageLib;
    
    // ========================================
    
    public void loadLibrary( MtsValue lib )
    {
        lib.call( MtsString.Empty, this );
    }
    
    // ========================================
    
}
