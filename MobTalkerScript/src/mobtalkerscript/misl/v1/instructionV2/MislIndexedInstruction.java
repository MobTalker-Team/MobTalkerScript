package mobtalkerscript.misl.v1.instructionV2;


/* package */abstract class MislIndexedInstruction extends MislInstruction
{
    
    protected int _index;
    
    protected MislIndexedInstruction( int i )
    {
        _index = i;
    }
    
}
