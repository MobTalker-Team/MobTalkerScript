/*
 * SPDX-FileCopyrightText: 2013-2020 Chimaine, MobTalkerScript contributors
 *
 * SPDX-License-Identifier: LGPL-3.0-or-later
 */
package net.mobtalker.mobtalkerscript;

import net.mobtalker.mobtalkerscript.v3.*;
import net.mobtalker.mobtalkerscript.v3.compiler.MtsCompiler;
import net.mobtalker.mobtalkerscript.v3.value.MtsClosure;

import org.junit.*;

public class TestScripts
{
    @BeforeClass
    public static void setUp() throws Exception
    {}
    
    private void run( String path ) throws Exception
    {
        MtsGlobals _G = new MtsGlobals();
        MtsFunctionPrototype prototype = MtsCompiler.loadFile( path );
        new MtsClosure( prototype, _G ).call();
    }
    
    @Test
    public void testCalls() throws Exception
    {
        run( "src/test/resources/scripts/calls.lua" );
    }
    
    @Test
    public void testConstructs() throws Exception
    {
        run( "src/test/resources/scripts/constructs.lua" );
    }
    
    @Test
    public void testLocals() throws Exception
    {
        run( "src/test/resources/scripts/locals.lua" );
    }
    
    @Test
    public void testVararg() throws Exception
    {
        run( "src/test/resources/scripts/vararg.lua" );
    }
    
    @Test
    public void testLargeMethod() throws Exception
    {
        run( "src/test/resources/scripts/largemethod.lua" );
    }
    
    /**
     * Regression test for #39 (https://bitbucket.org/MobTalker2/mobtalkerscript/issues/39/)
     */
    @Test
    public void testEquality()
            throws Exception
    {
        run( "src/test/resources/scripts/equality.lua" );
    }
}
