package language.jaha.parser;

import java.util.Arrays;
import java.util.List;

import language.jaha.lexer.Token;
import language.jaha.nodes.BinaryOperator;
import language.jaha.nodes.Node;

public class ErrorHandler {
	
	List<Token> listOfTokens=Arrays.asList();
	public ErrorHandler(List<Token> listOfTokens) {
		this.listOfTokens=listOfTokens;
	}
	
	
	public boolean isTokenGeneralObject(Node node,boolean isRightNode) {
		boolean isTokenGeneralObject= node.getType().equals("Integer") || node.getType().equals("Double") || node.getType().equals("Identifier") || node.getType().equals("Boolean");
		if(isRightNode) {
			return node.getType().equals("LeftParen") || isTokenGeneralObject;
		}
		else {
			return node.getType().equals("RightParen") || isTokenGeneralObject; 
		}
		//this is just for leaf nodes MUST BE UPDATED!
	}
	
	public boolean isBooleanBinaryOperationTypeAllowed(Node leftNode,Node rightNode) {
		if(leftNode.getType().equals("Boolean") && rightNode.getType().equals("Boolean"))
			return true;
		else if(leftNode.getType().equals("Identifier") && rightNode.getType().equals("Boolean"))
			return true;
		else if(leftNode.getType().equals("Boolean") && rightNode.getType().equals("Identifier"))
			return true;
		return false;
	}
	
	public boolean isNumericalBinaryOperationTypeAllowed(Node leftNode,Node rightNode) {
		if(leftNode.getType().equals("Integer") && rightNode.getType().equals("Integer"))
			return true;
		else if(leftNode.getType().equals("Double") && rightNode.getType().equals("Double"))
			return true;
		else if(leftNode.getType().equals("Double") && rightNode.getType().equals("Integer"))
			return true;
		else if(leftNode.getType().equals("Integer") && rightNode.getType().equals("Double"))
			return true;
		else if(leftNode.getType().equals("String") && rightNode.getType().equals("String"))
			return true;
		else if(leftNode.getType().equals("Identifier") && rightNode.getType().equals("String"))
			return true;
		else if(leftNode.getType().equals("String") && rightNode.getType().equals("Identifier"))
			return true;
		else if(leftNode.getType().equals("Identifier") && rightNode.getType().equals("Double"))
			return true;
		else if(leftNode.getType().equals("Double") && rightNode.getType().equals("Identifier"))
			return true;
		else if(leftNode.getType().equals("Identifier") && rightNode.getType().equals("Integer"))
			return true;
		else if(leftNode.getType().equals("Integer") && rightNode.getType().equals("Identifier"))
			return true;
		//System.out.println(leftNode.getType()+" "+rightNode.getType());
		return false;
		//nodes can be binary operations
	}
	
	public void isSortedListNull(List<List<Object>> priorityListOfOperators) throws Exception {
		if(priorityListOfOperators.isEmpty())
			throw new Exception("ERROR: no binary operation found ");
	}
	
	public boolean isIdentifierInitialized(Node node) {
		if(node.getType().equals("Identifier")){
			return true;
		}
		return true;
	}
	
