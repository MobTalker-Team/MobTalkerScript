grammar Mts3;

// =============================================================================
// Rules

chunk
    : block EOF
    ;

block
    : stmt* ( returnStmt | breakStmt )?
    ;

// ========================================
// Expressions

expr
    locals [int nResults = 1]
    : '(' expr ')'
        # EnclosedExpr
    | Literal=literal
        # LiteralExpr
    | '...'
        # VarargsExpr
    | 'function' Body=funcBody
        # FunctionDefinitionExpr
    | Var=var '=' Expr=expr
        # AssignmentExpr
    | varOrExpr Calls+=nameAndArgs*
        # PrefixExpr
    | tableCtor
        # TableConstructorExpr
    | <assoc=right> Left=expr Operator='^' Right=expr
        # BinaryExpr
    | Operator=( 'not' | '#' | '-' ) Expr=expr
        # UnaryExpr
    | Left=expr Operator=( '*' | '/' | '%' ) Right=expr
        # BinaryExpr
    | Left=expr Operator=( '+' | '-' ) Right=expr
        # BinaryExpr
    | <assoc=right> Left=expr Operator='..' Right=expr
        # BinaryExpr
    | Left=expr Operator=( '<' | '>' | '<=' | '>=' | '~=' | '==' ) Right=expr
        # LogicalExpr
    | Left=expr Operator='and' Right=expr
        # ConditionalExpr
    | Left=expr Operator='or' Right=expr
        # ConditionalExpr
    ;

literal
    : NIL
        # NilLiteral
    | BOOLEAN
        # BooleanLiteral
    | ( INT | HEX | FLOAT | HEX_FLOAT )
        # NumberLiteral
    | string
        # StringLiteral
    ;

exprList
    locals [int nTargets = -1]
    : Exprs+=expr ( ',' Exprs+=expr )*
    ;

string
    : NORMALSTRING
        # NormalString 
    | CHARSTRING 
        # CharString
    | LONGSTRING
        # LongString
    ;

// ========================================
// Tables

tableCtor
    : '{' FieldList=fieldList? '}'
    ;

field
    : '[' Field=expr ']' '=' Expr=expr
        # ExprField
    | Field=NAME '=' Expr=expr
        # NameField 
    | Expr=expr
        # ListField
    ;

fieldList
    : Fields+=field ( ( ',' | ';' ) Fields+=field )* ( ',' | ';' )?
    ;

// ========================================
// Statements

stmt
    : ';'
        # EmptyStmt
    | '::' Name=NAME '::'
        # LabelStmt
    | 'jump' Target=NAME
        # GotoStmt
    | 'do' block 'end'
        # BlockStmt
    | 'while' Condition=expr 'do' Block=block 'end'
        # WhileStmt
    | 'repeat' Block=block 'until' Condition=expr
        # RepeatStmt
    | 'if' IfCondition=expr 'then' IfBody=block ( ELSEIF ElseIfCondition+=expr 'then' ElseIfBlock+=block )* ( 'else' ElseBlock=block )? 'end'
        # IfThenElseStmt
    | 'for' Var=NAME '=' Init=expr ',' Limit=expr ( ',' Update=expr )? 'do' Block=block 'end'
        # NumericForStmt
    | 'for' NameList=nameList 'in' ExprList=exprList 'do' Block=block 'end'
        # GenericForStmt
    | 'function' Name=funcName Body=funcBody
        # FunctionDefinitionStmt
    | 'local' 'function' Name=NAME Body=funcBody
        # LocalFunctionDefinitionStmt
    | 'local' NameList=nameList ( '=' ExprList=exprList )?
        # LocalVariableDeclarationStmt
    | 'say' Args=exprList AsConclusion='as conclusion'?
        # SayStmt
    | 'show' Args=exprList ( 'at' At=expr )? ( 'offset' Offsets=exprList )? /*( 'with' exprList )?*/
        # ShowStmt
    | 'scene' Group=expr ( 'as' As=expr )? /*( 'with' exprList )?*/
        # SceneStmt
    | 'hide' ( 'scene' | Group=expr ) /*( 'with' exprList )?*/
        # HideStmt
    | 'menu' Caption=expr? Options+=menuOption+ 'end'
        # MenuStmt
    | VarList=varList '=' ExprList=exprList
        # AssignmentStmt
    | varOrExpr Calls+=nameAndArgs* LastCall=nameAndArgs
        # CallStmt
    ;

menuOption
    : 'option' Caption=expr Block=block
    ;

returnStmt
    : 'return' ExprList=exprList? ';'?
    ;

breakStmt
    : 'break' ';'?
    ;

// ========================================
// Calls

args
    : '(' ExprList=exprList? ')'
        # ArgList
    | tableCtor
        # ArgTable
    | string
        # ArgString
    ;

nameAndArgs
    : ( ':' Method=NAME )? Args=args
    ;

// ========================================
// Functions

funcName
    : Root=NAME ( '.' Fields+=NAME )* ( ':' Method=NAME )?
    ;

funcBody
    : '(' ParamList=paramList? ')' Block=block 'end'
    ;

paramList
    : Params=nameList ( ',' Varargs='...' )? 
    | Varargs='...'
    ;

// ========================================
// Vars

var
    : ( Name=NAME | '(' Expr=expr ')' Suffixes+=varSuffix ) Suffixes+=varSuffix*
    ;

varSuffix
    locals[boolean isAccess = true]
    : Calls+=nameAndArgs* '[' FieldExpr=expr ']'
        # FieldExprSuffix
    | Calls+=nameAndArgs* '.' FieldName=NAME
        # FieldNameSuffix
    ;

