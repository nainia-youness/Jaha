package language.jaha.nodes;

public class Identifier extends GeneralObject{
	
	private String type;
	private Object value;
	
	public Identifier(String type, Object value) {
		this.type = type;
		this.value = value;
	}
	
	public String diplayTree() {
		return "(identifier)";//+this.value;
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
