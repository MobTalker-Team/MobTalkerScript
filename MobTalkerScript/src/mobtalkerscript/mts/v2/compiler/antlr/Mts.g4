grammar Mts;

/* Lexer Rules */

WS              : [ \t\r\n\u000C]+ -> skip ;

LOCAL           : 'local' ;

FUNCTION        : 'function' ;
LABEL           : 'label' ;
JUMP            : 'jump' ;
CALL            : 'call' ;
RETURN          : 'return' ;
END             : 'end' ;
DO              : 'do' ;

IF              : 'if' ;
THEN            : 'then' ;
ELSEIF          : 'else if' ;
ELSE            : 'else' ;

WHILE           : 'while' ;
BREAK           : 'break' ;
REPEAT          : 'repeat' ;
UNTIL           : 'until' ;
FOR             : 'for' ;
IN              : 'in' ;

CMD_SAY         : 'say' ;
CMD_SHOW        : 'show' ;
CMD_AT          : 'at' ;
CMD_OFFSET      : 'offset' ;
CMD_WITH        : 'with' ;
CMD_SCENE       : 'scene' ;
CMD_AS          : 'as' ;
CMD_HIDE        : 'hide' ;
//CMD_MUSIC       : 'play music' ;
//CMD_SOUND       : 'play sound' ;
//CMD_STOP        : 'stop music' ;
//CMD_FADEOUT     : 'fadeout' ;
//CMD_FADEIN      : 'fadein' ;
//CMD_PAUSE       : 'pause' ;
CMD_MENU        : 'menu' ;
CMD_OPTION      : 'option' ;

LABEL_KEY       : '::' ;

LPAREN          : '(' ;
RPAREN          : ')' ;
LBRACE          : '{' ;
RBRACE          : '}' ;
LBRACK          : '[' ;
RBRACK          : ']' ;
SEMI            : ';' ;
COMMA           : ',' ;
DOT             : '.' ;

ASSIGN          : '=' ;
GT              : '>' ;
LT              : '<' ;
NOT             : 'not' ;
//QUESTION        : '?' ;
COLON           : ':' ;
EQUAL           : '==' ;
LE              : '<=' ;
GE              : '>=' ;
NOTEQUAL        : '!=' ;
AND             : 'and' ;
OR              : 'or' ;
INC             : '++' ;
DEC             : '--' ;
ADD             : '+' ;
SUB             : '-' ;
MUL             : '*' ;
DIV             : '/' ;
MOD             : '%' ;
POW             : '^' ;
STR_CONCAT      : '..' ;
TBL_APPEND      : '[]' ;
SIZEOF			: '#' ;

ADD_ASSIGN      : '+=' ;
SUB_ASSIGN      : '-=' ;
MUL_ASSIGN      : '*=' ;
DIV_ASSIGN      : '/=' ;
MOD_ASSIGN      : '%=' ;
POW_ASSIGN      : '^=' ;
CONCAT_ASSIGN   : '.=' ;

LineComment
    : '--' ~[\r\n]* -> channel( HIDDEN )
    ;

BlockComment
    : '--[[' .*? ']]' -> channel( HIDDEN )
    ;

Identifier
    : [_a-zA-Z][_a-zA-Z0-9]*
    ;

NULL
    : 'nil'
    ;

BOOLEAN
    : 'true' | 'false'
    ;

NORMALSTRING
	: '"' ( EscapeSequence | ~('\\'|'"') )* '"'
	;

