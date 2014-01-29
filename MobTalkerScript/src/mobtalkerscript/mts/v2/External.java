package mobtalkerscript.mts.v2;

import mobtalkerscript.mts.v2.instruction.*;
import mobtalkerscript.mts.v2.value.*;

public class External
{
    private final int _index;
    private State _state;
    
    // ========================================
    
    /*
     * Creates an open external, pointing to a local on the given frame.
     */
    public External( int index, MtsFrame frame )
    {
        _index = index;
        _state = new StateOpen( index, frame );
    }
    
    /**
     * Creates an already closed external.
     */
    public External( MtsValue value )
    {
        _index = -1;
        _state = new StateClosed( value );
    }
    
    // ========================================
    
    public int getIndex()
    {
        return _index;
    }
    
    public MtsValue get()
    {
        return _state.get();
    }
    
    public void set( MtsValue value )
    {
        _state.set( value );
    }
    
    // ========================================
    
    public void close()
    {
        if ( _state instanceof StateClosed )
            return;
        
        _state = new StateClosed( get() );
    }
    
    // ========================================
    
    private static abstract class State
    {
        public abstract MtsValue get();
        
        public abstract void set( MtsValue value );
    }
    
    private static final class StateOpen extends State
    {
        private final int _index;
        private final MtsFrame _frame;
        
        public StateOpen( int index, MtsFrame frame )
        {
            _index = index;
            _frame = frame;
        }
        
        @Override
        public MtsValue get()
        {
            return _frame.getLocal( _index );
        }
        
        @Override
        public void set( MtsValue value )
        {
            _frame.setLocal( _index, value );
        }
    }
    
    private static final class StateClosed extends State
    {
        private MtsValue _value;
        
        public StateClosed( MtsValue value )
        {
            _value = value;
        }
        
        @Override
        public MtsValue get()
        {
            return _value;
        }
        
        @Override
        public void set( MtsValue value )
        {
            _value = value;
        }
    }
}
