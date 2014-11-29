package net.mobtalker.mobtalkerscript.v3;

import java.io.*;
import java.util.List;

import net.mobtalker.mobtalkerscript.v3.instruction.MtsInstruction;

/**
 * Serializes a function prototype into a textual format.
 */
public class FunctionTextWriter
{
    public void write( MtsFunctionPrototype prototype, Writer writer ) throws IOException
    {
        // Name
        writer.append( ".function " ).append( prototype.getName() )
              .append( '\n' );
        
        // Source
        writer.append( ".source " ).append( prototype.getSource() )
              .append( ':' )
              .append( Integer.toString( prototype.getSourceLineStart() ) )
              .append( '-' )
              .append( Integer.toString( prototype.getSourceLineEnd() ) )
              .append( '\n' );
        
        // Constants
        int nConstants = prototype.getConstantCount();
        if ( nConstants > 0 )
        {
            writer.append( ".constants " ).append( Integer.toString( nConstants ) )
                  .append( '\n' );
            for ( int i = 0; i < nConstants; i++ )
            {
                writer.append( '[' ).append( Integer.toString( i ) ).append( "] " )
                      .append( prototype.getConstant( i ).toString() )
                      .append( '\n' );
            }
        }
        
        // Locals
        int nLocals = prototype.getLocalCount();
        if ( nLocals > 0 )
        {
            writer.append( ".locals " ).append( Integer.toString( nLocals ) )
                  .append( '\n' );
            for ( int i = 0; i < nLocals; i++ )
            {
                writer.append( '[' ).append( Integer.toString( i ) ).append( "] " )
                      .append( prototype.getLocalDescription( i ).toString() )
                      .append( '\n' );
            }
        }
        
        // Locals
        int nExternals = prototype.getExternalCount();
        if ( nExternals > 0 )
        {
            writer.append( ".externals " ).append( Integer.toString( nExternals ) )
                  .append( '\n' );
            for ( int i = 0; i < nExternals; i++ )
            {
                writer.append( '[' ).append( Integer.toString( i ) ).append( "] " )
                      .append( prototype.getExternalDescription( i ).toString() )
                      .append( '\n' );
            }
        }
        
        // Instructions
        List<MtsInstruction> instrs = prototype.getInstructions();
        int nInstrs = instrs.size();
        if ( nInstrs > 0 )
        {
            writer.append( ".instructions " ).append( Integer.toString( nInstrs ) )
                  .append( '\n' );
            for ( int i = 0; i < nInstrs; i++ )
            {
                writer.append( '[' ).append( Integer.toString( i ) ).append( "] " )
                      .append( instrs.get( i ).toString() )
                      .append( '\n' );
            }
        }
    }
}
