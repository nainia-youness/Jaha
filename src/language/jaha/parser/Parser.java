package language.jaha.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import language.jaha.lexer.Token;
import language.jaha.nodes.BinaryOperator;
import language.jaha.nodes.GeneralObject;
import language.jaha.nodes.Identifier;
import language.jaha.nodes.Node;
import language.jaha.nodes.Variable;

public class Parser {

	
	Node parsingTree;
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
	
	List<List<Object>> lineBinaryOperators=new ArrayList<> (Arrays.asList());
	
	public int getPriorityOfBinaryOp(String operator) {
		for(int i=0;i<binaryOperators.size();i++) {
			if(operator.equals(binaryOperators.get(i).get(0))) {
				return Integer.parseInt(binaryOperators.get(i).get(2));
			}
		}
		return 0;//error
	}
	
	
	public List<List<Object>> sortListByPriority(){
		int j=0;
		while(j<lineBinaryOperators.size()) {
			int max=(Integer)lineBinaryOperators.get(j).get(2);
			int maxi=j;
			for(int i=j;i<lineBinaryOperators.size();i++) {
				if(max<(Integer)lineBinaryOperators.get(i).get(2)) {
					max=(Integer)lineBinaryOperators.get(i).get(2);
					maxi=i;
				}
			}
			Collections.swap(lineBinaryOperators, j, maxi);
			j++;
		}
		return lineBinaryOperators;
	}
	
	int addedPriority=0;
	
	
	public Node createGeneralObjectNode(int i) {
		Token token=listOfTokens.get(i); 
		GeneralObject go;
		if(token.getType().equals("Integer")) {
			go= new Variable(token.getType(),Integer.parseInt(token.getSymbol()));
		}
		else if(token.getType().equals("Double")) {
			go= new Variable(token.getType(),Double.parseDouble(token.getSymbol()));
		}
		else{//it s an identifier
			go= new Identifier(token.getType(),token.getSymbol());
		}
		return go;
	}
	
	
	public void parseExpression(int i,ErrorHandler errorHandler) {
		Token token=listOfTokens.get(i);
		if(!token.getType().equals("Semicolon")) {
			if(token.getType().equals("LeftParen"))
			{
				//call the error handler to see if the right one is in the right place
				addedPriority+=30;
			}
			else if(token.getType().equals("RightParen")) {
				addedPriority-=30;
			}
			if(isBinaryOperation(i)) {
				int priority=getPriorityOfBinaryOp(token.getSymbol());
				priority+=addedPriority;
				List<Object> bo=new ArrayList<> (Arrays.asList(token.getSymbol(),i,priority));
				lineBinaryOperators.add(bo);
				//token.showToken();
			}
		}
		else {//you have reached the semicolon
			List<List<Object>> sortedList=sortListByPriority();
			System.out.println(sortedList);
			//we create the first leaf node
			int firstOperatorItem=(Integer)sortedList.get(0).get(1);
			String type1 = listOfTokens.get(firstOperatorItem).getType();
			String operator1=listOfTokens.get(firstOperatorItem).getSymbol();
			Node leftNode1=createGeneralObjectNode(firstOperatorItem-1);
			Node rightNode1=createGeneralObjectNode(firstOperatorItem+1);
			Node node1= new BinaryOperator(type1,operator1,leftNode1,rightNode1);
			//go trought the other ones and  create the tree
			if(sortedList.size()>=1) {
				Node nodej=node1;
				for(int j=1;j<sortedList.size();j++) {
					int OperatorItemj=(Integer)sortedList.get(j).get(1);
					String typej = listOfTokens.get(OperatorItemj).getType();
					String operatorj=listOfTokens.get(OperatorItemj).getSymbol();
					Node leftNodej=createGeneralObjectNode(OperatorItemj-1);;
					Node rightNodej=node1;
					nodej= new BinaryOperator(typej,operatorj,leftNodej,rightNodej);
					node1=nodej;
				}
				parsingTree=nodej;
			}
			else {
				parsingTree=node1;
			}
		}
	}
	
	public void parse() {
		ErrorHandler errorHandler=new ErrorHandler();
		
		for(int i=0;i<this.listOfTokens.size();i++) {
			Token token=listOfTokens.get(i);
			//if no key word in the line , only expressions
			parseExpression(i,errorHandler);
		}
		System.out.println(parsingTree.getType());
		System.out.println(parsingTree.diplayTree());
		
	}
}
