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
		StmtCloser=1, Newline=2, Comment=3, Whitespace=4, Null=5, Boolean=6, LocalVar=7, 
		Function=8, Label=9, Jump=10, Call=11, Return=12, End=13, Do=14, If=15, 
		Then=16, ElseIf=17, Else=18, While=19, Break=20, Repeat=21, Until=22, 
		For=23, CommandSay=24, CommandShow=25, CommandShowAt=26, CommandShowOffset=27, 
		CommandScene=28, CommandHide=29, CommandMenu=30, CommandMenuOption=31, 
		Separator=32, ExprBraceOpen=33, ExprBraceClose=34, FieldBraceOpen=35, 
		FieldBraceClose=36, TableBraceOpen=37, TableBraceClose=38, IncrOp=39, 
		DecrOp=40, MinusOp=41, UnaryNotOp=42, UnarySizeOp=43, BinaryPlusOp=44, 
		BinaryMultiplyOp=45, BinaryDivideOp=46, BinaryModuloOp=47, BinaryConcatOp=48, 
		BinaryLowerThenOp=49, BinaryLowerThenOrEqualOP=50, BinaryGreaterThenOp=51, 
		BinaryGreaterThenOrEqualOp=52, BinaryEqualOp=53, BinaryNotEqualOp=54, 
		BinaryAndOp=55, BinaryOrOp=56, AssignOp=57, TableAssocOp=58, TableAccessOp=59, 
		String=60, VariableKey=61, Name=62, Number=63;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] tokenNames = {
		"<INVALID>",
		"';'", "Newline", "Comment", "Whitespace", "'nil'", "Boolean", "'local'", 
		"'function'", "'label'", "'jump'", "'call'", "'return'", "'end'", "'do'", 
		"'if'", "'then'", "'else if'", "'else'", "'while'", "'break'", "'repeat'", 
		"'until'", "'for'", "'say'", "'show'", "'at'", "'offset'", "'scene'", 
		"'hide'", "'menu'", "'option'", "','", "'('", "')'", "'['", "']'", "'{'", 
		"'}'", "'++'", "'--'", "'-'", "'not'", "'#'", "'+'", "'*'", "'/'", "'%'", 
		"'..'", "'<'", "'<='", "'>'", "'>='", "'=='", "'!='", "'and'", "'or'", 
		"'='", "'=>'", "'.'", "String", "'$'", "Name", "Number"
	};
	public static final String[] ruleNames = {
		"StmtCloser", "Newline", "Comment", "Whitespace", "Null", "Boolean", "LocalVar", 
		"Function", "Label", "Jump", "Call", "Return", "End", "Do", "If", "Then", 
		"ElseIf", "Else", "While", "Break", "Repeat", "Until", "For", "CommandSay", 
		"CommandShow", "CommandShowAt", "CommandShowOffset", "CommandScene", "CommandHide", 
		"CommandMenu", "CommandMenuOption", "Separator", "ExprBraceOpen", "ExprBraceClose", 
		"FieldBraceOpen", "FieldBraceClose", "TableBraceOpen", "TableBraceClose", 
		"IncrOp", "DecrOp", "MinusOp", "UnaryNotOp", "UnarySizeOp", "BinaryPlusOp", 
		"BinaryMultiplyOp", "BinaryDivideOp", "BinaryModuloOp", "BinaryConcatOp", 
		"BinaryLowerThenOp", "BinaryLowerThenOrEqualOP", "BinaryGreaterThenOp", 
		"BinaryGreaterThenOrEqualOp", "BinaryEqualOp", "BinaryNotEqualOp", "BinaryAndOp", 
		"BinaryOrOp", "AssignOp", "TableAssocOp", "TableAccessOp", "String", "VariableKey", 
		"Name", "Number", "Digit"
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
		"\3\uacf5\uee8c\u4f5d\u8b0d\u4a45\u78bd\u1b2f\u3378\2A\u01a5\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\4"+
		",\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64\t"+
		"\64\4\65\t\65\4\66\t\66\4\67\t\67\48\t8\49\t9\4:\t:\4;\t;\4<\t<\4=\t="+
		"\4>\t>\4?\t?\4@\t@\4A\tA\3\2\3\2\3\3\5\3\u0087\n\3\3\3\3\3\3\3\3\3\3\4"+
		"\3\4\3\4\3\4\7\4\u0091\n\4\f\4\16\4\u0094\13\4\3\4\5\4\u0097\n\4\3\4\3"+
		"\4\3\4\3\4\3\5\6\5\u009e\n\5\r\5\16\5\u009f\3\5\3\5\3\6\3\6\3\6\3\6\3"+
		"\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\5\7\u00b1\n\7\3\b\3\b\3\b\3\b\3\b\3"+
		"\b\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\n\3\n\3\n\3\n\3\n\3\n\3\13\3"+
		"\13\3\13\3\13\3\13\3\f\3\f\3\f\3\f\3\f\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\16"+
		"\3\16\3\16\3\16\3\17\3\17\3\17\3\20\3\20\3\20\3\21\3\21\3\21\3\21\3\21"+
		"\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\23\3\23\3\23\3\23\3\23\3\24"+
		"\3\24\3\24\3\24\3\24\3\24\3\25\3\25\3\25\3\25\3\25\3\25\3\26\3\26\3\26"+
		"\3\26\3\26\3\26\3\26\3\27\3\27\3\27\3\27\3\27\3\27\3\30\3\30\3\30\3\30"+
		"\3\31\3\31\3\31\3\31\3\32\3\32\3\32\3\32\3\32\3\33\3\33\3\33\3\34\3\34"+
		"\3\34\3\34\3\34\3\34\3\34\3\35\3\35\3\35\3\35\3\35\3\35\3\36\3\36\3\36"+
		"\3\36\3\36\3\37\3\37\3\37\3\37\3\37\3 \3 \3 \3 \3 \3 \3 \3!\3!\3\"\3\""+
		"\3#\3#\3$\3$\3%\3%\3&\3&\3\'\3\'\3(\3(\3(\3)\3)\3)\3*\3*\3+\3+\3+\3+\3"+
		",\3,\3-\3-\3.\3.\3/\3/\3\60\3\60\3\61\3\61\3\61\3\62\3\62\3\63\3\63\3"+
		"\63\3\64\3\64\3\65\3\65\3\65\3\66\3\66\3\66\3\67\3\67\3\67\38\38\38\3"+
		"8\39\39\39\3:\3:\3;\3;\3;\3<\3<\3=\3=\7=\u0183\n=\f=\16=\u0186\13=\3="+
		"\3=\3>\3>\3?\6?\u018d\n?\r?\16?\u018e\3?\7?\u0192\n?\f?\16?\u0195\13?"+
		"\3@\6@\u0198\n@\r@\16@\u0199\3@\3@\6@\u019e\n@\r@\16@\u019f\5@\u01a2\n"+
		"@\3A\3A\3\u0092B\3\3\1\5\4\2\7\5\3\t\6\4\13\7\1\r\b\1\17\t\1\21\n\1\23"+
		"\13\1\25\f\1\27\r\1\31\16\1\33\17\1\35\20\1\37\21\1!\22\1#\23\1%\24\1"+
		"\'\25\1)\26\1+\27\1-\30\1/\31\1\61\32\1\63\33\1\65\34\1\67\35\19\36\1"+
		";\37\1= \1?!\1A\"\1C#\1E$\1G%\1I&\1K\'\1M(\1O)\1Q*\1S+\1U,\1W-\1Y.\1["+
		"/\1]\60\1_\61\1a\62\1c\63\1e\64\1g\65\1i\66\1k\67\1m8\1o9\1q:\1s;\1u<"+
		"\1w=\1y>\1{?\1}@\1\177A\1\u0081\2\1\3\2\7\4\2\13\13\"\"\4\2$$^^\5\2C\\"+
		"aac|\6\2\62;C\\aac|\3\2\62;\u01ae\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2"+
		"\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3"+
		"\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2"+
		"\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2"+
		"\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3\2\2\2\2\63\3\2\2\2\2\65\3\2"+
		"\2\2\2\67\3\2\2\2\29\3\2\2\2\2;\3\2\2\2\2=\3\2\2\2\2?\3\2\2\2\2A\3\2\2"+
		"\2\2C\3\2\2\2\2E\3\2\2\2\2G\3\2\2\2\2I\3\2\2\2\2K\3\2\2\2\2M\3\2\2\2\2"+
		"O\3\2\2\2\2Q\3\2\2\2\2S\3\2\2\2\2U\3\2\2\2\2W\3\2\2\2\2Y\3\2\2\2\2[\3"+
		"\2\2\2\2]\3\2\2\2\2_\3\2\2\2\2a\3\2\2\2\2c\3\2\2\2\2e\3\2\2\2\2g\3\2\2"+
		"\2\2i\3\2\2\2\2k\3\2\2\2\2m\3\2\2\2\2o\3\2\2\2\2q\3\2\2\2\2s\3\2\2\2\2"+
		"u\3\2\2\2\2w\3\2\2\2\2y\3\2\2\2\2{\3\2\2\2\2}\3\2\2\2\2\177\3\2\2\2\3"+
		"\u0083\3\2\2\2\5\u0086\3\2\2\2\7\u008c\3\2\2\2\t\u009d\3\2\2\2\13\u00a3"+
		"\3\2\2\2\r\u00b0\3\2\2\2\17\u00b2\3\2\2\2\21\u00b8\3\2\2\2\23\u00c1\3"+
		"\2\2\2\25\u00c7\3\2\2\2\27\u00cc\3\2\2\2\31\u00d1\3\2\2\2\33\u00d8\3\2"+
		"\2\2\35\u00dc\3\2\2\2\37\u00df\3\2\2\2!\u00e2\3\2\2\2#\u00e7\3\2\2\2%"+
		"\u00ef\3\2\2\2\'\u00f4\3\2\2\2)\u00fa\3\2\2\2+\u0100\3\2\2\2-\u0107\3"+
		"\2\2\2/\u010d\3\2\2\2\61\u0111\3\2\2\2\63\u0115\3\2\2\2\65\u011a\3\2\2"+
		"\2\67\u011d\3\2\2\29\u0124\3\2\2\2;\u012a\3\2\2\2=\u012f\3\2\2\2?\u0134"+
		"\3\2\2\2A\u013b\3\2\2\2C\u013d\3\2\2\2E\u013f\3\2\2\2G\u0141\3\2\2\2I"+
		"\u0143\3\2\2\2K\u0145\3\2\2\2M\u0147\3\2\2\2O\u0149\3\2\2\2Q\u014c\3\2"+
		"\2\2S\u014f\3\2\2\2U\u0151\3\2\2\2W\u0155\3\2\2\2Y\u0157\3\2\2\2[\u0159"+
		"\3\2\2\2]\u015b\3\2\2\2_\u015d\3\2\2\2a\u015f\3\2\2\2c\u0162\3\2\2\2e"+
		"\u0164\3\2\2\2g\u0167\3\2\2\2i\u0169\3\2\2\2k\u016c\3\2\2\2m\u016f\3\2"+
		"\2\2o\u0172\3\2\2\2q\u0176\3\2\2\2s\u0179\3\2\2\2u\u017b\3\2\2\2w\u017e"+
		"\3\2\2\2y\u0180\3\2\2\2{\u0189\3\2\2\2}\u018c\3\2\2\2\177\u0197\3\2\2"+
		"\2\u0081\u01a3\3\2\2\2\u0083\u0084\7=\2\2\u0084\4\3\2\2\2\u0085\u0087"+
		"\7\17\2\2\u0086\u0085\3\2\2\2\u0086\u0087\3\2\2\2\u0087\u0088\3\2\2\2"+
		"\u0088\u0089\7\f\2\2\u0089\u008a\3\2\2\2\u008a\u008b\b\3\2\2\u008b\6\3"+
		"\2\2\2\u008c\u008d\7\61\2\2\u008d\u008e\7\61\2\2\u008e\u0092\3\2\2\2\u008f"+
		"\u0091\13\2\2\2\u0090\u008f\3\2\2\2\u0091\u0094\3\2\2\2\u0092\u0093\3"+
		"\2\2\2\u0092\u0090\3\2\2\2\u0093\u0096\3\2\2\2\u0094\u0092\3\2\2\2\u0095"+
		"\u0097\7\17\2\2\u0096\u0095\3\2\2\2\u0096\u0097\3\2\2\2\u0097\u0098\3"+
		"\2\2\2\u0098\u0099\7\f\2\2\u0099\u009a\3\2\2\2\u009a\u009b\b\4\3\2\u009b"+
		"\b\3\2\2\2\u009c\u009e\t\2\2\2\u009d\u009c\3\2\2\2\u009e\u009f\3\2\2\2"+
		"\u009f\u009d\3\2\2\2\u009f\u00a0\3\2\2\2\u00a0\u00a1\3\2\2\2\u00a1\u00a2"+
		"\b\5\4\2\u00a2\n\3\2\2\2\u00a3\u00a4\7p\2\2\u00a4\u00a5\7k\2\2\u00a5\u00a6"+
		"\7n\2\2\u00a6\f\3\2\2\2\u00a7\u00a8\7v\2\2\u00a8\u00a9\7t\2\2\u00a9\u00aa"+
		"\7w\2\2\u00aa\u00b1\7g\2\2\u00ab\u00ac\7h\2\2\u00ac\u00ad\7c\2\2\u00ad"+
		"\u00ae\7n\2\2\u00ae\u00af\7u\2\2\u00af\u00b1\7g\2\2\u00b0\u00a7\3\2\2"+
		"\2\u00b0\u00ab\3\2\2\2\u00b1\16\3\2\2\2\u00b2\u00b3\7n\2\2\u00b3\u00b4"+
		"\7q\2\2\u00b4\u00b5\7e\2\2\u00b5\u00b6\7c\2\2\u00b6\u00b7\7n\2\2\u00b7"+
		"\20\3\2\2\2\u00b8\u00b9\7h\2\2\u00b9\u00ba\7w\2\2\u00ba\u00bb\7p\2\2\u00bb"+
		"\u00bc\7e\2\2\u00bc\u00bd\7v\2\2\u00bd\u00be\7k\2\2\u00be\u00bf\7q\2\2"+
		"\u00bf\u00c0\7p\2\2\u00c0\22\3\2\2\2\u00c1\u00c2\7n\2\2\u00c2\u00c3\7"+
		"c\2\2\u00c3\u00c4\7d\2\2\u00c4\u00c5\7g\2\2\u00c5\u00c6\7n\2\2\u00c6\24"+
		"\3\2\2\2\u00c7\u00c8\7l\2\2\u00c8\u00c9\7w\2\2\u00c9\u00ca\7o\2\2\u00ca"+
		"\u00cb\7r\2\2\u00cb\26\3\2\2\2\u00cc\u00cd\7e\2\2\u00cd\u00ce\7c\2\2\u00ce"+
		"\u00cf\7n\2\2\u00cf\u00d0\7n\2\2\u00d0\30\3\2\2\2\u00d1\u00d2\7t\2\2\u00d2"+
		"\u00d3\7g\2\2\u00d3\u00d4\7v\2\2\u00d4\u00d5\7w\2\2\u00d5\u00d6\7t\2\2"+
		"\u00d6\u00d7\7p\2\2\u00d7\32\3\2\2\2\u00d8\u00d9\7g\2\2\u00d9\u00da\7"+
		"p\2\2\u00da\u00db\7f\2\2\u00db\34\3\2\2\2\u00dc\u00dd\7f\2\2\u00dd\u00de"+
		"\7q\2\2\u00de\36\3\2\2\2\u00df\u00e0\7k\2\2\u00e0\u00e1\7h\2\2\u00e1 "+
		"\3\2\2\2\u00e2\u00e3\7v\2\2\u00e3\u00e4\7j\2\2\u00e4\u00e5\7g\2\2\u00e5"+
		"\u00e6\7p\2\2\u00e6\"\3\2\2\2\u00e7\u00e8\7g\2\2\u00e8\u00e9\7n\2\2\u00e9"+
		"\u00ea\7u\2\2\u00ea\u00eb\7g\2\2\u00eb\u00ec\7\"\2\2\u00ec\u00ed\7k\2"+
		"\2\u00ed\u00ee\7h\2\2\u00ee$\3\2\2\2\u00ef\u00f0\7g\2\2\u00f0\u00f1\7"+
		"n\2\2\u00f1\u00f2\7u\2\2\u00f2\u00f3\7g\2\2\u00f3&\3\2\2\2\u00f4\u00f5"+
		"\7y\2\2\u00f5\u00f6\7j\2\2\u00f6\u00f7\7k\2\2\u00f7\u00f8\7n\2\2\u00f8"+
		"\u00f9\7g\2\2\u00f9(\3\2\2\2\u00fa\u00fb\7d\2\2\u00fb\u00fc\7t\2\2\u00fc"+
		"\u00fd\7g\2\2\u00fd\u00fe\7c\2\2\u00fe\u00ff\7m\2\2\u00ff*\3\2\2\2\u0100"+
		"\u0101\7t\2\2\u0101\u0102\7g\2\2\u0102\u0103\7r\2\2\u0103\u0104\7g\2\2"+
		"\u0104\u0105\7c\2\2\u0105\u0106\7v\2\2\u0106,\3\2\2\2\u0107\u0108\7w\2"+
		"\2\u0108\u0109\7p\2\2\u0109\u010a\7v\2\2\u010a\u010b\7k\2\2\u010b\u010c"+
		"\7n\2\2\u010c.\3\2\2\2\u010d\u010e\7h\2\2\u010e\u010f\7q\2\2\u010f\u0110"+
		"\7t\2\2\u0110\60\3\2\2\2\u0111\u0112\7u\2\2\u0112\u0113\7c\2\2\u0113\u0114"+
		"\7{\2\2\u0114\62\3\2\2\2\u0115\u0116\7u\2\2\u0116\u0117\7j\2\2\u0117\u0118"+
		"\7q\2\2\u0118\u0119\7y\2\2\u0119\64\3\2\2\2\u011a\u011b\7c\2\2\u011b\u011c"+
		"\7v\2\2\u011c\66\3\2\2\2\u011d\u011e\7q\2\2\u011e\u011f\7h\2\2\u011f\u0120"+
		"\7h\2\2\u0120\u0121\7u\2\2\u0121\u0122\7g\2\2\u0122\u0123\7v\2\2\u0123"+
		"8\3\2\2\2\u0124\u0125\7u\2\2\u0125\u0126\7e\2\2\u0126\u0127\7g\2\2\u0127"+
		"\u0128\7p\2\2\u0128\u0129\7g\2\2\u0129:\3\2\2\2\u012a\u012b\7j\2\2\u012b"+
		"\u012c\7k\2\2\u012c\u012d\7f\2\2\u012d\u012e\7g\2\2\u012e<\3\2\2\2\u012f"+
		"\u0130\7o\2\2\u0130\u0131\7g\2\2\u0131\u0132\7p\2\2\u0132\u0133\7w\2\2"+
		"\u0133>\3\2\2\2\u0134\u0135\7q\2\2\u0135\u0136\7r\2\2\u0136\u0137\7v\2"+
		"\2\u0137\u0138\7k\2\2\u0138\u0139\7q\2\2\u0139\u013a\7p\2\2\u013a@\3\2"+
		"\2\2\u013b\u013c\7.\2\2\u013cB\3\2\2\2\u013d\u013e\7*\2\2\u013eD\3\2\2"+
		"\2\u013f\u0140\7+\2\2\u0140F\3\2\2\2\u0141\u0142\7]\2\2\u0142H\3\2\2\2"+
		"\u0143\u0144\7_\2\2\u0144J\3\2\2\2\u0145\u0146\7}\2\2\u0146L\3\2\2\2\u0147"+
		"\u0148\7\177\2\2\u0148N\3\2\2\2\u0149\u014a\7-\2\2\u014a\u014b\7-\2\2"+
		"\u014bP\3\2\2\2\u014c\u014d\7/\2\2\u014d\u014e\7/\2\2\u014eR\3\2\2\2\u014f"+
		"\u0150\7/\2\2\u0150T\3\2\2\2\u0151\u0152\7p\2\2\u0152\u0153\7q\2\2\u0153"+
		"\u0154\7v\2\2\u0154V\3\2\2\2\u0155\u0156\7%\2\2\u0156X\3\2\2\2\u0157\u0158"+
		"\7-\2\2\u0158Z\3\2\2\2\u0159\u015a\7,\2\2\u015a\\\3\2\2\2\u015b\u015c"+
		"\7\61\2\2\u015c^\3\2\2\2\u015d\u015e\7\'\2\2\u015e`\3\2\2\2\u015f\u0160"+
		"\7\60\2\2\u0160\u0161\7\60\2\2\u0161b\3\2\2\2\u0162\u0163\7>\2\2\u0163"+
		"d\3\2\2\2\u0164\u0165\7>\2\2\u0165\u0166\7?\2\2\u0166f\3\2\2\2\u0167\u0168"+
		"\7@\2\2\u0168h\3\2\2\2\u0169\u016a\7@\2\2\u016a\u016b\7?\2\2\u016bj\3"+
		"\2\2\2\u016c\u016d\7?\2\2\u016d\u016e\7?\2\2\u016el\3\2\2\2\u016f\u0170"+
		"\7#\2\2\u0170\u0171\7?\2\2\u0171n\3\2\2\2\u0172\u0173\7c\2\2\u0173\u0174"+
		"\7p\2\2\u0174\u0175\7f\2\2\u0175p\3\2\2\2\u0176\u0177\7q\2\2\u0177\u0178"+
		"\7t\2\2\u0178r\3\2\2\2\u0179\u017a\7?\2\2\u017at\3\2\2\2\u017b\u017c\7"+
		"?\2\2\u017c\u017d\7@\2\2\u017dv\3\2\2\2\u017e\u017f\7\60\2\2\u017fx\3"+
		"\2\2\2\u0180\u0184\7$\2\2\u0181\u0183\n\3\2\2\u0182\u0181\3\2\2\2\u0183"+
		"\u0186\3\2\2\2\u0184\u0182\3\2\2\2\u0184\u0185\3\2\2\2\u0185\u0187\3\2"+
		"\2\2\u0186\u0184\3\2\2\2\u0187\u0188\7$\2\2\u0188z\3\2\2\2\u0189\u018a"+
		"\7&\2\2\u018a|\3\2\2\2\u018b\u018d\t\4\2\2\u018c\u018b\3\2\2\2\u018d\u018e"+
		"\3\2\2\2\u018e\u018c\3\2\2\2\u018e\u018f\3\2\2\2\u018f\u0193\3\2\2\2\u0190"+
		"\u0192\t\5\2\2\u0191\u0190\3\2\2\2\u0192\u0195\3\2\2\2\u0193\u0191\3\2"+
		"\2\2\u0193\u0194\3\2\2\2\u0194~\3\2\2\2\u0195\u0193\3\2\2\2\u0196\u0198"+
		"\5\u0081A\2\u0197\u0196\3\2\2\2\u0198\u0199\3\2\2\2\u0199\u0197\3\2\2"+
		"\2\u0199\u019a\3\2\2\2\u019a\u01a1\3\2\2\2\u019b\u019d\7\60\2\2\u019c"+
		"\u019e\5\u0081A\2\u019d\u019c\3\2\2\2\u019e\u019f\3\2\2\2\u019f\u019d"+
		"\3\2\2\2\u019f\u01a0\3\2\2\2\u01a0\u01a2\3\2\2\2\u01a1\u019b\3\2\2\2\u01a1"+
		"\u01a2\3\2\2\2\u01a2\u0080\3\2\2\2\u01a3\u01a4\t\6\2\2\u01a4\u0082\3\2"+
		"\2\2\16\2\u0086\u0092\u0096\u009f\u00b0\u0184\u018e\u0193\u0199\u019f"+
		"\u01a1";
	public static final ATN _ATN =
		ATNSimulator.deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}