fragment 
EscapeSequence
    : '\\' [abfnrtvz$"\\]
    ;
    
LONGSTRING
	: '[[' .*? ']]'
	;

fragment 
Digit
    : [0-9]
    ;

fragment 
HexDigit
    : [0-9a-fA-F]
    ;

fragment
ExponentPart
	: [eE] [+-]? Digit+
	;
	
fragment
HexExponentPart
	: [pP] [+-]? Digit+
	;
    
INTEGER
	: Digit+
	;

FLOAT
	: Digit+ '.' Digit* ExponentPart?
	| '.' Digit+ ExponentPart?
	| Digit+ ExponentPart
	;

HEXINTEGER
    : '0' [xX] HexDigit+ ( '.' HexDigit+ )?
    ;

HEXFLOAT
	: '0' [xX] HexDigit+ '.' HexDigit* HexExponentPart?
	| '0' [xX] '.' HexDigit+ HexExponentPart?
	| '0' [xX] HexDigit+ HexExponentPart
	;

// =============================================================================

/* Parser Rules */
chunk
    : block EOF
    ;

block
    : stmt*
    ;

stmt
    : ';'
      # BlankStmt
    | assignExpr ';'
      # AssignmentStmt
    | 'local' NameList=nameList ( '=' ExprList=exprList )?
      # LocalVarDeclrStmt
    | call ';'
      # CallStmt
//    | command
//      # CommandStmt
    | '::' Name=Identifier '::'
      # LabelStmt
    | 'break' ';'
      # BreakStmt
    | 'return' ExprList=exprList? ';'
      # ReturnStmt
    | 'do' Block=block 'end'
      # NestedBlock
    | 'while' Condition=expr 'do' Block=block 'end'
      # WhileLoop
    | 'repeat' Block=block 'until' Condition=expr ';'
      # RepeatLoop
    | 'if' Condition=expr 'then' Block=block ElseIfs+=elseIfBody* ( Else=elseBody | 'end' )
      # IfThenElse
    | 'for' Control=numericForControl 'do' Block=block 'end'
      # NumericForLoop
    | 'for' Control=genericForControl 'do' Block=block 'end'
      # GenericForLoop
    | 'function' Name=funcName Body=funcBody
      # FuncDeclrStmt
    | 'local' 'function' Name=Identifier Body=funcBody
      # LocalFuncDeclrStmt
    ;

expr
    : '(' expr ')'
      # SimpleExpr
    | NULL 
      # NullLiteral
    | BOOLEAN
      # BooleanLiteral
    | ( INTEGER | FLOAT | HEXINTEGER | HEXFLOAT )
      # NumberLiteral
    | ( NORMALSTRING | LONGSTRING )
      # StringLiteral
    | 'function' funcBody
      # FuncDeclrExpr
    | Call=call
      # CallExpr
    | varAccess
      # SimpleExpr
    | tableCtor
      # SimpleExpr
//    | Operator=( '++' | '--' ) expr
//      # PrefixOpExpr
//    | expr Operator=( '++' | '--' )
//      # PostfixOpExpr
    | Operator=( '-' | 'not' | '#' ) Expr=expr
      # UnaryOpExpr
    | Left=expr Operator=( '*' | '/' | '%' | '^' ) Right=expr
      # BinaryOpExpr
    | Left=expr Operator=( '+' | '-'  ) Right=expr
      # BinaryOpExpr
    | Left=expr Operator=( '<=' | '>=' | '<' | '>' | '!=' | '==' ) Right=expr
      # LogicalOpExpr
    | Left=expr Operator=( 'and' | 'or' ) Right=expr
      # ConditionalOpExpr
    | Left=expr Operator='..' Right=expr
      # BinaryOpExpr
    ;

assignExpr
    : Targets=varExprList '=' Exprs=exprList
      # SimpleAssignmentStmt
  	//| varExpr ( '+=' | '-=' | '*=' | '/=' | '%=' | '^=' | '.=' ) expr
  	| Target=varExpr Operator=( '+=' | '-=' | '*=' | '/=' | '%=' | '^=' | '.=' ) Expr=expr
  	  # OperatorAssignmentStmt
    ;
	
varExpr
	: ( Root=Identifier | '(' RootExpr=expr ')' Suffixes+=varSuffix ) Suffixes+=varSuffix*
	;
	
varSuffix
	: callArgs[1]* fieldAccess
	;
	
varOrExpr
	: varExpr | '(' expr ')'
	;

varAccess
	: varOrExpr fieldAccess*
	;
	
call returns [int nReturn]
	: varOrExpr Args+=callArgs[1]+
	;

callArgs[int nReturn]
    : ':' Method=Identifier '(' Args=exprList? ')'
      # MethodCall
    | '(' Args=exprList? ')'
      # FunctionCall
    ;

fieldAccess
    : '[' Field=expr ']'
      # ExprFieldAccess
    | '.' Field=Identifier
      # NameFieldAccess
    ;

tableCtor
    : '{' fieldDefList? '}'
    ;

fieldDef
    : '[' Key=expr ']' '=' Value=expr
      # ExprKeyField
    | Key=Identifier '=' Value=expr
      # NameKeyField
    | Value=expr
      # ListField
    ;
    
elseIfBody
    : 'else if' expr 'then' block
    ;

elseBody
    : 'else' block 'end'
    ;

numericForControl
    : Identifier '=' expr ',' expr ( ',' expr )?
    ;

genericForControl
    : nameList 'in' exprList
    ;

funcName
    : RootName=Identifier ( '.' TableKeys+=Identifier )* ( ':' MethodName=Identifier )?
    ;

funcBody
    : '(' Params=paramList? ')' Body=block 'end'
    ;

//command
//    : 'jump' Identifier ';'
//      # JumpCommandStmt
//    | 'call' Identifier ';'
//      # CallCommandStmt
//    | 'say' expr? expr expr? ';'
//      # SayCommandStmt
//    | 'show' expr+ ( 'at' expr )? ( 'offset' exprList )? ( 'with' exprList )? ';'
//      # ShowCommandStmt
//    | 'scene' expr+ ( 'as' expr ) ( 'with' exprList )? ';'
//      # SceneCommandStmt
//    | 'hide' expr ( 'with' exprList )? ';'
//      # HideCommandStmt
//    /*| 'play music' exprList ( 'fadeout' expr )? ( 'fadein' expr )? ';'
//      # PlayMusicCommandStmt*/
//    /*| 'play sound' expr ( 'fadeout' expr )? ( 'fadein' expr )? ';'
//      # PlaySoundCommandStmt*/
//    /*| 'stop music' ( 'fadeout' expr )? ';'
//      # StopMusicCommandStmt*/
//    /*| 'pause' expr? ';'
//      # PauseCommandStmt*/
//    | 'menu' expr? ( 'option' expr block )+ 'end'
//      # MenuCommandStmt
//    ;
    
// ========================================
// Lists

nameList
    : Names+=Identifier ( ',' Names+=Identifier )*
    ;

paramList
    : NameList=nameList /*(',' '...')?*/
    /*| '...'*/
    ;

exprList
    : Exprs+=expr ( ',' Exprs+=expr )*
    ;
    
varExprList
	: ExprList+=varExpr ( ',' ExprList+=varExpr )*
	;

fieldDefList
    : FieldDefs+=fieldDef ( ',' FieldDef+=fieldDef )* ','?
    ;

