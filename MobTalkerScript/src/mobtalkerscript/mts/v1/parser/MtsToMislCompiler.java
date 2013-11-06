package mobtalkerscript.mts.v1.parser;

import java.util.*;

import mobtalkerscript.*;
import mobtalkerscript.misl.v1.*;
import mobtalkerscript.misl.v1.instruction.*;
import mobtalkerscript.misl.v1.value.*;
import mobtalkerscript.mts.v1.parser.MtsParser.BinaryExprContext;
import mobtalkerscript.mts.v1.parser.MtsParser.BlockContext;
import mobtalkerscript.mts.v1.parser.MtsParser.BooleanLiteralContext;
import mobtalkerscript.mts.v1.parser.MtsParser.CallContext;
import mobtalkerscript.mts.v1.parser.MtsParser.CommandHideContext;
import mobtalkerscript.mts.v1.parser.MtsParser.CommandMenuContext;
import mobtalkerscript.mts.v1.parser.MtsParser.CommandMenuOptionContext;
import mobtalkerscript.mts.v1.parser.MtsParser.CommandSayContext;
import mobtalkerscript.mts.v1.parser.MtsParser.CommandSceneContext;
import mobtalkerscript.mts.v1.parser.MtsParser.CommandShowContext;
import mobtalkerscript.mts.v1.parser.MtsParser.ExprContext;
import mobtalkerscript.mts.v1.parser.MtsParser.FieldDefExprContext;
import mobtalkerscript.mts.v1.parser.MtsParser.FuncDeclContext;
import mobtalkerscript.mts.v1.parser.MtsParser.FunctionCallContext;
import mobtalkerscript.mts.v1.parser.MtsParser.IfElseBlockContext;
import mobtalkerscript.mts.v1.parser.MtsParser.IndexedFieldContext;
import mobtalkerscript.mts.v1.parser.MtsParser.JumpContext;
import mobtalkerscript.mts.v1.parser.MtsParser.LabelDeclContext;
import mobtalkerscript.mts.v1.parser.MtsParser.LocalVariableAssignmentContext;
import mobtalkerscript.mts.v1.parser.MtsParser.LogicalExprContext;
import mobtalkerscript.mts.v1.parser.MtsParser.NamedFieldContext;
import mobtalkerscript.mts.v1.parser.MtsParser.NullLiteralContext;
import mobtalkerscript.mts.v1.parser.MtsParser.NumberLiteralContext;
import mobtalkerscript.mts.v1.parser.MtsParser.NumericForContext;
import mobtalkerscript.mts.v1.parser.MtsParser.RepeatBlockContext;
import mobtalkerscript.mts.v1.parser.MtsParser.ReturnStmtContext;
import mobtalkerscript.mts.v1.parser.MtsParser.StringLiteralContext;
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
import org.antlr.v4.runtime.tree.*;

import com.google.common.collect.*;

public class MtsToMislCompiler extends MtsBaseVisitor<Void>
{
    private final ArrayList<MislInstruction> _instructionList;
    private final MislInstructionList _instructions;
    private final IBindings _bindings;
    
    // ========================================
    
    public MtsToMislCompiler(MtsParser parser)
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
    
    @Override
    public Void visit(ParseTree tree)
    {
        if (tree == null)
        {
            return null;
        }
        
        if (tree instanceof ParserRuleContext)
        {
            checkLineNumber((ParserRuleContext) tree);
        }
        
        super.visit(tree);
        
        return null;
    }
    
    // ========================================
    // Label
    
    private Set<String> _locals = Sets.newHashSet();
    
    @Override
    public Void visitLabelDecl(LabelDeclContext ctx)
    {
        String funcName = ctx.Identifier.getText();
        InstrLabel label = new InstrLabel(funcName);
        
        addInstr(label);
        _bindings.set(funcName, new MislFunction(label, 0));
        
        _locals.clear();
        
        visit(ctx.LabelBlock);
        
        _locals.clear();
        
        addInstr(new InstrReturn());
        
        return null;
    }
    
