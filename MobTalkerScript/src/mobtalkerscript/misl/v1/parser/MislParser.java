package mobtalkerscript.misl.v1.parser;

import java.io.*;
import java.nio.charset.*;
import java.nio.file.*;
import java.util.*;
import java.util.regex.*;

import mobtalkerscript.*;
import mobtalkerscript.misl.v1.*;
import mobtalkerscript.misl.v1.instruction.*;
import mobtalkerscript.misl.v1.value.*;
import mobtalkerscript.util.Queue;

import org.apache.commons.lang3.*;

import com.google.common.collect.*;

/**
 * Parses lines of MISL instructions.
 * <p>
 * Each parser saves references to each named label it encounters. Therefore, one parser should be used for one script.
 * 
 * TODO Write a Lexer and LexerToken based parser.
 * 
 * @author Chimaine
 */
public class MislParser
{
    
    private static final Pattern _booleanPattern = Pattern.compile("^(true|false)$"); // true, false
    private static final Pattern _numberPattern = Pattern.compile("^([+-]?\\d+(:?\\.\\d+)?)$"); // 0, 0.0
    private static final Pattern _stringPattern = Pattern.compile("^\"(.*?)\"$"); // "abc"
    private static final Pattern _variablePattern = Pattern.compile("^([a-zA-Z_]\\w*)$"); // $x
    
    // ========================================
    
    private final HashMap<String, InstrLabel> _namedLabels;
    private final HashMap<InstrLabel, Integer> _functionArgCount;
    
    // ========================================
    
    {
        _namedLabels = Maps.newHashMap();
        _functionArgCount = Maps.newHashMap();
    }
    
    // ========================================
    
    /**
     * Gets a named label.
     * <p>
     * If the requestes label does not exist, a new one is created.
     * 
     * @param name The name of the label.
     * @return The label assoicated with the specified name within this parser.
     */
    private InstrLabel getNamedLabel(String name)
    {
        InstrLabel label = _namedLabels.get(name);
        
        if (label == null)
        {
            label = new InstrLabel(name);
            _namedLabels.put(name, label);
        }
        
        return label;
    }
    
    // ========================================
    
    private String _lastInstr;
    
