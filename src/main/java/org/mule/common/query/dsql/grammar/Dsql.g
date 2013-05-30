grammar Dsql;


options {
  language = Java;
  output=AST;
  ASTLabelType=CommonTree;
}

@header {
  package org.mule.common.query.dsql.grammar;
}

@lexer::header {
  package org.mule.common.query.dsql.grammar;
}

select:
      SELECT^
      (IDENT(','! IDENT)*| ASTERIX)
      from 
      where?
      orderBy?
      limit?
      offset?;

from:
      FROM^
      IDENT(','! IDENT)*;

where:
      WHERE^
      expression;
    
orderBy:
    ORDER^ BY! 
    IDENT(','IDENT)*;

limit:
    LIMIT^
    number;
    
offset:
    OFFSET^
    number;
    
string:
    STRING_LITERAL;

number:
  NUMBER_LITERAL;
    
term:
    IDENT
    | '('^expression')'!
    | string
    | number;
    
negation:
      'not'? term;

relation:
    negation (OPERATOR^ negation)*;

expression:
      relation (('and'^|'or'^) relation)*;
 
SELECT  : S_ E_ L_ E_ C_ T_ ; 
FROM  : F_ R_ O_ M_ ;
WHERE  : W_ H_ E_ R_ E_;
ORDER: O_ R_ D_ E_ R_;
BY: B_ Y_;
LIMIT: L_ I_ M_ I_ T_;
OFFSET: O_ F_ F_ S_ E_ T_;
 
STRING_LITERAL: '\''  ~('\'' | '\r' | '\n')* '\'';
NUMBER_LITERAL:('0'..'9'|'.')*; 
 
IDENT : ('a'..'z' | 'A'..'Z' | '0'..'9'| '-' | '_' | '.')+;

ASTERIX : '*';
OPERATOR : '='|'>'|'<'|'<='|'<>'|'>=';

COMMENT  : ( ('--'|'#') ~('\n'|'\r')* '\r'? '\n' ) {$channel=HIDDEN;};
WS : ( ' ' | '\t' | '\n' | '\r' | '\f' )+ {$channel=HIDDEN;};


fragment A_:('a'|'A');
fragment B_:('b'|'B');
fragment C_:('c'|'C');
fragment D_:('d'|'D');
fragment E_:('e'|'E');
fragment F_:('f'|'F');
fragment G_:('g'|'G');
fragment H_:('h'|'H');
fragment I_:('i'|'I');
fragment J_:('j'|'J');
fragment K_:('k'|'K');
fragment L_:('l'|'L');
fragment M_:('m'|'M');
fragment N_:('n'|'N');
fragment O_:('o'|'O');
fragment P_:('p'|'P');
fragment Q_:('q'|'Q');
fragment R_:('r'|'R');
fragment S_:('s'|'S');
fragment T_:('t'|'T');
fragment U_:('u'|'U');
fragment V_:('v'|'V');
fragment W_:('w'|'W');
fragment X_:('x'|'X');
fragment Y_:('y'|'Y');
fragment Z_:('z'|'Z');
