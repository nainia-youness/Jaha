package language.jaha.parser;

import java.util.Arrays;
import java.util.List;

import language.jaha.lexer.Token;
import language.jaha.nodes.BinaryOperator;

public class Parser {

	
	List<Object> parsingTree=Arrays.asList();
	List<Token> listOfTokens=Arrays.asList();
	
	public Parser(List<Token> listOfTokens) {
		this.listOfTokens=listOfTokens;
	}
	
	final List<List<String>> unaryOperators=Arrays.asList(
	Arrays.asList("!","Op_not","13"),
	Arrays.asList("&","Op_and","7"),
	Arrays.asList("|","Op_or","5"),
	Arrays.asList("++","Op_increment","14"),
	Arrays.asList("--","Op_negativeincrement","14"),
	Arrays.asList("-","Op_subtract","13")
	);
	
	final List<List<String>> binaryOperators=Arrays.asList(//priorite max 14 , pour les ( 15
			Arrays.asList("=","Op_assign","1"),
			Arrays.asList("==","Op_equal","8"),
			Arrays.asList("!=","Op_notequal","8"),
			Arrays.asList("+","Op_add","11"),
			Arrays.asList("-","Op_subtract","11"),
			Arrays.asList("*","Op_multiply","12"),
			Arrays.asList("/","Op_divide","12"),
			Arrays.asList("%","Op_mod","12"),
			Arrays.asList("<","Op_less","9"),
			Arrays.asList(">","Op_greater","9"),
			Arrays.asList("<=","Op_lessequal","9"),
			Arrays.asList(">=","Op_greaterequal","9"),
			Arrays.asList("&&","Op_and","4"),
			Arrays.asList("||","Op_or","3")
	);
	
	
	public boolean isBinaryOperation(int j) {
		if(j==0)
			return false;
		if(this.listOfTokens.get(j).getSymbol().equals("-"))
		{
			if(this.listOfTokens.get(j-1).getType().equals("RightParen") || this.listOfTokens.get(j-1).getType().equals("Integer") || this.listOfTokens.get(j-1).getType().equals("Double") || this.listOfTokens.get(j-1).getType().equals("Identifier"))
				return true;
			else
			{
				System.out.println("ERROR");
				return false;
			}
		}
		for(int i=0;i<binaryOperators.size();i++) {
			if(this.listOfTokens.get(j).getSymbol().equals(binaryOperators.get(i).get(0)))
				return true;
		}
		return false;
	}
	

	
	/*BinaryOperator createBinaryOperator(int i) {
		Object leftValue;
		if(this.listOfTokens.get(i).getType().equals("Integer"))
			leftValue=(Integer)Integer.parseInt(this.listOfTokens.get(i).getSymbol());
		else if(this.listOfTokens.get(i).getType().equals("Double"))
			leftValue=(Double)Double.parseDouble(this.listOfTokens.get(i).getSymbol());
		else if(this.listOfTokens.get(i).getType().equals("String"))
			leftValue=(Double)Double.parseDouble(this.listOfTokens.get(i).getSymbol());
		BinaryOperator myBo=new BinaryOperator(this.listOfTokens.get(i).getType(),this.listOfTokens.get(i).getSymbol(),this.listOfTokens.get(i-1).getSymbol(),this.listOfTokens.get(i+1).getSymbol());
		return myBo;
	}*/
	
	List<List<Object>> lineBinaryOperators=Arrays.asList();
	
	public int getPriorityOfBinaryOp(String operator) {
		for(int i=0;i<binaryOperators.size();i++) {
			if(operator.equals(binaryOperators.get(i).get(0))) {
				return Integer.parseInt(binaryOperators.get(i).get(2));
			}
		}
		return 0;//error
	}
	
	
	public List<Object> SortList(){
		return null;
	}
	
	
	public void parseExpression(int i,ErrorHandler errorHandler) {
		Token token=listOfTokens.get(i);
		if(!token.getType().equals("Semicolon")) {
			if(isBinaryOperation(i)) {
				int priority=getPriorityOfBinaryOp(token.getSymbol());
				List<Object> bo=Arrays.asList(token.getSymbol(),i,priority);
				lineBinaryOperators.add(bo);
				//BinaryOperator binaryOp=createBinaryOperator(i);
				token.showToken();
			}
		}
		else {//you have reached the semicolon
			
		}
	}
	
	public void parse() {
		ErrorHandler errorHandler=new ErrorHandler();
		
		for(int i=0;i<this.listOfTokens.size();i++) {
			Token token=listOfTokens.get(i);
			//if no key word in the line , only expressions
			parseExpression(i,errorHandler);
		}
	}
}
