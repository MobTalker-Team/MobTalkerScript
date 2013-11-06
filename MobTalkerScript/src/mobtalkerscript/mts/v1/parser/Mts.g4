grammar Mts;

/* Lexer Rules */

StmtCloser : ';' ;
Newline : '\r'? '\n' -> skip ;

Comment : '//' .*? '\r'? '\n' -> skip ;

Whitespace : [ \t]+ -> skip ;

Null : 'nil' ;
Boolean : 'true' | 'false' ;

LocalVar : 'local' ;

Function : 'function' ;
Label : 'label' ;

Jump : 'jump' ;
Call : 'call' ;

Return : 'return' ;

End : 'end' ;
Do : 'do' ;

If : 'if' ;
Then : 'then' ;
ElseIf : 'else if' ;
Else : 'else' ;

While : 'while' ;
Break : 'break' ;

Repeat : 'repeat' ;
Until : 'until' ;

For : 'for' ;

CommandSay : 'say' ;

CommandShow : 'show' ;
CommandShowAt : 'at' ;
CommandShowOffset : 'offset' ;

CommandScene : 'scene' ;

CommandHide : 'hide' ;

CommandMenu : 'menu' ;
CommandMenuOption : 'option' ;

Separator : ',' ;

ExprBraceOpen : '(' ;
ExprBraceClose : ')' ;

FieldBraceOpen : '[' ;
FieldBraceClose : ']' ;

TableBraceOpen : '{' ;
TableBraceClose : '}' ;

IncrOp : '++' ;
DecrOp : '--' ;

MinusOp : '-' ;

UnaryNotOp : 'not' ;
UnarySizeOp : '#' ;

BinaryPlusOp : '+' ;
BinaryMultiplyOp : '*' ;
BinaryDivideOp : '/' ;
BinaryModuloOp : '%' ;
BinaryConcatOp : '..' ;
BinaryLowerThenOp : '<' ;
BinaryLowerThenOrEqualOP : '<=' ;
BinaryGreaterThenOp : '>' ;
BinaryGreaterThenOrEqualOp : '>=' ;
BinaryEqualOp : '==' ;
BinaryNotEqualOp : '!=' ;
BinaryAndOp : 'and' ;
BinaryOrOp : 'or' ;

AssignOp : '=' ;
TableAssocOp : '=>' ;

TableAccessOp : '.' ;

String : '"' ~( '\\' | '"' )* '"' ;

VariableKey : '$' ;

Name: [_a-zA-Z]+ [_a-zA-Z0-9]* ;

Number : Digit+ ( '.' Digit+ )? ;

fragment Digit : [0-9] ;

/* Parser Rules */
script : block? ( labelDecl | funcDecl )* EOF ;

block : ( stmt | returnStmt )+ ;

stmt  : 'break'
        # Break
      | 'jump' Target=Name
        # Jump
      | 'call' Identifier=Name
        # Call
      | 'do' 
            Block=block?
        'end'
        # DoBlock
      | 'while' Condition=expr 'do' 
            LoopBlock=block?
        'end'
        # WhileBlock
      | ( ( 'for' Initializer=forLoopInitializer ';' Condition=expr ( ';' Step=expr )? 'do' )
        | ( 'for' '(' Initializer=forLoopInitializer ';' Condition=expr ( ';' Step=expr )? ')' 'do' ) )
            LoopBlock=block?
        'end'
        # NumericFor
      | 'repeat' 
            LoopBlock=block?
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

expr : Operator=( '-' | 'not' | '#' ) Right=expr
       # UnaryExpr
     | Left=expr Operator=( '*' | '/' | '%' ) Right=expr
       # BinaryExpr
     | Left=expr Operator=( '+' | '-'  ) Right=expr
       # BinaryExpr
     | Left=expr Operator=( '<=' | '>=' | '<' | '>' | '!=' | '==' ) Right=expr
       # BinaryExpr
     | Left=expr Operator='and' Right=expr
       # LogicalExpr
     | Left=expr Operator='or' Right=expr
       # LogicalExpr
     | Left=expr Operator='..' Right=expr
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

literalExpr : Literal=Number
              # NumberLiteral
            | Literal=Boolean
              # BooleanLiteral
            | Literal=String
              # StringLiteral
            | Null
              # NullLiteral
            ;

tableCtorExpr : '{' ( FieldExprs+=fieldDefExpr ( ',' FieldExprs+=fieldDefExpr )* ','? )? '}' ;

fieldDefExpr : '[' VariableNameExpr=expr ']' '=' VariableExpr=expr
               # ExpressionField
             | VariableName=variableExpr '=' VariableExpr=expr
               # NamedField
             | VariableExpr=expr
               # IndexedField
             ;

variableExpr : VariableKey Identifier=Name ;

assignmentExpr : TableExpr=tableExpr '=' VariableExpr=expr
                 # TableAssignment
               | 'local' VariableName=variableExpr '=' VariableExpr=expr
                 # LocalVariableAssignment
               | VariableName=variableExpr '=' VariableExpr=expr
                 # VariableAssignment
               ;

accessExpr : tableExpr
             # TableAccess
           | variableExpr
             # VariableAccess
           ;

callExpr : Identifier=Name FunctionArgs=funcArgs
           # FunctionCall
         | TableExpr=tableExpr FunctionArgs=funcArgs
           # TableCall
         ;

tableExpr : ParentTableExpr=variableExpr FieldExprs+=tableFieldAccess+ ;

tableFieldAccess : '.' Key=Name
                 | '[' KeyExpr=expr ']'
                 ;

funcArgs : '(' ( ArgumentExprs+=expr ( ',' ArgumentExprs+=expr )* )? ')' ;

commandStmt : 'say' Character=expr? Text=expr ';'
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

commandMenuOption : 'option' OptionTextExpr=expr 
                        OptionBlock=block? 
                  ;

exprStmt : callExpr
         | assignmentExpr
         ;

returnStmt : 'return' ReturnExpr=expr? ';' ;

funcDecl : 'function' Identifier=Name '(' ( Params+=variableExpr ( ',' Params+=variableExpr )* )? ')' 
               FunctionBlock=block? 
           'end' 
         ;

labelDecl : 'label' Identifier=Name 
               LabelBlock=block?
            'end' 
          ;

forLoopInitializer : VariableName=variableExpr '=' VariableExpr=expr ;