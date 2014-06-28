package mobtalkerscript.v2.compiler;

import static com.google.common.base.Preconditions.*;
import static com.google.common.base.Strings.*;
import static mobtalkerscript.util.logging.MtsLog.*;
import static mobtalkerscript.v2.compiler.CompilerConstants.*;
import static mobtalkerscript.v2.instruction.Instructions.*;
import static mobtalkerscript.v2.value.MtsValue.*;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.logging.*;
import java.util.regex.*;

import mobtalkerscript.util.*;
import mobtalkerscript.v2.*;
import mobtalkerscript.v2.compiler.antlr.*;
import mobtalkerscript.v2.compiler.antlr.MtsParser.ChunkContext;
import mobtalkerscript.v2.instruction.*;
import mobtalkerscript.v2.value.*;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.apache.commons.lang3.*;

public class MtsCompiler
{
    public static MtsFunctionPrototype loadFile( String path ) throws Exception
    {
        return loadFile( Paths.get( path ) );
    }
    
    public static MtsFunctionPrototype loadFile( File file ) throws Exception
    {
        return loadFile( file.toPath() );
    }
    
    public static MtsFunctionPrototype loadFile( Path path ) throws Exception
    {
        path = path.toRealPath();
        return load( new ANTLRFileStream( path.toString() ) );
    }
    
    // ========================================
    
    public static MtsFunctionPrototype loadString( String chunk, String source ) throws Exception
    {
        checkArgument( !isNullOrEmpty( chunk ), "chunk cannot be null or empty" );
        
        ANTLRInputStream stream = new ANTLRInputStream( chunk );
        stream.name = source;
        
        return load( stream );
    }
    
    // ========================================
    
    public static MtsFunctionPrototype load( ANTLRInputStream stream ) throws IOException, MtsSyntaxError
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
        
        ChunkContext chunk;
        try
        {
            chunk = parser.chunk();
        }
        catch ( MtsSyntaxError ex )
        {
            throw new MtsSyntaxError( ex.getSourceName(), ex.getSourcePosition(), ex.getOriginalMessage() );
        }
        
        // Compile it
        MtsCompiler compiler = new MtsCompiler( tokens.getSourceName(),
                                                chunk.getStart().getLine(),
                                                chunk.getStop().getLine() );
        
        AntlrCompilerAdapter adapter = new AntlrCompilerAdapter( compiler );
        adapter.compile( chunk );
        
