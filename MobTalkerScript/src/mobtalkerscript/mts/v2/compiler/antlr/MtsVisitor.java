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
	 * Visit a parse tree produced by {@link MtsParser#fieldDefList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFieldDefList(@NotNull MtsParser.FieldDefListContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#chunk}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitChunk(@NotNull MtsParser.ChunkContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#NullLiteral}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNullLiteral(@NotNull MtsParser.NullLiteralContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#paramList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParamList(@NotNull MtsParser.ParamListContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#elseBody}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitElseBody(@NotNull MtsParser.ElseBodyContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#LabelStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLabelStmt(@NotNull MtsParser.LabelStmtContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#AssignmentStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignmentStmt(@NotNull MtsParser.AssignmentStmtContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#BinaryOpExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBinaryOpExpr(@NotNull MtsParser.BinaryOpExprContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#CallStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCallStmt(@NotNull MtsParser.CallStmtContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#varSuffix}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarSuffix(@NotNull MtsParser.VarSuffixContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#varAccess}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarAccess(@NotNull MtsParser.VarAccessContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#BlankStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlankStmt(@NotNull MtsParser.BlankStmtContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#ReturnStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturnStmt(@NotNull MtsParser.ReturnStmtContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#UnaryOpExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnaryOpExpr(@NotNull MtsParser.UnaryOpExprContext ctx);

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
	 * Visit a parse tree produced by {@link MtsParser#IfThenElse}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfThenElse(@NotNull MtsParser.IfThenElseContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#SimpleAssignmentStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSimpleAssignmentStmt(@NotNull MtsParser.SimpleAssignmentStmtContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#nameList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNameList(@NotNull MtsParser.NameListContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#FuncDeclrStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFuncDeclrStmt(@NotNull MtsParser.FuncDeclrStmtContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#NestedBlock}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNestedBlock(@NotNull MtsParser.NestedBlockContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#call}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCall(@NotNull MtsParser.CallContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#LocalVarDeclrStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLocalVarDeclrStmt(@NotNull MtsParser.LocalVarDeclrStmtContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#ExprFieldAccess}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprFieldAccess(@NotNull MtsParser.ExprFieldAccessContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#BreakStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBreakStmt(@NotNull MtsParser.BreakStmtContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#elseIfBody}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitElseIfBody(@NotNull MtsParser.ElseIfBodyContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#varOrExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarOrExpr(@NotNull MtsParser.VarOrExprContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#varExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarExpr(@NotNull MtsParser.VarExprContext ctx);

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
	 * Visit a parse tree produced by {@link MtsParser#NumberLiteral}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNumberLiteral(@NotNull MtsParser.NumberLiteralContext ctx);

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
	 * Visit a parse tree produced by {@link MtsParser#CallExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCallExpr(@NotNull MtsParser.CallExprContext ctx);

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
	 * Visit a parse tree produced by {@link MtsParser#varExprList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarExprList(@NotNull MtsParser.VarExprListContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#NumericForLoop}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNumericForLoop(@NotNull MtsParser.NumericForLoopContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#ConditionalOpExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConditionalOpExpr(@NotNull MtsParser.ConditionalOpExprContext ctx);

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
	 * Visit a parse tree produced by {@link MtsParser#StringLiteral}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStringLiteral(@NotNull MtsParser.StringLiteralContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#WhileLoop}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhileLoop(@NotNull MtsParser.WhileLoopContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#GenericForLoop}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGenericForLoop(@NotNull MtsParser.GenericForLoopContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#funcName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFuncName(@NotNull MtsParser.FuncNameContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#FuncDeclrExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFuncDeclrExpr(@NotNull MtsParser.FuncDeclrExprContext ctx);

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
	 * Visit a parse tree produced by {@link MtsParser#LocalFuncDeclrStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLocalFuncDeclrStmt(@NotNull MtsParser.LocalFuncDeclrStmtContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#ListField}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitListField(@NotNull MtsParser.ListFieldContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#SimpleExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSimpleExpr(@NotNull MtsParser.SimpleExprContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#funcBody}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFuncBody(@NotNull MtsParser.FuncBodyContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#BooleanLiteral}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBooleanLiteral(@NotNull MtsParser.BooleanLiteralContext ctx);
}