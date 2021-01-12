// Define a grammar called XPath
grammar XPath;

ap  : 'doc("' FN '")' '/' rp
| 'doc("' FN '")' '//' rp;  // absolute path

rp : FN 
| '*'
| '.'
| '..'
| '.'
| 'text()'
| '@' FN // @attName, attribute
| '(' rp ')'
| rp '/' rp 
| rp '//' rp 
| rp '[' f ']' 
| rp ',' rp;

f : rp
| rp '=' rp
| rp 'eq' rp 
| rp '==' rp 
| rp 'is' rp  
| '(' f ')'
| f 'and' f 
| f 'or' f 
| 'not' f;

FN : [a-zA-Z0-9.!?;_''-]+ ; // filename, string
WS : [ \t\r\n]+ -> skip ;   // skip spaces, tabs, newlines