        return compiler.compile();
    }
    
    // ========================================
    
    private final FunctionState _mainFunction;
    private FunctionState _currentFunction;
    
    private final String _sourceName;
    private SourcePosition _curPosition;
    
    // ========================================
    
    {
        _curPosition = new SourcePosition( 0, 0 );
    }
    
    public MtsCompiler( String sourceName, int sourceLineStart, int sourceLineEnd )
    {
        _mainFunction = new FunctionState( null, "main", sourceName, sourceLineStart, sourceLineEnd );
        _mainFunction.addExternal( new ExternalDescription( ENV, 0, 0, true ) );
        
        _currentFunction = _mainFunction;
        
        _sourceName = sourceName;
    }
    
    // ========================================
    
    public void setSourcePosition( int line, int coloum )
    {
        if ( _curPosition.equals( line, coloum ) )
            return;
        
        _curPosition = new SourcePosition( line, coloum );
    }
    
    public SourcePosition getSourcePosition()
    {
        return _curPosition;
    }
    
    // ========================================
    
    public void addInstr( MtsInstruction instr )
    {
        CompilerLog.fine( "  Instruction: " + instr );
        
        _currentFunction.addInstruction( instr, _curPosition );
    }
    
    public void discardValue()
    {
        addInstr( InstrPop() );
    }
    
    // ========================================
    
    public void enterFunction( String name, int sourceLineStart, int sourceLineEnd, String... params )
    {
        CompilerLog.info( "Enter Function " );
        
        enterFunction( name, sourceLineStart, sourceLineEnd, params );
    }
    
    public void enterFunction( String name, int sourceLineStart, int sourceLineEnd, Iterable<String> params )
    {
        FunctionState child = new FunctionState( _currentFunction,
                                                 name,
                                                 _sourceName,
                                                 sourceLineStart,
                                                 sourceLineEnd );
        _currentFunction.addChild( child );
        _currentFunction = child;
        
        for ( String param : params )
        {
            declareLocal( param );
        }
    }
    
    public void exitFunction()
    {
        CompilerLog.info( "Exit Function " );
        
        addInstr( InstrReturn( 0 ) );
        _currentFunction = _currentFunction.getParent();
    }
    
    // ========================================
    
    public void enterBlock()
    {
        CompilerLog.info( "Enter Block" );
        
        _currentFunction.enterBlock();
    }
    
    public void exitBlock()
    {
        CompilerLog.info( "Exit Block" );
        
        _currentFunction.exitBlock();
    }
    
    // ========================================
    
    public void enterWhileLoop()
    {
        CompilerLog.info( "Enter WhileLoop" );
        
        _currentFunction.enterLoop();
    }
    
    public void enterWhileBody()
    {
        CompilerLog.info( "Enter WhileBody" );
        
        addInstr( InstrTest() );
        _currentFunction.markBreak();
        _currentFunction.enterBlock();
    }
    
    public void exitWhileLoop()
    {
        CompilerLog.info( "Exit WhileLoop" );
        
        addInstr( InstrJump() );
        _currentFunction.exitLoop();
        _currentFunction.exitBlock();
    }
    
    // ========================================
    
    public void enterRepeatLoop()
    {
        _currentFunction.enterLoop();
        _currentFunction.enterBlock();
    }
    
    public void enterUntilConditon()
    {
        _currentFunction.exitBlock();
    }
    
    public void exitRepeatLoop()
    {
        addInstr( InstrTest() );
        _currentFunction.exitLoop();
    }
    
    // ========================================
    
    public void enterNumericForLoop( String varName )
    {
        _currentFunction.enterBlock();
        _currentFunction.enterNumericForLoop( varName );
    }
    
    public void enterGenericForLoop( String... vars )
    {
        _currentFunction.enterBlock();
        _currentFunction.enterGenericForLoop( vars );
    }
    
    public void exitForLoop()
    {
        addInstr( InstrJump() );
        _currentFunction.exitLoop();
        _currentFunction.exitBlock();
    }
    
    // ========================================
    
    public void breakLoop()
    {
        addInstr( InstrJump() );
        _currentFunction.markBreak();
    }
    
    // ========================================
    
    public void enterIfThenElseBlock()
    {
        _currentFunction.enterIfThenElse();
    }
    
    public void enterIfCondition()
    {
        _currentFunction.enterIfCondition();
    }
    
    public void endIfCondition()
    {
        _currentFunction.endIfCondition();
    }
    
    public void endThenBlock()
    {
        _currentFunction.endThenBlock();
    }
    
    public void enterElseBlock()
    {
        _currentFunction.enterElseBlock();
    }
    
    public void exitIfThenElse()
    {
        _currentFunction.exitIfThenElse();
    }
    
    // ========================================
    
    public void declareLabel( String name )
    {
        _currentFunction.addLabel( name );
    }
    
    public void gotoLabel( String name )
    {
        _currentFunction.gotoLabel( name );
    }
    
