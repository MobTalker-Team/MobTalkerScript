package mobtalkerscript.mts.v2.instruction;

/* package */abstract class MtsIndexedInstruction extends MtsInstruction
{
    
    protected int _index;
    
    protected MtsIndexedInstruction( int i )
    {
        _index = i;
    }
    
}
