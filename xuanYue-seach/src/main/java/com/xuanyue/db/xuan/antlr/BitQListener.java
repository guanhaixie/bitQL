// Generated from BitQ.g4 by ANTLR 4.7.2

	package com.xuanyue.db.xuan.antlr;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link BitQParser}.
 */
public interface BitQListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link BitQParser#query}.
	 * @param ctx the parse tree
	 */
	void enterQuery(BitQParser.QueryContext ctx);
	/**
	 * Exit a parse tree produced by {@link BitQParser#query}.
	 * @param ctx the parse tree
	 */
	void exitQuery(BitQParser.QueryContext ctx);
	/**
	 * Enter a parse tree produced by {@link BitQParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterExpr(BitQParser.ExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link BitQParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitExpr(BitQParser.ExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link BitQParser#repo}.
	 * @param ctx the parse tree
	 */
	void enterRepo(BitQParser.RepoContext ctx);
	/**
	 * Exit a parse tree produced by {@link BitQParser#repo}.
	 * @param ctx the parse tree
	 */
	void exitRepo(BitQParser.RepoContext ctx);
	/**
	 * Enter a parse tree produced by {@link BitQParser#saveAsFile}.
	 * @param ctx the parse tree
	 */
	void enterSaveAsFile(BitQParser.SaveAsFileContext ctx);
	/**
	 * Exit a parse tree produced by {@link BitQParser#saveAsFile}.
	 * @param ctx the parse tree
	 */
	void exitSaveAsFile(BitQParser.SaveAsFileContext ctx);
	/**
	 * Enter a parse tree produced by {@link BitQParser#tablePart}.
	 * @param ctx the parse tree
	 */
	void enterTablePart(BitQParser.TablePartContext ctx);
	/**
	 * Exit a parse tree produced by {@link BitQParser#tablePart}.
	 * @param ctx the parse tree
	 */
	void exitTablePart(BitQParser.TablePartContext ctx);
	/**
	 * Enter a parse tree produced by {@link BitQParser#tjoinPart}.
	 * @param ctx the parse tree
	 */
	void enterTjoinPart(BitQParser.TjoinPartContext ctx);
	/**
	 * Exit a parse tree produced by {@link BitQParser#tjoinPart}.
	 * @param ctx the parse tree
	 */
	void exitTjoinPart(BitQParser.TjoinPartContext ctx);
	/**
	 * Enter a parse tree produced by {@link BitQParser#orCondition}.
	 * @param ctx the parse tree
	 */
	void enterOrCondition(BitQParser.OrConditionContext ctx);
	/**
	 * Exit a parse tree produced by {@link BitQParser#orCondition}.
	 * @param ctx the parse tree
	 */
	void exitOrCondition(BitQParser.OrConditionContext ctx);
	/**
	 * Enter a parse tree produced by {@link BitQParser#andCondition}.
	 * @param ctx the parse tree
	 */
	void enterAndCondition(BitQParser.AndConditionContext ctx);
	/**
	 * Exit a parse tree produced by {@link BitQParser#andCondition}.
	 * @param ctx the parse tree
	 */
	void exitAndCondition(BitQParser.AndConditionContext ctx);
	/**
	 * Enter a parse tree produced by {@link BitQParser#conditionElement}.
	 * @param ctx the parse tree
	 */
	void enterConditionElement(BitQParser.ConditionElementContext ctx);
	/**
	 * Exit a parse tree produced by {@link BitQParser#conditionElement}.
	 * @param ctx the parse tree
	 */
	void exitConditionElement(BitQParser.ConditionElementContext ctx);
	/**
	 * Enter a parse tree produced by {@link BitQParser#groupCondition}.
	 * @param ctx the parse tree
	 */
	void enterGroupCondition(BitQParser.GroupConditionContext ctx);
	/**
	 * Exit a parse tree produced by {@link BitQParser#groupCondition}.
	 * @param ctx the parse tree
	 */
	void exitGroupCondition(BitQParser.GroupConditionContext ctx);
	/**
	 * Enter a parse tree produced by {@link BitQParser#conditionExpr}.
	 * @param ctx the parse tree
	 */
	void enterConditionExpr(BitQParser.ConditionExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link BitQParser#conditionExpr}.
	 * @param ctx the parse tree
	 */
	void exitConditionExpr(BitQParser.ConditionExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link BitQParser#values}.
	 * @param ctx the parse tree
	 */
	void enterValues(BitQParser.ValuesContext ctx);
	/**
	 * Exit a parse tree produced by {@link BitQParser#values}.
	 * @param ctx the parse tree
	 */
	void exitValues(BitQParser.ValuesContext ctx);
	/**
	 * Enter a parse tree produced by {@link BitQParser#to_date}.
	 * @param ctx the parse tree
	 */
	void enterTo_date(BitQParser.To_dateContext ctx);
	/**
	 * Exit a parse tree produced by {@link BitQParser#to_date}.
	 * @param ctx the parse tree
	 */
	void exitTo_date(BitQParser.To_dateContext ctx);
	/**
	 * Enter a parse tree produced by {@link BitQParser#result}.
	 * @param ctx the parse tree
	 */
	void enterResult(BitQParser.ResultContext ctx);
	/**
	 * Exit a parse tree produced by {@link BitQParser#result}.
	 * @param ctx the parse tree
	 */
	void exitResult(BitQParser.ResultContext ctx);
	/**
	 * Enter a parse tree produced by {@link BitQParser#fullName}.
	 * @param ctx the parse tree
	 */
	void enterFullName(BitQParser.FullNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link BitQParser#fullName}.
	 * @param ctx the parse tree
	 */
	void exitFullName(BitQParser.FullNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link BitQParser#boolTF}.
	 * @param ctx the parse tree
	 */
	void enterBoolTF(BitQParser.BoolTFContext ctx);
	/**
	 * Exit a parse tree produced by {@link BitQParser#boolTF}.
	 * @param ctx the parse tree
	 */
	void exitBoolTF(BitQParser.BoolTFContext ctx);
	/**
	 * Enter a parse tree produced by {@link BitQParser#orNot}.
	 * @param ctx the parse tree
	 */
	void enterOrNot(BitQParser.OrNotContext ctx);
	/**
	 * Exit a parse tree produced by {@link BitQParser#orNot}.
	 * @param ctx the parse tree
	 */
	void exitOrNot(BitQParser.OrNotContext ctx);
	/**
	 * Enter a parse tree produced by {@link BitQParser#andNot}.
	 * @param ctx the parse tree
	 */
	void enterAndNot(BitQParser.AndNotContext ctx);
	/**
	 * Exit a parse tree produced by {@link BitQParser#andNot}.
	 * @param ctx the parse tree
	 */
	void exitAndNot(BitQParser.AndNotContext ctx);
	/**
	 * Enter a parse tree produced by {@link BitQParser#phone_seach}.
	 * @param ctx the parse tree
	 */
	void enterPhone_seach(BitQParser.Phone_seachContext ctx);
	/**
	 * Exit a parse tree produced by {@link BitQParser#phone_seach}.
	 * @param ctx the parse tree
	 */
	void exitPhone_seach(BitQParser.Phone_seachContext ctx);
	/**
	 * Enter a parse tree produced by {@link BitQParser#sortE}.
	 * @param ctx the parse tree
	 */
	void enterSortE(BitQParser.SortEContext ctx);
	/**
	 * Exit a parse tree produced by {@link BitQParser#sortE}.
	 * @param ctx the parse tree
	 */
	void exitSortE(BitQParser.SortEContext ctx);
	/**
	 * Enter a parse tree produced by {@link BitQParser#sortBy}.
	 * @param ctx the parse tree
	 */
	void enterSortBy(BitQParser.SortByContext ctx);
	/**
	 * Exit a parse tree produced by {@link BitQParser#sortBy}.
	 * @param ctx the parse tree
	 */
	void exitSortBy(BitQParser.SortByContext ctx);
	/**
	 * Enter a parse tree produced by {@link BitQParser#limit}.
	 * @param ctx the parse tree
	 */
	void enterLimit(BitQParser.LimitContext ctx);
	/**
	 * Exit a parse tree produced by {@link BitQParser#limit}.
	 * @param ctx the parse tree
	 */
	void exitLimit(BitQParser.LimitContext ctx);
	/**
	 * Enter a parse tree produced by {@link BitQParser#mix}.
	 * @param ctx the parse tree
	 */
	void enterMix(BitQParser.MixContext ctx);
	/**
	 * Exit a parse tree produced by {@link BitQParser#mix}.
	 * @param ctx the parse tree
	 */
	void exitMix(BitQParser.MixContext ctx);
}