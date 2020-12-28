// Generated from Expression.g4 by ANTLR 4.7.2
package com.castlemock.core.expression;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class ExpressionLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.7.2", RuntimeMetaData.VERSION); }

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
			"'['", "']'", "'<'", "'>'", "'|'", "'?'", "'!'", "'@'", "'#'", "'\u20AC'", 
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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2%\u0093\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\3\2\3\2\3\2\3\3\3\3\3\4\3\4\3\5\3\5\3\6\3\6\3"+
		"\7\3\7\3\b\3\b\3\t\3\t\3\n\3\n\3\13\3\13\3\f\3\f\3\r\3\r\3\16\3\16\3\17"+
		"\3\17\3\20\3\20\3\21\3\21\3\22\3\22\3\23\3\23\3\24\3\24\3\25\3\25\3\26"+
		"\3\26\3\27\3\27\3\30\3\30\3\31\3\31\3\32\3\32\3\33\3\33\3\34\3\34\3\35"+
		"\3\35\3\36\3\36\3\37\3\37\3 \3 \3!\3!\3\"\3\"\3#\3#\3$\6$\u0090\n$\r$"+
		"\16$\u0091\2\2%\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31"+
		"\16\33\17\35\20\37\21!\22#\23%\24\'\25)\26+\27-\30/\31\61\32\63\33\65"+
		"\34\67\359\36;\37= ?!A\"C#E$G%\3\2\4\4\2C\\c|\5\2\13\f\17\17\"\"\2\u0093"+
		"\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2"+
		"\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2"+
		"\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2"+
		"\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2"+
		"\2\2\61\3\2\2\2\2\63\3\2\2\2\2\65\3\2\2\2\2\67\3\2\2\2\29\3\2\2\2\2;\3"+
		"\2\2\2\2=\3\2\2\2\2?\3\2\2\2\2A\3\2\2\2\2C\3\2\2\2\2E\3\2\2\2\2G\3\2\2"+
		"\2\3I\3\2\2\2\5L\3\2\2\2\7N\3\2\2\2\tP\3\2\2\2\13R\3\2\2\2\rT\3\2\2\2"+
		"\17V\3\2\2\2\21X\3\2\2\2\23Z\3\2\2\2\25\\\3\2\2\2\27^\3\2\2\2\31`\3\2"+
		"\2\2\33b\3\2\2\2\35d\3\2\2\2\37f\3\2\2\2!h\3\2\2\2#j\3\2\2\2%l\3\2\2\2"+
		"\'n\3\2\2\2)p\3\2\2\2+r\3\2\2\2-t\3\2\2\2/v\3\2\2\2\61x\3\2\2\2\63z\3"+
		"\2\2\2\65|\3\2\2\2\67~\3\2\2\29\u0080\3\2\2\2;\u0082\3\2\2\2=\u0084\3"+
		"\2\2\2?\u0086\3\2\2\2A\u0088\3\2\2\2C\u008a\3\2\2\2E\u008c\3\2\2\2G\u008f"+
		"\3\2\2\2IJ\7&\2\2JK\7}\2\2K\4\3\2\2\2LM\7*\2\2M\6\3\2\2\2NO\7.\2\2O\b"+
		"\3\2\2\2PQ\7+\2\2Q\n\3\2\2\2RS\7\177\2\2S\f\3\2\2\2TU\7?\2\2U\16\3\2\2"+
		"\2VW\7^\2\2W\20\3\2\2\2XY\7$\2\2Y\22\3\2\2\2Z[\7\60\2\2[\24\3\2\2\2\\"+
		"]\7]\2\2]\26\3\2\2\2^_\7_\2\2_\30\3\2\2\2`a\7>\2\2a\32\3\2\2\2bc\7@\2"+
		"\2c\34\3\2\2\2de\7~\2\2e\36\3\2\2\2fg\7A\2\2g \3\2\2\2hi\7#\2\2i\"\3\2"+
		"\2\2jk\7B\2\2k$\3\2\2\2lm\7%\2\2m&\3\2\2\2no\7\u20ae\2\2o(\3\2\2\2pq\7"+
		"\'\2\2q*\3\2\2\2rs\7(\2\2s,\3\2\2\2tu\7\61\2\2u.\3\2\2\2vw\7-\2\2w\60"+
		"\3\2\2\2xy\7/\2\2y\62\3\2\2\2z{\7,\2\2{\64\3\2\2\2|}\7`\2\2}\66\3\2\2"+
		"\2~\177\7<\2\2\1778\3\2\2\2\u0080\u0081\7=\2\2\u0081:\3\2\2\2\u0082\u0083"+
		"\7&\2\2\u0083<\3\2\2\2\u0084\u0085\7}\2\2\u0085>\3\2\2\2\u0086\u0087\7"+
		")\2\2\u0087@\3\2\2\2\u0088\u0089\t\2\2\2\u0089B\3\2\2\2\u008a\u008b\4"+
		"\62;\2\u008bD\3\2\2\2\u008c\u008d\7a\2\2\u008dF\3\2\2\2\u008e\u0090\t"+
		"\3\2\2\u008f\u008e\3\2\2\2\u0090\u0091\3\2\2\2\u0091\u008f\3\2\2\2\u0091"+
		"\u0092\3\2\2\2\u0092H\3\2\2\2\4\2\u0091\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}