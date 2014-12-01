// Generated from src/main/antlr/Mts3.g4 by ANTLR 4.4
package net.mobtalker.mobtalkerscript.v3.compiler.antlr.generated;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.*;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link Mts3Parser}.
 */
public interface Mts3Listener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by the {@code FieldNameSuffix}
	 * labeled alternative in {@link Mts3Parser#varSuffix}.
	 * @param ctx the parse tree
	 */
	void enterFieldNameSuffix(@NotNull Mts3Parser.FieldNameSuffixContext ctx);

	/**
	 * Enter a parse tree produced by the {@code LabelStmt}
	 * labeled alternative in {@link Mts3Parser#stmt}.
	 * @param ctx the parse tree
	 */
	void enterLabelStmt(@NotNull Mts3Parser.LabelStmtContext ctx);

	/**
	 * Enter a parse tree produced by the {@code GenericForStmt}
	 * labeled alternative in {@link Mts3Parser#stmt}.
	 * @param ctx the parse tree
	 */
	void enterGenericForStmt(@NotNull Mts3Parser.GenericForStmtContext ctx);

	/**
	 * Enter a parse tree produced by the {@code FunctionDefinitionExpr}
	 * labeled alternative in {@link Mts3Parser#expr}.
	 * @param ctx the parse tree
	 */
	void enterFunctionDefinitionExpr(@NotNull Mts3Parser.FunctionDefinitionExprContext ctx);

	/**
	 * Enter a parse tree produced by {@link Mts3Parser#nameAndArgs}.
	 * @param ctx the parse tree
	 */
	void enterNameAndArgs(@NotNull Mts3Parser.NameAndArgsContext ctx);

	/**
	 * Enter a parse tree produced by the {@code NameField}
	 * labeled alternative in {@link Mts3Parser#field}.
	 * @param ctx the parse tree
	 */
	void enterNameField(@NotNull Mts3Parser.NameFieldContext ctx);

	/**
	 * Enter a parse tree produced by the {@code GotoStmt}
	 * labeled alternative in {@link Mts3Parser#stmt}.
	 * @param ctx the parse tree
	 */
	void enterGotoStmt(@NotNull Mts3Parser.GotoStmtContext ctx);

	/**
	 * Enter a parse tree produced by the {@code LocalVariableDeclarationStmt}
	 * labeled alternative in {@link Mts3Parser#stmt}.
	 * @param ctx the parse tree
	 */
	void enterLocalVariableDeclarationStmt(@NotNull Mts3Parser.LocalVariableDeclarationStmtContext ctx);

	/**
	 * Enter a parse tree produced by {@link Mts3Parser#block}.
	 * @param ctx the parse tree
	 */
	void enterBlock(@NotNull Mts3Parser.BlockContext ctx);

	/**
	 * Enter a parse tree produced by the {@code ShowStmt}
	 * labeled alternative in {@link Mts3Parser#stmt}.
	 * @param ctx the parse tree
	 */
	void enterShowStmt(@NotNull Mts3Parser.ShowStmtContext ctx);

	/**
	 * Enter a parse tree produced by {@link Mts3Parser#var}.
	 * @param ctx the parse tree
	 */
	void enterVar(@NotNull Mts3Parser.VarContext ctx);

	/**
	 * Enter a parse tree produced by the {@code BlockStmt}
	 * labeled alternative in {@link Mts3Parser#stmt}.
	 * @param ctx the parse tree
	 */
	void enterBlockStmt(@NotNull Mts3Parser.BlockStmtContext ctx);

	/**
	 * Enter a parse tree produced by the {@code ExprField}
	 * labeled alternative in {@link Mts3Parser#field}.
	 * @param ctx the parse tree
	 */
	void enterExprField(@NotNull Mts3Parser.ExprFieldContext ctx);

	/**
	 * Enter a parse tree produced by the {@code AssignmentExpr}
	 * labeled alternative in {@link Mts3Parser#expr}.
	 * @param ctx the parse tree
	 */
	void enterAssignmentExpr(@NotNull Mts3Parser.AssignmentExprContext ctx);

	/**
	 * Enter a parse tree produced by {@link Mts3Parser#returnStmt}.
	 * @param ctx the parse tree
	 */
	void enterReturnStmt(@NotNull Mts3Parser.ReturnStmtContext ctx);

	/**
	 * Enter a parse tree produced by the {@code UnaryExpr}
	 * labeled alternative in {@link Mts3Parser#expr}.
	 * @param ctx the parse tree
	 */
	void enterUnaryExpr(@NotNull Mts3Parser.UnaryExprContext ctx);

	/**
	 * Enter a parse tree produced by the {@code PrefixExpr}
	 * labeled alternative in {@link Mts3Parser#expr}.
	 * @param ctx the parse tree
	 */
	void enterPrefixExpr(@NotNull Mts3Parser.PrefixExprContext ctx);

	/**
	 * Enter a parse tree produced by the {@code StringLiteral}
	 * labeled alternative in {@link Mts3Parser#literal}.
	 * @param ctx the parse tree
	 */
	void enterStringLiteral(@NotNull Mts3Parser.StringLiteralContext ctx);

	/**
	 * Enter a parse tree produced by {@link Mts3Parser#field}.
	 * @param ctx the parse tree
	 */
	void enterField(@NotNull Mts3Parser.FieldContext ctx);

	/**
	 * Enter a parse tree produced by the {@code NormalString}
	 * labeled alternative in {@link Mts3Parser#string}.
	 * @param ctx the parse tree
	 */
	void enterNormalString(@NotNull Mts3Parser.NormalStringContext ctx);

	/**
	 * Enter a parse tree produced by {@link Mts3Parser#breakStmt}.
	 * @param ctx the parse tree
	 */
	void enterBreakStmt(@NotNull Mts3Parser.BreakStmtContext ctx);

	/**
	 * Enter a parse tree produced by {@link Mts3Parser#paramList}.
	 * @param ctx the parse tree
	 */
	void enterParamList(@NotNull Mts3Parser.ParamListContext ctx);

	/**
	 * Enter a parse tree produced by the {@code ArgList}
	 * labeled alternative in {@link Mts3Parser#args}.
	 * @param ctx the parse tree
	 */
	void enterArgList(@NotNull Mts3Parser.ArgListContext ctx);

	/**
	 * Enter a parse tree produced by the {@code MenuStmt}
	 * labeled alternative in {@link Mts3Parser#stmt}.
	 * @param ctx the parse tree
	 */
	void enterMenuStmt(@NotNull Mts3Parser.MenuStmtContext ctx);

	/**
	 * Enter a parse tree produced by the {@code FieldExprSuffix}
	 * labeled alternative in {@link Mts3Parser#varSuffix}.
	 * @param ctx the parse tree
	 */
	void enterFieldExprSuffix(@NotNull Mts3Parser.FieldExprSuffixContext ctx);

	/**
	 * Enter a parse tree produced by the {@code LogicalExpr}
	 * labeled alternative in {@link Mts3Parser#expr}.
	 * @param ctx the parse tree
	 */
	void enterLogicalExpr(@NotNull Mts3Parser.LogicalExprContext ctx);

	/**
	 * Enter a parse tree produced by {@link Mts3Parser#funcBody}.
	 * @param ctx the parse tree
	 */
	void enterFuncBody(@NotNull Mts3Parser.FuncBodyContext ctx);

	/**
	 * Enter a parse tree produced by the {@code CharString}
	 * labeled alternative in {@link Mts3Parser#string}.
	 * @param ctx the parse tree
	 */
	void enterCharString(@NotNull Mts3Parser.CharStringContext ctx);

	/**
	 * Enter a parse tree produced by the {@code TableConstructorExpr}
	 * labeled alternative in {@link Mts3Parser#expr}.
	 * @param ctx the parse tree
	 */
	void enterTableConstructorExpr(@NotNull Mts3Parser.TableConstructorExprContext ctx);

	/**
	 * Enter a parse tree produced by {@link Mts3Parser#string}.
	 * @param ctx the parse tree
	 */
	void enterString(@NotNull Mts3Parser.StringContext ctx);

	/**
	 * Enter a parse tree produced by the {@code BooleanLiteral}
	 * labeled alternative in {@link Mts3Parser#literal}.
	 * @param ctx the parse tree
	 */
	void enterBooleanLiteral(@NotNull Mts3Parser.BooleanLiteralContext ctx);

	/**
	 * Enter a parse tree produced by the {@code LocalFunctionDefinitionStmt}
	 * labeled alternative in {@link Mts3Parser#stmt}.
	 * @param ctx the parse tree
	 */
	void enterLocalFunctionDefinitionStmt(@NotNull Mts3Parser.LocalFunctionDefinitionStmtContext ctx);

	/**
	 * Enter a parse tree produced by the {@code FunctionDefinitionStmt}
	 * labeled alternative in {@link Mts3Parser#stmt}.
	 * @param ctx the parse tree
	 */
	void enterFunctionDefinitionStmt(@NotNull Mts3Parser.FunctionDefinitionStmtContext ctx);

	/**
	 * Enter a parse tree produced by the {@code SayStmt}
	 * labeled alternative in {@link Mts3Parser#stmt}.
	 * @param ctx the parse tree
	 */
	void enterSayStmt(@NotNull Mts3Parser.SayStmtContext ctx);

	/**
	 * Enter a parse tree produced by {@link Mts3Parser#chunk}.
	 * @param ctx the parse tree
	 */
	void enterChunk(@NotNull Mts3Parser.ChunkContext ctx);

	/**
	 * Enter a parse tree produced by the {@code ListField}
	 * labeled alternative in {@link Mts3Parser#field}.
	 * @param ctx the parse tree
	 */
	void enterListField(@NotNull Mts3Parser.ListFieldContext ctx);

	/**
	 * Enter a parse tree produced by the {@code VarargsExpr}
	 * labeled alternative in {@link Mts3Parser#expr}.
	 * @param ctx the parse tree
	 */
	void enterVarargsExpr(@NotNull Mts3Parser.VarargsExprContext ctx);

	/**
	 * Enter a parse tree produced by the {@code RepeatStmt}
	 * labeled alternative in {@link Mts3Parser#stmt}.
	 * @param ctx the parse tree
	 */
	void enterRepeatStmt(@NotNull Mts3Parser.RepeatStmtContext ctx);

	/**
	 * Enter a parse tree produced by {@link Mts3Parser#literal}.
	 * @param ctx the parse tree
	 */
	void enterLiteral(@NotNull Mts3Parser.LiteralContext ctx);

	/**
	 * Enter a parse tree produced by the {@code SceneStmt}
	 * labeled alternative in {@link Mts3Parser#stmt}.
	 * @param ctx the parse tree
	 */
	void enterSceneStmt(@NotNull Mts3Parser.SceneStmtContext ctx);

	/**
	 * Enter a parse tree produced by {@link Mts3Parser#funcName}.
	 * @param ctx the parse tree
	 */
	void enterFuncName(@NotNull Mts3Parser.FuncNameContext ctx);

	/**
	 * Enter a parse tree produced by the {@code HideStmt}
	 * labeled alternative in {@link Mts3Parser#stmt}.
	 * @param ctx the parse tree
	 */
	void enterHideStmt(@NotNull Mts3Parser.HideStmtContext ctx);

	/**
	 * Enter a parse tree produced by the {@code WhileStmt}
	 * labeled alternative in {@link Mts3Parser#stmt}.
	 * @param ctx the parse tree
	 */
	void enterWhileStmt(@NotNull Mts3Parser.WhileStmtContext ctx);

	/**
	 * Enter a parse tree produced by the {@code LiteralExpr}
	 * labeled alternative in {@link Mts3Parser#expr}.
	 * @param ctx the parse tree
	 */
	void enterLiteralExpr(@NotNull Mts3Parser.LiteralExprContext ctx);

	/**
	 * Enter a parse tree produced by the {@code LongString}
	 * labeled alternative in {@link Mts3Parser#string}.
	 * @param ctx the parse tree
	 */
	void enterLongString(@NotNull Mts3Parser.LongStringContext ctx);

	/**
	 * Enter a parse tree produced by {@link Mts3Parser#expr}.
	 * @param ctx the parse tree
	 */
	void enterExpr(@NotNull Mts3Parser.ExprContext ctx);

	/**
	 * Enter a parse tree produced by {@link Mts3Parser#menuOption}.
	 * @param ctx the parse tree
	 */
	void enterMenuOption(@NotNull Mts3Parser.MenuOptionContext ctx);

	/**
	 * Enter a parse tree produced by the {@code EmptyStmt}
	 * labeled alternative in {@link Mts3Parser#stmt}.
	 * @param ctx the parse tree
	 */
	void enterEmptyStmt(@NotNull Mts3Parser.EmptyStmtContext ctx);

	/**
	 * Enter a parse tree produced by {@link Mts3Parser#fieldList}.
	 * @param ctx the parse tree
	 */
	void enterFieldList(@NotNull Mts3Parser.FieldListContext ctx);

	/**
	 * Enter a parse tree produced by the {@code ArgString}
	 * labeled alternative in {@link Mts3Parser#args}.
	 * @param ctx the parse tree
	 */
	void enterArgString(@NotNull Mts3Parser.ArgStringContext ctx);

	/**
	 * Enter a parse tree produced by {@link Mts3Parser#varOrExpr}.
	 * @param ctx the parse tree
	 */
	void enterVarOrExpr(@NotNull Mts3Parser.VarOrExprContext ctx);

	/**
	 * Enter a parse tree produced by the {@code BinaryExpr}
	 * labeled alternative in {@link Mts3Parser#expr}.
	 * @param ctx the parse tree
	 */
	void enterBinaryExpr(@NotNull Mts3Parser.BinaryExprContext ctx);

	/**
	 * Enter a parse tree produced by the {@code CallStmt}
	 * labeled alternative in {@link Mts3Parser#stmt}.
	 * @param ctx the parse tree
	 */
	void enterCallStmt(@NotNull Mts3Parser.CallStmtContext ctx);

	/**
	 * Enter a parse tree produced by the {@code ConditionalExpr}
	 * labeled alternative in {@link Mts3Parser#expr}.
	 * @param ctx the parse tree
	 */
	void enterConditionalExpr(@NotNull Mts3Parser.ConditionalExprContext ctx);

	/**
	 * Enter a parse tree produced by {@link Mts3Parser#args}.
	 * @param ctx the parse tree
	 */
	void enterArgs(@NotNull Mts3Parser.ArgsContext ctx);

	/**
	 * Enter a parse tree produced by {@link Mts3Parser#tableCtor}.
	 * @param ctx the parse tree
	 */
	void enterTableCtor(@NotNull Mts3Parser.TableCtorContext ctx);

	/**
	 * Enter a parse tree produced by {@link Mts3Parser#varList}.
	 * @param ctx the parse tree
	 */
	void enterVarList(@NotNull Mts3Parser.VarListContext ctx);

	/**
	 * Enter a parse tree produced by the {@code IfThenElseStmt}
	 * labeled alternative in {@link Mts3Parser#stmt}.
	 * @param ctx the parse tree
	 */
	void enterIfThenElseStmt(@NotNull Mts3Parser.IfThenElseStmtContext ctx);

	/**
	 * Enter a parse tree produced by the {@code NilLiteral}
	 * labeled alternative in {@link Mts3Parser#literal}.
	 * @param ctx the parse tree
	 */
	void enterNilLiteral(@NotNull Mts3Parser.NilLiteralContext ctx);

	/**
	 * Enter a parse tree produced by {@link Mts3Parser#exprList}.
	 * @param ctx the parse tree
	 */
	void enterExprList(@NotNull Mts3Parser.ExprListContext ctx);

	/**
	 * Enter a parse tree produced by {@link Mts3Parser#nameList}.
	 * @param ctx the parse tree
	 */
	void enterNameList(@NotNull Mts3Parser.NameListContext ctx);

	/**
	 * Enter a parse tree produced by the {@code AssignmentStmt}
	 * labeled alternative in {@link Mts3Parser#stmt}.
	 * @param ctx the parse tree
	 */
	void enterAssignmentStmt(@NotNull Mts3Parser.AssignmentStmtContext ctx);

	/**
	 * Enter a parse tree produced by the {@code ArgTable}
	 * labeled alternative in {@link Mts3Parser#args}.
	 * @param ctx the parse tree
	 */
	void enterArgTable(@NotNull Mts3Parser.ArgTableContext ctx);

	/**
	 * Enter a parse tree produced by the {@code EnclosedExpr}
	 * labeled alternative in {@link Mts3Parser#expr}.
	 * @param ctx the parse tree
	 */
	void enterEnclosedExpr(@NotNull Mts3Parser.EnclosedExprContext ctx);

	/**
	 * Enter a parse tree produced by the {@code NumericForStmt}
	 * labeled alternative in {@link Mts3Parser#stmt}.
	 * @param ctx the parse tree
	 */
	void enterNumericForStmt(@NotNull Mts3Parser.NumericForStmtContext ctx);

	/**
	 * Enter a parse tree produced by the {@code NumberLiteral}
	 * labeled alternative in {@link Mts3Parser#literal}.
	 * @param ctx the parse tree
	 */
	void enterNumberLiteral(@NotNull Mts3Parser.NumberLiteralContext ctx);

	/**
	 * Enter a parse tree produced by {@link Mts3Parser#stmt}.
	 * @param ctx the parse tree
	 */
	void enterStmt(@NotNull Mts3Parser.StmtContext ctx);

	/**
	 * Enter a parse tree produced by {@link Mts3Parser#varSuffix}.
	 * @param ctx the parse tree
	 */
	void enterVarSuffix(@NotNull Mts3Parser.VarSuffixContext ctx);

    void visit(@NotNull ParseTree tree);

    void visitChildren(@NotNull RuleNode node);
}