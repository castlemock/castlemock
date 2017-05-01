grammar Expression;


expression
    : '${' type=expressionName
       ('(' (WS+)? (arguments+=argument (',' (WS+)? arguments+=argument )*)? ')')? '}'
     ;

expressionName
    : value=(CHAR | DIGIT | UNDER_SCORE)+
    ;

argument
    : name=argumentName
    '=' value=argumentValue
    ;

argumentName
    : value=CHAR ((CHAR| DIGIT)+)?
    ;


argumentValue
    : (argumentNumber | argumentString | array)
    ;


argumentString
    : ('\\')? '"' (value=string)? ('\\')? '"'
    ;

argumentNumber
    : value=number
    ;

number
    : value=DIGIT+ ('.' DIGIT+)?
    ;

array
    :
    ('[' (WS+)? (value+=argumentValue ( ',' (WS+)? value+=argumentValue)*)? ']')
    ;

string
    : value=(DIGIT | CHAR | WS | '<' | '>' | '|' | '(' | ')' | '?'
    | '!' | '@' | '#' | '€' | '%' | '&' | '/' | '=' | '+'
    | '-' | '*' | '^' | '^' | '.' | ',' | ':' | ';' | '^')+
    ;

CHAR: ('a'..'z' | 'A'..'Z');
DIGIT: ('0'..'9');
UNDER_SCORE: '_';
WS  : [ \t\r\n]+;