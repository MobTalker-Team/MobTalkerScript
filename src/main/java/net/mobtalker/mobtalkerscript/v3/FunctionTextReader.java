package net.mobtalker.mobtalkerscript.v3;

import java.io.*;
import java.util.*;

import net.mobtalker.mobtalkerscript.v3.compiler.SourcePosition;
import net.mobtalker.mobtalkerscript.v3.instruction.MtsInstruction;
import net.mobtalker.mobtalkerscript.v3.value.*;

import com.google.common.collect.ImmutableList;
import com.google.common.io.LineReader;

/**
 * Deserializes a function prototype from a textual format.
 */
public class FunctionTextReader
{
    public MtsFunctionPrototype read( Readable readable ) throws IOException
    {
        LineReader reader = new LineReader( readable );
        
        String functionName;
        String source;
        int nParams, maxStackSize;
        boolean hasVarargs;
        int sourceLineStart, sourceLineEnd;
        List<MtsValue> constants;
        List<LocalDescription> locals;
        List<ExternalDescription> externals;
        List<MtsInstruction> instructions;
        
        String line;
        
        // Name
        for ( ;; )
        {
            if ( ( line = reader.readLine() ) == null )
                return null;
            if ( !line.startsWith( ".function" ) )
                continue;
            
            functionName = line.substring( 10 );
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
            sourceLineStart = Integer.parseInt( line.substring( colon + 1, hyphen ) );
            sourceLineEnd = Integer.parseInt( line.substring( hyphen + 1 ) );
            
            assert source != null;
            assert sourceLineStart >= 0;
            assert sourceLineEnd >= 0;
        }
        
        // Parameter count
        if ( ( line = reader.readLine() ) == null )
            throw new EOFException();
        if ( !line.startsWith( ".params" ) )
            throw new IOException( "expected .params tag" );
        nParams = Integer.parseInt( line.substring( 8 ) );
        
        // Stack size
        if ( ( line = reader.readLine() ) == null )
            throw new EOFException();
        if ( !line.startsWith( ".stacksize" ) )
            throw new IOException( "expected .stacksize tag" );
        maxStackSize = Integer.parseInt( line.substring( 11 ) );
        
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
            
            int count = Integer.parseInt( line.substring( 11 ) );
            constants = new ArrayList<>( count );
            
            for ( int i = 0; i < count; i++ )
            {
                if ( ( line = reader.readLine() ) == null )
                    throw new EOFException();
                
                if ( line.charAt( 0 ) == '"' )
                {
                    constants.add( MtsValue.valueOf( line.substring( 1, line.length() - 1 ) ) );
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
            
            int count = Integer.parseInt( line.substring( 8 ) );
            locals = new ArrayList<>( count );
            
            for ( int i = 0; i < count; i++ )
            {
                if ( ( line = reader.readLine() ) == null )
                    throw new EOFException();
                
                int colon = line.indexOf( ':', 1 );
                int hyphen = line.indexOf( '-', colon + 1 );
                locals.add( new LocalDescription( line.substring( 0, colon ), i,
                                                  Integer.parseInt( line.substring( colon + 1, hyphen ) ),
                                                  Integer.parseInt( line.substring( hyphen + 1 ) ) ) );
            }
        }
        
        // Externals
        {
            if ( ( line = reader.readLine() ) == null )
                throw new EOFException();
            if ( !line.startsWith( ".externals" ) )
                throw new IOException( "expected .externals tag" );
            
            int count = Integer.parseInt( line.substring( 11 ) );
            externals = new ArrayList<>( count );
            
            for ( int i = 0; i < count; i++ )
            {
                if ( ( line = reader.readLine() ) == null )
                    throw new EOFException();
                
                int comma1 = line.indexOf( ',', 1 );
                int comma2 = line.indexOf( ',', comma1 + 1 );
                externals.add( new ExternalDescription( line.substring( 0, comma1 ), i,
                                                        Integer.parseInt( line.substring( comma1 + 1, comma2 ) ),
                                                        line.charAt( comma2 + 1 ) == 't' ) );
            }
        }
        
        // Instructions
        {
            if ( ( line = reader.readLine() ) == null )
                throw new EOFException();
            if ( !line.startsWith( ".instructions" ) )
                throw new IOException( "expected .instructions tag" );
            
            int count = Integer.parseInt( line.substring( 14 ) );
            instructions = new ArrayList<>( count );
            
        }
        
        return new MtsFunctionPrototype( ImmutableList.copyOf( instructions ),
                                         maxStackSize, nParams, hasVarargs,
                                         ImmutableList.copyOf( constants ),
                                         ImmutableList.copyOf( locals ),
                                         ImmutableList.copyOf( externals ),
                                         functionName,
                                         Collections.<SourcePosition> emptyList(),
                                         source, sourceLineStart, sourceLineEnd );
    }
}
