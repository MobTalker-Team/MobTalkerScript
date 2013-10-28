package mobtalkerscript.mts.v1.parser;

import java.util.*;
import java.util.regex.*;

import mobtalkerscript.*;
import mobtalkerscript.misl.v1.*;
import mobtalkerscript.misl.v1.instruction.*;
import mobtalkerscript.misl.v1.value.*;
import mobtalkerscript.mts.v1.parser.MtsParser.BinaryExprContext;
import mobtalkerscript.mts.v1.parser.MtsParser.BlockContext;
import mobtalkerscript.mts.v1.parser.MtsParser.CallContext;
import mobtalkerscript.mts.v1.parser.MtsParser.CommandHideContext;
import mobtalkerscript.mts.v1.parser.MtsParser.CommandMenuContext;
import mobtalkerscript.mts.v1.parser.MtsParser.CommandMenuOptionContext;
import mobtalkerscript.mts.v1.parser.MtsParser.CommandSayContext;
import mobtalkerscript.mts.v1.parser.MtsParser.CommandSceneContext;
import mobtalkerscript.mts.v1.parser.MtsParser.CommandShowContext;
import mobtalkerscript.mts.v1.parser.MtsParser.ConstantAssignmentContext;
import mobtalkerscript.mts.v1.parser.MtsParser.ExprContext;
import mobtalkerscript.mts.v1.parser.MtsParser.FieldDefExprContext;
import mobtalkerscript.mts.v1.parser.MtsParser.FuncDeclContext;
import mobtalkerscript.mts.v1.parser.MtsParser.FunctionCallContext;
import mobtalkerscript.mts.v1.parser.MtsParser.IfElseBlockContext;
import mobtalkerscript.mts.v1.parser.MtsParser.IndexedFieldContext;
import mobtalkerscript.mts.v1.parser.MtsParser.JumpContext;
import mobtalkerscript.mts.v1.parser.MtsParser.LabelDeclContext;
import mobtalkerscript.mts.v1.parser.MtsParser.LiteralExprContext;
import mobtalkerscript.mts.v1.parser.MtsParser.LocalVariableAssignmentContext;
import mobtalkerscript.mts.v1.parser.MtsParser.LogicalExprContext;
import mobtalkerscript.mts.v1.parser.MtsParser.NamedFieldContext;
import mobtalkerscript.mts.v1.parser.MtsParser.RepeatBlockContext;
import mobtalkerscript.mts.v1.parser.MtsParser.ReturnStmtContext;
import mobtalkerscript.mts.v1.parser.MtsParser.TableAccessContext;
import mobtalkerscript.mts.v1.parser.MtsParser.TableAssignmentContext;
import mobtalkerscript.mts.v1.parser.MtsParser.TableCallContext;
import mobtalkerscript.mts.v1.parser.MtsParser.TableCtorExprContext;
import mobtalkerscript.mts.v1.parser.MtsParser.TableExprContext;
import mobtalkerscript.mts.v1.parser.MtsParser.TableFieldAccessContext;
import mobtalkerscript.mts.v1.parser.MtsParser.UnaryExprContext;
import mobtalkerscript.mts.v1.parser.MtsParser.VariableAccessContext;
import mobtalkerscript.mts.v1.parser.MtsParser.VariableAssignmentContext;
import mobtalkerscript.mts.v1.parser.MtsParser.VariableExprContext;
import mobtalkerscript.mts.v1.parser.MtsParser.WhileBlockContext;
import mobtalkerscript.util.logging.*;

import org.antlr.v4.runtime.*;

import com.google.common.collect.*;

public class MtsToMislDirectTranslater extends MtsBaseVisitor<Void>
{
    private final ArrayList<MislInstruction> _instructionList;
    private final MislInstructionList _instructions;
    private final IBindings _bindings;
    
    // ========================================
    
    public MtsToMislDirectTranslater(MtsParser parser)
    {
        _instructionList = Lists.newArrayListWithExpectedSize(100);
        _instructions = new MislInstructionList();
        _bindings = new SimpleBindings();
    }
    
    // ========================================
    
    public MislInstructionList getInstructions()
    {
        return _instructions;
    }
    
    public List<MislInstruction> getInstructionsAsList()
    {
        return _instructionList;
    }
    
    public IBindings getBindings()
    {
        return _bindings;
    }
    
    // ========================================
    
    private void addInstr(MislInstruction instr)
    {
        _instructionList.add(instr);
        _instructions.add(instr);
        
        MTSLog.info("Added instr %s", instr.toString());
    }
    
