package language.jaha.parser;

import java.util.Arrays;
import java.util.List;

import language.jaha.lexer.Token;

public class Parser {

	
	List<Object> parsingTree=Arrays.asList();
	List<Token> listOfTokens=Arrays.asList();
	
	public Parser(List<Token> listOfTokens) {
		this.listOfTokens=listOfTokens;
	}
	
	final List<List<String>> unaryOperators=Arrays.asList(
	Arrays.asList("!","Op_not"),
	Arrays.asList("&","Op_and"),
	Arrays.asList("|","Op_or"),
	Arrays.asList("++","Op_increment"),
	Arrays.asList("--","Op_negativeincrement"),
	Arrays.asList("-","Op_subtract")
	);
	
	final List<List<String>> binaryOperators=Arrays.asList(
			Arrays.asList("=","Op_assign"),
			Arrays.asList("==","Op_equal"),
			Arrays.asList("!=","Op_notequal"),
			Arrays.asList("+","Op_add"),
			Arrays.asList("-","Op_subtract"),
			Arrays.asList("*","Op_multiply"),
			Arrays.asList("/","Op_divide"),
			Arrays.asList("%","Op_mod"),
			Arrays.asList("<","Op_less"),
			Arrays.asList(">","Op_greater"),
			Arrays.asList("<=","Op_lessequal"),
			Arrays.asList(">=","Op_greaterequal"),
			Arrays.asList("&&","Op_and"),
			Arrays.asList("||","Op_or")
	);
	
	
	public boolean isBinaryOperation(int j) {
		if(j==0)
			return false;
		if(this.listOfTokens.get(j).getSymbol().equals("-"))
		{
			if(this.listOfTokens.get(j-1).getType().equals("Integer") || this.listOfTokens.get(j-1).getType().equals("Double") || this.listOfTokens.get(j-1).getType().equals("Identifier"))
				return true;
			else
				return false;
		}
		for(int i=0;i<binaryOperators.size();i++) {
			if(this.listOfTokens.get(j).getSymbol().equals(binaryOperators.get(i).get(0)))
				return true;
		}
		return false;
	}
	
	
	public void parse() {
		for(int i=0;i<this.listOfTokens.size();i++) {
			Token token=listOfTokens.get(i);
			if(isBinaryOperation(i)) {
				boolean isNextTokenVar=this.listOfTokens.get(i+1).getType().equals("Integer") || this.listOfTokens.get(i+1).getType().equals("Double") || this.listOfTokens.get(i+1).getType().equals("Identifier");
				if(!isNextTokenVar)
					System.out.println("ERROR= wrong operation usage");
				token.showToken();
			}
		}
	}
}
