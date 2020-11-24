// Generated from BitQ.g4 by ANTLR 4.7.2

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
	static { RuntimeMetaData.checkVersion("4.7.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, TransArrt=9, 
		SAVE=10, AS=11, MIX=12, DESC=13, ASC=14, Phone_seach=15, PositionMatch=16, 
		Contains=17, Has_Every_Char=18, SELECT=19, FROM=20, WHERE=21, ON=22, LEFT=23, 
		RIGHT=24, JOIN=25, AND=26, OR=27, NOT=28, TO_DATE=29, ExprNot=30, COMMA=31, 
		SEMI=32, LIMIT=33, Order=34, By=35, NAME=36, DOT=37, Brackets_L=38, Brackets_R=39, 
		STRING=40, NUM=41, TRUE=42, FALSE=43, WS=44, SQL_COMMENT=45;
	public static final int
		RULE_query = 0, RULE_expr = 1, RULE_repo = 2, RULE_saveAsFile = 3, RULE_tablePart = 4, 
		RULE_tjoinPart = 5, RULE_orCondition = 6, RULE_andCondition = 7, RULE_conditionElement = 8, 
		RULE_groupCondition = 9, RULE_conditionExpr = 10, RULE_values = 11, RULE_to_date = 12, 
		RULE_result = 13, RULE_fullName = 14, RULE_boolTF = 15, RULE_orNot = 16, 
		RULE_andNot = 17, RULE_phone_seach = 18, RULE_sortE = 19, RULE_sortBy = 20, 
		RULE_limit = 21, RULE_mix = 22;
	private static String[] makeRuleNames() {
		return new String[] {
			"query", "expr", "repo", "saveAsFile", "tablePart", "tjoinPart", "orCondition", 
			"andCondition", "conditionElement", "groupCondition", "conditionExpr", 
			"values", "to_date", "result", "fullName", "boolTF", "orNot", "andNot", 
			"phone_seach", "sortE", "sortBy", "limit", "mix"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'='", "'!='", "'>='", "'>'", "'<='", "'<'", "'['", "']'", null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, "'!'", "','", "';'", 
			null, null, null, null, "'.'", "'('", "')'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, "TransArrt", "SAVE", 
			"AS", "MIX", "DESC", "ASC", "Phone_seach", "PositionMatch", "Contains", 
			"Has_Every_Char", "SELECT", "FROM", "WHERE", "ON", "LEFT", "RIGHT", "JOIN", 
			"AND", "OR", "NOT", "TO_DATE", "ExprNot", "COMMA", "SEMI", "LIMIT", "Order", 
			"By", "NAME", "DOT", "Brackets_L", "Brackets_R", "STRING", "NUM", "TRUE", 
			"FALSE", "WS", "SQL_COMMENT"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "BitQ.g4"; }

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
			setState(46);
			expr();
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
		public TerminalNode SELECT() { return getToken(BitQParser.SELECT, 0); }
		public ResultContext result() {
			return getRuleContext(ResultContext.class,0);
		}
		public TerminalNode FROM() { return getToken(BitQParser.FROM, 0); }
		public RepoContext repo() {
			return getRuleContext(RepoContext.class,0);
		}
		public TerminalNode WHERE() { return getToken(BitQParser.WHERE, 0); }
		public OrConditionContext orCondition() {
			return getRuleContext(OrConditionContext.class,0);
		}
		public SortByContext sortBy() {
			return getRuleContext(SortByContext.class,0);
		}
		public MixContext mix() {
			return getRuleContext(MixContext.class,0);
		}
		public LimitContext limit() {
			return getRuleContext(LimitContext.class,0);
		}
		public SaveAsFileContext saveAsFile() {
			return getRuleContext(SaveAsFileContext.class,0);
		}
		public TerminalNode SEMI() { return getToken(BitQParser.SEMI, 0); }
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

	@Override
	public Token match(int ttype) throws RecognitionException {
		Token t = getCurrentToken();
		if ( t.getType()==ttype ) {
			if ( ttype==Token.EOF ) {
				matchedEOF = true;
			}
			_errHandler.reportMatch(this);
			consume();
			return t;
		}
		else {
			throw new RuntimeException("miss "+ _SYMBOLIC_NAMES[ttype]);
		}
		
	}
	
	public final ExprContext expr() throws RecognitionException {
		ExprContext _localctx = new ExprContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_expr);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(48);
			match(SELECT);
			setState(49);
			result();
			setState(50);
			match(FROM);
			setState(51);
			repo();
			setState(54);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==WHERE) {
				{
				setState(52);
				match(WHERE);
				setState(53);
				orCondition();
				}
			}

			setState(57);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Order) {
				{
				setState(56);
				sortBy();
				}
			}

			setState(60);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==MIX) {
				{
				setState(59);
				mix();
				}
			}

			setState(63);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LIMIT) {
				{
				setState(62);
				limit();
				}
			}

			setState(66);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==SAVE) {
				{
				setState(65);
				saveAsFile();
				}
			}

			setState(69);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==SEMI) {
				{
				setState(68);
				match(SEMI);
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
		public List<FullNameContext> fullName() {
			return getRuleContexts(FullNameContext.class);
		}
		public FullNameContext fullName(int i) {
			return getRuleContext(FullNameContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(BitQParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(BitQParser.COMMA, i);
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
			setState(71);
			fullName();
			setState(76);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(72);
				match(COMMA);
				setState(73);
				fullName();
				}
				}
				setState(78);
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

	public static class SaveAsFileContext extends ParserRuleContext {
		public TerminalNode SAVE() { return getToken(BitQParser.SAVE, 0); }
		public TerminalNode AS() { return getToken(BitQParser.AS, 0); }
		public TerminalNode STRING() { return getToken(BitQParser.STRING, 0); }
		public SaveAsFileContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_saveAsFile; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BitQListener ) ((BitQListener)listener).enterSaveAsFile(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BitQListener ) ((BitQListener)listener).exitSaveAsFile(this);
		}
	}

	public final SaveAsFileContext saveAsFile() throws RecognitionException {
		SaveAsFileContext _localctx = new SaveAsFileContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_saveAsFile);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(79);
			match(SAVE);
			setState(80);
			match(AS);
			setState(81);
			match(STRING);
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
		public TjoinPartContext tjoinPart() {
			return getRuleContext(TjoinPartContext.class,0);
		}
		public FullNameContext fullName() {
			return getRuleContext(FullNameContext.class,0);
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
		enterRule(_localctx, 8, RULE_tablePart);
		try {
			setState(85);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,7,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(83);
				tjoinPart();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(84);
				fullName();
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
		public List<FullNameContext> fullName() {
			return getRuleContexts(FullNameContext.class);
		}
		public FullNameContext fullName(int i) {
			return getRuleContext(FullNameContext.class,i);
		}
		public List<TerminalNode> JOIN() { return getTokens(BitQParser.JOIN); }
		public TerminalNode JOIN(int i) {
			return getToken(BitQParser.JOIN, i);
		}
		public List<TerminalNode> ON() { return getTokens(BitQParser.ON); }
		public TerminalNode ON(int i) {
			return getToken(BitQParser.ON, i);
		}
		public List<OrConditionContext> orCondition() {
			return getRuleContexts(OrConditionContext.class);
		}
		public OrConditionContext orCondition(int i) {
			return getRuleContext(OrConditionContext.class,i);
		}
		public List<TerminalNode> LEFT() { return getTokens(BitQParser.LEFT); }
		public TerminalNode LEFT(int i) {
			return getToken(BitQParser.LEFT, i);
		}
		public List<TerminalNode> RIGHT() { return getTokens(BitQParser.RIGHT); }
		public TerminalNode RIGHT(int i) {
			return getToken(BitQParser.RIGHT, i);
		}
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
		enterRule(_localctx, 10, RULE_tjoinPart);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(87);
			fullName();
			setState(96);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==LEFT || _la==RIGHT) {
				{
				{
				setState(88);
				_la = _input.LA(1);
				if ( !(_la==LEFT || _la==RIGHT) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(89);
				match(JOIN);
				setState(90);
				fullName();
				setState(91);
				match(ON);
				setState(92);
				orCondition();
				}
				}
				setState(98);
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
		public TerminalNode NOT() { return getToken(BitQParser.NOT, 0); }
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
		enterRule(_localctx, 12, RULE_orCondition);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(100);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==NOT) {
				{
				setState(99);
				match(NOT);
				}
			}

			setState(102);
			andCondition();
			setState(108);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==OR) {
				{
				{
				setState(103);
				((OrConditionContext)_localctx).operator = orNot();
				setState(104);
				andCondition();
				}
				}
				setState(110);
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
		public List<ConditionElementContext> conditionElement() {
			return getRuleContexts(ConditionElementContext.class);
		}
		public ConditionElementContext conditionElement(int i) {
			return getRuleContext(ConditionElementContext.class,i);
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
		enterRule(_localctx, 14, RULE_andCondition);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(111);
			conditionElement();
			setState(117);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==AND) {
				{
				{
				setState(112);
				((AndConditionContext)_localctx).operator = andNot();
				setState(113);
				conditionElement();
				}
				}
				setState(119);
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
		public GroupConditionContext groupCondition() {
			return getRuleContext(GroupConditionContext.class,0);
		}
		public ConditionExprContext conditionExpr() {
			return getRuleContext(ConditionExprContext.class,0);
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
		enterRule(_localctx, 16, RULE_conditionElement);
		try {
			setState(122);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case Brackets_L:
				enterOuterAlt(_localctx, 1);
				{
				setState(120);
				groupCondition();
				}
				break;
			case Phone_seach:
			case NAME:
				enterOuterAlt(_localctx, 2);
				{
				setState(121);
				conditionExpr();
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
		public TerminalNode Brackets_L() { return getToken(BitQParser.Brackets_L, 0); }
		public OrConditionContext orCondition() {
			return getRuleContext(OrConditionContext.class,0);
		}
		public TerminalNode Brackets_R() { return getToken(BitQParser.Brackets_R, 0); }
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
		enterRule(_localctx, 18, RULE_groupCondition);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(124);
			match(Brackets_L);
			setState(125);
			orCondition();
			setState(126);
			match(Brackets_R);
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
		public FullNameContext fullName() {
			return getRuleContext(FullNameContext.class,0);
		}
		public ValuesContext values() {
			return getRuleContext(ValuesContext.class,0);
		}
		public TerminalNode Contains() { return getToken(BitQParser.Contains, 0); }
		public TerminalNode PositionMatch() { return getToken(BitQParser.PositionMatch, 0); }
		public Phone_seachContext phone_seach() {
			return getRuleContext(Phone_seachContext.class,0);
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
		enterRule(_localctx, 20, RULE_conditionExpr);
		int _la;
		try {
			setState(133);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case NAME:
				enterOuterAlt(_localctx, 1);
				{
				setState(128);
				fullName();
				setState(129);
				((ConditionExprContext)_localctx).op = _input.LT(1);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << T__1) | (1L << T__2) | (1L << T__3) | (1L << T__4) | (1L << T__5) | (1L << PositionMatch) | (1L << Contains))) != 0)) ) {
					((ConditionExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(130);
				values();
				}
				break;
			case Phone_seach:
				enterOuterAlt(_localctx, 2);
				{
				setState(132);
				phone_seach();
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
		public To_dateContext to_date() {
			return getRuleContext(To_dateContext.class,0);
		}
		public TerminalNode STRING() { return getToken(BitQParser.STRING, 0); }
		public TerminalNode NUM() { return getToken(BitQParser.NUM, 0); }
		public FullNameContext fullName() {
			return getRuleContext(FullNameContext.class,0);
		}
		public BoolTFContext boolTF() {
			return getRuleContext(BoolTFContext.class,0);
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
		enterRule(_localctx, 22, RULE_values);
		try {
			setState(141);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case TO_DATE:
				enterOuterAlt(_localctx, 1);
				{
				setState(135);
				to_date();
				}
				break;
			case STRING:
				enterOuterAlt(_localctx, 2);
				{
				setState(136);
				match(STRING);
				}
				break;
			case NUM:
				enterOuterAlt(_localctx, 3);
				{
				setState(137);
				match(NUM);
				}
				break;
			case NAME:
				enterOuterAlt(_localctx, 4);
				{
				setState(138);
				fullName();
				}
				break;
			case TRUE:
			case FALSE:
				enterOuterAlt(_localctx, 5);
				{
				setState(139);
				boolTF();
				}
				break;
			case TransArrt:
				enterOuterAlt(_localctx, 6);
				{
				setState(140);
				match(TransArrt);
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
		public TerminalNode TO_DATE() { return getToken(BitQParser.TO_DATE, 0); }
		public TerminalNode Brackets_L() { return getToken(BitQParser.Brackets_L, 0); }
		public List<TerminalNode> STRING() { return getTokens(BitQParser.STRING); }
		public TerminalNode STRING(int i) {
			return getToken(BitQParser.STRING, i);
		}
		public TerminalNode COMMA() { return getToken(BitQParser.COMMA, 0); }
		public TerminalNode Brackets_R() { return getToken(BitQParser.Brackets_R, 0); }
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
		enterRule(_localctx, 24, RULE_to_date);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(143);
			match(TO_DATE);
			setState(144);
			match(Brackets_L);
			setState(145);
			match(STRING);
			setState(146);
			match(COMMA);
			setState(147);
			match(STRING);
			setState(148);
			match(Brackets_R);
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
		public List<FullNameContext> fullName() {
			return getRuleContexts(FullNameContext.class);
		}
		public FullNameContext fullName(int i) {
			return getRuleContext(FullNameContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(BitQParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(BitQParser.COMMA, i);
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
		enterRule(_localctx, 26, RULE_result);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(150);
			fullName();
			setState(155);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(151);
				match(COMMA);
				setState(152);
				fullName();
				}
				}
				setState(157);
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
		public List<TerminalNode> NAME() { return getTokens(BitQParser.NAME); }
		public TerminalNode NAME(int i) {
			return getToken(BitQParser.NAME, i);
		}
		public List<TerminalNode> DOT() { return getTokens(BitQParser.DOT); }
		public TerminalNode DOT(int i) {
			return getToken(BitQParser.DOT, i);
		}
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
		enterRule(_localctx, 28, RULE_fullName);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(158);
			match(NAME);
			setState(163);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==DOT) {
				{
				{
				setState(159);
				match(DOT);
				setState(160);
				match(NAME);
				}
				}
				setState(165);
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
		public TerminalNode TRUE() { return getToken(BitQParser.TRUE, 0); }
		public TerminalNode FALSE() { return getToken(BitQParser.FALSE, 0); }
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
		enterRule(_localctx, 30, RULE_boolTF);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(166);
			_la = _input.LA(1);
			if ( !(_la==TRUE || _la==FALSE) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
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

	public static class OrNotContext extends ParserRuleContext {
		public TerminalNode OR() { return getToken(BitQParser.OR, 0); }
		public TerminalNode NOT() { return getToken(BitQParser.NOT, 0); }
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
		enterRule(_localctx, 32, RULE_orNot);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(168);
			match(OR);
			setState(170);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==NOT) {
				{
				setState(169);
				match(NOT);
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
		public TerminalNode AND() { return getToken(BitQParser.AND, 0); }
		public TerminalNode NOT() { return getToken(BitQParser.NOT, 0); }
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
		enterRule(_localctx, 34, RULE_andNot);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(172);
			match(AND);
			setState(174);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==NOT) {
				{
				setState(173);
				match(NOT);
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
		public TerminalNode Phone_seach() { return getToken(BitQParser.Phone_seach, 0); }
		public TerminalNode Brackets_L() { return getToken(BitQParser.Brackets_L, 0); }
		public FullNameContext fullName() {
			return getRuleContext(FullNameContext.class,0);
		}
		public List<TerminalNode> COMMA() { return getTokens(BitQParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(BitQParser.COMMA, i);
		}
		public TerminalNode STRING() { return getToken(BitQParser.STRING, 0); }
		public TerminalNode Brackets_R() { return getToken(BitQParser.Brackets_R, 0); }
		public TerminalNode PositionMatch() { return getToken(BitQParser.PositionMatch, 0); }
		public TerminalNode Contains() { return getToken(BitQParser.Contains, 0); }
		public TerminalNode Has_Every_Char() { return getToken(BitQParser.Has_Every_Char, 0); }
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
		enterRule(_localctx, 36, RULE_phone_seach);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(176);
			match(Phone_seach);
			setState(177);
			match(Brackets_L);
			setState(178);
			fullName();
			setState(179);
			match(COMMA);
			setState(180);
			((Phone_seachContext)_localctx).op = _input.LT(1);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << PositionMatch) | (1L << Contains) | (1L << Has_Every_Char))) != 0)) ) {
				((Phone_seachContext)_localctx).op = (Token)_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			setState(181);
			match(COMMA);
			setState(182);
			match(STRING);
			setState(183);
			match(Brackets_R);
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
		public FullNameContext fullName() {
			return getRuleContext(FullNameContext.class,0);
		}
		public TerminalNode STRING() { return getToken(BitQParser.STRING, 0); }
		public TerminalNode DESC() { return getToken(BitQParser.DESC, 0); }
		public TerminalNode ASC() { return getToken(BitQParser.ASC, 0); }
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
		enterRule(_localctx, 38, RULE_sortE);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(185);
			fullName();
			setState(189);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__6) {
				{
				setState(186);
				match(T__6);
				setState(187);
				match(STRING);
				setState(188);
				match(T__7);
				}
			}

			setState(192);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==DESC || _la==ASC) {
				{
				setState(191);
				_la = _input.LA(1);
				if ( !(_la==DESC || _la==ASC) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
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
		public List<TerminalNode> COMMA() { return getTokens(BitQParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(BitQParser.COMMA, i);
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
		enterRule(_localctx, 40, RULE_sortBy);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(194);
			match(Order);
			setState(195);
			match(By);
			setState(196);
			sortE();
			setState(201);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(197);
				match(COMMA);
				setState(198);
				sortE();
				}
				}
				setState(203);
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
		public TerminalNode COMMA() { return getToken(BitQParser.COMMA, 0); }
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
		enterRule(_localctx, 42, RULE_limit);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(204);
			match(LIMIT);
			setState(205);
			match(NUM);
			setState(208);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==COMMA) {
				{
				setState(206);
				match(COMMA);
				setState(207);
				match(NUM);
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
		public TerminalNode MIX() { return getToken(BitQParser.MIX, 0); }
		public FullNameContext fullName() {
			return getRuleContext(FullNameContext.class,0);
		}
		public List<TerminalNode> NUM() { return getTokens(BitQParser.NUM); }
		public TerminalNode NUM(int i) {
			return getToken(BitQParser.NUM, i);
		}
		public List<TerminalNode> COMMA() { return getTokens(BitQParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(BitQParser.COMMA, i);
		}
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
		enterRule(_localctx, 44, RULE_mix);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(210);
			match(MIX);
			setState(211);
			fullName();
			setState(222);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__6) {
				{
				setState(212);
				match(T__6);
				setState(213);
				match(NUM);
				setState(218);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==COMMA) {
					{
					{
					setState(214);
					match(COMMA);
					setState(215);
					match(NUM);
					}
					}
					setState(220);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(221);
				match(T__7);
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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3/\u00e3\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\3\2\3\2\3"+
		"\3\3\3\3\3\3\3\3\3\3\3\5\39\n\3\3\3\5\3<\n\3\3\3\5\3?\n\3\3\3\5\3B\n\3"+
		"\3\3\5\3E\n\3\3\3\5\3H\n\3\3\4\3\4\3\4\7\4M\n\4\f\4\16\4P\13\4\3\5\3\5"+
		"\3\5\3\5\3\6\3\6\5\6X\n\6\3\7\3\7\3\7\3\7\3\7\3\7\3\7\7\7a\n\7\f\7\16"+
		"\7d\13\7\3\b\5\bg\n\b\3\b\3\b\3\b\3\b\7\bm\n\b\f\b\16\bp\13\b\3\t\3\t"+
		"\3\t\3\t\7\tv\n\t\f\t\16\ty\13\t\3\n\3\n\5\n}\n\n\3\13\3\13\3\13\3\13"+
		"\3\f\3\f\3\f\3\f\3\f\5\f\u0088\n\f\3\r\3\r\3\r\3\r\3\r\3\r\5\r\u0090\n"+
		"\r\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\17\3\17\3\17\7\17\u009c\n\17\f"+
		"\17\16\17\u009f\13\17\3\20\3\20\3\20\7\20\u00a4\n\20\f\20\16\20\u00a7"+
		"\13\20\3\21\3\21\3\22\3\22\5\22\u00ad\n\22\3\23\3\23\5\23\u00b1\n\23\3"+
		"\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\25\3\25\3\25\3\25\5\25\u00c0"+
		"\n\25\3\25\5\25\u00c3\n\25\3\26\3\26\3\26\3\26\3\26\7\26\u00ca\n\26\f"+
		"\26\16\26\u00cd\13\26\3\27\3\27\3\27\3\27\5\27\u00d3\n\27\3\30\3\30\3"+
		"\30\3\30\3\30\3\30\7\30\u00db\n\30\f\30\16\30\u00de\13\30\3\30\5\30\u00e1"+
		"\n\30\3\30\2\2\31\2\4\6\b\n\f\16\20\22\24\26\30\32\34\36 \"$&(*,.\2\7"+
		"\3\2\31\32\4\2\3\b\22\23\3\2,-\3\2\22\24\3\2\17\20\2\u00e8\2\60\3\2\2"+
		"\2\4\62\3\2\2\2\6I\3\2\2\2\bQ\3\2\2\2\nW\3\2\2\2\fY\3\2\2\2\16f\3\2\2"+
		"\2\20q\3\2\2\2\22|\3\2\2\2\24~\3\2\2\2\26\u0087\3\2\2\2\30\u008f\3\2\2"+
		"\2\32\u0091\3\2\2\2\34\u0098\3\2\2\2\36\u00a0\3\2\2\2 \u00a8\3\2\2\2\""+
		"\u00aa\3\2\2\2$\u00ae\3\2\2\2&\u00b2\3\2\2\2(\u00bb\3\2\2\2*\u00c4\3\2"+
		"\2\2,\u00ce\3\2\2\2.\u00d4\3\2\2\2\60\61\5\4\3\2\61\3\3\2\2\2\62\63\7"+
		"\25\2\2\63\64\5\34\17\2\64\65\7\26\2\2\658\5\6\4\2\66\67\7\27\2\2\679"+
		"\5\16\b\28\66\3\2\2\289\3\2\2\29;\3\2\2\2:<\5*\26\2;:\3\2\2\2;<\3\2\2"+
		"\2<>\3\2\2\2=?\5.\30\2>=\3\2\2\2>?\3\2\2\2?A\3\2\2\2@B\5,\27\2A@\3\2\2"+
		"\2AB\3\2\2\2BD\3\2\2\2CE\5\b\5\2DC\3\2\2\2DE\3\2\2\2EG\3\2\2\2FH\7\"\2"+
		"\2GF\3\2\2\2GH\3\2\2\2H\5\3\2\2\2IN\5\36\20\2JK\7!\2\2KM\5\36\20\2LJ\3"+
		"\2\2\2MP\3\2\2\2NL\3\2\2\2NO\3\2\2\2O\7\3\2\2\2PN\3\2\2\2QR\7\f\2\2RS"+
		"\7\r\2\2ST\7*\2\2T\t\3\2\2\2UX\5\f\7\2VX\5\36\20\2WU\3\2\2\2WV\3\2\2\2"+
		"X\13\3\2\2\2Yb\5\36\20\2Z[\t\2\2\2[\\\7\33\2\2\\]\5\36\20\2]^\7\30\2\2"+
		"^_\5\16\b\2_a\3\2\2\2`Z\3\2\2\2ad\3\2\2\2b`\3\2\2\2bc\3\2\2\2c\r\3\2\2"+
		"\2db\3\2\2\2eg\7\36\2\2fe\3\2\2\2fg\3\2\2\2gh\3\2\2\2hn\5\20\t\2ij\5\""+
		"\22\2jk\5\20\t\2km\3\2\2\2li\3\2\2\2mp\3\2\2\2nl\3\2\2\2no\3\2\2\2o\17"+
		"\3\2\2\2pn\3\2\2\2qw\5\22\n\2rs\5$\23\2st\5\22\n\2tv\3\2\2\2ur\3\2\2\2"+
		"vy\3\2\2\2wu\3\2\2\2wx\3\2\2\2x\21\3\2\2\2yw\3\2\2\2z}\5\24\13\2{}\5\26"+
		"\f\2|z\3\2\2\2|{\3\2\2\2}\23\3\2\2\2~\177\7(\2\2\177\u0080\5\16\b\2\u0080"+
		"\u0081\7)\2\2\u0081\25\3\2\2\2\u0082\u0083\5\36\20\2\u0083\u0084\t\3\2"+
		"\2\u0084\u0085\5\30\r\2\u0085\u0088\3\2\2\2\u0086\u0088\5&\24\2\u0087"+
		"\u0082\3\2\2\2\u0087\u0086\3\2\2\2\u0088\27\3\2\2\2\u0089\u0090\5\32\16"+
		"\2\u008a\u0090\7*\2\2\u008b\u0090\7+\2\2\u008c\u0090\5\36\20\2\u008d\u0090"+
		"\5 \21\2\u008e\u0090\7\13\2\2\u008f\u0089\3\2\2\2\u008f\u008a\3\2\2\2"+
		"\u008f\u008b\3\2\2\2\u008f\u008c\3\2\2\2\u008f\u008d\3\2\2\2\u008f\u008e"+
		"\3\2\2\2\u0090\31\3\2\2\2\u0091\u0092\7\37\2\2\u0092\u0093\7(\2\2\u0093"+
		"\u0094\7*\2\2\u0094\u0095\7!\2\2\u0095\u0096\7*\2\2\u0096\u0097\7)\2\2"+
		"\u0097\33\3\2\2\2\u0098\u009d\5\36\20\2\u0099\u009a\7!\2\2\u009a\u009c"+
		"\5\36\20\2\u009b\u0099\3\2\2\2\u009c\u009f\3\2\2\2\u009d\u009b\3\2\2\2"+
		"\u009d\u009e\3\2\2\2\u009e\35\3\2\2\2\u009f\u009d\3\2\2\2\u00a0\u00a5"+
		"\7&\2\2\u00a1\u00a2\7\'\2\2\u00a2\u00a4\7&\2\2\u00a3\u00a1\3\2\2\2\u00a4"+
		"\u00a7\3\2\2\2\u00a5\u00a3\3\2\2\2\u00a5\u00a6\3\2\2\2\u00a6\37\3\2\2"+
		"\2\u00a7\u00a5\3\2\2\2\u00a8\u00a9\t\4\2\2\u00a9!\3\2\2\2\u00aa\u00ac"+
		"\7\35\2\2\u00ab\u00ad\7\36\2\2\u00ac\u00ab\3\2\2\2\u00ac\u00ad\3\2\2\2"+
		"\u00ad#\3\2\2\2\u00ae\u00b0\7\34\2\2\u00af\u00b1\7\36\2\2\u00b0\u00af"+
		"\3\2\2\2\u00b0\u00b1\3\2\2\2\u00b1%\3\2\2\2\u00b2\u00b3\7\21\2\2\u00b3"+
		"\u00b4\7(\2\2\u00b4\u00b5\5\36\20\2\u00b5\u00b6\7!\2\2\u00b6\u00b7\t\5"+
		"\2\2\u00b7\u00b8\7!\2\2\u00b8\u00b9\7*\2\2\u00b9\u00ba\7)\2\2\u00ba\'"+
		"\3\2\2\2\u00bb\u00bf\5\36\20\2\u00bc\u00bd\7\t\2\2\u00bd\u00be\7*\2\2"+
		"\u00be\u00c0\7\n\2\2\u00bf\u00bc\3\2\2\2\u00bf\u00c0\3\2\2\2\u00c0\u00c2"+
		"\3\2\2\2\u00c1\u00c3\t\6\2\2\u00c2\u00c1\3\2\2\2\u00c2\u00c3\3\2\2\2\u00c3"+
		")\3\2\2\2\u00c4\u00c5\7$\2\2\u00c5\u00c6\7%\2\2\u00c6\u00cb\5(\25\2\u00c7"+
		"\u00c8\7!\2\2\u00c8\u00ca\5(\25\2\u00c9\u00c7\3\2\2\2\u00ca\u00cd\3\2"+
		"\2\2\u00cb\u00c9\3\2\2\2\u00cb\u00cc\3\2\2\2\u00cc+\3\2\2\2\u00cd\u00cb"+
		"\3\2\2\2\u00ce\u00cf\7#\2\2\u00cf\u00d2\7+\2\2\u00d0\u00d1\7!\2\2\u00d1"+
		"\u00d3\7+\2\2\u00d2\u00d0\3\2\2\2\u00d2\u00d3\3\2\2\2\u00d3-\3\2\2\2\u00d4"+
		"\u00d5\7\16\2\2\u00d5\u00e0\5\36\20\2\u00d6\u00d7\7\t\2\2\u00d7\u00dc"+
		"\7+\2\2\u00d8\u00d9\7!\2\2\u00d9\u00db\7+\2\2\u00da\u00d8\3\2\2\2\u00db"+
		"\u00de\3\2\2\2\u00dc\u00da\3\2\2\2\u00dc\u00dd\3\2\2\2\u00dd\u00df\3\2"+
		"\2\2\u00de\u00dc\3\2\2\2\u00df\u00e1\7\n\2\2\u00e0\u00d6\3\2\2\2\u00e0"+
		"\u00e1\3\2\2\2\u00e1/\3\2\2\2\338;>ADGNWbfnw|\u0087\u008f\u009d\u00a5"+
		"\u00ac\u00b0\u00bf\u00c2\u00cb\u00d2\u00dc\u00e0";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}