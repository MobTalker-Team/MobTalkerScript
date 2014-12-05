package net.mobtalker.mobtalkerscript.v3.serialization;

import static java.lang.Integer.*;
import static net.mobtalker.mobtalkerscript.v3.instruction.Instructions.*;

import java.io.*;
import java.util.*;

import net.mobtalker.mobtalkerscript.v3.*;
import net.mobtalker.mobtalkerscript.v3.compiler.SourcePosition;
import net.mobtalker.mobtalkerscript.v3.instruction.*;
import net.mobtalker.mobtalkerscript.v3.value.*;

import org.apache.commons.lang3.*;

import com.google.common.collect.ImmutableList;
import com.google.common.io.LineReader;

/**
 * Deserializes a function prototype from a textual format.
 */
public class FunctionTextReader
{
    public MtsFunctionPrototype read( Readable readable ) throws IOException
    {
        return read( new LineReader( readable ) );
    }
    
    public MtsFunctionPrototype read( LineReader reader ) throws IOException
    {
        String functionName;
        String source;
        int nParams, maxStackSize;
        boolean hasVarargs;
        int sourceLineStart, sourceLineEnd;
        List<MtsValue> constants;
        List<LocalDescription> locals;
        List<ExternalDescription> externals;
        List<MtsInstruction> instructions;
        List<SourcePosition> sourcePositions;
        
        String line;
        
        // Name
        for ( ;; )
        {
            if ( ( line = reader.readLine() ) == null )
                throw new EOFException();
            if ( !line.startsWith( ".function" ) )
                throw new IOException( "expected .function tag" );
            
            functionName = line.substring( 10, line.lastIndexOf( '(' ) - 1 );
            assert functionName != null;
            
            break;
        }
        
        // Source
        {
            if ( ( line = reader.readLine() ) == null )
                throw new EOFException();
            if ( !line.startsWith( ".source" ) )
                throw new IOException( "expected .source tag" );
            
            int colon = line.indexOf( ':', 8 );
            source = line.substring( 8, colon );
            
            int hyphen = line.indexOf( '-', colon + 1 );
            sourceLineStart = parseInt( line.substring( colon + 1, hyphen ) );
            sourceLineEnd = parseInt( line.substring( hyphen + 1 ) );
            
            assert source != null;
            assert sourceLineStart >= 0;
            assert sourceLineEnd >= 0;
        }
        
        // Parameter count
        if ( ( line = reader.readLine() ) == null )
            throw new EOFException();
        if ( !line.startsWith( ".params" ) )
            throw new IOException( "expected .params tag" );
        nParams = parseInt( line.substring( 8 ) );
        
        // Stack size
        if ( ( line = reader.readLine() ) == null )
            throw new EOFException();
        if ( !line.startsWith( ".stacksize" ) )
            throw new IOException( "expected .stacksize tag" );
        maxStackSize = parseInt( line.substring( 11 ) );
        
        // Varargs
        if ( ( line = reader.readLine() ) == null )
            throw new EOFException();
        if ( !line.startsWith( ".varargs" ) )
            throw new IOException( "expected .varargs tag" );
        hasVarargs = line.charAt( 9 ) == 't';
        
        // Constants
        {
            if ( ( line = reader.readLine() ) == null )
                throw new EOFException();
            if ( !line.startsWith( ".constants" ) )
                throw new IOException( "expected .constants tag" );
            
            int count = parseInt( line.substring( 11 ) );
            constants = new ArrayList<>( count );
            
            for ( int i = 0; i < count; i++ )
            {
                if ( ( line = reader.readLine() ) == null )
                    throw new EOFException();
                
                line = line.split( ";", 2 )[1];
                
                if ( line.charAt( 0 ) == '"' )
                {
                    String escaped = line.substring( 1, line.length() - 1 );
                    
                    constants.add( MtsValue.valueOf( StringEscapeUtils.unescapeJava( escaped ) ) );
                }
                else
                {
                    constants.add( MtsNumber.parse( line ) );
                }
            }
        }
        
        // Locals
        {
            if ( ( line = reader.readLine() ) == null )
                throw new EOFException();
            if ( !line.startsWith( ".locals" ) )
                throw new IOException( "expected .locals tag" );
            
            int count = parseInt( line.substring( 8 ) );
            locals = new ArrayList<>( count );
            
            for ( int i = 0; i < count; i++ )
            {
                if ( ( line = reader.readLine() ) == null )
                    throw new EOFException();
                
                String[] values = line.split( ";", 3 );
                
                int hyphen = values[2].indexOf( '-', 1 );
                locals.add( new LocalDescription( values[1], i,
                                                  parseInt( values[2].substring( 0, hyphen ) ),
                                                  parseInt( values[2].substring( hyphen + 1 ) ) ) );
            }
        }
        
        // Externals
        {
            if ( ( line = reader.readLine() ) == null )
                throw new EOFException();
            if ( !line.startsWith( ".externals" ) )
                throw new IOException( "expected .externals tag" );
            
            int count = parseInt( line.substring( 11 ) );
            externals = new ArrayList<>( count );
            
            for ( int i = 0; i < count; i++ )
            {
                if ( ( line = reader.readLine() ) == null )
                    throw new EOFException();
                
                String[] values = line.split( ";", 4 );
                
                externals.add( new ExternalDescription( values[1], i,
                                                        parseInt( values[2] ),
                                                        values[3].equals( "t" ) ) );
            }
        }
        
        // Instructions
        {
            if ( ( line = reader.readLine() ) == null )
                throw new EOFException();
            if ( !line.startsWith( ".instructions" ) )
                throw new IOException( "expected .instructions tag" );
            
            int count = parseInt( line.substring( 14 ) );
            instructions = new ArrayList<>( count );
            sourcePositions = new ArrayList<>( count );
            
            for ( int i = 0; i < count; i++ )
            {
                if ( ( line = reader.readLine() ) == null )
                    throw new EOFException();
                
                String[] values = line.split( ";", 4 );
                
                instructions.add( parseInstruction( values[1] ) );
                sourcePositions.add( parseSourcePosition( values[2] ) );
            }
        }
        
        MtsFunctionPrototype prototype = new MtsFunctionPrototype( ImmutableList.copyOf( instructions ),
                                                                   maxStackSize, nParams, hasVarargs,
                                                                   ImmutableList.copyOf( constants ),
                                                                   ImmutableList.copyOf( locals ),
                                                                   ImmutableList.copyOf( externals ),
                                                                   functionName,
                                                                   sourcePositions,
                                                                   source, sourceLineStart, sourceLineEnd );
        
        // Childs
        {
            if ( ( line = reader.readLine() ) == null )
                throw new EOFException();
            if ( !line.startsWith( ".childs" ) )
                throw new IOException( "expected .childs tag" );
            
            int count = parseInt( line.substring( 8 ) );
            
            for ( int i = 0; i < count; i++ )
            {
                MtsFunctionPrototype child = read( reader );
                if ( child == null )
                    throw new EOFException();
                
                prototype.addNestedPrototype( child );
            }
        }
        
        return prototype;
    }
    
