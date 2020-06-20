/*
 * Copyright (C) 2013-2020 Chimaine, MobTalkerScript contributors
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

import java.io.*;
import java.nio.file.*;
import java.util.List;

import net.mobtalker.mobtalkerscript.v3.*;
import net.mobtalker.mobtalkerscript.v3.compiler.SourcePosition;
import net.mobtalker.mobtalkerscript.v3.instruction.MtsInstruction;
import net.mobtalker.mobtalkerscript.v3.value.MtsValue;

public class FunctionBinaryWriter
{
    public static void writeChunk( MtsFunctionPrototype prototype, Path path, OpenOption... options ) throws IOException
    {
        try (
            BufferedOutputStream stream = new BufferedOutputStream( Files.newOutputStream( path, options ) ) )
        {
            writeChunk( prototype, stream );
        }
    }
    
    public static void writeChunk( MtsFunctionPrototype prototype, OutputStream stream ) throws IOException
    {
        new FunctionBinaryWriter().write( prototype, stream );
    }
    
    // ========================================
    
    public void write( MtsFunctionPrototype prototype, OutputStream stream ) throws IOException
    {
        write( prototype, new DataOutputStream( stream ) );
    }
    
    private void write( MtsFunctionPrototype prototype, DataOutputStream stream ) throws IOException
    {
        stream.writeBytes( "MTS" );
        stream.writeByte( ( VERSION_MAJOR << 4 ) | VERSION_MINOR );
        
        stream.writeUTF( prototype.getName() );
        stream.writeUTF( prototype.getSource() );
        stream.writeShort( prototype.getSourceLineStart() );
        stream.writeShort( prototype.getSourceLineEnd() );
        stream.writeByte( prototype.getParameterCount() );
        stream.writeByte( prototype.getMaxStackSize() );
        stream.writeBoolean( prototype.hasVarargs() );
        
        int nConstants = prototype.getConstantCount();
        stream.writeShort( nConstants );
        for ( int i = 0; i < nConstants; i++ )
        {
            MtsValue constant = prototype.getConstant( i );
            if ( constant.isString() )
            {
                stream.writeBoolean( true );
                stream.writeUTF( constant.asString().toJava() );
            }
            else
            {
                stream.writeBoolean( false );
                stream.writeDouble( constant.asNumber().toJavaDouble() );
            }
        }
        
        int nLocals = prototype.getLocalCount();
        stream.writeByte( nLocals );
        for ( int i = 0; i < nLocals; i++ )
        {
            LocalDescription local = prototype.getLocalDescription( i );
            stream.writeUTF( local.getName() );
            stream.writeShort( local.getStart() );
            stream.writeShort( local.getEnd() );
        }
        
        int nExternals = prototype.getExternalCount();
        stream.writeByte( nExternals );
        for ( int i = 0; i < nExternals; i++ )
        {
            ExternalDescription external = prototype.getExternalDescription( i );
            stream.writeUTF( external.getName() );
            stream.writeBoolean( external.isParentLocal() );
            stream.writeByte( external.getParentIndex() );
        }
        
        MtsInstruction[] instrs = prototype.getInstructions();
        int nInstrs = instrs.length;
        stream.writeShort( nInstrs );
        for ( int i = 0; i < nInstrs; i++ )
        {
            instrs[i].writeTo( stream );
            SourcePosition sourcePosition = prototype.getSourcePosition( i );
            stream.writeShort( sourcePosition.Line );
            stream.writeShort( sourcePosition.Coloum );
        }
        
        List<MtsFunctionPrototype> childs = prototype.getNestedPrototypes();
        int nChilds = childs.size();
        stream.writeByte( nChilds );
        for ( int i = 0; i < nChilds; i++ )
        {
            write( childs.get( i ), stream );
        }
    }
}
