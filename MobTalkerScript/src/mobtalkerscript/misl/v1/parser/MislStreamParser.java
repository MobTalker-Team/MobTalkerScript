package mobtalkerscript.misl.v1.parser;

import java.io.*;
import java.util.regex.*;

import mobtalkerscript.*;
import mobtalkerscript.misl.v1.*;
import mobtalkerscript.misl.v1.instruction.*;
import mobtalkerscript.misl.v1.value.*;

import org.apache.commons.lang3.*;

public class MislStreamParser
{
    
    private static final Pattern _booleanPattern = Pattern.compile("^(true|false)$"); // true, false
    private static final Pattern _numberPattern = Pattern.compile("^([+-]?\\d+(:?\\.\\d+)?)$"); // 0, 0.0
    private static final Pattern _stringPattern = Pattern.compile("^\"(.*?)\"$"); // "abc"
    private static final Pattern _variablePattern = Pattern.compile("^([a-zA-Z_]\\w*)$"); // $x
    
    // ========================================
    
    private final BufferedReader _reader;
    
    private final MislInstructionList _instrList;
    private final SimpleBindings _bindings;
    
    private String _lastInstr;
    
    // ========================================
    
    public MislStreamParser(BufferedReader reader)
    {
        _reader = reader;
        _instrList = new MislInstructionList();
        _bindings = new SimpleBindings();
    }
    
    // ========================================
    
    public IBindings parse() throws IOException
    {
        parseInstructions();
        
        return _bindings;
    }
    
    private void parseInstructions() throws IOException
    {
        for (String instrStr = _reader.readLine(); instrStr != null; instrStr = _reader.readLine())
        {
            instrStr = StringUtils.trimToNull(instrStr);
            
            if (instrStr != null)
            {
                if (!parseInstruction(instrStr))
                {
                    break;
                }
            }
        }
    }
    
    private static class ParserToken
    {
        public final String Instruction;
        public final String[] Arguments;
        
        public ParserToken(String instruction, String[] arguments)
        {
            super();
            Instruction = instruction;
            Arguments = arguments;
        }
    }
    
    private ParserToken getToken(String line)
    {
        String[] split = line.split(" ", 2);
        String instr = split[0];
        String[] args = new String[3];
        
//        System.out.println(instr);
        
        if (split.length > 1)
        {
            InstrArgMatcher m = new InstrArgMatcher(split[1]);
            
            for (int i = 0; m.find(); i++)
            {
//                System.out.println(m.getMatch());
                args[i] = m.getMatch();
            }
        }
        
        return new ParserToken(instr, args);
    }
    
