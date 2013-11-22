package mobtalkerscript.misl.v1.value;

import mobtalkerscript.misl.v1.*;

public class MislNil extends MislValue
{
    
    private final MislString stringValue;
    
    // ========================================
    
    MislNil()
    {
        stringValue = MislValue.valueOf( "nil" );
    }
    
    // ========================================
    
    @Override
    public MislString toMtsString()
    {
        return stringValue;
    }
    
    // ========================================
    
    @Override
    public MislValue checkNotNil( String msg, Object... args )
    {
        throw new ScriptRuntimeException( msg, args );
    }
    
    @Override
    public boolean isNil()
    {
        return true;
    }
    
    @Override
    public String getTypeName()
    {
        return "nil";
    }
    
    @Override
    public MislBoolean equal( MislValue x )
    {
        return valueOf( x.isNil() );
    }
    
    // ========================================
    
    public Object toJavaValue()
    {
        return null;
    }
    
    @Override
    public String toString()
    {
        return "nil";
    }
    
}
