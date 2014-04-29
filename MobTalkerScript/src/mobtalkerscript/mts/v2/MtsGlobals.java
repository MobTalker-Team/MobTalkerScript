package mobtalkerscript.mts.v2;

import static com.google.common.base.Preconditions.*;

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
    
    public MtsFunctionPrototype loadFile( Path path ) throws IOException
    {
        checkArgument( Files.exists( path ), "Path '%s' does not exist", path );
        
        // Read the file
        ANTLRInputStream stream = new ANTLRFileStream( path.toString() );
        
        // Lex it
        MtsLexer lexer = new MtsLexer( stream );
        lexer.setTokenFactory( new CommonTokenFactory( false ) );
        TokenStream tokens = new UnbufferedTokenStream<Token>( lexer );
        
        // Parse it
        MtsParser parser = new MtsParser( tokens );
        parser.removeErrorListeners();
        parser.addErrorListener( new MtsAntlrErrorListener() );
        parser.setErrorHandler( new MtsErrorStrategy() );
        
        // TODO: Doesn't seem to work, look further into it.
//        parser.getInterpreter().setPredictionMode( PredictionMode.SLL );
        parser.getInterpreter().setPredictionMode( PredictionMode.LL );
        
        ChunkContext chunk = parser.chunk();
        
        // Compile it
        MtsCompiler compiler = new MtsCompiler( tokens.getSourceName(),
                                                chunk.getStart().getLine(),
                                                chunk.getStop().getLine() );
        
        AntlrCompilerAdapter adapter = new AntlrCompilerAdapter();
        adapter.compile( compiler, chunk );
        
        return compiler.compile();
    }
    // ========================================
    
}
