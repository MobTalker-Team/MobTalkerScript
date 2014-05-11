package mobtalkerscript.mts.v2.lib.mobtalker;

public interface ITalkingPlayer extends IInteractableEntity
{
    String getGameMode();
    
    double getExp();
    
    void setExp( double exp );
}
