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
    
    // ========================================
    
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = ( prime * result ) + Coloum;
        result = ( prime * result ) + Line;
        return result;
    }
    
    @Override
    public boolean equals( Object obj )
    {
        if ( this == obj )
            return true;
        if ( obj == null )
            return false;
        if ( !( obj instanceof SourcePosition ) )
            return false;
        SourcePosition other = (SourcePosition) obj;
        if ( Coloum != other.Coloum )
            return false;
        if ( Line != other.Line )
            return false;
        return true;
    }
    
    public boolean equals( int line, int col )
    {
        return ( Line == line ) && ( Coloum == col );
    }
    
    // ========================================
    
    @Override
    public String toString()
    {
        return Line + ":" + Coloum;
    }
}
