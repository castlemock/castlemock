// Generated from java-escape by ANTLR 4.11.1
package com.castlemock.core.expression;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue"})
public class ExpressionLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.11.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		T__17=18, T__18=19, T__19=20, T__20=21, T__21=22, T__22=23, T__23=24, 
		T__24=25, T__25=26, T__26=27, T__27=28, T__28=29, T__29=30, T__30=31, 
		CHAR=32, DIGIT=33, UNDER_SCORE=34, WS=35;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "T__6", "T__7", "T__8", 
			"T__9", "T__10", "T__11", "T__12", "T__13", "T__14", "T__15", "T__16", 
			"T__17", "T__18", "T__19", "T__20", "T__21", "T__22", "T__23", "T__24", 
			"T__25", "T__26", "T__27", "T__28", "T__29", "T__30", "CHAR", "DIGIT", 
			"UNDER_SCORE", "WS"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'${'", "'('", "','", "')'", "'}'", "'='", "'\\'", "'\"'", "'.'", 
			"'['", "']'", "'<'", "'>'", "'|'", "'?'", "'!'", "'@'", "'#'", "'\\u20AC'", 
			"'%'", "'&'", "'/'", "'+'", "'-'", "'*'", "'^'", "':'", "';'", "'$'", 
			"'{'", "'''", null, null, "'_'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, "CHAR", "DIGIT", "UNDER_SCORE", 
			"WS"
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


	public ExpressionLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "Expression.g4"; }

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

	public static final String _serializedATN =
		"\u0004\u0000#\u0091\u0006\uffff\uffff\u0002\u0000\u0007\u0000\u0002\u0001"+
		"\u0007\u0001\u0002\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004"+
		"\u0007\u0004\u0002\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007"+
		"\u0007\u0007\u0002\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b"+
		"\u0007\u000b\u0002\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0002"+
		"\u000f\u0007\u000f\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011\u0002"+
		"\u0012\u0007\u0012\u0002\u0013\u0007\u0013\u0002\u0014\u0007\u0014\u0002"+
		"\u0015\u0007\u0015\u0002\u0016\u0007\u0016\u0002\u0017\u0007\u0017\u0002"+
		"\u0018\u0007\u0018\u0002\u0019\u0007\u0019\u0002\u001a\u0007\u001a\u0002"+
		"\u001b\u0007\u001b\u0002\u001c\u0007\u001c\u0002\u001d\u0007\u001d\u0002"+
		"\u001e\u0007\u001e\u0002\u001f\u0007\u001f\u0002 \u0007 \u0002!\u0007"+
		"!\u0002\"\u0007\"\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0001\u0001"+
		"\u0001\u0001\u0002\u0001\u0002\u0001\u0003\u0001\u0003\u0001\u0004\u0001"+
		"\u0004\u0001\u0005\u0001\u0005\u0001\u0006\u0001\u0006\u0001\u0007\u0001"+
		"\u0007\u0001\b\u0001\b\u0001\t\u0001\t\u0001\n\u0001\n\u0001\u000b\u0001"+
		"\u000b\u0001\f\u0001\f\u0001\r\u0001\r\u0001\u000e\u0001\u000e\u0001\u000f"+
		"\u0001\u000f\u0001\u0010\u0001\u0010\u0001\u0011\u0001\u0011\u0001\u0012"+
		"\u0001\u0012\u0001\u0013\u0001\u0013\u0001\u0014\u0001\u0014\u0001\u0015"+
		"\u0001\u0015\u0001\u0016\u0001\u0016\u0001\u0017\u0001\u0017\u0001\u0018"+
		"\u0001\u0018\u0001\u0019\u0001\u0019\u0001\u001a\u0001\u001a\u0001\u001b"+
		"\u0001\u001b\u0001\u001c\u0001\u001c\u0001\u001d\u0001\u001d\u0001\u001e"+
		"\u0001\u001e\u0001\u001f\u0001\u001f\u0001 \u0001 \u0001!\u0001!\u0001"+
		"\"\u0004\"\u008e\b\"\u000b\"\f\"\u008f\u0000\u0000#\u0001\u0001\u0003"+
		"\u0002\u0005\u0003\u0007\u0004\t\u0005\u000b\u0006\r\u0007\u000f\b\u0011"+
		"\t\u0013\n\u0015\u000b\u0017\f\u0019\r\u001b\u000e\u001d\u000f\u001f\u0010"+
		"!\u0011#\u0012%\u0013\'\u0014)\u0015+\u0016-\u0017/\u00181\u00193\u001a"+
		"5\u001b7\u001c9\u001d;\u001e=\u001f? A!C\"E#\u0001\u0000\u0002\u0002\u0000"+
		"AZaz\u0003\u0000\t\n\r\r  \u0091\u0000\u0001\u0001\u0000\u0000\u0000\u0000"+
		"\u0003\u0001\u0000\u0000\u0000\u0000\u0005\u0001\u0000\u0000\u0000\u0000"+
		"\u0007\u0001\u0000\u0000\u0000\u0000\t\u0001\u0000\u0000\u0000\u0000\u000b"+
		"\u0001\u0000\u0000\u0000\u0000\r\u0001\u0000\u0000\u0000\u0000\u000f\u0001"+
		"\u0000\u0000\u0000\u0000\u0011\u0001\u0000\u0000\u0000\u0000\u0013\u0001"+
		"\u0000\u0000\u0000\u0000\u0015\u0001\u0000\u0000\u0000\u0000\u0017\u0001"+
		"\u0000\u0000\u0000\u0000\u0019\u0001\u0000\u0000\u0000\u0000\u001b\u0001"+
		"\u0000\u0000\u0000\u0000\u001d\u0001\u0000\u0000\u0000\u0000\u001f\u0001"+
		"\u0000\u0000\u0000\u0000!\u0001\u0000\u0000\u0000\u0000#\u0001\u0000\u0000"+
		"\u0000\u0000%\u0001\u0000\u0000\u0000\u0000\'\u0001\u0000\u0000\u0000"+
		"\u0000)\u0001\u0000\u0000\u0000\u0000+\u0001\u0000\u0000\u0000\u0000-"+
		"\u0001\u0000\u0000\u0000\u0000/\u0001\u0000\u0000\u0000\u00001\u0001\u0000"+
		"\u0000\u0000\u00003\u0001\u0000\u0000\u0000\u00005\u0001\u0000\u0000\u0000"+
		"\u00007\u0001\u0000\u0000\u0000\u00009\u0001\u0000\u0000\u0000\u0000;"+
		"\u0001\u0000\u0000\u0000\u0000=\u0001\u0000\u0000\u0000\u0000?\u0001\u0000"+
		"\u0000\u0000\u0000A\u0001\u0000\u0000\u0000\u0000C\u0001\u0000\u0000\u0000"+
		"\u0000E\u0001\u0000\u0000\u0000\u0001G\u0001\u0000\u0000\u0000\u0003J"+
		"\u0001\u0000\u0000\u0000\u0005L\u0001\u0000\u0000\u0000\u0007N\u0001\u0000"+
		"\u0000\u0000\tP\u0001\u0000\u0000\u0000\u000bR\u0001\u0000\u0000\u0000"+
		"\rT\u0001\u0000\u0000\u0000\u000fV\u0001\u0000\u0000\u0000\u0011X\u0001"+
		"\u0000\u0000\u0000\u0013Z\u0001\u0000\u0000\u0000\u0015\\\u0001\u0000"+
		"\u0000\u0000\u0017^\u0001\u0000\u0000\u0000\u0019`\u0001\u0000\u0000\u0000"+
		"\u001bb\u0001\u0000\u0000\u0000\u001dd\u0001\u0000\u0000\u0000\u001ff"+
		"\u0001\u0000\u0000\u0000!h\u0001\u0000\u0000\u0000#j\u0001\u0000\u0000"+
		"\u0000%l\u0001\u0000\u0000\u0000\'n\u0001\u0000\u0000\u0000)p\u0001\u0000"+
		"\u0000\u0000+r\u0001\u0000\u0000\u0000-t\u0001\u0000\u0000\u0000/v\u0001"+
		"\u0000\u0000\u00001x\u0001\u0000\u0000\u00003z\u0001\u0000\u0000\u0000"+
		"5|\u0001\u0000\u0000\u00007~\u0001\u0000\u0000\u00009\u0080\u0001\u0000"+
		"\u0000\u0000;\u0082\u0001\u0000\u0000\u0000=\u0084\u0001\u0000\u0000\u0000"+
		"?\u0086\u0001\u0000\u0000\u0000A\u0088\u0001\u0000\u0000\u0000C\u008a"+
		"\u0001\u0000\u0000\u0000E\u008d\u0001\u0000\u0000\u0000GH\u0005$\u0000"+
		"\u0000HI\u0005{\u0000\u0000I\u0002\u0001\u0000\u0000\u0000JK\u0005(\u0000"+
		"\u0000K\u0004\u0001\u0000\u0000\u0000LM\u0005,\u0000\u0000M\u0006\u0001"+
		"\u0000\u0000\u0000NO\u0005)\u0000\u0000O\b\u0001\u0000\u0000\u0000PQ\u0005"+
		"}\u0000\u0000Q\n\u0001\u0000\u0000\u0000RS\u0005=\u0000\u0000S\f\u0001"+
		"\u0000\u0000\u0000TU\u0005\\\u0000\u0000U\u000e\u0001\u0000\u0000\u0000"+
		"VW\u0005\"\u0000\u0000W\u0010\u0001\u0000\u0000\u0000XY\u0005.\u0000\u0000"+
		"Y\u0012\u0001\u0000\u0000\u0000Z[\u0005[\u0000\u0000[\u0014\u0001\u0000"+
		"\u0000\u0000\\]\u0005]\u0000\u0000]\u0016\u0001\u0000\u0000\u0000^_\u0005"+
		"<\u0000\u0000_\u0018\u0001\u0000\u0000\u0000`a\u0005>\u0000\u0000a\u001a"+
		"\u0001\u0000\u0000\u0000bc\u0005|\u0000\u0000c\u001c\u0001\u0000\u0000"+
		"\u0000de\u0005?\u0000\u0000e\u001e\u0001\u0000\u0000\u0000fg\u0005!\u0000"+
		"\u0000g \u0001\u0000\u0000\u0000hi\u0005@\u0000\u0000i\"\u0001\u0000\u0000"+
		"\u0000jk\u0005#\u0000\u0000k$\u0001\u0000\u0000\u0000lm\u0005\u20ac\u0000"+
		"\u0000m&\u0001\u0000\u0000\u0000no\u0005%\u0000\u0000o(\u0001\u0000\u0000"+
		"\u0000pq\u0005&\u0000\u0000q*\u0001\u0000\u0000\u0000rs\u0005/\u0000\u0000"+
		"s,\u0001\u0000\u0000\u0000tu\u0005+\u0000\u0000u.\u0001\u0000\u0000\u0000"+
		"vw\u0005-\u0000\u0000w0\u0001\u0000\u0000\u0000xy\u0005*\u0000\u0000y"+
		"2\u0001\u0000\u0000\u0000z{\u0005^\u0000\u0000{4\u0001\u0000\u0000\u0000"+
		"|}\u0005:\u0000\u0000}6\u0001\u0000\u0000\u0000~\u007f\u0005;\u0000\u0000"+
		"\u007f8\u0001\u0000\u0000\u0000\u0080\u0081\u0005$\u0000\u0000\u0081:"+
		"\u0001\u0000\u0000\u0000\u0082\u0083\u0005{\u0000\u0000\u0083<\u0001\u0000"+
		"\u0000\u0000\u0084\u0085\u0005\'\u0000\u0000\u0085>\u0001\u0000\u0000"+
		"\u0000\u0086\u0087\u0007\u0000\u0000\u0000\u0087@\u0001\u0000\u0000\u0000"+
		"\u0088\u0089\u000209\u0000\u0089B\u0001\u0000\u0000\u0000\u008a\u008b"+
		"\u0005_\u0000\u0000\u008bD\u0001\u0000\u0000\u0000\u008c\u008e\u0007\u0001"+
		"\u0000\u0000\u008d\u008c\u0001\u0000\u0000\u0000\u008e\u008f\u0001\u0000"+
		"\u0000\u0000\u008f\u008d\u0001\u0000\u0000\u0000\u008f\u0090\u0001\u0000"+
		"\u0000\u0000\u0090F\u0001\u0000\u0000\u0000\u0002\u0000\u008f\u0000";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}