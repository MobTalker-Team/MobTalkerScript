package mobtalkerscript.mts.v2;

// package mobtalker.script.misl.v1;
//
//import java.util.*;
//import java.util.concurrent.locks.*;
//
//import mobtalker.script.misl.v1.value.*;
//
//import com.google.common.collect.*;
//
//public class ConcurrentBindings implements IBindings
//{
//    
//    private final Map<String, MislValue> _bindings;
//    private final MislValue _defaultValue;
//    
//    private final ReadWriteLock _lock;
//    private final Lock _readLock;
//    private final Lock _writeLock;
//    
//    // ========================================
//    
//    {
//        _bindings = new HashMap<String, MislValue>();
//        
//        _lock = new ReentrantReadWriteLock(true);
//        _readLock = _lock.readLock();
//        _writeLock = _lock.writeLock();
//    }
//    
//    public ConcurrentBindings(MislValue defaultValue)
//    {
//        _defaultValue = defaultValue;
//    }
//    
//    // ========================================
//    
//    @Override
//    public MislValue getDefault()
//    {
//        return _defaultValue;
//    }
//    
//    @Override
//    public boolean contains(String key)
//    {
//        boolean result;
//        
//        _readLock.lock();
//        result = _bindings.containsKey(key);
//        _readLock.unlock();
//        
//        return result;
//    }
//    
//    @Override
//    public MislValue get(String key)
//    {
//        MislValue result;
//        
//        _readLock.lock();
//        if (_bindings.containsKey(key))
//        {
//            result = _bindings.get(key);
//        }
//        else
//        {
//            result = _defaultValue;
//        }
//        _readLock.unlock();
//        
//        return result;
//    }
//    
//    @Override
//    public MislValue set(String key, MislValue value)
//    {
//        MislValue result;
//        
//        _writeLock.lock();
//        if (_bindings.containsKey(key))
//        {
//            result = _bindings.put(key, value);
//        }
//        else
//        {
//            _bindings.put(key, value);
//            result = _defaultValue;
//        }
//        _writeLock.unlock();
//        
//        return result;
//    }
//    
//    @Override
//    public Set<String> getBindings()
//    {
//        Set<String> result = Sets.newHashSet();
//        
//        _readLock.lock();
//        result.addAll(_bindings.keySet());
//        _readLock.unlock();
//        
//        return result;
//    }
//    
//    @Override
//    public void copyTo(IBindings target)
//    {
//        if (this == target)
//        {
//            return;
//        }
//        
//        _readLock.lock();
//        for (Map.Entry<String, MislValue> binding : _bindings.entrySet())
//        {
//            target.set(binding.getKey(), binding.getValue());
//        }
//        _readLock.unlock();
//    }
//    
//}
