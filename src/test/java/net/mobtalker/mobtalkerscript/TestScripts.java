/*
 * Copyright (C) 2013-2014 Chimaine
 * 
 * This program is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package net.mobtalker.mobtalkerscript;

import java.io.*;
import java.util.logging.Logger;

import net.mobtalker.mobtalkerscript.util.logging.MtsLog;
import net.mobtalker.mobtalkerscript.v3.*;
import net.mobtalker.mobtalkerscript.v3.compiler.MtsCompiler;
import net.mobtalker.mobtalkerscript.v3.value.MtsClosure;

import org.junit.*;

public class TestScripts
{
    public static void main( String[] args ) throws Exception
    {
        setUp();
        TestScripts test = new TestScripts();
        
        BufferedReader in = new BufferedReader( new InputStreamReader( System.in ) );
        in.readLine();
        
        test.testCalls();
        test.testConstructs();
        test.testVararg();
        
        System.out.println( "Done!" );
        in.readLine();
    }
    
    @BeforeClass
    public static void setUp() throws Exception
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
        run( "src/test/resources/scripts/calls.lua" );
    }
    
    @Test
    public void testConstructs() throws Exception
    {
        run( "src/test/resources/scripts/constructs.lua" );
    }
    
    @Test
    public void testVararg() throws Exception
    {
        run( "src/test/resources/scripts/vararg.lua" );
    }
    
}
