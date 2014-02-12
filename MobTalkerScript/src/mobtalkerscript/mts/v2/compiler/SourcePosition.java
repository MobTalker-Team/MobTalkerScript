package mobtalkerscript.mts.v2.compiler;

public class SourcePosition
{
    public static final SourcePosition ZERO = new SourcePosition( 0, 0 );
    
    // ========================================
    
    public final int Line;
    public final int Coloum;
    
    public SourcePosition( int line, int coloum )
    {
        Line = line;
        Coloum = coloum;
    }
    
    @Override
    public String toString()
    {
        return Line + ":" + Coloum;
    }
}