    @Override
    public Void visitFuncDecl(FuncDeclContext ctx)
    {
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
            
            addInstr(new InstrStoreL(argName));
        }
        
        visit(ctx.FunctionBlock);
        
        _locals.clear();
        
        addInstr(new InstrReturn());
        
        return null;
    }
    
    @Override
    public Void visitReturnStmt(ReturnStmtContext ctx)
    {
        visit(ctx.ReturnExpr);
        
        addInstr(new InstrReturn());
        
        return null;
    }
    
    @Override
    public Void visitJump(JumpContext ctx)
    {
        String labelName = ctx.Target.getText();
        
        addInstr(new InstrLoad(labelName));
        addInstr(new InstrJumpF());
        
        return null;
    }
    
    @Override
    public Void visitCall(CallContext ctx)
    {
        String funcName = ctx.Identifier.getText();
        
        addInstr(new InstrLoad(funcName));
        addInstr(new InstrCall(0, 0));
        
        return null;
    }
    
    // ========================================
    // Literal
    
    @Override
    public Void visitStringLiteral(StringLiteralContext ctx)
    {
        String literal = ctx.Literal.getText();
        addInstr(new InstrPush(literal.substring(1, literal.length() - 1)));
        
        return null;
    }
    
    @Override
    public Void visitNumberLiteral(NumberLiteralContext ctx)
    {
        int literal = Integer.parseInt(ctx.Literal.getText());
        addInstr(new InstrPush(literal));
        
        return null;
    }
    
    @Override
    public Void visitBooleanLiteral(BooleanLiteralContext ctx)
    {
        boolean literal = Boolean.parseBoolean(ctx.Literal.getText());
        addInstr(new InstrPush(literal));
        
        return null;
    }
    
    @Override
    public Void visitNullLiteral(NullLiteralContext ctx)
    {
        addInstr(new InstrPush(MislValue.NIL));
        
        return null;
    }
    
    // ========================================
    // Operators
    
    @Override
    public Void visitBinaryExpr(BinaryExprContext ctx)
    {
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
        visit(ctx.Right);
        
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
        
        return null;
    }
    
    @Override
    public Void visitLogicalExpr(LogicalExprContext ctx)
    {
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
    public Void visitVariableAssignment(VariableAssignmentContext ctx)
    {
        visit(ctx.VariableExpr);
        
        String varName = ctx.VariableName.Identifier.getText();
        
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
        visit(ctx.expr());
        
        String varName = ctx.variableExpr().Identifier.getText();
        
        addInstr(new InstrStoreL(varName));
        
        _locals.add(varName);
        
        return null;
    }
    
    @Override
    public Void visitVariableAccess(VariableAccessContext ctx)
    {
        String varName = ctx.variableExpr().Identifier.getText();
        
        addInstr(new InstrLoad(varName));
        
        return null;
    }
    
    // ========================================
    // Calls
    
    @Override
    public Void visitFunctionCall(FunctionCallContext ctx)
    {
        visitChildren(ctx.FunctionArgs);
        
        boolean shouldReturnValue = ctx.getParent() instanceof ExprContext;
        List<ExprContext> exprListCtx = ctx.FunctionArgs.ArgumentExprs;
        
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
        visitChildren(ctx.FunctionArgs);
        
        boolean shouldReturnValue = ctx.getParent() instanceof ExprContext;
        List<ExprContext> exprListCtx = ctx.FunctionArgs.ArgumentExprs;
        
        int argCount = exprListCtx.size();
        int retCount = shouldReturnValue ? 1 : 0;
        
        visit(ctx.TableExpr);
        addInstr(new InstrCall(argCount, retCount));
        
        return null;
    }
    
    // ========================================
    // Tables
    
    @Override
    public Void visitTableCtorExpr(TableCtorExprContext ctx)
    {
        int i = 1;
        for (FieldDefExprContext fieldDef : ctx.FieldExprs)
        {
            if (fieldDef instanceof IndexedFieldContext)
            {
                addInstr(new InstrPush(i));
                i++;
            }
            
            visit(fieldDef);
        }
        
        int tSize = ctx.FieldExprs.size();
        
        addInstr(new InstrCreateT(tSize));
        
        return null;
    }
    
    @Override
    public Void visitNamedField(NamedFieldContext ctx)
    {
        String key = ctx.variableExpr().Identifier.getText();
        
        addInstr(new InstrPush(key));
        visit(ctx.expr());
        
        return null;
    }
    
    @Override
    public Void visitTableExpr(TableExprContext ctx)
    {
        String parentVar = ctx.ParentTableExpr.Identifier.getText();
        
        addInstr(new InstrLoad(parentVar));
        
        for (TableFieldAccessContext fieldCtx : ctx.FieldExprs)
        {
            visit(fieldCtx);
            
            addInstr(new InstrLoadT());
        }
        
        return null;
    }
    
    @Override
    public Void visitTableAssignment(TableAssignmentContext parentCtx)
    {
        TableExprContext ctx = parentCtx.tableExpr();
        
        String parentVar = ctx.variableExpr().Identifier.getText();
        
        addInstr(new InstrLoad(parentVar));
        
        // Visit all field access nodes except the last.
        int limit = ctx.FieldExprs.size() - 1;
        for (int i = 0; i <= limit; i++)
        {
            TableFieldAccessContext fieldCtx = ctx.FieldExprs.get(i);
            
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
        addInstr(new InstrPushScope());
        
        InstrLabel loop = new InstrLabel("while");
        addInstr(loop);
        
        visit(ctx.Condition);
        
        InstrLabel cont = new InstrLabel("continue");
        addInstr(new InstrJumpIfNot(cont));
        
        visit(ctx.LoopBlock);
        
        addInstr(new InstrJump(loop, false, false));
        addInstr(cont);
        
        addInstr(new InstrPopScope());
        
        return null;
    }
    
    @Override
    public Void visitRepeatBlock(RepeatBlockContext ctx)
    {
        addInstr(new InstrPushScope());
        
        InstrLabel loop = new InstrLabel("repeat");
        addInstr(loop);
        
        visit(ctx.LoopBlock);
        visit(ctx.Condition);
        
        addInstr(new InstrNot());
        addInstr(new InstrJumpIfNot(loop));
        
        addInstr(new InstrPopScope());
        
        return null;
    }
    
    @Override
    public Void visitNumericFor(NumericForContext ctx)
    {
        addInstr(new InstrPushScope());
        
        visit(ctx.Initializer.expr());
        String loopVarName = ctx.Initializer.variableExpr().getText();
        addInstr(new InstrStoreL(loopVarName));
        
        InstrLabel loop = new InstrLabel("for");
        addInstr(loop);
        
        visit(ctx.Condition);
        
        InstrLabel cont = new InstrLabel("continue");
        addInstr(new InstrJumpIfNot(cont));
        
        visit(ctx.block());
        
        if (ctx.Step != null)
        {
            visit(ctx.Step);
        }
        else
        {
            addInstr(new InstrIncr(loopVarName));
        }
        
        addInstr(new InstrJump(loop, false, false));
        addInstr(cont);
        
        addInstr(new InstrPopScope());
        
        return null;
    }
    
    // ========================================
    // Commands
    
    @Override
    public Void visitCommandSay(CommandSayContext ctx)
    {
        visit(ctx.Character);
        visit(ctx.Text);
        
        int argCount = ctx.Character == null ? 1 : 2;
        
        addInstr(new InstrLoad("DisplayText"));
        addInstr(new InstrCall(argCount, 0));
        addInstr(new InstrAwait());
        
        return null;
    }
    
    @Override
    public Void visitCommandShow(CommandShowContext ctx)
    {
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
        
        for (CommandMenuOptionContext optionCtx : ctx.Options)
        {
            optionExprs.add(optionCtx.OptionTextExpr);
            optionBlocks.add(optionCtx.OptionBlock);
        }
        
        for (ExprContext optionExpr : optionExprs)
        {
            visit(optionExpr);
        }
        
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
}
