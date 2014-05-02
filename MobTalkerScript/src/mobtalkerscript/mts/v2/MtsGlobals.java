package mobtalkerscript.mts.v2;

import static com.google.common.base.Preconditions.*;
import static com.google.common.base.Strings.*;

import java.io.*;
import java.nio.file.*;

import mobtalkerscript.mts.v2.compiler.*;
import mobtalkerscript.mts.v2.compiler.antlr.*;
import mobtalkerscript.mts.v2.compiler.antlr.MtsParser.ChunkContext;
import mobtalkerscript.mts.v2.lib.*;
import mobtalkerscript.mts.v2.value.*;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;

public class MtsGlobals extends MtsTable
{
    
    public PrintStream out = System.out;
    public BufferedReader in = new BufferedReader( new InputStreamReader( System.in ) );
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
    
    public MtsFunctionPrototype loadFile( String path ) throws Exception
    {
        return loadFile( Paths.get( path ) );
    }
    
    public MtsFunctionPrototype loadFile( File file ) throws Exception
    {
        return loadFile( file.toPath() );
    }
    
    public MtsFunctionPrototype loadFile( Path path ) throws Exception
    {
        checkArgument( Files.exists( path ), "Path '%s' does not exist", path );
        
        return load( new ANTLRFileStream( path.toString() ) );
    }
    
    // ========================================
    
    public MtsFunctionPrototype loadString( String chunk ) throws Exception
    {
        checkArgument( !isNullOrEmpty( chunk ), "chunk cannot be null or empty" );
        
        return load( new ANTLRInputStream( chunk ) );
    }
    
    // ========================================
    
    public MtsFunctionPrototype load( ANTLRInputStream stream ) throws Exception
    {
        // Lex it
        MtsLexer lexer = new MtsLexer( stream );
        lexer.setTokenFactory( new CommonTokenFactory( false ) );
        TokenStream tokens = new UnbufferedTokenStream<Token>( lexer );
        
        // Parse it
        MtsParser parser = new MtsParser( tokens );
        parser.removeErrorListeners();
        parser.addErrorListener( new MtsAntlrErrorListener() );
        parser.setErrorHandler( new MtsErrorStrategy() );
        
        // TODO: SLL doesn't seem to work, look further into it.
        parser.getInterpreter().setPredictionMode( PredictionMode.LL_EXACT_AMBIG_DETECTION );
        
        ChunkContext chunk = parser.chunk();
        
        // Compile it
        MtsCompiler compiler = new MtsCompiler( tokens.getSourceName(),
                                                chunk.getStart().getLine(),
                                                chunk.getStop().getLine() );
        
        AntlrCompilerAdapter adapter = new AntlrCompilerAdapter( compiler );
        adapter.compile( chunk );
        
        return compiler.compile();
    }
    
    // ========================================
    
}
