package mobtalkerscript.mts.v2;

import java.io.*;
import java.nio.charset.*;
import java.nio.file.*;
import java.util.*;

import mobtalkerscript.misl.v1.instruction.*;

import com.google.common.collect.*;

public class MislScript
{
    
    protected final String _version;
    protected final long _timestamp;
    
    protected final List<MtsInstruction> _instrs;
    protected final IBindings _bindings;
    
    // ========================================
    
    public MislScript( String version, long timestamp, List<MtsInstruction> instrs, IBindings bindings )
    {
        _version = version;
        _timestamp = timestamp;
        _instrs = instrs;
        _bindings = bindings;
    }
    
    // ========================================
    
    public String getVersion()
    {
        return _version;
    }
    
    public long getTimestamp()
    {
        return _timestamp;
    }
    
    public List<MtsInstruction> getInstructions()
    {
        return _instrs;
    }
    
    public IBindings getBindings()
    {
        return _bindings;
    }
    
    // ========================================
    
    public void saveToFile( Path path, Charset charset, OpenOption... options ) throws IOException
    {
        List<String> lines = Lists.newArrayListWithCapacity( _instrs.size() );
        
        for ( MtsInstruction instr : _instrs )
        {
            lines.add( instr.toString() );
        }
        
        Files.write( path, lines, charset, options );
    }
}
