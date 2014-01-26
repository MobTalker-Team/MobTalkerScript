package mobtalkerscript.mts.v2.instruction;

public abstract class MtsInstruction
{
    
    public abstract void execute( MtsFrame frame );
    
    public boolean exits()
    {
        return false;
    }
    
    public boolean yields()
    {
        return false;
    }
    
    @Override
    public abstract String toString();
    
}
