package language.jaha.nodes;

public class UnaryOperator implements Node{

	private Node childNode;
	private String type;
	private String operator;
	
	public UnaryOperator(Node value, String type, String operator) {
		this.childNode = childNode;
		this.type = type;
		this.operator = operator;
	}

	public String diplayTree() {
		return "Value:"; 
	}
	
	public Object eval() {
		return null;
		/*switch(operator) {
			case "!":
				return !(boolean)value.eval();
				else
					return null;
			case "-":
				if(value.getClass().getName()=="java.lang.Integer")
					return -(Integer)value;
				else if(value.getClass().getName()=="java.lang.Double"){
					return -(Double)value;
				}
				else
					return null;
			default:
				return null;
		}*/
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


}