grammar Mts;

/* Lexer Rules */

WS              : [ \t\r\n\u000C]+ -> skip ;

LOCAL           : 'local' ;

FUNCTION        : 'function' ;
LABEL           : 'label' ;
JUMP            : 'jump' ;
//GOTO            : 'goto' ;
RETURN          : 'return' ;
END             : 'end' ;
DO              : 'do' ;

IF              : 'if' ;
THEN            : 'then' ;
ELSEIF          : 'elseif'
                | 'else if' ;
ELSE            : 'else' ;

WHILE           : 'while' ;
BREAK           : 'break' ;
REPEAT          : 'repeat' ;
UNTIL           : 'until' ;
FOR             : 'for' ;
IN              : 'in' ;

CMD_SAY         : 'say' ;
CMD_SAY_LAST    : 'as conclusion' ;
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
SIZEOF            : '#' ;

ADD_ASSIGN      : '+=' ;
SUB_ASSIGN      : '-=' ;
MUL_ASSIGN      : '*=' ;
DIV_ASSIGN      : '/=' ;
MOD_ASSIGN      : '%=' ;
POW_ASSIGN      : '^=' ;
CONCAT_ASSIGN   : '.=' ;

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
//    : '\\' [abfnrtvz$"\\]
    : '\\' [nt"\\]
    ;

fragment
NestedString
    : '[' .*? ']'
    ;
    
LONGSTRING
    : '[' NestedString ']'
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

BlockComment
    : '--[' NestedString ']' 
      -> skip
    ;

LineComment
    : '--'
      ( ~( '[' | '\r' | '\n' )* ~( '\r' | '\n' )* )?
      ( '\r\n' | '\r' | '\n' | EOF )
      -> skip
    ;

Identifier
    : [_a-zA-Z][_a-zA-Z0-9]*
    ;

// =============================================================================

/* Parser Rules */
chunk
    : ( block | expr ) EOF
    ;

block
    : stmt*
    ;

stmt
    : ';'
    # BlankStmt
    | assignExpr
    # AssignmentStmt
    | 'local' NameList=nameList ( '=' ExprList=exprList )?
    # LocalVarDeclrStmt
    | call
    # CallStmt
    | command
    # CommandStmt
    | 'jump' Target=Identifier 
    # GotoStmt
    | '::' Name=Identifier '::'
    # LabelStmt
    | 'break'
    # BreakStmt
    | 'return' ExprList=exprList? 
    # ReturnStmt
    | 'do' Block=block 'end'
    # NestedBlock
    | 'while' Condition=expr 'do' Block=block 'end'
    # WhileLoop
    | 'repeat' Block=block 'until' Condition=expr
    # RepeatLoop
    | 'if' Condition=expr 'then' Block=block ElseIfs+=elseIfBody* Else=elseBody? 'end'
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
    | <assoc=right> Left=expr Operator='^' Right=expr
      # BinaryOpExpr
    | Operator=( 'not' | '#' | '-' ) Expr=expr
      # UnaryOpExpr
    | Left=expr Operator=( '*' | '/' | '%' ) Right=expr
      # BinaryOpExpr
    | Left=expr Operator=( '+' | '-'  ) Right=expr
      # BinaryOpExpr
    | <assoc=right> Left=expr Operator='..' Right=expr
      # BinaryOpExpr
    | Left=expr Operator=( '<' | '>' | '<=' | '>=' | '!=' | '==' ) Right=expr
      # LogicalOpExpr
    | Left=expr Operator='and' Right=expr
      # ConditionalOpExpr
    | Left=expr Operator='or' Right=expr
      # ConditionalOpExpr
    ;

assignExpr
    : Targets=varExprList '=' Exprs=exprList
      # SimpleAssignmentStmt
//      | Target=varExpr Operator=( '+=' | '-=' | '*=' | '/=' | '%=' | '^=' | '.=' ) Expr=expr
//        # OperatorAssignmentStmt
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
    @init { $nReturn = 1; }
    : varOrExpr Args+=callArgs[1]+
    ;

callArgs[int nReturn]
    : ( ':' Method=Identifier )? ( '(' Args=exprList? ')' | Arg=NORMALSTRING )
    ;

fieldAccess
    : '[' Field=expr ']'
      # ExprFieldAccess
    | '.' Field=Identifier
      # NameFieldAccess
    ;

tableCtor
    : '{' ( FieldDefs+=fieldDef ( ',' FieldDefs+=fieldDef )* ','? )? '}'
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
    : ELSEIF Condition=expr 'then' Block=block
    ;

elseBody
    : 'else' Block=block
    ;

numericForControl
    : Var=Identifier '=' Start=expr ',' Limit=expr ( ',' Step=expr )?
    ;

genericForControl
    : Vars=nameList 'in' Exprs=exprList
    ;

funcName
    : RootName=Identifier ( '.' TableKeys+=Identifier )* ( ':' MethodName=Identifier )?
    ;

funcBody
    : '(' Params=paramList? ')' Body=block 'end'
    ;

command
    : 'say' Arg1=expr Arg2=expr? IsLast='as conclusion'?
      # CommandSay
    | 'show' Group=expr 
             Path+=expr+ 
             ( 'at' Position=expr )? 
             ( 'offset' Offset=exprList )? 
             /*( 'with' Effect=exprList )?*/
      # CommandShow
    | 'scene' Path+=expr+ 
    		  ( 'as' Mode=expr )? 
    		  /*( 'with' exprList )?*/
      # CommandScene
    | 'hide' ( Group=expr | Scene='scene' )
    	     /*( 'with' exprList )?*/
      # CommandHide
    | 'menu' Caption=expr? Options+=commandMenuOption+ 'end'
	  # CommandMenu
    ;

commandMenuOption
    : 'option' Caption=expr Block=block
    ;
    
// ========================================
// Lists

nameList
    : Names+=Identifier ( ',' Names+=Identifier )*
    ;

paramList
    : NameList=nameList /*(',' '...')?*/
//    | '...'
    ;

exprList
    : Exprs+=expr ( ',' Exprs+=expr )*
    ;
    
varExprList
    : ExprList+=varExpr ( ',' ExprList+=varExpr )*
    ;