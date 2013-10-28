// Generated from C:\Users\Tobsen\Desktop\Minecraft\MobTalker2\1.6.2\src\MobTalkerScript\mobtalkerscript\mts\v1\parser\Mts.g4 by ANTLR 4.1
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
		StmtCloser=1, Newline=2, Comment=3, Whitespace=4, Null=5, Boolean=6, LocalVar=7, 
		Function=8, Label=9, Jump=10, Call=11, Return=12, End=13, Do=14, If=15, 
		Then=16, ElseIf=17, Else=18, While=19, Break=20, Repeat=21, Until=22, 
		CommandSay=23, CommandShow=24, CommandShowAt=25, CommandShowOffset=26, 
		CommandScene=27, CommandHide=28, CommandMenu=29, CommandMenuOption=30, 
		Separator=31, ExprBraceOpen=32, ExprBraceClose=33, FieldBraceOpen=34, 
		FieldBraceClose=35, TableBraceOpen=36, TableBraceClose=37, IncrOp=38, 
		DecrOp=39, MinusOp=40, UnaryNotOp=41, UnarySizeOp=42, BinaryPlusOp=43, 
		BinaryMultiplyOp=44, BinaryDivideOp=45, BinaryModuloOp=46, BinaryConcatOp=47, 
		BinaryLowerThenOp=48, BinaryLowerThenOrEqualOP=49, BinaryGreaterThenOp=50, 
		BinaryGreaterThenOrEqualOp=51, BinaryEqualOp=52, BinaryNotEqualOp=53, 
		BinaryAndOp=54, BinaryOrOp=55, AssignOp=56, TableAssocOp=57, TableAccessOp=58, 
		String=59, VariableKey=60, Name=61, Number=62;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] tokenNames = {
		"<INVALID>",
		"';'", "Newline", "Comment", "Whitespace", "'nil'", "Boolean", "'local'", 
		"'function'", "'label'", "'jump'", "'call'", "'return'", "'end'", "'do'", 
		"'if'", "'then'", "'else if'", "'else'", "'while'", "'break'", "'repeat'", 
		"'until'", "'say'", "'show'", "'at'", "'offset'", "'scene'", "'hide'", 
		"'menu'", "'option'", "','", "'('", "')'", "'['", "']'", "'{'", "'}'", 
		"'++'", "'--'", "'-'", "'not'", "'#'", "'+'", "'*'", "'/'", "'%'", "'..'", 
		"'<'", "'<='", "'>'", "'>='", "'=='", "'!='", "'and'", "'or'", "'='", 
		"'=>'", "'.'", "String", "'$'", "Name", "Number"
	};
	public static final String[] ruleNames = {
		"StmtCloser", "Newline", "Comment", "Whitespace", "Null", "Boolean", "LocalVar", 
		"Function", "Label", "Jump", "Call", "Return", "End", "Do", "If", "Then", 
		"ElseIf", "Else", "While", "Break", "Repeat", "Until", "CommandSay", "CommandShow", 
		"CommandShowAt", "CommandShowOffset", "CommandScene", "CommandHide", "CommandMenu", 
		"CommandMenuOption", "Separator", "ExprBraceOpen", "ExprBraceClose", "FieldBraceOpen", 
		"FieldBraceClose", "TableBraceOpen", "TableBraceClose", "IncrOp", "DecrOp", 
		"MinusOp", "UnaryNotOp", "UnarySizeOp", "BinaryPlusOp", "BinaryMultiplyOp", 
		"BinaryDivideOp", "BinaryModuloOp", "BinaryConcatOp", "BinaryLowerThenOp", 
		"BinaryLowerThenOrEqualOP", "BinaryGreaterThenOp", "BinaryGreaterThenOrEqualOp", 
		"BinaryEqualOp", "BinaryNotEqualOp", "BinaryAndOp", "BinaryOrOp", "AssignOp", 
		"TableAssocOp", "TableAccessOp", "String", "VariableKey", "Name", "Number", 
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
		case 1: Newline_action((RuleContext)_localctx, actionIndex); break;

		case 2: Comment_action((RuleContext)_localctx, actionIndex); break;

		case 3: Whitespace_action((RuleContext)_localctx, actionIndex); break;
		}
	}
	private void Newline_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 0: skip();  break;
		}
	}
	private void Comment_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 1: skip();  break;
		}
	}
	private void Whitespace_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 2: skip();  break;
		}
	}

	public static final String _serializedATN =
		"\3\uacf5\uee8c\u4f5d\u8b0d\u4a45\u78bd\u1b2f\u3378\2@\u019f\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\4"+
		",\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64\t"+
		"\64\4\65\t\65\4\66\t\66\4\67\t\67\48\t8\49\t9\4:\t:\4;\t;\4<\t<\4=\t="+
		"\4>\t>\4?\t?\4@\t@\3\2\3\2\3\3\5\3\u0085\n\3\3\3\3\3\3\3\3\3\3\4\3\4\3"+
		"\4\3\4\7\4\u008f\n\4\f\4\16\4\u0092\13\4\3\4\5\4\u0095\n\4\3\4\3\4\3\4"+
		"\3\4\3\5\6\5\u009c\n\5\r\5\16\5\u009d\3\5\3\5\3\6\3\6\3\6\3\6\3\7\3\7"+
		"\3\7\3\7\3\7\3\7\3\7\3\7\3\7\5\7\u00af\n\7\3\b\3\b\3\b\3\b\3\b\3\b\3\t"+
		"\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\n\3\n\3\n\3\n\3\n\3\n\3\13\3\13\3\13"+
		"\3\13\3\13\3\f\3\f\3\f\3\f\3\f\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\16\3\16\3"+
		"\16\3\16\3\17\3\17\3\17\3\20\3\20\3\20\3\21\3\21\3\21\3\21\3\21\3\22\3"+
		"\22\3\22\3\22\3\22\3\22\3\22\3\22\3\23\3\23\3\23\3\23\3\23\3\24\3\24\3"+
		"\24\3\24\3\24\3\24\3\25\3\25\3\25\3\25\3\25\3\25\3\26\3\26\3\26\3\26\3"+
		"\26\3\26\3\26\3\27\3\27\3\27\3\27\3\27\3\27\3\30\3\30\3\30\3\30\3\31\3"+
		"\31\3\31\3\31\3\31\3\32\3\32\3\32\3\33\3\33\3\33\3\33\3\33\3\33\3\33\3"+
		"\34\3\34\3\34\3\34\3\34\3\34\3\35\3\35\3\35\3\35\3\35\3\36\3\36\3\36\3"+
		"\36\3\36\3\37\3\37\3\37\3\37\3\37\3\37\3\37\3 \3 \3!\3!\3\"\3\"\3#\3#"+
		"\3$\3$\3%\3%\3&\3&\3\'\3\'\3\'\3(\3(\3(\3)\3)\3*\3*\3*\3*\3+\3+\3,\3,"+
		"\3-\3-\3.\3.\3/\3/\3\60\3\60\3\60\3\61\3\61\3\62\3\62\3\62\3\63\3\63\3"+
		"\64\3\64\3\64\3\65\3\65\3\65\3\66\3\66\3\66\3\67\3\67\3\67\3\67\38\38"+
		"\38\39\39\3:\3:\3:\3;\3;\3<\3<\7<\u017d\n<\f<\16<\u0180\13<\3<\3<\3=\3"+
		"=\3>\6>\u0187\n>\r>\16>\u0188\3>\7>\u018c\n>\f>\16>\u018f\13>\3?\6?\u0192"+
		"\n?\r?\16?\u0193\3?\3?\6?\u0198\n?\r?\16?\u0199\5?\u019c\n?\3@\3@\3\u0090"+
		"A\3\3\1\5\4\2\7\5\3\t\6\4\13\7\1\r\b\1\17\t\1\21\n\1\23\13\1\25\f\1\27"+
		"\r\1\31\16\1\33\17\1\35\20\1\37\21\1!\22\1#\23\1%\24\1\'\25\1)\26\1+\27"+
		"\1-\30\1/\31\1\61\32\1\63\33\1\65\34\1\67\35\19\36\1;\37\1= \1?!\1A\""+
		"\1C#\1E$\1G%\1I&\1K\'\1M(\1O)\1Q*\1S+\1U,\1W-\1Y.\1[/\1]\60\1_\61\1a\62"+
		"\1c\63\1e\64\1g\65\1i\66\1k\67\1m8\1o9\1q:\1s;\1u<\1w=\1y>\1{?\1}@\1\177"+
		"\2\1\3\2\7\4\2\13\13\"\"\4\2$$^^\5\2C\\aac|\6\2\62;C\\aac|\3\2\62;\u01a8"+
		"\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2"+
		"\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2"+
		"\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2"+
		"\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2"+
		"\2\2\61\3\2\2\2\2\63\3\2\2\2\2\65\3\2\2\2\2\67\3\2\2\2\29\3\2\2\2\2;\3"+
		"\2\2\2\2=\3\2\2\2\2?\3\2\2\2\2A\3\2\2\2\2C\3\2\2\2\2E\3\2\2\2\2G\3\2\2"+
		"\2\2I\3\2\2\2\2K\3\2\2\2\2M\3\2\2\2\2O\3\2\2\2\2Q\3\2\2\2\2S\3\2\2\2\2"+
		"U\3\2\2\2\2W\3\2\2\2\2Y\3\2\2\2\2[\3\2\2\2\2]\3\2\2\2\2_\3\2\2\2\2a\3"+
		"\2\2\2\2c\3\2\2\2\2e\3\2\2\2\2g\3\2\2\2\2i\3\2\2\2\2k\3\2\2\2\2m\3\2\2"+
		"\2\2o\3\2\2\2\2q\3\2\2\2\2s\3\2\2\2\2u\3\2\2\2\2w\3\2\2\2\2y\3\2\2\2\2"+
		"{\3\2\2\2\2}\3\2\2\2\3\u0081\3\2\2\2\5\u0084\3\2\2\2\7\u008a\3\2\2\2\t"+
		"\u009b\3\2\2\2\13\u00a1\3\2\2\2\r\u00ae\3\2\2\2\17\u00b0\3\2\2\2\21\u00b6"+
		"\3\2\2\2\23\u00bf\3\2\2\2\25\u00c5\3\2\2\2\27\u00ca\3\2\2\2\31\u00cf\3"+
		"\2\2\2\33\u00d6\3\2\2\2\35\u00da\3\2\2\2\37\u00dd\3\2\2\2!\u00e0\3\2\2"+
		"\2#\u00e5\3\2\2\2%\u00ed\3\2\2\2\'\u00f2\3\2\2\2)\u00f8\3\2\2\2+\u00fe"+
		"\3\2\2\2-\u0105\3\2\2\2/\u010b\3\2\2\2\61\u010f\3\2\2\2\63\u0114\3\2\2"+
		"\2\65\u0117\3\2\2\2\67\u011e\3\2\2\29\u0124\3\2\2\2;\u0129\3\2\2\2=\u012e"+
		"\3\2\2\2?\u0135\3\2\2\2A\u0137\3\2\2\2C\u0139\3\2\2\2E\u013b\3\2\2\2G"+
		"\u013d\3\2\2\2I\u013f\3\2\2\2K\u0141\3\2\2\2M\u0143\3\2\2\2O\u0146\3\2"+
		"\2\2Q\u0149\3\2\2\2S\u014b\3\2\2\2U\u014f\3\2\2\2W\u0151\3\2\2\2Y\u0153"+
		"\3\2\2\2[\u0155\3\2\2\2]\u0157\3\2\2\2_\u0159\3\2\2\2a\u015c\3\2\2\2c"+
		"\u015e\3\2\2\2e\u0161\3\2\2\2g\u0163\3\2\2\2i\u0166\3\2\2\2k\u0169\3\2"+
		"\2\2m\u016c\3\2\2\2o\u0170\3\2\2\2q\u0173\3\2\2\2s\u0175\3\2\2\2u\u0178"+
		"\3\2\2\2w\u017a\3\2\2\2y\u0183\3\2\2\2{\u0186\3\2\2\2}\u0191\3\2\2\2\177"+
		"\u019d\3\2\2\2\u0081\u0082\7=\2\2\u0082\4\3\2\2\2\u0083\u0085\7\17\2\2"+
		"\u0084\u0083\3\2\2\2\u0084\u0085\3\2\2\2\u0085\u0086\3\2\2\2\u0086\u0087"+
		"\7\f\2\2\u0087\u0088\3\2\2\2\u0088\u0089\b\3\2\2\u0089\6\3\2\2\2\u008a"+
		"\u008b\7\61\2\2\u008b\u008c\7\61\2\2\u008c\u0090\3\2\2\2\u008d\u008f\13"+
		"\2\2\2\u008e\u008d\3\2\2\2\u008f\u0092\3\2\2\2\u0090\u0091\3\2\2\2\u0090"+
		"\u008e\3\2\2\2\u0091\u0094\3\2\2\2\u0092\u0090\3\2\2\2\u0093\u0095\7\17"+
		"\2\2\u0094\u0093\3\2\2\2\u0094\u0095\3\2\2\2\u0095\u0096\3\2\2\2\u0096"+
		"\u0097\7\f\2\2\u0097\u0098\3\2\2\2\u0098\u0099\b\4\3\2\u0099\b\3\2\2\2"+
		"\u009a\u009c\t\2\2\2\u009b\u009a\3\2\2\2\u009c\u009d\3\2\2\2\u009d\u009b"+
		"\3\2\2\2\u009d\u009e\3\2\2\2\u009e\u009f\3\2\2\2\u009f\u00a0\b\5\4\2\u00a0"+
		"\n\3\2\2\2\u00a1\u00a2\7p\2\2\u00a2\u00a3\7k\2\2\u00a3\u00a4\7n\2\2\u00a4"+
		"\f\3\2\2\2\u00a5\u00a6\7v\2\2\u00a6\u00a7\7t\2\2\u00a7\u00a8\7w\2\2\u00a8"+
		"\u00af\7g\2\2\u00a9\u00aa\7h\2\2\u00aa\u00ab\7c\2\2\u00ab\u00ac\7n\2\2"+
		"\u00ac\u00ad\7u\2\2\u00ad\u00af\7g\2\2\u00ae\u00a5\3\2\2\2\u00ae\u00a9"+
		"\3\2\2\2\u00af\16\3\2\2\2\u00b0\u00b1\7n\2\2\u00b1\u00b2\7q\2\2\u00b2"+
		"\u00b3\7e\2\2\u00b3\u00b4\7c\2\2\u00b4\u00b5\7n\2\2\u00b5\20\3\2\2\2\u00b6"+
		"\u00b7\7h\2\2\u00b7\u00b8\7w\2\2\u00b8\u00b9\7p\2\2\u00b9\u00ba\7e\2\2"+
		"\u00ba\u00bb\7v\2\2\u00bb\u00bc\7k\2\2\u00bc\u00bd\7q\2\2\u00bd\u00be"+
		"\7p\2\2\u00be\22\3\2\2\2\u00bf\u00c0\7n\2\2\u00c0\u00c1\7c\2\2\u00c1\u00c2"+
		"\7d\2\2\u00c2\u00c3\7g\2\2\u00c3\u00c4\7n\2\2\u00c4\24\3\2\2\2\u00c5\u00c6"+
		"\7l\2\2\u00c6\u00c7\7w\2\2\u00c7\u00c8\7o\2\2\u00c8\u00c9\7r\2\2\u00c9"+
		"\26\3\2\2\2\u00ca\u00cb\7e\2\2\u00cb\u00cc\7c\2\2\u00cc\u00cd\7n\2\2\u00cd"+
		"\u00ce\7n\2\2\u00ce\30\3\2\2\2\u00cf\u00d0\7t\2\2\u00d0\u00d1\7g\2\2\u00d1"+
		"\u00d2\7v\2\2\u00d2\u00d3\7w\2\2\u00d3\u00d4\7t\2\2\u00d4\u00d5\7p\2\2"+
		"\u00d5\32\3\2\2\2\u00d6\u00d7\7g\2\2\u00d7\u00d8\7p\2\2\u00d8\u00d9\7"+
		"f\2\2\u00d9\34\3\2\2\2\u00da\u00db\7f\2\2\u00db\u00dc\7q\2\2\u00dc\36"+
		"\3\2\2\2\u00dd\u00de\7k\2\2\u00de\u00df\7h\2\2\u00df \3\2\2\2\u00e0\u00e1"+
		"\7v\2\2\u00e1\u00e2\7j\2\2\u00e2\u00e3\7g\2\2\u00e3\u00e4\7p\2\2\u00e4"+
		"\"\3\2\2\2\u00e5\u00e6\7g\2\2\u00e6\u00e7\7n\2\2\u00e7\u00e8\7u\2\2\u00e8"+
		"\u00e9\7g\2\2\u00e9\u00ea\7\"\2\2\u00ea\u00eb\7k\2\2\u00eb\u00ec\7h\2"+
		"\2\u00ec$\3\2\2\2\u00ed\u00ee\7g\2\2\u00ee\u00ef\7n\2\2\u00ef\u00f0\7"+
		"u\2\2\u00f0\u00f1\7g\2\2\u00f1&\3\2\2\2\u00f2\u00f3\7y\2\2\u00f3\u00f4"+
		"\7j\2\2\u00f4\u00f5\7k\2\2\u00f5\u00f6\7n\2\2\u00f6\u00f7\7g\2\2\u00f7"+
		"(\3\2\2\2\u00f8\u00f9\7d\2\2\u00f9\u00fa\7t\2\2\u00fa\u00fb\7g\2\2\u00fb"+
		"\u00fc\7c\2\2\u00fc\u00fd\7m\2\2\u00fd*\3\2\2\2\u00fe\u00ff\7t\2\2\u00ff"+
		"\u0100\7g\2\2\u0100\u0101\7r\2\2\u0101\u0102\7g\2\2\u0102\u0103\7c\2\2"+
		"\u0103\u0104\7v\2\2\u0104,\3\2\2\2\u0105\u0106\7w\2\2\u0106\u0107\7p\2"+
		"\2\u0107\u0108\7v\2\2\u0108\u0109\7k\2\2\u0109\u010a\7n\2\2\u010a.\3\2"+
		"\2\2\u010b\u010c\7u\2\2\u010c\u010d\7c\2\2\u010d\u010e\7{\2\2\u010e\60"+
		"\3\2\2\2\u010f\u0110\7u\2\2\u0110\u0111\7j\2\2\u0111\u0112\7q\2\2\u0112"+
		"\u0113\7y\2\2\u0113\62\3\2\2\2\u0114\u0115\7c\2\2\u0115\u0116\7v\2\2\u0116"+
		"\64\3\2\2\2\u0117\u0118\7q\2\2\u0118\u0119\7h\2\2\u0119\u011a\7h\2\2\u011a"+
		"\u011b\7u\2\2\u011b\u011c\7g\2\2\u011c\u011d\7v\2\2\u011d\66\3\2\2\2\u011e"+
		"\u011f\7u\2\2\u011f\u0120\7e\2\2\u0120\u0121\7g\2\2\u0121\u0122\7p\2\2"+
		"\u0122\u0123\7g\2\2\u01238\3\2\2\2\u0124\u0125\7j\2\2\u0125\u0126\7k\2"+
		"\2\u0126\u0127\7f\2\2\u0127\u0128\7g\2\2\u0128:\3\2\2\2\u0129\u012a\7"+
		"o\2\2\u012a\u012b\7g\2\2\u012b\u012c\7p\2\2\u012c\u012d\7w\2\2\u012d<"+
		"\3\2\2\2\u012e\u012f\7q\2\2\u012f\u0130\7r\2\2\u0130\u0131\7v\2\2\u0131"+
		"\u0132\7k\2\2\u0132\u0133\7q\2\2\u0133\u0134\7p\2\2\u0134>\3\2\2\2\u0135"+
		"\u0136\7.\2\2\u0136@\3\2\2\2\u0137\u0138\7*\2\2\u0138B\3\2\2\2\u0139\u013a"+
		"\7+\2\2\u013aD\3\2\2\2\u013b\u013c\7]\2\2\u013cF\3\2\2\2\u013d\u013e\7"+
		"_\2\2\u013eH\3\2\2\2\u013f\u0140\7}\2\2\u0140J\3\2\2\2\u0141\u0142\7\177"+
		"\2\2\u0142L\3\2\2\2\u0143\u0144\7-\2\2\u0144\u0145\7-\2\2\u0145N\3\2\2"+
		"\2\u0146\u0147\7/\2\2\u0147\u0148\7/\2\2\u0148P\3\2\2\2\u0149\u014a\7"+
		"/\2\2\u014aR\3\2\2\2\u014b\u014c\7p\2\2\u014c\u014d\7q\2\2\u014d\u014e"+
		"\7v\2\2\u014eT\3\2\2\2\u014f\u0150\7%\2\2\u0150V\3\2\2\2\u0151\u0152\7"+
		"-\2\2\u0152X\3\2\2\2\u0153\u0154\7,\2\2\u0154Z\3\2\2\2\u0155\u0156\7\61"+
		"\2\2\u0156\\\3\2\2\2\u0157\u0158\7\'\2\2\u0158^\3\2\2\2\u0159\u015a\7"+
		"\60\2\2\u015a\u015b\7\60\2\2\u015b`\3\2\2\2\u015c\u015d\7>\2\2\u015db"+
		"\3\2\2\2\u015e\u015f\7>\2\2\u015f\u0160\7?\2\2\u0160d\3\2\2\2\u0161\u0162"+
		"\7@\2\2\u0162f\3\2\2\2\u0163\u0164\7@\2\2\u0164\u0165\7?\2\2\u0165h\3"+
		"\2\2\2\u0166\u0167\7?\2\2\u0167\u0168\7?\2\2\u0168j\3\2\2\2\u0169\u016a"+
		"\7#\2\2\u016a\u016b\7?\2\2\u016bl\3\2\2\2\u016c\u016d\7c\2\2\u016d\u016e"+
		"\7p\2\2\u016e\u016f\7f\2\2\u016fn\3\2\2\2\u0170\u0171\7q\2\2\u0171\u0172"+
		"\7t\2\2\u0172p\3\2\2\2\u0173\u0174\7?\2\2\u0174r\3\2\2\2\u0175\u0176\7"+
		"?\2\2\u0176\u0177\7@\2\2\u0177t\3\2\2\2\u0178\u0179\7\60\2\2\u0179v\3"+
		"\2\2\2\u017a\u017e\7$\2\2\u017b\u017d\n\3\2\2\u017c\u017b\3\2\2\2\u017d"+
		"\u0180\3\2\2\2\u017e\u017c\3\2\2\2\u017e\u017f\3\2\2\2\u017f\u0181\3\2"+
		"\2\2\u0180\u017e\3\2\2\2\u0181\u0182\7$\2\2\u0182x\3\2\2\2\u0183\u0184"+
		"\7&\2\2\u0184z\3\2\2\2\u0185\u0187\t\4\2\2\u0186\u0185\3\2\2\2\u0187\u0188"+
		"\3\2\2\2\u0188\u0186\3\2\2\2\u0188\u0189\3\2\2\2\u0189\u018d\3\2\2\2\u018a"+
		"\u018c\t\5\2\2\u018b\u018a\3\2\2\2\u018c\u018f\3\2\2\2\u018d\u018b\3\2"+
		"\2\2\u018d\u018e\3\2\2\2\u018e|\3\2\2\2\u018f\u018d\3\2\2\2\u0190\u0192"+
		"\5\177@\2\u0191\u0190\3\2\2\2\u0192\u0193\3\2\2\2\u0193\u0191\3\2\2\2"+
		"\u0193\u0194\3\2\2\2\u0194\u019b\3\2\2\2\u0195\u0197\7\60\2\2\u0196\u0198"+
		"\5\177@\2\u0197\u0196\3\2\2\2\u0198\u0199\3\2\2\2\u0199\u0197\3\2\2\2"+
		"\u0199\u019a\3\2\2\2\u019a\u019c\3\2\2\2\u019b\u0195\3\2\2\2\u019b\u019c"+
		"\3\2\2\2\u019c~\3\2\2\2\u019d\u019e\t\6\2\2\u019e\u0080\3\2\2\2\16\2\u0084"+
		"\u0090\u0094\u009d\u00ae\u017e\u0188\u018d\u0193\u0199\u019b";
	public static final ATN _ATN =
		ATNSimulator.deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}