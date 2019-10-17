package lexer;

import java.util.List;
import java.util.ArrayList;

public class Lexer {

    public List<String> extractWords(String input) {
    	List<String> words = new ArrayList<>();
    	String word = "";
    	int line = 1;
    	for (int i = 0; i < input.length(); i++) {
    		char character = input.charAt(i);
    		if (character != ' ') {
    			if (character != '+' && character != ';')
    				word = word.concat(Character.toString(character));
    			else {
    				words.add(word);
    				words.add(Character.toString(character));
    				word = "";
    			}
    		}
    	}
    	return words;
    }
}
