package mobtalkerscript.mts.v2.value;

public abstract class MtsMetaTableValue extends MtsValue
{
    protected MtsTable _metaTable;
    
    // ========================================
    
    @Override
    public boolean hasMetaTable()
    {
        return ( _metaTable != null ) && !_metaTable.isNil();
    }
    
    @Override
    public MtsTable getMetaTable()
    {
        return _metaTable;
    }
    
    @Override
    public void setMetaTable( MtsTable t )
    {
        _metaTable = t;
    }
    
    // ========================================
    
    protected boolean hasMetaTag( MtsValue tag )
    {
        return hasMetaTable() && _metaTable.containsKey( tag );
    }
    
    protected MtsValue getMetaTag( MtsValue tag )
    {
        return hasMetaTable() ? _metaTable.get( tag ) : NIL;
    }
    
    // ========================================
    
    @Override
    public MtsValue call( MtsVarArgs args )
    {
        if ( !hasMetaTag( METATAG_CALL ) )
            return super.call( args );
        
        return getMetaTag( METATAG_CALL ).call( this, args );
    }
}
