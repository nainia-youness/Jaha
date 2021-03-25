package language.jaha.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import language.jaha.lexer.Token;
import language.jaha.nodes.BinaryOperator;
import language.jaha.nodes.CodeBlock;
import language.jaha.nodes.ExpressionNode;
import language.jaha.nodes.GeneralObject;
import language.jaha.nodes.Identifier;
import language.jaha.nodes.IfNode;
import language.jaha.nodes.Node;
import language.jaha.nodes.UnaryOperator;
import language.jaha.nodes.Variable;

public class Parser {

	
	//Node parsingTree;
	List<Node> listOfParsingTrees=new ArrayList<> ();
	List<Token> listOfTokens=Arrays.asList();
	List<List<Object>> lineOfOperators=new ArrayList<> (Arrays.asList());
	int addedPriority=0;
	List<List<Object>> ListOfNodes=new ArrayList<> (Arrays.asList());
	private String endToken="Semicolon";
	List<List<Object>> ifStartIndexes=new ArrayList<> (Arrays.asList());
	List<Integer> listOfRightBraceIndexes=new ArrayList<>();
	
	public Parser(List<Token> listOfTokens) {
		this.listOfTokens=listOfTokens;
	}
	
	final List<List<String>> unaryOperators=Arrays.asList(
	Arrays.asList("!","Unary_Op_not","13"),
	Arrays.asList("++","Op_increment","14"),
	Arrays.asList("--","Op_negativeincrement","14"),
	Arrays.asList("-","Unary_Op_subtract","13"),
	Arrays.asList("+","Unary_Op_add","13")
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
			Arrays.asList("||","Op_or","3"),
			Arrays.asList("+=","Op_add_assign","1"),
			Arrays.asList("-=","Op_subtract_assign","1"),
			Arrays.asList("*=","Op_multiply_assign","1"),
			Arrays.asList("%=","Op_Op_mod_assign","1")
	);
	
		
	
	
	private int getPriorityOfBinaryOp(String operator) {
		for(int i=0;i<binaryOperators.size();i++) {
			if(operator.equals(binaryOperators.get(i).get(0))) {
				return Integer.parseInt(binaryOperators.get(i).get(2));
			}
		}
		return 0;//error
	}
	
	private int getPriorityOfUnaryOp(String operator) {
		for(int i=0;i<unaryOperators.size();i++) {
			if(operator.equals(unaryOperators.get(i).get(0))) {
				return Integer.parseInt(unaryOperators.get(i).get(2));
			}
		}
		return 0;//error
	}
	
