// Generated from java-escape by ANTLR 4.11.1
package com.castlemock.core.expression;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue"})
public class ExpressionParser extends Parser {
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
	public static final int
		RULE_expression = 0, RULE_expressionName = 1, RULE_argument = 2, RULE_argumentName = 3, 
		RULE_argumentValue = 4, RULE_argumentString = 5, RULE_argumentNumber = 6, 
		RULE_number = 7, RULE_array = 8, RULE_string = 9;
	private static String[] makeRuleNames() {
		return new String[] {
			"expression", "expressionName", "argument", "argumentName", "argumentValue", 
			"argumentString", "argumentNumber", "number", "array", "string"
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

	@Override
	public String getGrammarFileName() { return "java-escape"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public ExpressionParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ExpressionContext extends ParserRuleContext {
		public ExpressionNameContext type;
		public ArgumentContext argument;
		public List<ArgumentContext> arguments = new ArrayList<ArgumentContext>();
		public ExpressionNameContext expressionName() {
			return getRuleContext(ExpressionNameContext.class,0);
		}
		public List<ArgumentContext> argument() {
			return getRuleContexts(ArgumentContext.class);
		}
		public ArgumentContext argument(int i) {
			return getRuleContext(ArgumentContext.class,i);
		}
		public List<TerminalNode> WS() { return getTokens(ExpressionParser.WS); }
		public TerminalNode WS(int i) {
			return getToken(ExpressionParser.WS, i);
		}
		public ExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionListener ) ((ExpressionListener)listener).enterExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionListener ) ((ExpressionListener)listener).exitExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ExpressionVisitor ) return ((ExpressionVisitor<? extends T>)visitor).visitExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExpressionContext expression() throws RecognitionException {
		ExpressionContext _localctx = new ExpressionContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_expression);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(20);
			match(T__0);
			setState(21);
			((ExpressionContext)_localctx).type = expressionName();
			setState(48);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__1) {
				{
				setState(22);
				match(T__1);
				setState(28);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==WS) {
					{
					setState(24); 
					_errHandler.sync(this);
					_la = _input.LA(1);
					do {
						{
						{
						setState(23);
						match(WS);
						}
						}
						setState(26); 
						_errHandler.sync(this);
						_la = _input.LA(1);
					} while ( _la==WS );
					}
				}

				setState(45);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==CHAR) {
					{
					setState(30);
					((ExpressionContext)_localctx).argument = argument();
					((ExpressionContext)_localctx).arguments.add(((ExpressionContext)_localctx).argument);
					setState(42);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==T__2) {
						{
						{
						setState(31);
						match(T__2);
						setState(37);
						_errHandler.sync(this);
						_la = _input.LA(1);
						if (_la==WS) {
							{
							setState(33); 
							_errHandler.sync(this);
							_la = _input.LA(1);
							do {
								{
								{
								setState(32);
								match(WS);
								}
								}
								setState(35); 
								_errHandler.sync(this);
								_la = _input.LA(1);
							} while ( _la==WS );
							}
						}

						setState(39);
						((ExpressionContext)_localctx).argument = argument();
						((ExpressionContext)_localctx).arguments.add(((ExpressionContext)_localctx).argument);
						}
						}
						setState(44);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					}
				}

