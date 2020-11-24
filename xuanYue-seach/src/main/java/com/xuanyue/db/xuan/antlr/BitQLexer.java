// Generated from BitQ.g4 by ANTLR 4.7.2

	package com.xuanyue.db.xuan.antlr;

import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class BitQLexer extends Lexer {
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
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "T__6", "T__7", "TransArrt", 
			"SAVE", "AS", "MIX", "DESC", "ASC", "Phone_seach", "PositionMatch", "Contains", 
			"Has_Every_Char", "SELECT", "FROM", "WHERE", "ON", "LEFT", "RIGHT", "JOIN", 
			"AND", "OR", "NOT", "TO_DATE", "ExprNot", "COMMA", "SEMI", "LIMIT", "Order", 
			"By", "NAME", "DOT", "Brackets_L", "Brackets_R", "STRING", "NUM", "TRUE", 
			"FALSE", "WS", "SQL_COMMENT"
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


		
		private int parallel = 1;
		
		java.util.regex.Pattern parallelReg = java.util.regex.Pattern.compile("parallel\\s*?\\(\\s*?(\\d+?)\\s*?\\)");
		java.util.regex.Pattern p = java.util.regex.Pattern.compile("/\\*(.*?)\\*/");
		public int getParallel(){
			return this.parallel;
		}
		void showme(){
			
			java.util.regex.Matcher m = p.matcher(this.getText());
			
			if(m.find()){
				
				java.util.regex.Matcher m2 = parallelReg.matcher(m.group(1));
				if(m2.find()) {
					parallel = Integer.parseInt(m2.group(1));
					if(parallel<1){
						parallel=1;
					}else if(parallel>20){
						parallel =20;
					}
				}
			}
			
			skip();
		}


	public BitQLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "BitQ.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	@Override
	public void action(RuleContext _localctx, int ruleIndex, int actionIndex) {
		switch (ruleIndex) {
		case 44:
			SQL_COMMENT_action((RuleContext)_localctx, actionIndex);
			break;
		}
	}
	private void SQL_COMMENT_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 0:
			 showme(); 
			break;
		}
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2/\u015d\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\4"+
		",\t,\4-\t-\4.\t.\3\2\3\2\3\3\3\3\3\3\3\4\3\4\3\4\3\5\3\5\3\6\3\6\3\6\3"+
		"\7\3\7\3\b\3\b\3\t\3\t\3\n\3\n\6\ns\n\n\r\n\16\nt\3\13\3\13\3\13\3\13"+
		"\3\13\3\f\3\f\3\f\3\r\3\r\3\r\3\r\3\16\3\16\3\16\3\16\3\16\3\17\3\17\3"+
		"\17\3\17\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3"+
		"\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3"+
		"\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\23\3\23\3\23\3\23\3\23\3"+
		"\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\24\3\24\3\24\3\24\3"+
		"\24\3\24\3\24\3\25\3\25\3\25\3\25\3\25\3\26\3\26\3\26\3\26\3\26\3\26\3"+
		"\27\3\27\3\27\3\30\3\30\3\30\3\30\3\30\3\31\3\31\3\31\3\31\3\31\3\31\3"+
		"\32\3\32\3\32\3\32\3\32\3\33\3\33\3\33\3\33\3\34\3\34\3\34\3\35\3\35\3"+
		"\35\3\35\3\36\3\36\3\36\3\36\3\36\3\36\3\36\3\36\3\37\3\37\3 \3 \3!\3"+
		"!\3\"\3\"\3\"\3\"\3\"\3\"\3#\3#\3#\3#\3#\3#\3$\3$\3$\3%\3%\7%\u010d\n"+
		"%\f%\16%\u0110\13%\3&\3&\3\'\3\'\3(\3(\3)\3)\3)\3)\7)\u011c\n)\f)\16)"+
		"\u011f\13)\3)\3)\3)\3)\3)\7)\u0126\n)\f)\16)\u0129\13)\3)\5)\u012c\n)"+
		"\3*\6*\u012f\n*\r*\16*\u0130\3*\3*\6*\u0135\n*\r*\16*\u0136\5*\u0139\n"+
		"*\3*\5*\u013c\n*\3+\3+\3+\3+\3+\3,\3,\3,\3,\3,\3,\3-\6-\u014a\n-\r-\16"+
		"-\u014b\3-\3-\3.\3.\3.\3.\7.\u0154\n.\f.\16.\u0157\13.\3.\3.\3.\3.\3."+
		"\3\u0155\2/\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16\33"+
		"\17\35\20\37\21!\22#\23%\24\'\25)\26+\27-\30/\31\61\32\63\33\65\34\67"+
		"\359\36;\37= ?!A\"C#E$G%I&K\'M(O)Q*S+U,W-Y.[/\3\2\37\6\2\62;C\\aac|\4"+
		"\2UUuu\4\2CCcc\4\2XXxx\4\2GGgg\4\2OOoo\4\2KKkk\4\2ZZzz\4\2FFff\4\2EEe"+
		"e\4\2RRrr\4\2JJjj\4\2QQqq\4\2PPpp\4\2VVvv\4\2TTtt\4\2[[{{\4\2NNnn\4\2"+
		"HHhh\4\2YYyy\4\2IIii\4\2LLll\4\2DDdd\4\2C\\c|\4\2))^^\4\2$$^^\4\2hhnn"+
		"\4\2WWww\5\2\13\f\17\17\"\"\2\u0169\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2"+
		"\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23"+
		"\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2"+
		"\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2"+
		"\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3\2\2\2\2\63\3\2\2\2\2\65\3"+
		"\2\2\2\2\67\3\2\2\2\29\3\2\2\2\2;\3\2\2\2\2=\3\2\2\2\2?\3\2\2\2\2A\3\2"+
		"\2\2\2C\3\2\2\2\2E\3\2\2\2\2G\3\2\2\2\2I\3\2\2\2\2K\3\2\2\2\2M\3\2\2\2"+
		"\2O\3\2\2\2\2Q\3\2\2\2\2S\3\2\2\2\2U\3\2\2\2\2W\3\2\2\2\2Y\3\2\2\2\2["+
		"\3\2\2\2\3]\3\2\2\2\5_\3\2\2\2\7b\3\2\2\2\te\3\2\2\2\13g\3\2\2\2\rj\3"+
		"\2\2\2\17l\3\2\2\2\21n\3\2\2\2\23p\3\2\2\2\25v\3\2\2\2\27{\3\2\2\2\31"+
		"~\3\2\2\2\33\u0082\3\2\2\2\35\u0087\3\2\2\2\37\u008b\3\2\2\2!\u0097\3"+
		"\2\2\2#\u00a5\3\2\2\2%\u00ae\3\2\2\2\'\u00bd\3\2\2\2)\u00c4\3\2\2\2+\u00c9"+
		"\3\2\2\2-\u00cf\3\2\2\2/\u00d2\3\2\2\2\61\u00d7\3\2\2\2\63\u00dd\3\2\2"+
		"\2\65\u00e2\3\2\2\2\67\u00e6\3\2\2\29\u00e9\3\2\2\2;\u00ed\3\2\2\2=\u00f5"+
		"\3\2\2\2?\u00f7\3\2\2\2A\u00f9\3\2\2\2C\u00fb\3\2\2\2E\u0101\3\2\2\2G"+
		"\u0107\3\2\2\2I\u010a\3\2\2\2K\u0111\3\2\2\2M\u0113\3\2\2\2O\u0115\3\2"+
		"\2\2Q\u012b\3\2\2\2S\u012e\3\2\2\2U\u013d\3\2\2\2W\u0142\3\2\2\2Y\u0149"+
		"\3\2\2\2[\u014f\3\2\2\2]^\7?\2\2^\4\3\2\2\2_`\7#\2\2`a\7?\2\2a\6\3\2\2"+
		"\2bc\7@\2\2cd\7?\2\2d\b\3\2\2\2ef\7@\2\2f\n\3\2\2\2gh\7>\2\2hi\7?\2\2"+
		"i\f\3\2\2\2jk\7>\2\2k\16\3\2\2\2lm\7]\2\2m\20\3\2\2\2no\7_\2\2o\22\3\2"+
		"\2\2pr\7A\2\2qs\t\2\2\2rq\3\2\2\2st\3\2\2\2tr\3\2\2\2tu\3\2\2\2u\24\3"+
		"\2\2\2vw\t\3\2\2wx\t\4\2\2xy\t\5\2\2yz\t\6\2\2z\26\3\2\2\2{|\t\4\2\2|"+
		"}\t\3\2\2}\30\3\2\2\2~\177\t\7\2\2\177\u0080\t\b\2\2\u0080\u0081\t\t\2"+
		"\2\u0081\32\3\2\2\2\u0082\u0083\t\n\2\2\u0083\u0084\t\6\2\2\u0084\u0085"+
		"\t\3\2\2\u0085\u0086\t\13\2\2\u0086\34\3\2\2\2\u0087\u0088\t\4\2\2\u0088"+
		"\u0089\t\3\2\2\u0089\u008a\t\13\2\2\u008a\36\3\2\2\2\u008b\u008c\t\f\2"+
		"\2\u008c\u008d\t\r\2\2\u008d\u008e\t\16\2\2\u008e\u008f\t\17\2\2\u008f"+
		"\u0090\t\6\2\2\u0090\u0091\7a\2\2\u0091\u0092\t\3\2\2\u0092\u0093\t\6"+
		"\2\2\u0093\u0094\t\4\2\2\u0094\u0095\t\13\2\2\u0095\u0096\t\r\2\2\u0096"+
		" \3\2\2\2\u0097\u0098\t\f\2\2\u0098\u0099\t\16\2\2\u0099\u009a\t\3\2\2"+
		"\u009a\u009b\t\b\2\2\u009b\u009c\t\20\2\2\u009c\u009d\t\b\2\2\u009d\u009e"+
		"\t\16\2\2\u009e\u009f\t\17\2\2\u009f\u00a0\t\7\2\2\u00a0\u00a1\t\4\2\2"+
		"\u00a1\u00a2\t\20\2\2\u00a2\u00a3\t\13\2\2\u00a3\u00a4\t\r\2\2\u00a4\""+
		"\3\2\2\2\u00a5\u00a6\t\13\2\2\u00a6\u00a7\t\16\2\2\u00a7\u00a8\t\17\2"+
		"\2\u00a8\u00a9\t\20\2\2\u00a9\u00aa\t\4\2\2\u00aa\u00ab\t\b\2\2\u00ab"+
		"\u00ac\t\17\2\2\u00ac\u00ad\t\3\2\2\u00ad$\3\2\2\2\u00ae\u00af\t\r\2\2"+
		"\u00af\u00b0\t\4\2\2\u00b0\u00b1\t\3\2\2\u00b1\u00b2\7a\2\2\u00b2\u00b3"+
		"\t\6\2\2\u00b3\u00b4\t\5\2\2\u00b4\u00b5\t\6\2\2\u00b5\u00b6\t\21\2\2"+
		"\u00b6\u00b7\t\22\2\2\u00b7\u00b8\7a\2\2\u00b8\u00b9\t\13\2\2\u00b9\u00ba"+
		"\t\r\2\2\u00ba\u00bb\t\4\2\2\u00bb\u00bc\t\21\2\2\u00bc&\3\2\2\2\u00bd"+
		"\u00be\t\3\2\2\u00be\u00bf\t\6\2\2\u00bf\u00c0\t\23\2\2\u00c0\u00c1\t"+
		"\6\2\2\u00c1\u00c2\t\13\2\2\u00c2\u00c3\t\20\2\2\u00c3(\3\2\2\2\u00c4"+
		"\u00c5\t\24\2\2\u00c5\u00c6\t\21\2\2\u00c6\u00c7\t\16\2\2\u00c7\u00c8"+
		"\t\7\2\2\u00c8*\3\2\2\2\u00c9\u00ca\t\25\2\2\u00ca\u00cb\t\r\2\2\u00cb"+
		"\u00cc\t\6\2\2\u00cc\u00cd\t\21\2\2\u00cd\u00ce\t\6\2\2\u00ce,\3\2\2\2"+
		"\u00cf\u00d0\t\16\2\2\u00d0\u00d1\t\17\2\2\u00d1.\3\2\2\2\u00d2\u00d3"+
		"\t\23\2\2\u00d3\u00d4\t\6\2\2\u00d4\u00d5\t\24\2\2\u00d5\u00d6\t\20\2"+
		"\2\u00d6\60\3\2\2\2\u00d7\u00d8\t\21\2\2\u00d8\u00d9\t\b\2\2\u00d9\u00da"+
		"\t\26\2\2\u00da\u00db\t\r\2\2\u00db\u00dc\t\20\2\2\u00dc\62\3\2\2\2\u00dd"+
		"\u00de\t\27\2\2\u00de\u00df\t\16\2\2\u00df\u00e0\t\b\2\2\u00e0\u00e1\t"+
		"\17\2\2\u00e1\64\3\2\2\2\u00e2\u00e3\t\4\2\2\u00e3\u00e4\t\17\2\2\u00e4"+
		"\u00e5\t\n\2\2\u00e5\66\3\2\2\2\u00e6\u00e7\t\16\2\2\u00e7\u00e8\t\21"+
		"\2\2\u00e88\3\2\2\2\u00e9\u00ea\t\17\2\2\u00ea\u00eb\t\16\2\2\u00eb\u00ec"+
		"\t\20\2\2\u00ec:\3\2\2\2\u00ed\u00ee\t\20\2\2\u00ee\u00ef\t\16\2\2\u00ef"+
		"\u00f0\7a\2\2\u00f0\u00f1\t\n\2\2\u00f1\u00f2\t\4\2\2\u00f2\u00f3\t\20"+
		"\2\2\u00f3\u00f4\t\6\2\2\u00f4<\3\2\2\2\u00f5\u00f6\7#\2\2\u00f6>\3\2"+
		"\2\2\u00f7\u00f8\7.\2\2\u00f8@\3\2\2\2\u00f9\u00fa\7=\2\2\u00faB\3\2\2"+
		"\2\u00fb\u00fc\t\23\2\2\u00fc\u00fd\t\b\2\2\u00fd\u00fe\t\7\2\2\u00fe"+
		"\u00ff\t\b\2\2\u00ff\u0100\t\20\2\2\u0100D\3\2\2\2\u0101\u0102\t\16\2"+
		"\2\u0102\u0103\t\21\2\2\u0103\u0104\t\n\2\2\u0104\u0105\t\6\2\2\u0105"+
		"\u0106\t\21\2\2\u0106F\3\2\2\2\u0107\u0108\t\30\2\2\u0108\u0109\t\22\2"+
		"\2\u0109H\3\2\2\2\u010a\u010e\t\31\2\2\u010b\u010d\t\2\2\2\u010c\u010b"+
		"\3\2\2\2\u010d\u0110\3\2\2\2\u010e\u010c\3\2\2\2\u010e\u010f\3\2\2\2\u010f"+
		"J\3\2\2\2\u0110\u010e\3\2\2\2\u0111\u0112\7\60\2\2\u0112L\3\2\2\2\u0113"+
		"\u0114\7*\2\2\u0114N\3\2\2\2\u0115\u0116\7+\2\2\u0116P\3\2\2\2\u0117\u011d"+
		"\7)\2\2\u0118\u011c\n\32\2\2\u0119\u011a\7^\2\2\u011a\u011c\13\2\2\2\u011b"+
		"\u0118\3\2\2\2\u011b\u0119\3\2\2\2\u011c\u011f\3\2\2\2\u011d\u011b\3\2"+
		"\2\2\u011d\u011e\3\2\2\2\u011e\u0120\3\2\2\2\u011f\u011d\3\2\2\2\u0120"+
		"\u012c\7)\2\2\u0121\u0127\7$\2\2\u0122\u0126\n\33\2\2\u0123\u0124\7^\2"+
		"\2\u0124\u0126\13\2\2\2\u0125\u0122\3\2\2\2\u0125\u0123\3\2\2\2\u0126"+
		"\u0129\3\2\2\2\u0127\u0125\3\2\2\2\u0127\u0128\3\2\2\2\u0128\u012a\3\2"+
		"\2\2\u0129\u0127\3\2\2\2\u012a\u012c\7$\2\2\u012b\u0117\3\2\2\2\u012b"+
		"\u0121\3\2\2\2\u012cR\3\2\2\2\u012d\u012f\4\62;\2\u012e\u012d\3\2\2\2"+
		"\u012f\u0130\3\2\2\2\u0130\u012e\3\2\2\2\u0130\u0131\3\2\2\2\u0131\u0138"+
		"\3\2\2\2\u0132\u0134\5K&\2\u0133\u0135\4\62;\2\u0134\u0133\3\2\2\2\u0135"+
		"\u0136\3\2\2\2\u0136\u0134\3\2\2\2\u0136\u0137\3\2\2\2\u0137\u0139\3\2"+
		"\2\2\u0138\u0132\3\2\2\2\u0138\u0139\3\2\2\2\u0139\u013b\3\2\2\2\u013a"+
		"\u013c\t\34\2\2\u013b\u013a\3\2\2\2\u013b\u013c\3\2\2\2\u013cT\3\2\2\2"+
		"\u013d\u013e\t\20\2\2\u013e\u013f\t\21\2\2\u013f\u0140\t\35\2\2\u0140"+
		"\u0141\t\6\2\2\u0141V\3\2\2\2\u0142\u0143\t\24\2\2\u0143\u0144\t\4\2\2"+
		"\u0144\u0145\t\23\2\2\u0145\u0146\t\3\2\2\u0146\u0147\t\6\2\2\u0147X\3"+
		"\2\2\2\u0148\u014a\t\36\2\2\u0149\u0148\3\2\2\2\u014a\u014b\3\2\2\2\u014b"+
		"\u0149\3\2\2\2\u014b\u014c\3\2\2\2\u014c\u014d\3\2\2\2\u014d\u014e\b-"+
		"\2\2\u014eZ\3\2\2\2\u014f\u0150\7\61\2\2\u0150\u0151\7,\2\2\u0151\u0155"+
		"\3\2\2\2\u0152\u0154\13\2\2\2\u0153\u0152\3\2\2\2\u0154\u0157\3\2\2\2"+
		"\u0155\u0156\3\2\2\2\u0155\u0153\3\2\2\2\u0156\u0158\3\2\2\2\u0157\u0155"+
		"\3\2\2\2\u0158\u0159\7,\2\2\u0159\u015a\7\61\2\2\u015a\u015b\3\2\2\2\u015b"+
		"\u015c\b.\3\2\u015c\\\3\2\2\2\20\2t\u010e\u011b\u011d\u0125\u0127\u012b"+
		"\u0130\u0136\u0138\u013b\u014b\u0155\4\b\2\2\3.\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}