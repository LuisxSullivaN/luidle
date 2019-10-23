package com.sullivan.editor;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sullivan.editor.table.TableToken;
import com.sullivan.lexer.Category;
import com.sullivan.lexer.Lexer;
import com.sullivan.lexer.Token;

public class EditorModel {

    private String[] keywords = {
    		"Int","String","Char","Bool","Float","readln","print","if","elseif","endif",
    		"for","endfor","main","void","endf"};
    private String[] specialChars = {
    	"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W",
    	"X","Y","Z","a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u",
    	"v","w","x","y","z","\"","(",")","{","}","[","]","\\","¬","$",";",":",".","¡","¿","?","\'","|",
    	"="," "};
    private String[] aritOp = {"+","-","/","*","%"};
    private String[] relOp = {"==","!=",">=","<=",">","<"};
    private String[] logOp = {"&&","||","!"};

	public void writeToFile(File file, String text) {
		try {
			Files.write(file.toPath(), text.getBytes());
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public String readFromFile(File file) {
		String content;
		try {
			content = new String(Files.readAllBytes(file.toPath()));
		} catch(IOException e) {
			e.printStackTrace();
			content = "";
		}
		return content;
	}
	
	public List<Token> retrieveTokens(Lexer lexer) {
    	List<Token> tokens = new ArrayList<>();
    	try {
    		Token token;
    		while ((token = lexer.yylex()) != null) {
    			tokens.add(token);
    		}
    	} catch(IOException e) {
    		e.printStackTrace();
    	}
    	return tokens;
	}
	
	public List<TableToken> toTableTokens(List<Token> tokens) {
		List<TableToken> tableTokens = new ArrayList<>();
		Map<String,Integer> knownTokens = new HashMap<>();
		int id = 1;
		for (Token token : tokens) {
			if (knownTokens.containsKey(token.getLexeme())) {
				tableTokens.stream()
					.filter(tableToken -> tableToken.getSymbol().equals(token.getLexeme()))
					.forEach(tableToken -> {
						if (!tableToken.getLines().contains(token.getLine()))
							tableToken.getLines().add(token.getLine());
					});
			} else {
				TableToken tableToken = new TableToken();
				tableToken.setId(id++);
				tableToken.setSymbol(token.getLexeme());
				tableToken.getLines().add(token.getLine());
				tableToken.setAlias(token.getCategory().getAlias());
				tableTokens.add(tableToken);
				knownTokens.put(token.getLexeme(), token.getLine());
			}
		}
		return tableTokens;
	}
	
	public List<TableToken> staticTokens(Category category) {
		List<TableToken> tableTokens = new ArrayList<>();
		String[] elements = new String[0];
		switch (category) {
		case ARIT_OP: elements = aritOp;
			break;
		case KEYWORD: elements = keywords;
			break;
		case LOG_OP: elements = logOp;
			break;
		case REL_OP: elements = relOp;
			break;
		case SPECIAL_CHAR: elements = specialChars;
			break;
		default:
			break;
		}
    	for (int i = 0; i < elements.length; i++) {
    		TableToken tableToken = new TableToken();
    		tableToken.setId(i + 1);
    		tableToken.setSymbol(elements[i]);
    		tableToken.setAlias(category.getAlias());
    		tableTokens.add(tableToken);
    	}
    	return tableTokens;
	}
}