    // ========================================
    
    private int _curLine;
    
    private void checkLineNumber(ParserRuleContext ctx)
    {
        checkLineNumber(ctx.start);
    }
    
    private void checkLineNumber(Token token)
    {
        int line = token.getLine();
        
        if (_curLine == line)
        {
            return;
        }
        
        addInstr(new InstrLine(_curLine));
        
        _curLine = line;
    }
    
    // ========================================
    
    @Override
    public Void visitBlock(BlockContext ctx)
    {
        visitChildren(ctx);
        
        return null;
    }
    
    // ========================================
    // Label
    
    private Set<String> _locals = Sets.newHashSet();
    
    @Override
    public Void visitLabelDecl(LabelDeclContext ctx)
    {
        checkLineNumber(ctx);
        
        String funcName = ctx.Identifier.getText();
        InstrLabel label = new InstrLabel(funcName);
        
        addInstr(label);
        _bindings.set(funcName, new MislFunction(label, 0));
        
        _locals.clear();
        
        visitChildren(ctx);
        
        _locals.clear();
        
        addInstr(new InstrReturn());
        
        return null;
    }
    
    @Override
    public Void visitFuncDecl(FuncDeclContext ctx)
    {
        checkLineNumber(ctx);
        
        String funcName = ctx.Identifier.getText();
        int argCount = ctx.Params.size();
        
        InstrLabel label = new InstrLabel(funcName);
        
        addInstr(label);
        _bindings.set(funcName, new MislFunction(label, argCount));
        
        _locals.clear();
        
        for (VariableExprContext paramCtx : ctx.Params)
        {
            String argName = paramCtx.Identifier.getText();
            
            _locals.add(argName);
            
            checkLineNumber(paramCtx);
            
            addInstr(new InstrStoreL(argName));
        }
        
        visit(ctx.block());
        
        _locals.clear();
        
        addInstr(new InstrReturn());
        
        return null;
    }
    
    @Override
    public Void visitReturnStmt(ReturnStmtContext ctx)
    {
        visitChildren(ctx);
        
        checkLineNumber(ctx);
        
        addInstr(new InstrReturn());
        
        return null;
    }
    
    @Override
    public Void visitJump(JumpContext ctx)
    {
        checkLineNumber(ctx);
        
        String labelName = ctx.Target.getText();
        
        addInstr(new InstrLoad(labelName));
        addInstr(new InstrJumpF());
        
        return null;
    }
    
    @Override
    public Void visitCall(CallContext ctx)
    {
        checkLineNumber(ctx);
        
        String funcName = ctx.Identifier.getText();
        
        addInstr(new InstrLoad(funcName));
        addInstr(new InstrCall(0, 0));
        
        return null;
    }
    
    // ========================================
    // Literal
    
    @Override
    public Void visitLiteralExpr(LiteralExprContext ctx)
    {
        checkLineNumber(ctx);
        
        String constantStr = ctx.Literal != null ? ctx.Literal.getText() : "nil";
        MislValue constant = parseConstant(constantStr);
        
        addInstr(new InstrPush(constant));
        
        return null;
    }
    
    // ========================================
    // Operators
    
    @Override
    public Void visitBinaryExpr(BinaryExprContext ctx)
    {
        checkLineNumber(ctx);
        
        String op = ctx.Operator.getText();
        
        if ("+".equals(op))
        {
            visit(ctx.Left);
            visit(ctx.Right);
            
            addInstr(new InstrAdd());
            
        }
        else if ("-".equals(op))
        {
            visit(ctx.Left);
            visit(ctx.Right);
            
            addInstr(new InstrSub());
        }
        else if ("*".equals(op))
        {
            visit(ctx.Left);
            visit(ctx.Right);
            
            addInstr(new InstrMul());
        }
        else if ("/".equals(op))
        {
            visit(ctx.Left);
            visit(ctx.Right);
            
            addInstr(new InstrDiv());
        }
        else if ("%".equals(op))
        {
            visit(ctx.Left);
            visit(ctx.Right);
            
            addInstr(new InstrMod());
        }
        else if ("<".equals(op))
        {
            visit(ctx.Left);
            visit(ctx.Right);
            
            addInstr(new InstrLessThen());
        }
        else if ("<=".equals(op))
        {
            visit(ctx.Left);
            visit(ctx.Right);
            
            addInstr(new InstrLessThenEqual());
        }
        else if (">".equals(op))
        {
            visit(ctx.Right);
            visit(ctx.Left);
            
            addInstr(new InstrLessThen());
        }
        else if (">=".equals(op))
        {
            visit(ctx.Right);
            visit(ctx.Left);
            
            addInstr(new InstrLessThenEqual());
        }
        else if ("==".equals(op))
        {
            visit(ctx.Left);
            visit(ctx.Right);
            
            addInstr(new InstrEqual());
        }
        else if ("!=".equals(op))
        {
            visit(ctx.Left);
            visit(ctx.Right);
            
            addInstr(new InstrEqual());
            addInstr(new InstrNot());
        }
        else if ("..".equals(op))
        {
            visit(ctx.Left);
            visit(ctx.Right);
            
            addInstr(new InstrConcat());
        }
        else
        {
            throw new ScriptParserException("Unknown binary operator: %s", op);
        }
        
        return null;
    }
    
