package net.mobtalker.mobtalkerscript.v2.lib.console.mobtalker;

public interface ITalkingEntity extends IInteractableEntity
{
    void setName( String name );
    
    double getLoveLevel( IInteractableEntity entity );
    
    void setLoveLevel( IInteractableEntity entity, double level );
}
