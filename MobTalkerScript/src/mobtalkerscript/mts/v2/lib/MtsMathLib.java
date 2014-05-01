package mobtalkerscript.mts.v2.lib;

import mobtalkerscript.mts.v2.*;
import mobtalkerscript.mts.v2.value.*;

public final class MtsMathLib extends MtsLibrary
{
    private static final MtsNumber PI = valueOf( Math.PI );
    private static final MtsNumber E = valueOf( Math.E );
    
    private static final MtsJavaFunction Abs = new Abs();
    private static final MtsJavaFunction Ceil = new Ceil();
    private static final MtsJavaFunction Cos = new Cos();
    private static final MtsJavaFunction CosH = new CosH();
    private static final MtsJavaFunction Floor = new Floor();
    private static final MtsJavaFunction Log = new Log();
    private static final MtsJavaFunction Log10 = new Log10();
    private static final MtsJavaFunction Max = new Max();
    private static final MtsJavaFunction Min = new Min();
    private static final MtsJavaFunction Pow = new Pow();
    private static final MtsJavaFunction Round = new Round();
    private static final MtsJavaFunction Sin = new Sin();
    private static final MtsJavaFunction SinH = new SinH();
    private static final MtsJavaFunction Sign = new Sign();
    private static final MtsJavaFunction Sqrt = new Sqrt();
    private static final MtsJavaFunction Tan = new Tan();
    private static final MtsJavaFunction TanH = new TanH();
    private static final MtsJavaFunction ToDegrees = new ToDegrees();
    private static final MtsJavaFunction ToRadians = new ToRadians();
    
    // ========================================
    
    @Override
    public MtsValue bind( MtsString name, MtsValue env )
    {
        MtsTable math = new MtsTable( 0, 20 );
        
        math.setRaw( "PI", PI );
        math.setRaw( "E", E );
        
        bindFunction( math, "Abs", Abs );
        bindFunction( math, "Ceil", Ceil );
        bindFunction( math, "Cos", Cos );
        bindFunction( math, "CosH", CosH );
        bindFunction( math, "Floor", Floor );
        bindFunction( math, "Log", Log );
        bindFunction( math, "Log10", Log10 );
        bindFunction( math, "Max", Max );
        bindFunction( math, "Min", Min );
        bindFunction( math, "Pow", Pow );
        bindFunction( math, "Random", new Random() );
        bindFunction( math, "Round", Round );
        bindFunction( math, "Sin", Sin );
        bindFunction( math, "SinH", SinH );
        bindFunction( math, "Sign", Sign );
        bindFunction( math, "Sqrt", Sqrt );
        bindFunction( math, "Tan", Tan );
        bindFunction( math, "TanH", TanH );
        bindFunction( math, "ToDegrees", ToDegrees );
        bindFunction( math, "ToRadians", ToRadians );
        
        env.set( "Math", math );
        
        return NIL;
    }
    
    // ========================================
    
    private static final class Abs extends MtsOneArgFunction
    {
        @Override
        protected MtsValue invoke( MtsValue arg1 )
        {
            checkNumber( arg1, 1 );
            
            return valueOf( Math.abs( arg1.asNumber().toJava() ) );
        }
    }
    
    private static final class Ceil extends MtsOneArgFunction
    {
        @Override
        protected MtsValue invoke( MtsValue arg1 )
        {
            checkNumber( arg1, 1 );
            
            return valueOf( Math.ceil( arg1.asNumber().toJava() ) );
        }
    }
    
    private static final class Cos extends MtsOneArgFunction
    {
        @Override
        protected MtsValue invoke( MtsValue arg1 )
        {
            checkNumber( arg1, 1 );
            
            return valueOf( Math.cos( arg1.asNumber().toJava() ) );
        }
    }
    
    private static final class CosH extends MtsOneArgFunction
    {
        @Override
        protected MtsValue invoke( MtsValue arg1 )
        {
            checkNumber( arg1, 1 );
            
            return valueOf( Math.cosh( arg1.asNumber().toJava() ) );
        }
    }
    
    private static final class Floor extends MtsOneArgFunction
    {
        @Override
        protected MtsValue invoke( MtsValue arg1 )
        {
            checkNumber( arg1, 1 );
            
            return valueOf( Math.floor( arg1.asNumber().toJava() ) );
        }
    }
    
    private static final class Log extends MtsOneArgFunction
    {
        @Override
        protected MtsValue invoke( MtsValue arg1 )
        {
            checkNumber( arg1, 1 );
            
            return valueOf( Math.log( arg1.asNumber().toJava() ) );
        }
    }
    
    private static final class Log10 extends MtsOneArgFunction
    {
        @Override
        protected MtsValue invoke( MtsValue arg1 )
        {
            checkNumber( arg1, 1 );
            
            return valueOf( Math.log10( arg1.asNumber().toJava() ) );
        }
    }
    
