package net.mobtalker.mobtalkerscript.api;

public class ScoreboardTeamInfo
{
    public final String Name;
    public final String Color;
    public final boolean FriendlyFire;
    public final boolean CanSeeInvisibleMembers;
    
    // ========================================
    
    public ScoreboardTeamInfo( String name, String color, boolean friendlyFire, boolean canSeeInvisibleMembers )
    {
        Name = name;
        Color = color;
        FriendlyFire = friendlyFire;
        CanSeeInvisibleMembers = canSeeInvisibleMembers;
    }
}
