package language.jaha.parser;

import java.util.Arrays;
import java.util.List;

import language.jaha.lexer.Token;
import language.jaha.nodes.BinaryOperator;
import language.jaha.nodes.Node;

public class ErrorHandler {
	
	
	public ErrorHandler() {}
	
	boolean isNextTokenVarOrPar(Node rightNode) {
		return rightNode.getType().equals("LeftParen") || rightNode.getType().equals("Integer") || rightNode.getType().equals("Double") || rightNode.getType().equals("Identifier");
	}
	
	boolean isBinaryOperationTypeAllowed(Node leftNode,Node rightNode) {
		if(leftNode.getType().equals("Integer") && rightNode.getType().equals("String"))
			return false;
		else if(leftNode.getType().equals("String") && rightNode.getType().equals("Integer"))
			return false;
		return true;
	}
	
	public void BinaryOperatorErrorCheck(BinaryOperator bo) {
		Node leftNode=(Node)  bo.getLeftNode();
		Node rightNode=(Node) bo.getRightNode();
		if(bo.getOperator().equals("+") || bo.getOperator().equals("-") || bo.getOperator().equals("*"))
			{
				if(!isNextTokenVarOrPar(rightNode)) {
					System.out.println("ERROR: Next token not variable or parameter");
				}
				else if(!isBinaryOperationTypeAllowed(leftNode,rightNode)) {
					System.out.println("ERROR: Binary operation parameters not allowed");
				}
			}
		
	}
	
	
	
}