	private List<List<Object>> sortListByPriority(){
		int j=0;
		while(j<lineOfOperators.size()) {
			int max=(Integer)lineOfOperators.get(j).get(2);
			int maxi=j;
			for(int i=j;i<lineOfOperators.size();i++) {
				if(max<(Integer)lineOfOperators.get(i).get(2)) {
					max=(Integer)lineOfOperators.get(i).get(2);
					maxi=i;
				}
			}
			Collections.swap(lineOfOperators, j, maxi);
			j++;
		}
		return lineOfOperators;
	}
	
	
	
	
	private ExpressionNode createGeneralObjectNode(int i) {
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
			go= new Identifier(token.getType(),null,token.getSymbol());
		}
		return (ExpressionNode)go;
	}
	
	
	private boolean isBinaryOperation(int j) {
		if(j==0)
			return false;
		if(this.listOfTokens.get(j).getSymbol().equals("-") || this.listOfTokens.get(j).getSymbol().equals("+"))
		{
			if(this.listOfTokens.get(j-1).getType().equals("RightParen") || this.listOfTokens.get(j-1).getType().equals("Integer") || this.listOfTokens.get(j-1).getType().equals("Double") || this.listOfTokens.get(j-1).getType().equals("Identifier") || this.listOfTokens.get(j-1).getType().equals("String"))
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
	
	private boolean isUnaryOperation(int j) {
		for(int i=0;i<unaryOperators.size();i++) {
			if(this.listOfTokens.get(j).getSymbol().equals(unaryOperators.get(i).get(0)))
				return true;
		}
		return false;
	}
	
	
	
	private void initialize() {
		ListOfNodes.remove(0);
		addedPriority=0;
		lineOfOperators=new ArrayList<> (Arrays.asList());
	}
	
	
	private boolean isRightNode(List<List<Object>> priorityListOfOperators,int j) {
		int OperatorItemj=(Integer)priorityListOfOperators.get(j).get(1);
		if(listOfTokens.get(OperatorItemj+1).getSymbol().equals("(")) {
			Token token=listOfTokens.get(OperatorItemj+1);
			int i=OperatorItemj+1;
			while(i<listOfTokens.size() && !token.getSymbol().equals(")")) {
				if(isBinaryOperation(i) || isUnaryOperation(i)) 
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
	
	
	private ExpressionNode getLeftNode(int OperatorItemj) {//return the node with the minimum index bigger than OperatorItemj
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
		ExpressionNode node=(ExpressionNode)ListOfNodes.get(j).get(1);
		ListOfNodes.remove(j);
		return node;
	}
	
	private ExpressionNode getRightNode(int OperatorItemj) {//return the node with the maximum index smaller than OperatorItemj
		if(ListOfNodes.size()==0) return null;
		int minIndex=1000000;
		int j=0;
		for(int i=0;i<ListOfNodes.size();i++) {
			if((Integer)ListOfNodes.get(i).get(0)-OperatorItemj<minIndex) {
				minIndex=(Integer)ListOfNodes.get(i).get(0)-OperatorItemj;
				j=i;
			}
		}
		ExpressionNode node=(ExpressionNode)ListOfNodes.get(j).get(1);
		ListOfNodes.remove(j);
		return node;
	}
	
	private String getTypeOfBinaryOperator(String operator,ExpressionNode leftNode,ExpressionNode rightNode){
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
				type=rightNode.getType();
			}
		}
		return type;
	}
	
	private String getTypeOfUnaryOperator(String operator,ExpressionNode childNode){
		return childNode.getType();
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
	
	private void addTolineOfOperators(int i,ErrorHandler errorHandler)throws Exception {
		Token token=listOfTokens.get(i);
		if(token.getType().equals("LeftParen"))
		{
			errorHandler.isRightTokenExist(i,"RightParen","LeftParen",true);
			addedPriority+=30;
		}
		else if(token.getType().equals("RightParen")) {
			errorHandler.isLeftTokenExist(i,"RightParen","LeftParen",true);
			addedPriority-=30;
		}
		int priority;
		if(isBinaryOperation(i)) {
			priority=getPriorityOfBinaryOp(token.getSymbol());
			priority+=addedPriority;
			List<Object> bo=new ArrayList<> (Arrays.asList(token.getSymbol(),i,priority));
			lineOfOperators.add(bo);
		}
		else if(isUnaryOperation(i)) {
			priority=getPriorityOfUnaryOp(token.getSymbol());
			priority+=addedPriority;
			List<Object> uo=new ArrayList<> (Arrays.asList(token.getSymbol(),i,priority));
			lineOfOperators.add(uo);
		}
	}
	
	
	private ExpressionNode addBinaryOperatorNode(List<List<Object>> priorityListOfOperators,int OperatorItemj,int j) {
		System.out.println("-------binary operator");
		ExpressionNode leftNode;
		ExpressionNode rightNode;
		if(!isLeftNode(priorityListOfOperators,j) && !isRightNode(priorityListOfOperators,j)) {
			leftNode=createGeneralObjectNode(getLeftVariableIndex(OperatorItemj));
			rightNode=createGeneralObjectNode(getRightVariableIndex(OperatorItemj));
		}
		else {		
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
		System.out.println("left node= "+leftNode.diplayTree());
		System.out.println("right node= "+rightNode.diplayTree());
		//System.out.println("eval= "+node.eval() +" "+OperatorItemj);
		String operator=listOfTokens.get(OperatorItemj).getSymbol();
		String type=getTypeOfBinaryOperator(operator,leftNode,rightNode);
		
		if(operator.equals("=")) {//doing Type Inference
			leftNode.setType(type);
		}
		ExpressionNode node= new BinaryOperator(type,operator,leftNode,rightNode);
		ListOfNodes.add(Arrays.asList(OperatorItemj,node));
		return node;
	}
	
	
	private ExpressionNode addUnaryOperatorNode(List<List<Object>> priorityListOfOperators,int OperatorItemj,int j) {
		ExpressionNode childNode;
		System.out.println("-------unary operator");					
		if(isRightNode(priorityListOfOperators,j)) {
			System.out.println("child is node");
			childNode=getRightNode(OperatorItemj);
		}
		else {
			System.out.println("child is not node");
			childNode=createGeneralObjectNode(getRightVariableIndex(OperatorItemj));
		}
		//System.out.println("Childnode= "+childNode.diplayTree());
		String operator=listOfTokens.get(OperatorItemj).getSymbol();
		String type=getTypeOfUnaryOperator(operator,childNode);
		ExpressionNode node= new UnaryOperator(type,operator,childNode);
		//System.out.println("eval= "+node.eval() +" "+OperatorItemj);
		ListOfNodes.add(Arrays.asList(OperatorItemj,node));
		return node;
	}
	
	
	private void parseExpression(int i,ErrorHandler errorHandler) throws Exception {
		System.out.println(endToken);
		Token token=listOfTokens.get(i);
		errorHandler.isExistingOperator(token);
		if(!token.getType().equals(endToken)) {
			 addTolineOfOperators(i,errorHandler);
		}
		else {//you have reached the semicolon for example
			List<List<Object>> priorityListOfOperators=sortListByPriority();
			System.out.println(priorityListOfOperators);
			errorHandler.isSortedListNull(priorityListOfOperators);
			for(int j=0;j<priorityListOfOperators.size();j++) {
				ExpressionNode node;
				int OperatorItemj=(Integer)priorityListOfOperators.get(j).get(1);
				if(isBinaryOperation(OperatorItemj)) {
					node=addBinaryOperatorNode(priorityListOfOperators,OperatorItemj,j);
				}
				else if(isUnaryOperation(OperatorItemj)) {//not including i++
					node=addUnaryOperatorNode(priorityListOfOperators,OperatorItemj,j);
				}
				//System.out.println(ListOfNodes);
			}
			Node parsingTree=(Node)ListOfNodes.get(0).get(1);
			System.out.println(parsingTree.eval());
			System.out.println(parsingTree.diplayTree());
			listOfParsingTrees.add(parsingTree);
			initialize();
		}
	}
	
	private int findStartBlockIndex(int i) {
		boolean isLastNodeAndTheNodeBeforeBlockNodes=listOfParsingTrees.get(listOfParsingTrees.size()-1).getClass().getName().equals("language.jaha.nodes.CodeBlock") && listOfParsingTrees.get(listOfParsingTrees.size()-2).getClass().getName().equals("language.jaha.nodes.CodeBlock");
		if(isLastNodeAndTheNodeBeforeBlockNodes)
			return listOfParsingTrees.size()-2;
		for(int j=listOfParsingTrees.size()-1;j>=0;j--) {
			if(listOfParsingTrees.get(j).getClass().getName().equals("language.jaha.nodes.CodeBlock")) {
				if(listOfParsingTrees.get(j).eval()==null) {
					return j;
				}
			}
		}
		return -1;//EROOR
	}
	
	
	private void deleteNodesOfBlock(int blockStartIndex) {
		int nbtOfNodesToDelete=listOfParsingTrees.size()-blockStartIndex-1;
		for(int i=0;i<nbtOfNodesToDelete;i++) {
			listOfParsingTrees.remove(listOfParsingTrees.size()-1);
		}
		System.out.println(listOfParsingTrees);
	}
	
	private int getIndexOfRightBrace(int i) {
		int nbrLeftBraces=0;
		int nbrRightBraces=0;
		int rightBraceIndex=0;
		for(int j=i;j<listOfTokens.size();j++) {
			rightBraceIndex=j;
			if(listOfTokens.get(j).getType().equals("LeftBrace")) {//LeftParen
				nbrLeftBraces++;
			}
			if(listOfTokens.get(j).getType().equals("RightBrace")) {
					nbrRightBraces++;
			}
			if(nbrLeftBraces!=0 && (nbrLeftBraces==nbrRightBraces))
				break;
		}
		System.out.println(rightBraceIndex);
		return rightBraceIndex; 
	}
	

	
	private void parseCodeBlock(int i,ErrorHandler errorHandler) throws Exception {
		Token token=listOfTokens.get(i);
		System.out.println(listOfRightBraceIndexes);
		if(token.getType().equals("LeftBrace")) {
			endToken="Semicolon";
			errorHandler.isRightTokenExist(i,"RightBrace","LeftBrace",false);
			CodeBlock block=new CodeBlock();
			listOfRightBraceIndexes.add(getIndexOfRightBrace(i));
			listOfParsingTrees.add(block);
		}
		if(listOfRightBraceIndexes.size()!=0){
			if(i==listOfRightBraceIndexes.get(listOfRightBraceIndexes.size()-1)) {
				errorHandler.isLeftTokenExist(i,"RightBrace","LeftBrace",false);
				int blockStartIndex=findStartBlockIndex(i);
				System.out.println("blockStartIndex: "+blockStartIndex);
				CodeBlock block=(CodeBlock)listOfParsingTrees.get(blockStartIndex);
				for(int j=blockStartIndex+1;j<listOfParsingTrees.size();j++) {
					block.addExpression(listOfParsingTrees.get(j));
				}
				deleteNodesOfBlock(blockStartIndex);
				listOfRightBraceIndexes.remove(listOfRightBraceIndexes.size()-1);
			}
		}
	}
	
	
	private boolean isElseExist(int i) {
		int nbrLeftBraces=0;
		int nbrRightBraces=0;
		int elseIndex=0;
		for(int j=i;j<listOfTokens.size();j++) {
			elseIndex=j;
			if(listOfTokens.get(j).getType().equals("LeftBrace")) {//LeftParen
				nbrLeftBraces++;
			}
			if(listOfTokens.get(j).getType().equals("RightBrace")) {
					nbrRightBraces++;
			}
			if(nbrLeftBraces!=0 && (nbrLeftBraces==nbrRightBraces))
				break;
		}
		if(elseIndex+1>=listOfTokens.size())
			return false;
		if(listOfTokens.get(elseIndex+1).getType().equals("Keyword_else"))
			return true;
		return false;
	}
	
	
	private void parseIfNode(int i,ErrorHandler errorHandler) throws Exception {
		Token token=listOfTokens.get(i);
		if(token.getType().equals("Keyword_if")) {
			IfNode ifNode= new IfNode();
			listOfParsingTrees.add(ifNode);
			endToken="LeftBrace";
			List<Object> ifStartIndexList=Arrays.asList(listOfParsingTrees.size()-1,isElseExist(i));
			ifStartIndexes.add(ifStartIndexList);
			
		}
		if(ifStartIndexes.size()!=0) {
			int ifStartIndex=(Integer)(ifStartIndexes.get(ifStartIndexes.size()-1).get(0));
			if(listOfParsingTrees.size()-1-ifStartIndex==2)//if after the ifNode , there is only the expression and the blockNode
			{
				if(listOfParsingTrees.get(ifStartIndex+2).getClass().getName().equals("language.jaha.nodes.CodeBlock")) {
					
					if(!((CodeBlock)listOfParsingTrees.get(ifStartIndex+2)).getExpressions().isEmpty()) {
						ExpressionNode exp=(ExpressionNode)listOfParsingTrees.get(ifStartIndex+1);
						CodeBlock block=(CodeBlock)listOfParsingTrees.get(ifStartIndex+2);
						((IfNode)listOfParsingTrees.get(ifStartIndex)).setCondition(exp);
						((IfNode)listOfParsingTrees.get(ifStartIndex)).setIfCodeblock(block);
						listOfParsingTrees.remove(listOfParsingTrees.size()-1);
						listOfParsingTrees.remove(listOfParsingTrees.size()-1);
						 if((Boolean)(ifStartIndexes.get(ifStartIndexes.size()-1).get(1))==false)
						 	ifStartIndexes.remove(ifStartIndexes.size()-1);
					}
				}
			}
		}
		if(ifStartIndexes.size()!=0) {
			int ifStartIndex=(Integer)(ifStartIndexes.get(ifStartIndexes.size()-1).get(0));
			System.out.println(ifStartIndexes.size()-1+" "+ifStartIndexes);
			if((Boolean)(ifStartIndexes.get(ifStartIndexes.size()-1).get(1))==true) {
				if(listOfParsingTrees.size()-1-ifStartIndex==1) {//if after the ifNode , there is only the blockNode
					if(!((CodeBlock)listOfParsingTrees.get(ifStartIndex+1)).getExpressions().isEmpty()) {
						CodeBlock elseBlock=(CodeBlock)listOfParsingTrees.get(ifStartIndex+1);
						((IfNode)listOfParsingTrees.get(ifStartIndex)).setElseCodeblock(elseBlock);
						listOfParsingTrees.remove(listOfParsingTrees.size()-1);
						ifStartIndexes.remove(ifStartIndexes.size()-1);
					}
				}
			}
		}
	}
	
	
	public void parse() throws Exception {
		ErrorHandler errorHandler=new ErrorHandler(listOfTokens);
		for(int i=0;i<this.listOfTokens.size();i++) {
			System.out.println(listOfParsingTrees);
			Token token=listOfTokens.get(i);
			//token.showToken();
			parseExpression(i,errorHandler);//the order of the parsers is important
			parseCodeBlock(i,errorHandler);
			parseIfNode(i,errorHandler);
			
		}
		System.out.println("...................................................");
		System.out.println(ifStartIndexes);
		System.out.println(listOfParsingTrees);
		System.out.println(listOfParsingTrees.get(0).diplayTree());
	}
}
