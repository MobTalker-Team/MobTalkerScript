// Generated from C:\Users\Tobsen\Desktop\Minecraft\MobTalker2\MobTalkerScript\MobTalkerScript\src\mobtalkerscript\mts\v1\parser\Mts.g4 by ANTLR 4.1
package mobtalkerscript.mts.v1.parser;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class MtsLexer extends Lexer {
	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		WS=1, LOCAL=2, FUNCTION=3, LABEL=4, JUMP=5, CALL=6, RETURN=7, END=8, DO=9, 
		IF=10, THEN=11, ELSEIF=12, ELSE=13, WHILE=14, BREAK=15, REPEAT=16, UNTIL=17, 
		FOR=18, IN=19, CMD_SAY=20, CMD_SHOW=21, CMD_AT=22, CMD_OFFSET=23, CMD_SCENE=24, 
		CMD_HIDE=25, CMD_MENU=26, CMD_OPTION=27, VARIABLE_KEY=28, LPAREN=29, RPAREN=30, 
		LBRACE=31, RBRACE=32, LBRACK=33, RBRACK=34, SEMI=35, COMMA=36, DOT=37, 
		ASSIGN=38, GT=39, LT=40, NOT=41, EQUAL=42, LE=43, GE=44, NOTEQUAL=45, 
		AND=46, OR=47, ADD=48, SUB=49, MUL=50, DIV=51, MOD=52, CONCAT=53, LineComment=54, 
		BlockComment=55, NullLiteral=56, BooleanLiteral=57, StringLiteral=58, 
		Identifier=59, NumberLiteral=60;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] tokenNames = {
		"<INVALID>",
		"WS", "'local'", "'function'", "'label'", "'jump'", "'call'", "'return'", 
		"'end'", "'do'", "'if'", "'then'", "'else if'", "'else'", "'while'", "'break'", 
		"'repeat'", "'until'", "'for'", "'in'", "'say'", "'show'", "'at'", "'offset'", 
		"'scene'", "'hide'", "'menu'", "'option'", "'$'", "'('", "')'", "'{'", 
		"'}'", "'['", "']'", "';'", "','", "'.'", "'='", "'>'", "'<'", "'not'", 
		"'=='", "'<='", "'>='", "'!='", "'and'", "'or'", "'+'", "'-'", "'*'", 
		"'/'", "'%'", "'..'", "LineComment", "BlockComment", "'nil'", "BooleanLiteral", 
		"StringLiteral", "Identifier", "NumberLiteral"
	};
	public static final String[] ruleNames = {
		"WS", "LOCAL", "FUNCTION", "LABEL", "JUMP", "CALL", "RETURN", "END", "DO", 
		"IF", "THEN", "ELSEIF", "ELSE", "WHILE", "BREAK", "REPEAT", "UNTIL", "FOR", 
		"IN", "CMD_SAY", "CMD_SHOW", "CMD_AT", "CMD_OFFSET", "CMD_SCENE", "CMD_HIDE", 
		"CMD_MENU", "CMD_OPTION", "VARIABLE_KEY", "LPAREN", "RPAREN", "LBRACE", 
		"RBRACE", "LBRACK", "RBRACK", "SEMI", "COMMA", "DOT", "ASSIGN", "GT", 
		"LT", "NOT", "EQUAL", "LE", "GE", "NOTEQUAL", "AND", "OR", "ADD", "SUB", 
		"MUL", "DIV", "MOD", "CONCAT", "LineComment", "BlockComment", "NullLiteral", 
		"BooleanLiteral", "StringLiteral", "EscapeSequence", "Identifier", "NumberLiteral", 
		"Digit"
	};


	public MtsLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "Mts.g4"; }

	@Override
	public String[] getTokenNames() { return tokenNames; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	@Override
	public void action(RuleContext _localctx, int ruleIndex, int actionIndex) {
		switch (ruleIndex) {
		case 0: WS_action((RuleContext)_localctx, actionIndex); break;

		case 53: LineComment_action((RuleContext)_localctx, actionIndex); break;

		case 54: BlockComment_action((RuleContext)_localctx, actionIndex); break;
		}
	}
	private void BlockComment_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 2: skip();  break;
		}
	}
	private void LineComment_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 1: skip();  break;
		}
	}
	private void WS_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 0: skip();  break;
		}
	}

	public static final String _serializedATN =
		"\3\uacf5\uee8c\u4f5d\u8b0d\u4a45\u78bd\u1b2f\u3378\2>\u019f\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\4"+
		",\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64\t"+
		"\64\4\65\t\65\4\66\t\66\4\67\t\67\48\t8\49\t9\4:\t:\4;\t;\4<\t<\4=\t="+
		"\4>\t>\4?\t?\3\2\6\2\u0081\n\2\r\2\16\2\u0082\3\2\3\2\3\3\3\3\3\3\3\3"+
		"\3\3\3\3\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\5\3\5\3\5\3\5\3\5\3\5\3"+
		"\6\3\6\3\6\3\6\3\6\3\7\3\7\3\7\3\7\3\7\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\t"+
		"\3\t\3\t\3\t\3\n\3\n\3\n\3\13\3\13\3\13\3\f\3\f\3\f\3\f\3\f\3\r\3\r\3"+
		"\r\3\r\3\r\3\r\3\r\3\r\3\16\3\16\3\16\3\16\3\16\3\17\3\17\3\17\3\17\3"+
		"\17\3\17\3\20\3\20\3\20\3\20\3\20\3\20\3\21\3\21\3\21\3\21\3\21\3\21\3"+
		"\21\3\22\3\22\3\22\3\22\3\22\3\22\3\23\3\23\3\23\3\23\3\24\3\24\3\24\3"+
		"\25\3\25\3\25\3\25\3\26\3\26\3\26\3\26\3\26\3\27\3\27\3\27\3\30\3\30\3"+
		"\30\3\30\3\30\3\30\3\30\3\31\3\31\3\31\3\31\3\31\3\31\3\32\3\32\3\32\3"+
		"\32\3\32\3\33\3\33\3\33\3\33\3\33\3\34\3\34\3\34\3\34\3\34\3\34\3\34\3"+
		"\35\3\35\3\36\3\36\3\37\3\37\3 \3 \3!\3!\3\"\3\"\3#\3#\3$\3$\3%\3%\3&"+
		"\3&\3\'\3\'\3(\3(\3)\3)\3*\3*\3*\3*\3+\3+\3+\3,\3,\3,\3-\3-\3-\3.\3.\3"+
		".\3/\3/\3/\3/\3\60\3\60\3\60\3\61\3\61\3\62\3\62\3\63\3\63\3\64\3\64\3"+
		"\65\3\65\3\66\3\66\3\66\3\67\3\67\3\67\3\67\7\67\u0155\n\67\f\67\16\67"+
		"\u0158\13\67\3\67\3\67\38\38\38\38\78\u0160\n8\f8\168\u0163\138\38\38"+
		"\38\38\38\39\39\39\39\3:\3:\3:\3:\3:\3:\3:\3:\3:\5:\u0177\n:\3;\3;\3;"+
		"\7;\u017c\n;\f;\16;\u017f\13;\3;\3;\3<\3<\3<\3=\6=\u0187\n=\r=\16=\u0188"+
		"\3=\7=\u018c\n=\f=\16=\u018f\13=\3>\6>\u0192\n>\r>\16>\u0193\3>\3>\6>"+
		"\u0198\n>\r>\16>\u0199\5>\u019c\n>\3?\3?\3\u0161@\3\3\2\5\4\1\7\5\1\t"+
		"\6\1\13\7\1\r\b\1\17\t\1\21\n\1\23\13\1\25\f\1\27\r\1\31\16\1\33\17\1"+
		"\35\20\1\37\21\1!\22\1#\23\1%\24\1\'\25\1)\26\1+\27\1-\30\1/\31\1\61\32"+
		"\1\63\33\1\65\34\1\67\35\19\36\1;\37\1= \1?!\1A\"\1C#\1E$\1G%\1I&\1K\'"+
		"\1M(\1O)\1Q*\1S+\1U,\1W-\1Y.\1[/\1]\60\1_\61\1a\62\1c\63\1e\64\1g\65\1"+
		"i\66\1k\67\1m8\3o9\4q:\1s;\1u<\1w\2\1y=\1{>\1}\2\1\3\2\t\5\2\13\f\16\17"+
		"\"\"\4\2\f\f\17\17\4\2$$^^\6\2$$&&^^pp\5\2C\\aac|\6\2\62;C\\aac|\3\2\62"+
		";\u01a7\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2"+
		"\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3"+
		"\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2"+
		"\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2"+
		"/\3\2\2\2\2\61\3\2\2\2\2\63\3\2\2\2\2\65\3\2\2\2\2\67\3\2\2\2\29\3\2\2"+
		"\2\2;\3\2\2\2\2=\3\2\2\2\2?\3\2\2\2\2A\3\2\2\2\2C\3\2\2\2\2E\3\2\2\2\2"+
		"G\3\2\2\2\2I\3\2\2\2\2K\3\2\2\2\2M\3\2\2\2\2O\3\2\2\2\2Q\3\2\2\2\2S\3"+
		"\2\2\2\2U\3\2\2\2\2W\3\2\2\2\2Y\3\2\2\2\2[\3\2\2\2\2]\3\2\2\2\2_\3\2\2"+
		"\2\2a\3\2\2\2\2c\3\2\2\2\2e\3\2\2\2\2g\3\2\2\2\2i\3\2\2\2\2k\3\2\2\2\2"+
		"m\3\2\2\2\2o\3\2\2\2\2q\3\2\2\2\2s\3\2\2\2\2u\3\2\2\2\2y\3\2\2\2\2{\3"+
		"\2\2\2\3\u0080\3\2\2\2\5\u0086\3\2\2\2\7\u008c\3\2\2\2\t\u0095\3\2\2\2"+
		"\13\u009b\3\2\2\2\r\u00a0\3\2\2\2\17\u00a5\3\2\2\2\21\u00ac\3\2\2\2\23"+
		"\u00b0\3\2\2\2\25\u00b3\3\2\2\2\27\u00b6\3\2\2\2\31\u00bb\3\2\2\2\33\u00c3"+
		"\3\2\2\2\35\u00c8\3\2\2\2\37\u00ce\3\2\2\2!\u00d4\3\2\2\2#\u00db\3\2\2"+
		"\2%\u00e1\3\2\2\2\'\u00e5\3\2\2\2)\u00e8\3\2\2\2+\u00ec\3\2\2\2-\u00f1"+
		"\3\2\2\2/\u00f4\3\2\2\2\61\u00fb\3\2\2\2\63\u0101\3\2\2\2\65\u0106\3\2"+
		"\2\2\67\u010b\3\2\2\29\u0112\3\2\2\2;\u0114\3\2\2\2=\u0116\3\2\2\2?\u0118"+
		"\3\2\2\2A\u011a\3\2\2\2C\u011c\3\2\2\2E\u011e\3\2\2\2G\u0120\3\2\2\2I"+
		"\u0122\3\2\2\2K\u0124\3\2\2\2M\u0126\3\2\2\2O\u0128\3\2\2\2Q\u012a\3\2"+
		"\2\2S\u012c\3\2\2\2U\u0130\3\2\2\2W\u0133\3\2\2\2Y\u0136\3\2\2\2[\u0139"+
		"\3\2\2\2]\u013c\3\2\2\2_\u0140\3\2\2\2a\u0143\3\2\2\2c\u0145\3\2\2\2e"+
		"\u0147\3\2\2\2g\u0149\3\2\2\2i\u014b\3\2\2\2k\u014d\3\2\2\2m\u0150\3\2"+
		"\2\2o\u015b\3\2\2\2q\u0169\3\2\2\2s\u0176\3\2\2\2u\u0178\3\2\2\2w\u0182"+
		"\3\2\2\2y\u0186\3\2\2\2{\u0191\3\2\2\2}\u019d\3\2\2\2\177\u0081\t\2\2"+
		"\2\u0080\177\3\2\2\2\u0081\u0082\3\2\2\2\u0082\u0080\3\2\2\2\u0082\u0083"+
		"\3\2\2\2\u0083\u0084\3\2\2\2\u0084\u0085\b\2\2\2\u0085\4\3\2\2\2\u0086"+
		"\u0087\7n\2\2\u0087\u0088\7q\2\2\u0088\u0089\7e\2\2\u0089\u008a\7c\2\2"+
		"\u008a\u008b\7n\2\2\u008b\6\3\2\2\2\u008c\u008d\7h\2\2\u008d\u008e\7w"+
		"\2\2\u008e\u008f\7p\2\2\u008f\u0090\7e\2\2\u0090\u0091\7v\2\2\u0091\u0092"+
		"\7k\2\2\u0092\u0093\7q\2\2\u0093\u0094\7p\2\2\u0094\b\3\2\2\2\u0095\u0096"+
		"\7n\2\2\u0096\u0097\7c\2\2\u0097\u0098\7d\2\2\u0098\u0099\7g\2\2\u0099"+
		"\u009a\7n\2\2\u009a\n\3\2\2\2\u009b\u009c\7l\2\2\u009c\u009d\7w\2\2\u009d"+
		"\u009e\7o\2\2\u009e\u009f\7r\2\2\u009f\f\3\2\2\2\u00a0\u00a1\7e\2\2\u00a1"+
		"\u00a2\7c\2\2\u00a2\u00a3\7n\2\2\u00a3\u00a4\7n\2\2\u00a4\16\3\2\2\2\u00a5"+
		"\u00a6\7t\2\2\u00a6\u00a7\7g\2\2\u00a7\u00a8\7v\2\2\u00a8\u00a9\7w\2\2"+
		"\u00a9\u00aa\7t\2\2\u00aa\u00ab\7p\2\2\u00ab\20\3\2\2\2\u00ac\u00ad\7"+
		"g\2\2\u00ad\u00ae\7p\2\2\u00ae\u00af\7f\2\2\u00af\22\3\2\2\2\u00b0\u00b1"+
		"\7f\2\2\u00b1\u00b2\7q\2\2\u00b2\24\3\2\2\2\u00b3\u00b4\7k\2\2\u00b4\u00b5"+
		"\7h\2\2\u00b5\26\3\2\2\2\u00b6\u00b7\7v\2\2\u00b7\u00b8\7j\2\2\u00b8\u00b9"+
		"\7g\2\2\u00b9\u00ba\7p\2\2\u00ba\30\3\2\2\2\u00bb\u00bc\7g\2\2\u00bc\u00bd"+
		"\7n\2\2\u00bd\u00be\7u\2\2\u00be\u00bf\7g\2\2\u00bf\u00c0\7\"\2\2\u00c0"+
		"\u00c1\7k\2\2\u00c1\u00c2\7h\2\2\u00c2\32\3\2\2\2\u00c3\u00c4\7g\2\2\u00c4"+
		"\u00c5\7n\2\2\u00c5\u00c6\7u\2\2\u00c6\u00c7\7g\2\2\u00c7\34\3\2\2\2\u00c8"+
		"\u00c9\7y\2\2\u00c9\u00ca\7j\2\2\u00ca\u00cb\7k\2\2\u00cb\u00cc\7n\2\2"+
		"\u00cc\u00cd\7g\2\2\u00cd\36\3\2\2\2\u00ce\u00cf\7d\2\2\u00cf\u00d0\7"+
		"t\2\2\u00d0\u00d1\7g\2\2\u00d1\u00d2\7c\2\2\u00d2\u00d3\7m\2\2\u00d3 "+
		"\3\2\2\2\u00d4\u00d5\7t\2\2\u00d5\u00d6\7g\2\2\u00d6\u00d7\7r\2\2\u00d7"+
		"\u00d8\7g\2\2\u00d8\u00d9\7c\2\2\u00d9\u00da\7v\2\2\u00da\"\3\2\2\2\u00db"+
		"\u00dc\7w\2\2\u00dc\u00dd\7p\2\2\u00dd\u00de\7v\2\2\u00de\u00df\7k\2\2"+
		"\u00df\u00e0\7n\2\2\u00e0$\3\2\2\2\u00e1\u00e2\7h\2\2\u00e2\u00e3\7q\2"+
		"\2\u00e3\u00e4\7t\2\2\u00e4&\3\2\2\2\u00e5\u00e6\7k\2\2\u00e6\u00e7\7"+
		"p\2\2\u00e7(\3\2\2\2\u00e8\u00e9\7u\2\2\u00e9\u00ea\7c\2\2\u00ea\u00eb"+
		"\7{\2\2\u00eb*\3\2\2\2\u00ec\u00ed\7u\2\2\u00ed\u00ee\7j\2\2\u00ee\u00ef"+
		"\7q\2\2\u00ef\u00f0\7y\2\2\u00f0,\3\2\2\2\u00f1\u00f2\7c\2\2\u00f2\u00f3"+
		"\7v\2\2\u00f3.\3\2\2\2\u00f4\u00f5\7q\2\2\u00f5\u00f6\7h\2\2\u00f6\u00f7"+
		"\7h\2\2\u00f7\u00f8\7u\2\2\u00f8\u00f9\7g\2\2\u00f9\u00fa\7v\2\2\u00fa"+
		"\60\3\2\2\2\u00fb\u00fc\7u\2\2\u00fc\u00fd\7e\2\2\u00fd\u00fe\7g\2\2\u00fe"+
		"\u00ff\7p\2\2\u00ff\u0100\7g\2\2\u0100\62\3\2\2\2\u0101\u0102\7j\2\2\u0102"+
		"\u0103\7k\2\2\u0103\u0104\7f\2\2\u0104\u0105\7g\2\2\u0105\64\3\2\2\2\u0106"+
		"\u0107\7o\2\2\u0107\u0108\7g\2\2\u0108\u0109\7p\2\2\u0109\u010a\7w\2\2"+
		"\u010a\66\3\2\2\2\u010b\u010c\7q\2\2\u010c\u010d\7r\2\2\u010d\u010e\7"+
		"v\2\2\u010e\u010f\7k\2\2\u010f\u0110\7q\2\2\u0110\u0111\7p\2\2\u01118"+
		"\3\2\2\2\u0112\u0113\7&\2\2\u0113:\3\2\2\2\u0114\u0115\7*\2\2\u0115<\3"+
		"\2\2\2\u0116\u0117\7+\2\2\u0117>\3\2\2\2\u0118\u0119\7}\2\2\u0119@\3\2"+
		"\2\2\u011a\u011b\7\177\2\2\u011bB\3\2\2\2\u011c\u011d\7]\2\2\u011dD\3"+
		"\2\2\2\u011e\u011f\7_\2\2\u011fF\3\2\2\2\u0120\u0121\7=\2\2\u0121H\3\2"+
		"\2\2\u0122\u0123\7.\2\2\u0123J\3\2\2\2\u0124\u0125\7\60\2\2\u0125L\3\2"+
		"\2\2\u0126\u0127\7?\2\2\u0127N\3\2\2\2\u0128\u0129\7@\2\2\u0129P\3\2\2"+
		"\2\u012a\u012b\7>\2\2\u012bR\3\2\2\2\u012c\u012d\7p\2\2\u012d\u012e\7"+
		"q\2\2\u012e\u012f\7v\2\2\u012fT\3\2\2\2\u0130\u0131\7?\2\2\u0131\u0132"+
		"\7?\2\2\u0132V\3\2\2\2\u0133\u0134\7>\2\2\u0134\u0135\7?\2\2\u0135X\3"+
		"\2\2\2\u0136\u0137\7@\2\2\u0137\u0138\7?\2\2\u0138Z\3\2\2\2\u0139\u013a"+
		"\7#\2\2\u013a\u013b\7?\2\2\u013b\\\3\2\2\2\u013c\u013d\7c\2\2\u013d\u013e"+
		"\7p\2\2\u013e\u013f\7f\2\2\u013f^\3\2\2\2\u0140\u0141\7q\2\2\u0141\u0142"+
		"\7t\2\2\u0142`\3\2\2\2\u0143\u0144\7-\2\2\u0144b\3\2\2\2\u0145\u0146\7"+
		"/\2\2\u0146d\3\2\2\2\u0147\u0148\7,\2\2\u0148f\3\2\2\2\u0149\u014a\7\61"+
		"\2\2\u014ah\3\2\2\2\u014b\u014c\7\'\2\2\u014cj\3\2\2\2\u014d\u014e\7\60"+
		"\2\2\u014e\u014f\7\60\2\2\u014fl\3\2\2\2\u0150\u0151\7\61\2\2\u0151\u0152"+
		"\7\61\2\2\u0152\u0156\3\2\2\2\u0153\u0155\n\3\2\2\u0154\u0153\3\2\2\2"+
		"\u0155\u0158\3\2\2\2\u0156\u0154\3\2\2\2\u0156\u0157\3\2\2\2\u0157\u0159"+
		"\3\2\2\2\u0158\u0156\3\2\2\2\u0159\u015a\b\67\3\2\u015an\3\2\2\2\u015b"+
		"\u015c\7\61\2\2\u015c\u015d\7,\2\2\u015d\u0161\3\2\2\2\u015e\u0160\13"+
		"\2\2\2\u015f\u015e\3\2\2\2\u0160\u0163\3\2\2\2\u0161\u0162\3\2\2\2\u0161"+
		"\u015f\3\2\2\2\u0162\u0164\3\2\2\2\u0163\u0161\3\2\2\2\u0164\u0165\7,"+
		"\2\2\u0165\u0166\7\61\2\2\u0166\u0167\3\2\2\2\u0167\u0168\b8\4\2\u0168"+
		"p\3\2\2\2\u0169\u016a\7p\2\2\u016a\u016b\7k\2\2\u016b\u016c\7n\2\2\u016c"+
		"r\3\2\2\2\u016d\u016e\7v\2\2\u016e\u016f\7t\2\2\u016f\u0170\7w\2\2\u0170"+
		"\u0177\7g\2\2\u0171\u0172\7h\2\2\u0172\u0173\7c\2\2\u0173\u0174\7n\2\2"+
		"\u0174\u0175\7u\2\2\u0175\u0177\7g\2\2\u0176\u016d\3\2\2\2\u0176\u0171"+
		"\3\2\2\2\u0177t\3\2\2\2\u0178\u017d\7$\2\2\u0179\u017c\5w<\2\u017a\u017c"+
		"\n\4\2\2\u017b\u0179\3\2\2\2\u017b\u017a\3\2\2\2\u017c\u017f\3\2\2\2\u017d"+
		"\u017b\3\2\2\2\u017d\u017e\3\2\2\2\u017e\u0180\3\2\2\2\u017f\u017d\3\2"+
		"\2\2\u0180\u0181\7$\2\2\u0181v\3\2\2\2\u0182\u0183\7^\2\2\u0183\u0184"+
		"\t\5\2\2\u0184x\3\2\2\2\u0185\u0187\t\6\2\2\u0186\u0185\3\2\2\2\u0187"+
		"\u0188\3\2\2\2\u0188\u0186\3\2\2\2\u0188\u0189\3\2\2\2\u0189\u018d\3\2"+
		"\2\2\u018a\u018c\t\7\2\2\u018b\u018a\3\2\2\2\u018c\u018f\3\2\2\2\u018d"+
		"\u018b\3\2\2\2\u018d\u018e\3\2\2\2\u018ez\3\2\2\2\u018f\u018d\3\2\2\2"+
		"\u0190\u0192\5}?\2\u0191\u0190\3\2\2\2\u0192\u0193\3\2\2\2\u0193\u0191"+
		"\3\2\2\2\u0193\u0194\3\2\2\2\u0194\u019b\3\2\2\2\u0195\u0197\7\60\2\2"+
		"\u0196\u0198\5}?\2\u0197\u0196\3\2\2\2\u0198\u0199\3\2\2\2\u0199\u0197"+
		"\3\2\2\2\u0199\u019a\3\2\2\2\u019a\u019c\3\2\2\2\u019b\u0195\3\2\2\2\u019b"+
		"\u019c\3\2\2\2\u019c|\3\2\2\2\u019d\u019e\t\b\2\2\u019e~\3\2\2\2\16\2"+
		"\u0082\u0156\u0161\u0176\u017b\u017d\u0188\u018d\u0193\u0199\u019b";
	public static final ATN _ATN =
		ATNSimulator.deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}