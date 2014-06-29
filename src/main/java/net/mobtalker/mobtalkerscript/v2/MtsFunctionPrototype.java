package net.mobtalker.mobtalkerscript.v2;

import static com.google.common.base.Preconditions.*;

import java.util.*;

import net.mobtalker.mobtalkerscript.v2.compiler.*;
import net.mobtalker.mobtalkerscript.v2.instruction.*;
import net.mobtalker.mobtalkerscript.v2.value.*;

import com.google.common.collect.*;

public class MtsFunctionPrototype
{
    private final List<MtsInstruction> _instructions;
    private final int _maxStackSize;
    
    private final List<MtsValue> _constants;
    private final List<ExternalDescription> _externals;
    private final List<LocalDescription> _locals;
    
    private final List<MtsFunctionPrototype> _nestedPrototypes;
    
    // Debug information
    private final String _name;
    private final List<SourcePosition> _lineNumbers;
    private final String _sourceName;
    private final int _sourceLineStart;
    private final int _sourceLineEnd;
    
    // ========================================
    
    public MtsFunctionPrototype( List<MtsInstruction> instructions,
                                 int maxStackSize,
                                 List<MtsValue> constants,
                                 List<ExternalDescription> externals,
                                 List<LocalDescription> locals,
                                 String name,
                                 List<SourcePosition> lineNumbers,
                                 String sourceName,
                                 int sourceStart,
                                 int sourceEnd )
    {
        checkNotNull( instructions );
        checkArgument( 0 <= maxStackSize, "Stack size cannot be negative: " + maxStackSize );
        checkArgument( maxStackSize <= 0xFE, "Stack size exceeds maximum " + maxStackSize );
        
        _instructions = instructions;
        _maxStackSize = maxStackSize;
        
        _constants = constants;
        _externals = externals;
        _locals = locals;
        
        _nestedPrototypes = Lists.newArrayList();
        
        _name = name;
        _lineNumbers = lineNumbers;
        _sourceName = sourceName;
        _sourceLineStart = sourceStart;
        _sourceLineEnd = sourceEnd;
    }
    
    // ========================================
    
    public List<MtsInstruction> getInstructions()
    {
        return _instructions;
    }
    
    public int getMaxStackSize()
    {
        return _maxStackSize;
    }
    
    // ========================================
    
    public int getConstantCount()
    {
        return _constants.size();
    }
    
    public MtsValue getConstant( int index )
    {
        return _constants.get( index );
    }
    
    // ========================================
    
    public int getExternalCount()
    {
        return _externals.size();
    }
    
    public ExternalDescription getExternalDescription( int index )
    {
        return _externals.get( index );
    }
    
    // ========================================
    
    public int getLocalCount()
    {
        return _locals.size();
    }
    
    public LocalDescription getLocalDescription( int index )
    {
        return _locals.get( index );
    }
    
    // ========================================
    
    public void addNestedPrototype( MtsFunctionPrototype prototype )
    {
        _nestedPrototypes.add( prototype );
    }
    
    public MtsFunctionPrototype getNestedPrototype( int i )
    {
        return _nestedPrototypes.get( i );
    }
    
    // ========================================
    
    public String getName()
    {
        return _name;
    }
    
    public String getSource()
    {
        return _sourceName;
    }
    
    public SourcePosition getSourcePosition( int instruction )
    {
        checkElementIndex( instruction, _lineNumbers.size() );
        
        return _lineNumbers.get( instruction );
    }
    
    // ========================================
    
    @Override
    public String toString()
    {
        StringBuilder s = new StringBuilder();
        
        s.append( "function " ).append( _name ).append( '\n' );
        s.append( " source: " )
         .append( _sourceName )
         .append( ':' )
         .append( _sourceLineStart )
         .append( '-' )
         .append( _sourceLineEnd )
         .append( '\n' );
        s.append( " stack: " ).append( _maxStackSize ).append( '\n' );
        s.append( " locals: " ).append( getLocalCount() ).append( '\n' );
        s.append( " externals: " ).append( getExternalCount() ).append( '\n' );
        s.append( " constants: " ).append( getConstantCount() ).append( '\n' );
        s.append( " stacksize: " ).append( _maxStackSize ).append( '\n' );
        s.append( " instructions: " ).append( _instructions.size() );
        
        return s.toString();
    }
    
    public String toString( boolean full )
    {
        if ( !full )
            return toString();
        
        StringBuilder s = new StringBuilder();
        
        s.append( "function " ).append( _name ).append( '\n' );
        s.append( "  source: " )
         .append( _sourceName )
         .append( ':' )
         .append( _sourceLineStart )
         .append( '-' )
         .append( _sourceLineEnd )
         .append( '\n' );
        s.append( "  stack: " ).append( _maxStackSize ).append( '\n' );
        s.append( "  locals: " ).append( getLocalCount() ).append( '\n' );
        s.append( "  externals: " ).append( getExternalCount() ).append( '\n' );
        s.append( "  constants: " ).append( getConstantCount() );
        for ( int i = 0; i < getConstantCount(); i++ )
        {
            s.append( '\n' );
            s.append( "    [" ).append( i ).append( "] " );
            s.append( getConstant( i ) );
        }
        s.append( '\n' );
        
        s.append( "  instructions: " ).append( _instructions.size() );
        for ( int i = 0; i < _instructions.size(); i++ )
        {
            s.append( '\n' );
            s.append( "    [" ).append( i ).append( "] " );
            s.append( _instructions.get( i ) );
        }
        s.append( '\n' );
        
        return s.toString();
    }
    
    public String toString( boolean full, boolean includeChildren )
    {
        if ( !includeChildren )
            return toString( full );
        
        StringBuilder s = new StringBuilder( toString( full ) );
        for ( MtsFunctionPrototype child : _nestedPrototypes )
        {
            s.append( '\n' );
            s.append( child.toString( full, true ) );
        }
        
        return s.toString();
    }
}
