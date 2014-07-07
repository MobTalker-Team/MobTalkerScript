package net.mobtalker.mobtalkerscript.v2;

import net.mobtalker.mobtalkerscript.v2.compiler.*;

public abstract class MtsStackTraceElement
{
    private final String _function;
    
    /**
     * For Java functions
     */
    protected MtsStackTraceElement( String function )
    {
        _function = function;
    }
    
    public String getFunction()
    {
        return _function;
    }
    
    public abstract String getSource();
    
    @Override
    public String toString()
    {
        return getSource() + ": in function '" + getFunction() + "'";
    }
    
    // ========================================
    
    public static class Native extends MtsStackTraceElement
    {
        public Native( String function )
        {
            super( function );
        }
        
        @Override
        public String getSource()
        {
            return "[Java]";
        }
    }
    
    // ========================================
    
    public static class Compiled extends MtsStackTraceElement
    {
        private final String _source;
        private final SourcePosition _position;
        
        public Compiled( String source, SourcePosition position, String function )
        {
            super( function );
            _source = source;
            _position = position;
        }
        
        @Override
        public String getSource()
        {
            return _source + ":" + _position.Line;
        }
    }
}
