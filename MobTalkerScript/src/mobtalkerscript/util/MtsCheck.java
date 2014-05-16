package mobtalkerscript.util;

import mobtalkerscript.mts.v2.*;
import mobtalkerscript.mts.v2.value.*;

public class MtsCheck
{
    public static void checkNotNil( MtsValue value )
    {
        if ( value.isNil() )
            throw new BadArgumentException( "value cannot be nil" );
    }
    
    public static void checkNotNil( MtsValue value, String msg )
    {
        if ( value.isNil() )
            throw new BadArgumentException( msg );
    }
    
    public static void checkType( MtsValue value, int i, MtsType expected )
    {
        if ( value.getType() != expected )
        {
            // TODO
            throw new BadArgumentException( "(%s expected, got %s)", expected, value.getType() );
        }
    }
    
    public static void checkType( MtsValue value, int i, MtsType... expected )
    {
        for ( MtsType t : expected )
        {
            if ( t == value.getType() )
                return;
        }
        
        StringBuilder msg = new StringBuilder();
        msg.append( "(" );
        
        msg.append( expected[0].getName() );
        int to = expected.length - 1;
        for ( int j = 1; j < to; j++ )
        {
            msg.append( ", " ).append( expected[j].getName() );
        }
        msg.append( "or " ).append( expected[to].getName() );
        msg.append( " expected, got %s)" );
        
        throw new BadArgumentException( msg.toString(), value.getType() );
    }
    
    public static MtsTable checkTable( MtsValue value, int i )
    {
        if ( value.getType() == MtsType.TABLE )
            return value.asTable();
        
        throw new BadArgumentException( "(%s expected, got %s)", MtsType.TABLE.getName(), value.getType() );
    }
    
    public static String checkString( MtsValue value, int i )
    {
        if ( ( value.getType() == MtsType.STRING ) || ( value.getType() == MtsType.NUMBER ) )
            return value.asString().asJavaString();
        
        throw new BadArgumentException( "(%s expected, got %s)", MtsType.STRING.getName(), value.getType() );
    }
    
    public static double checkNumber( MtsValue value, int i )
    {
        if ( ( value.getType() == MtsType.NUMBER ) || ( value.getType() == MtsType.STRING ) )
            return value.asNumber().asJavaDouble();
        
        throw new ScriptRuntimeException( "(%s expected, got %s)", MtsType.NUMBER.getName(), value.getType() );
    }
    
    public static double checkNumber( MtsVarArgs args, int i )
    {
        return checkNumber( args.get( i ), i );
    }
    
    public static int checkInteger( MtsValue value, int i )
    {
        if ( ( value.getType() == MtsType.NUMBER ) || ( value.getType() == MtsType.STRING ) )
            return value.asNumber().asJavaInt();
        
        throw new ScriptRuntimeException( "(%s expected, got %s)", MtsType.NUMBER.getName(), value.getType() );
    }
}
