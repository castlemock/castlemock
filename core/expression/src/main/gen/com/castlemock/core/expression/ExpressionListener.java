// Generated from /Users/karldahlgren/Github/castlemock/castlemock/code/core/expression/src/main/antlr/com/castlemock/core/expression/Expression.g4 by ANTLR 4.7
package com.castlemock.core.expression;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link ExpressionParser}.
 */
public interface ExpressionListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link ExpressionParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpression(ExpressionParser.ExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpression(ExpressionParser.ExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExpressionParser#expressionName}.
	 * @param ctx the parse tree
	 */
	void enterExpressionName(ExpressionParser.ExpressionNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionParser#expressionName}.
	 * @param ctx the parse tree
	 */
	void exitExpressionName(ExpressionParser.ExpressionNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExpressionParser#argument}.
	 * @param ctx the parse tree
	 */
	void enterArgument(ExpressionParser.ArgumentContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionParser#argument}.
	 * @param ctx the parse tree
	 */
	void exitArgument(ExpressionParser.ArgumentContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExpressionParser#argumentName}.
	 * @param ctx the parse tree
	 */
	void enterArgumentName(ExpressionParser.ArgumentNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionParser#argumentName}.
	 * @param ctx the parse tree
	 */
	void exitArgumentName(ExpressionParser.ArgumentNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExpressionParser#argumentValue}.
	 * @param ctx the parse tree
	 */
	void enterArgumentValue(ExpressionParser.ArgumentValueContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionParser#argumentValue}.
	 * @param ctx the parse tree
	 */
	void exitArgumentValue(ExpressionParser.ArgumentValueContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExpressionParser#argumentString}.
	 * @param ctx the parse tree
	 */
	void enterArgumentString(ExpressionParser.ArgumentStringContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionParser#argumentString}.
	 * @param ctx the parse tree
	 */
	void exitArgumentString(ExpressionParser.ArgumentStringContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExpressionParser#argumentNumber}.
	 * @param ctx the parse tree
	 */
	void enterArgumentNumber(ExpressionParser.ArgumentNumberContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionParser#argumentNumber}.
	 * @param ctx the parse tree
	 */
	void exitArgumentNumber(ExpressionParser.ArgumentNumberContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExpressionParser#number}.
	 * @param ctx the parse tree
	 */
	void enterNumber(ExpressionParser.NumberContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionParser#number}.
	 * @param ctx the parse tree
	 */
	void exitNumber(ExpressionParser.NumberContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExpressionParser#array}.
	 * @param ctx the parse tree
	 */
	void enterArray(ExpressionParser.ArrayContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionParser#array}.
	 * @param ctx the parse tree
	 */
	void exitArray(ExpressionParser.ArrayContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExpressionParser#string}.
	 * @param ctx the parse tree
	 */
	void enterString(ExpressionParser.StringContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionParser#string}.
	 * @param ctx the parse tree
	 */
	void exitString(ExpressionParser.StringContext ctx);
}