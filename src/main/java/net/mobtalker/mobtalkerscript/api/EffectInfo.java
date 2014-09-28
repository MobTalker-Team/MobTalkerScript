package net.mobtalker.mobtalkerscript.api;

public class EffectInfo
{
    public final String Name;
    public final int Amplifier;
    public final int Duration;
    
    public EffectInfo( String name, int amplifier, int duration )
    {
        Name = name;
        Amplifier = amplifier;
        Duration = duration;
    }
    
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = ( prime * result ) + Amplifier;
        result = ( prime * result ) + Duration;
        result = ( prime * result ) + Name.hashCode();
        return result;
    }
    
    @Override
    public boolean equals( Object obj )
    {
        if ( this == obj )
            return true;
        if ( obj == null )
            return false;
        if ( !( obj instanceof EffectInfo ) )
            return false;
        
        return equals( (EffectInfo) obj );
    }
    
    public boolean equals( EffectInfo other )
    {
        if ( Amplifier != other.Amplifier )
            return false;
        if ( Duration != other.Duration )
            return false;
        if ( !Name.equals( other.Name ) )
            return false;
        return true;
    }
}
