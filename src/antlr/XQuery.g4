// Define a grammar called XQuery
grammar XQuery;
import XPath;

r : ap
| '(' r ')'
| r '/' rp
| r '//' rp
| r ',' r
| '<' FN '>' '{' r '}' '</' FN '>'
| fc lc? wc? rc 
| lc r
| '$' FN // variable
| STRING 
| join
| '<' FN '>' r (r)* '</' FN '>'
;

fc : 'for' '$' FN 'in' r (',' '$' FN 'in' r)*;

wc : 'where' c;

lc : 'let' '$' FN ':=' r (',' '$' FN ':=' r)*;

rc : 'return' r;

join: 'join' '(' r ',' r ',' list ',' list ')';

list: '[' (FN)* (',' FN)* ']';

c : 'empty' '(' r ')'
| 'some' '$' FN 'in' r (',' '$' FN 'in' r)* 'satisfies'  c
| c 'or' c
| '(' c ')'
| 'not' c
| c 'and' c
| r '=' r
| r 'eq' r
| r '==' r
| r 'is' r
;

STRING : '"' [a-zA-Z0-9 .,!?;_'"-]+ '"' ;       
