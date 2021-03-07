package client;

import language.jaha.lexer.Lexer;
import language.jaha.parser.Parser;

public class Client {

	public static void main(String[] args) throws Exception {
		Lexer lexer=new Lexer();
		//lexer.printCode();
		lexer.tokenize();
		//lexer.showTokens();
		Parser parser=new Parser(lexer.getListOfTokens());
		parser.parse();
	}
}
