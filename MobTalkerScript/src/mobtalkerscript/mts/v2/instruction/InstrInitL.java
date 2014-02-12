package mobtalkerscript.mts.v2.instruction;

public class InstrInitL extends MtsInstruction
{
    private final int _local;
    private final int _constant;
    
    public InstrInitL( int local, int constant )
    {
        _local = local;
        _constant = constant;
    }
    
    @Override
    public void execute( MtsFrame frame )
    {
        frame.getLocal( _local ).set( frame.getConstant( _constant ) );
    }
    
    @Override
    public String toString()
    {
        return "INITL " + _local + " " + _constant;
    }
    
}
