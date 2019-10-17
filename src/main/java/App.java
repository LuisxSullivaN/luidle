import lexer.Lexer;

public class App {
    public static void main(String... args) {
    	Lexer lexer = new Lexer();
    	
    	String input = "5               + 5;";
    	
    	for (String word : lexer.extractWords(input))
    		System.out.println(word + " ");
    }
}
