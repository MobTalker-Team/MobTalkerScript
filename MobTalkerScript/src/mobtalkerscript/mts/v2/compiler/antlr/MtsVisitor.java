// Generated from Mts.g4 by ANTLR 4.1
package mobtalkerscript.mts.v2.compiler.antlr;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link MtsParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface MtsVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link MtsParser#chunk}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitChunk(@NotNull MtsParser.ChunkContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#ExprCallExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprCallExpr(@NotNull MtsParser.ExprCallExprContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#FieldAssignmentTarget}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFieldAssignmentTarget(@NotNull MtsParser.FieldAssignmentTargetContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#paramList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParamList(@NotNull MtsParser.ParamListContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#returnStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturnStmt(@NotNull MtsParser.ReturnStmtContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#elseBody}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitElseBody(@NotNull MtsParser.ElseBodyContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#SayCommandStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSayCommandStmt(@NotNull MtsParser.SayCommandStmtContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#LabelStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLabelStmt(@NotNull MtsParser.LabelStmtContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#funcExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFuncExpr(@NotNull MtsParser.FuncExprContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#AssignmentStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignmentStmt(@NotNull MtsParser.AssignmentStmtContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#NameAccessExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNameAccessExpr(@NotNull MtsParser.NameAccessExprContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#CommandStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCommandStmt(@NotNull MtsParser.CommandStmtContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#BinaryOpExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBinaryOpExpr(@NotNull MtsParser.BinaryOpExprContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#PostfixOpExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPostfixOpExpr(@NotNull MtsParser.PostfixOpExprContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#CallStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCallStmt(@NotNull MtsParser.CallStmtContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#ExprAccessExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprAccessExpr(@NotNull MtsParser.ExprAccessExprContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#BlankStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlankStmt(@NotNull MtsParser.BlankStmtContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#PrefixOpExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrefixOpExpr(@NotNull MtsParser.PrefixOpExprContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#FunctionCall}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionCall(@NotNull MtsParser.FunctionCallContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#NameFieldAccess}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNameFieldAccess(@NotNull MtsParser.NameFieldAccessContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#ShowCommandStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitShowCommandStmt(@NotNull MtsParser.ShowCommandStmtContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#IfThenElse}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfThenElse(@NotNull MtsParser.IfThenElseContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#Return}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturn(@NotNull MtsParser.ReturnContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#AnonymousFunctionDeclr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAnonymousFunctionDeclr(@NotNull MtsParser.AnonymousFunctionDeclrContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#FunctionDeclr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionDeclr(@NotNull MtsParser.FunctionDeclrContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#SimpleAssignmentStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSimpleAssignmentStmt(@NotNull MtsParser.SimpleAssignmentStmtContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#LocalFunctionDeclr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLocalFunctionDeclr(@NotNull MtsParser.LocalFunctionDeclrContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#nameList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNameList(@NotNull MtsParser.NameListContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#ExprFieldAccess}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprFieldAccess(@NotNull MtsParser.ExprFieldAccessContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#elseIfBody}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitElseIfBody(@NotNull MtsParser.ElseIfBodyContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#SimpleAssignmentTarget}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSimpleAssignmentTarget(@NotNull MtsParser.SimpleAssignmentTargetContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#DoBlock}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDoBlock(@NotNull MtsParser.DoBlockContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#block}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlock(@NotNull MtsParser.BlockContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#tableCtor}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTableCtor(@NotNull MtsParser.TableCtorContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#OperatorAssignmentStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOperatorAssignmentStmt(@NotNull MtsParser.OperatorAssignmentStmtContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#LogicalOpExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLogicalOpExpr(@NotNull MtsParser.LogicalOpExprContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#MethodCall}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMethodCall(@NotNull MtsParser.MethodCallContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#breakStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBreakStmt(@NotNull MtsParser.BreakStmtContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#appendExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAppendExpr(@NotNull MtsParser.AppendExprContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#ExprKeyField}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprKeyField(@NotNull MtsParser.ExprKeyFieldContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#RepeatLoop}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRepeatLoop(@NotNull MtsParser.RepeatLoopContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#LiteralExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLiteralExpr(@NotNull MtsParser.LiteralExprContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#NameCallExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNameCallExpr(@NotNull MtsParser.NameCallExprContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#LocalVariableDeclr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLocalVariableDeclr(@NotNull MtsParser.LocalVariableDeclrContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#NumericForLoop}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNumericForLoop(@NotNull MtsParser.NumericForLoopContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#NameKeyField}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNameKeyField(@NotNull MtsParser.NameKeyFieldContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#exprList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprList(@NotNull MtsParser.ExprListContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#WhileLoop}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhileLoop(@NotNull MtsParser.WhileLoopContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#ExprPrefixedAssignmentTarget}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprPrefixedAssignmentTarget(@NotNull MtsParser.ExprPrefixedAssignmentTargetContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#fieldList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFieldList(@NotNull MtsParser.FieldListContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#CallCommandStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCallCommandStmt(@NotNull MtsParser.CallCommandStmtContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#GenericForLoop}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGenericForLoop(@NotNull MtsParser.GenericForLoopContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#GlobalFunctionDeclr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGlobalFunctionDeclr(@NotNull MtsParser.GlobalFunctionDeclrContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#funcName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFuncName(@NotNull MtsParser.FuncNameContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#Break}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBreak(@NotNull MtsParser.BreakContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#genericForControl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGenericForControl(@NotNull MtsParser.GenericForControlContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#numericForControl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNumericForControl(@NotNull MtsParser.NumericForControlContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#MenuCommandStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMenuCommandStmt(@NotNull MtsParser.MenuCommandStmtContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#JumpCommandStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitJumpCommandStmt(@NotNull MtsParser.JumpCommandStmtContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#ListField}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitListField(@NotNull MtsParser.ListFieldContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#HideCommandStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitHideCommandStmt(@NotNull MtsParser.HideCommandStmtContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#SimpleExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSimpleExpr(@NotNull MtsParser.SimpleExprContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#SceneCommandStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSceneCommandStmt(@NotNull MtsParser.SceneCommandStmtContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#funcBody}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFuncBody(@NotNull MtsParser.FuncBodyContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#assignmentTargetList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignmentTargetList(@NotNull MtsParser.AssignmentTargetListContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLiteral(@NotNull MtsParser.LiteralContext ctx);
}