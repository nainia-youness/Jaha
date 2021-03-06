package language.java.nodes;

public class BinaryOperator {
	private String type;
	private String operator;
	private Object rightNode;
	private Object leftNode;
	
	public BinaryOperator(String type, String operator, Object rightNode, Object leftNode) {
		this.type = type;
		this.operator = operator;
		this.rightNode = rightNode;
		this.leftNode = leftNode;
	}
	

}
