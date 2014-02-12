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

NullLiteral
    : 'nil'
    ;

BooleanLiteral
    : 'true' | 'false'
    ;

StringLiteral
    : '"' ( EscapeSequence | ~('\\'|'"') )* '"'
    ;

fragment EscapeSequence
    : '\\' [$"\\]
    ;

Name
    : [_a-zA-Z]+ [_a-zA-Z0-9]*
    ;

NumberLiteral
    : Digit+ ( '.' Digit+ )?
    ;

fragment Digit
    : [0-9]
    ;

HexNumberLiteral
    : '0' [xX] HexDigit+ ( '.' HexDigit+ )?
    ;

fragment HexDigit
    : [0-9a-fA-F]
    ;

// =============================================================================

/* Parser Rules */
chunk
    : block EOF
    ;

funcDeclr
    : 'function' funcName funcBody
      # GlobalFunctionDeclr
    | 'local' 'function' Name funcBody
      # LocalFunctionDeclr
    ;

funcName
    : Name ( '.' Name )* ( ':' Name )?
    ;

funcExpr
    : 'function' funcBody
    ;

funcBody
    : '(' paramList? ')' block 'end'
    ;

paramList
    : nameList /*(',' '...')?*/
    /*| '...'*/
    ;

nameList
    : Name ( ',' Name )*
    ;

block
    : stmt* returnStmt?
    ;

stmt
    : ';'
      # BlankStmt
    | assignment
      # AssignmentStmt
    | call ';'
      # CallStmt
    | command
      # CommandStmt
    | '::' Name '::'
      # LabelStmt
    | breakStmt
      # Break
    | returnStmt
      # Return
    | 'do' block 'end'
      # DoBlock
    | 'while' expr 'do' block 'end'
      # WhileLoop
    | 'repeat' block 'until' expr ';'
      # RepeatLoop
    | 'if' expr 'then' block elseIfBody* ( elseBody | 'end' )
      # IfThenElse
    | 'for' numericForControl 'do' block 'end'
      # NumericForLoop
    | 'for' genericForControl 'do' block 'end'
      # GenericForLoop
    | funcDeclr
      # FunctionDeclr
    ;

assignment
    : assignmentTarget ( '+=' | '-=' | '*=' | '/=' | '%=' | '^=' | '.=' ) expr ';'
      # OperatorAssignmentStmt
    | assignmentTargetList '=' exprList ';'
      # SimpleAssignmentStmt
    | 'local' nameList ( '=' exprList )? ';'
      # LocalVariableDeclr
    ;

assignmentTarget
    : Name
      # SimpleAssignmentTarget
    | Name ( fieldAccess | callArgs )* ( fieldAccess | appendExpr )
      # FieldAssignmentTarget
    | '(' expr ')' ( fieldAccess | callArgs )* ( fieldAccess | appendExpr )
      # ExprPrefixedAssignmentTarget
    ;

expr
    : '(' expr ')'
      # SimpleExpr
    | literal
      # LiteralExpr
    | funcExpr
      # AnonymousFunctionDeclr
    | varAccess
      # SimpleExpr
    | tableCtor
      # SimpleExpr
    | expr ( '++' | '--' )
      # PostfixOpExpr
    | ( '++' | '--' | '-' | 'not' ) expr
      # PrefixOpExpr
    | expr ( '*' | '/' | '%' | '^' ) expr
      # BinaryOpExpr
    | expr ( '+' | '-'  ) expr
      # BinaryOpExpr
    | expr ( '<=' | '>=' | '<' | '>' | '!=' | '==' ) expr
      # LogicalOpExpr
    | expr ( 'and' | 'or' ) expr
      # LogicalOpExpr
    | expr '..'<assoc=right> expr
      # BinaryOpExpr
    ;

literal
    : NullLiteral
    | BooleanLiteral
    | NumberLiteral
    | HexNumberLiteral
    | StringLiteral
    ;

varAccess
    : Name ( fieldAccess | callArgs )*
      # NameAccessExpr
    | '(' expr ')' ( fieldAccess | callArgs )*
      # ExprAccessExpr
    ;

callArgs
    : ':' Name '(' exprList? ')'
      # MethodCall
    | '(' exprList? ')'
      # FunctionCall
    ;

exprList
    : expr ( ',' expr )*
    ;

fieldAccess
    : '[' expr ']'
      # ExprFieldAccess
    | '.' Name
      # NameFieldAccess
    ;

appendExpr
    : '[]'
    ;

tableCtor
    : '{' fieldList? '}'
    ;

fieldList
    : field ( ',' field )* ','?
    ;

field
    : '[' expr ']' '=' expr
      # ExprKeyField
    | Name '=' expr
      # NameKeyField
    | expr
      # ListField
    ;

command
    : 'jump' Name ';'
      # JumpCommandStmt
    | 'call' Name ';'
      # CallCommandStmt
    | 'say' expr? expr expr? ';'
      # SayCommandStmt
    | 'show' expr+ ( 'at' expr )? ( 'offset' exprList )? ( 'with' exprList )? ';'
      # ShowCommandStmt
    | 'scene' expr+ ( 'as' expr ) ( 'with' exprList )? ';'
      # SceneCommandStmt
    | 'hide' expr ( 'with' exprList )? ';'
      # HideCommandStmt
    /*| 'play music' exprList ( 'fadeout' expr )? ( 'fadein' expr )? ';'
      # PlayMusicCommandStmt
    | 'play sound' expr ( 'fadeout' expr )? ( 'fadein' expr )? ';'
      # PlaySoundCommandStmt
    | 'stop music' ( 'fadeout' expr )? ';'
      # StopMusicCommandStmt*/
    /*| 'pause' expr? ';'
      # PauseCommandStmt*/
    | 'menu' expr? ( 'option' expr block )+ 'end'
      # MenuCommandStmt
    ;

call
    : Name ( fieldAccess | callArgs )* callArgs
      # NameCallExpr
    | '(' expr ')' ( fieldAccess | callArgs )* callArgs
      # ExprCallExpr
    ;

elseIfBody
    : 'else if' expr 'then' block
    ;

elseBody
    : 'else' block 'end'
    ;

numericForControl
    : Name '=' expr ',' expr ( ',' expr )?
    ;

genericForControl
    : nameList 'in' exprList
    ;

assignmentTargetList
    : assignmentTarget ( ',' assignmentTarget )*
    ;

returnStmt
    : 'return' exprList? ';'
    ;

breakStmt
    : 'break' ';'
    ;