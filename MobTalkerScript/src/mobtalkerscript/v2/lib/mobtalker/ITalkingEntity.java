package mobtalkerscript.v2.lib.mobtalker;

public interface ITalkingEntity extends IInteractableEntity
{
    void setName( String name );
    
    double getLoveLevel( IInteractableEntity entity );
    
    void setLoveLevel( IInteractableEntity entity, double level );
}
