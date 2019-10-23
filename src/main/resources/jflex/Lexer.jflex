/*This file should be used as an input when running jflex to generate the desired lexer*/

package com.sullivan.lexer;

import java.util.List;
import java.util.ArrayList;

%%

%class Lexer
%unicode
%line
%type Token

%{
	private List<Token> errors = new ArrayList<>();
	
	public List<Token> getErrors() {
		return errors;
	}
    private Token token(Category type) {
    	if (type == Category.STRING || type == Category.CHARACTER) {
    		return new Token(
    			yytext().substring(1, yytext().length() - 1),
    			yyline + 1,
    			type);
    	}
        return new Token(yytext(), yyline + 1, type);
    }
%}

Integer = [\+|-]?[0-9]
Float = {Integer}\.[0-9]*
Identifier = [A-Za-z$_][A-Za-z$_0-9]*
String = \"[^\n\r\"]*\"
Character = \'[^\n\r\"]?\'

%%

"Int" {return token(Category.KEYWORD);}
"String" {return token(Category.KEYWORD);}
"Char" {return token(Category.KEYWORD);}
"Bool" {return token(Category.KEYWORD);}
"Float" {return token(Category.KEYWORD);}
"readln" {return token(Category.KEYWORD);}
"print" {return token(Category.KEYWORD);}
"if" {return token(Category.KEYWORD);}
"elseif" {return token(Category.KEYWORD);}
"else" {return token(Category.KEYWORD);}
"endif" {return token(Category.KEYWORD);}
"for" {return token(Category.KEYWORD);}
"endfor" {return token(Category.KEYWORD);}
"main" {return token(Category.KEYWORD);}
"void" {return token(Category.KEYWORD);}
"endf" {return token(Category.KEYWORD);}

"+"   {return token(Category.ARIT_OP);}
"-"   {return token(Category.ARIT_OP);}
"*"   {return token(Category.ARIT_OP);}
"/"   {return token(Category.ARIT_OP);}
"%"   {return token(Category.ARIT_OP);}

"=="   {return token(Category.REL_OP);}
"<"   {return token(Category.REL_OP);}
"<="   {return token(Category.REL_OP);}
">"   {return token(Category.REL_OP);}
">="   {return token(Category.REL_OP);}
"!="   {return token(Category.REL_OP);}

"&&"   {return token(Category.LOG_OP);}
"\|\|"   {return token(Category.LOG_OP);}
"!"   {return token(Category.LOG_OP);}

"="   {return token(Category.SPECIAL_CHAR);}
";"   {return token(Category.SPECIAL_CHAR);}
","   {return token(Category.SPECIAL_CHAR);}
"\."   {return token(Category.SPECIAL_CHAR);}
"\$"   {return token(Category.SPECIAL_CHAR);}
"_"   {return token(Category.SPECIAL_CHAR);}
"-"   {return token(Category.SPECIAL_CHAR);}
":"   {return token(Category.SPECIAL_CHAR);}
\"   {return token(Category.SPECIAL_CHAR);}
\'   {return token(Category.SPECIAL_CHAR);}
"#"   {return token(Category.SPECIAL_CHAR);}
"@"   {return token(Category.SPECIAL_CHAR);}
"\^"   {return token(Category.SPECIAL_CHAR);}
"&"   {return token(Category.SPECIAL_CHAR);}
"\|"   {return token(Category.SPECIAL_CHAR);}
"¡"   {return token(Category.SPECIAL_CHAR);}
"¿"   {return token(Category.SPECIAL_CHAR);}
"\?"   {return token(Category.SPECIAL_CHAR);}
"\("   {return token(Category.SPECIAL_CHAR);}
"\)"   {return token(Category.SPECIAL_CHAR);}
"\["   {return token(Category.SPECIAL_CHAR);}
"\]"   {return token(Category.SPECIAL_CHAR);}
"{"   {return token(Category.SPECIAL_CHAR);}
"}"   {return token(Category.SPECIAL_CHAR);}

{Integer}   {return token(Category.INTEGER);}
{Float}    {return token(Category.REAL);}
{Identifier}    {return token(Category.IDENTIFIER);}
{String}    {return token(Category.STRING);}
{Character}    {return token(Category.CHARACTER);}

\s	{ /* Ignore */ }
[^] {errors.add(token(null));}