    @Override
    public Void visitUnaryExpr(UnaryExprContext ctx)
    {
        visitChildren(ctx);
        
        String op = ctx.Operator.getText();
        
        if ("not".equals(op))
        {
            addInstr(new InstrNot());
        }
        else if ("-".equals(op))
        {
            addInstr(new InstrNeg());
        }
        else
        {
            throw new ScriptParserException("Unknown binary operator: %s", op);
        }
        
        checkLineNumber(ctx);
        
        return null;
    }
    
    @Override
    public Void visitLogicalExpr(LogicalExprContext ctx)
    {
        checkLineNumber(ctx);
        
        InstrLabel cont = new InstrLabel("continue");
        
        String op = ctx.Operator.getText();
        
        visit(ctx.Left);
        
        if ("and".equals(op))
        {
            addInstr(new InstrAnd(cont));
        }
        else if ("or".equals(op))
        {
            addInstr(new InstrOr(cont));
        }
        
        visit(ctx.Right);
        
        addInstr(cont);
        
        return null;
    }
    
    // ========================================
    // Variables
    
    @Override
    public Void visitConstantAssignment(ConstantAssignmentContext ctx)
    {
        checkLineNumber(ctx);
        
        String constantStr = ctx.literalExpr().getText();
        MislValue constant = parseConstant(constantStr);
        
        String varName = ctx.variableExpr().Identifier.getText();
        
        addInstr(new InstrStoreC(varName, constant));
        
        return null;
    }
    
    @Override
    public Void visitVariableAssignment(VariableAssignmentContext ctx)
    {
        visitChildren(ctx);
        
        checkLineNumber(ctx);
        
        String varName = ctx.variableExpr().Identifier.getText();
        
        if (_locals.contains(varName))
        {
            addInstr(new InstrStoreL(varName));
        }
        else
        {
            addInstr(new InstrStore(varName));
        }
        
        return null;
    }
    
    @Override
    public Void visitLocalVariableAssignment(LocalVariableAssignmentContext ctx)
    {
        visitChildren(ctx);
        
        checkLineNumber(ctx);
        
        String varName = ctx.variableExpr().Identifier.getText();
        
        addInstr(new InstrStoreL(varName));
        
        _locals.add(varName);
        
        return null;
    }
    
    @Override
    public Void visitVariableAccess(VariableAccessContext ctx)
    {
        checkLineNumber(ctx);
        
        String varName = ctx.variableExpr().Identifier.getText();
        
        addInstr(new InstrLoad(varName));
        
        return null;
    }
    
    // ========================================
    // Calls
    
    @Override
    public Void visitFunctionCall(FunctionCallContext ctx)
    {
        checkLineNumber(ctx);
        
        visitChildren(ctx.funcArgs());
        
        boolean shouldReturnValue = ctx.getParent() instanceof ExprContext;
        List<ExprContext> exprListCtx = ctx.funcArgs().expr();
        
        String funcName = ctx.Identifier.getText();
        int argCount = exprListCtx.size();
        int retCount = shouldReturnValue ? 1 : 0;
        
        addInstr(new InstrLoad(funcName));
        addInstr(new InstrCall(argCount, retCount));
        
        return null;
    }
    
    @Override
    public Void visitTableCall(TableCallContext ctx)
    {
        checkLineNumber(ctx);
        
        visitChildren(ctx.funcArgs());
        
        boolean shouldReturnValue = ctx.getParent() instanceof ExprContext;
        List<ExprContext> exprListCtx = ctx.funcArgs().expr();
        
        int argCount = exprListCtx.size();
        int retCount = shouldReturnValue ? 1 : 0;
        
        visit(ctx.tableExpr());
        addInstr(new InstrCall(argCount, retCount));
        
        return null;
    }
    
