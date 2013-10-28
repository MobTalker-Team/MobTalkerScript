package mobtalkerscript.mts.v1.parser;

import java.util.regex.*;

import org.antlr.v4.runtime.*;

/**
 */
public class MtsParseErrorTransformer
{
    
    // ========================================
    
    private static final Pattern missingPattern = Pattern.compile("missing " //
                                                                  + "'(.+?)'"
                                                                  + " at "
                                                                  + "'(.+?)'");
    private static final Pattern mismatchPattern = Pattern.compile("mismatched input " //
                                                                   + "'(.+?)'"
                                                                   + " expecting "
                                                                   + "'(.+?)'");
    private static final Pattern noAlternativePattern = Pattern.compile("no viable alternative at input " //
                                                                        + "'(.+?)'");
    
    // ========================================
    
//    public static MtsSyntaxError generateParseException(MtsParser parser, RecognitionException cause)
//    {
//        if (cause instanceof NoViableAltException)
//        {
//            NoViableAltException ex = (NoViableAltException) cause;
//            
//            TokenStream tokens = parser.getInputStream();
//            String input;
//            if (tokens instanceof TokenStream)
//            {
//                if (ex.getStartToken().getType() == Token.EOF)
//                {
//                    input = "<EOF>";
//                }
//                else
//                {
//                    input = tokens.getText(ex.getStartToken(), ex.getOffendingToken());
//                }
//            }
//            else
//            {
//                input = "<unknown input>";
//            }
//            
//            String msg = "no viable alternative at input " + escapeWSAndQuote(input);
//            Token offendingToken = ex.getOffendingToken();
//            int line = offendingToken.getLine();
//            int charPositionInLine = offendingToken.getCharPositionInLine();
//            
//            return transformSyntaxError(parser, offendingToken, line, charPositionInLine, msg);
//        }
//        else if (cause instanceof InputMismatchException)
//        {
//            InputMismatchException ex = (InputMismatchException) cause;
//            
//            String msg = "mismatched input " + getTokenErrorDisplay(ex.getOffendingToken());
//            /* + " expecting " + ex.getExpectedTokens().toString(parser.getTokenNames()); */
//            
//            Token offendingToken = ex.getOffendingToken();
//            int line = offendingToken.getLine();
//            int charPositionInLine = offendingToken.getCharPositionInLine();
//            
//            return transformSyntaxError(parser, offendingToken, line, charPositionInLine, msg);
//        }
//        else if (cause instanceof FailedPredicateException)
//        {
//            FailedPredicateException ex = (FailedPredicateException) cause;
//            
//            String ruleName = parser.getRuleNames()[parser.getContext().getRuleIndex()];
//            String msg = "rule " + ruleName + " " + ex.getMessage();
//            Token offendingToken = ex.getOffendingToken();
//            int line = offendingToken.getLine();
//            int charPositionInLine = offendingToken.getCharPositionInLine();
//            
//            return transformSyntaxError(parser, offendingToken, line, charPositionInLine, msg);
//        }
//        else
//        {
//            throw cause;
//        }
//    }
//    
//    protected static String escapeWSAndQuote(String s)
//    {
////   if ( s==null ) return s;
//        s = s.replaceAll("\n", "\\\\n");
//        s = s.replaceAll("\r", "\\\\r");
//        s = s.replaceAll("\t", "\\\\t");
//        return "'" + s + "'";
//    }
//    
//    public static String getTokenErrorDisplay(Token t)
//    {
//        if (t == null)
//        {
//            return "<no token>";
//        }
//        String s = getSymbolText(t);
//        if (s == null)
//        {
//            if (getSymbolType(t) == Token.EOF)
//            {
//                s = "<EOF>";
//            }
//            else
//            {
//                s = "<" + getSymbolType(t) + ">";
//            }
//        }
//        return escapeWSAndQuote(s);
//    }
//    
//    protected static String getSymbolText(@NotNull Token symbol)
//    {
//        return symbol.getText();
//    }
//    
//    protected static int getSymbolType(@NotNull Token symbol)
//    {
//        return symbol.getType();
//    }
//    
//    // ========================================
//    
//    protected static String[] getLines(MtsParser parser)
//    {
//        CommonTokenStream tokens = (CommonTokenStream) parser.getInputStream();
//        String input = tokens.getTokenSource().getInputStream().toString();
//        String[] lines = input.split("\n");
//        
//        for (int i = 0; i < lines.length; i++)
//        {
//            lines[i] = StringUtils.chomp(lines[i]);
//        }
//        
//        return lines;
//    }
    
    public static MtsSyntaxError transformSyntaxError(MtsParser parser,
                                                      Token offendingToken,
                                                      int line,
                                                      int charPositionInLine,
                                                      String msg)
    {
        
        Matcher missingMatcher = missingPattern.matcher(msg);
        if (missingMatcher.matches() && missingMatcher.group(1).equals(";"))
        {
            return new MtsSyntaxError(line, charPositionInLine, "missing ';'");
        }
        
        Matcher mismatchMatcher = mismatchPattern.matcher(msg);
        if (mismatchMatcher.matches() && mismatchMatcher.group(2).equals(";"))
        {
            return new MtsSyntaxError(line, charPositionInLine, "missing ';'");
        }
        
        Matcher noAlternativeMatcher = noAlternativePattern.matcher(msg);
        if (noAlternativeMatcher.matches() && noAlternativeMatcher.group(1).startsWith("if"))
        {
            return new MtsSyntaxError(line, charPositionInLine, msg + ", are you missing a 'then'?");
        }
        
        if ("=".equals(offendingToken.getText()))
        {
            return new MtsSyntaxError(line,
                                      charPositionInLine,
                                      "tried to assign to a function or label, are you missing an '$'?");
        }
        
        return new MtsSyntaxError(line, charPositionInLine, msg);
    }
}