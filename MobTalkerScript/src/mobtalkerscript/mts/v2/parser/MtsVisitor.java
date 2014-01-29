// Generated from C:\Users\Tobsen\Desktop\Minecraft\MobTalker2\MobTalkerScript\MobTalkerScript\src\mobtalkerscript\mts\v2\parser\Mts.g4 by ANTLR 4.x
package mobtalkerscript.mts.v2.parser;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link MtsParser}.
 *
 * @param <Result> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface MtsVisitor<Result> extends ParseTreeVisitor<Result> {
	/**
	 * Visit a parse tree produced by {@link MtsParser#chunk}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	Result visitChunk(@NotNull MtsParser.ChunkContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#ExprCallExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	Result visitExprCallExpr(@NotNull MtsParser.ExprCallExprContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#paramList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	Result visitParamList(@NotNull MtsParser.ParamListContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#returnStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	Result visitReturnStmt(@NotNull MtsParser.ReturnStmtContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#elseBody}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	Result visitElseBody(@NotNull MtsParser.ElseBodyContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#SayCommandStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	Result visitSayCommandStmt(@NotNull MtsParser.SayCommandStmtContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#funcExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	Result visitFuncExpr(@NotNull MtsParser.FuncExprContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#AssignmentStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	Result visitAssignmentStmt(@NotNull MtsParser.AssignmentStmtContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#NameAccessExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	Result visitNameAccessExpr(@NotNull MtsParser.NameAccessExprContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#CommandStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	Result visitCommandStmt(@NotNull MtsParser.CommandStmtContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#BinaryOpExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	Result visitBinaryOpExpr(@NotNull MtsParser.BinaryOpExprContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#PostfixOpExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	Result visitPostfixOpExpr(@NotNull MtsParser.PostfixOpExprContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#CallStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	Result visitCallStmt(@NotNull MtsParser.CallStmtContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#ExprAccessExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	Result visitExprAccessExpr(@NotNull MtsParser.ExprAccessExprContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#BlankStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	Result visitBlankStmt(@NotNull MtsParser.BlankStmtContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#PauseCommandStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	Result visitPauseCommandStmt(@NotNull MtsParser.PauseCommandStmtContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#PrefixOpExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	Result visitPrefixOpExpr(@NotNull MtsParser.PrefixOpExprContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#FunctionCall}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	Result visitFunctionCall(@NotNull MtsParser.FunctionCallContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#NameFieldAccess}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	Result visitNameFieldAccess(@NotNull MtsParser.NameFieldAccessContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#ShowCommandStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	Result visitShowCommandStmt(@NotNull MtsParser.ShowCommandStmtContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#IfThenElse}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	Result visitIfThenElse(@NotNull MtsParser.IfThenElseContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#StopMusicCommandStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	Result visitStopMusicCommandStmt(@NotNull MtsParser.StopMusicCommandStmtContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#Return}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	Result visitReturn(@NotNull MtsParser.ReturnContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#AnonymousFunctionDeclr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	Result visitAnonymousFunctionDeclr(@NotNull MtsParser.AnonymousFunctionDeclrContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#SimpleAssignmentStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	Result visitSimpleAssignmentStmt(@NotNull MtsParser.SimpleAssignmentStmtContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#LocalFunctionDeclr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	Result visitLocalFunctionDeclr(@NotNull MtsParser.LocalFunctionDeclrContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#nameList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	Result visitNameList(@NotNull MtsParser.NameListContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#NamePrefixedAssignmentTarget}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	Result visitNamePrefixedAssignmentTarget(@NotNull MtsParser.NamePrefixedAssignmentTargetContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#ExprFieldAccess}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	Result visitExprFieldAccess(@NotNull MtsParser.ExprFieldAccessContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#elseIfBody}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	Result visitElseIfBody(@NotNull MtsParser.ElseIfBodyContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#SimpleAssignmentTarget}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	Result visitSimpleAssignmentTarget(@NotNull MtsParser.SimpleAssignmentTargetContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#DoBlock}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	Result visitDoBlock(@NotNull MtsParser.DoBlockContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#block}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	Result visitBlock(@NotNull MtsParser.BlockContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#tableCtor}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	Result visitTableCtor(@NotNull MtsParser.TableCtorContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#OperatorAssignmentStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	Result visitOperatorAssignmentStmt(@NotNull MtsParser.OperatorAssignmentStmtContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#LogicalOpExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	Result visitLogicalOpExpr(@NotNull MtsParser.LogicalOpExprContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#MethodCall}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	Result visitMethodCall(@NotNull MtsParser.MethodCallContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#breakStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	Result visitBreakStmt(@NotNull MtsParser.BreakStmtContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#appendExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	Result visitAppendExpr(@NotNull MtsParser.AppendExprContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#ExprKeyField}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	Result visitExprKeyField(@NotNull MtsParser.ExprKeyFieldContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#RepeatLoop}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	Result visitRepeatLoop(@NotNull MtsParser.RepeatLoopContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#LiteralExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	Result visitLiteralExpr(@NotNull MtsParser.LiteralExprContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#NameCallExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	Result visitNameCallExpr(@NotNull MtsParser.NameCallExprContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#LocalVariableDeclr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	Result visitLocalVariableDeclr(@NotNull MtsParser.LocalVariableDeclrContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#NumericForLoop}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	Result visitNumericForLoop(@NotNull MtsParser.NumericForLoopContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#NameKeyField}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	Result visitNameKeyField(@NotNull MtsParser.NameKeyFieldContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#exprList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	Result visitExprList(@NotNull MtsParser.ExprListContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#WhileLoop}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	Result visitWhileLoop(@NotNull MtsParser.WhileLoopContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#ExprPrefixedAssignmentTarget}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	Result visitExprPrefixedAssignmentTarget(@NotNull MtsParser.ExprPrefixedAssignmentTargetContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#fieldList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	Result visitFieldList(@NotNull MtsParser.FieldListContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#CallCommandStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	Result visitCallCommandStmt(@NotNull MtsParser.CallCommandStmtContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#GenericForLoop}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	Result visitGenericForLoop(@NotNull MtsParser.GenericForLoopContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#GlobalFunctionDeclr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	Result visitGlobalFunctionDeclr(@NotNull MtsParser.GlobalFunctionDeclrContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#funcName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	Result visitFuncName(@NotNull MtsParser.FuncNameContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#Break}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	Result visitBreak(@NotNull MtsParser.BreakContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#genericForControl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	Result visitGenericForControl(@NotNull MtsParser.GenericForControlContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#numericForControl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	Result visitNumericForControl(@NotNull MtsParser.NumericForControlContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#MenuCommandStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	Result visitMenuCommandStmt(@NotNull MtsParser.MenuCommandStmtContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#PlayMusicCommandStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	Result visitPlayMusicCommandStmt(@NotNull MtsParser.PlayMusicCommandStmtContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#JumpCommandStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	Result visitJumpCommandStmt(@NotNull MtsParser.JumpCommandStmtContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#ListField}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	Result visitListField(@NotNull MtsParser.ListFieldContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#HideCommandStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	Result visitHideCommandStmt(@NotNull MtsParser.HideCommandStmtContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#SimpleExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	Result visitSimpleExpr(@NotNull MtsParser.SimpleExprContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#PlaySoundCommandStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	Result visitPlaySoundCommandStmt(@NotNull MtsParser.PlaySoundCommandStmtContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#Label}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	Result visitLabel(@NotNull MtsParser.LabelContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#SceneCommandStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	Result visitSceneCommandStmt(@NotNull MtsParser.SceneCommandStmtContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#funcBody}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	Result visitFuncBody(@NotNull MtsParser.FuncBodyContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#assignmentTargetList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	Result visitAssignmentTargetList(@NotNull MtsParser.AssignmentTargetListContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	Result visitLiteral(@NotNull MtsParser.LiteralContext ctx);
}