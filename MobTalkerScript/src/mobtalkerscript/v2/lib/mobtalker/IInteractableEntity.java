package mobtalkerscript.v2.lib.mobtalker;


public interface IInteractableEntity
{
    String getName();
    
    String getType();
    
    double getHealth();
    
    void setHealth( double health );
    
    double getMaxHealth();
    
    void setMaxHealth( double maxHealth );
}