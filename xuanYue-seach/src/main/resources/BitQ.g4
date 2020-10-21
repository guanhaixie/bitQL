grammar BitQ;
@header {
	package com.xuanyue.db.xuan.antlr;
}
query : expr;
//expr : SELECT result FROM repo (WHERE orCondition  groupBy? sortBy? mix? limit? SEMI)?;
expr : SELECT result FROM repo (WHERE orCondition)? sortBy? mix? limit? SEMI?;
//groupBy:Group By fullName (Having function op=('=' | '!='|'>='|'>'|'<='|'<')  NUM)?;
//function: op=(Sum|Max|Min|Count|Avg) '(' (fullName|NUM)  ')';
repo: fullName (',' fullName)*;

tablePart: tjoinPart|fullName;
tjoinPart: fullName  ((LEFT|RIGHT) JOIN fullName ON orCondition)*;

orCondition : andCondition (operator=orNot  andCondition)*;
andCondition : conditionElement (operator=andNot conditionElement)*;
conditionElement:  groupCondition|conditionExpr;
groupCondition:  '(' orCondition ')';
conditionExpr : fullName op=('=' | '!='|'>='|'>'|'<='|'<'|Contains|PositionMatch) values|phone_seach;
values:to_date|STRING|NUM|fullName|boolTF|TransArrt;
to_date:TO_DATE '(' STRING COMMA STRING  ')';
//date_attr:STRING;
result:fullName(','fullName)*;
//result:fullName(','fullName)*|function(','function)*;
fullName:NAME ('.'NAME)*;
boolTF: TRUE|FALSE;

TransArrt:'?'('0' .. '9')+;

orNot:OR NOT?;
andNot:AND NOT?;

phone_seach:Phone_seach '('  fullName  ','  op=(PositionMatch|Contains|Has_Every_Char)  ','STRING  ')';


sortE: fullName('[' STRING ']')? (DESC|ASC)?;
sortBy: Order By  sortE   (','sortE)*;
limit:LIMIT NUM(','NUM)?;

mix:MIX fullName ('[' NUM  (','NUM)*  ']')?;
//Sum:[Ss][Uu][Mm];
//Max:[Mm][Aa][Xx];
//Min:[Mm][Ii][Nn];
//Avg:[Aa][Vv][Gg];
//Count:[Cc][Oo][Uu][Nn][Tt];
//Having:[Hh][Aa][Vv][Ii][Nn][Gg];
//Group:[Gg][Rr][Oo][Uu][Pp];
MIX:[Mm][Ii][Xx];
DESC:[Dd][Ee][Ss][Cc];
ASC:[Aa][Ss][Cc];
Phone_seach:[Pp][Hh][Oo][Nn][Ee]'_'[Ss][Ee][Aa][Cc][Hh];
PositionMatch:[Pp][Oo][Ss][Ii][Tt][Ii][Oo][Nn][Mm][Aa][Tt][Cc][Hh];
Contains:[Cc][Oo][Nn][Tt][Aa][Ii][Nn][Ss];
Has_Every_Char:[Hh][Aa][Ss]'_'[Ee][Vv][Ee][Rr][Yy]'_'[Cc][Hh][Aa][Rr];
SELECT : [Ss][Ee][Ll][Ee][Cc][Tt];
FROM : [Ff][Rr][Oo][Mm];
WHERE : [Ww][Hh][Ee][Rr][Ee];
ON:[Oo][Nn];
LEFT:[Ll][Ee][Ff][Tt];
RIGHT:[Rr][Ii][Gg][Hh][Tt];
JOIN:[Jj][Oo][Ii][Nn];
AND : [Aa][Nn][Dd];
OR : [Oo][Rr];
NOT : [Nn][Oo][Tt];
TO_DATE:[Tt][Oo]'_'[Dd][Aa][Tt][Ee];
ExprNot:'!';
COMMA : ',';
SEMI : ';';
LIMIT:[Ll][Ii][Mm][Ii][Tt];
Order:[Oo][Rr][Dd][Ee][Rr];
By:[Bb][Yy];
NAME:[a-zA-Z][a-zA-Z_0-9]*;
DOT:'.';
Brackets_L:'(';
Brackets_R:')';
STRING : '\'' ( ~('\''|'\\') | ('\\' .) )* '\''
    | '"' ( ~('"'|'\\') | ('\\' .) )* '"'
    ;
//INT : '0'..'9'+;
NUM: '0'..'9'+(DOT '0'..'9'+ )?('l'|'f')?;
TRUE:[Tt][Rr][Uu][Ee];
FALSE:[Ff][Aa][Ll][Ss][Ee];
WS : [ \t\n\r]+ -> skip ;
