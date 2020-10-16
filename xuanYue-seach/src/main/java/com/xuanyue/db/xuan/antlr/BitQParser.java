// Generated from BitQ.g4 by ANTLR 4.4

	package com.xuanyue.db.xuan.antlr;

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class BitQParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.4", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__7=1, T__6=2, T__5=3, T__4=4, T__3=5, T__2=6, T__1=7, T__0=8, TransArrt=9, 
		MIX=10, DESC=11, ASC=12, Phone_seach=13, PositionMatch=14, Contains=15, 
		Has_Every_Char=16, SELECT=17, FROM=18, WHERE=19, ON=20, LEFT=21, RIGHT=22, 
		JOIN=23, AND=24, OR=25, NOT=26, TO_DATE=27, ExprNot=28, COMMA=29, SEMI=30, 
		LIMIT=31, Order=32, By=33, NAME=34, DOT=35, Brackets_L=36, Brackets_R=37, 
		STRING=38, NUM=39, TRUE=40, FALSE=41, WS=42;
	public static final String[] tokenNames = {
		"<INVALID>", "'<='", "'!='", "'['", "'>='", "'<'", "']'", "'='", "'>'", 
		"TransArrt", "MIX", "DESC", "ASC", "Phone_seach", "PositionMatch", "Contains", 
		"Has_Every_Char", "SELECT", "FROM", "WHERE", "ON", "LEFT", "RIGHT", "JOIN", 
		"AND", "OR", "NOT", "TO_DATE", "'!'", "','", "';'", "LIMIT", "Order", 
		"By", "NAME", "'.'", "'('", "')'", "STRING", "NUM", "TRUE", "FALSE", "WS"
	};
	public static final int
		RULE_query = 0, RULE_expr = 1, RULE_repo = 2, RULE_tablePart = 3, RULE_tjoinPart = 4, 
		RULE_orCondition = 5, RULE_andCondition = 6, RULE_conditionElement = 7, 
		RULE_groupCondition = 8, RULE_conditionExpr = 9, RULE_values = 10, RULE_to_date = 11, 
		RULE_result = 12, RULE_fullName = 13, RULE_boolTF = 14, RULE_orNot = 15, 
		RULE_andNot = 16, RULE_phone_seach = 17, RULE_sortE = 18, RULE_sortBy = 19, 
		RULE_limit = 20, RULE_mix = 21;
	public static final String[] ruleNames = {
		"query", "expr", "repo", "tablePart", "tjoinPart", "orCondition", "andCondition", 
		"conditionElement", "groupCondition", "conditionExpr", "values", "to_date", 
		"result", "fullName", "boolTF", "orNot", "andNot", "phone_seach", "sortE", 
		"sortBy", "limit", "mix"
	};

	@Override
	public String getGrammarFileName() { return "BitQ.g4"; }

	@Override
	public String[] getTokenNames() { return tokenNames; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public BitQParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class QueryContext extends ParserRuleContext {
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public QueryContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_query; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BitQListener ) ((BitQListener)listener).enterQuery(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BitQListener ) ((BitQListener)listener).exitQuery(this);
		}
	}

	public final QueryContext query() throws RecognitionException {
		QueryContext _localctx = new QueryContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_query);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(44); expr();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExprContext extends ParserRuleContext {
		public SortByContext sortBy() {
			return getRuleContext(SortByContext.class,0);
		}
		public ResultContext result() {
			return getRuleContext(ResultContext.class,0);
		}
		public OrConditionContext orCondition() {
			return getRuleContext(OrConditionContext.class,0);
		}
		public TerminalNode SEMI() { return getToken(BitQParser.SEMI, 0); }
		public MixContext mix() {
			return getRuleContext(MixContext.class,0);
		}
		public RepoContext repo() {
			return getRuleContext(RepoContext.class,0);
		}
		public TerminalNode WHERE() { return getToken(BitQParser.WHERE, 0); }
		public TerminalNode FROM() { return getToken(BitQParser.FROM, 0); }
		public TerminalNode SELECT() { return getToken(BitQParser.SELECT, 0); }
		public LimitContext limit() {
			return getRuleContext(LimitContext.class,0);
		}
		public ExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BitQListener ) ((BitQListener)listener).enterExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BitQListener ) ((BitQListener)listener).exitExpr(this);
		}
	}

	public final ExprContext expr() throws RecognitionException {
		ExprContext _localctx = new ExprContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_expr);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(46); match(SELECT);
			setState(47); result();
			setState(48); match(FROM);
			setState(49); repo();
			setState(63);
			_la = _input.LA(1);
			if (_la==WHERE) {
				{
				setState(50); match(WHERE);
				setState(51); orCondition();
				setState(53);
				_la = _input.LA(1);
				if (_la==Order) {
					{
					setState(52); sortBy();
					}
				}

				setState(56);
				_la = _input.LA(1);
				if (_la==MIX) {
					{
					setState(55); mix();
					}
				}

				setState(59);
				_la = _input.LA(1);
				if (_la==LIMIT) {
					{
					setState(58); limit();
					}
				}

				setState(61); match(SEMI);
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class RepoContext extends ParserRuleContext {
		public FullNameContext fullName(int i) {
			return getRuleContext(FullNameContext.class,i);
		}
		public List<FullNameContext> fullName() {
			return getRuleContexts(FullNameContext.class);
		}
		public RepoContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_repo; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BitQListener ) ((BitQListener)listener).enterRepo(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BitQListener ) ((BitQListener)listener).exitRepo(this);
		}
	}

	public final RepoContext repo() throws RecognitionException {
		RepoContext _localctx = new RepoContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_repo);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(65); fullName();
			setState(70);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(66); match(COMMA);
				setState(67); fullName();
				}
				}
				setState(72);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TablePartContext extends ParserRuleContext {
		public FullNameContext fullName() {
			return getRuleContext(FullNameContext.class,0);
		}
		public TjoinPartContext tjoinPart() {
			return getRuleContext(TjoinPartContext.class,0);
		}
		public TablePartContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_tablePart; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BitQListener ) ((BitQListener)listener).enterTablePart(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BitQListener ) ((BitQListener)listener).exitTablePart(this);
		}
	}

	public final TablePartContext tablePart() throws RecognitionException {
		TablePartContext _localctx = new TablePartContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_tablePart);
		try {
			setState(75);
			switch ( getInterpreter().adaptivePredict(_input,5,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(73); tjoinPart();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(74); fullName();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TjoinPartContext extends ParserRuleContext {
		public List<TerminalNode> LEFT() { return getTokens(BitQParser.LEFT); }
		public FullNameContext fullName(int i) {
			return getRuleContext(FullNameContext.class,i);
		}
		public List<TerminalNode> ON() { return getTokens(BitQParser.ON); }
		public OrConditionContext orCondition(int i) {
			return getRuleContext(OrConditionContext.class,i);
		}
		public List<FullNameContext> fullName() {
			return getRuleContexts(FullNameContext.class);
		}
		public List<OrConditionContext> orCondition() {
			return getRuleContexts(OrConditionContext.class);
		}
		public TerminalNode ON(int i) {
			return getToken(BitQParser.ON, i);
		}
		public TerminalNode LEFT(int i) {
			return getToken(BitQParser.LEFT, i);
		}
		public TerminalNode JOIN(int i) {
			return getToken(BitQParser.JOIN, i);
		}
		public List<TerminalNode> RIGHT() { return getTokens(BitQParser.RIGHT); }
		public TerminalNode RIGHT(int i) {
			return getToken(BitQParser.RIGHT, i);
		}
		public List<TerminalNode> JOIN() { return getTokens(BitQParser.JOIN); }
		public TjoinPartContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_tjoinPart; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BitQListener ) ((BitQListener)listener).enterTjoinPart(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BitQListener ) ((BitQListener)listener).exitTjoinPart(this);
		}
	}

	public final TjoinPartContext tjoinPart() throws RecognitionException {
		TjoinPartContext _localctx = new TjoinPartContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_tjoinPart);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(77); fullName();
			setState(86);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==LEFT || _la==RIGHT) {
				{
				{
				setState(78);
				_la = _input.LA(1);
				if ( !(_la==LEFT || _la==RIGHT) ) {
				_errHandler.recoverInline(this);
				}
				consume();
				setState(79); match(JOIN);
				setState(80); fullName();
				setState(81); match(ON);
				setState(82); orCondition();
				}
				}
				setState(88);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class OrConditionContext extends ParserRuleContext {
		public OrNotContext operator;
		public List<AndConditionContext> andCondition() {
			return getRuleContexts(AndConditionContext.class);
		}
		public AndConditionContext andCondition(int i) {
			return getRuleContext(AndConditionContext.class,i);
		}
		public List<OrNotContext> orNot() {
			return getRuleContexts(OrNotContext.class);
		}
		public OrNotContext orNot(int i) {
			return getRuleContext(OrNotContext.class,i);
		}
		public OrConditionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_orCondition; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BitQListener ) ((BitQListener)listener).enterOrCondition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BitQListener ) ((BitQListener)listener).exitOrCondition(this);
		}
	}

	public final OrConditionContext orCondition() throws RecognitionException {
		OrConditionContext _localctx = new OrConditionContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_orCondition);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(89); andCondition();
			setState(95);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==OR) {
				{
				{
				setState(90); ((OrConditionContext)_localctx).operator = orNot();
				setState(91); andCondition();
				}
				}
				setState(97);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AndConditionContext extends ParserRuleContext {
		public AndNotContext operator;
		public ConditionElementContext conditionElement(int i) {
			return getRuleContext(ConditionElementContext.class,i);
		}
		public List<ConditionElementContext> conditionElement() {
			return getRuleContexts(ConditionElementContext.class);
		}
		public List<AndNotContext> andNot() {
			return getRuleContexts(AndNotContext.class);
		}
		public AndNotContext andNot(int i) {
			return getRuleContext(AndNotContext.class,i);
		}
		public AndConditionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_andCondition; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BitQListener ) ((BitQListener)listener).enterAndCondition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BitQListener ) ((BitQListener)listener).exitAndCondition(this);
		}
	}

	public final AndConditionContext andCondition() throws RecognitionException {
		AndConditionContext _localctx = new AndConditionContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_andCondition);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(98); conditionElement();
			setState(104);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==AND) {
				{
				{
				setState(99); ((AndConditionContext)_localctx).operator = andNot();
				setState(100); conditionElement();
				}
				}
				setState(106);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ConditionElementContext extends ParserRuleContext {
		public ConditionExprContext conditionExpr() {
			return getRuleContext(ConditionExprContext.class,0);
		}
		public GroupConditionContext groupCondition() {
			return getRuleContext(GroupConditionContext.class,0);
		}
		public ConditionElementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_conditionElement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BitQListener ) ((BitQListener)listener).enterConditionElement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BitQListener ) ((BitQListener)listener).exitConditionElement(this);
		}
	}

	public final ConditionElementContext conditionElement() throws RecognitionException {
		ConditionElementContext _localctx = new ConditionElementContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_conditionElement);
		try {
			setState(109);
			switch (_input.LA(1)) {
			case Brackets_L:
				enterOuterAlt(_localctx, 1);
				{
				setState(107); groupCondition();
				}
				break;
			case Phone_seach:
			case NAME:
				enterOuterAlt(_localctx, 2);
				{
				setState(108); conditionExpr();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class GroupConditionContext extends ParserRuleContext {
		public OrConditionContext orCondition() {
			return getRuleContext(OrConditionContext.class,0);
		}
		public GroupConditionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_groupCondition; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BitQListener ) ((BitQListener)listener).enterGroupCondition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BitQListener ) ((BitQListener)listener).exitGroupCondition(this);
		}
	}

	public final GroupConditionContext groupCondition() throws RecognitionException {
		GroupConditionContext _localctx = new GroupConditionContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_groupCondition);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(111); match(Brackets_L);
			setState(112); orCondition();
			setState(113); match(Brackets_R);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ConditionExprContext extends ParserRuleContext {
		public Token op;
		public TerminalNode Contains() { return getToken(BitQParser.Contains, 0); }
		public Phone_seachContext phone_seach() {
			return getRuleContext(Phone_seachContext.class,0);
		}
		public FullNameContext fullName() {
			return getRuleContext(FullNameContext.class,0);
		}
		public TerminalNode PositionMatch() { return getToken(BitQParser.PositionMatch, 0); }
		public ValuesContext values() {
			return getRuleContext(ValuesContext.class,0);
		}
		public ConditionExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_conditionExpr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BitQListener ) ((BitQListener)listener).enterConditionExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BitQListener ) ((BitQListener)listener).exitConditionExpr(this);
		}
	}

	public final ConditionExprContext conditionExpr() throws RecognitionException {
		ConditionExprContext _localctx = new ConditionExprContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_conditionExpr);
		int _la;
		try {
			setState(120);
			switch (_input.LA(1)) {
			case NAME:
				enterOuterAlt(_localctx, 1);
				{
				setState(115); fullName();
				setState(116);
				((ConditionExprContext)_localctx).op = _input.LT(1);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__7) | (1L << T__6) | (1L << T__4) | (1L << T__3) | (1L << T__1) | (1L << T__0) | (1L << PositionMatch) | (1L << Contains))) != 0)) ) {
					((ConditionExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(117); values();
				}
				break;
			case Phone_seach:
				enterOuterAlt(_localctx, 2);
				{
				setState(119); phone_seach();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ValuesContext extends ParserRuleContext {
		public TerminalNode NUM() { return getToken(BitQParser.NUM, 0); }
		public FullNameContext fullName() {
			return getRuleContext(FullNameContext.class,0);
		}
		public BoolTFContext boolTF() {
			return getRuleContext(BoolTFContext.class,0);
		}
		public TerminalNode STRING() { return getToken(BitQParser.STRING, 0); }
		public To_dateContext to_date() {
			return getRuleContext(To_dateContext.class,0);
		}
		public TerminalNode TransArrt() { return getToken(BitQParser.TransArrt, 0); }
		public ValuesContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_values; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BitQListener ) ((BitQListener)listener).enterValues(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BitQListener ) ((BitQListener)listener).exitValues(this);
		}
	}

	public final ValuesContext values() throws RecognitionException {
		ValuesContext _localctx = new ValuesContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_values);
		try {
			setState(128);
			switch (_input.LA(1)) {
			case TO_DATE:
				enterOuterAlt(_localctx, 1);
				{
				setState(122); to_date();
				}
				break;
			case STRING:
				enterOuterAlt(_localctx, 2);
				{
				setState(123); match(STRING);
				}
				break;
			case NUM:
				enterOuterAlt(_localctx, 3);
				{
				setState(124); match(NUM);
				}
				break;
			case NAME:
				enterOuterAlt(_localctx, 4);
				{
				setState(125); fullName();
				}
				break;
			case TRUE:
			case FALSE:
				enterOuterAlt(_localctx, 5);
				{
				setState(126); boolTF();
				}
				break;
			case TransArrt:
				enterOuterAlt(_localctx, 6);
				{
				setState(127); match(TransArrt);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class To_dateContext extends ParserRuleContext {
		public TerminalNode STRING(int i) {
			return getToken(BitQParser.STRING, i);
		}
		public TerminalNode COMMA() { return getToken(BitQParser.COMMA, 0); }
		public List<TerminalNode> STRING() { return getTokens(BitQParser.STRING); }
		public TerminalNode TO_DATE() { return getToken(BitQParser.TO_DATE, 0); }
		public To_dateContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_to_date; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BitQListener ) ((BitQListener)listener).enterTo_date(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BitQListener ) ((BitQListener)listener).exitTo_date(this);
		}
	}

	public final To_dateContext to_date() throws RecognitionException {
		To_dateContext _localctx = new To_dateContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_to_date);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(130); match(TO_DATE);
			setState(131); match(Brackets_L);
			setState(132); match(STRING);
			setState(133); match(COMMA);
			setState(134); match(STRING);
			setState(135); match(Brackets_R);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ResultContext extends ParserRuleContext {
		public FullNameContext fullName(int i) {
			return getRuleContext(FullNameContext.class,i);
		}
		public List<FullNameContext> fullName() {
			return getRuleContexts(FullNameContext.class);
		}
		public ResultContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_result; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BitQListener ) ((BitQListener)listener).enterResult(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BitQListener ) ((BitQListener)listener).exitResult(this);
		}
	}

	public final ResultContext result() throws RecognitionException {
		ResultContext _localctx = new ResultContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_result);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(137); fullName();
			setState(142);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(138); match(COMMA);
				setState(139); fullName();
				}
				}
				setState(144);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FullNameContext extends ParserRuleContext {
		public TerminalNode NAME(int i) {
			return getToken(BitQParser.NAME, i);
		}
		public List<TerminalNode> NAME() { return getTokens(BitQParser.NAME); }
		public FullNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_fullName; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BitQListener ) ((BitQListener)listener).enterFullName(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BitQListener ) ((BitQListener)listener).exitFullName(this);
		}
	}

	public final FullNameContext fullName() throws RecognitionException {
		FullNameContext _localctx = new FullNameContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_fullName);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(145); match(NAME);
			setState(150);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==DOT) {
				{
				{
				setState(146); match(DOT);
				setState(147); match(NAME);
				}
				}
				setState(152);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BoolTFContext extends ParserRuleContext {
		public TerminalNode FALSE() { return getToken(BitQParser.FALSE, 0); }
		public TerminalNode TRUE() { return getToken(BitQParser.TRUE, 0); }
		public BoolTFContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_boolTF; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BitQListener ) ((BitQListener)listener).enterBoolTF(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BitQListener ) ((BitQListener)listener).exitBoolTF(this);
		}
	}

	public final BoolTFContext boolTF() throws RecognitionException {
		BoolTFContext _localctx = new BoolTFContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_boolTF);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(153);
			_la = _input.LA(1);
			if ( !(_la==TRUE || _la==FALSE) ) {
			_errHandler.recoverInline(this);
			}
			consume();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class OrNotContext extends ParserRuleContext {
		public TerminalNode NOT() { return getToken(BitQParser.NOT, 0); }
		public TerminalNode OR() { return getToken(BitQParser.OR, 0); }
		public OrNotContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_orNot; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BitQListener ) ((BitQListener)listener).enterOrNot(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BitQListener ) ((BitQListener)listener).exitOrNot(this);
		}
	}

	public final OrNotContext orNot() throws RecognitionException {
		OrNotContext _localctx = new OrNotContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_orNot);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(155); match(OR);
			setState(157);
			_la = _input.LA(1);
			if (_la==NOT) {
				{
				setState(156); match(NOT);
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AndNotContext extends ParserRuleContext {
		public TerminalNode NOT() { return getToken(BitQParser.NOT, 0); }
		public TerminalNode AND() { return getToken(BitQParser.AND, 0); }
		public AndNotContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_andNot; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BitQListener ) ((BitQListener)listener).enterAndNot(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BitQListener ) ((BitQListener)listener).exitAndNot(this);
		}
	}

	public final AndNotContext andNot() throws RecognitionException {
		AndNotContext _localctx = new AndNotContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_andNot);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(159); match(AND);
			setState(161);
			_la = _input.LA(1);
			if (_la==NOT) {
				{
				setState(160); match(NOT);
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Phone_seachContext extends ParserRuleContext {
		public Token op;
		public TerminalNode Contains() { return getToken(BitQParser.Contains, 0); }
		public FullNameContext fullName() {
			return getRuleContext(FullNameContext.class,0);
		}
		public TerminalNode PositionMatch() { return getToken(BitQParser.PositionMatch, 0); }
		public TerminalNode STRING() { return getToken(BitQParser.STRING, 0); }
		public TerminalNode Has_Every_Char() { return getToken(BitQParser.Has_Every_Char, 0); }
		public TerminalNode Phone_seach() { return getToken(BitQParser.Phone_seach, 0); }
		public Phone_seachContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_phone_seach; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BitQListener ) ((BitQListener)listener).enterPhone_seach(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BitQListener ) ((BitQListener)listener).exitPhone_seach(this);
		}
	}

	public final Phone_seachContext phone_seach() throws RecognitionException {
		Phone_seachContext _localctx = new Phone_seachContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_phone_seach);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(163); match(Phone_seach);
			setState(164); match(Brackets_L);
			setState(165); fullName();
			setState(166); match(COMMA);
			setState(167);
			((Phone_seachContext)_localctx).op = _input.LT(1);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << PositionMatch) | (1L << Contains) | (1L << Has_Every_Char))) != 0)) ) {
				((Phone_seachContext)_localctx).op = (Token)_errHandler.recoverInline(this);
			}
			consume();
			setState(168); match(COMMA);
			setState(169); match(STRING);
			setState(170); match(Brackets_R);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SortEContext extends ParserRuleContext {
		public TerminalNode ASC() { return getToken(BitQParser.ASC, 0); }
		public FullNameContext fullName() {
			return getRuleContext(FullNameContext.class,0);
		}
		public TerminalNode STRING() { return getToken(BitQParser.STRING, 0); }
		public TerminalNode DESC() { return getToken(BitQParser.DESC, 0); }
		public SortEContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_sortE; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BitQListener ) ((BitQListener)listener).enterSortE(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BitQListener ) ((BitQListener)listener).exitSortE(this);
		}
	}

	public final SortEContext sortE() throws RecognitionException {
		SortEContext _localctx = new SortEContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_sortE);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(172); fullName();
			setState(176);
			_la = _input.LA(1);
			if (_la==T__5) {
				{
				setState(173); match(T__5);
				setState(174); match(STRING);
				setState(175); match(T__2);
				}
			}

			setState(179);
			_la = _input.LA(1);
			if (_la==DESC || _la==ASC) {
				{
				setState(178);
				_la = _input.LA(1);
				if ( !(_la==DESC || _la==ASC) ) {
				_errHandler.recoverInline(this);
				}
				consume();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SortByContext extends ParserRuleContext {
		public TerminalNode Order() { return getToken(BitQParser.Order, 0); }
		public TerminalNode By() { return getToken(BitQParser.By, 0); }
		public List<SortEContext> sortE() {
			return getRuleContexts(SortEContext.class);
		}
		public SortEContext sortE(int i) {
			return getRuleContext(SortEContext.class,i);
		}
		public SortByContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_sortBy; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BitQListener ) ((BitQListener)listener).enterSortBy(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BitQListener ) ((BitQListener)listener).exitSortBy(this);
		}
	}

	public final SortByContext sortBy() throws RecognitionException {
		SortByContext _localctx = new SortByContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_sortBy);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(181); match(Order);
			setState(182); match(By);
			setState(183); sortE();
			setState(188);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(184); match(COMMA);
				setState(185); sortE();
				}
				}
				setState(190);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LimitContext extends ParserRuleContext {
		public TerminalNode LIMIT() { return getToken(BitQParser.LIMIT, 0); }
		public List<TerminalNode> NUM() { return getTokens(BitQParser.NUM); }
		public TerminalNode NUM(int i) {
			return getToken(BitQParser.NUM, i);
		}
		public LimitContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_limit; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BitQListener ) ((BitQListener)listener).enterLimit(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BitQListener ) ((BitQListener)listener).exitLimit(this);
		}
	}

	public final LimitContext limit() throws RecognitionException {
		LimitContext _localctx = new LimitContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_limit);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(191); match(LIMIT);
			setState(192); match(NUM);
			setState(195);
			_la = _input.LA(1);
			if (_la==COMMA) {
				{
				setState(193); match(COMMA);
				setState(194); match(NUM);
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class MixContext extends ParserRuleContext {
		public FullNameContext fullName() {
			return getRuleContext(FullNameContext.class,0);
		}
		public List<TerminalNode> NUM() { return getTokens(BitQParser.NUM); }
		public TerminalNode NUM(int i) {
			return getToken(BitQParser.NUM, i);
		}
		public TerminalNode MIX() { return getToken(BitQParser.MIX, 0); }
		public MixContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_mix; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BitQListener ) ((BitQListener)listener).enterMix(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BitQListener ) ((BitQListener)listener).exitMix(this);
		}
	}

	public final MixContext mix() throws RecognitionException {
		MixContext _localctx = new MixContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_mix);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(197); match(MIX);
			setState(198); fullName();
			setState(209);
			_la = _input.LA(1);
			if (_la==T__5) {
				{
				setState(199); match(T__5);
				setState(200); match(NUM);
				setState(205);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==COMMA) {
					{
					{
					setState(201); match(COMMA);
					setState(202); match(NUM);
					}
					}
					setState(207);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(208); match(T__2);
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3,\u00d6\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\3\2\3\2\3\3\3\3\3\3"+
		"\3\3\3\3\3\3\3\3\5\38\n\3\3\3\5\3;\n\3\3\3\5\3>\n\3\3\3\3\3\5\3B\n\3\3"+
		"\4\3\4\3\4\7\4G\n\4\f\4\16\4J\13\4\3\5\3\5\5\5N\n\5\3\6\3\6\3\6\3\6\3"+
		"\6\3\6\3\6\7\6W\n\6\f\6\16\6Z\13\6\3\7\3\7\3\7\3\7\7\7`\n\7\f\7\16\7c"+
		"\13\7\3\b\3\b\3\b\3\b\7\bi\n\b\f\b\16\bl\13\b\3\t\3\t\5\tp\n\t\3\n\3\n"+
		"\3\n\3\n\3\13\3\13\3\13\3\13\3\13\5\13{\n\13\3\f\3\f\3\f\3\f\3\f\3\f\5"+
		"\f\u0083\n\f\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\16\3\16\3\16\7\16\u008f\n\16"+
		"\f\16\16\16\u0092\13\16\3\17\3\17\3\17\7\17\u0097\n\17\f\17\16\17\u009a"+
		"\13\17\3\20\3\20\3\21\3\21\5\21\u00a0\n\21\3\22\3\22\5\22\u00a4\n\22\3"+
		"\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\24\3\24\3\24\3\24\5\24\u00b3"+
		"\n\24\3\24\5\24\u00b6\n\24\3\25\3\25\3\25\3\25\3\25\7\25\u00bd\n\25\f"+
		"\25\16\25\u00c0\13\25\3\26\3\26\3\26\3\26\5\26\u00c6\n\26\3\27\3\27\3"+
		"\27\3\27\3\27\3\27\7\27\u00ce\n\27\f\27\16\27\u00d1\13\27\3\27\5\27\u00d4"+
		"\n\27\3\27\2\2\30\2\4\6\b\n\f\16\20\22\24\26\30\32\34\36 \"$&(*,\2\7\3"+
		"\2\27\30\6\2\3\4\6\7\t\n\20\21\3\2*+\3\2\20\22\3\2\r\16\u00d9\2.\3\2\2"+
		"\2\4\60\3\2\2\2\6C\3\2\2\2\bM\3\2\2\2\nO\3\2\2\2\f[\3\2\2\2\16d\3\2\2"+
		"\2\20o\3\2\2\2\22q\3\2\2\2\24z\3\2\2\2\26\u0082\3\2\2\2\30\u0084\3\2\2"+
		"\2\32\u008b\3\2\2\2\34\u0093\3\2\2\2\36\u009b\3\2\2\2 \u009d\3\2\2\2\""+
		"\u00a1\3\2\2\2$\u00a5\3\2\2\2&\u00ae\3\2\2\2(\u00b7\3\2\2\2*\u00c1\3\2"+
		"\2\2,\u00c7\3\2\2\2./\5\4\3\2/\3\3\2\2\2\60\61\7\23\2\2\61\62\5\32\16"+
		"\2\62\63\7\24\2\2\63A\5\6\4\2\64\65\7\25\2\2\65\67\5\f\7\2\668\5(\25\2"+
		"\67\66\3\2\2\2\678\3\2\2\28:\3\2\2\29;\5,\27\2:9\3\2\2\2:;\3\2\2\2;=\3"+
		"\2\2\2<>\5*\26\2=<\3\2\2\2=>\3\2\2\2>?\3\2\2\2?@\7 \2\2@B\3\2\2\2A\64"+
		"\3\2\2\2AB\3\2\2\2B\5\3\2\2\2CH\5\34\17\2DE\7\37\2\2EG\5\34\17\2FD\3\2"+
		"\2\2GJ\3\2\2\2HF\3\2\2\2HI\3\2\2\2I\7\3\2\2\2JH\3\2\2\2KN\5\n\6\2LN\5"+
		"\34\17\2MK\3\2\2\2ML\3\2\2\2N\t\3\2\2\2OX\5\34\17\2PQ\t\2\2\2QR\7\31\2"+
		"\2RS\5\34\17\2ST\7\26\2\2TU\5\f\7\2UW\3\2\2\2VP\3\2\2\2WZ\3\2\2\2XV\3"+
		"\2\2\2XY\3\2\2\2Y\13\3\2\2\2ZX\3\2\2\2[a\5\16\b\2\\]\5 \21\2]^\5\16\b"+
		"\2^`\3\2\2\2_\\\3\2\2\2`c\3\2\2\2a_\3\2\2\2ab\3\2\2\2b\r\3\2\2\2ca\3\2"+
		"\2\2dj\5\20\t\2ef\5\"\22\2fg\5\20\t\2gi\3\2\2\2he\3\2\2\2il\3\2\2\2jh"+
		"\3\2\2\2jk\3\2\2\2k\17\3\2\2\2lj\3\2\2\2mp\5\22\n\2np\5\24\13\2om\3\2"+
		"\2\2on\3\2\2\2p\21\3\2\2\2qr\7&\2\2rs\5\f\7\2st\7\'\2\2t\23\3\2\2\2uv"+
		"\5\34\17\2vw\t\3\2\2wx\5\26\f\2x{\3\2\2\2y{\5$\23\2zu\3\2\2\2zy\3\2\2"+
		"\2{\25\3\2\2\2|\u0083\5\30\r\2}\u0083\7(\2\2~\u0083\7)\2\2\177\u0083\5"+
		"\34\17\2\u0080\u0083\5\36\20\2\u0081\u0083\7\13\2\2\u0082|\3\2\2\2\u0082"+
		"}\3\2\2\2\u0082~\3\2\2\2\u0082\177\3\2\2\2\u0082\u0080\3\2\2\2\u0082\u0081"+
		"\3\2\2\2\u0083\27\3\2\2\2\u0084\u0085\7\35\2\2\u0085\u0086\7&\2\2\u0086"+
		"\u0087\7(\2\2\u0087\u0088\7\37\2\2\u0088\u0089\7(\2\2\u0089\u008a\7\'"+
		"\2\2\u008a\31\3\2\2\2\u008b\u0090\5\34\17\2\u008c\u008d\7\37\2\2\u008d"+
		"\u008f\5\34\17\2\u008e\u008c\3\2\2\2\u008f\u0092\3\2\2\2\u0090\u008e\3"+
		"\2\2\2\u0090\u0091\3\2\2\2\u0091\33\3\2\2\2\u0092\u0090\3\2\2\2\u0093"+
		"\u0098\7$\2\2\u0094\u0095\7%\2\2\u0095\u0097\7$\2\2\u0096\u0094\3\2\2"+
		"\2\u0097\u009a\3\2\2\2\u0098\u0096\3\2\2\2\u0098\u0099\3\2\2\2\u0099\35"+
		"\3\2\2\2\u009a\u0098\3\2\2\2\u009b\u009c\t\4\2\2\u009c\37\3\2\2\2\u009d"+
		"\u009f\7\33\2\2\u009e\u00a0\7\34\2\2\u009f\u009e\3\2\2\2\u009f\u00a0\3"+
		"\2\2\2\u00a0!\3\2\2\2\u00a1\u00a3\7\32\2\2\u00a2\u00a4\7\34\2\2\u00a3"+
		"\u00a2\3\2\2\2\u00a3\u00a4\3\2\2\2\u00a4#\3\2\2\2\u00a5\u00a6\7\17\2\2"+
		"\u00a6\u00a7\7&\2\2\u00a7\u00a8\5\34\17\2\u00a8\u00a9\7\37\2\2\u00a9\u00aa"+
		"\t\5\2\2\u00aa\u00ab\7\37\2\2\u00ab\u00ac\7(\2\2\u00ac\u00ad\7\'\2\2\u00ad"+
		"%\3\2\2\2\u00ae\u00b2\5\34\17\2\u00af\u00b0\7\5\2\2\u00b0\u00b1\7(\2\2"+
		"\u00b1\u00b3\7\b\2\2\u00b2\u00af\3\2\2\2\u00b2\u00b3\3\2\2\2\u00b3\u00b5"+
		"\3\2\2\2\u00b4\u00b6\t\6\2\2\u00b5\u00b4\3\2\2\2\u00b5\u00b6\3\2\2\2\u00b6"+
		"\'\3\2\2\2\u00b7\u00b8\7\"\2\2\u00b8\u00b9\7#\2\2\u00b9\u00be\5&\24\2"+
		"\u00ba\u00bb\7\37\2\2\u00bb\u00bd\5&\24\2\u00bc\u00ba\3\2\2\2\u00bd\u00c0"+
		"\3\2\2\2\u00be\u00bc\3\2\2\2\u00be\u00bf\3\2\2\2\u00bf)\3\2\2\2\u00c0"+
		"\u00be\3\2\2\2\u00c1\u00c2\7!\2\2\u00c2\u00c5\7)\2\2\u00c3\u00c4\7\37"+
		"\2\2\u00c4\u00c6\7)\2\2\u00c5\u00c3\3\2\2\2\u00c5\u00c6\3\2\2\2\u00c6"+
		"+\3\2\2\2\u00c7\u00c8\7\f\2\2\u00c8\u00d3\5\34\17\2\u00c9\u00ca\7\5\2"+
		"\2\u00ca\u00cf\7)\2\2\u00cb\u00cc\7\37\2\2\u00cc\u00ce\7)\2\2\u00cd\u00cb"+
		"\3\2\2\2\u00ce\u00d1\3\2\2\2\u00cf\u00cd\3\2\2\2\u00cf\u00d0\3\2\2\2\u00d0"+
		"\u00d2\3\2\2\2\u00d1\u00cf\3\2\2\2\u00d2\u00d4\7\b\2\2\u00d3\u00c9\3\2"+
		"\2\2\u00d3\u00d4\3\2\2\2\u00d4-\3\2\2\2\30\67:=AHMXajoz\u0082\u0090\u0098"+
		"\u009f\u00a3\u00b2\u00b5\u00be\u00c5\u00cf\u00d3";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}