    // ========================================
    // Tables
    
    @Override
    public Void visitTableCtorExpr(TableCtorExprContext ctx)
    {
        checkLineNumber(ctx);
        
        int i = 1;
        for (FieldDefExprContext fieldDef : ctx.fieldDefExpr())
        {
            if (fieldDef instanceof IndexedFieldContext)
            {
                addInstr(new InstrPush(i));
                i++;
            }
            
            visit(fieldDef);
        }
        
        int tSize = ctx.fieldDefExpr().size();
        
        addInstr(new InstrCreateT(tSize));
        
        return null;
    }
    
    @Override
    public Void visitNamedField(NamedFieldContext ctx)
    {
        checkLineNumber(ctx);
        
        String key = ctx.variableExpr().Identifier.getText();
        
        addInstr(new InstrPush(key));
        visit(ctx.expr());
        
        return null;
    }
    
    @Override
    public Void visitTableExpr(TableExprContext ctx)
    {
        checkLineNumber(ctx);
        
        String parentVar = ctx.variableExpr().Identifier.getText();
        
        addInstr(new InstrLoad(parentVar));
        
        for (TableFieldAccessContext fieldCtx : ctx.Fields)
        {
            visit(fieldCtx);
            
            addInstr(new InstrLoadT());
        }
        
        return null;
    }
    
    @Override
    public Void visitTableAccess(TableAccessContext ctx)
    {
        visit(ctx.tableExpr());
        
        return null;
    }
    
    @Override
    public Void visitTableAssignment(TableAssignmentContext parentCtx)
    {
        checkLineNumber(parentCtx);
        
        TableExprContext ctx = parentCtx.tableExpr();
        
        String parentVar = ctx.variableExpr().Identifier.getText();
        
        addInstr(new InstrLoad(parentVar));
        
        // Visit all field access nodes except the last.
        int limit = ctx.Fields.size() - 1;
        for (int i = 0; i <= limit; i++)
        {
            TableFieldAccessContext fieldCtx = ctx.Fields.get(i);
            
            visit(fieldCtx);
            
            if (i < limit)
            {
                addInstr(new InstrLoadT());
            }
        }
        
        visit(parentCtx.expr());
        
        addInstr(new InstrStoreT());
        
        return null;
    }
    
    @Override
    public Void visitTableFieldAccess(TableFieldAccessContext ctx)
    {
        checkLineNumber(ctx);
        
        if (ctx.Key == null)
        {
            visit(ctx.expr());
        }
        else
        {
            String key = ctx.Key.getText();
            
            addInstr(new InstrPush(key));
        }
        
        return null;
    }
    
    // ========================================
    // Blocks
    
    @Override
    public Void visitIfElseBlock(IfElseBlockContext ctx)
    {
        checkLineNumber(ctx);
        
        InstrLabel cont = new InstrLabel("continue");
        
        singleIfElse(ctx, cont);
        
        if (ctx.Condition.size() > 1)
        {
            multiIfElse(ctx, cont);
        }
        
        if (ctx.ElseBlock != null)
        {
            visit(ctx.ElseBlock);
        }
        
        addInstr(cont);
        
        return null;
    }
    
    private void singleIfElse(IfElseBlockContext ctx, InstrLabel cont)
    {
        InstrLabel elze = new InstrLabel("else");
        
        ExprContext ifCond = ctx.Condition.get(0);
        visit(ifCond);
        
        addInstr(new InstrJumpIfNot(elze));
        
        BlockContext thenBlock = ctx.ThenBlock.get(0);
        visit(thenBlock);
        
        addInstr(new InstrJump(cont, false, false));
        addInstr(elze);
    }
    
    private void multiIfElse(IfElseBlockContext ctx, InstrLabel cont)
    {
        for (int i = 1; i < ctx.Condition.size(); i++)
        {
            InstrLabel elze = new InstrLabel("elseif");
            
            ExprContext ifCond = ctx.Condition.get(i);
            visit(ifCond);
            
            addInstr(new InstrJumpIfNot(elze));
            
            BlockContext thenBlock = ctx.ThenBlock.get(i);
            visit(thenBlock);
            
            addInstr(new InstrJump(cont, false, false));
            addInstr(elze);
        }
    }
    
