package mobtalkerscript.misl.v1.value;

import mobtalkerscript.misl.v1.*;
import mobtalkerscript.misl.v1.instruction.*;

/**
 * A function that was parsed from a script file.
 * 
 * @author Chimaine
 */
public class MislFunction extends MislValue
{
    
    static final MislValue[] EMPTY_ARGS = {};
    
    // ========================================
    
    private final InstrLabel _instruction;
    private final int _argCount;
    
    // ========================================
    
    public MislFunction( InstrLabel instruction, int argCount )
    {
        if ( instruction == null )
        {
            throw new NullPointerException( "instruction" );
        }
        
        _instruction = instruction;
        _argCount = argCount;
    }
    
    // ========================================
    
    public InstrLabel getInstruction()
    {
        return _instruction;
    }
    
    public int getParamCount()
    {
        return _argCount;
    }
    
    // ========================================
    
    @Override
    public MislFunction asFunction()
    {
        return this;
    }
    
    // ========================================
    
    @Override
    public MislString toMtsString()
    {
        throw new ScriptRuntimeException( "Cannot convert a function to a string" );
    }
    
    // ========================================
    
    @Override
    public boolean isFunction()
    {
        return true;
    }
    
    @Override
    public String getTypeName()
    {
        return "function";
    }
    
    @Override
    public MislBoolean equal( MislValue x )
    {
        return valueOf( equals( x ) );
    }
    
    // ========================================
    
    @Override
    public String toString()
    {
        return "func#" + Integer.toHexString( hashCode() );
    }
    
    @Override
    public int compareTo( MislValue o )
    {
        return hashCode() - o.hashCode();
    }
    
}
