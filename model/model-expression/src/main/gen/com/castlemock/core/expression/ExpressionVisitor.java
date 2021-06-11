// Generated from Expression.g4 by ANTLR 4.9.2
package com.castlemock.core.expression;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link ExpressionParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface ExpressionVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link ExpressionParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpression(ExpressionParser.ExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExpressionParser#expressionName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpressionName(ExpressionParser.ExpressionNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExpressionParser#argument}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArgument(ExpressionParser.ArgumentContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExpressionParser#argumentName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArgumentName(ExpressionParser.ArgumentNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExpressionParser#argumentValue}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArgumentValue(ExpressionParser.ArgumentValueContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExpressionParser#argumentString}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArgumentString(ExpressionParser.ArgumentStringContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExpressionParser#argumentNumber}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArgumentNumber(ExpressionParser.ArgumentNumberContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExpressionParser#number}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNumber(ExpressionParser.NumberContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExpressionParser#array}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArray(ExpressionParser.ArrayContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExpressionParser#string}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitString(ExpressionParser.StringContext ctx);
}