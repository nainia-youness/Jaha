package client;

import language.jaha.lexer.Lexer;

public class Client {

	public static void main(String[] args) {
		Lexer lexer=new Lexer();
		//lexer.printCode();
		lexer.tokenize();
	}
}
