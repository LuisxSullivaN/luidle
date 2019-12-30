/*This file should be used as an input when running jflex to generate the desired lexer*/

package com.sullivan.lexer;

import java_cup.runtime.Symbol;

%%

%class LexerCup
%unicode
%line
%column
%cup

%{
	
    private Symbol symbol(int type) {
    	if (type == sym.STRING_LITERAL || type == sym.CHARACTER_LITERAL) {
    		return new Symbol(
    			type,
    			yyline + 1,
    			yycolumn + 1,
    			yytext().substring(1, yytext().length() - 1)
    			);
    	}
        return new Symbol(type, yyline + 1, yycolumn + 1, yytext());
    }
%}

Integer = [\+|-]?[0-9]+
Float = {Integer}\.[0-9]*
Identifier = [A-Za-z$_][A-Za-z$_0-9]*
String = \"[^\n\r\"]*\"
Character = \'[^\n\r\"]?\'

%%

"Int" {return symbol(sym.INT);}
"String" {return symbol(sym.STRING);}
"Char" {return symbol(sym.CHAR);}
"Bool" {return symbol(sym.BOOL);}
"Float" {return symbol(sym.FLOAT);}
"readln" {return symbol(sym.READLN);}
"print" {return symbol(sym.PRINT);}
"if" {return symbol(sym.IF);}
"else" {return symbol(sym.ELSE);}
"endif" {return symbol(sym.ENDIF);}
"for" {return symbol(sym.FOR);}
"endfor" {return symbol(sym.ENDFOR);}
"main" {return symbol(sym.MAIN);}
"void" {return symbol(sym.VOID);}
"endf" {return symbol(sym.ENDF);}
"true"	{return symbol(sym.TRUE);}
"false"	{return symbol(sym.FALSE);}

"+"   {return symbol(sym.PLUS);}
"-"   {return symbol(sym.MINUS);}
"*"   {return symbol(sym.TIMES);}
"/"   {return symbol(sym.DIVIDE);}
"%"   {return symbol(sym.MOD);}

"=="   {return symbol(sym.EQUAL);}
"<"   {return symbol(sym.LOWER_THAN);}
"<="   {return symbol(sym.LOWER_EQ_THAN);}
">"   {return symbol(sym.GREATER_THAN);}
">="   {return symbol(sym.GREATER_EQ_THAN);}
"!="   {return symbol(sym.NOT_EQUAL);}

"&&"   {return symbol(sym.AND);}
"\|\|"   {return symbol(sym.OR);}
"!"   {return symbol(sym.NEGATE);}

"="   {return symbol(sym.ASSIGN);}
";"   {return symbol(sym.SEMICOLON);}
","   {return symbol(sym.COMMA);}
\\   {return symbol(sym.INV_DIAGONAL);}
"\."   {return symbol(sym.DOT);}
"\$"   {return symbol(sym.DOLLAR);}
"_"   {return symbol(sym.UNDERSCORE);}
":"   {return symbol(sym.COLON);}
\"   {return symbol(sym.DOUBLE_QUOTE);}
\'   {return symbol(sym.SINGLE_QUOTE);}
"#"   {return symbol(sym.HASH);}
"@"   {return symbol(sym.AT);}
"\^"   {return symbol(sym.EXP);}
"&"   {return symbol(sym.AMPERSAND);}
"\|"   {return symbol(sym.PIPE);}
"¡"   {return symbol(sym.L_EXCL);}
"¿"   {return symbol(sym.OP_QMARK);}
"\?"   {return symbol(sym.CLOSE_QMARK);}
"\("   {return symbol(sym.LPAR);}
"\)"   {return symbol(sym.RPAR);}
"\["   {return symbol(sym.LBRACKET);}
"\]"   {return symbol(sym.RBRACKET);}
"{"   {return symbol(sym.LBRACE);}
"}"   {return symbol(sym.RBRACE);}

{Integer}   {return symbol(sym.INTEGER);}
{Float}    {return symbol(sym.REAL);}
{Identifier}    {return symbol(sym.IDENTIFIER);}
{String}    {return symbol(sym.STRING_LITERAL);}
{Character}    {return symbol(sym.CHARACTER_LITERAL);}

\s	{ /* Ignore */ }
[^] {return symbol(0);}
