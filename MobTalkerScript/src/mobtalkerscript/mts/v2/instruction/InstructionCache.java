package mobtalkerscript.mts.v2.instruction;

import static com.google.common.base.Preconditions.*;

import java.util.*;

import com.google.common.collect.*;

public class InstructionCache
{
    private static final Map<String, MtsInstruction> _unaryOps;
    private static final InstrNeg _neg = new InstrNeg();
    private static final InstrNot _not = new InstrNot();
    private static final InstrSize _size = new InstrSize();
    
    private static final Map<String, MtsInstruction> _binaryOps;
    private static final InstrAdd _add = new InstrAdd();
    private static final InstrSub _sub = new InstrSub();
    private static final InstrMul _mul = new InstrMul();
    private static final InstrDiv _div = new InstrDiv();
    private static final InstrMod _mod = new InstrMod();
    private static final InstrPow _pow = new InstrPow();
    
    private static final Map<String, MtsInstruction> _logicalOps;
    private static final InstrEQ _eq = new InstrEQ();
    private static final InstrLT _lt = new InstrLT();
    private static final InstrLTE _lte = new InstrLTE();
    
    private static final Map<String, MtsInstruction> _assignOps;
    
    private static final InstrDup _dup = new InstrDup();
    private static final InstrPop _pop = new InstrPop();
    
    private static final InstrNewTable _newTable = new InstrNewTable();
    private static final InstrLoadT _loadT = new InstrLoadT();
    private static final InstrStoreT _storeT = new InstrStoreT();
    
    private static final InstrLoadL[] _loadL;
    private static final InstrLoadE[] _loadE;
    private static final InstrLoadC[] _loadC;
    private static final InstrLoadNil _loadNil = new InstrLoadNil();
    
    private static final InstrStoreL[] _storeL;
    private static final InstrStoreE[] _storeE;
    
    private static final InstrReturn[] _return;
    
    // ========================================
    
    static
    {
        _unaryOps = Maps.newHashMapWithExpectedSize( 3 );
        _unaryOps.put( "-", InstrNeg() );
        _unaryOps.put( "not", InstrNot() );
        _unaryOps.put( "#", InstrSize() );
        
        _binaryOps = Maps.newHashMapWithExpectedSize( 6 );
        _binaryOps.put( "+", InstrAdd() );
        _binaryOps.put( "-", InstrSub() );
        _binaryOps.put( "*", InstrMul() );
        _binaryOps.put( "/", InstrDiv() );
        _binaryOps.put( "%", InstrMod() );
        _binaryOps.put( "^", InstrPow() );
        
        _logicalOps = Maps.newHashMapWithExpectedSize( 3 );
        _logicalOps.put( "==", InstrEq() );
        _logicalOps.put( "<", InstrLt() );
        _logicalOps.put( "<=", InstrLte() );
        
        _assignOps = Maps.newHashMapWithExpectedSize( 6 );
        _assignOps.put( "+=", InstrAdd() );
        _assignOps.put( "-=", InstrSub() );
        _assignOps.put( "*=", InstrMul() );
        _assignOps.put( "/=", InstrDiv() );
        _assignOps.put( "%=", InstrMod() );
        _assignOps.put( "^=", InstrPow() );
        
        _loadL = new InstrLoadL[15];
        _loadE = new InstrLoadE[15];
        _loadC = new InstrLoadC[15];
        _storeL = new InstrStoreL[15];
        _storeE = new InstrStoreE[15];
        
        for ( int i = 0; i < 15; i++ )
        {
            _loadL[i] = new InstrLoadL( i );
            _loadE[i] = new InstrLoadE( i );
            _loadC[i] = new InstrLoadC( i );
            _storeL[i] = new InstrStoreL( i );
            _storeE[i] = new InstrStoreE( i );
        }
        
        _return = new InstrReturn[5];
        for ( int i = 0; i < 5; i++ )
        {
            _return[i] = new InstrReturn( i );
        }
    }
    
    // ========================================
    
    public static MtsInstruction InstrUnaryOp( String op )
    {
        MtsInstruction instr = _unaryOps.get( op );
        checkArgument( instr != null, "%s is not a valid unary operator!" );
        
        return instr;
    }
    
