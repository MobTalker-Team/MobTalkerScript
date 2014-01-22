package mobtalkerscript.misl.v1;

import mobtalkerscript.misl.v1.value.*;

public class External
{
    private final int _index;
    private State _state;
    
    // ========================================
    
    public External( int index, MislFrame frame )
    {
        _index = index;
        _state = new StateOpen( index, frame );
    }
    
    // ========================================
    
    public int getIndex()
    {
        return _index;
    }
    
    public MislValue get()
    {
        return _state.get();
    }
    
    public void set( MislValue value )
    {
        _state.set( value );
    }
    
    // ========================================
    
    public void close()
    {
        _state = new StateClosed( get() );
    }
    
    // ========================================
    
    private static abstract class State
    {
        public abstract MislValue get();
        
        public abstract void set( MislValue value );
    }
    
    private static final class StateOpen extends State
    {
        private final int _index;
        private final MislFrame _frame;
        
        public StateOpen( int index, MislFrame frame )
        {
            _index = index;
            _frame = frame;
        }
        
        @Override
        public MislValue get()
        {
            return _frame.getLocal( _index );
        }
        
        @Override
        public void set( MislValue value )
        {
            _frame.setLocal( _index, value );
        }
    }
    
    private static final class StateClosed extends State
    {
        private MislValue _value;
        
        public StateClosed( MislValue value )
        {
            _value = value;
        }
        
        @Override
        public MislValue get()
        {
            return _value;
        }
        
        @Override
        public void set( MislValue value )
        {
            _value = value;
        }
    }
}
