package mobtalkerscript.mts.v2.instruction;

import mobtalkerscript.mts.v2.*;
import mobtalkerscript.mts.v2.value.*;

public abstract class MtsInstruction
{
    public static MtsValue getMetaTag( MtsValue a, MtsValue b, MtsString key )
    {
        MtsValue tag = a.getMetaTag( key );
        return tag.isNil() ? b.getMetaTag( key ) : tag;
    }
    
    // ========================================
    
    public abstract void execute( MtsFrame frame );
    
    /**
     * If this instruction causes the current frame to exit after its execution.
     */
    public boolean exits()
    {
        return false;
    }
    
    /**
     * The amount of elements this instruction adds (positive integer) or removes (negative integer) from the stack. Used to
     * calculate the maximum stack size.
     * <p>
     * If there are multiple possibilities, the highest possible positive integer is choosen.
     */
    public abstract int stackSizeChange();
    
    @Override
    public abstract String toString();
    
    public String toString( MtsFunctionPrototype proto )
    {
        return toString();
    }
}
