/*
 * Copyright (C) 2013-2015 Chimaine
 * 
 * This program is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package net.mobtalker.mobtalkerscript.v3.serialization;

import static net.mobtalker.mobtalkerscript.v3.MtsGlobals.*;
import static net.mobtalker.mobtalkerscript.v3.instruction.Instructions.*;

import java.io.*;
import java.nio.file.*;
import java.util.Arrays;

import net.mobtalker.mobtalkerscript.v3.*;
import net.mobtalker.mobtalkerscript.v3.compiler.SourcePosition;
import net.mobtalker.mobtalkerscript.v3.instruction.MtsInstruction;
import net.mobtalker.mobtalkerscript.v3.value.*;

public class FunctionBinaryReader
{
    public static MtsFunctionPrototype readChunk( Path path ) throws IOException
    {
        try (
            BufferedInputStream stream = new BufferedInputStream( Files.newInputStream( path ) ) )
        {
            return readChunk( stream );
        }
    }
    
    public static MtsFunctionPrototype readChunk( InputStream stream ) throws IOException
    {
        return new FunctionBinaryReader().read( stream );
    }
    
    // ========================================
    
    public MtsFunctionPrototype read( InputStream stream ) throws IOException
    {
        return read( new DataInputStream( stream ) );
    }
    
    private MtsFunctionPrototype read( DataInputStream stream ) throws IOException
    {
        if ( !( ( stream.read() == 'M' ) && ( stream.read() == 'T' ) && ( stream.read() == 'S' ) ) )
            throw new IOException( "invalid file header" );
        if ( !( stream.read() == ( ( VERSION_MAJOR << 4 ) | VERSION_MINOR ) ) )
            throw new IOException( "incompatible file version" );
        
        String name = stream.readUTF();
        String source = stream.readUTF();
        int sourceStart = stream.readUnsignedShort();
        int sourceEnd = stream.readUnsignedShort();
        int paramCount = stream.readUnsignedByte();
        int maxStack = stream.readUnsignedByte();
        boolean hasVarargs = stream.readBoolean();
        
        int nConstants = stream.readUnsignedShort();
        MtsValue[] constants = new MtsValue[nConstants];
        for ( int i = 0; i < nConstants; i++ )
        {
            constants[i] = stream.readBoolean()
                    ? MtsString.of( stream.readUTF() )
                    : MtsNumber.of( stream.readDouble() );
        }
        
        int nLocals = stream.readUnsignedByte();
        LocalDescription[] locals = new LocalDescription[nLocals];
        for ( int i = 0; i < nLocals; i++ )
        {
            locals[i] = new LocalDescription( stream.readUTF(), i, stream.readUnsignedShort(), stream.readUnsignedShort() );
        }
        
        int nExternals = stream.readUnsignedByte();
        ExternalDescription[] externals = new ExternalDescription[nExternals];
        for ( int i = 0; i < nExternals; i++ )
        {
            externals[i] = new ExternalDescription( stream.readUTF(), i,
                                                    stream.readBoolean(),
                                                    stream.readUnsignedByte() );
        }
        
        int nInstrs = stream.readUnsignedShort();
        MtsInstruction[] instrs = new MtsInstruction[nInstrs];
        SourcePosition[] sourcePositions = new SourcePosition[nInstrs];
        for ( int i = 0; i < nInstrs; i++ )
        {
            instrs[i] = readInstruction( stream );
            sourcePositions[i] = new SourcePosition( stream.readUnsignedShort(), stream.readUnsignedShort() );
        }
        
        MtsFunctionPrototype prototype = new MtsFunctionPrototype( name,
                                                                   Arrays.asList( constants ),
                                                                   Arrays.asList( locals ),
                                                                   Arrays.asList( externals ),
                                                                   paramCount, hasVarargs, maxStack,
                                                                   Arrays.asList( instrs ),
                                                                   Arrays.asList( sourcePositions ),
                                                                   source, sourceStart, sourceEnd );
        
        int nChilds = stream.readUnsignedByte();
        for ( int i = 0; i < nChilds; i++ )
        {
            prototype.addNestedPrototype( read( stream ) );
        }
        
        return prototype;
    }
    
    private static MtsInstruction readInstruction( DataInputStream stream ) throws IOException
    {
        int instr = stream.readUnsignedByte();
        switch ( instr )
        {
            case ADD:
                return InstrAdd();
            case AND:
                return InstrAnd( stream.readUnsignedByte() );
            case CALL:
                return InstrCall( stream.readUnsignedByte(), stream.readByte() );
            case CLSR:
                return InstrClosure( stream.readUnsignedByte() );
            case CONC:
                return InstrConcat( stream.readUnsignedByte() );
            case DIV:
                return InstrDiv();
            case DUP:
                return InstrDup();
            case EQ:
                return InstrEq();
            case GFL:
                return InstrGForLoop( stream.readUnsignedByte(), stream.readUnsignedByte(), stream.readUnsignedByte() );
            case JMP:
                return InstrJump( stream.readShort() );
            case LDC:
                return InstrLoadC( stream.readUnsignedShort() );
            case LDE:
                return InstrLoadE( stream.readUnsignedByte() );
            case LDFALSE:
                return InstrLoadFalse();
            case LDL:
                return InstrLoadL( stream.readUnsignedByte() );
            case LDM:
                return InstrLoadM( stream.readUnsignedShort() );
            case LDNIL:
                return InstrLoadNil( stream.readUnsignedByte() );
            case LDT:
                return InstrLoadT();
            case LDTC:
                return InstrLoadTC( stream.readUnsignedShort() );
            case LDTRUE:
                return InstrLoadTrue();
            case LT:
                return InstrLessThen();
            case LTE:
                return InstrLessThenEqual();
            case MOD:
                return InstrMod();
            case MUL:
                return InstrMul();
            case NEG:
                return InstrNeg();
            case NEWTBL:
                return InstrNewTable( stream.readUnsignedByte(), stream.readUnsignedByte() );
            case NFL:
                return InstrNForLoop( stream.readUnsignedByte(), stream.readUnsignedShort() );
            case NFP:
                return InstrNForPrep( stream.readUnsignedByte() );
            case NOT:
                return InstrNot();
            case OR:
                return InstrOr( stream.readUnsignedByte() );
            case POP:
                return InstrPop();
            case POW:
                return InstrPow();
            case RET:
                return InstrReturn( stream.readUnsignedByte() );
            case SIZE:
                return InstrSize();
            case STE:
                return InstrStoreE( stream.readUnsignedByte() );
            case STL:
                return InstrStoreL( stream.readUnsignedByte() );
            case STT:
                return InstrStoreT();
            case SUB:
                return InstrSub();
            case TCALL:
                return InstrTailcall( stream.readUnsignedByte() );
            case TEST:
                return InstrTest( stream.readShort() );
            case VARARG:
                return InstrVarargs( stream.readByte() );
            default:
                throw new AssertionError( instr );
        }
    }
}
