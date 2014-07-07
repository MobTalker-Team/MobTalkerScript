package net.mobtalker.mobtalkerscript.v2.lib.console.mobtalker;

public interface ITalkingPlayer extends IInteractableEntity
{
    String getGameMode();
    
    double getExp();
    
    void setExp( double exp );
}
