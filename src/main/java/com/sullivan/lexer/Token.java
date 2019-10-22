package com.sullivan.lexer;

public class Token {
	private String lexeme;
	private int line;
	private Category category;
	
	public Token() {}
	
	public Token(String lexeme, int line, Category category) {
		this.lexeme = lexeme;
		this.line = line;
		this.category = category;
	}

	public String getLexeme() {
		return lexeme;
	}

	public void setLexeme(String lexeme) {
		this.lexeme = lexeme;
	}
	
	public int getLine() {
		return line;
	}
	
	public void setLine(int line) {
		this.line = line;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
	
	@Override
	public String toString() {
		return "Lexeme: " + lexeme + " Category: " + category + " Line: " + line;
	}
}
