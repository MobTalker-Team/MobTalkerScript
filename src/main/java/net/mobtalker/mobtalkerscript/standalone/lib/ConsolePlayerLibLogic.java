package net.mobtalker.mobtalkerscript.standalone.lib;

import net.mobtalker.mobtalkerscript.api.InventoryInfo;
import net.mobtalker.mobtalkerscript.api.library.IPlayerLibLogic;
import net.mobtalker.mobtalkerscript.standalone.DummyPlayer;

public class ConsolePlayerLibLogic implements IPlayerLibLogic
{
    private final DummyPlayer _player;
    
    // ========================================
    
    public ConsolePlayerLibLogic( DummyPlayer player )
    {
        _player = player;
    }
    
    // ========================================
    
    @Override
    public String getGameMode()
    {
        return _player.getGameMode();
    }
    
    @Override
    public double getArmor()
    {
        // TODO
        return 0;
    }
    
    @Override
    public int getExperienceLevel()
    {
        // TODO
        return 0;
    }
    
    @Override
    public int getExperience()
    {
        // TODO
        return 0;
    }
    
    @Override
    public int getExperienceRequired()
    {
        // TODO
        return 0;
    }
    
    @Override
    public void giveExperience( int amount )
    {
        // TODO
    }
    
    @Override
    public boolean takeExperience( int amount )
    {
        // TODO
        return true;
    }
    
    @Override
    public boolean isValidItem( String name )
    {
        // TODO
        return true;
    }
    
    @Override
    public int getItemCount( String name, int meta )
    {
        // TODO
        return 64;
    }
    
    @Override
    public boolean takeItems( String name, int meta, int amount )
    {
        // TODO
        return true;
    }
    
    @Override
    public boolean giveItems( String name, int meta, int amount )
    {
        // TODO
        return true;
    }
    
    @Override
    public InventoryInfo getInventory()
    {
        // TODO
        return new InventoryInfo();
    }
}