//    public abstract void gotoFunction( String name );
    
    // ========================================
    
    public LocalDescription declareLocal( String name )
    {
        CompilerLog.info( "Declare local: " + name );
        
        return _currentFunction.declareLocal( name );
    }
    
    public LocalDescription declareAnonymousLocal( String name )
    {
        CompilerLog.info( "Declare internal: " + name );
        
        return _currentFunction.declareAnonymousLocal( name );
    }
    
    private void loadEnvironment()
    {
        if ( _currentFunction.isLocal( ENV ) )
        { // Local environment
            int index = _currentFunction.getLocalIndex( ENV );
            addInstr( InstrLoadL( index ) );
        }
        else
        { // Parent environment
            int index = _currentFunction.getExternalIndex( ENV );
            addInstr( InstrLoadE( index ) );
        }
    }
    
    public void loadVariable( String name )
    {
        CompilerLog.info( "Load Variable: " + name );
        
        if ( _currentFunction.isLocal( name ) )
        { // Local
            int index = _currentFunction.getLocalIndex( name );
            addInstr( InstrLoadL( index ) );
        }
        else if ( _currentFunction.isExternal( name ) )
        { // External
            int index = _currentFunction.getExternalIndex( name );
            addInstr( InstrLoadE( index ) );
        }
        else
        { // Global
            loadEnvironment();
            
            int constant = _currentFunction.getConstantIndex( valueOf( name ) );
            addInstr( InstrLoadC( constant ) );
            addInstr( InstrLoadT() );
        }
    }
    
    public void loadLocal( int index )
    {
        addInstr( InstrLoadL( index ) );
    }
    
    public void loadConstant( MtsValue value )
    {
        checkNotNull( value != null, "value cannot be null" );
        checkArgument( !value.isNil(), "value cannot be nil" );
        
        CompilerLog.info( "Load constant: " + value );
        
        int index = _currentFunction.getConstantIndex( value );
        addInstr( InstrLoadC( index ) );
    }
    
    public void loadConstant( String s )
    {
        loadConstant( parseString( s ) );
    }
    
    public void loadNil()
    {
        CompilerLog.info( "Load nil" );
        
        addInstr( InstrLoadNil() );
    }
    
    public void loadNil( int count )
    {
        for ( int i = 0; i < count; i++ )
        {
            loadNil();
        }
    }
    
    public void storeVariable( String name )
    {
        CompilerLog.info( "Store Variable: " + name );
        
        if ( _currentFunction.isLocal( name ) )
        { // Local
            int index = _currentFunction.getLocalIndex( name );
            addInstr( InstrStoreL( index ) );
        }
        else if ( _currentFunction.isExternal( name ) )
        { // External
            int index = _currentFunction.getExternalIndex( name );
            addInstr( InstrStoreE( index ) );
        }
        else
        { // Global
            loadEnvironment();
            
            int constant = _currentFunction.getConstantIndex( valueOf( name ) );
            addInstr( InstrLoadC( constant ) );
            addInstr( InstrStoreT() );
        }
    }
    
    public void storeLocal( int index )
    {
        addInstr( InstrStoreL( index ) );
    }
    
    // ========================================
    
    public void createTable( int listElements, int hashPairs )
    {
        CompilerLog.info( "Create Table" );
        
        addInstr( InstrNewTable( listElements, hashPairs ) );
    }
    
    public void loadFromTable()
    {
        CompilerLog.info( "Load from Table" );
        
        addInstr( InstrLoadT() );
    }
    
    public void storeInTable()
    {
        CompilerLog.info( "Store in Table" );
        
        addInstr( InstrStoreT() );
    }
    
    public void loadMethod( String name )
    {
        CompilerLog.info( "Load Method: " + name );
        
        int index = _currentFunction.getConstantIndex( valueOf( name ) );
        addInstr( InstrLoadM( index ) );
    }
    
    // ========================================
    
    public void assignmentOperation( String op )
    {
        CompilerLog.info( "Operator: " + op );
        
        throw new UnsupportedOperationException();
    }
    
    public void unaryOperation( String op )
    {
        CompilerLog.info( "Operator: " + op );
        
        addInstr( InstrUnaryOp( op ) );
    }
    
    public void binaryOperation( String op )
    {
        // Beware of the '%' operator
        CompilerLog.log( Level.INFO, "Operator: " + op );
        
        addInstr( InstrBinaryOp( op ) );
    }
    
    public void logicOperation( String op )
    {
        CompilerLog.info( "Operator: " + op );
        
        if ( ">".equals( op ) )
        {
            addInstr( InstrLte() );
            addInstr( InstrNot() );
        }
        else if ( ">=".equals( op ) )
        {
            addInstr( InstrLt() );
            addInstr( InstrNot() );
        }
        else if ( "!=".equals( op ) )
        {
            addInstr( InstrEq() );
            addInstr( InstrNot() );
        }
        else
        {
            addInstr( InstrLogicalOp( op ) );
        }
    }
    
    // ========================================
    
    /**
     * Mark the beginning of the second expression of an <code>and</code> or <code>or</code> operator.
     */
    public void enterConditionalBlock( String op )
    {
        CompilerLog.info( "Operator: " + op );
        
        if ( "and".equals( op ) )
        {
            addInstr( InstrAnd() );
            _currentFunction.markPendingJump();
        }
        else if ( "or".equals( op ) )
        {
            addInstr( InstrOr() );
            _currentFunction.markPendingJump();
        }
        else
        {
            throw new IllegalArgumentException( op + " is not a valid conditional operator" );
        }
    }
    
    /**
     * Signal the completion of the second expression of an <code>and</code> or <code>or</code> operator.
     */
    public void exitConditionalBlock()
    {
        _currentFunction.setPendingJump( 1 );
    }
    
    // ========================================
    
    /**
     * Creates a closure off the latest child of the current function (the function compiled last).
     * <p>
     * CLOSURE index
     */
    public void createClosure()
    {
        CompilerLog.info( "Create Closure" );
        
        List<FunctionState> childs = _currentFunction.getChilds();
        int index = childs.size() - 1;
        
        addInstr( InstrClosure( index ) );
    }
    
    /**
     * CALL nArgs nReturn
     */
    public void callFunction( int nArgs, int nReturn )
    {
        CompilerLog.info( "Call Function" );
        
        addInstr( InstrCall( nArgs, nReturn ) );
    }
    
    public void returnFunction( int nValues )
    {
        CompilerLog.info( "Return Function" );
        
        addInstr( InstrReturn( nValues ) );
    }
    
    public void tailCall( int nArgs, int nReturn )
    {
        CompilerLog.info( "Tailcall Function" );
        
        addInstr( InstrTailcall( nArgs, nReturn ) );
    }
    
    // ========================================
    
    private static String stripHyphen( String s )
    {
        return StringUtils.strip( s, "\"" );
    }
    
    private static String unescape( String s )
    {
        return StringEscapeUtil.unescape( s );
    }
    
    public static String cleanString( String s )
    {
        return unescape( stripHyphen( s ) );
    }
    
    public static MtsString parseString( String s )
    {
        return valueOf( cleanString( s ) );
    }
    
    public static MtsBoolean parseBoolean( String s )
    {
        return valueOf( Boolean.parseBoolean( s ) );
    }
    
    public static MtsNumber parseNumber( String s )
    {
        String input = s;
        if ( StringUtils.startsWithIgnoreCase( input, "0x" )
             && ( StringUtils.lastIndexOfIgnoreCase( input, "p" ) < 0 ) )
        {
            input = input + "p0";
        }
        
        try
        {
            return valueOf( Double.parseDouble( input ) );
        }
        catch ( NumberFormatException ex )
        {
            throw new NumberFormatException( s );
        }
    }
    
    // ========================================
    
    private static final Pattern _interpolationPattern = Pattern.compile( "(?<!\\\\)\\{([_a-zA-Z][_a-zA-Z0-9]*)\\}" );
    
    public void interpolateString( String s )
    {
        s = cleanString( s );
        Matcher matcher = _interpolationPattern.matcher( s );
        
        int start = 0;
        int end = 0;
        if ( matcher.find() )
        {
            int count = 0;
            do
            {
                end = matcher.start( 0 );
                
                if ( ( end - start ) > 0 )
                {
                    loadConstant( s.substring( start, end ) );
                    count++;
                }
                
                String var = matcher.group( 1 );
                loadVariable( var );
                count++;
                
                start = matcher.end( 0 );
            }
            while ( matcher.find() );
            
            if ( ( s.length() - start ) > 0 )
            {
                loadConstant( s.substring( start ) );
                count++;
            }
            
            addInstr( InstrConcat( count ) );
        }
        else
        {
            loadConstant( s );
        }
    }
    
    // ========================================
    
    public MtsFunctionPrototype compile()
    {
        if ( _currentFunction != _mainFunction )
            throw new IllegalStateException();
        
        return _mainFunction.createPrototype();
    }
}
