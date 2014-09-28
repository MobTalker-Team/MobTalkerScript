package net.mobtalker.mobtalkerscript.api;

public class WorldPosition
{
    public final int X;
    public final int Y;
    public final int Z;
    
    // ========================================
    
    public WorldPosition( int x, int y, int z )
    {
        X = x;
        Y = y;
        Z = z;
    }
    
    // ========================================
    
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = ( prime * result ) + X;
        result = ( prime * result ) + Y;
        result = ( prime * result ) + Z;
        return result;
    }
    
    @Override
    public boolean equals( Object obj )
    {
        if ( this == obj )
            return true;
        if ( obj == null )
            return false;
        if ( !( obj instanceof WorldPosition ) )
            return false;
        
        return equals( (WorldPosition) obj );
    }
    
    public boolean equals( WorldPosition other )
    {
        if ( X != other.X )
            return false;
        if ( Y != other.Y )
            return false;
        if ( Z != other.Z )
            return false;
        return true;
    }
}
