/*
 * SPDX-FileCopyrightText: 2013-2020 Chimaine, MobTalkerScript contributors
 *
 * SPDX-License-Identifier: LGPL-3.0-or-later
 */
package net.mobtalker.mobtalkerscript.v3.instruction;

import java.util.HashMap;
import java.util.Map;

import static org.apache.commons.lang3.Validate.notNull;

public class Instructions
{
    public static final int ADD = 0x01;
    public static final String ADD_NAME = "ADD";
    
    public static final int AND = 0x02;
    public static final String AND_NAME = "AND";
    
    public static final int CALL = 0x03;
    public static final String CALL_NAME = "CALL";
    
    public static final int CLSR = 0x04;
    public static final String CLSR_NAME = "CLSR";
    
    public static final int CONC = 0x05;
    public static final String CONC_NAME = "CONC";
    
    public static final int DIV = 0x06;
    public static final String DIV_NAME = "DIV";
    
    public static final int DUP = 0x07;
    public static final String DUP_NAME = "DUP";
    
    public static final int EQ = 0x08;
    public static final String EQ_NAME = "EQ";
    
    public static final int GFL = 0x09;
    public static final String GFL_NAME = "GFL";
    
    public static final int JMP = 0x0A;
    public static final String JMP_NAME = "JMP";
    
    public static final int LT = 0x0B;
    public static final String LT_NAME = "LT";
    
    public static final int LTE = 0x0C;
    public static final String LTE_NAME = "LTE";
    
    public static final int LDC = 0x0D;
    public static final String LDC_NAME = "LDC";
    
    public static final int LDE = 0x0E;
    public static final String LDE_NAME = "LDE";
    
    public static final int LDFALSE = 0x0F;
    public static final String LDFALSE_NAME = "LDFALSE";
    
    public static final int LDL = 0x10;
    public static final String LDL_NAME = "LDL";
    
    public static final int LDM = 0x11;
    public static final String LDM_NAME = "LDM";
    
    public static final int LDNIL = 0x12;
    public static final String LDNIL_NAME = "LDNIL";
    
    public static final int LDT = 0x13;
    public static final String LDT_NAME = "LDT";
    
    public static final int LDTC = 0x14;
    public static final String LDTC_NAME = "LDTC";
    
    public static final int LDTRUE = 0x15;
    public static final String LDTRUE_NAME = "LDTRUE";
    
    public static final int MOD = 0x16;
    public static final String MOD_NAME = "MOD";
    
    public static final int MUL = 0x17;
    public static final String MUL_NAME = "MUL";
    
    public static final int NEG = 0x18;
    public static final String NEG_NAME = "NEG";
    
    public static final int NEWTBL = 0x19;
    public static final String NEWTBL_NAME = "NEWTBL";
    
    public static final int NFL = 0x1A;
    public static final String NFL_NAME = "NFL";
    
    public static final int NFP = 0x1B;
    public static final String NFP_NAME = "NFP";
    
    public static final int NOT = 0x1C;
    public static final String NOT_NAME = "NOT";
    
    public static final int OR = 0x1D;
    public static final String OR_NAME = "OR";
    
    public static final int POP = 0x1E;
    public static final String POP_NAME = "POP";
    
    public static final int POW = 0x1F;
    public static final String POW_NAME = "POW";
    
    public static final int RET = 0x20;
    public static final String RET_NAME = "RET";
    
    public static final int SIZE = 0x21;
    public static final String SIZE_NAME = "SIZE";
    
    public static final int STE = 0x22;
    public static final String STE_NAME = "STE";
    
    public static final int STL = 0x23;
    public static final String STL_NAME = "STL";
    
    public static final int STT = 0x24;
    public static final String STT_NAME = "STT";
    
    public static final int SUB = 0x25;
    public static final String SUB_NAME = "SUB";
    
    public static final int TCALL = 0x26;
    public static final String TCALL_NAME = "TCALL";
    
    public static final int TEST = 0x27;
    public static final String TEST_NAME = "TEST";
    
