package mobtalkerscript.misl.v1.value;

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
