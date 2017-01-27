grammar Muzean;

/*
    <programa> ::= <cabecalho> <estruturas>
*/
programa : cabecalho estruturas;

/*
2. <cabecalho> ::= <definicoes> \n\n <flags>+
*/
cabecalho : flags '\n' definicao* ;

/*
3. <definicoes> ::= # ALIAS : <compassos>
*/
//definicoes : definicao+;

definicao : '#' ALIAS ':' estruturas '\n';

/*
4. <flags> ::= ‘tom’ : NOTA  \n  ‘escala’ : ESCALA  \n ‘compasso’ : NUMERO \n <flag>
*/
flags : flag flag_op*; 

flag : 'tom' ':' NOTA '\n' 'escala' ':' ESCALA '\n' 'compasso' ':' NUMERO '\n'; 

/*
5. <flag> ::= ‘transposicao’ : SIGNED_NUMERO \n <flag>
*/
flag_op : 'transposicao' ':' SIGNED_NUMERO '\n';

estruturas : (estrutura '\n')+;

/*
7. <estruturas> ::= <loop> | <compasso> | ALIAS
*/
estrutura : loop | compasso | ALIAS;

/*
8. <loop> ::= NUMERO { <compassos> }
*/
loop : NUMERO '{' '\n' estruturas '}';  // colocar \n antes de compassos?

/*
9. <compasso> :: = [ <sons> ]
*/
compasso : '[' sons ']';

/*
10. <sons> ::= <som> (, <som>)*
*/
sons : a=som (', ' b+=som)*;

/*
11. <som> ::= <nota> | - | *
*/
som : nota | '-' | '*';

/*
12. <nota> ::= NOTA OCTAVE
*/
nota : NOTA NUMERO;

/*REGEX*/

/*
14. ALIAS : [A-Z]+; 
*/


/*
15. OCTAVE: ([0-9])
*/


/*
16. NOTA: C | C# | D | D# | E | F | F# | G | G# | A | A# | B
*/
ESCALA : 'm' | 'M';
NOTA : 'C' | 'C#' | 'D' | 'D#' | 'E' | 'F' | 'F#' | 'G'| 'G#' | 'A' | 'A#' | 'B';
ALIAS: ('A'..'Z')+;
/*
17. COMMENT: \/\/.*\\n
*/
COMMENT :	'--' ~('\n' | '\r')* '\r'? '\n' {skip();};

/*
18. NUMERO: [1-9][0-9]*
*/
NUMERO : '1'..'9' ('0'..'9')*;

SIGNED_NUMERO : ('-' | '+') NUMERO;

/*
19. ESCALA: m | M
*/


/*
    Foi criada uma regra do lexer para identificar espaços em branco de acordo
    com a especificação da linguagem e com o enunciado do trabalho.
*/
WS : (' ' |'\t' | '\r' ) {skip(); };