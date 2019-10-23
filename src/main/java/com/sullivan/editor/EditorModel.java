package com.sullivan.editor;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

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
	
	public List<Token> doLexicalAnalysis(String input) {
    	List<Token> tokens = new ArrayList<>();
    	Reader buffer = new BufferedReader(new StringReader(input));
    	Lexer lexer = new Lexer(buffer);
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
}
