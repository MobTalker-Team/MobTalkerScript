package net.mobtalker.mobtalkerscript.v2.value.userdata;

import net.mobtalker.mobtalkerscript.v2.value.*;

public class MtsNatives
{
    public static InstanceAdapter wrapInstance( Object instance )
    {
        ClassAdapter classAdapter = ClassAdapter.get( instance.getClass() );
        return new InstanceAdapter( classAdapter, instance );
    }
    
    // ========================================
    
    public static MtsTable asMetatable( Object instance )
    {
        return LibraryAdapter.bind( instance );
    }
    
    // ========================================
    
    public static MtsTable createLibrary( Class<?> library )
    {
        return LibraryAdapter.bind( library );
    }
    
    public static MtsTable createLibrary( Object library )
    {
        return LibraryAdapter.bind( library );
    }
    
    public static void createLibrary( Class<?> library, MtsTable table )
    {
        LibraryAdapter.bind( library, table );
    }
    
    public static void createLibrary( Object library, MtsTable table )
    {
        LibraryAdapter.bind( library, table );
    }
}