    public static final int VARARG = 0x28;
    public static final String VARARG_NAME = "VARARG";
    
    // ========================================
    
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
    
    private static final InstrConcat[] _concat;
    
    private static final Map<String, MtsInstruction> _logicalOps;
    private static final InstrEqual _eq = new InstrEqual();
    private static final InstrLessThen _lt = new InstrLessThen();
    private static final InstrLessThenEqual _lte = new InstrLessThenEqual();
    
    private static final InstrDup _dup = new InstrDup();
    private static final InstrPop _pop = new InstrPop();
    
    private static final InstrLoadT _loadT = new InstrLoadT();
    private static final InstrStoreT _storeT = new InstrStoreT();
    
    private static final InstrLoadTC[] _loadTC;
    
    private static final InstrLoadL[] _loadL;
    private static final InstrLoadE[] _loadE;
    private static final InstrLoadC[] _loadC;
    
    private static final InstrLoadNil[] _loadNil;
    private static final InstrLoadTrue _loadTrue = new InstrLoadTrue();
    private static final InstrLoadFalse _loadFalse = new InstrLoadFalse();
    
    private static final InstrStoreL[] _storeL;
    private static final InstrStoreE[] _storeE;
    
    private static final InstrVarargs _varargsAll = new InstrVarargs( -1 );
    private static final InstrVarargs[] _varargs;
    
    private static final InstrReturn[] _return;
    
    // ========================================
    
    static
    {
        _unaryOps = new HashMap<>(3, 1);
        _unaryOps.put( "-", InstrNeg() );
        _unaryOps.put( "not", InstrNot() );
        _unaryOps.put( "#", InstrSize() );
        
        _binaryOps = new HashMap<>(6, 1);
        _binaryOps.put( "+", InstrAdd() );
        _binaryOps.put( "-", InstrSub() );
        _binaryOps.put( "*", InstrMul() );
        _binaryOps.put( "/", InstrDiv() );
        _binaryOps.put( "%", InstrMod() );
        _binaryOps.put( "^", InstrPow() );
        
        _concat = new InstrConcat[10];
        for ( int i = 0; i < 10; i++ )
            _concat[i] = new InstrConcat( i );
        
        _binaryOps.put( "..", InstrConcat( 2 ) );
        
        _logicalOps = new HashMap<>(3, 1);
        _logicalOps.put( "==", InstrEq() );
        _logicalOps.put( "<", InstrLessThen() );
        _logicalOps.put( "<=", InstrLessThenEqual() );
        
        _loadNil = new InstrLoadNil[10];
        for ( int i = 0; i < _loadNil.length; i++ )
        {
            _loadNil[i] = new InstrLoadNil( i + 1 );
        }
        
        _loadL = new InstrLoadL[32];
        _storeL = new InstrStoreL[_loadL.length];
        for ( int i = 0; i < _loadL.length; i++ )
        {
            _loadL[i] = new InstrLoadL( i );
            _storeL[i] = new InstrStoreL( i );
        }
        
        _loadE = new InstrLoadE[16];
        _storeE = new InstrStoreE[_loadE.length];
        for ( int i = 0; i < _loadE.length; i++ )
        {
            _loadE[i] = new InstrLoadE( i );
            _storeE[i] = new InstrStoreE( i );
        }
        
        _loadC = new InstrLoadC[128];
        for ( int i = 0; i < _loadC.length; i++ )
            _loadC[i] = new InstrLoadC( i );
        
        _loadTC = new InstrLoadTC[1];
        for ( int i = 0; i < _loadTC.length; i++ )
            _loadTC[i] = new InstrLoadTC( i );
        
        _varargs = new InstrVarargs[5];
        for ( int i = 0; i < _varargs.length; i++ )
            _varargs[i] = new InstrVarargs( i + 1 );
        
        _return = new InstrReturn[10];
        for ( int i = 0; i < _return.length; i++ )
            _return[i] = new InstrReturn( i );
    }
    
    // ========================================
    
