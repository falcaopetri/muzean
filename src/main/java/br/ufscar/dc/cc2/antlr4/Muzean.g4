grammar Muzean;

programa : cabecalho estruturas;

cabecalho : flags '\n' definicao* ;

definicao : '#' ALIAS ':' '\n' estruturas '\n';

flags : flag flag_op*; 

flag : 'tom' ':' NOTA '\n' 'escala' ':' ESCALA '\n' 'compasso' ':' NUMERO '\n'; 

flag_op : 'transposicao' ':' SIGNED_NUMERO '\n';

estruturas : (estrutura '\n')+;

estrutura : loop | compasso | ALIAS;

loop : NUMERO '{' '\n' estruturas '}';  // colocar \n antes de compassos?

compasso : '[' sons ']';

sons : a=som (', ' b+=som)*;

som : nota | '-' | '*';

nota : NOTA NUMERO;

/*REGEX*/

ESCALA : 'm' | 'M';
NOTA : 'C' | 'C#' | 'D' | 'D#' | 'E' | 'F' | 'F#' | 'G'| 'G#' | 'A' | 'A#' | 'B';
ALIAS: ('A'..'Z')+;

COMMENT :	'--' ~('\n' | '\r')* '\r'? '\n' {skip();};

NUMERO : '1'..'9' ('0'..'9')*;

SIGNED_NUMERO : ('-' | '+') NUMERO;

WS : (' ' |'\t' | '\r' ) {skip(); };