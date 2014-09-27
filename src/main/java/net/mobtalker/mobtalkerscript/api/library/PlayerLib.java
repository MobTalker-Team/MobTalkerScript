package net.mobtalker.mobtalkerscript.api.library;

import static net.mobtalker.mobtalkerscript.v2.MtsCheck.*;
import static net.mobtalker.mobtalkerscript.v2.value.MtsValue.*;
import net.mobtalker.mobtalkerscript.api.*;
import net.mobtalker.mobtalkerscript.v2.BadArgumentException;
import net.mobtalker.mobtalkerscript.v2.value.*;
import net.mobtalker.mobtalkerscript.v2.value.userdata.MtsNativeFunction;

public final class PlayerLib
{
    private final IPlayerLibLogic _logic;
    
    // ========================================
    
    public PlayerLib( IPlayerLibLogic logic )
    {
        _logic = logic;
    }
    
    // ========================================
    
    public IPlayerLibLogic getLogic()
    {
        return _logic;
    }
    
    // ========================================
    
    @MtsNativeFunction
    public MtsString getGameMode()
    {
        return valueOf( _logic.getGameMode() );
    }
    
    // ========================================
    
    @MtsNativeFunction
    public MtsNumber getArmor()
    {
        return valueOf( _logic.getArmor() );
    }
    
    // ========================================
    
    @MtsNativeFunction
    public MtsVarArgs getExperience()
    {
        return MtsVarArgs.of( valueOf( _logic.getExperienceLevel() ),
                              valueOf( _logic.getExperience() ),
                              valueOf( _logic.getExperienceRequired() ) );
    }
    
    @MtsNativeFunction
    public void giveExperience( MtsValue argAmount )
    {
        _logic.giveExperience( checkInteger( argAmount, 0 ) );
    }
    
    @MtsNativeFunction
    public MtsBoolean takeExperience( MtsValue argAmount )
    {
        return valueOf( _logic.takeExperience( checkInteger( argAmount, 0 ) ) );
    }
    
    // ========================================
    
    @MtsNativeFunction
    public MtsNumber countItem( MtsValue argItemName, MtsValue argItemMeta )
    {
        String name = checkString( argItemName, 0 );
        if ( !_logic.isValidItem( name ) )
            throw new BadArgumentException( 0, "unknown item name '%s'", name );
        
        return valueOf( _logic.getItemCount( name,
                                             checkInteger( argItemMeta, 1, -1 ) ) );
    }
    
    @MtsNativeFunction
    public MtsBoolean giveItem( MtsValue argItemName, MtsValue argItemCount, MtsValue argItemMeta )
    {
        String name = checkString( argItemName, 0 );
        if ( !_logic.isValidItem( name ) )
            throw new BadArgumentException( 0, "unknown item name '%s'", name );
        
        return valueOf( _logic.giveItems( name,
                                          checkIntegerWithMinimum( argItemCount, 1, 1, 1 ),
                                          checkIntegerWithMinimum( argItemMeta, 2, 0, -1 ) ) );
    }
    
    @MtsNativeFunction
    public MtsBoolean takeItem( MtsValue argItemName, MtsValue argItemCount, MtsValue argItemMeta )
    {
        String name = checkString( argItemName, 0 );
        if ( !_logic.isValidItem( name ) )
            throw new BadArgumentException( 0, "unknown item name '%s'", name );
        
        return valueOf( _logic.takeItems( name,
                                          checkIntegerWithMinimum( argItemCount, 1, 1, 1 ),
                                          checkIntegerWithMinimum( argItemMeta, 2, 0, -1 ) ) );
    }
    
    @MtsNativeFunction
    public MtsTable getInventory()
    {
        InventoryInfo inventory = _logic.getInventory();
        
        MtsTable t = new MtsTable( inventory.count(), 0 );
        for ( ItemInfo item : inventory.getItems() )
        {
            MtsTable info = new MtsTable( 0, 3 );
            info.set( "name", valueOf( item.Name ) );
            info.set( "meta", valueOf( item.Meta ) );
            info.set( "count", valueOf( inventory.getAmount( item ) ) );
            t.add( info );
        }
        
        return t;
    }
}
