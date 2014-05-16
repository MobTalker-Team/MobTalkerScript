package mobtalkerscript.mts.v2.value.userdata;

import mobtalkerscript.mts.v2.value.*;

public class MtsNatives
{
    public static InstanceAdapter wrapInstance( Object instance )
    {
        ClassAdapter classAdapter = ClassAdapter.get( instance.getClass() );
        return new InstanceAdapter( classAdapter, instance );
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
        LibraryAdapter.bindTo( library, table );
    }
    
    public static void createLibrary( Object library, MtsTable table )
    {
        LibraryAdapter.bindTo( library, table );
    }
}
