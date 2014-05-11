package mobtalkerscript.mts.v2.value;

public class MtsNil extends MtsValue
{
    private final MtsString stringValue;
    
    // ========================================
    
    /* package */MtsNil()
    {
        stringValue = MtsValue.valueOf( "nil" );
    }
    
    // ========================================
    
    @Override
    public boolean isNil()
    {
        return true;
    }
    
    @Override
    public MtsType getType()
    {
        return MtsType.NIL;
    }
    
    // ========================================
    
    public Object toJavaValue()
    {
        return null;
    }
    
    @Override
    public MtsBoolean isMtsEqual( MtsValue x )
    {
        return valueOf( x.isNil() );
    }
    
    @Override
    public MtsString toMtsString()
    {
        return stringValue;
    }
    
    @Override
    public String toString()
    {
        return "nil";
    }
    
}