    private boolean parseInstruction(String instrStr) throws IOException
    {
        System.out.println("Parsing " + instrStr);
        
        ParserToken token = getToken(instrStr);
        _lastInstr = token.Instruction;
        
        // Instructions
        
        if (token.Instruction.equals("#"))
        {
            ; // Comment
        }
        else if (token.Instruction.equals("add"))
        {
            _instrList.add(new InstrAdd());
        }
        else if (token.Instruction.equals("and"))
        {
            MislInstruction lcont = new InstrLabel("continue");
            
            _instrList.add(new InstrAnd(lcont));
            
            parseInstructions();
            
            _instrList.add(lcont);
        }
        else if (token.Instruction.equals("await"))
        {
            _instrList.add(new InstrAwait());
        }
        else if (token.Instruction.equals("call"))
        {
            int argCount = Integer.parseInt(token.Arguments[0]);
            int retCount = Integer.parseInt(token.Arguments[1]);
            
            _instrList.add(new InstrCall(argCount, retCount));
        }
        else if (token.Instruction.equals("concat"))
        {
            _instrList.add(new InstrConcat());
        }
        else if (token.Instruction.equals("cond"))
        {
//                result.add(new InstrLabel("cond"));
        }
        else if (token.Instruction.equals("continue"))
        {
            return false;
        }
        else if (token.Instruction.equals("create_t"))
        {
            int size = Integer.parseInt(token.Arguments[0]);
            _instrList.add(new InstrCreateT(size));
        }
        else if (token.Instruction.equals("do"))
        {
            return false;
        }
        else if (token.Instruction.equals("div"))
        {
            _instrList.add(new InstrDiv());
        }
        else if (token.Instruction.equals("dup"))
        {
            _instrList.add(new InstrDup());
        }
        else if (token.Instruction.equals("else"))
        {
            return false;
        }
        else if (token.Instruction.equals("elseif"))
        {
            return false;
        }
        else if (token.Instruction.equals("eq"))
        {
            _instrList.add(new InstrEqual());
        }
        else if (token.Instruction.equals("error"))
        {
            _instrList.add(new InstrError());
        }
        else if (token.Instruction.equals("function"))
        {
            String funcName = token.Arguments[0];
            InstrLabel label = new InstrLabel(funcName);
            int argCount = Integer.parseInt(token.Arguments[1]);
            
            _bindings.set(funcName, new MislFunction(label, argCount));
            
            _instrList.add(label);
        }
        else if (token.Instruction.equals("gt"))
        {
            _instrList.add(new InstrGreaterThen());
        }
        else if (token.Instruction.equals("gte"))
        {
            _instrList.add(new InstrGreaterThenEqual());
        }
        else if (token.Instruction.equals("if"))
        {
            MislInstruction lcont = new InstrLabel("continue");
            
            for (;;)
            {
                MislInstruction lelse = new InstrLabel("else");
                
                // if condition
                parseInstructions();
                _instrList.add(new InstrJumpIfNot(lelse));
                
                // then block
                parseInstructions();
                _instrList.add(new InstrJump(lcont, false, false));
                
                _instrList.add(lelse);
                
                if (!_lastInstr.equals("elseif"))
                {
                    break;
                }
            }
            
            // Else block
            parseInstructions();
            _instrList.add(lcont);
        }
        else if (token.Instruction.equals("jumpf"))
        {
            _instrList.add(new InstrJumpF());
        }
//        else if (token.Instruction.equals("label"))
//        {
//            String labelName = token.Arguments[0];
//            InstrLabel label = new InstrLabel(labelName);
//            
//            _bindings.set(labelName, new MislFunction(label, 0));
//            
//            _instrList.add(label);
//        }
        else if (token.Instruction.equals("line"))
        {
            String lineNumStr = token.Arguments[0];
            int lineNum = Integer.parseInt(lineNumStr);
            
            _instrList.add(new InstrLine(lineNum));
        }
        else if (token.Instruction.equals("load"))
        {
            String varNameStr = token.Arguments[0];
            String varName = parseVariableName(varNameStr);
            
            _instrList.add(new InstrLoad(varName));
        }
        else if (token.Instruction.equals("load_t"))
        {
            _instrList.add(new InstrLoadT());
        }
        else if (token.Instruction.equals("lt"))
        {
            _instrList.add(new InstrLessThen());
        }
        else if (token.Instruction.equals("lte"))
        {
            _instrList.add(new InstrLessThenEqual());
        }
        else if (token.Instruction.equals("mod"))
        {
            _instrList.add(new InstrMod());
        }
        else if (token.Instruction.equals("mul"))
        {
            _instrList.add(new InstrMul());
        }
        else if (token.Instruction.equals("neg"))
        {
            _instrList.add(new InstrNeg());
        }
        else if (token.Instruction.equals("not"))
        {
            _instrList.add(new InstrNot());
        }
        else if (token.Instruction.equals("neq"))
        {
            _instrList.add(new InstrNotEqual());
        }
        else if (token.Instruction.equals("or"))
        {
            MislInstruction lcont = new InstrLabel("continue");
            
            _instrList.add(new InstrOr(lcont));
            
            // b
            parseInstructions();
            
            _instrList.add(lcont);
        }
        else if (token.Instruction.equals("pop"))
        {
            _instrList.add(new InstrPop());
        }
        else if (token.Instruction.equals("push"))
        {
            String constantStr = token.Arguments[0];
            MislValue constant = parseConstant(constantStr);
            
            _instrList.add(new InstrPush(constant));
        }
        else if (token.Instruction.equals("return"))
        {
            _instrList.add(new InstrReturn());
        }
        else if (token.Instruction.equals("store"))
        {
            String varNameStr = token.Arguments[0];
            String varName = parseVariableName(varNameStr);
            
            _instrList.add(new InstrStore(varName));
        }
        else if (token.Instruction.equals("store_c"))
        {
            String varNameStr = token.Arguments[0];
            String varName = parseVariableName(varNameStr);
            
            String constantStr = token.Arguments[1];
            MislValue constant = parseConstant(constantStr);
            
            _instrList.add(new InstrStoreC(varName, constant));
        }
        else if (token.Instruction.equals("store_l"))
        {
            String varNameStr = token.Arguments[0];
            String varName = parseVariableName(varNameStr);
            
            _instrList.add(new InstrStoreL(varName));
        }
        else if (token.Instruction.equals("store_t"))
        {
            _instrList.add(new InstrStoreT());
        }
        else if (token.Instruction.equals("sub"))
        {
            _instrList.add(new InstrSub());
        }
        else if (token.Instruction.equals("then"))
        {
            return false;
        }
        else if (token.Instruction.equals("while"))
        {
            MislInstruction lwhile = new InstrLabel("while");
            MislInstruction ldo = new InstrLabel("do");
            MislInstruction lcont = new InstrLabel("continue");
            
            _instrList.add(lwhile);
            
            // while condition
            parseInstructions();
            _instrList.add(new InstrJumpIfNot(lcont));
            
            _instrList.add(ldo);
            
            // do block
            parseInstructions();
            _instrList.add(new InstrJump(lwhile, false, false));
            
            _instrList.add(lcont);
        }
        else
        {
            throw new ScriptParserException("Unknown instruction: %s", token.Instruction);
        }
        
        return true;
    }
    
    // ========================================
    
    private static MislValue parseConstant(String token)
    {
        MislValue result;
        Matcher matcher;
        if (token == null)
        {
            throw new ScriptParserException("Invalid constant: NullPointer");
        }
        else if ((matcher = _booleanPattern.matcher(token)).matches())
        {
            boolean bool = Boolean.valueOf(matcher.group(1));
            result = MislValue.valueOf(bool);
        }
        else if ((matcher = _numberPattern.matcher(token)).matches())
        {
            double number = Double.valueOf(matcher.group(1));
            result = MislValue.valueOf(number);
        }
        else if ((matcher = _stringPattern.matcher(token)).matches())
        {
            String str = matcher.group(1);
            result = MislValue.valueOf(str);
        }
        else if ("nil".equals(token))
        {
            result = MislValue.NIL;
        }
        else
        {
            throw new ScriptParserException("Invalid constant: %s", token);
        }
        
        return result;
    }
    
    private static String parseVariableName(String token)
    {
        Matcher matcher = _variablePattern.matcher(token);
        
        if (!matcher.matches())
        {
            throw new ScriptParserException("Invalid variable name: %s", token);
        }
        
        return matcher.group(1);
    }
    
}
