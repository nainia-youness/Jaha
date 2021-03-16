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

	
	//Node parsingTree;
	List<Node> listOfParsingTrees=new ArrayList<> ();
	List<Token> listOfTokens=Arrays.asList();
	List<List<Object>> lineBinaryOperators=new ArrayList<> (Arrays.asList());
	int addedPriority=0;
	List<List<Object>> ListOfNodes=new ArrayList<> (Arrays.asList());
	
	
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
	
		
	
	
	private int getPriorityOfBinaryOp(String operator) {
		for(int i=0;i<binaryOperators.size();i++) {
			if(operator.equals(binaryOperators.get(i).get(0))) {
				return Integer.parseInt(binaryOperators.get(i).get(2));
			}
		}
		return 0;//error
	}
	
	
	private List<List<Object>> sortListByPriority(){
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
	
	
	
	
	private Node createGeneralObjectNode(int i) {
		Token token=listOfTokens.get(i); 
		GeneralObject go;
		if(token.getType().equals("Integer")) {
			go= new Variable(token.getType(),Integer.parseInt(token.getSymbol()));
		}
		else if(token.getType().equals("Double")) {
			go= new Variable(token.getType(),Double.parseDouble(token.getSymbol()));
		}
		else if(token.getType().equals("String")) {
			go= new Variable(token.getType(),token.getSymbol());
		}
		else if(token.getType().equals("Boolean")) {
			boolean value;
			if(token.getSymbol()=="true")
				value=true;
			else
				value=false;
			go= new Variable(token.getType(),value);
		}
		else{//it s an identifier
			go= new Identifier(token.getType(),token.getSymbol(),token.getSymbol());
		}
		return go;
	}
	
	
	public boolean isBinaryOperation(int j) {
		if(j==0)
			return false;
		if(this.listOfTokens.get(j).getSymbol().equals("-"))
		{
			if(this.listOfTokens.get(j-1).getType().equals("RightParen") || this.listOfTokens.get(j-1).getType().equals("Integer") || this.listOfTokens.get(j-1).getType().equals("Double") || this.listOfTokens.get(j-1).getType().equals("Identifier"))
				return true;
			else
			{
				return false;
			}
		}
		for(int i=0;i<binaryOperators.size();i++) {
			if(this.listOfTokens.get(j).getSymbol().equals(binaryOperators.get(i).get(0)))
				return true;
		}
		return false;
	}
	
	
	private void initialize() {
		addedPriority=0;
		for(int i=0;i<lineBinaryOperators.size();i++) {
			lineBinaryOperators.remove(i);
		}
		if(lineBinaryOperators.size()>1)
			lineBinaryOperators.remove(0);
	}
	
	
	private boolean isRightNode(List<List<Object>> priorityListOfOperators,int j) {
		int OperatorItemj=(Integer)priorityListOfOperators.get(j).get(1);
		if(listOfTokens.get(OperatorItemj+1).getSymbol().equals("(")) {
			Token token=listOfTokens.get(OperatorItemj+1);
			int i=OperatorItemj+1;
			while(i<listOfTokens.size() && !token.getSymbol().equals(")")) {
				if(isBinaryOperation(i)) 
					return true;
				token=listOfTokens.get(i);
				i++;
			}
			return false;
		}
			
		for(int i=0;i<j;i++) {
			if((Integer)priorityListOfOperators.get(i).get(1)==OperatorItemj+2)
				return true;
		}
		return false;
	}
	
	private boolean isLeftNode(List<List<Object>> priorityListOfOperators,int j) {
		
		int OperatorItemj=(Integer)priorityListOfOperators.get(j).get(1);
		if(listOfTokens.get(OperatorItemj-1).getSymbol().equals(")")) {
			Token token=listOfTokens.get(OperatorItemj-1);
			int i=OperatorItemj-1;
			while(i>=0 && !token.getSymbol().equals("(")) {
				if(isBinaryOperation(i)) 
					return true;
				token=listOfTokens.get(i);
				i--;
			}
			return false;
		}
			
		for(int i=0;i<j;i++) {
			if((Integer)priorityListOfOperators.get(i).get(1)==OperatorItemj-2) {
				return true;
			}
				
		}
		return false;
	}
	
	private Node getLeftNode(int OperatorItemj) {//return the node with the minimum index bigger than OperatorItemj
		if(ListOfNodes.size()==0) return null;
		int maxIndex=0;
		int j=0;
		for(int i=0;i<ListOfNodes.size();i++) {
			if((Integer)ListOfNodes.get(i).get(0)-OperatorItemj>maxIndex)
			{
				maxIndex=(Integer)ListOfNodes.get(i).get(0)-OperatorItemj;
				j=i;
			}
		}
		Node node=(Node)ListOfNodes.get(j).get(1);
		ListOfNodes.remove(j);
		return node;
	}
	
	private Node getRightNode(int OperatorItemj) {//return the node with the maximum index smaller than OperatorItemj
		if(ListOfNodes.size()==0) return null;
		int minIndex=1000000;
		int j=0;
		for(int i=0;i<ListOfNodes.size();i++) {
			if((Integer)ListOfNodes.get(i).get(0)-OperatorItemj<minIndex) {
				minIndex=(Integer)ListOfNodes.get(i).get(0)-OperatorItemj;
				j=i;
			}
		}
		Node node=(Node)ListOfNodes.get(j).get(1);
		ListOfNodes.remove(j);
		return node;
	}
	
	private String getTypeOfBinaryOperator(String operator,Node leftNode,Node rightNode){
		String type;
		if(operator.equals("/"))
			type="Double";
		else if(operator.equals(">") || operator.equals(">=") || operator.equals("<") || operator.equals("<=") || operator.equals("==") || operator.equals("!=")) {
			type="Boolean";
		}
		else {
			if(leftNode.getType().equals("Double") || rightNode.getType().equals("Double"))
				type="Double";
			else {
				type=leftNode.getType();
			}
		}
		return type;
	}
	
	private int getLeftVariableIndex(int OperatorItemj) {
		int i=OperatorItemj-1;
		while(i>=0){
			if(listOfTokens.get(i).getType().equals("Integer") || listOfTokens.get(i).getType().equals("Double") || listOfTokens.get(i).getType().equals("String") || listOfTokens.get(i).getType().equals("Boolean"))
				return i;
			i--;
		}
		return 0;//ERROR!
	}
	
	private int getRightVariableIndex(int OperatorItemj) {
		int i=OperatorItemj+1;
		while(i<listOfTokens.size()){
			if(listOfTokens.get(i).getType().equals("Integer") || listOfTokens.get(i).getType().equals("Double") || listOfTokens.get(i).getType().equals("String") || listOfTokens.get(i).getType().equals("Boolean"))
				return i;
			i++;
		}
		return 0;//ERROR!
	}
	
	private void parseExpression(int i,ErrorHandler errorHandler,String endType) throws Exception {
		Token token=listOfTokens.get(i);
		if(!token.getType().equals(endType)) {
			if(token.getType().equals("LeftParen"))
			{
				errorHandler.isRightParenthesExist(i);
				addedPriority+=30;
			}
			else if(token.getType().equals("RightParen")) {
				errorHandler.isLeftParenthesExist(i);
				addedPriority-=30;
			}
			if(isBinaryOperation(i)) {
				int priority=getPriorityOfBinaryOp(token.getSymbol());
				priority+=addedPriority;
				List<Object> bo=new ArrayList<> (Arrays.asList(token.getSymbol(),i,priority));
				lineBinaryOperators.add(bo);
			}
		}
		else {//you have reached the semicolon for example
			List<List<Object>> priorityListOfOperators=sortListByPriority();
			System.out.println(priorityListOfOperators);
			errorHandler.isSortedListNull(priorityListOfOperators);
			Node node;
			for(int j=0;j<priorityListOfOperators.size();j++) {
				int OperatorItemj=(Integer)priorityListOfOperators.get(j).get(1);
				Node leftNode;
				Node rightNode;
				if(!isLeftNode(priorityListOfOperators,j) && !isRightNode(priorityListOfOperators,j)) {
					leftNode=createGeneralObjectNode(getLeftVariableIndex(OperatorItemj));
					rightNode=createGeneralObjectNode(getRightVariableIndex(OperatorItemj));
				}
				else {		
					//getLeftVariableIndex(OperatorItemj)
					if(!isLeftNode(priorityListOfOperators,j) && isRightNode(priorityListOfOperators,j) ){
						leftNode=createGeneralObjectNode(getLeftVariableIndex(OperatorItemj));
						rightNode=getRightNode(OperatorItemj);
						System.out.println("left not node and right node");
					}
					else if(isLeftNode(priorityListOfOperators,j) && !isRightNode(priorityListOfOperators,j)){
						leftNode=getLeftNode(OperatorItemj);
						rightNode=createGeneralObjectNode(getRightVariableIndex(OperatorItemj));
						System.out.println("left node and right not node");
					}
					else {
						rightNode=getLeftNode(OperatorItemj);
						leftNode=getRightNode(OperatorItemj);
						System.out.println("left node and right node");
					}
				}
				//System.out.println("left node= "+leftNode.diplayTree());
				//System.out.println("right node= "+rightNode.diplayTree());
				String operator=listOfTokens.get(OperatorItemj).getSymbol();
				String type=getTypeOfBinaryOperator(operator,leftNode,rightNode);
				node= new BinaryOperator(type,operator,leftNode,rightNode);
				//System.out.println("eval= "+node.eval() +" "+OperatorItemj);
				ListOfNodes.add(Arrays.asList(OperatorItemj,node));
				//System.out.println(ListOfNodes);
				errorHandler.BinaryOperatorErrorCheck((BinaryOperator)node);
			}
			
			Node parsingTree=(Node)ListOfNodes.get(0).get(1);
			System.out.println(parsingTree.diplayTree());
			System.out.println(parsingTree.eval());
			initialize();
		}
	}
	
	public void parse() throws Exception {
		ErrorHandler errorHandler=new ErrorHandler(listOfTokens);
		for(int i=0;i<this.listOfTokens.size();i++) {
			//Token token=listOfTokens.get(i);
			//token.showToken();
			//if no key word in the line , only expressions
			parseExpression(i,errorHandler,"Semicolon");
		}
		//System.out.println(listOfParsingTrees);
	}
}
