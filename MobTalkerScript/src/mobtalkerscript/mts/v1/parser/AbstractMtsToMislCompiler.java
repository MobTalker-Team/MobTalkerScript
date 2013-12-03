package mobtalkerscript.mts.v1.parser;

import java.util.*;

import mobtalkerscript.misl.v1.instruction.*;
import mobtalkerscript.util.logging.*;

import com.google.common.collect.*;

public abstract class AbstractMtsToMislCompiler extends MtsBaseVisitor<Void>
{
    private final ArrayList<MislInstruction> _instructionList;
    private final MislInstructionList _instructions;
    
    private String _curSourceName;
    private int _curSourceLine;
    
    // ========================================
    
    {
        _instructionList = Lists.newArrayListWithExpectedSize( 100 );
        _instructions = new MislInstructionList();
    }
    
    // ========================================
    
    public boolean setSourcePosition( String source, int line )
    {
        if ( Objects.equals( source, _curSourceName ) && ( line == _curSourceLine ) )
        {
            return false;
        }
        
        _curSourceName = source;
        _curSourceLine = line;
        
        return true;
    }
    
    public void addInstr( MislInstruction instr )
    {
        _instructions.add( instr );
        _instructionList.add( instr );
        
        MTSLog.finest( "[Compiler] %s", instr.toString() );
    }
    
    // ========================================
    
    public List<MislInstruction> getInstructionsAsList()
    {
        return _instructionList;
    }
    
}