package mobtalkerscript.mts.v2.compiler;

import java.io.*;
import java.util.logging.*;

import mobtalkerscript.mts.v2.*;
import mobtalkerscript.mts.v2.compiler.antlr.*;
import mobtalkerscript.mts.v2.compiler.antlr.MtsParser.ChunkContext;
import mobtalkerscript.mts.v2.lib.*;
import mobtalkerscript.mts.v2.lib.mobtalker.*;
import mobtalkerscript.mts.v2.value.*;
import mobtalkerscript.util.logging.*;

import org.antlr.v4.runtime.*;
import org.junit.*;

public class MtsCompilerTest
{
    
    @BeforeClass
    public static void setUp() throws SecurityException, IOException
    {
        Logger logger = Logger.getLogger( "MobTalkerScript" );
        logger.setLevel( Level.FINER );
        
        MTSLog.setLogger( logger );
    }
    
    @Test
    public void antlr() throws IOException
    {
//        ANTLRInputStream stream = new ANTLRInputStream( "a, b = 4, 2; " //
//                                                        + "local c = { 1, 2, y = \"abc\", [\"z\"] = b }; "
//                                                        + "function d( e, f ) c[e] = f; return 1; end "
//                                                        + "x = d(\"b\", 2); "
//                                                        + "c.a = a ^ b; "
//                                                        + "return 10 + c.a + c.b; " );
//        ANTLRInputStream stream = new ANTLRInputStream( "local i, x = 1, \"bar\"; while ( i <= 10 ) do print( \"i: \" .. i ); i = i + 1; x = toString( i ); end return x;" );
//        ANTLRInputStream stream = new ANTLRInputStream( "local a = \"bar\"; for i = 10, 1, -1 do local b = i * 2; print( a .. \": \" .. b ); end" );
//        ANTLRInputStream stream = new ANTLRInputStream( "   local t = { 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, a = \"foo\" };"
//                                                        + " for i, v in next, t do"
//                                                        + "     print( i .. \": \" .. v );"
//                                                        + " end" );
        
//        ANTLRInputStream stream = new ANTLRFileStream( "D:\\MobTalker2\\lua-5.2.2-tests\\locals.lua" );
        ANTLRInputStream stream = new ANTLRFileStream( "C:\\Users\\Tobsen\\Desktop\\MtsExampleScript.txt" );
        
        MtsLexer lexer = new MtsLexer( stream );
        lexer.setTokenFactory( new CommonTokenFactory( false ) );
        TokenStream tokens = new UnbufferedTokenStream<Token>( lexer );
        
        MtsParser parser = new MtsParser( tokens );
        
        ChunkContext chunk = parser.chunk();
        
        System.out.println( chunk.toStringTree( parser ) );
        
        AntlrMtsParser compiler = new AntlrMtsParser( chunk );
        
        MtsFunctionPrototype p = compiler.compile();
        
        MtsGlobals _G = new MtsGlobals();
        _G.loadLibrary( new ConsoleCommandLib() );
        
        _G.loadLibrary( new MobTalkerConsoleBaseLib( "Console", 0 ) );
        _G.loadLibrary( new MobTalkerConsoleCharacterLib() );
        
        System.out.println( p.toString( true, true ) );
        
        MtsClosure closure = new MtsClosure( p, _G );
        MtsValue result = closure.call();
        
        System.out.println( result );
    }
    
}