    public static MtsInstruction InstrUnaryOp( String op )
    {
        MtsInstruction instr = _unaryOps.get( op );
        notNull(instr, "%s is not a valid unary operator!", op);
        
        return instr;
    }
    
    public static MtsInstruction InstrBinaryOp( String op )
    {
        MtsInstruction instr = _binaryOps.get( op );
        notNull( instr, "%s is not a valid binary operator!", op );
        
        return instr;
    }
    
    public static MtsInstruction InstrLogicalOp( String op )
    {
        MtsInstruction instr = _logicalOps.get( op );
        notNull( instr, "%s is not a valid logic operator!", op );
        
        return instr;
    }
    
    // ========================================
    // Jump Instructions. These should never be cached or interned.
    
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
    
    public static InstrConcat InstrConcat( int count )
    {
        return ( count < _concat.length ) ? _concat[count] : new InstrConcat( count );
    }
    
    public static InstrEqual InstrEq()
    {
        return _eq;
    }
    
    public static InstrLessThen InstrLessThen()
    {
        return _lt;
    }
    
    public static InstrLessThenEqual InstrLessThenEqual()
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
    
    public static InstrLoadNil InstrLoadNil( int count )
    {
        return ( count <= _loadNil.length ) ? _loadNil[count - 1] : new InstrLoadNil( count );
    }
    
    public static InstrLoadTrue InstrLoadTrue()
    {
        return _loadTrue;
    }
    
    public static InstrLoadFalse InstrLoadFalse()
    {
        return _loadFalse;
    }
    
    public static InstrLoadT InstrLoadT()
    {
        return _loadT;
    }
    
    public static InstrLoadTC InstrLoadTC( int index )
    {
        return ( index < _loadTC.length ) ? _loadTC[index] : new InstrLoadTC( index );
    }
    
    public static InstrLoadM InstrLoadM( int index )
    {
        return new InstrLoadM( index );
    }
    
    public static MtsInstruction InstrVarargs( int count )
    {
        if ( count == -1 )
            return _varargsAll;
        return ( count <= _varargs.length ) ? _varargs[count - 1] : new InstrVarargs( count );
    }
    
    public static InstrNewTable InstrNewTable( int listElements, int hashPairs )
    {
        return new InstrNewTable( listElements, hashPairs );
    }
    
    // ========================================
    
    public static InstrStoreL InstrStoreL( int index )
    {
        return ( index < _storeL.length ) ? _storeL[index] : new InstrStoreL( index );
    }
    
    public static InstrStoreE InstrStoreE( int index )
    {
        return ( index < _storeE.length ) ? _storeE[index] : new InstrStoreE( index );
    }
    
    public static InstrStoreT InstrStoreT()
    {
        return _storeT;
    }
    
    // ========================================
    
    public static InstrClosure InstrClosure( int index )
    {
        return new InstrClosure( index );
    }
    
    public static InstrCall InstrCall( int nArgs, int nReturn )
    {
        return new InstrCall( nArgs, nReturn );
    }
    
    public static InstrReturn InstrReturn( int count )
    {
        return ( count < _return.length ) ? _return[count] : new InstrReturn( count );
    }
    
    public static InstrTailcall InstrTailcall( int nArgs )
    {
        return new InstrTailcall( nArgs );
    }
    
    // ========================================
    
    public static InstrNForPrep InstrNForPrep( int varIndex )
    {
        return new InstrNForPrep( varIndex );
    }
    
    public static InstrNForLoop InstrNForLoop( int varIndex )
    {
        return new InstrNForLoop( varIndex );
    }
    
    public static InstrNForLoop InstrNForLoop( int varIndex, int jumpDistance )
    {
        return new InstrNForLoop( varIndex, jumpDistance );
    }
    
    public static InstrGForLoop InstrGForLoop( int varIndex, int nVars )
    {
        return new InstrGForLoop( varIndex, nVars );
    }
    
    public static InstrGForLoop InstrGForLoop( int varIndex, int nVars, int jumpDistance )
    {
        return new InstrGForLoop( varIndex, nVars, jumpDistance );
    }
    
    // ========================================
    
    private Instructions()
    {}
}
