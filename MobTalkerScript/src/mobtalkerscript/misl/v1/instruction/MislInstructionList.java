package mobtalkerscript.misl.v1.instruction;

/**
 * Provides a method for easy chaining of instructions.
 * <p>
 * Instructions that are added have their previous and next instructions automatically changed.
 * 
 * @author Chimaine
 */
public class MislInstructionList
{
    private MislInstruction _first;
    private MislInstruction _last;
    
    // ========================================
    
    public MislInstructionList()
    {}
    
    // ========================================
    
    public void add( MislInstruction instr )
    {
        instr.setPrev( _last );
        
        if ( _first == null )
        {
            _first = instr;
        }
        
        if ( _last != null )
        {
            _last.setNext( instr );
        }
        
        _last = instr;
    }
    
    public void add( MislInstructionList instrs )
    {
        MislInstruction first = instrs.getFirst();
        MislInstruction last = instrs.getLast();
        
        if ( first == null )
        {
            return;
        }
        
        first.setPrev( _last );
        
        if ( _first == null )
        {
            _first = first;
        }
        
        if ( _last != null )
        {
            _last.setNext( first );
        }
        
        _last = last;
    }
    
    public void removeLast()
    {
        _last.setPrev( null );
        _last = null;
    }
    
    // ========================================
    
    public MislInstruction getFirst()
    {
        return _first;
    }
    
    public MislInstruction getLast()
    {
        return _last;
    }
}