    private static SourcePosition parseSourcePosition( String s )
    {
        int splitter = s.indexOf( ':' );
        return new SourcePosition( parseInt( s.substring( 0, splitter ) ),
                                   parseInt( s.substring( splitter + 1 ) ) );
    }
    
    private static MtsInstruction parseInstruction( String s )
    {
        String[] args = StringUtils.split( s );
        
        switch ( args[0] )
        {
            case "ADD":
                return InstrAdd();
            case "AND":
                return InstrAnd( parseInt( args[1] ) );
            case "CALL":
                return InstrCall( parseInt( args[1] ), parseInt( args[2] ) );
            case "CLOSURE":
                return InstrClosure( parseInt( args[1] ) );
            case "CONCAT":
                return InstrConcat( parseInt( args[1] ) );
            case "DIV":
                return InstrDiv();
            case "DUP":
                return InstrDup();
            case "EQ":
                return InstrEq();
            case "GFORLOOP":
                return InstrGForLoop( parseInt( args[1] ), parseInt( args[2] ), parseInt( args[3] ) );
            case "JUMP":
                return InstrJump( parseInt( args[1] ) );
            case "LT":
                return InstrLessThen();
            case "LTE":
                return InstrLessThenEqual();
            case "LINE":
                return new InstrLine( parseInt( args[1] ) );
            case "LOADC":
                return InstrLoadC( parseInt( args[1] ) );
            case "LOADE":
                return InstrLoadE( parseInt( args[1] ) );
            case "LOADFALSE":
                return InstrLoadFalse();
            case "LOADL":
                return InstrLoadL( parseInt( args[1] ) );
            case "LOADM":
                return InstrLoadM( parseInt( args[1] ) );
            case "LOADNIL":
                return InstrLoadNil( parseInt( args[1] ) );
            case "LOADT":
                return InstrLoadT();
            case "LOADTC":
                return InstrLoadTC( parseInt( args[1] ) );
            case "LOADTRUE":
                return InstrLoadTrue();
            case "MOD":
                return InstrMod();
            case "MUL":
                return InstrMul();
            case "NEG":
                return InstrNeg();
            case "NEWTABLE":
                return InstrNewTable( parseInt( args[1] ), parseInt( args[2] ) );
            case "NFORLOOP":
                return InstrNForLoop( parseInt( args[1] ), parseInt( args[2] ) );
            case "NFORPREP":
                return InstrNForPrep( parseInt( args[1] ) );
            case "NOT":
                return InstrNot();
            case "OR":
                return InstrOr( parseInt( args[1] ) );
            case "POP":
                return InstrPop();
            case "POW":
                return InstrPow();
            case "RETURN":
                return InstrReturn( parseInt( args[1] ) );
            case "SIZE":
                return InstrSize();
            case "STOREE":
                return InstrStoreE( parseInt( args[1] ) );
            case "STOREL":
                return InstrStoreL( parseInt( args[1] ) );
            case "STORET":
                return InstrStoreT();
            case "SUB":
                return InstrSub();
            case "TAILCALL":
                return InstrTailcall( parseInt( args[1] ) );
            case "TEST":
                return InstrTest( parseInt( args[1] ) );
            case "VARARGS":
                return InstrVarargs( parseInt( args[1] ) );
            default:
                throw new AssertionError();
        }
    }
}