    private static final class Max extends MtsJavaFunction
    {
        @Override
        protected MtsValue invoke( MtsVarArgs args )
        {
            if ( args.isEmpty() )
                throw new BadArgumentException( "got no value" );
            
            double result = args.get( 0 ).asNumber().toJava();
            
            for ( int i = 1; i < args.count(); i++ )
            {
                double n = args.get( i ).asNumber().toJava();
                result = Math.max( result, n );
            }
            
            return valueOf( result );
        }
    }
    
    private static final class Min extends MtsJavaFunction
    {
        @Override
        protected MtsValue invoke( MtsVarArgs args )
        {
            if ( args.isEmpty() )
                throw new BadArgumentException( "got no value" );
            
            double result = args.get( 0 ).asNumber().toJava();
            
            for ( int i = 1; i < args.count(); i++ )
            {
                double n = args.get( i ).asNumber().toJava();
                result = Math.min( result, n );
            }
            
            return valueOf( result );
        }
    }
    
    private static final class Pow extends MtsTwoArgFunction
    {
        @Override
        protected MtsValue invoke( MtsValue arg1, MtsValue arg2 )
        {
            checkNumber( arg1, 1 );
            checkNumber( arg2, 2 );
            
            return valueOf( Math.pow( arg1.asNumber().toJava(), arg2.asNumber().toJava() ) );
        }
    }
    
    private static final class Random extends MtsTwoArgFunction
    {
        private final java.util.Random _rnd = new java.util.Random();
        
        @Override
        protected MtsValue invoke()
        {
            return valueOf( _rnd.nextDouble() );
        }
        
        @Override
        protected MtsValue invoke( MtsValue arg1 )
        {
            checkNumber( arg1, 1 );
            
            return valueOf( _rnd.nextInt( (int) arg1.asNumber().toJava() ) );
        }
        
        @Override
        protected MtsValue invoke( MtsValue arg1, MtsValue arg2 )
        {
            checkNumber( arg1, 1 );
            checkNumber( arg2, 2 );
            
            int a = (int) arg1.asNumber().toJava();
            int b = (int) arg2.asNumber().toJava();
            
            return valueOf( a + _rnd.nextInt( b ) );
        }
    }
    
    private static final class Round extends MtsOneArgFunction
    {
        @Override
        protected MtsValue invoke( MtsValue arg1 )
        {
            checkNumber( arg1, 1 );
            
            return valueOf( Math.round( arg1.asNumber().toJava() ) );
        }
    }
    
    private static final class Sin extends MtsOneArgFunction
    {
        @Override
        protected MtsValue invoke( MtsValue arg1 )
        {
            checkNumber( arg1, 1 );
            
            return valueOf( Math.sin( arg1.asNumber().toJava() ) );
        }
    }
    
    private static final class SinH extends MtsOneArgFunction
    {
        @Override
        protected MtsValue invoke( MtsValue arg1 )
        {
            checkNumber( arg1, 1 );
            
            return valueOf( Math.sinh( arg1.asNumber().toJava() ) );
        }
    }
    
    private static final class Sign extends MtsOneArgFunction
    {
        @Override
        protected MtsValue invoke( MtsValue arg1 )
        {
            checkNumber( arg1, 1 );
            
            return valueOf( Math.signum( arg1.asNumber().toJava() ) );
        }
    }
    
    private static final class Sqrt extends MtsOneArgFunction
    {
        @Override
        protected MtsValue invoke( MtsValue arg1 )
        {
            checkNumber( arg1, 1 );
            
            return valueOf( Math.sqrt( arg1.asNumber().toJava() ) );
        }
    }
    
    private static final class Tan extends MtsOneArgFunction
    {
        @Override
        protected MtsValue invoke( MtsValue arg1 )
        {
            checkNumber( arg1, 1 );
            
            return valueOf( Math.tan( arg1.asNumber().toJava() ) );
        }
    }
    
    private static final class TanH extends MtsOneArgFunction
    {
        @Override
        protected MtsValue invoke( MtsValue arg1 )
        {
            checkNumber( arg1, 1 );
            
            return valueOf( Math.tanh( arg1.asNumber().toJava() ) );
        }
    }
    
    private static final class ToDegrees extends MtsOneArgFunction
    {
        @Override
        protected MtsValue invoke( MtsValue arg1 )
        {
            checkNumber( arg1, 1 );
            
            return valueOf( Math.toDegrees( arg1.asNumber().toJava() ) );
        }
    }
    
    private static final class ToRadians extends MtsOneArgFunction
    {
        @Override
        protected MtsValue invoke( MtsValue arg1 )
        {
            checkNumber( arg1, 1 );
            
            return valueOf( Math.toRadians( arg1.asNumber().toJava() ) );
        }
    }
}
