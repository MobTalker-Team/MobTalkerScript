package mobtalkerscript.misl.v1.value;

public abstract class MislObject extends MislValue
{
    
//    private final Object _value;
//    
//    // ========================================
//    
//    MtsObject(Object value) {
//        _value = value;
//    }
    
    // ========================================
    
    @Override
    public MislObject asObject() throws ClassCastException
    {
        return this;
    }
    
    // ========================================
    
    @Override
    public MislString toMtsString()
    {
        return MislValue.valueOf(toString());
    }
    
    // ========================================
    
    public abstract MislValue get(MislValue key);
    
    public abstract void set(MislValue key, MislValue value);
    
    // ========================================
    
    @Override
    public boolean isObject()
    {
        return true;
    }
    
    @Override
    public String getTypeName()
    {
        return "userdata";
    }
    
//    @Override
//    public MtsBoolean equal(MtsValue x) {
//        return x.isObject() ? valueOf(x.asObject().equals(_value)) : FALSE;
//    }
    
    // ========================================
    
//    @SuppressWarnings("unchecked")
    public <T> T toJavaValue() throws ClassCastException
    {
        throw new UnsupportedOperationException();
    };
    
    @Override
    public String toString()
    {
        return "userdata"; // #" + _value.toString();
    }
    
//    @Override
//    public int hashCode() {
//        return _value.hashCode();
//    }
    
//    @Override
//    public boolean equals(Object obj) {
//        if (obj instanceof MtsObject) {
//            Object other = ((MtsObject) obj).toJavaValue();
//            return other.equals(_value);
//        }
//        
//        return false;
//    }
    
}