	public void BinaryOperatorErrorCheck(BinaryOperator bo) throws Exception {
		Node leftNode=(Node)  bo.getLeftNode();
		Node rightNode=(Node) bo.getRightNode();
		if(bo.getOperator().equals("=")) {
			if(!isTokenGeneralObject(rightNode,true)) {
				throw new Exception("ERROR: Next token not variable or parameter");
			}
			if(!isTokenGeneralObject(leftNode,false)) {
				throw new Exception("ERROR: Previous token not variable or parameter");
			}
			if(!isTokenGeneralObject(rightNode,true)) {
				throw new Exception("ERROR: Next token not variable or parameter");
			}
			else if(!leftNode.getType().equals("Identifier")) {
				throw new Exception("ERROR: You cannot assign to a different type than an Identifier");
			}
		}
		else if(bo.getOperator().equals(">") || bo.getOperator().equals("<") || bo.getOperator().equals(">=") || bo.getOperator().equals("<=")) {
			if(!isTokenGeneralObject(rightNode,true))
				throw new Exception("ERROR: Next token not variable or parameter");
			if(!isTokenGeneralObject(leftNode,false)) {
				throw new Exception("ERROR: Previous token not variable or parameter");
			}
			else if(!isNumericalBinaryOperationTypeAllowed(leftNode,rightNode)) {
				throw new Exception("ERROR: Binary operation parameters not allowed");
			}
			else if(!isIdentifierInitialized(leftNode)) {
				throw new Exception("ERROR: Variable not initialized");
			}
			else if(!isIdentifierInitialized(rightNode)) {
				throw new Exception("ERROR: Variable not initialized");
			}
		}
		else if(bo.getOperator().equals("==") || bo.getOperator().equals("!=")) {
			if(!isTokenGeneralObject(rightNode,true))
				throw new Exception("ERROR: Next token not variable or parameter");
			if(!isTokenGeneralObject(leftNode,false)) {
				throw new Exception("ERROR: Previous token not variable or parameter");
			}
			else if(!isNumericalBinaryOperationTypeAllowed(leftNode,rightNode) && !isBooleanBinaryOperationTypeAllowed(leftNode,rightNode)) {
				throw new Exception("ERROR: Binary operation parameters not allowed");
			}
			else if(!isIdentifierInitialized(leftNode)) {
				throw new Exception("ERROR: Variable not initialized");
			}
			else if(!isIdentifierInitialized(rightNode)) {
				throw new Exception("ERROR: Variable not initialized");
			}
		}
		else if(bo.getOperator().equals("&&") ||  bo.getOperator().equals("||")) {
			if(!isTokenGeneralObject(rightNode,true)) {
				throw new Exception("ERROR: Next token not variable or parameter");
			}
			if(!isTokenGeneralObject(leftNode,false)) {
				throw new Exception("ERROR: Previous token not variable or parameter");
			}
			if(!isBooleanBinaryOperationTypeAllowed(leftNode,rightNode)) {
				throw new Exception("ERROR: Previous token not variable or parameter");
			}
			else if(!isIdentifierInitialized(leftNode)) {
				throw new Exception("ERROR: Variable not initialized");
			}
			else if(!isIdentifierInitialized(rightNode)) {
				throw new Exception("ERROR: Variable not initialized");
			}
		}
		else if(bo.getOperator().equals("%")) {
			if(!isTokenGeneralObject(rightNode,true)) {
				throw new Exception("ERROR: Next token not variable or parameter");
			}
			if(!isTokenGeneralObject(leftNode,false)) {
				throw new Exception("ERROR: Previous token not variable or parameter");
			}
			if(!(leftNode.getType().equals("Integer") && leftNode.getType().equals("Integer"))) {
				throw new Exception("ERROR: Previous token not variable or parameter");
			}
			else if(!isIdentifierInitialized(rightNode)) {
				throw new Exception("ERROR: Variable not initialized");
			}
		}
		else
			{
				if(!isTokenGeneralObject(rightNode,true)) {
					throw new Exception("ERROR: Next token not variable or parameter");
				}
				if(!isTokenGeneralObject(leftNode,false)) {
					throw new Exception("ERROR: Previous token not variable or parameter");
				}
				else if(!isNumericalBinaryOperationTypeAllowed(leftNode,rightNode)) {
					//System.out.println("left: "+leftNode.getType()+" "+leftNode.eval());
					//System.out.println("right: "+rightNode.getType());
					throw new Exception("ERROR: Binary operation parameters not allowedd");
				}
				else if(!isIdentifierInitialized(leftNode)) {//should be defined
					throw new Exception("ERROR: Variable not initialized");
				}
				else if(!isIdentifierInitialized(rightNode)) {//should be defined
					throw new Exception("ERROR: Variable not initialized");
				}
			}

	}
	
	
	public void isRightParenthesExist(int i) throws Exception{
		if(listOfTokens.get(i+1).getType().equals("Semicolon"))
			throw new Exception("ERROR: Wrong useage of Parenthesis!");
		if(listOfTokens.get(i+1).getType().equals("RightParen"))
			throw new Exception("ERROR: Two Parenthesis can t be empty!");
		int nbrLeftParenthes=0;
		int nbrRightParenthes=0;
		for(int j=i;j<listOfTokens.size();j++) {
			if(listOfTokens.get(j).getType().equals("LeftParen")) {
				nbrLeftParenthes++;
			}
			if(listOfTokens.get(j).getType().equals("RightParen")) {
				if(nbrLeftParenthes>nbrRightParenthes)
					nbrRightParenthes++;
			}
		}
		if(nbrLeftParenthes!=nbrRightParenthes)
			throw new Exception("ERROR: Right Parenthesis is missing");
	}
	
	
	public void isLeftParenthesExist(int i) throws Exception{
		if(i==0)
			throw new Exception("ERROR: Wrong useage of Parenthesis!");
		if(listOfTokens.get(i-1).getType().equals("LeftParen"))
			throw new Exception("ERROR: Two Parenthesis can t be empty!");
		int nbrLeftParenthes=0;
		int nbrRightParenthes=0;
		for(int j=i;j>=0;j--) {
			if(listOfTokens.get(j).getType().equals("LeftParen")) {
				if(nbrLeftParenthes<nbrRightParenthes)
					nbrLeftParenthes++;
			}
			if(listOfTokens.get(j).getType().equals("RightParen")) {
				nbrRightParenthes++;
			}
		}
		if(nbrLeftParenthes!=nbrRightParenthes)
			throw new Exception("ERROR: Left Parenthesis is missing");
	}
	
	

}
