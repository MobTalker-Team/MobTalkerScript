package mobtalkerscript.mts.v2.value;

public class MtsTailcall extends MtsVarArgs
{
    private MtsFunction _func;
    private MtsVarArgs _args;
    private final int _nResults;
    private MtsValue _result;
    
    // ========================================
    
    public MtsTailcall( MtsFunction func, MtsVarArgs args, int nResults )
    {
        _func = func;
        _args = args;
        _nResults = nResults;
    }
    
    // ========================================
    
    public MtsFunction getFunction()
    {
        return _func;
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
            eval();
        
        if ( _result.isVarArgs() )
            return _result.asVarArgs().subArgs( start, end );
        else
            return MtsVarArgs.of( _result );
    }
    
    @Override
    public MtsValue get( int i )
    {
        if ( _result == null )
            eval();
        
        return _result.get( i );
    }
    
    @Override
    public MtsValue[] toArray()
    {
        if ( _result == null )
            eval();
        
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
    
    private void eval()
    {
        MtsValue result = _func.call( _args );
        
        while ( result instanceof MtsTailcall )
        {
            MtsTailcall next = (MtsTailcall) result;
            result = next.getFunction().call( next.getArguments() );
        }
        
        _func = null;
        _args = null;
        _result = result;
    }
}
