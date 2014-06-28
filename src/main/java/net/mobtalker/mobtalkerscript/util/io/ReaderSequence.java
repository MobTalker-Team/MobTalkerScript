package mobtalkerscript.util.io;

import java.io.*;
import java.util.*;

import com.google.common.collect.*;
import com.google.common.io.*;

public class ReaderSequence extends Reader
{
    
    private final LinkedList<Reader> _readers;
    
    // ========================================
    
    public ReaderSequence( Iterable<? extends Reader> readers )
    {
        _readers = Lists.newLinkedList( readers );
    }
    
    // ========================================
    
    /**
     * {@inheritDoc}
     * <p>
     * Reads all readers in sequence.
     */
    @Override
    @SuppressWarnings( "resource" )
    public int read( char[] cbuf, int off, int len ) throws IOException
    {
        if ( _readers.isEmpty() )
        {
            return -1;
        }
        
        int readTotal = 0;
        
        while ( !_readers.isEmpty() && ( readTotal < len ) )
        {
            Reader reader = _readers.getFirst();
            
            int read = reader.read( cbuf, off, len );
            
            if ( read == -1 )
            {
                Closeables.close( reader, true );
                _readers.removeFirst();
            }
            else
            {
                readTotal += read;
                off += read;
                len -= read;
            }
        }
        
        return readTotal;
    }
    
    /**
     * Closes all remaining, open readers.
     * <p>
     * Never throws an {@link IOException}.
     */
    @Override
    public void close() throws IOException
    {
        while ( !_readers.isEmpty() )
        {
            Closeables.close( _readers.removeFirst(), true );
        }
    }
    
}
