package mobtalkerscript.misl.v1.instruction;

import mobtalkerscript.misl.v1.*;
import mobtalkerscript.misl.v1.value.*;

public class InstrPush extends AbstractMislInstruction
{
    private final MislValue _value;
    
    // ========================================
    
    public InstrPush( MislValue value )
    {
        if ( !( value.isNumber() || value.isBoolean() || value.isString() || value.isNil() ) )
        {
            throw new ScriptEngineException( "can only push constants" );
        }
        
        _value = value;
    }
    
    public InstrPush( String value )
    {
        this( MislValue.valueOf( value ) );
    }
    
    public InstrPush( double value )
    {
        this( MislValue.valueOf( value ) );
    }
    
    public InstrPush( boolean value )
    {
        this( MislValue.valueOf( value ) );
    }
    
    // ========================================
    
    @Override
    protected void doExecute( MislFrame frame, ScriptContext context )
    {
        frame.push( _value );
    }
    
    // ========================================
    
    @Override
    public String toString()
    {
        return String.format( "%1$-10s %2$s", "PUSH", _value );
    }
}