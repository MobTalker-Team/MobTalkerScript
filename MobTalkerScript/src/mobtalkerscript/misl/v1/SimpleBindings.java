package mobtalkerscript.misl.v1;

import java.util.*;

import mobtalkerscript.misl.v1.value.*;

public class SimpleBindings implements IBindings
{
    
    private final IBindings _parent;
    private final Map<String, MislValue> _bindings;
    private final MislValue _defaultValue;
    
    // ========================================
    
    {
        _bindings = new HashMap<String, MislValue>();
        _defaultValue = MislValue.NIL;
    }
    
    public SimpleBindings()
    {
        _parent = null;
    }
    
    public SimpleBindings(IBindings parent)
    {
        _parent = parent;
    }
    
    // ========================================
    
    @Override
    public IBindings getParent()
    {
        return _parent;
    }
    
    @Override
    public MislValue getDefault()
    {
        return _defaultValue;
    }
    
    @Override
    public boolean contains(String key)
    {
        boolean result = _bindings.containsKey(key);
        
        if (!result && (_parent != null))
        {
            result = _parent.contains(key);
        }
        
        return result;
    }
    
    @Override
    public MislValue get(String key)
    {
        
        if (_bindings.containsKey(key))
        {
            return _bindings.get(key);
        }
        else if ((_parent != null) && _parent.contains(key))
        {
            return _parent.get(key);
        }
        else
        {
            return _defaultValue;
        }
    }
    
    @Override
    public MislValue set(String key, MislValue value)
    {
        MislValue result;
        
        if (_bindings.containsKey(key))
        {
            result = _bindings.put(key, value);
        }
        else
        {
            _bindings.put(key, value);
            result = _defaultValue;
        }
        
        return result;
    }
    
    @Override
    public void copyTo(IBindings target)
    {
        if (this == target)
        {
            return;
        }
        
        for (Map.Entry<String, MislValue> binding : _bindings.entrySet())
        {
            target.set(binding.getKey(), binding.getValue());
        }
    }
    
}
