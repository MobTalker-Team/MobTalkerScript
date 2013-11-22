// Generated from C:\Users\Tobsen\Desktop\Minecraft\MobTalker2\MobTalkerScript\MobTalkerScript\src\mobtalkerscript\mts\v1\parser\Mts.g4 by ANTLR 4.1
package mobtalkerscript.mts.v1.parser;
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
	 * Visit a parse tree produced by {@link MtsParser#NullLiteral}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNullLiteral(@NotNull MtsParser.NullLiteralContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#BlankStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlankStatement(@NotNull MtsParser.BlankStatementContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#CommandSay}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCommandSay(@NotNull MtsParser.CommandSayContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#labelDecl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLabelDecl(@NotNull MtsParser.LabelDeclContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#ExpressionKeyedFieldDef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpressionKeyedFieldDef(@NotNull MtsParser.ExpressionKeyedFieldDefContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#Jump}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitJump(@NotNull MtsParser.JumpContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#returnStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturnStmt(@NotNull MtsParser.ReturnStmtContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#TableAccess}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTableAccess(@NotNull MtsParser.TableAccessContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#IdentifierKeyedAccess}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdentifierKeyedAccess(@NotNull MtsParser.IdentifierKeyedAccessContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#tableExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTableExpr(@NotNull MtsParser.TableExprContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#VariableAssignment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariableAssignment(@NotNull MtsParser.VariableAssignmentContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#LogicalExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLogicalExpr(@NotNull MtsParser.LogicalExprContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#Literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLiteral(@NotNull MtsParser.LiteralContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#CommandShow}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCommandShow(@NotNull MtsParser.CommandShowContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#CommandMenu}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCommandMenu(@NotNull MtsParser.CommandMenuContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#Call}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCall(@NotNull MtsParser.CallContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#UnaryExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnaryExpr(@NotNull MtsParser.UnaryExprContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#funcDecl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFuncDecl(@NotNull MtsParser.FuncDeclContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#FunctionCall}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionCall(@NotNull MtsParser.FunctionCallContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#TableCall}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTableCall(@NotNull MtsParser.TableCallContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#WhileBlock}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhileBlock(@NotNull MtsParser.WhileBlockContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#IfElseBlock}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfElseBlock(@NotNull MtsParser.IfElseBlockContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#Command}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCommand(@NotNull MtsParser.CommandContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#Statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement(@NotNull MtsParser.StatementContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#block}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlock(@NotNull MtsParser.BlockContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#DoBlock}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDoBlock(@NotNull MtsParser.DoBlockContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#NumberLiteral}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNumberLiteral(@NotNull MtsParser.NumberLiteralContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#loopBlock}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLoopBlock(@NotNull MtsParser.LoopBlockContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#ListFieldDef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitListFieldDef(@NotNull MtsParser.ListFieldDefContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#GenericFor}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGenericFor(@NotNull MtsParser.GenericForContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#breakStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBreakStmt(@NotNull MtsParser.BreakStmtContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#BinaryExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBinaryExpr(@NotNull MtsParser.BinaryExprContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#tableCtorExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTableCtorExpr(@NotNull MtsParser.TableCtorExprContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#LocalFieldDefinition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLocalFieldDefinition(@NotNull MtsParser.LocalFieldDefinitionContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#commandMenuOption}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCommandMenuOption(@NotNull MtsParser.CommandMenuOptionContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#exprStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprStmt(@NotNull MtsParser.ExprStmtContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#IdentifierKeyedFieldDef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdentifierKeyedFieldDef(@NotNull MtsParser.IdentifierKeyedFieldDefContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#VariableAccess}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariableAccess(@NotNull MtsParser.VariableAccessContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#StringLiteral}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStringLiteral(@NotNull MtsParser.StringLiteralContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#ExpressionKeyedAccess}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpressionKeyedAccess(@NotNull MtsParser.ExpressionKeyedAccessContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#CommandScene}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCommandScene(@NotNull MtsParser.CommandSceneContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#NumericFor}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNumericFor(@NotNull MtsParser.NumericForContext ctx);

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
	 * Visit a parse tree produced by {@link MtsParser#TableAssignment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTableAssignment(@NotNull MtsParser.TableAssignmentContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#RepeatBlock}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRepeatBlock(@NotNull MtsParser.RepeatBlockContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#CommandHide}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCommandHide(@NotNull MtsParser.CommandHideContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#SimpleExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSimpleExpr(@NotNull MtsParser.SimpleExprContext ctx);

	/**
	 * Visit a parse tree produced by {@link MtsParser#BooleanLiteral}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBooleanLiteral(@NotNull MtsParser.BooleanLiteralContext ctx);
}