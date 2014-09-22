package net.mobtalker.mobtalkerscript.v2.value.userdata;

import java.lang.reflect.Method;

public class AnnotatedMethod
{
    private final java.lang.reflect.Method _method;
    private final String _name;
    
    // ========================================
    
    public AnnotatedMethod( Method method, String name )
    {
        _method = method;
        _name = name;
    }
    
    // ========================================
    
    public Method getMethod()
    {
        return _method;
    }
    
    public String getName()
    {
        return _name;
    }
}
