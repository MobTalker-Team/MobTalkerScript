/*
 * Copyright (C) 2013-2020 Chimaine, MobTalkerScript contributors
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
package net.mobtalker.mobtalkerscript.v3.compiler;

import com.google.common.collect.Lists;
import net.mobtalker.mobtalkerscript.util.StringEscapeUtil;
import net.mobtalker.mobtalkerscript.v3.*;
import net.mobtalker.mobtalkerscript.v3.compiler.antlr.MtsErrorStrategy;
import net.mobtalker.mobtalkerscript.v3.compiler.antlr.MtsLexerErrorListener;
import net.mobtalker.mobtalkerscript.v3.compiler.antlr.MtsParserErrorListener;
import net.mobtalker.mobtalkerscript.v3.compiler.antlr.generated.Mts3BaseListener;
import net.mobtalker.mobtalkerscript.v3.compiler.antlr.generated.Mts3Lexer;
import net.mobtalker.mobtalkerscript.v3.compiler.antlr.generated.Mts3Parser;
import net.mobtalker.mobtalkerscript.v3.compiler.antlr.generated.Mts3Parser.*;
import net.mobtalker.mobtalkerscript.v3.instruction.InstrLoadTC;
import net.mobtalker.mobtalkerscript.v3.instruction.MtsInstruction;
import net.mobtalker.mobtalkerscript.v3.value.MtsBoolean;
import net.mobtalker.mobtalkerscript.v3.value.MtsNumber;
import net.mobtalker.mobtalkerscript.v3.value.MtsString;
import net.mobtalker.mobtalkerscript.v3.value.MtsValue;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.PredictionMode;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Strings.isNullOrEmpty;
import static net.mobtalker.mobtalkerscript.v3.compiler.CompilerConstants.ENV;
import static net.mobtalker.mobtalkerscript.v3.instruction.Instructions.*;

public class MtsCompiler extends Mts3BaseListener
{
    private static final TokenFactory TokenFactory = new CommonTokenFactory( false );
    
    // ========================================
    
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
        return loadChunk( new ANTLRFileStream( path.toString() ) );
    }
    
    // ========================================
    
    public static MtsFunctionPrototype loadChunk( String chunk, String source ) throws Exception
    {
        checkNotNull( chunk, "chunk" );
        
        ANTLRInputStream stream = new ANTLRInputStream( chunk );
        stream.name = source;
        
        return loadChunk( stream );
    }
    
    // ========================================
    
    public static MtsFunctionPrototype loadChunk( CharStream stream ) throws IOException, MtsSyntaxError
    {
        Mts3Parser parser = getParser( stream );
        
        ChunkContext chunk;
        try
        {
            chunk = parser.chunk();
        }
        catch ( MtsSyntaxError ex )
        {
            // Re-throw to hide the stack trace
            throw new MtsSyntaxError( ex.getSourceName(), ex.getSourcePosition(), ex.getOriginalMessage() );
        }
        
//        new TreeViewer( Arrays.asList( parser.getRuleNames() ), chunk ).open();
        
        int lineStart = chunk.getStart().getLine();
        
        // stop token CAN be null if the input is empty and contains only comments and EOF
        int lineEnd = chunk.getStop() != null ? chunk.getStop().getLine() : lineStart;
        
        // Compile it
        MtsCompiler compiler = new MtsCompiler( stream.getSourceName(), lineStart, lineEnd );
        compiler.enterChunk( chunk );
        
        return compiler.compile();
    }
    
    // ========================================
    
    public static Mts3Lexer getLexer( CharStream stream )
    {
        Mts3Lexer lexer = new Mts3Lexer( stream );
        lexer.setTokenFactory( TokenFactory );
        lexer.removeErrorListeners();
        lexer.addErrorListener( new MtsLexerErrorListener() );
        
        return lexer;
    }
    
    public static Mts3Parser getParser( CharStream stream )
    {
        Mts3Lexer lexer = getLexer( stream );
        
        Mts3Parser parser = new Mts3Parser( new CommonTokenStream( lexer ) );
        parser.removeErrorListeners();
        parser.addErrorListener( new MtsParserErrorListener() );
        parser.setErrorHandler( new MtsErrorStrategy() );
        parser.getInterpreter().setPredictionMode( PredictionMode.SLL );
        
        return parser;
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
        _mainFunction = new FunctionState( null, "main", 0, true,
                                           sourceName, sourceLineStart, sourceLineEnd );
        _mainFunction.addExternal( new ExternalDescription( ENV, 0, true, 0 ) );
        
        _currentFunction = _mainFunction;
        
        _sourceName = sourceName;
    }
    
    // ========================================
    
    public String getSourceName()
    {
        return _sourceName;
    }
    
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
    
    private void addInstr( MtsInstruction instr )
    {
        _currentFunction.addInstruction( instr, _curPosition );
    }
    
//    public void discardValue()
//    {
//        addInstr( InstrPop() );
//    }
    
    public void duplicateValue()
    {
        addInstr( InstrDup() );
    }
    
    // ========================================
    
    public void enterFunction( String name, int sourceLineStart, int sourceLineEnd, List<String> params, boolean isVarargs )
    {
        FunctionState child = new FunctionState( _currentFunction, name, params.size(), isVarargs,
                                                 _sourceName, sourceLineStart, sourceLineEnd );
        _currentFunction.addChild( child );
        _currentFunction = child;
        
        for ( String param : params )
        {
            declareLocal( param );
        }
    }
    
    public void exitFunction()
    {
        addInstr( InstrReturn( 0 ) );
        _currentFunction = _currentFunction.getParent();
    }
    
    // ========================================
    
    public void enterBlock()
    {
        _currentFunction.enterBlock( _curPosition );
    }
    
    public void exitBlock()
    {
        _currentFunction.exitBlock();
    }
    
    // ========================================
    
    public void enterWhileLoop()
    {
        _currentFunction.enterLoop();
    }
    
    public void enterWhileBody()
    {
        addInstr( InstrTest() );
        _currentFunction.markBreak();
        enterBlock();
    }
    
    public void exitWhileLoop()
    {
        addInstr( InstrJump() );
        _currentFunction.exitLoop();
        exitBlock();
    }
    
    // ========================================
    
    public void enterRepeatLoop()
    {
        enterBlock();
        _currentFunction.enterLoop();
    }
    
    public void enterUntilConditon()
    {}
    
    public void exitRepeatLoop()
    {
        addInstr( InstrTest() );
        _currentFunction.exitLoop();
        exitBlock();
    }
    
    // ========================================
    
    public void enterNumericForLoop( String varName )
    {
        enterBlock();
        _currentFunction.enterNumericForLoop( varName );
    }
    
    public void enterGenericForLoop( List<String> varNames )
    {
        enterBlock();
        _currentFunction.enterGenericForLoop( varNames );
    }
    
    public void exitForLoop()
    {
        addInstr( InstrJump() );
        _currentFunction.exitLoop();
        exitBlock();
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
    
    // public abstract void gotoFunction( String name );
    
    // ========================================
    
    public LocalDescription declareLocal( String name )
    {
        try
        {
            return _currentFunction.declareLocal( name );
        }
        catch ( MtsParserException ex )
        {
            throw new MtsSyntaxError( _sourceName, _curPosition, ex.getMessage() );
        }
    }
    
    public LocalDescription declareAnonymousLocal( String name )
    {
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
            loadFromTable( name );
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
        
        int index = _currentFunction.getConstantIndex( value );
        addInstr( InstrLoadC( index ) );
    }
    
    public void loadCharString( String s )
    {
        loadConstant( MtsString.of( cleanString( s ) ) );
    }
    
    public void loadInterpolatedString( String s )
    {
        new StringInterpolator( this ).interpolate( cleanString( s ) );
    }
    
    public void loadNil( int count )
    {
        if ( count < 1 )
            return;
        
        addInstr( InstrLoadNil( count ) );
    }
    
    public void loadBoolean( boolean b )
    {
        addInstr( b ? InstrLoadTrue() : InstrLoadFalse() );
    }
    
    public void loadBoolean( MtsBoolean b )
    {
        addInstr( b == MtsBoolean.True ? InstrLoadTrue() : InstrLoadFalse() );
    }
    
    public void loadVarargs( int count )
    {
        addInstr( InstrVarargs( count ) );
    }
    
    // ========================================
    
    public void storeVariable( String name )
    {
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
            
            int constant = _currentFunction.getConstantIndex( MtsString.of( name ) );
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
        
        addInstr( InstrNewTable( listElements, hashPairs ) );
    }
    
    public void loadFromTable()
    {
        addInstr( InstrLoadT() );
    }
    
    public void loadFromTable( String field )
    {
        loadFromTable( MtsString.of( field ) );
    }
    
    public void loadFromTable( MtsValue field )
    {
        int index = _currentFunction.getConstantIndex( field );
        addInstr( new InstrLoadTC( index ) );
    }
    
    public void storeInTable()
    {
        addInstr( InstrStoreT() );
    }
    
    public void storeInTable( String field )
    {
        storeInTable( MtsString.of( field ) );
    }
    
    public void storeInTable( MtsValue field )
    {
        loadConstant( field );
        storeInTable();
    }
    
    public void loadMethod( String name )
    {
        int index = _currentFunction.getConstantIndex( MtsString.of( name ) );
        addInstr( InstrLoadM( index ) );
    }
    
    // ========================================
    
    public void concatStrings( int count )
    {
        addInstr( InstrConcat( count ) );
    }
    
    public void assignmentOperation( String op )
    {
        throw new UnsupportedOperationException();
    }
    
    public void unaryOperation( String op )
    {
        addInstr( InstrUnaryOp( op ) );
    }
    
    public void binaryOperation( String op )
    {
        addInstr( InstrBinaryOp( op ) );
    }
    
    public void logicOperation( String op )
    {
        if ( ">".equals( op ) )
        {
            addInstr( InstrLessThenEqual() );
            addInstr( InstrNot() );
        }
        else if ( ">=".equals( op ) )
        {
            addInstr( InstrLessThen() );
            addInstr( InstrNot() );
        }
        else if ( "~=".equals( op ) )
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
        if ( "and".equals( op ) )
        {
            addInstr( InstrAnd() );
        }
        else if ( "or".equals( op ) )
        {
            addInstr( InstrOr() );
        }
        else
        {
            throw new IllegalArgumentException( op + " is not a valid conditional operator" );
        }
        
        _currentFunction.markPendingJump();
    }
    
    /**
     * Signal the completion of the second expression of an <code>and</code> or <code>or</code> operator.
     */
    public void exitConditionalBlock()
    {
        _currentFunction.setPendingJump( 1 );
    }
    
    // ========================================
    
    public void loadStringExpression( String input )
    {
        checkArgument( !isNullOrEmpty( input ), "input cannot be null or empty" );
        
        ANTLRInputStream stream = new ANTLRInputStream( input );
        stream.name = getSourceName() + ":" + getSourcePosition();
        
        Mts3Parser parser = getParser( stream );
        
        ExprContext expr;
        try
        {
            expr = parser.expr();
        }
        catch ( MtsSyntaxError ex )
        {
            throw new MtsSyntaxError( ex.getSourceName(), ex.getSourcePosition(), ex.getOriginalMessage() );
        }
        
        enterExpr( expr );
    }
    
    // ========================================
    
    /**
     * Creates a closure off the latest child of the current function (the function compiled last).
     * <p>
     * CLOSURE index
     */
    public void createClosure()
    {
        List<FunctionState> childs = _currentFunction.getChilds();
        addInstr( InstrClosure( childs.size() - 1 ) );
    }
    
    /**
     * CALL nArgs nReturn
     */
    public void callFunction( int nArgs, int nReturn )
    {
        addInstr( InstrCall( nArgs, nReturn ) );
    }
    
    public void returnFunction( int nValues )
    {
        addInstr( InstrReturn( nValues ) );
    }
    
    public void tailcallFunction( int nArgs )
    {
        addInstr( InstrTailcall( nArgs ) );
    }
    
    // ========================================
    
    private static String unescape( String s )
    {
        return StringEscapeUtil.unescape( s );
    }
    
    private static String cleanString( String s )
    {
        return unescape( s );
    }
    
    // ========================================
    
    public MtsFunctionPrototype compile()
    {
        if ( _currentFunction != _mainFunction )
            throw new IllegalStateException();
        
        return _mainFunction.createPrototype();
    }
    
    // ========================================================================================================================
    // ANTLR stuff
    
    @Override
    public void enterChunk( ChunkContext ctx )
    {
        visitChildren( ctx );
        returnFunction( 0 );
    }
    
    // ========================================
    // Literals
    
    @Override
    public void enterNilLiteral( NilLiteralContext ctx )
    {
        loadNil( 1 );
    }
    
    @Override
    public void enterBooleanLiteral( BooleanLiteralContext ctx )
    {
        loadBoolean( MtsBoolean.parse( ctx.getText() ) );
    }
    
    @Override
    public void enterNumberLiteral( NumberLiteralContext ctx )
    {
        loadConstant( MtsNumber.parse( ctx.getText() ) );
    }
    
    @Override
    public void enterCharString( CharStringContext ctx )
    {
        String s = ctx.getText();
        loadCharString( s.substring( 1, s.length() - 1 ) );
    }
    
    @Override
    public void enterNormalString( NormalStringContext ctx )
    {
        String s = ctx.getText();
        loadInterpolatedString( s.substring( 1, s.length() - 1 ) );
    }
    
    @Override
    public void enterLongString( LongStringContext ctx )
    {
        String s = ctx.getText();
        int tokenLength = s.indexOf( '[', 1 ) + 1;
        loadCharString( s.substring( tokenLength, s.length() - tokenLength ) );
    }
    
    // ========================================
    // Variables
    
    @Override
    public void enterVar( VarContext ctx )
    {
        List<VarSuffixContext> suffixes = ctx.Suffixes;
        boolean hasSuffixes = ( suffixes != null ) && !suffixes.isEmpty();
        boolean isAccess = ctx.getParent() instanceof VarOrExprContext;
        
        if ( ctx.Name != null )
        {
            if ( hasSuffixes || isAccess )
            {
                loadVariable( ctx.Name.getText() );
            }
            else
            {
                storeVariable( ctx.Name.getText() );
                return;
            }
        }
        else if ( ctx.Expr != null )
        {
            visit( ctx.Expr );
        }
        else
        {
            throw new AssertionError();
        }
        
        if ( ( suffixes == null ) || suffixes.isEmpty() )
            return;
        
        getLast( suffixes ).isAccess = isAccess;
        visit( suffixes );
    }
    
    @Override
    public void enterFieldNameSuffix( FieldNameSuffixContext ctx )
    {
        visit( ctx.Calls );
        
        if ( ctx.isAccess )
        {
            loadFromTable( ctx.FieldName.getText() );
        }
        else
        {
            storeInTable( ctx.FieldName.getText() );
        }
    }
    
    @Override
    public void enterFieldExprSuffix( FieldExprSuffixContext ctx )
    {
        visit( ctx.Calls );
        
        MtsValue field = null;
        
        if ( ctx.FieldExpr instanceof LiteralExprContext )
        {
            LiteralContext literal = ( (LiteralExprContext) ctx.FieldExpr ).Literal;
            if ( literal instanceof NumberLiteralContext )
            {
                field = MtsNumber.parse( literal.getText() );
            }
            else
            {
                visit( literal );
            }
        }
        else
        {
            visit( ctx.FieldExpr );
        }
        
        if ( ctx.isAccess )
        {
            if ( field == null )
                loadFromTable();
            else
                loadFromTable( field );
        }
        else
        {
            if ( field == null )
                storeInTable();
            else
                storeInTable( field );
        }
    }
    
    @Override
    public void enterAssignmentExpr( AssignmentExprContext ctx )
    {
        visit( ctx.Expr );
        if ( ctx.nResults > 0 )
            duplicateValue();
        
        visit( ctx.Var );
    }
    
    @Override
    public void enterAssignmentStmt( AssignmentStmtContext ctx )
    {
        List<VarContext> vars = ctx.VarList.Vars;
        ExprListContext exprList = ctx.ExprList;
        
        exprList.nTargets = vars.size();
        visit( exprList );
        
        for ( VarContext var : Lists.reverse( vars ) )
        {
            visit( var );
        }
    }
    
    @Override
    public void enterLocalVariableDeclarationStmt( LocalVariableDeclarationStmtContext ctx )
    {
        List<Token> names = ctx.NameList.Names;
        
        ExprListContext exprList = ctx.ExprList;
        if ( ( exprList != null ) && !exprList.isEmpty() )
        {
            // Expressions are evaluated left to right, so they must be assigned right to left.
            // They also cannot be declared before the expressions are evaluated, because of potential shadowing
            // However, order of declaration should be preserved
            
            exprList.nTargets = names.size();
            visit( exprList );
            
            for ( Token identifier : names )
            {
                declareLocal( identifier.getText() );
            }
            for ( Token identifier : Lists.reverse( names ) )
            {
                storeVariable( identifier.getText() );
            }
        }
        else
        {
            for ( Token identifier : names )
            {
                declareLocal( identifier.getText() );
            }
        }
    }
    
    @Override
    public void enterVarargsExpr( VarargsExprContext ctx )
    {
        loadVarargs( ctx.nResults );
    }
    
    // ========================================
    // Blocks
    
    @Override
    public void enterBlockStmt( BlockStmtContext ctx )
    {
        enterBlock();
        visitChildren( ctx );
        exitBlock();
    }
    
    // ========================================
    // Operators
    
    @Override
    public void enterUnaryExpr( UnaryExprContext ctx )
    {
        visit( ctx.Expr );
        unaryOperation( ctx.Operator.getText() );
    }
    
    @Override
    public void enterBinaryExpr( BinaryExprContext ctx )
    {
        visit( ctx.Left );
        visit( ctx.Right );
        binaryOperation( ctx.Operator.getText() );
    }
    
    @Override
    public void enterLogicalExpr( LogicalExprContext ctx )
    {
        visit( ctx.Left );
        visit( ctx.Right );
        logicOperation( ctx.Operator.getText() );
    }
    
    @Override
    public void enterConditionalExpr( ConditionalExprContext ctx )
    {
        visit( ctx.Left );
        enterConditionalBlock( ctx.Operator.getText() );
        visit( ctx.Right );
        exitConditionalBlock();
    }
    
    // ========================================
    // Table constructors
    
    @Override
    public void enterTableCtor( TableCtorContext ctx )
    {
        if ( ctx.FieldList == null )
        {
            createTable( 0, 0 );
            return;
        }
        
        int nPairs = 0;
        
        List<ListFieldContext> listFields = new ArrayList<>();
        for ( FieldContext field : ctx.FieldList.Fields )
        {
            if ( field instanceof ListFieldContext )
            {
                listFields.add( (ListFieldContext) field );
            }
            else
            {
                nPairs++;
                visit( field );
            }
        }
        
        if ( !listFields.isEmpty() )
        {
            getLast( listFields ).Expr.nResults = -1;
            visit( listFields );
        }
        
        createTable( listFields.size(), nPairs );
    }
    
    @Override
    public void enterNameField( NameFieldContext ctx )
    {
        loadConstant( MtsString.of( ctx.Field.getText() ) );
        visit( ctx.Expr );
    }
    
    @Override
    public void enterExprField( ExprFieldContext ctx )
    {
        visit( ctx.Field );
        visit( ctx.Expr );
    }
    
    // ========================================
    // Expression list result adjustment
    
    @Override
    public void enterExprList( ExprListContext ctx )
    {
        int nTargets = ctx.nTargets;
        assert ( nTargets == -1 ) || ( nTargets > 0 );
        
        List<ExprContext> exprs = ctx.Exprs;
        int nExprs = exprs.size();
        int lastIndex = nExprs - 1;
        
        if ( nTargets == -1 )
        {
            exprs.get( lastIndex ).nResults = -1;
            visit( exprs );
        }
        else
        {
            int unsatisfiedTargets = nTargets;
            
            for ( int i = 0; i < nExprs; i++ )
            {
                ExprContext expr = exprs.get( i );
                
                if ( i < lastIndex )
                {
                    expr.nResults = i < nTargets ? 1 : 0;
                    unsatisfiedTargets--;
                }
                else
                {
                    expr.nResults = i < nTargets ? nTargets - i : 0;
                    
                    if ( isCallOrVarargs( expr ) )
                    {
                        unsatisfiedTargets = 0;
                    }
                    else
                    {
                        unsatisfiedTargets--;
                    }
                }
                
                visit( expr );
            }
            
            loadNil( unsatisfiedTargets );
        }
    }
    
    private static boolean isCallOrVarargs( ExprContext ctx )
    {
        if ( ctx instanceof VarargsExprContext )
            return true;
        if ( ctx instanceof PrefixExprContext )
            return !( (PrefixExprContext) ctx ).Calls.isEmpty();
        return false;
    }
    
    // ========================================
    // Jumps
    
    @Override
    public void enterLabelStmt( LabelStmtContext ctx )
    {
        declareLabel( ctx.Name.getText() );
    }
    
    @Override
    public void enterGotoStmt( GotoStmtContext ctx )
    {
        gotoLabel( ctx.Target.getText() );
    }
    
    // ========================================
    // Conditionals
    
    @Override
    public void enterIfThenElseStmt( IfThenElseStmtContext ctx )
    {
        enterIfThenElseBlock();
        visit( ctx.IfCondition );
        endIfCondition();
        
        enterBlock();
        visit( ctx.IfBody );
        exitBlock();
        endThenBlock();
        
        if ( ( ctx.ElseIfBlock != null ) && !ctx.ElseIfBlock.isEmpty() )
        {
            int nElseIfs = ctx.ElseIfBlock.size();
            for ( int i = 0; i < nElseIfs; i++ )
            {
                enterIfCondition();
                visit( ctx.ElseIfCondition.get( i ) );
                endIfCondition();
                
                enterBlock();
                visit( ctx.ElseIfBlock.get( i ) );
                exitBlock();
                endThenBlock();
            }
        }
        
        if ( ctx.ElseBlock != null )
        {
            enterElseBlock();
            enterBlock();
            visit( ctx.ElseBlock );
            exitBlock();
        }
        
        exitIfThenElse();
    }
    
    @Override
    public void enterMenuStmt( MenuStmtContext ctx )
    {
        enterBlock();
        int choiceIndex = declareAnonymousLocal( "choice" ).getIndex();
        
        loadVariable( "ShowMenu" );
        if ( ctx.Caption != null )
        {
            visit( ctx.Caption );
        }
        else
        {
            loadNil( 1 );
        }
        
        int nOptions = ctx.Options.size();
        for ( int i = 0; i < nOptions; i++ )
        {
            visit( ctx.Options.get( i ).Caption );
        }
        
        callFunction( 1 + nOptions, 1 );
        storeLocal( choiceIndex );
        
        enterIfThenElseBlock();
        
        for ( int i = 0; i < nOptions; i++ )
        {
            if ( i > 0 )
            {
                enterIfCondition();
            }
            
            loadLocal( choiceIndex );
            loadConstant( MtsNumber.of( i + 1 ) );
            logicOperation( "==" );
            endIfCondition();
            visit( ctx.Options.get( i ).Block );
            endThenBlock();
        }
        
        exitIfThenElse();
        exitBlock();
    }
    
    // ========================================
    // Loops
    
    @Override
    public void enterBreakStmt( BreakStmtContext ctx )
    {
        breakLoop();
    }
    
    @Override
    public void enterWhileStmt( WhileStmtContext ctx )
    {
        enterWhileLoop();
        visit( ctx.Condition );
        enterWhileBody();
        visit( ctx.Block );
        exitWhileLoop();
    }
    
    @Override
    public void enterRepeatStmt( RepeatStmtContext ctx )
    {
        enterRepeatLoop();
        visit( ctx.Block );
        enterUntilConditon();
        visit( ctx.Condition );
        exitRepeatLoop();
    }
    
    @Override
    public void enterNumericForStmt( NumericForStmtContext ctx )
    {
        visit( ctx.Init );
        visit( ctx.Limit );
        
        if ( ctx.Update != null )
        {
            visit( ctx.Update );
        }
        else
        {
            loadConstant( MtsNumber.One );
        }
        
        enterNumericForLoop( ctx.Var.getText() );
        visit( ctx.Block );
        exitForLoop();
    }
    
    @Override
    public void enterGenericForStmt( GenericForStmtContext ctx )
    {
        ctx.ExprList.nTargets = 3;
        visit( ctx.ExprList );
        
        List<String> names = new ArrayList<>();
        for ( Token nameToken : ctx.NameList.Names )
        {
            names.add( nameToken.getText() );
        }
        
        enterGenericForLoop( names );
        visit( ctx.Block );
        exitForLoop();
    }
    
    // ========================================
    // Calls and returns
    
    @Override
    public void enterNameAndArgs( NameAndArgsContext ctx )
    {
        int nArgs = getArgCount( ctx );
        int nReturn = getReturnCount( ctx );
        
        Token method = ctx.Method;
        if ( method != null )
        {
            loadMethod( method.getText() );
            nArgs++;
        }
        
        visit( ctx.Args );
        
        if ( isTailcall( ctx ) )
        {
            tailcallFunction( nArgs );
        }
        else
        {
            callFunction( nArgs, nReturn );
        }
    }
    
    private static int getArgCount( NameAndArgsContext ctx )
    {
        if ( !( ctx.Args instanceof ArgListContext ) )
            return 1;
        
        ExprListContext exprList = ( (ArgListContext) ctx.Args ).ExprList;
        return ( exprList != null ) ? exprList.Exprs.size() : 0;
    }
    
    private static int getReturnCount( NameAndArgsContext ctx )
    {
        if ( ctx.getParent() instanceof PrefixExprContext )
        {
            PrefixExprContext parent = (PrefixExprContext) ctx.getParent();
            return getLast( parent.Calls ).equals( ctx ) ? parent.nResults : 1;
        }
        if ( ( ctx.getParent() instanceof CallStmtContext ) && ctx.equals( ( (CallStmtContext) ctx.getParent() ).LastCall ) )
            return 0;
        
        return 1;
    }
    
    private static boolean isTailcall( NameAndArgsContext ctx )
    {
        ParserRuleContext parent = ctx.getParent();
        return ( parent instanceof PrefixExprContext )
               && getLast( ( (PrefixExprContext) parent ).Calls ).equals( ctx )
               && ( parent.getParent().getParent() instanceof ReturnStmtContext )
               && ( ( (ExprListContext) parent.getParent() ).Exprs.size() == 1 );
    }
    
    // ========================================
    // Special form calls
    
    @Override
    public void enterSayStmt( SayStmtContext ctx )
    {
        loadVariable( "ShowText" );
        
        List<ExprContext> args = ctx.Args.Exprs;
        if ( args.size() > 1 )
        {
            visit( args.get( 0 ) );
            visit( args.get( 1 ) );
        }
        else
        {
            loadConstant( MtsString.Empty );
            visit( args.get( 0 ) );
        }
        
        loadBoolean( ctx.AsConclusion != null );
        
        callFunction( 3, 0 );
    }
    
    @Override
    public void enterShowStmt( ShowStmtContext ctx )
    {
        loadVariable( "ShowSprite" );
        
        if ( ctx.Args.Exprs.size() > 1 )
        {
            ctx.Args.nTargets = 2;
            visit( ctx.Args );
        }
        else
        {
            loadNil( 1 );
            visit( ctx.Args.Exprs.get( 0 ) );
        }
        
        if ( ctx.At != null )
        {
            visit( ctx.At );
        }
        else
        {
            loadNil( 1 );
        }
        
        if ( ctx.Offsets != null )
        {
            ctx.Offsets.nTargets = 2;
            visit( ctx.Offsets );
            
            callFunction( 5, 0 );
        }
        else
        {
            callFunction( 3, 0 );
        }
    }
    
    @Override
    public void enterSceneStmt( SceneStmtContext ctx )
    {
        loadVariable( "ShowScene" );
        
        visit( ctx.Group );
        
        if ( ctx.As != null )
        {
            visit( ctx.As );
        }
        else
        {
            loadNil( 1 );
        }
        
        callFunction( 2, 0 );
    }
    
    @Override
    public void enterHideStmt( HideStmtContext ctx )
    {
        loadVariable( Reference.FunctionNames.COMMAND_HIDE );
        
        if ( ctx.Group != null )
        {
            visit( ctx.Group );
        }
        else
        {
            loadConstant( MtsString.of( "scene" ) );
        }
        
        callFunction( 1, 0 );
    }
    
    // ========================================
    // Function declarations
    
    @Override
    public void enterFunctionDefinitionExpr( FunctionDefinitionExprContext ctx )
    {
        enterFunction( "?", ctx.getStart().getLine(), ctx.getStop().getLine(),
                       false, ctx.Body.ParamList, ctx.Body.Block );
        createClosure();
    }
    
    @Override
    public void enterFunctionDefinitionStmt( FunctionDefinitionStmtContext ctx )
    {
        FuncNameContext nameCtx = ctx.Name;
        boolean isMethod = nameCtx.Method != null;
        List<Token> fields = nameCtx.Fields;
        boolean isTableName = ( fields != null ) && !fields.isEmpty();
        String rootName = nameCtx.Root.getText();
        
        String funcName;
        if ( isMethod )
            funcName = nameCtx.Method.getText();
        else if ( isTableName )
            funcName = getLast( fields ).getText();
        else
            funcName = rootName;
        
        enterFunction( funcName, ctx.getStart().getLine(), ctx.getStop().getLine(),
                       isMethod, ctx.Body.ParamList, ctx.Body.Block );
        createClosure();
        
        if ( isTableName || isMethod )
        {
            loadVariable( rootName );
            
            if ( isTableName )
            {
                int lastIndex = fields.size() - 1;
                String lastFieldName = fields.get( lastIndex ).getText();
                for ( int i = 0; i < lastIndex; i++ )
                {
                    loadFromTable( fields.get( i ).getText() );
                }
                
                if ( isMethod )
                {
                    loadFromTable( lastFieldName );
                }
            }
            
            storeInTable( funcName );
        }
        else
        {
            storeVariable( rootName );
        }
    }
    
    @Override
    public void enterLocalFunctionDefinitionStmt( LocalFunctionDefinitionStmtContext ctx )
    {
        String name = ctx.Name.getText();
        declareLocal( name );
        
        enterFunction( name, ctx.getStart().getLine(), ctx.getStop().getLine(),
                       false, ctx.Body.ParamList, ctx.Body.Block );
        createClosure();
        
        storeVariable( name );
    }
    
    private void enterFunction( String name, int start, int end,
                                boolean isMethod, ParamListContext paramList, BlockContext block )
    {
        List<String> paramNames = new ArrayList<>( 8 );
        boolean isVarargs = false;
        
        if ( isMethod )
        {
            paramNames.add( "self" );
        }
        
        if ( paramList != null )
        {
            NameListContext params = paramList.Params;
            if ( params != null )
            {
                for ( Token paramToken : params.Names )
                {
                    paramNames.add( paramToken.getText() );
                }
            }
            
            isVarargs = paramList.Varargs != null;
        }
        
        enterFunction( name, start, end, paramNames, isVarargs );
        visit( block );
        exitFunction();
    }
    
    @Override
    public void enterReturnStmt( ReturnStmtContext ctx )
    {
        if ( ctx.ExprList == null )
        {
            returnFunction( 0 );
            return;
        }
        
        visit( ctx.ExprList );
        returnFunction( ctx.ExprList.Exprs.size() );
    }
    
    // ========================================
    
    @Override
    public void visit( ParseTree tree )
    {
        if ( tree == null )
            return;
        
        if ( tree instanceof ParserRuleContext )
        {
            Token start = ( (ParserRuleContext) tree ).getStart();
            setSourcePosition( start.getLine(), start.getCharPositionInLine() );
        }
        
        super.visit( tree );
    }
    
    private void visit( List<? extends ParserRuleContext> ctxs )
    {
        if ( ( ctxs == null ) || ctxs.isEmpty() )
            return;
        
        for ( ParserRuleContext ctx : ctxs )
        {
            visit( ctx );
        }
    }
    
    // ========================================
    
    private static <T> T getLast( List<T> list )
    {
        return list.get( list.size() - 1 );
    }
}
