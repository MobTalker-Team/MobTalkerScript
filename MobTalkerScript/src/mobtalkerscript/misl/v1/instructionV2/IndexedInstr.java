package mobtalkerscript.misl.v1.instructionV2;


/* package */abstract class IndexedInstr extends MislInstruction
{
    
    protected int _index;
    
    protected IndexedInstr( int i )
    {
        _index = i;
    }
    
}
