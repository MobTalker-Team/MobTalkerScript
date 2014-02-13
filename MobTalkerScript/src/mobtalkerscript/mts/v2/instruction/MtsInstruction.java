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
    
    public abstract int stackSizeChange();
    
    @Override
    public abstract String toString();
    
}
