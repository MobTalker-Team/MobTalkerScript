package mobtalkerscript.misl.v1.lib;

import java.util.Map.Entry;

import mobtalkerscript.misl.v1.*;
import mobtalkerscript.misl.v1.value.*;

public class MislBaseLib implements IMislLibrary
{
    
    private static final Assert Assert = new Assert();
    private static final Print Print = new Print();
    
    private static final TypeOf TypeOf = new TypeOf();
    private static final SizeOf SizeOf = new SizeOf();
    
    private static final ToBoolean ToBoolean = new ToBoolean();
    private static final ToNumber ToNumber = new ToNumber();
    private static final ToString ToString = new ToString();
    
    private static final Next Next = new Next();
    
    @Override
    public void loadInto( IBindings env )
    {
        env.set( "Assert", Assert );
        env.set( "Print", Print );
        
        env.set( "TypeOf", TypeOf );
        env.set( "SizeOf", SizeOf );
        
        env.set( "ToBoolean", ToBoolean );
        env.set( "ToNumber", ToNumber );
        env.set( "ToString", ToString );
        
        env.set( "Next", Next );
    }
    
    // ========================================
    
    private static final class Assert extends MislOneArgFunction
    {
        @Override
        public MislValue call( IBindings env, MislValue arg1 )
        {
            if ( ( arg1 == FALSE ) || ( arg1 == NIL ) )
            {
                throw new ScriptRuntimeException( "Assertion error" );
            }
            
            return null;
        }
    }
    
    private static final class Print extends MislVarArgFunction
    {
        @Override
        public MislValue call( IBindings env, MislValue... args )
        {
            if ( ( args != null ) && ( args.length > 0 ) )
            {
                MislString str = MislString.concat( args );
                System.out.println( str.toJava() );
            }
            else
            {
                System.out.println();
            }
            
            return null;
        }
    }
    
    // ========================================
    
    private static final class TypeOf extends MislOneArgFunction
    {
        @Override
        public MislValue call( IBindings env, MislValue arg1 )
        {
            String name = arg1.getTypeName();
            return valueOf( name );
        }
    }
    
    private static final class SizeOf extends MislOneArgFunction
    {
        @Override
        public MislValue call( IBindings env, MislValue arg1 )
        {
            if ( arg1.isTable() )
            {
                return arg1.asTable().getSize();
            }
            else if ( arg1.isString() )
            {
                return arg1.asString().getLength();
            }
            else
            {
                throw new ScriptRuntimeException( "Expected String or Table, got %s", arg1.getTypeName() );
            }
        }
    }
    
    // ========================================
    
    private static final class ToBoolean extends MislOneArgFunction
    {
        @Override
        public MislValue call( IBindings env, MislValue arg1 )
        {
            if ( arg1.isBoolean() )
            {
                return arg1;
            }
            else if ( arg1.isNumber() )
            {
                return MislBoolean.parse( arg1.asNumber() );
            }
            else if ( arg1.isString() )
            {
                return MislBoolean.parse( arg1.asString() );
            }
            else
            {
                return FALSE;
            }
        }
    }
    
    private static final class ToNumber extends MislOneArgFunction
    {
        @Override
        public MislValue call( IBindings env, MislValue arg1 )
        {
            if ( arg1.isNumber() )
            {
                return arg1;
            }
            else if ( arg1.isBoolean() )
            {
                return MislNumber.parse( arg1.asBoolean() );
            }
            else if ( arg1.isString() )
            {
                return MislNumber.parse( arg1.asString() );
            }
            else
            {
                return null;
            }
        }
    }
    
    private static final class ToString extends MislOneArgFunction
    {
        @Override
        public MislValue call( IBindings env, MislValue arg1 )
        {
            if ( arg1.isString() )
            {
                return arg1;
            }
            else
            {
                return arg1.toMtsString();
            }
        }
    }
    
    // ========================================
    
    private static final class Next extends MislTwoArgFunction
    {
        @Override
        public MislValue call( IBindings env, MislValue arg1, MislValue arg2 )
        {
            MislTable t = arg1.asTable();
            
            Entry<MislValue, MislValue> next = t.getEntryAfter( arg2 );
            
            if ( next == null )
            {
                return null;
            }
            else
            {
                return next.getKey();
            }
        }
    }
}
