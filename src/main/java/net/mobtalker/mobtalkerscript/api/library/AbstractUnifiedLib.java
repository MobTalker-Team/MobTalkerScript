package net.mobtalker.mobtalkerscript.api.library;

public class AbstractUnifiedLib<T>
{
    protected final T _logic;
    
    public AbstractUnifiedLib( T logic )
    {
        _logic = logic;
    }
    
    public T getLogic()
    {
        return _logic;
    }
}