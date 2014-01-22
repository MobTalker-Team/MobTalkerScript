package mobtalkerscript.misl.v1.lib;

import mobtalkerscript.misl.v1.*;
import mobtalkerscript.misl.v1.value.*;

public class MislBaseLib extends MislLibrary
{
    
    private static final Assert Assert = new Assert();
    private static final Print Print = new Print();
    
    private static final TypeOf TypeOf = new TypeOf();
    private static final SizeOf SizeOf = new SizeOf();
    
    private static final ToBoolean ToBoolean = new ToBoolean();
    private static final ToNumber ToNumber = new ToNumber();
    private static final ToString ToString = new ToString();
    
    private static final Next Next = new Next();
    private static final RawGet RawGet = new RawGet();
    private static final RawSet RawSet = new RawSet();
    
    @Override
    public MislValue bind( MislString name, MislValue env )
    {
        env.set( "Assert", Assert );
        env.set( "Print", Print );
        
        env.set( "TypeOf", TypeOf );
        env.set( "SizeOf", SizeOf );
        
        env.set( "ToBoolean", ToBoolean );
        env.set( "ToNumber", ToNumber );
        env.set( "ToString", ToString );
        
        env.set( "Next", Next );
        env.set( "RawGet", RawGet );
        env.set( "RawSet", RawSet );
        
        return env;
    }
    
    // ========================================
    
    private static final class Assert extends MislTwoArgFunction
    {
        @Override
        public MislValue call( IBindings env, MislValue arg1, MislValue arg2 )
        {
            if ( ( arg1 == FALSE ) || ( arg1 == NIL ) )
            {
                String msg = arg2.isNil() ? "Assertion error" : arg2.toStringMts().toJava();
                
                throw new ScriptRuntimeException( msg );
            }
            
            return null;
        }
    }
    
    private static final class Print extends MislFunction
    {
        @Override
        public MislValue call( MislValue... args )
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
                return valueOf( arg1.asTable().listSize() );
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
                return arg1.toStringMts();
            }
        }
    }
    
    // ========================================
    
    private static final class Next extends MislTwoArgFunction
    {
        @Override
        public MislValue call( IBindings env, MislValue arg1, MislValue arg2 )
        {
            MislTable.Entry next = arg1.asTable().getEntryAfter( arg2 );
            
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
    
    private static final class RawGet extends MislTwoArgFunction
    {
        @Override
        public MislValue call( IBindings env, MislValue arg1, MislValue arg2 )
        {
            return arg1.asTable().getRaw( arg2 );
        }
    }
    
    private static final class RawSet extends MislThreeArgFunction
    {
        @Override
        public MislValue call( IBindings env, MislValue arg1, MislValue arg2, MislValue arg3 )
        {
            return arg1.asTable().setRaw( arg2, arg3 );
        }
    }
    
}
