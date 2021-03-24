package language.jaha.nodes;

public class UnaryOperator implements Node{

	private Node childNode;
	private String type;
	private String operator;
	private Object value;
	
	public UnaryOperator(String type, String operator,Node childNode) {
		this.childNode = childNode;
		this.type = type;
		this.operator = operator;
	}

	public String diplayTree() {
		return "(child Node: "+operator+childNode.diplayTree()+" )"; 
	}
	
	public Object eval() {
		switch(operator) {
			case "!":
				if(this.childNode.getType().equals("Boolean"))
					return !((Boolean)this.childNode.eval());
			case "+":
				if(this.childNode.getType().equals("Integer"))
					return +((Integer)this.childNode.eval());
				else if(this.childNode.getType().equals("Double"))
					return +((Double)this.childNode.eval());
			case "-":
				if(this.childNode.getType().equals("Integer"))
					return -((Integer)this.childNode.eval());
				else if(this.childNode.getType().equals("Double"))
					return -((Double)this.childNode.eval());
			case "++":
				if(this.childNode.getType().equals("Integer")) {
					childNode.setValue((Integer)childNode.getValue()+1);
					return (Integer)this.childNode.eval();
				}	
				else if(this.childNode.getType().equals("Double")) {
					childNode.setValue((Double)childNode.getValue()+1);
					return (Double)this.childNode.eval();
				}
			default:
				return null;
		}
	}
	
	public Object getChildNode() {
		return childNode;
	}



	public void setChildNode(Node childNode) {
		this.childNode = childNode;
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
	
	public Object getValue() {
		return this.eval();
	}

	public void setValue(Object value) {
		this.value = value;
	}


}