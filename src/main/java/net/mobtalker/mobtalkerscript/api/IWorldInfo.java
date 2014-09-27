package net.mobtalker.mobtalkerscript.api;

public interface IWorldInfo
{
    int getID();
    
    String getName();
    
    String getType();
    
    long getTime();
    
    boolean isDaytime();
    
    int getMoonphase();
}