    /**
     * Parses MISL instructions by consuming a queue of lines that contain one instruction each.
     * <p>
     * If a nested block is found, like an if or a loop, the parser calls this method on itself recursively.
     * <p>
     * When the queue is empty, a line contains a jump or return statement, this method returns.<br>
     * That means that this method may leave the queue non-empty if there are more then one top-level label and may be called
     * repeatedly until the queue is fully consumed.
     * <p>
     * <b>Note that this parser does only minimal checking of the given instructions!</b> No sanity checks are applied, only
     * unparseable instructions throw an exception.
     * 
     * @param lines The lines to parse.
     * @return The instructions that were parsed in this pass. Can be empty if the queue was empty.
     */
    public MislInstructionList parseInstructions(Queue<String> lines)
    {
        MislInstructionList result = new MislInstructionList();
        
        while (!lines.isEmpty())
        {
            String line = lines.poll();
            
            String[] tokens = line.split(" ", 2);
            
            if ((tokens.length < 1) || tokens[0].isEmpty())
            {
                throw new ScriptParserException("invalid line: %s", line);
            }
            
            String instrToken = tokens[0];
            _lastInstr = instrToken;
            
            String argToken = tokens.length > 1 ? tokens[1] : "";
            
            // System.out.println("Parsing tokens: " + Arrays.toString(tokens));
            
            if (instrToken.equals("#"))
            {
                // Comment
            }
            else if (instrToken.equals("add"))
            {
                result.add(new InstrAdd());
            }
            else if (instrToken.equals("and"))
            {
                MislInstruction lcont = new InstrLabel("continue");
                
                result.add(new InstrAnd(lcont));
                
                MislInstructionList b = parseInstructions(lines);
                result.add(b);
                
                result.add(lcont);
            }
            else if (instrToken.equals("await"))
            {
                result.add(new InstrAwait());
            }
            else if (instrToken.equals("call"))
            {
                String[] args = argToken.split(" ", 2);
                
                int argCount = Integer.parseInt(args[0]);
                int retCount = Integer.parseInt(args[1]);
                
                result.add(new InstrCall(argCount, retCount));
            }
            else if (instrToken.equals("concat"))
            {
                result.add(new InstrConcat());
            }
            else if (instrToken.equals("cond"))
            {
//                result.add(new InstrLabel("cond"));
            }
            else if (instrToken.equals("continue"))
            {
                break;
            }
            else if (instrToken.equals("create_t"))
            {
                int size = Integer.parseInt(argToken);
                result.add(new InstrCreateT(size));
            }
            else if (instrToken.equals("do"))
            {
                break;
            }
            else if (instrToken.equals("div"))
            {
                result.add(new InstrDiv());
            }
            else if (instrToken.equals("dup"))
            {
                result.add(new InstrDup());
            }
            else if (instrToken.equals("else"))
            {
                break;
            }
            else if (instrToken.equals("elseif"))
            {
                break;
            }
            else if (instrToken.equals("eq"))
            {
                result.add(new InstrEqual());
            }
            else if (instrToken.equals("error"))
            {
                result.add(new InstrError());
            }
            else if (instrToken.equals("frame"))
            {
//                int nLocals = Integer.parseInt(argToken);
//                result.add(new InstrFrame(nLocals));
            }
            else if (instrToken.equals("function"))
            {
                String[] args = argToken.split(" ");
                
                String funcName = args[0];
                int argCount = Integer.parseInt(args[1]);
                
                InstrLabel label = getNamedLabel(funcName);
                _functionArgCount.put(label, argCount);
                
                result.add(label);
            }
            else if (instrToken.equals("gt"))
            {
                result.add(new InstrGreaterThen());
            }
            else if (instrToken.equals("gte"))
            {
                result.add(new InstrGreaterThenEqual());
            }
            else if (instrToken.equals("if"))
            {
                InstrLabel lcont = new InstrLabel("continue");
                
                for (;;)
                {
                    InstrLabel lelse = new InstrLabel("else");
                    
                    MislInstructionList ifCond = parseInstructions(lines);
                    result.add(ifCond);
                    result.add(new InstrJumpIfNot(lelse));
                    
                    MislInstructionList thenBlock = parseInstructions(lines);
                    result.add(thenBlock);
                    result.add(new InstrJump(lcont, false, false));
                    
                    result.add(lelse);
                    
                    if (!_lastInstr.equals("elseif"))
                    {
                        break;
                    }
                }
                
                MislInstructionList elseBlock = parseInstructions(lines);
                result.add(elseBlock);
                result.add(lcont);
            }
            else if (instrToken.equals("jump"))
            {
                InstrLabel target = getNamedLabel(argToken);
                result.add(new InstrJump(target, false, true));
            }
            else if (instrToken.equals("label"))
            {
                InstrLabel label = getNamedLabel(argToken);
                result.add(label);
            }
            else if (instrToken.equals("line"))
            {
                int lineNum = Integer.parseInt(argToken);
                result.add(new InstrLine(lineNum));
            }
            else if (instrToken.equals("load"))
            {
                String varName = parseVariableName(argToken);
                result.add(new InstrLoad(varName));
            }
//            else if (instrToken.equals("load_l"))
//            {
//                int index = Integer.valueOf(argToken);
//                result.add(new InstrLoadL(index));
//            }
            else if (instrToken.equals("load_t"))
            {
                result.add(new InstrLoadT());
            }
            else if (instrToken.equals("lt"))
            {
                result.add(new InstrLessThen());
            }
            else if (instrToken.equals("lte"))
            {
                result.add(new InstrLessThenEqual());
            }
            else if (instrToken.equals("mod"))
            {
                result.add(new InstrMod());
            }
            else if (instrToken.equals("mul"))
            {
                result.add(new InstrMul());
            }
            else if (instrToken.equals("neg"))
            {
                result.add(new InstrNeg());
            }
            else if (instrToken.equals("not"))
            {
                result.add(new InstrNot());
            }
            else if (instrToken.equals("neq"))
            {
                result.add(new InstrNotEqual());
            }
            else if (instrToken.equals("or"))
            {
                MislInstruction lcont = new InstrLabel("continue");
                
                result.add(new InstrOr(lcont));
                
                MislInstructionList b = parseInstructions(lines);
                result.add(b);
                
                result.add(lcont);
            }
            else if (instrToken.equals("pop"))
            {
                result.add(new InstrPop());
            }
            else if (instrToken.equals("push"))
            {
                MislValue constant = parseConstant(argToken);
                result.add(new InstrPush(constant));
            }
            else if (instrToken.equals("return"))
            {
                result.add(new InstrReturn());
            }
            else if (instrToken.equals("store"))
            {
                String varName = parseVariableName(argToken);
                result.add(new InstrStore(varName));
            }
            else if (instrToken.equals("store_c"))
            {
                String[] argsTokens = argToken.split(" ", 2);
                
                String varName = parseVariableName(argsTokens[0]);
                MislValue constant = parseConstant(argsTokens[1]);
                
                result.add(new InstrStoreC(varName, constant));
            }
            else if (instrToken.equals("store_l"))
            {
                result.add(new InstrStoreL(argToken));
            }
            else if (instrToken.equals("store_t"))
            {
                result.add(new InstrStoreT());
            }
            else if (instrToken.equals("sub"))
            {
                result.add(new InstrSub());
            }
            else if (instrToken.equals("then"))
            {
                break;
            }
            else if (instrToken.equals("while"))
            {
                InstrLabel lwhile = new InstrLabel("while");
                InstrLabel ldo = new InstrLabel("do");
                InstrLabel lcont = new InstrLabel("continue");
                
                result.add(lwhile);
                
                MislInstructionList whileBlock = parseInstructions(lines);
                result.add(whileBlock);
                result.add(new InstrJumpIfNot(lcont));
                
                result.add(ldo);
                
                MislInstructionList doBlock = parseInstructions(lines);
                result.add(doBlock);
                result.add(new InstrJump(lwhile, false, false));
                
                result.add(lcont);
            }
            else
            {
                throw new ScriptParserException("Unknown instruction: %s", line);
            }
            
            // System.out.println("Parsed Instruction: " + result.getLast() );
        }
        
        return result;
    }
    
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
    
