// Generated from /Users/karldahlgren/Github/castlemock/castlemock/code/core/expression/src/main/antlr/com/castlemock/core/expression/Expression.g4 by ANTLR 4.7
package com.castlemock.core.expression;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class ExpressionParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.7", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		T__17=18, T__18=19, T__19=20, T__20=21, T__21=22, T__22=23, T__23=24, 
		T__24=25, T__25=26, T__26=27, T__27=28, CHAR=29, DIGIT=30, UNDER_SCORE=31, 
		WS=32;
	public static final int
		RULE_expression = 0, RULE_expressionName = 1, RULE_argument = 2, RULE_argumentName = 3, 
		RULE_argumentValue = 4, RULE_argumentString = 5, RULE_argumentNumber = 6, 
		RULE_number = 7, RULE_array = 8, RULE_string = 9;
	public static final String[] ruleNames = {
		"expression", "expressionName", "argument", "argumentName", "argumentValue", 
		"argumentString", "argumentNumber", "number", "array", "string"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'${'", "'('", "','", "')'", "'}'", "'='", "'\\'", "'\"'", "'.'", 
		"'['", "']'", "'<'", "'>'", "'|'", "'?'", "'!'", "'@'", "'#'", "'\u20AC'", 
		"'%'", "'&'", "'/'", "'+'", "'-'", "'*'", "'^'", "':'", "';'", null, null, 
		"'_'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, "CHAR", "DIGIT", "UNDER_SCORE", "WS"
	};
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
	public String getGrammarFileName() { return "Expression.g4"; }

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
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << CHAR) | (1L << DIGIT) | (1L << UNDER_SCORE))) != 0)) ) {
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
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << CHAR) | (1L << DIGIT) | (1L << UNDER_SCORE))) != 0) );
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
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__1) | (1L << T__2) | (1L << T__3) | (1L << T__5) | (1L << T__8) | (1L << T__11) | (1L << T__12) | (1L << T__13) | (1L << T__14) | (1L << T__15) | (1L << T__16) | (1L << T__17) | (1L << T__18) | (1L << T__19) | (1L << T__20) | (1L << T__21) | (1L << T__22) | (1L << T__23) | (1L << T__24) | (1L << T__25) | (1L << T__26) | (1L << T__27) | (1L << CHAR) | (1L << DIGIT) | (1L << WS))) != 0)) {
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
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__6) | (1L << T__7) | (1L << T__9) | (1L << DIGIT))) != 0)) {
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
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__1) | (1L << T__2) | (1L << T__3) | (1L << T__5) | (1L << T__8) | (1L << T__11) | (1L << T__12) | (1L << T__13) | (1L << T__14) | (1L << T__15) | (1L << T__16) | (1L << T__17) | (1L << T__18) | (1L << T__19) | (1L << T__20) | (1L << T__21) | (1L << T__22) | (1L << T__23) | (1L << T__24) | (1L << T__25) | (1L << T__26) | (1L << T__27) | (1L << CHAR) | (1L << DIGIT) | (1L << WS))) != 0)) ) {
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
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__1) | (1L << T__2) | (1L << T__3) | (1L << T__5) | (1L << T__8) | (1L << T__11) | (1L << T__12) | (1L << T__13) | (1L << T__14) | (1L << T__15) | (1L << T__16) | (1L << T__17) | (1L << T__18) | (1L << T__19) | (1L << T__20) | (1L << T__21) | (1L << T__22) | (1L << T__23) | (1L << T__24) | (1L << T__25) | (1L << T__26) | (1L << T__27) | (1L << CHAR) | (1L << DIGIT) | (1L << WS))) != 0) );
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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\"\u0088\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\3\2\3\2\3\2\3\2\6\2\33\n\2\r\2\16\2\34\5\2\37\n\2\3\2\3\2\3\2\6\2"+
		"$\n\2\r\2\16\2%\5\2(\n\2\3\2\7\2+\n\2\f\2\16\2.\13\2\5\2\60\n\2\3\2\5"+
		"\2\63\n\2\3\2\3\2\3\3\6\38\n\3\r\3\16\39\3\4\3\4\3\4\3\4\3\5\3\5\6\5B"+
		"\n\5\r\5\16\5C\5\5F\n\5\3\6\3\6\3\6\5\6K\n\6\3\7\5\7N\n\7\3\7\3\7\5\7"+
		"R\n\7\3\7\5\7U\n\7\3\7\3\7\3\b\3\b\3\t\6\t\\\n\t\r\t\16\t]\3\t\3\t\6\t"+
		"b\n\t\r\t\16\tc\5\tf\n\t\3\n\3\n\6\nj\n\n\r\n\16\nk\5\nn\n\n\3\n\3\n\3"+
		"\n\6\ns\n\n\r\n\16\nt\5\nw\n\n\3\n\7\nz\n\n\f\n\16\n}\13\n\5\n\177\n\n"+
		"\3\n\3\n\3\13\6\13\u0084\n\13\r\13\16\13\u0085\3\13\2\2\f\2\4\6\b\n\f"+
		"\16\20\22\24\2\5\3\2\37!\3\2\37 \7\2\4\6\b\b\13\13\16 \"\"\2\u0096\2\26"+
		"\3\2\2\2\4\67\3\2\2\2\6;\3\2\2\2\b?\3\2\2\2\nJ\3\2\2\2\fM\3\2\2\2\16X"+
		"\3\2\2\2\20[\3\2\2\2\22g\3\2\2\2\24\u0083\3\2\2\2\26\27\7\3\2\2\27\62"+
		"\5\4\3\2\30\36\7\4\2\2\31\33\7\"\2\2\32\31\3\2\2\2\33\34\3\2\2\2\34\32"+
		"\3\2\2\2\34\35\3\2\2\2\35\37\3\2\2\2\36\32\3\2\2\2\36\37\3\2\2\2\37/\3"+
		"\2\2\2 ,\5\6\4\2!\'\7\5\2\2\"$\7\"\2\2#\"\3\2\2\2$%\3\2\2\2%#\3\2\2\2"+
		"%&\3\2\2\2&(\3\2\2\2\'#\3\2\2\2\'(\3\2\2\2()\3\2\2\2)+\5\6\4\2*!\3\2\2"+
		"\2+.\3\2\2\2,*\3\2\2\2,-\3\2\2\2-\60\3\2\2\2.,\3\2\2\2/ \3\2\2\2/\60\3"+
		"\2\2\2\60\61\3\2\2\2\61\63\7\6\2\2\62\30\3\2\2\2\62\63\3\2\2\2\63\64\3"+
		"\2\2\2\64\65\7\7\2\2\65\3\3\2\2\2\668\t\2\2\2\67\66\3\2\2\289\3\2\2\2"+
		"9\67\3\2\2\29:\3\2\2\2:\5\3\2\2\2;<\5\b\5\2<=\7\b\2\2=>\5\n\6\2>\7\3\2"+
		"\2\2?E\7\37\2\2@B\t\3\2\2A@\3\2\2\2BC\3\2\2\2CA\3\2\2\2CD\3\2\2\2DF\3"+
		"\2\2\2EA\3\2\2\2EF\3\2\2\2F\t\3\2\2\2GK\5\16\b\2HK\5\f\7\2IK\5\22\n\2"+
		"JG\3\2\2\2JH\3\2\2\2JI\3\2\2\2K\13\3\2\2\2LN\7\t\2\2ML\3\2\2\2MN\3\2\2"+
		"\2NO\3\2\2\2OQ\7\n\2\2PR\5\24\13\2QP\3\2\2\2QR\3\2\2\2RT\3\2\2\2SU\7\t"+
		"\2\2TS\3\2\2\2TU\3\2\2\2UV\3\2\2\2VW\7\n\2\2W\r\3\2\2\2XY\5\20\t\2Y\17"+
		"\3\2\2\2Z\\\7 \2\2[Z\3\2\2\2\\]\3\2\2\2][\3\2\2\2]^\3\2\2\2^e\3\2\2\2"+
		"_a\7\13\2\2`b\7 \2\2a`\3\2\2\2bc\3\2\2\2ca\3\2\2\2cd\3\2\2\2df\3\2\2\2"+
		"e_\3\2\2\2ef\3\2\2\2f\21\3\2\2\2gm\7\f\2\2hj\7\"\2\2ih\3\2\2\2jk\3\2\2"+
		"\2ki\3\2\2\2kl\3\2\2\2ln\3\2\2\2mi\3\2\2\2mn\3\2\2\2n~\3\2\2\2o{\5\n\6"+
		"\2pv\7\5\2\2qs\7\"\2\2rq\3\2\2\2st\3\2\2\2tr\3\2\2\2tu\3\2\2\2uw\3\2\2"+
		"\2vr\3\2\2\2vw\3\2\2\2wx\3\2\2\2xz\5\n\6\2yp\3\2\2\2z}\3\2\2\2{y\3\2\2"+
		"\2{|\3\2\2\2|\177\3\2\2\2}{\3\2\2\2~o\3\2\2\2~\177\3\2\2\2\177\u0080\3"+
		"\2\2\2\u0080\u0081\7\r\2\2\u0081\23\3\2\2\2\u0082\u0084\t\4\2\2\u0083"+
		"\u0082\3\2\2\2\u0084\u0085\3\2\2\2\u0085\u0083\3\2\2\2\u0085\u0086\3\2"+
		"\2\2\u0086\25\3\2\2\2\32\34\36%\',/\629CEJMQT]cekmtv{~\u0085";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}