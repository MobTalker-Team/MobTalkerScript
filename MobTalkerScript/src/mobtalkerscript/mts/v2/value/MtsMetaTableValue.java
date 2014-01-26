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
}
