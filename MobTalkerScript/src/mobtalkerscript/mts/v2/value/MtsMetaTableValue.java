package mobtalkerscript.mts.v2.value;

public abstract class MtsMetaTableValue extends MtsValue
{
    protected MtsTable _metaTable;
    
    // ========================================
    
    @Override
    public boolean hasMetaTable()
    {
        return _metaTable != null;
    }
    
    @Override
    public MtsTable getMetaTable()
    {
        return _metaTable;
    }
    
    @Override
    public void setMetaTable( MtsValue t )
    {
        _metaTable = t.isTable() ? t.asTable() : null;
    }
    
    // ========================================
    
    @Override
    public boolean hasMetaTag( MtsString tag )
    {
        return hasMetaTable() && _metaTable.containsKey( tag );
    }
    
    // ========================================
    
    @Override
    public MtsVarArgs call( MtsVarArgs args )
    {
        return __call( args );
    }
    
    // ========================================
    
    public MtsVarArgs __call( MtsVarArgs args )
    {
        MtsValue tag = getMetaTag( __CALL );
        
        if ( tag.isNil() )
            return super.call( args );
        
        return tag.call( this, args );
    }
    
    public MtsValue __index( MtsValue table, MtsValue key )
    {
        MtsValue tag = getMetaTag( __INDEX );
        
        if ( tag.isNil() )
            return super.get( key );
        if ( tag.isFunction() )
            return tag.call( this, key );
        
        return tag.get( key );
    }
    
    public void __newindex( MtsValue table, MtsValue key, MtsValue value )
    {
        MtsValue tag = getMetaTag( __NEWINDEX );
        
        if ( tag.isNil() )
            super.set( key, value );
        else if ( tag.isFunction() )
            tag.call( this, key, value );
        else
            tag.set( key, value );
    }
}