varOrExpr
    : var
    | '(' expr ')'
    ;

varList
    : Vars+=var ( ',' Vars+=var )*
    ;

nameList
    : Names+=NAME ( ',' Names+=NAME )*
    ;

// =============================================================================
// Lexer

AND             : 'and' ;
AS_CONCLUSION   : 'as conclusion' ;
AS              : 'as' ;
AT              : 'at' ;
BREAK           : 'break' ;
BOOLEAN         : 'true' 
                | 'false' ;
DO              : 'do' ;
ELSEIF          : 'elseif'
                | 'else if' ;
ELSE            : 'else' ;
END             : 'end' ;
FADEOUT         : 'fadeout' ;
FADEIN          : 'fadein' ;
FOR             : 'for' ;
FUNCTION        : 'function' ;
HIDE            : 'hide' ;
IF              : 'if' ;
IN              : 'in' ;
JUMP            : 'jump' ;
LOCAL           : 'local' ;
LABEL           : 'label' ;
MENU            : 'menu' ;
NIL             : 'nil' ;
NOT             : 'not' ;
OFFSET          : 'offset' ;
OPTION          : 'option' ;
OR              : 'or' ;
PAUSE           : 'pause' ;
MUSIC           : 'play music' ;
SOUND           : 'play sound' ;
REPEAT          : 'repeat' ;
RETURN          : 'return' ;
SAY             : 'say' ;
SCENE           : 'scene' ;
SHOW            : 'show' ;
STOP            : 'stop music' ;
THEN            : 'then' ;
UNTIL           : 'until' ;
WHILE           : 'while' ;
WITH            : 'with' ;

VARARGS         : '...' ;
CONCAT          : '..' ;
DOUBLECOLON     : '::' ;

ROUND_OPEN      : '(' ;
ROUND_CLOSE     : ')' ;
SQUARE_OPEN     : '[' ;
SQUARE_CLOSE    : ']' ;
CURLY_OPEN      : '{' ;
CURLY_CLOSE     : '}' ;
DOT             : '.' ;
COMMA           : ',' ;
COLON           : ':' ;
SEMICOLON       : ';' ;

//INCREMENT       : '++' ;
//DECREMENT       : '--' ;
//
//ASSIGN_PLUS     : '+=' ;
//ASSIGN_MINUS    : '-=' ;
//ASSIGN_MUL      : '*=' ;
//ASSIGN_DIV      : '/=' ;
//ASSIGN_MOD      : '%=' ;
//ASSIGN_POW      : '^=' ;
//ASSIGN_CONCAT   : '.=' ;

CMP_EQUAL       : '==' ;
CMP_NEQUAL      : '~=' ;
CMP_LEQUAL      : '<=' ;
CMP_GEQUAL      : '>=' ;

EQUALS          : '=' ;
PLUS            : '+' ;
MINUS           : '-' ;
MUL             : '*' ;
DIV             : '/' ;
MOD             : '%' ;
POW             : '^' ;
NUMSIGN         : '#' ;
LESS            : '<' ;
GREATER         : '>' ;

NORMALSTRING
    : '"' ( EscapeSequence | ~('\\'|'"') )* '"'
    ;

CHARSTRING
    : '\'' ( EscapeSequence | ~('\''|'\\') )* '\''
    ;

LONGSTRING
    : '[' NESTED_STR ']'
    ;

fragment
NESTED_STR
    : '=' NESTED_STR '='
    | '[' .*? ']'
    ;

INT
    : Digit+
    ;

HEX
    : '0' [xX] HexDigit+
    ;

FLOAT
    : Digit+ '.' Digit* ExponentPart?
    | '.' Digit+ ExponentPart?
    | Digit+ ExponentPart
    ;

HEX_FLOAT
    : '0' [xX] HexDigit+ '.' HexDigit* HexExponentPart?
    | '0' [xX] '.' HexDigit+ HexExponentPart?
    | '0' [xX] HexDigit+ HexExponentPart
    ;

fragment
ExponentPart
    : [eE] [+-]? Digit+
    ;

fragment
HexExponentPart
    : [pP] [+-]? Digit+
    ;

fragment
EscapeSequence
    : '\\' [abfnrtvz"'\\]
    | '\\' '\r'? '\n'
    | DecimalEscape
    | HexEscape
    ;

fragment
DecimalEscape
    : '\\' Digit
    | '\\' Digit Digit
    | '\\' [0-2] Digit Digit
    ;

fragment
HexEscape
    : '\\' 'x' HexDigit HexDigit
    ;

fragment
Digit
    : [0-9]
    ;

fragment
HexDigit
    : [0-9a-fA-F]
    ;
    
NAME
    : [a-zA-Z_][a-zA-Z_0-9]*
    ;

COMMENT
    : '--[' NESTED_STR ']' 
        -> channel(HIDDEN)
    ;

LINE_COMMENT
    : '--'
    (                                               // --
    | '[' '='*                                      // --[==
    | '[' '='* ~('='|'['|'\r'|'\n') ~('\r'|'\n')*   // --[==AA
    | ~('['|'\r'|'\n') ~('\r'|'\n')*                // --AAA
    ) ('\r\n'|'\r'|'\n'|EOF)
        -> channel(HIDDEN)
    ;

WS
    : [ \t\u000C\r\n]+ -> skip
    ;

SHEBANG
    : '#' '!' ~('\n'|'\r')* 
        -> channel(HIDDEN)
    ;
