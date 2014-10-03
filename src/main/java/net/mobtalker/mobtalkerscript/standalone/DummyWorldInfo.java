package net.mobtalker.mobtalkerscript.standalone;

import net.mobtalker.mobtalkerscript.api.IWorldInfo;

public class DummyWorldInfo implements IWorldInfo
{
    private int _worldID = 0;
    private String _worldName = "Overworld";
    private String _worldType = "default";
    private int _worldTime = 6000;
    private boolean _isDaytime = true;
    private int _moonphase = 0;
    
    // ========================================
    
    @Override
    public int getID()
    {
        return _worldID;
    }
    
    @Override
    public String getName()
    {
        return _worldName;
    }
    
    @Override
    public String getType()
    {
        return _worldType;
    }
    
    @Override
    public long getTime()
    {
        return _worldTime;
    }
    
    @Override
    public boolean isDaytime()
    {
        return _isDaytime;
    }
    
    @Override
    public int getMoonphase()
    {
        return _moonphase;
    }
}
