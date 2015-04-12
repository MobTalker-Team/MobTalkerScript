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

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.util.List;

import net.mobtalker.mobtalkerscript.v3.*;
import net.mobtalker.mobtalkerscript.v3.instruction.MtsInstruction;

/**
 * Serializes a function prototype into a textual format.
 */
public class FunctionTextWriter
{
    public static void writeChunk( MtsFunctionPrototype prototype, Path path, Charset charset, OpenOption... options )
            throws IOException
    {
        try (
                BufferedWriter writer = Files.newBufferedWriter( path, charset, options ) )
                {
            writeChunk( prototype, writer );
                }
    }

    public static void writeChunk( MtsFunctionPrototype prototype, Appendable appendable ) throws IOException
    {
        new FunctionTextWriter().write( prototype, appendable );
    }

    // ========================================

    public void write( MtsFunctionPrototype prototype, Appendable appendable ) throws IOException
    {
        write( prototype, appendable, 0 );
    }

    private void write( MtsFunctionPrototype prototype, Appendable appendable, int level ) throws IOException
    {
        // Name
        appendable.append( ".function " ).append( prototype.getName() )
        .append( " (level " ).append( Integer.toString( level ) ).append( ')' )
        .append( '\n' );

        // Source
        appendable.append( ".source " ).append( prototype.getSource() )
        .append( ':' )
        .append( Integer.toString( prototype.getSourceLineStart() ) )
        .append( '-' )
        .append( Integer.toString( prototype.getSourceLineEnd() ) )
        .append( '\n' );

        appendable.append( ".params " ).append( Integer.toString( prototype.getParameterCount() ) )
        .append( '\n' );
        appendable.append( ".stacksize " ).append( Integer.toString( prototype.getMaxStackSize() ) )
        .append( '\n' );
        appendable.append( ".varargs " ).append( prototype.hasVarargs() ? 't' : 'f' )
        .append( '\n' );

        // Constants
        int nConstants = prototype.getConstantCount();
        appendable.append( ".constants " ).append( Integer.toString( nConstants ) )
        .append( '\n' );
        for ( int i = 0; i < nConstants; i++ )
        {
            appendable.append( Integer.toString( i ) )
            .append( ';' )
            .append( prototype.getConstant( i ).toString() )
            .append( '\n' );
        }

        // Locals
        int nLocals = prototype.getLocalCount();
        appendable.append( ".locals " ).append( Integer.toString( nLocals ) )
        .append( '\n' );
        for ( int i = 0; i < nLocals; i++ )
        {
            LocalDescription local = prototype.getLocalDescription( i );
            appendable.append( Integer.toString( i ) )
            .append( ';' )
            .append( local.getName() )
            .append( ';' )
            .append( Integer.toString( local.getStart() ) )
            .append( '-' )
            .append( Integer.toString( local.getEnd() ) )
            .append( '\n' );
        }

        // Locals
        int nExternals = prototype.getExternalCount();
        appendable.append( ".externals " ).append( Integer.toString( nExternals ) )
        .append( '\n' );
        for ( int i = 0; i < nExternals; i++ )
        {
            ExternalDescription external = prototype.getExternalDescription( i );
            appendable.append( Integer.toString( i ) )
            .append( ';' )
            .append( external.getName() )
            .append( ';' )
            .append( external.isParentLocal() ? 'l' : 'e' )
            .append( ';' )
            .append( Integer.toString( external.getParentIndex() ) )
            .append( '\n' );
        }

        // Instructions
        MtsInstruction[] instrs = prototype.getInstructions();
        int nInstrs = instrs.length;
        appendable.append( ".instructions " ).append( Integer.toString( nInstrs ) )
        .append( '\n' );
        for ( int i = 0; i < nInstrs; i++ )
        {
            appendable.append( Integer.toString( i ) )
            .append( ';' )
            .append( instrs[i].toString() )
            .append( ';' )
            .append( prototype.getSourcePosition( i ).toString() )
            .append( '\n' );
        }

        // Childs
        List<MtsFunctionPrototype> childs = prototype.getNestedPrototypes();
        int nChilds = childs.size();
        appendable.append( ".childs " ).append( Integer.toString( nChilds ) )
        .append( '\n' );
        for ( int i = 0; i < nChilds; i++ )
        {
            write( childs.get( i ), appendable, level + 1 );
        }

    }
}
