/*
 * SPDX-FileCopyrightText: 2013-2020 Chimaine, MobTalkerScript contributors
 *
 * SPDX-License-Identifier: LGPL-3.0-or-later
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
