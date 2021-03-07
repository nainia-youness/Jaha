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
		boolean isTokenGeneralObject= node.getType().equals("Integer") || node.getType().equals("Double") || node.getType().equals("Identifier");
		if(isRightNode) {
			return node.getType().equals("LeftParen") || isTokenGeneralObject;
		}
		else {
			return node.getType().equals("RightParen") || isTokenGeneralObject; 
		}
		//this is just for leaf nodes MUST BE UPDATED!
	}
	
	public boolean isBinaryOperationTypeAllowed(Node leftNode,Node rightNode) {
		if(leftNode.getType().equals("Integer") && rightNode.getType().equals("String"))
			return false;
		else if(leftNode.getType().equals("String") && rightNode.getType().equals("Integer"))
			return false;
		return true;
	}
	
	public boolean isIdentifierInitialized(Node node) {
		if(node.getType().equals("Identifier")){
			return true;
		}
		return true;
	}
	
	public void BinaryOperatorErrorCheck(BinaryOperator bo) {
		Node leftNode=(Node)  bo.getLeftNode();
		Node rightNode=(Node) bo.getRightNode();
		if(bo.getOperator().equals("+") || bo.getOperator().equals("-") || bo.getOperator().equals("*"))
			{
				if(!isTokenGeneralObject(rightNode,true)) {
					System.out.println("ERROR: Next token not variable or parameter");
				}
				if(!isTokenGeneralObject(leftNode,false)) {
					System.out.println("ERROR: Previous token not variable or parameter");
				}
				else if(!isBinaryOperationTypeAllowed(leftNode,rightNode)) {
					System.out.println("ERROR: Binary operation parameters not allowed");
				}
				else if(!isIdentifierInitialized(leftNode)) {//should be defined
					System.out.println("ERROR: Variable not initialized");
				}
			}
		if(bo.getOperator().equals("=")) {
			if(isTokenGeneralObject(rightNode,false)) {
				System.out.println("ERROR: Next token not variable or parameter");
			}
			else if(!leftNode.getType().equals("Identifier")) {
				System.out.println("ERROR: You cannot assign to a different type than an Identifier");
			}
		}
	}
	
	public void isRightParenthesExist(int i){
		int nbrLeftParenthes=0;
		int nbrRightParenthes=0;
		for(int j=i;j<listOfTokens.size();j++) {
			if(listOfTokens.get(j).getType().equals("LeftParen")) {
				nbrLeftParenthes++;
			}
			if(listOfTokens.get(j).getType().equals("RightParen")) {
				nbrRightParenthes++;
			}
		}
		if(nbrLeftParenthes!=nbrRightParenthes)
			System.out.println("ERROR: Right Parenthesis is missing");
	}
	
	public void isLeftParenthesExist(int i){
		int nbrLeftParenthes=0;
		int nbrRightParenthes=0;
		for(int j=i;j<listOfTokens.size();j++) {
			if(listOfTokens.get(j).getType().equals("LeftParen")) {
				nbrLeftParenthes++;
			}
			if(listOfTokens.get(j).getType().equals("RightParen")) {
				nbrRightParenthes++;
			}
		}
		if(nbrLeftParenthes!=nbrRightParenthes)
			System.out.println("ERROR: Right Parenthesis is missing");
	}
	
}
