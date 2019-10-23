package com.sullivan.editor;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sullivan.editor.table.TableToken;
import com.sullivan.lexer.Lexer;
import com.sullivan.lexer.Token;

public class EditorModel {
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
	
}
