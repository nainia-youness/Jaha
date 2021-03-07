package language.jaha.nodes;

public class Variable extends GeneralObject{
	
	private String type;
	private Node value;
	
	public Variable(String type, Node value) {
		this.type = type;
		this.value = value;
	}
	
	public Object eval() {
		return this.value;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Node value) {
		this.value = value;
	}

}
