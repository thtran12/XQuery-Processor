// Generated from XQuery.g4 by ANTLR 4.7.1
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class XQueryParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.7.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		T__17=18, T__18=19, T__19=20, T__20=21, T__21=22, T__22=23, T__23=24, 
		T__24=25, T__25=26, T__26=27, T__27=28, T__28=29, T__29=30, T__30=31, 
		T__31=32, T__32=33, T__33=34, T__34=35, T__35=36, T__36=37, STRING=38, 
		FN=39, WS=40;
	public static final int
		RULE_r = 0, RULE_fc = 1, RULE_wc = 2, RULE_lc = 3, RULE_rc = 4, RULE_join = 5, 
		RULE_list = 6, RULE_c = 7, RULE_ap = 8, RULE_rp = 9, RULE_f = 10;
	public static final String[] ruleNames = {
		"r", "fc", "wc", "lc", "rc", "join", "list", "c", "ap", "rp", "f"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'('", "')'", "'/'", "'//'", "','", "'<'", "'>'", "'{'", "'}'", 
		"'</'", "'$'", "'for'", "'in'", "'where'", "'let'", "':='", "'return'", 
		"'join'", "'['", "']'", "'empty'", "'some'", "'satisfies'", "'or'", "'not'", 
		"'and'", "'='", "'eq'", "'=='", "'is'", "'doc(\"'", "'\")'", "'*'", "'.'", 
		"'..'", "'text()'", "'@'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, "STRING", "FN", "WS"
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
	public String getGrammarFileName() { return "XQuery.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public XQueryParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class RContext extends ParserRuleContext {
		public ApContext ap() {
			return getRuleContext(ApContext.class,0);
		}
		public List<RContext> r() {
			return getRuleContexts(RContext.class);
		}
		public RContext r(int i) {
			return getRuleContext(RContext.class,i);
		}
		public List<TerminalNode> FN() { return getTokens(XQueryParser.FN); }
		public TerminalNode FN(int i) {
			return getToken(XQueryParser.FN, i);
		}
		public FcContext fc() {
			return getRuleContext(FcContext.class,0);
		}
		public RcContext rc() {
			return getRuleContext(RcContext.class,0);
		}
		public LcContext lc() {
			return getRuleContext(LcContext.class,0);
		}
		public WcContext wc() {
			return getRuleContext(WcContext.class,0);
		}
		public TerminalNode STRING() { return getToken(XQueryParser.STRING, 0); }
		public JoinContext join() {
			return getRuleContext(JoinContext.class,0);
		}
		public RpContext rp() {
			return getRuleContext(RpContext.class,0);
		}
		public RContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_r; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof XQueryListener ) ((XQueryListener)listener).enterR(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof XQueryListener ) ((XQueryListener)listener).exitR(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof XQueryVisitor ) return ((XQueryVisitor<? extends T>)visitor).visitR(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RContext r() throws RecognitionException {
		return r(0);
	}

	private RContext r(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		RContext _localctx = new RContext(_ctx, _parentState);
		RContext _prevctx = _localctx;
		int _startState = 0;
		enterRecursionRule(_localctx, 0, RULE_r, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(68);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,3,_ctx) ) {
			case 1:
				{
				setState(23);
				ap();
				}
				break;
			case 2:
				{
				setState(24);
				match(T__0);
				setState(25);
				r(0);
				setState(26);
				match(T__1);
				}
				break;
			case 3:
				{
				setState(28);
				match(T__5);
				setState(29);
				match(FN);
				setState(30);
				match(T__6);
				setState(31);
				match(T__7);
				setState(32);
				r(0);
				setState(33);
				match(T__8);
				setState(34);
				match(T__9);
				setState(35);
				match(FN);
				setState(36);
				match(T__6);
				}
				break;
			case 4:
				{
				setState(38);
				fc();
				setState(40);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__14) {
					{
					setState(39);
					lc();
					}
				}

				setState(43);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__13) {
					{
					setState(42);
					wc();
					}
				}

				setState(45);
				rc();
				}
				break;
			case 5:
				{
				setState(47);
				lc();
				setState(48);
				r(5);
				}
				break;
			case 6:
				{
				setState(50);
				match(T__10);
				setState(51);
				match(FN);
				}
				break;
			case 7:
				{
				setState(52);
				match(STRING);
				}
				break;
			case 8:
				{
				setState(53);
				join();
				}
				break;
			case 9:
				{
				setState(54);
				match(T__5);
				setState(55);
				match(FN);
				setState(56);
				match(T__6);
				setState(57);
				r(0);
				setState(61);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << T__5) | (1L << T__10) | (1L << T__11) | (1L << T__14) | (1L << T__17) | (1L << T__30) | (1L << STRING))) != 0)) {
					{
					{
					setState(58);
					r(0);
					}
					}
					setState(63);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(64);
				match(T__9);
				setState(65);
				match(FN);
				setState(66);
				match(T__6);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(81);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,5,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(79);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,4,_ctx) ) {
					case 1:
						{
						_localctx = new RContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_r);
						setState(70);
						if (!(precpred(_ctx, 8))) throw new FailedPredicateException(this, "precpred(_ctx, 8)");
						setState(71);
						match(T__4);
						setState(72);
						r(9);
						}
						break;
					case 2:
						{
						_localctx = new RContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_r);
						setState(73);
						if (!(precpred(_ctx, 10))) throw new FailedPredicateException(this, "precpred(_ctx, 10)");
						setState(74);
						match(T__2);
						setState(75);
						rp(0);
						}
						break;
					case 3:
						{
						_localctx = new RContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_r);
						setState(76);
						if (!(precpred(_ctx, 9))) throw new FailedPredicateException(this, "precpred(_ctx, 9)");
						setState(77);
						match(T__3);
						setState(78);
						rp(0);
						}
						break;
					}
					} 
				}
				setState(83);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,5,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class FcContext extends ParserRuleContext {
		public List<TerminalNode> FN() { return getTokens(XQueryParser.FN); }
		public TerminalNode FN(int i) {
			return getToken(XQueryParser.FN, i);
		}
		public List<RContext> r() {
			return getRuleContexts(RContext.class);
		}
		public RContext r(int i) {
			return getRuleContext(RContext.class,i);
		}
		public FcContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_fc; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof XQueryListener ) ((XQueryListener)listener).enterFc(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof XQueryListener ) ((XQueryListener)listener).exitFc(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof XQueryVisitor ) return ((XQueryVisitor<? extends T>)visitor).visitFc(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FcContext fc() throws RecognitionException {
		FcContext _localctx = new FcContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_fc);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(84);
			match(T__11);
			setState(85);
			match(T__10);
			setState(86);
			match(FN);
			setState(87);
			match(T__12);
			setState(88);
			r(0);
			setState(96);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__4) {
				{
				{
				setState(89);
				match(T__4);
				setState(90);
				match(T__10);
				setState(91);
				match(FN);
				setState(92);
				match(T__12);
				setState(93);
				r(0);
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

	public static class WcContext extends ParserRuleContext {
		public CContext c() {
			return getRuleContext(CContext.class,0);
		}
		public WcContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_wc; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof XQueryListener ) ((XQueryListener)listener).enterWc(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof XQueryListener ) ((XQueryListener)listener).exitWc(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof XQueryVisitor ) return ((XQueryVisitor<? extends T>)visitor).visitWc(this);
			else return visitor.visitChildren(this);
		}
	}

	public final WcContext wc() throws RecognitionException {
		WcContext _localctx = new WcContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_wc);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(99);
			match(T__13);
			setState(100);
			c(0);
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

	public static class LcContext extends ParserRuleContext {
		public List<TerminalNode> FN() { return getTokens(XQueryParser.FN); }
		public TerminalNode FN(int i) {
			return getToken(XQueryParser.FN, i);
		}
		public List<RContext> r() {
			return getRuleContexts(RContext.class);
		}
		public RContext r(int i) {
			return getRuleContext(RContext.class,i);
		}
		public LcContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_lc; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof XQueryListener ) ((XQueryListener)listener).enterLc(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof XQueryListener ) ((XQueryListener)listener).exitLc(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof XQueryVisitor ) return ((XQueryVisitor<? extends T>)visitor).visitLc(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LcContext lc() throws RecognitionException {
		LcContext _localctx = new LcContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_lc);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(102);
			match(T__14);
			setState(103);
			match(T__10);
			setState(104);
			match(FN);
			setState(105);
			match(T__15);
			setState(106);
			r(0);
			setState(114);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__4) {
				{
				{
				setState(107);
				match(T__4);
				setState(108);
				match(T__10);
				setState(109);
				match(FN);
				setState(110);
				match(T__15);
				setState(111);
				r(0);
				}
				}
				setState(116);
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

	public static class RcContext extends ParserRuleContext {
		public RContext r() {
			return getRuleContext(RContext.class,0);
		}
		public RcContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_rc; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof XQueryListener ) ((XQueryListener)listener).enterRc(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof XQueryListener ) ((XQueryListener)listener).exitRc(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof XQueryVisitor ) return ((XQueryVisitor<? extends T>)visitor).visitRc(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RcContext rc() throws RecognitionException {
		RcContext _localctx = new RcContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_rc);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(117);
			match(T__16);
			setState(118);
			r(0);
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

	public static class JoinContext extends ParserRuleContext {
		public List<RContext> r() {
			return getRuleContexts(RContext.class);
		}
		public RContext r(int i) {
			return getRuleContext(RContext.class,i);
		}
		public List<ListContext> list() {
			return getRuleContexts(ListContext.class);
		}
		public ListContext list(int i) {
			return getRuleContext(ListContext.class,i);
		}
		public JoinContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_join; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof XQueryListener ) ((XQueryListener)listener).enterJoin(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof XQueryListener ) ((XQueryListener)listener).exitJoin(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof XQueryVisitor ) return ((XQueryVisitor<? extends T>)visitor).visitJoin(this);
			else return visitor.visitChildren(this);
		}
	}

	public final JoinContext join() throws RecognitionException {
		JoinContext _localctx = new JoinContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_join);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(120);
			match(T__17);
			setState(121);
			match(T__0);
			setState(122);
			r(0);
			setState(123);
			match(T__4);
			setState(124);
			r(0);
			setState(125);
			match(T__4);
			setState(126);
			list();
			setState(127);
			match(T__4);
			setState(128);
			list();
			setState(129);
			match(T__1);
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

	public static class ListContext extends ParserRuleContext {
		public List<TerminalNode> FN() { return getTokens(XQueryParser.FN); }
		public TerminalNode FN(int i) {
			return getToken(XQueryParser.FN, i);
		}
		public ListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_list; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof XQueryListener ) ((XQueryListener)listener).enterList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof XQueryListener ) ((XQueryListener)listener).exitList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof XQueryVisitor ) return ((XQueryVisitor<? extends T>)visitor).visitList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ListContext list() throws RecognitionException {
		ListContext _localctx = new ListContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_list);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(131);
			match(T__18);
			setState(135);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==FN) {
				{
				{
				setState(132);
				match(FN);
				}
				}
				setState(137);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(142);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__4) {
				{
				{
				setState(138);
				match(T__4);
				setState(139);
				match(FN);
				}
				}
				setState(144);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(145);
			match(T__19);
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

	public static class CContext extends ParserRuleContext {
		public List<RContext> r() {
			return getRuleContexts(RContext.class);
		}
		public RContext r(int i) {
			return getRuleContext(RContext.class,i);
		}
		public List<TerminalNode> FN() { return getTokens(XQueryParser.FN); }
		public TerminalNode FN(int i) {
			return getToken(XQueryParser.FN, i);
		}
		public List<CContext> c() {
			return getRuleContexts(CContext.class);
		}
		public CContext c(int i) {
			return getRuleContext(CContext.class,i);
		}
		public CContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_c; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof XQueryListener ) ((XQueryListener)listener).enterC(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof XQueryListener ) ((XQueryListener)listener).exitC(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof XQueryVisitor ) return ((XQueryVisitor<? extends T>)visitor).visitC(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CContext c() throws RecognitionException {
		return c(0);
	}

	private CContext c(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		CContext _localctx = new CContext(_ctx, _parentState);
		CContext _prevctx = _localctx;
		int _startState = 14;
		enterRecursionRule(_localctx, 14, RULE_c, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(193);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,11,_ctx) ) {
			case 1:
				{
				setState(148);
				match(T__20);
				setState(149);
				match(T__0);
				setState(150);
				r(0);
				setState(151);
				match(T__1);
				}
				break;
			case 2:
				{
				setState(153);
				match(T__21);
				setState(154);
				match(T__10);
				setState(155);
				match(FN);
				setState(156);
				match(T__12);
				setState(157);
				r(0);
				setState(165);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__4) {
					{
					{
					setState(158);
					match(T__4);
					setState(159);
					match(T__10);
					setState(160);
					match(FN);
					setState(161);
					match(T__12);
					setState(162);
					r(0);
					}
					}
					setState(167);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(168);
				match(T__22);
				setState(169);
				c(9);
				}
				break;
			case 3:
				{
				setState(171);
				match(T__0);
				setState(172);
				c(0);
				setState(173);
				match(T__1);
				}
				break;
			case 4:
				{
				setState(175);
				match(T__24);
				setState(176);
				c(6);
				}
				break;
			case 5:
				{
				setState(177);
				r(0);
				setState(178);
				match(T__26);
				setState(179);
				r(0);
				}
				break;
			case 6:
				{
				setState(181);
				r(0);
				setState(182);
				match(T__27);
				setState(183);
				r(0);
				}
				break;
			case 7:
				{
				setState(185);
				r(0);
				setState(186);
				match(T__28);
				setState(187);
				r(0);
				}
				break;
			case 8:
				{
				setState(189);
				r(0);
				setState(190);
				match(T__29);
				setState(191);
				r(0);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(203);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,13,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(201);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,12,_ctx) ) {
					case 1:
						{
						_localctx = new CContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_c);
						setState(195);
						if (!(precpred(_ctx, 8))) throw new FailedPredicateException(this, "precpred(_ctx, 8)");
						setState(196);
						match(T__23);
						setState(197);
						c(9);
						}
						break;
					case 2:
						{
						_localctx = new CContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_c);
						setState(198);
						if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
						setState(199);
						match(T__25);
						setState(200);
						c(6);
						}
						break;
					}
					} 
				}
				setState(205);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,13,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class ApContext extends ParserRuleContext {
		public TerminalNode FN() { return getToken(XQueryParser.FN, 0); }
		public RpContext rp() {
			return getRuleContext(RpContext.class,0);
		}
		public ApContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ap; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof XQueryListener ) ((XQueryListener)listener).enterAp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof XQueryListener ) ((XQueryListener)listener).exitAp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof XQueryVisitor ) return ((XQueryVisitor<? extends T>)visitor).visitAp(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ApContext ap() throws RecognitionException {
		ApContext _localctx = new ApContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_ap);
		try {
			setState(216);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,14,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(206);
				match(T__30);
				setState(207);
				match(FN);
				setState(208);
				match(T__31);
				setState(209);
				match(T__2);
				setState(210);
				rp(0);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(211);
				match(T__30);
				setState(212);
				match(FN);
				setState(213);
				match(T__31);
				setState(214);
				match(T__3);
				setState(215);
				rp(0);
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

	public static class RpContext extends ParserRuleContext {
		public TerminalNode FN() { return getToken(XQueryParser.FN, 0); }
		public List<RpContext> rp() {
			return getRuleContexts(RpContext.class);
		}
		public RpContext rp(int i) {
			return getRuleContext(RpContext.class,i);
		}
		public FContext f() {
			return getRuleContext(FContext.class,0);
		}
		public RpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_rp; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof XQueryListener ) ((XQueryListener)listener).enterRp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof XQueryListener ) ((XQueryListener)listener).exitRp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof XQueryVisitor ) return ((XQueryVisitor<? extends T>)visitor).visitRp(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RpContext rp() throws RecognitionException {
		return rp(0);
	}

	private RpContext rp(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		RpContext _localctx = new RpContext(_ctx, _parentState);
		RpContext _prevctx = _localctx;
		int _startState = 18;
		enterRecursionRule(_localctx, 18, RULE_rp, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(231);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,15,_ctx) ) {
			case 1:
				{
				setState(219);
				match(FN);
				}
				break;
			case 2:
				{
				setState(220);
				match(T__32);
				}
				break;
			case 3:
				{
				setState(221);
				match(T__33);
				}
				break;
			case 4:
				{
				setState(222);
				match(T__34);
				}
				break;
			case 5:
				{
				setState(223);
				match(T__33);
				}
				break;
			case 6:
				{
				setState(224);
				match(T__35);
				}
				break;
			case 7:
				{
				setState(225);
				match(T__36);
				setState(226);
				match(FN);
				}
				break;
			case 8:
				{
				setState(227);
				match(T__0);
				setState(228);
				rp(0);
				setState(229);
				match(T__1);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(249);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,17,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(247);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,16,_ctx) ) {
					case 1:
						{
						_localctx = new RpContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_rp);
						setState(233);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(234);
						match(T__2);
						setState(235);
						rp(5);
						}
						break;
					case 2:
						{
						_localctx = new RpContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_rp);
						setState(236);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(237);
						match(T__3);
						setState(238);
						rp(4);
						}
						break;
					case 3:
						{
						_localctx = new RpContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_rp);
						setState(239);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(240);
						match(T__4);
						setState(241);
						rp(2);
						}
						break;
					case 4:
						{
						_localctx = new RpContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_rp);
						setState(242);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(243);
						match(T__18);
						setState(244);
						f(0);
						setState(245);
						match(T__19);
						}
						break;
					}
					} 
				}
				setState(251);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,17,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class FContext extends ParserRuleContext {
		public List<RpContext> rp() {
			return getRuleContexts(RpContext.class);
		}
		public RpContext rp(int i) {
			return getRuleContext(RpContext.class,i);
		}
		public List<FContext> f() {
			return getRuleContexts(FContext.class);
		}
		public FContext f(int i) {
			return getRuleContext(FContext.class,i);
		}
		public FContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_f; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof XQueryListener ) ((XQueryListener)listener).enterF(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof XQueryListener ) ((XQueryListener)listener).exitF(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof XQueryVisitor ) return ((XQueryVisitor<? extends T>)visitor).visitF(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FContext f() throws RecognitionException {
		return f(0);
	}

	private FContext f(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		FContext _localctx = new FContext(_ctx, _parentState);
		FContext _prevctx = _localctx;
		int _startState = 20;
		enterRecursionRule(_localctx, 20, RULE_f, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(276);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,18,_ctx) ) {
			case 1:
				{
				setState(253);
				rp(0);
				}
				break;
			case 2:
				{
				setState(254);
				rp(0);
				setState(255);
				match(T__26);
				setState(256);
				rp(0);
				}
				break;
			case 3:
				{
				setState(258);
				rp(0);
				setState(259);
				match(T__27);
				setState(260);
				rp(0);
				}
				break;
			case 4:
				{
				setState(262);
				rp(0);
				setState(263);
				match(T__28);
				setState(264);
				rp(0);
				}
				break;
			case 5:
				{
				setState(266);
				rp(0);
				setState(267);
				match(T__29);
				setState(268);
				rp(0);
				}
				break;
			case 6:
				{
				setState(270);
				match(T__0);
				setState(271);
				f(0);
				setState(272);
				match(T__1);
				}
				break;
			case 7:
				{
				setState(274);
				match(T__24);
				setState(275);
				f(1);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(286);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,20,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(284);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,19,_ctx) ) {
					case 1:
						{
						_localctx = new FContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_f);
						setState(278);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(279);
						match(T__25);
						setState(280);
						f(4);
						}
						break;
					case 2:
						{
						_localctx = new FContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_f);
						setState(281);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(282);
						match(T__23);
						setState(283);
						f(3);
						}
						break;
					}
					} 
				}
				setState(288);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,20,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 0:
			return r_sempred((RContext)_localctx, predIndex);
		case 7:
			return c_sempred((CContext)_localctx, predIndex);
		case 9:
			return rp_sempred((RpContext)_localctx, predIndex);
		case 10:
			return f_sempred((FContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean r_sempred(RContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 8);
		case 1:
			return precpred(_ctx, 10);
		case 2:
			return precpred(_ctx, 9);
		}
		return true;
	}
	private boolean c_sempred(CContext _localctx, int predIndex) {
		switch (predIndex) {
		case 3:
			return precpred(_ctx, 8);
		case 4:
			return precpred(_ctx, 5);
		}
		return true;
	}
	private boolean rp_sempred(RpContext _localctx, int predIndex) {
		switch (predIndex) {
		case 5:
			return precpred(_ctx, 4);
		case 6:
			return precpred(_ctx, 3);
		case 7:
			return precpred(_ctx, 1);
		case 8:
			return precpred(_ctx, 2);
		}
		return true;
	}
	private boolean f_sempred(FContext _localctx, int predIndex) {
		switch (predIndex) {
		case 9:
			return precpred(_ctx, 3);
		case 10:
			return precpred(_ctx, 2);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3*\u0124\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3"+
		"\2\3\2\3\2\3\2\5\2+\n\2\3\2\5\2.\n\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3"+
		"\2\3\2\3\2\3\2\3\2\3\2\7\2>\n\2\f\2\16\2A\13\2\3\2\3\2\3\2\3\2\5\2G\n"+
		"\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\7\2R\n\2\f\2\16\2U\13\2\3\3\3\3"+
		"\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\7\3a\n\3\f\3\16\3d\13\3\3\4\3\4\3\4\3"+
		"\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\7\5s\n\5\f\5\16\5v\13\5\3\6\3\6"+
		"\3\6\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\b\3\b\7\b\u0088\n\b"+
		"\f\b\16\b\u008b\13\b\3\b\3\b\7\b\u008f\n\b\f\b\16\b\u0092\13\b\3\b\3\b"+
		"\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\7\t\u00a6"+
		"\n\t\f\t\16\t\u00a9\13\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3"+
		"\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\5\t\u00c4\n\t\3"+
		"\t\3\t\3\t\3\t\3\t\3\t\7\t\u00cc\n\t\f\t\16\t\u00cf\13\t\3\n\3\n\3\n\3"+
		"\n\3\n\3\n\3\n\3\n\3\n\3\n\5\n\u00db\n\n\3\13\3\13\3\13\3\13\3\13\3\13"+
		"\3\13\3\13\3\13\3\13\3\13\3\13\3\13\5\13\u00ea\n\13\3\13\3\13\3\13\3\13"+
		"\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\7\13\u00fa\n\13\f\13"+
		"\16\13\u00fd\13\13\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f"+
		"\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\5\f\u0117\n\f\3\f\3\f\3\f"+
		"\3\f\3\f\3\f\7\f\u011f\n\f\f\f\16\f\u0122\13\f\3\f\2\6\2\20\24\26\r\2"+
		"\4\6\b\n\f\16\20\22\24\26\2\2\2\u0148\2F\3\2\2\2\4V\3\2\2\2\6e\3\2\2\2"+
		"\bh\3\2\2\2\nw\3\2\2\2\fz\3\2\2\2\16\u0085\3\2\2\2\20\u00c3\3\2\2\2\22"+
		"\u00da\3\2\2\2\24\u00e9\3\2\2\2\26\u0116\3\2\2\2\30\31\b\2\1\2\31G\5\22"+
		"\n\2\32\33\7\3\2\2\33\34\5\2\2\2\34\35\7\4\2\2\35G\3\2\2\2\36\37\7\b\2"+
		"\2\37 \7)\2\2 !\7\t\2\2!\"\7\n\2\2\"#\5\2\2\2#$\7\13\2\2$%\7\f\2\2%&\7"+
		")\2\2&\'\7\t\2\2\'G\3\2\2\2(*\5\4\3\2)+\5\b\5\2*)\3\2\2\2*+\3\2\2\2+-"+
		"\3\2\2\2,.\5\6\4\2-,\3\2\2\2-.\3\2\2\2./\3\2\2\2/\60\5\n\6\2\60G\3\2\2"+
		"\2\61\62\5\b\5\2\62\63\5\2\2\7\63G\3\2\2\2\64\65\7\r\2\2\65G\7)\2\2\66"+
		"G\7(\2\2\67G\5\f\7\289\7\b\2\29:\7)\2\2:;\7\t\2\2;?\5\2\2\2<>\5\2\2\2"+
		"=<\3\2\2\2>A\3\2\2\2?=\3\2\2\2?@\3\2\2\2@B\3\2\2\2A?\3\2\2\2BC\7\f\2\2"+
		"CD\7)\2\2DE\7\t\2\2EG\3\2\2\2F\30\3\2\2\2F\32\3\2\2\2F\36\3\2\2\2F(\3"+
		"\2\2\2F\61\3\2\2\2F\64\3\2\2\2F\66\3\2\2\2F\67\3\2\2\2F8\3\2\2\2GS\3\2"+
		"\2\2HI\f\n\2\2IJ\7\7\2\2JR\5\2\2\13KL\f\f\2\2LM\7\5\2\2MR\5\24\13\2NO"+
		"\f\13\2\2OP\7\6\2\2PR\5\24\13\2QH\3\2\2\2QK\3\2\2\2QN\3\2\2\2RU\3\2\2"+
		"\2SQ\3\2\2\2ST\3\2\2\2T\3\3\2\2\2US\3\2\2\2VW\7\16\2\2WX\7\r\2\2XY\7)"+
		"\2\2YZ\7\17\2\2Zb\5\2\2\2[\\\7\7\2\2\\]\7\r\2\2]^\7)\2\2^_\7\17\2\2_a"+
		"\5\2\2\2`[\3\2\2\2ad\3\2\2\2b`\3\2\2\2bc\3\2\2\2c\5\3\2\2\2db\3\2\2\2"+
		"ef\7\20\2\2fg\5\20\t\2g\7\3\2\2\2hi\7\21\2\2ij\7\r\2\2jk\7)\2\2kl\7\22"+
		"\2\2lt\5\2\2\2mn\7\7\2\2no\7\r\2\2op\7)\2\2pq\7\22\2\2qs\5\2\2\2rm\3\2"+
		"\2\2sv\3\2\2\2tr\3\2\2\2tu\3\2\2\2u\t\3\2\2\2vt\3\2\2\2wx\7\23\2\2xy\5"+
		"\2\2\2y\13\3\2\2\2z{\7\24\2\2{|\7\3\2\2|}\5\2\2\2}~\7\7\2\2~\177\5\2\2"+
		"\2\177\u0080\7\7\2\2\u0080\u0081\5\16\b\2\u0081\u0082\7\7\2\2\u0082\u0083"+
		"\5\16\b\2\u0083\u0084\7\4\2\2\u0084\r\3\2\2\2\u0085\u0089\7\25\2\2\u0086"+
		"\u0088\7)\2\2\u0087\u0086\3\2\2\2\u0088\u008b\3\2\2\2\u0089\u0087\3\2"+
		"\2\2\u0089\u008a\3\2\2\2\u008a\u0090\3\2\2\2\u008b\u0089\3\2\2\2\u008c"+
		"\u008d\7\7\2\2\u008d\u008f\7)\2\2\u008e\u008c\3\2\2\2\u008f\u0092\3\2"+
		"\2\2\u0090\u008e\3\2\2\2\u0090\u0091\3\2\2\2\u0091\u0093\3\2\2\2\u0092"+
		"\u0090\3\2\2\2\u0093\u0094\7\26\2\2\u0094\17\3\2\2\2\u0095\u0096\b\t\1"+
		"\2\u0096\u0097\7\27\2\2\u0097\u0098\7\3\2\2\u0098\u0099\5\2\2\2\u0099"+
		"\u009a\7\4\2\2\u009a\u00c4\3\2\2\2\u009b\u009c\7\30\2\2\u009c\u009d\7"+
		"\r\2\2\u009d\u009e\7)\2\2\u009e\u009f\7\17\2\2\u009f\u00a7\5\2\2\2\u00a0"+
		"\u00a1\7\7\2\2\u00a1\u00a2\7\r\2\2\u00a2\u00a3\7)\2\2\u00a3\u00a4\7\17"+
		"\2\2\u00a4\u00a6\5\2\2\2\u00a5\u00a0\3\2\2\2\u00a6\u00a9\3\2\2\2\u00a7"+
		"\u00a5\3\2\2\2\u00a7\u00a8\3\2\2\2\u00a8\u00aa\3\2\2\2\u00a9\u00a7\3\2"+
		"\2\2\u00aa\u00ab\7\31\2\2\u00ab\u00ac\5\20\t\13\u00ac\u00c4\3\2\2\2\u00ad"+
		"\u00ae\7\3\2\2\u00ae\u00af\5\20\t\2\u00af\u00b0\7\4\2\2\u00b0\u00c4\3"+
		"\2\2\2\u00b1\u00b2\7\33\2\2\u00b2\u00c4\5\20\t\b\u00b3\u00b4\5\2\2\2\u00b4"+
		"\u00b5\7\35\2\2\u00b5\u00b6\5\2\2\2\u00b6\u00c4\3\2\2\2\u00b7\u00b8\5"+
		"\2\2\2\u00b8\u00b9\7\36\2\2\u00b9\u00ba\5\2\2\2\u00ba\u00c4\3\2\2\2\u00bb"+
		"\u00bc\5\2\2\2\u00bc\u00bd\7\37\2\2\u00bd\u00be\5\2\2\2\u00be\u00c4\3"+
		"\2\2\2\u00bf\u00c0\5\2\2\2\u00c0\u00c1\7 \2\2\u00c1\u00c2\5\2\2\2\u00c2"+
		"\u00c4\3\2\2\2\u00c3\u0095\3\2\2\2\u00c3\u009b\3\2\2\2\u00c3\u00ad\3\2"+
		"\2\2\u00c3\u00b1\3\2\2\2\u00c3\u00b3\3\2\2\2\u00c3\u00b7\3\2\2\2\u00c3"+
		"\u00bb\3\2\2\2\u00c3\u00bf\3\2\2\2\u00c4\u00cd\3\2\2\2\u00c5\u00c6\f\n"+
		"\2\2\u00c6\u00c7\7\32\2\2\u00c7\u00cc\5\20\t\13\u00c8\u00c9\f\7\2\2\u00c9"+
		"\u00ca\7\34\2\2\u00ca\u00cc\5\20\t\b\u00cb\u00c5\3\2\2\2\u00cb\u00c8\3"+
		"\2\2\2\u00cc\u00cf\3\2\2\2\u00cd\u00cb\3\2\2\2\u00cd\u00ce\3\2\2\2\u00ce"+
		"\21\3\2\2\2\u00cf\u00cd\3\2\2\2\u00d0\u00d1\7!\2\2\u00d1\u00d2\7)\2\2"+
		"\u00d2\u00d3\7\"\2\2\u00d3\u00d4\7\5\2\2\u00d4\u00db\5\24\13\2\u00d5\u00d6"+
		"\7!\2\2\u00d6\u00d7\7)\2\2\u00d7\u00d8\7\"\2\2\u00d8\u00d9\7\6\2\2\u00d9"+
		"\u00db\5\24\13\2\u00da\u00d0\3\2\2\2\u00da\u00d5\3\2\2\2\u00db\23\3\2"+
		"\2\2\u00dc\u00dd\b\13\1\2\u00dd\u00ea\7)\2\2\u00de\u00ea\7#\2\2\u00df"+
		"\u00ea\7$\2\2\u00e0\u00ea\7%\2\2\u00e1\u00ea\7$\2\2\u00e2\u00ea\7&\2\2"+
		"\u00e3\u00e4\7\'\2\2\u00e4\u00ea\7)\2\2\u00e5\u00e6\7\3\2\2\u00e6\u00e7"+
		"\5\24\13\2\u00e7\u00e8\7\4\2\2\u00e8\u00ea\3\2\2\2\u00e9\u00dc\3\2\2\2"+
		"\u00e9\u00de\3\2\2\2\u00e9\u00df\3\2\2\2\u00e9\u00e0\3\2\2\2\u00e9\u00e1"+
		"\3\2\2\2\u00e9\u00e2\3\2\2\2\u00e9\u00e3\3\2\2\2\u00e9\u00e5\3\2\2\2\u00ea"+
		"\u00fb\3\2\2\2\u00eb\u00ec\f\6\2\2\u00ec\u00ed\7\5\2\2\u00ed\u00fa\5\24"+
		"\13\7\u00ee\u00ef\f\5\2\2\u00ef\u00f0\7\6\2\2\u00f0\u00fa\5\24\13\6\u00f1"+
		"\u00f2\f\3\2\2\u00f2\u00f3\7\7\2\2\u00f3\u00fa\5\24\13\4\u00f4\u00f5\f"+
		"\4\2\2\u00f5\u00f6\7\25\2\2\u00f6\u00f7\5\26\f\2\u00f7\u00f8\7\26\2\2"+
		"\u00f8\u00fa\3\2\2\2\u00f9\u00eb\3\2\2\2\u00f9\u00ee\3\2\2\2\u00f9\u00f1"+
		"\3\2\2\2\u00f9\u00f4\3\2\2\2\u00fa\u00fd\3\2\2\2\u00fb\u00f9\3\2\2\2\u00fb"+
		"\u00fc\3\2\2\2\u00fc\25\3\2\2\2\u00fd\u00fb\3\2\2\2\u00fe\u00ff\b\f\1"+
		"\2\u00ff\u0117\5\24\13\2\u0100\u0101\5\24\13\2\u0101\u0102\7\35\2\2\u0102"+
		"\u0103\5\24\13\2\u0103\u0117\3\2\2\2\u0104\u0105\5\24\13\2\u0105\u0106"+
		"\7\36\2\2\u0106\u0107\5\24\13\2\u0107\u0117\3\2\2\2\u0108\u0109\5\24\13"+
		"\2\u0109\u010a\7\37\2\2\u010a\u010b\5\24\13\2\u010b\u0117\3\2\2\2\u010c"+
		"\u010d\5\24\13\2\u010d\u010e\7 \2\2\u010e\u010f\5\24\13\2\u010f\u0117"+
		"\3\2\2\2\u0110\u0111\7\3\2\2\u0111\u0112\5\26\f\2\u0112\u0113\7\4\2\2"+
		"\u0113\u0117\3\2\2\2\u0114\u0115\7\33\2\2\u0115\u0117\5\26\f\3\u0116\u00fe"+
		"\3\2\2\2\u0116\u0100\3\2\2\2\u0116\u0104\3\2\2\2\u0116\u0108\3\2\2\2\u0116"+
		"\u010c\3\2\2\2\u0116\u0110\3\2\2\2\u0116\u0114\3\2\2\2\u0117\u0120\3\2"+
		"\2\2\u0118\u0119\f\5\2\2\u0119\u011a\7\34\2\2\u011a\u011f\5\26\f\6\u011b"+
		"\u011c\f\4\2\2\u011c\u011d\7\32\2\2\u011d\u011f\5\26\f\5\u011e\u0118\3"+
		"\2\2\2\u011e\u011b\3\2\2\2\u011f\u0122\3\2\2\2\u0120\u011e\3\2\2\2\u0120"+
		"\u0121\3\2\2\2\u0121\27\3\2\2\2\u0122\u0120\3\2\2\2\27*-?FQSbt\u0089\u0090"+
		"\u00a7\u00c3\u00cb\u00cd\u00da\u00e9\u00f9\u00fb\u0116\u011e\u0120";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}