    @Override
    public Void visitWhileBlock(WhileBlockContext ctx)
    {
        checkLineNumber(ctx);
        
        InstrLabel cont = new InstrLabel("continue");
        
        visit(ctx.expr());
        
        addInstr(new InstrJumpIfNot(cont));
        
        visit(ctx.block());
        
        addInstr(cont);
        
        return null;
    }
    
    @Override
    public Void visitRepeatBlock(RepeatBlockContext ctx)
    {
        checkLineNumber(ctx);
        
        visit(ctx.block());
        
        InstrLabel cont = new InstrLabel("continue");
        
        visit(ctx.expr());
        
        addInstr(new InstrJumpIfNot(cont));
        
        visit(ctx.block());
        
        addInstr(cont);
        
        return null;
    }
    
    // ========================================
    // Commands
    
    @Override
    public Void visitCommandSay(CommandSayContext ctx)
    {
        visitChildren(ctx);
        
        checkLineNumber(ctx);
        
        int argCount = ctx.Character == null ? 1 : 2;
        
        addInstr(new InstrLoad("DisplayText"));
        addInstr(new InstrCall(argCount, 0));
        addInstr(new InstrAwait());
        
        return null;
    }
    
    @Override
    public Void visitCommandShow(CommandShowContext ctx)
    {
        checkLineNumber(ctx);
        
        int argCount = 2;
        
        if (ctx.Group != null)
        {
            visit(ctx.Group);
        }
        else
        {
            addInstr(new InstrPush(MislValue.NIL));
        }
        
        visit(ctx.Path);
        
        if (ctx.Pos != null)
        {
            argCount += 1;
            visit(ctx.Pos);
        }
        
        if ((ctx.XOffset != null) && (ctx.YOffset != null))
        {
            argCount += 2;
            visit(ctx.XOffset);
            visit(ctx.YOffset);
        }
        
        addInstr(new InstrLoad("ShowTexture"));
        addInstr(new InstrCall(argCount, 0));
        
        return null;
    }
    
    @Override
    public Void visitCommandScene(CommandSceneContext ctx)
    {
        checkLineNumber(ctx);
        
        int argCount = 2;
        
        if (ctx.Group != null)
        {
            visit(ctx.Group);
        }
        else
        {
            addInstr(new InstrPush(MislValue.NIL));
        }
        
        visit(ctx.Path);
        
        addInstr(new InstrLoad("ShowScene"));
        addInstr(new InstrCall(argCount, 0));
        
        return null;
    }
    
    @Override
    public Void visitCommandHide(CommandHideContext ctx)
    {
        checkLineNumber(ctx);
        
        visit(ctx.Group);
        
        addInstr(new InstrLoad("HideTexture"));
        addInstr(new InstrCall(1, 0));
        
        return null;
    }
    
    @Override
    public Void visitCommandMenu(CommandMenuContext ctx)
    {
        List<ExprContext> optionExprs = Lists.newArrayList();
        List<BlockContext> optionBlocks = Lists.newArrayList();
        
        for (CommandMenuOptionContext optionCtx : ctx.commandMenuOption())
        {
            optionExprs.add(optionCtx.expr());
            optionBlocks.add(optionCtx.block());
        }
        
        for (ExprContext optionExpr : optionExprs)
        {
            visit(optionExpr);
        }
        
        checkLineNumber(ctx);
        
        addInstr(new InstrLoad("DisplayChoice"));
        addInstr(new InstrCall(optionExprs.size(), 0));
        addInstr(new InstrAwait());
        
        InstrLabel cont = new InstrLabel("continue");
        
        for (int i = 0; i < optionBlocks.size(); i++)
        {
            InstrLabel elze = new InstrLabel("else");
            
            addInstr(new InstrDup());
            addInstr(new InstrPush(i));
            addInstr(new InstrEqual());
            addInstr(new InstrJumpIfNot(elze));
            
            addInstr(new InstrPop());
            visit(optionBlocks.get(i));
            addInstr(new InstrJump(cont, false, false));
            
            addInstr(elze);
        }
        
        addInstr(new InstrPop());
        addInstr(new InstrPush("AssertionError"));
        addInstr(new InstrError());
        
        addInstr(cont);
        
        return null;
    }
    
    // ========================================
    
    private static final Pattern _booleanPattern = Pattern.compile("^(true|false)$"); // true, false
    private static final Pattern _numberPattern = Pattern.compile("^([+-]?\\d+(:?\\.\\d+)?)$"); // 0, 0.0
    private static final Pattern _stringPattern = Pattern.compile("^\"(.*?)\"$"); // "abc"
    
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
}
