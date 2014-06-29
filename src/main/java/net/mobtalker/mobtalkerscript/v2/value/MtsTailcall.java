package net.mobtalker.mobtalkerscript.v2.value;

public class MtsTailcall extends MtsVarArgs
{
    private MtsValue _target;
    private MtsVarArgs _args;
    private final int _nResults;
    private MtsValue _result;
    
    // ========================================
    
    public MtsTailcall( MtsValue target, MtsVarArgs args, int nResults )
    {
        _target = target;
        _args = args;
        _nResults = nResults;
    }
    
    // ========================================
    
    public MtsValue getTarget()
    {
        return _target;
    }
    
    public MtsVarArgs getArguments()
    {
        return _args;
    }
    
    // ========================================
    
    @Override
    public MtsVarArgs subArgs( int start, int end )
    {
        if ( _result == null )
            evaluate();
        
        if ( _result.isVarArgs() )
            return _result.asVarArgs().subArgs( start, end );
        else
            return MtsVarArgs.of( _result );
    }
    
    @Override
    public MtsValue get( int i )
    {
        if ( _result == null )
            evaluate();
        
        return _result.get( i );
    }
    
    @Override
    public MtsValue[] toArray()
    {
        if ( _result == null )
            evaluate();
        
        if ( _result.isVarArgs() )
            return _result.asVarArgs().toArray();
        else
            return new MtsValue[] { _result };
    }
    
    @Override
    public int count()
    {
        return _nResults;
    }
    
    @Override
    public boolean isEmpty()
    {
        return _nResults <= 0;
    }
    
    // ========================================
    
    /**
     * Executes this tail call and any subsequent returned tail calls.
     */
    public void evaluate()
    {
        MtsValue result = _target.call( _args );
        
        while ( result instanceof MtsTailcall )
        {
            MtsTailcall next = (MtsTailcall) result;
            result = next.getTarget().call( next.getArguments() );
        }
        
        _target = null;
        _args = null;
        _result = result;
    }
}
