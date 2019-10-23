package com.sullivan.lexer;

public enum Category {
	KEYWORD(1),
	SPECIAL_CHAR(2),
	ARIT_OP(3),
	REL_OP(4),
	LOG_OP(5),
	IDENTIFIER(6),
	INTEGER(7),
	REAL(8),
	CHARACTER(9),
	STRING(10);
	
	private final int ALIAS;
	
	private Category(int ALIAS) {
		this.ALIAS = ALIAS;
	}
	
	public int getAlias() {
		return ALIAS;
	}
}