				setState(47);
				match(T__3);
				}
			}

			setState(50);
			match(T__4);
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

	@SuppressWarnings("CheckReturnValue")
	public static class ExpressionNameContext extends ParserRuleContext {
		public Token value;
		public List<TerminalNode> CHAR() { return getTokens(ExpressionParser.CHAR); }
		public TerminalNode CHAR(int i) {
			return getToken(ExpressionParser.CHAR, i);
		}
		public List<TerminalNode> DIGIT() { return getTokens(ExpressionParser.DIGIT); }
		public TerminalNode DIGIT(int i) {
			return getToken(ExpressionParser.DIGIT, i);
		}
		public List<TerminalNode> UNDER_SCORE() { return getTokens(ExpressionParser.UNDER_SCORE); }
		public TerminalNode UNDER_SCORE(int i) {
			return getToken(ExpressionParser.UNDER_SCORE, i);
		}
		public ExpressionNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expressionName; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionListener ) ((ExpressionListener)listener).enterExpressionName(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionListener ) ((ExpressionListener)listener).exitExpressionName(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ExpressionVisitor ) return ((ExpressionVisitor<? extends T>)visitor).visitExpressionName(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExpressionNameContext expressionName() throws RecognitionException {
		ExpressionNameContext _localctx = new ExpressionNameContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_expressionName);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(53); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(52);
				((ExpressionNameContext)_localctx).value = _input.LT(1);
				_la = _input.LA(1);
				if ( !(((_la) & ~0x3f) == 0 && ((1L << _la) & 30064771072L) != 0) ) {
					((ExpressionNameContext)_localctx).value = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				}
				}
				setState(55); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( ((_la) & ~0x3f) == 0 && ((1L << _la) & 30064771072L) != 0 );
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

	@SuppressWarnings("CheckReturnValue")
	public static class ArgumentContext extends ParserRuleContext {
		public ArgumentNameContext name;
		public ArgumentValueContext value;
		public ArgumentNameContext argumentName() {
			return getRuleContext(ArgumentNameContext.class,0);
		}
		public ArgumentValueContext argumentValue() {
			return getRuleContext(ArgumentValueContext.class,0);
		}
		public ArgumentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_argument; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionListener ) ((ExpressionListener)listener).enterArgument(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionListener ) ((ExpressionListener)listener).exitArgument(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ExpressionVisitor ) return ((ExpressionVisitor<? extends T>)visitor).visitArgument(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArgumentContext argument() throws RecognitionException {
		ArgumentContext _localctx = new ArgumentContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_argument);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(57);
			((ArgumentContext)_localctx).name = argumentName();
			setState(58);
			match(T__5);
			setState(59);
			((ArgumentContext)_localctx).value = argumentValue();
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

	@SuppressWarnings("CheckReturnValue")
	public static class ArgumentNameContext extends ParserRuleContext {
		public Token value;
		public List<TerminalNode> CHAR() { return getTokens(ExpressionParser.CHAR); }
		public TerminalNode CHAR(int i) {
			return getToken(ExpressionParser.CHAR, i);
		}
		public List<TerminalNode> DIGIT() { return getTokens(ExpressionParser.DIGIT); }
		public TerminalNode DIGIT(int i) {
			return getToken(ExpressionParser.DIGIT, i);
		}
		public ArgumentNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_argumentName; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionListener ) ((ExpressionListener)listener).enterArgumentName(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionListener ) ((ExpressionListener)listener).exitArgumentName(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ExpressionVisitor ) return ((ExpressionVisitor<? extends T>)visitor).visitArgumentName(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArgumentNameContext argumentName() throws RecognitionException {
		ArgumentNameContext _localctx = new ArgumentNameContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_argumentName);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(61);
			((ArgumentNameContext)_localctx).value = match(CHAR);
			setState(67);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==CHAR || _la==DIGIT) {
				{
				setState(63); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(62);
					_la = _input.LA(1);
					if ( !(_la==CHAR || _la==DIGIT) ) {
					_errHandler.recoverInline(this);
					}
					else {
						if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
						_errHandler.reportMatch(this);
						consume();
					}
					}
					}
					setState(65); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==CHAR || _la==DIGIT );
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

	@SuppressWarnings("CheckReturnValue")
	public static class ArgumentValueContext extends ParserRuleContext {
		public ArgumentNumberContext argumentNumber() {
			return getRuleContext(ArgumentNumberContext.class,0);
		}
		public ArgumentStringContext argumentString() {
			return getRuleContext(ArgumentStringContext.class,0);
		}
		public ArrayContext array() {
			return getRuleContext(ArrayContext.class,0);
		}
		public ArgumentValueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_argumentValue; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionListener ) ((ExpressionListener)listener).enterArgumentValue(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionListener ) ((ExpressionListener)listener).exitArgumentValue(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ExpressionVisitor ) return ((ExpressionVisitor<? extends T>)visitor).visitArgumentValue(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArgumentValueContext argumentValue() throws RecognitionException {
		ArgumentValueContext _localctx = new ArgumentValueContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_argumentValue);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(72);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case DIGIT:
				{
				setState(69);
				argumentNumber();
				}
				break;
			case T__6:
			case T__7:
				{
				setState(70);
				argumentString();
				}
				break;
			case T__9:
				{
				setState(71);
				array();
				}
				break;
			default:
				throw new NoViableAltException(this);
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

	@SuppressWarnings("CheckReturnValue")
	public static class ArgumentStringContext extends ParserRuleContext {
		public StringContext value;
		public StringContext string() {
			return getRuleContext(StringContext.class,0);
		}
		public ArgumentStringContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_argumentString; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionListener ) ((ExpressionListener)listener).enterArgumentString(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionListener ) ((ExpressionListener)listener).exitArgumentString(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ExpressionVisitor ) return ((ExpressionVisitor<? extends T>)visitor).visitArgumentString(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArgumentStringContext argumentString() throws RecognitionException {
		ArgumentStringContext _localctx = new ArgumentStringContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_argumentString);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(75);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__6) {
				{
				setState(74);
				match(T__6);
				}
			}

			setState(77);
			match(T__7);
			setState(79);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (((_la) & ~0x3f) == 0 && ((1L << _la) & 68719476348L) != 0) {
				{
				setState(78);
				((ArgumentStringContext)_localctx).value = string();
				}
			}

			setState(82);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__6) {
				{
				setState(81);
				match(T__6);
				}
			}

			setState(84);
			match(T__7);
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

	@SuppressWarnings("CheckReturnValue")
	public static class ArgumentNumberContext extends ParserRuleContext {
		public NumberContext value;
		public NumberContext number() {
			return getRuleContext(NumberContext.class,0);
		}
		public ArgumentNumberContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_argumentNumber; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionListener ) ((ExpressionListener)listener).enterArgumentNumber(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionListener ) ((ExpressionListener)listener).exitArgumentNumber(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ExpressionVisitor ) return ((ExpressionVisitor<? extends T>)visitor).visitArgumentNumber(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArgumentNumberContext argumentNumber() throws RecognitionException {
		ArgumentNumberContext _localctx = new ArgumentNumberContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_argumentNumber);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(86);
			((ArgumentNumberContext)_localctx).value = number();
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

	@SuppressWarnings("CheckReturnValue")
	public static class NumberContext extends ParserRuleContext {
		public Token value;
		public List<TerminalNode> DIGIT() { return getTokens(ExpressionParser.DIGIT); }
		public TerminalNode DIGIT(int i) {
			return getToken(ExpressionParser.DIGIT, i);
		}
		public NumberContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_number; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionListener ) ((ExpressionListener)listener).enterNumber(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionListener ) ((ExpressionListener)listener).exitNumber(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ExpressionVisitor ) return ((ExpressionVisitor<? extends T>)visitor).visitNumber(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NumberContext number() throws RecognitionException {
		NumberContext _localctx = new NumberContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_number);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(89); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(88);
				((NumberContext)_localctx).value = match(DIGIT);
				}
				}
				setState(91); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==DIGIT );
			setState(99);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__8) {
				{
				setState(93);
				match(T__8);
				setState(95); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(94);
					match(DIGIT);
					}
					}
					setState(97); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==DIGIT );
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

	@SuppressWarnings("CheckReturnValue")
	public static class ArrayContext extends ParserRuleContext {
		public ArgumentValueContext argumentValue;
		public List<ArgumentValueContext> value = new ArrayList<ArgumentValueContext>();
		public List<ArgumentValueContext> argumentValue() {
			return getRuleContexts(ArgumentValueContext.class);
		}
		public ArgumentValueContext argumentValue(int i) {
			return getRuleContext(ArgumentValueContext.class,i);
		}
		public List<TerminalNode> WS() { return getTokens(ExpressionParser.WS); }
		public TerminalNode WS(int i) {
			return getToken(ExpressionParser.WS, i);
		}
		public ArrayContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_array; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionListener ) ((ExpressionListener)listener).enterArray(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionListener ) ((ExpressionListener)listener).exitArray(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ExpressionVisitor ) return ((ExpressionVisitor<? extends T>)visitor).visitArray(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArrayContext array() throws RecognitionException {
		ArrayContext _localctx = new ArrayContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_array);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(101);
			match(T__9);
			setState(107);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==WS) {
				{
				setState(103); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(102);
					match(WS);
					}
					}
					setState(105); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==WS );
				}
			}

			setState(124);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (((_la) & ~0x3f) == 0 && ((1L << _la) & 8589936000L) != 0) {
				{
				setState(109);
				((ArrayContext)_localctx).argumentValue = argumentValue();
				((ArrayContext)_localctx).value.add(((ArrayContext)_localctx).argumentValue);
				setState(121);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__2) {
					{
					{
					setState(110);
					match(T__2);
					setState(116);
					_errHandler.sync(this);
					_la = _input.LA(1);
					if (_la==WS) {
						{
						setState(112); 
						_errHandler.sync(this);
						_la = _input.LA(1);
						do {
							{
							{
							setState(111);
							match(WS);
							}
							}
							setState(114); 
							_errHandler.sync(this);
							_la = _input.LA(1);
						} while ( _la==WS );
						}
					}

					setState(118);
					((ArrayContext)_localctx).argumentValue = argumentValue();
					((ArrayContext)_localctx).value.add(((ArrayContext)_localctx).argumentValue);
					}
					}
					setState(123);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(126);
			match(T__10);
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

	@SuppressWarnings("CheckReturnValue")
	public static class StringContext extends ParserRuleContext {
		public Token value;
		public List<TerminalNode> DIGIT() { return getTokens(ExpressionParser.DIGIT); }
		public TerminalNode DIGIT(int i) {
			return getToken(ExpressionParser.DIGIT, i);
		}
		public List<TerminalNode> CHAR() { return getTokens(ExpressionParser.CHAR); }
		public TerminalNode CHAR(int i) {
			return getToken(ExpressionParser.CHAR, i);
		}
		public List<TerminalNode> WS() { return getTokens(ExpressionParser.WS); }
		public TerminalNode WS(int i) {
			return getToken(ExpressionParser.WS, i);
		}
		public List<TerminalNode> UNDER_SCORE() { return getTokens(ExpressionParser.UNDER_SCORE); }
		public TerminalNode UNDER_SCORE(int i) {
			return getToken(ExpressionParser.UNDER_SCORE, i);
		}
		public StringContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_string; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionListener ) ((ExpressionListener)listener).enterString(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionListener ) ((ExpressionListener)listener).exitString(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ExpressionVisitor ) return ((ExpressionVisitor<? extends T>)visitor).visitString(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StringContext string() throws RecognitionException {
		StringContext _localctx = new StringContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_string);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(129); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(128);
				((StringContext)_localctx).value = _input.LT(1);
				_la = _input.LA(1);
				if ( !(((_la) & ~0x3f) == 0 && ((1L << _la) & 68719476348L) != 0) ) {
					((StringContext)_localctx).value = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				}
				}
				setState(131); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( ((_la) & ~0x3f) == 0 && ((1L << _la) & 68719476348L) != 0 );
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
		"\u0004\u0001#\u0086\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002"+
		"\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0002"+
		"\b\u0007\b\u0002\t\u0007\t\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000"+
		"\u0004\u0000\u0019\b\u0000\u000b\u0000\f\u0000\u001a\u0003\u0000\u001d"+
		"\b\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0004\u0000\"\b\u0000\u000b"+
		"\u0000\f\u0000#\u0003\u0000&\b\u0000\u0001\u0000\u0005\u0000)\b\u0000"+
		"\n\u0000\f\u0000,\t\u0000\u0003\u0000.\b\u0000\u0001\u0000\u0003\u0000"+
		"1\b\u0000\u0001\u0000\u0001\u0000\u0001\u0001\u0004\u00016\b\u0001\u000b"+
		"\u0001\f\u00017\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001"+
		"\u0003\u0001\u0003\u0004\u0003@\b\u0003\u000b\u0003\f\u0003A\u0003\u0003"+
		"D\b\u0003\u0001\u0004\u0001\u0004\u0001\u0004\u0003\u0004I\b\u0004\u0001"+
		"\u0005\u0003\u0005L\b\u0005\u0001\u0005\u0001\u0005\u0003\u0005P\b\u0005"+
		"\u0001\u0005\u0003\u0005S\b\u0005\u0001\u0005\u0001\u0005\u0001\u0006"+
		"\u0001\u0006\u0001\u0007\u0004\u0007Z\b\u0007\u000b\u0007\f\u0007[\u0001"+
		"\u0007\u0001\u0007\u0004\u0007`\b\u0007\u000b\u0007\f\u0007a\u0003\u0007"+
		"d\b\u0007\u0001\b\u0001\b\u0004\bh\b\b\u000b\b\f\bi\u0003\bl\b\b\u0001"+
		"\b\u0001\b\u0001\b\u0004\bq\b\b\u000b\b\f\br\u0003\bu\b\b\u0001\b\u0005"+
		"\bx\b\b\n\b\f\b{\t\b\u0003\b}\b\b\u0001\b\u0001\b\u0001\t\u0004\t\u0082"+
		"\b\t\u000b\t\f\t\u0083\u0001\t\u0000\u0000\n\u0000\u0002\u0004\u0006\b"+
		"\n\f\u000e\u0010\u0012\u0000\u0003\u0001\u0000 \"\u0001\u0000 !\u0002"+
		"\u0000\u0002\u0006\t#\u0094\u0000\u0014\u0001\u0000\u0000\u0000\u0002"+
		"5\u0001\u0000\u0000\u0000\u00049\u0001\u0000\u0000\u0000\u0006=\u0001"+
		"\u0000\u0000\u0000\bH\u0001\u0000\u0000\u0000\nK\u0001\u0000\u0000\u0000"+
		"\fV\u0001\u0000\u0000\u0000\u000eY\u0001\u0000\u0000\u0000\u0010e\u0001"+
		"\u0000\u0000\u0000\u0012\u0081\u0001\u0000\u0000\u0000\u0014\u0015\u0005"+
		"\u0001\u0000\u0000\u00150\u0003\u0002\u0001\u0000\u0016\u001c\u0005\u0002"+
		"\u0000\u0000\u0017\u0019\u0005#\u0000\u0000\u0018\u0017\u0001\u0000\u0000"+
		"\u0000\u0019\u001a\u0001\u0000\u0000\u0000\u001a\u0018\u0001\u0000\u0000"+
		"\u0000\u001a\u001b\u0001\u0000\u0000\u0000\u001b\u001d\u0001\u0000\u0000"+
		"\u0000\u001c\u0018\u0001\u0000\u0000\u0000\u001c\u001d\u0001\u0000\u0000"+
		"\u0000\u001d-\u0001\u0000\u0000\u0000\u001e*\u0003\u0004\u0002\u0000\u001f"+
		"%\u0005\u0003\u0000\u0000 \"\u0005#\u0000\u0000! \u0001\u0000\u0000\u0000"+
		"\"#\u0001\u0000\u0000\u0000#!\u0001\u0000\u0000\u0000#$\u0001\u0000\u0000"+
		"\u0000$&\u0001\u0000\u0000\u0000%!\u0001\u0000\u0000\u0000%&\u0001\u0000"+
		"\u0000\u0000&\'\u0001\u0000\u0000\u0000\')\u0003\u0004\u0002\u0000(\u001f"+
		"\u0001\u0000\u0000\u0000),\u0001\u0000\u0000\u0000*(\u0001\u0000\u0000"+
		"\u0000*+\u0001\u0000\u0000\u0000+.\u0001\u0000\u0000\u0000,*\u0001\u0000"+
		"\u0000\u0000-\u001e\u0001\u0000\u0000\u0000-.\u0001\u0000\u0000\u0000"+
		"./\u0001\u0000\u0000\u0000/1\u0005\u0004\u0000\u00000\u0016\u0001\u0000"+
		"\u0000\u000001\u0001\u0000\u0000\u000012\u0001\u0000\u0000\u000023\u0005"+
		"\u0005\u0000\u00003\u0001\u0001\u0000\u0000\u000046\u0007\u0000\u0000"+
		"\u000054\u0001\u0000\u0000\u000067\u0001\u0000\u0000\u000075\u0001\u0000"+
		"\u0000\u000078\u0001\u0000\u0000\u00008\u0003\u0001\u0000\u0000\u0000"+
		"9:\u0003\u0006\u0003\u0000:;\u0005\u0006\u0000\u0000;<\u0003\b\u0004\u0000"+
		"<\u0005\u0001\u0000\u0000\u0000=C\u0005 \u0000\u0000>@\u0007\u0001\u0000"+
		"\u0000?>\u0001\u0000\u0000\u0000@A\u0001\u0000\u0000\u0000A?\u0001\u0000"+
		"\u0000\u0000AB\u0001\u0000\u0000\u0000BD\u0001\u0000\u0000\u0000C?\u0001"+
		"\u0000\u0000\u0000CD\u0001\u0000\u0000\u0000D\u0007\u0001\u0000\u0000"+
		"\u0000EI\u0003\f\u0006\u0000FI\u0003\n\u0005\u0000GI\u0003\u0010\b\u0000"+
		"HE\u0001\u0000\u0000\u0000HF\u0001\u0000\u0000\u0000HG\u0001\u0000\u0000"+
		"\u0000I\t\u0001\u0000\u0000\u0000JL\u0005\u0007\u0000\u0000KJ\u0001\u0000"+
		"\u0000\u0000KL\u0001\u0000\u0000\u0000LM\u0001\u0000\u0000\u0000MO\u0005"+
		"\b\u0000\u0000NP\u0003\u0012\t\u0000ON\u0001\u0000\u0000\u0000OP\u0001"+
		"\u0000\u0000\u0000PR\u0001\u0000\u0000\u0000QS\u0005\u0007\u0000\u0000"+
		"RQ\u0001\u0000\u0000\u0000RS\u0001\u0000\u0000\u0000ST\u0001\u0000\u0000"+
		"\u0000TU\u0005\b\u0000\u0000U\u000b\u0001\u0000\u0000\u0000VW\u0003\u000e"+
		"\u0007\u0000W\r\u0001\u0000\u0000\u0000XZ\u0005!\u0000\u0000YX\u0001\u0000"+
		"\u0000\u0000Z[\u0001\u0000\u0000\u0000[Y\u0001\u0000\u0000\u0000[\\\u0001"+
		"\u0000\u0000\u0000\\c\u0001\u0000\u0000\u0000]_\u0005\t\u0000\u0000^`"+
		"\u0005!\u0000\u0000_^\u0001\u0000\u0000\u0000`a\u0001\u0000\u0000\u0000"+
		"a_\u0001\u0000\u0000\u0000ab\u0001\u0000\u0000\u0000bd\u0001\u0000\u0000"+
		"\u0000c]\u0001\u0000\u0000\u0000cd\u0001\u0000\u0000\u0000d\u000f\u0001"+
		"\u0000\u0000\u0000ek\u0005\n\u0000\u0000fh\u0005#\u0000\u0000gf\u0001"+
		"\u0000\u0000\u0000hi\u0001\u0000\u0000\u0000ig\u0001\u0000\u0000\u0000"+
		"ij\u0001\u0000\u0000\u0000jl\u0001\u0000\u0000\u0000kg\u0001\u0000\u0000"+
		"\u0000kl\u0001\u0000\u0000\u0000l|\u0001\u0000\u0000\u0000my\u0003\b\u0004"+
		"\u0000nt\u0005\u0003\u0000\u0000oq\u0005#\u0000\u0000po\u0001\u0000\u0000"+
		"\u0000qr\u0001\u0000\u0000\u0000rp\u0001\u0000\u0000\u0000rs\u0001\u0000"+
		"\u0000\u0000su\u0001\u0000\u0000\u0000tp\u0001\u0000\u0000\u0000tu\u0001"+
		"\u0000\u0000\u0000uv\u0001\u0000\u0000\u0000vx\u0003\b\u0004\u0000wn\u0001"+
		"\u0000\u0000\u0000x{\u0001\u0000\u0000\u0000yw\u0001\u0000\u0000\u0000"+
		"yz\u0001\u0000\u0000\u0000z}\u0001\u0000\u0000\u0000{y\u0001\u0000\u0000"+
		"\u0000|m\u0001\u0000\u0000\u0000|}\u0001\u0000\u0000\u0000}~\u0001\u0000"+
		"\u0000\u0000~\u007f\u0005\u000b\u0000\u0000\u007f\u0011\u0001\u0000\u0000"+
		"\u0000\u0080\u0082\u0007\u0002\u0000\u0000\u0081\u0080\u0001\u0000\u0000"+
		"\u0000\u0082\u0083\u0001\u0000\u0000\u0000\u0083\u0081\u0001\u0000\u0000"+
		"\u0000\u0083\u0084\u0001\u0000\u0000\u0000\u0084\u0013\u0001\u0000\u0000"+
		"\u0000\u0018\u001a\u001c#%*-07ACHKOR[acikrty|\u0083";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}