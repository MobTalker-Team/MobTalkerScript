grammar Mts;

/* Lexer Rules */

WS              : [ \t\r\n\u000C]+ -> skip ;

LOCAL           : 'local' ;

FUNCTION        : 'function' ;
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
CMD_SCENE       : 'scene' ;
CMD_HIDE        : 'hide' ;
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
CONCAT          : '..' ;

ADD_ASSIGN      : '+=' ;
SUB_ASSIGN      : '-=' ;
MUL_ASSIGN      : '*=' ;
DIV_ASSIGN      : '/=' ;
MOD_ASSIGN      : '%=' ;
POW_ASSIGN      : '^=' ;
CONCAT_ASSIGN   : '.=' ;

LineComment
    : '//' ~[\r\n]* -> skip
    ;

BlockComment
    : '/*' .*? '*/' -> skip
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
    : '\\' [$n"\\]
    ;

Identifier
    : [_a-zA-Z]+ [_a-zA-Z0-9]*
    ;

NumberLiteral
    : Digit+ ( '.' Digit+ )?
    ;

fragment Digit
    : [0-9]
    ;

/* Parser Rules */
chunk 
    : ( /*block |*/ labelDecl | funcDecl )* EOF 
    ;

block 
    : ( stmt | returnStmt )+ 
    ;

loopBlock 
    : ( stmt | returnStmt | breakStmt )+ 
    ;

stmt
    : 'jump' LabelName=Identifier
      # Jump
    | 'call' FunctionName=Identifier
      # Call
    | 'do' 
        Block=block?
      'end'
      # DoBlock
    | 'while' Condition=expr 'do' 
        LoopBlock=loopBlock?
      'end'
      # WhileBlock
    | 'for' Control=numericForControl 'do'
        LoopBlock=loopBlock?
      'end'
      # NumericFor
    | 'for' Control=genericForControl 'do'
        LoopBlock=loopBlock?
      'end'
      # GenericFor
    | 'repeat' 
        LoopBlock=loopBlock?
      'until' Condition=expr ';'
      # RepeatBlock
    | ( 'if' Condition+=expr 'then' 
        ThenBlock+=block? )
      ( 'else if' Condition+=expr 'then' 
        ThenBlock+=block? )*
      ( 'else' 
        ElseBlock=block? )?
      'end'
      # IfElseBlock
    | commandStmt
      # Command
    | exprStmt ';'
      # Statement
    | ';'
      # BlankStatement
    ;

expr 
    : Operator=( '-' | 'not' ) Right=expr
      # UnaryExpr
    | Left=expr Operator=( '*' | '/' | '%' | '^' ) Right=expr
      # BinaryExpr
    | Left=expr Operator=( '+' | '-'  ) Right=expr
      # BinaryExpr
    | Left=expr Operator=( '<=' | '>=' | '<' | '>' | '!=' | '==' ) Right=expr
      # BinaryExpr
    | Left=expr Operator='and' Right=expr
      # LogicalExpr
    | Left=expr Operator='or' Right=expr
      # LogicalExpr
    | Left=expr Operator='..'<assoc=right> Right=expr
      # BinaryExpr
    | literalExpr
       # Literal
    | tableCtorExpr
      # SimpleExpr
    | assignmentExpr
      # SimpleExpr
    | accessExpr
      # SimpleExpr
    | callExpr
      # SimpleExpr
    | '(' expr ')'
      # SimpleExpr
    ;

literalExpr 
    : Literal=NumberLiteral
      # NumberLiteral
    | Literal=BooleanLiteral
      # BooleanLiteral
    | Literal=StringLiteral
      # StringLiteral
    | NullLiteral
      # NullLiteral
    ;

numericForControl
    : '$' LoopVariable=Identifier '=' ValueExpr=expr ',' Condition=expr ( ',' UpdateExpr=expr )?
    ;

genericForControl
    : '$' KeyVariable=Identifier ',' '$' ValueVariable=Identifier 'in' TableExpr=expr
    ;

tableCtorExpr
    : '{' ( FieldExprs+=fieldDefExpr ( ',' FieldExprs+=fieldDefExpr )* ','? )? '}'
    ;

fieldDefExpr 
    : ValueExpr=expr
      # ListFieldDef
    | Key=Identifier '=' ValueExpr=expr
      # IdentifierKeyedFieldDef
    | '[' KeyExpr=expr ']' '=' ValueExpr=expr
      # ExpressionKeyedFieldDef
    ;    

assignmentExpr 
    : TableFieldExpr=tableExpr '=' ValueExpr=expr
      # TableAssignment
    | '$' VariableName=Identifier '=' ValueExpr=expr
      # VariableAssignment
    | 'local' '$' VariableName=Identifier ( '=' ValueExpr=expr )?
      # LocalFieldDefinition
    ;

accessExpr 
    : TableFieldExpr=tableExpr
      # TableAccess
    | '$' VariableName=Identifier
      # VariableAccess
    ;

callExpr 
    : FunctionName=Identifier '(' ( ArgumentExprs+=expr ( ',' ArgumentExprs+=expr )* )? ')'
      # FunctionCall
    | FunctionExpr=tableExpr '(' ( ArgumentExprs+=expr ( ',' ArgumentExprs+=expr )* )? ')'
      # TableCall
    ;

tableExpr 
    : '$' ParentVariable=Identifier FieldAccesses+=tableFieldAccess* LastFieldAccess=tableFieldAccess 
    ;

tableFieldAccess 
    : '.' Key=Identifier
      # IdentifierKeyedAccess
    | '[' KeyExpr=expr ']'
      # ExpressionKeyedAccess
    ;

commandStmt 
    : 'say' Character=expr? Text=expr ';'
      # CommandSay
    | 'show' Group=expr? Path=expr 
      ( 'at' Pos=expr )?
      ( 'offset' XOffset=expr YOffset=expr )?
      ';'
      # CommandShow
    | 'scene' Group=expr? Path=expr ';'
      # CommandScene
    | 'hide' Group=expr? ';'
      # CommandHide
    | 'menu' Caption=expr? 
        Options+=commandMenuOption+             
      'end'
      # CommandMenu
    ;

commandMenuOption 
    : 'option' CaptionExpr=expr 
        Block=block? 
    ;

exprStmt 
    : callExpr
    | assignmentExpr
    ;

returnStmt 
    : 'return' ReturnExpr=expr? ';'
    ;

breakStmt
    : 'break' ';'
    ;

funcDecl 
    : /*'local'?*/ 'function' FunctionName=Identifier '(' ( '$' Parameters+=Identifier ( ',' '$' Parameters+=Identifier )* )? ')' 
        Block=block? 
      'end' 
    ;

labelDecl 
    : /*'local'?*/ 'label' LabelName=Identifier
        Block=block?
      'end' 
    ;