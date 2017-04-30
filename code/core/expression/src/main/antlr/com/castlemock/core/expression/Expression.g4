grammar Expression;


expression
    : type=expressionName
       ('(' (arguments+=argument ( ',' arguments+=argument )*)? ')')?
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
    : '"' (value=string)? '"'
    ;

argumentNumber
    : value=number
    ;

number
    : value=DIGIT+ ('.' DIGIT+)?
    ;

array
    :
    ('[' (value+=argumentValue ( ',' value+=argumentValue)*)? ']')
    ;

string
    : value=(DIGIT | CHAR | '<' | '>' | '|' | '(' | ')' | '?'
    | '!' | '@' | '#' | '€' | '%' | '&' | '/' | '\\' | '=' | '+'
    | '-' | '*' | '^' | '^' | '.' | ',' | ':' | ';' | '^')+
    ;

CHAR: ('a'..'z' | 'A'..'Z');
DIGIT: ('0'..'9');
UNDER_SCORE: '_';
WS  : [ \t\r\n]+ -> skip ;