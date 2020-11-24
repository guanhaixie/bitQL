grammar BitQ;
@header {
	package com.xuanyue.db.xuan.antlr;
}

@lexer::members{
	
	private int parallel = 1;
	
	java.util.regex.Pattern parallelReg = java.util.regex.Pattern.compile("parallel\\s*?\\(\\s*?(\\d+?)\\s*?\\)");
	java.util.regex.Pattern p = java.util.regex.Pattern.compile("/\\*(.*?)\\*/");
	public int getParallel(){
		return this.parallel;
	}
	void showme(){
		
		java.util.regex.Matcher m = p.matcher(this.getText());
		
		if(m.find()){
			
			java.util.regex.Matcher m2 = parallelReg.matcher(m.group(1));
			if(m2.find()) {
				parallel = Integer.parseInt(m2.group(1));
				if(parallel<1){
					parallel=1;
				}else if(parallel>20){
					parallel =20;
				}
			}
		}
		
		skip();
	}
}

@parser::members{
@Override
public Token match(int ttype) throws RecognitionException {
	Token t = getCurrentToken();
	if ( t.getType()==ttype ) {
		if ( ttype==Token.EOF ) {
			matchedEOF = true;
		}
		_errHandler.reportMatch(this);
		consume();
		return t;
	}
	else {
		throw new RuntimeException("miss "+ tokenNames[ttype]);
	}
	
}
}
query : expr;

//expr : SELECT result FROM repo (WHERE orCondition  groupBy? sortBy? mix? limit? SEMI)?;
expr :  SELECT result FROM repo (WHERE orCondition)? sortBy? mix? limit? saveAsFile?  SEMI?;
//groupBy:Group By fullName (Having function op=('=' | '!='|'>='|'>'|'<='|'<')  NUM)?;
//function: op=(Sum|Max|Min|Count|Avg) '(' (fullName|NUM)  ')';
repo: fullName (',' fullName)*;
saveAsFile: SAVE AS STRING;
tablePart: tjoinPart|fullName;
tjoinPart: fullName  ((LEFT|RIGHT) JOIN fullName ON orCondition)*;

orCondition : NOT? andCondition (operator=orNot  andCondition)*;
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

TransArrt:'?'[a-zA-Z_0-9]+;

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
SAVE:[Ss][Aa][Vv][Ee];
AS:[Aa][Ss];
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
SQL_COMMENT : '/*' .*? '*/' { showme(); };
