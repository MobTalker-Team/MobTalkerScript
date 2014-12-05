package net.mobtalker.mobtalkerscript.v3.serialization;

import java.io.*;
import java.util.List;

import net.mobtalker.mobtalkerscript.v3.*;
import net.mobtalker.mobtalkerscript.v3.instruction.MtsInstruction;

/**
 * Serializes a function prototype into a textual format.
 */
public class FunctionTextWriter
{
    public void write( MtsFunctionPrototype prototype, Writer writer ) throws IOException
    {
        write( prototype, writer, 0 );
    }
    
    private void write( MtsFunctionPrototype prototype, Writer writer, int level ) throws IOException
    {
        // Name
        writer.append( ".function " ).append( prototype.getName() )
              .append( " (level " ).append( Integer.toString( level ) ).append( ')' )
              .append( '\n' );
        
        // Source
        writer.append( ".source " ).append( prototype.getSource() )
              .append( ':' )
              .append( Integer.toString( prototype.getSourceLineStart() ) )
              .append( '-' )
              .append( Integer.toString( prototype.getSourceLineEnd() ) )
              .append( '\n' );
        
        writer.append( ".params " ).append( Integer.toString( prototype.getParameterCount() ) )
              .append( '\n' );
        writer.append( ".stacksize " ).append( Integer.toString( prototype.getMaxStackSize() ) )
              .append( '\n' );
        writer.append( ".varargs " ).append( prototype.hasVarargs() ? 't' : 'f' )
              .append( '\n' );
        
        // Constants
        int nConstants = prototype.getConstantCount();
        writer.append( ".constants " ).append( Integer.toString( nConstants ) )
              .append( '\n' );
        for ( int i = 0; i < nConstants; i++ )
        {
            writer.append( Integer.toString( i ) )
                  .append( ';' )
                  .append( prototype.getConstant( i ).toString() )
                  .append( '\n' );
        }
        
        // Locals
        int nLocals = prototype.getLocalCount();
        writer.append( ".locals " ).append( Integer.toString( nLocals ) )
              .append( '\n' );
        for ( int i = 0; i < nLocals; i++ )
        {
            LocalDescription local = prototype.getLocalDescription( i );
            writer.append( Integer.toString( i ) )
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
        writer.append( ".externals " ).append( Integer.toString( nExternals ) )
              .append( '\n' );
        for ( int i = 0; i < nExternals; i++ )
        {
            ExternalDescription external = prototype.getExternalDescription( i );
            writer.append( Integer.toString( i ) )
                  .append( ';' )
                  .append( external.getName() )
                  .append( ';' )
                  .append( Integer.toString( external.getParentIndex() ) )
                  .append( ';' )
                  .append( external.isParentLocal() ? 't' : 'f' )
                  .append( '\n' );
        }
        
        // Instructions
        MtsInstruction[] instrs = prototype.getInstructions();
        int nInstrs = instrs.length;
        writer.append( ".instructions " ).append( Integer.toString( nInstrs ) )
              .append( '\n' );
        for ( int i = 0; i < nInstrs; i++ )
        {
            writer.append( Integer.toString( i ) )
                  .append( ';' )
                  .append( instrs[i].toString() )
                  .append( ';' )
                  .append( prototype.getSourcePosition( i ).toString() )
                  .append( '\n' );
        }
        
        // Childs
        List<MtsFunctionPrototype> childs = prototype.getNestedPrototypes();
        int nChilds = childs.size();
        writer.append( ".childs " ).append( Integer.toString( nChilds ) )
              .append( '\n' );
        for ( int i = 0; i < nChilds; i++ )
        {
            write( childs.get( i ), writer, level + 1 );
        }
        
    }
}