    // ========================================
    
    private IBindings createBindings()
    {
        IBindings bindings = new SimpleBindings();
        
        for (InstrLabel label : _namedLabels.values())
        {
            int argCount;
            if (_functionArgCount.containsKey(label))
            {
                argCount = _functionArgCount.get(label);
            }
            else
            {
                argCount = 0;
            }
            
            bindings.set(label.getName(), new MislFunction(label, argCount));
        }
        
        return bindings;
    }
    
    private static List<String> cleanup(List<String> strings)
    {
        List<String> result = Lists.newArrayListWithCapacity(strings.size());
        
        int size = strings.size();
        for (int i = 0; i < size; i++)
        {
            String str = strings.get(i);
            result.add(StringUtils.trimToEmpty(str));
        }
        
        return result;
    }
    
    /**
     * Calls {@link #parseInstructions(Queue)} until all lines are consumed and returns each named label.
     * 
     * @param lines
     * @return
     */
    public static IBindings parseAll(List<String> lines)
    {
        MislParser parser = new MislParser();
        
        Queue<String> parseQueue = Queue.newQueue(cleanup(lines));
        
        do
        {
            parser.parseInstructions(parseQueue);
        }
        while (!parseQueue.isEmpty());
        
        return parser.createBindings();
    }
    
    public static IBindings parseString(String str)
    {
        List<String> readLines = Arrays.asList(str.split("\n"));
        
        Queue<String> lines = Queue.newQueue(readLines);
        MislParser parser = new MislParser();
        
        do
        {
            parser.parseInstructions(lines);
        }
        while (!lines.isEmpty());
        
        return parser.createBindings();
    }
    
    private static final Charset CHARSET = Charset.forName("UTF-8");
    
    /**
     * Parses a MISL script file and creates a function for each parsed label.
     * 
     * @param path The path of the script file.
     * @return Bindings that contain all parsed labels as functions.
     * @throws IOException If an exception occurs while reading the script file.
     */
    public static IBindings parseFile(Path path) throws IOException
    {
        
        List<String> readLines = Files.readAllLines(path, CHARSET);
        readLines = cleanup(readLines);
        
        Queue<String> lines = Queue.newQueue(readLines);
        MislParser parser = new MislParser();
        
        do
        {
            parser.parseInstructions(lines);
        }
        while (!lines.isEmpty());
        
        return parser.createBindings();
    }
}
