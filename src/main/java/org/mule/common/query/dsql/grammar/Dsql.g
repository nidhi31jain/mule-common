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

@rulecatch {
    catch (RecognitionException e) {
        throw e;
    }
}

@parser::members {
    public void reportError(RecognitionException e) {
        throw new org.mule.common.query.dsql.parser.exception.DsqlParsingException(e);
    }
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
    (IDENT(','! IDENT)*)
    direction?;

direction:
	(ASC|DESC);

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
    | OPENING_PARENTHESIS^expression CLOSING_PARENTHESIS!
    | string
    | number;
    
negation:
      NOT^* term;

relation:
    negation (OPERATOR^ negation)*;

expression:
      relation ((AND^|OR^) relation)*;

ASC : (A_ S_ C_ | A_ S_ C_ E_ N_ D_ I_ N_ G_);
DESC : (D_ E_ S_ C_ | D_ E_ S_ C_ E_ N_ D_ I_ N_ G_);
SELECT  : S_ E_ L_ E_ C_ T_ ; 
FROM  : F_ R_ O_ M_ ;
WHERE  : W_ H_ E_ R_ E_;
ORDER: O_ R_ D_ E_ R_;
BY: B_ Y_;
LIMIT: L_ I_ M_ I_ T_;
OFFSET: O_ F_ F_ S_ E_ T_;
AND: A_ N_ D_;
OR: O_ R_;
NOT: N_ O_ T_;
OPENING_PARENTHESIS: '(';
CLOSING_PARENTHESIS: ')';

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