    public static MtsInstruction InstrBinaryOp( String op )
    {
        MtsInstruction instr = _binaryOps.get( op );
        checkArgument( instr != null, "%s is not a valid binary operator!" );
        
        return instr;
    }
    
    public static MtsInstruction InstrLogicalOp( String op )
    {
        MtsInstruction instr = _logicalOps.get( op );
        checkArgument( instr != null, "%s is not a valid logic operator!" );
        
        return instr;
    }
    
    // ========================================
    
    public static InstrAnd InstrAnd()
    {
        return new InstrAnd();
    }
    
    public static InstrAnd InstrAnd( int distance )
    {
        return new InstrAnd( distance );
    }
    
    public static InstrOr InstrOr()
    {
        return new InstrOr();
    }
    
    public static InstrOr InstrOr( int distance )
    {
        return new InstrOr( distance );
    }
    
    public static InstrTest InstrTest()
    {
        return new InstrTest();
    }
    
    public static InstrTest InstrTest( int distance )
    {
        return new InstrTest( distance );
    }
    
    public static InstrJump InstrJump()
    {
        return new InstrJump();
    }
    
    public static InstrJump InstrJump( int distance )
    {
        return new InstrJump( distance );
    }
    
    // ========================================
    
    public static InstrLoadL InstrLoadL( int index )
    {
        return ( index < _loadL.length ) ? _loadL[index] : new InstrLoadL( index );
    }
    
    public static InstrLoadE InstrLoadE( int index )
    {
        return ( index < _loadE.length ) ? _loadE[index] : new InstrLoadE( index );
    }
    
    public static InstrLoadC InstrLoadC( int index )
    {
        return ( index < _loadC.length ) ? _loadC[index] : new InstrLoadC( index );
    }
    
//    public static InstrLoadET InstrLoadET( int external, int constant )
//    {
//        return new InstrLoadET( external, constant );
//    }
    
    public static InstrLoadNil InstrLoadNil()
    {
        return _loadNil;
    }
    
    public static InstrStoreL InstrStoreL( int index )
    {
        return ( index < _storeL.length ) ? _storeL[index] : new InstrStoreL( index );
    }
    
    public static InstrStoreE InstrStoreE( int index )
    {
        return ( index < _storeE.length ) ? _storeE[index] : new InstrStoreE( index );
    }
    
//    public static InstrStoreET InstrStoreET( int external, int constant )
//    {
//        return new InstrStoreET( external, constant );
//    }
    
    // ========================================
    
    public static InstrReturn InstrReturn( int count )
    {
        return ( count < _return.length ) ? _return[count] : new InstrReturn( count );
    }
    
    // ========================================
    
    public static InstrNeg InstrNeg()
    {
        return _neg;
    }
    
    public static InstrNot InstrNot()
    {
        return _not;
    }
    
    public static InstrSize InstrSize()
    {
        return _size;
    }
    
    public static InstrAdd InstrAdd()
    {
        return _add;
    }
    
    public static InstrSub InstrSub()
    {
        return _sub;
    }
    
    public static InstrMul InstrMul()
    {
        return _mul;
    }
    
    public static InstrDiv InstrDiv()
    {
        return _div;
    }
    
    public static InstrMod InstrMod()
    {
        return _mod;
    }
    
    public static InstrPow InstrPow()
    {
        return _pow;
    }
    
    public static InstrEQ InstrEq()
    {
        return _eq;
    }
    
    public static InstrLT InstrLt()
    {
        return _lt;
    }
    
    public static InstrLTE InstrLte()
    {
        return _lte;
    }
    
    public static InstrDup InstrDup()
    {
        return _dup;
    }
    
    public static InstrPop InstrPop()
    {
        return _pop;
    }
    
    public static InstrNewTable InstrNewTable()
    {
        return _newTable;
    }
    
    public static InstrLoadT InstrLoadT()
    {
        return _loadT;
    }
    
    public static InstrStoreT InstrStoreT()
    {
        return _storeT;
    }
    
    // ========================================
    
    // ========================================
    
    private InstructionCache()
    {}
}
