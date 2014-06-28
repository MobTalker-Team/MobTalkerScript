package mobtalkerscript.v2.value;

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
}
