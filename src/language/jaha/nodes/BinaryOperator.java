package language.jaha.nodes;

public class BinaryOperator implements Node{
	private String type;
	private String operator;
	private Node rightNode;//GeneralObject or BinaryOperator or a Unary operator
	private Node leftNode;
	
	public BinaryOperator(String type, String operator, Node leftNode ,Node rightNode) {
		this.type = type;
		this.operator = operator;
		this.rightNode = rightNode;
		this.leftNode = leftNode;
	}
	
	public String diplayTree() {
		return "(right Node: "+this.rightNode.diplayTree()+this.operator+ " left Node: "+this.leftNode.diplayTree()+")";
	}
	
	public Object eval() {
		return null;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public Object getRightNode() {
		return rightNode;
	}

	public void setRightNode(Node rightNode) {
		this.rightNode = rightNode;
	}

	public Object getLeftNode() {
		return leftNode;
	}

	public void setLeftNode(Node leftNode) {
		this.leftNode = leftNode;
	}
}
