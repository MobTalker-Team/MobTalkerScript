package net.mobtalker.mobtalkerscript.api.library;

public class ItemInfo
{
    private final String _name;
    private final int _meta;
    
    public ItemInfo( String name, int meta )
    {
        _name = name;
        _meta = meta;
    }
    
    public String getName()
    {
        return _name;
    }
    
    public int getMeta()
    {
        return _meta;
    }
    
    // ========================================
    
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = ( prime * result ) + _meta;
        result = ( prime * result ) + _name.hashCode();
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
        if ( !_name.equals( other._name ) )
            return false;
        if ( _meta != other._meta )
            return false;
        return true;
    }
}
