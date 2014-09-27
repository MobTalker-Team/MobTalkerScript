package net.mobtalker.mobtalkerscript.api;

public class ItemInfo
{
    public final String Name;
    public final int Meta;
    
    public ItemInfo( String name, int meta )
    {
        Name = name;
        Meta = meta;
    }
    
    // ========================================
    
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = ( prime * result ) + Meta;
        result = ( prime * result ) + Name.hashCode();
        return result;
    }
    
    @Override
    public boolean equals( Object obj )
    {
        if ( this == obj )
            return true;
        if ( ( obj == null ) || !( obj instanceof ItemInfo ) )
            return false;
        
        return equals( (ItemInfo) obj );
    }
    
    public boolean equals( ItemInfo other )
    {
        if ( !Name.equals( other.Name ) )
            return false;
        if ( Meta != other.Meta )
            return false;
        return true;
    }
}
