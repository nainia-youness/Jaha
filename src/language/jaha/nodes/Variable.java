package language.jaha.nodes;

public class Variable extends GeneralObject{
	
	private String type;
	private Object value;
	
	public Variable(String type, Object value) {
		this.type = type;
		this.value = value;
	}
	
	public String diplayTree() {
		return "(Value: "+this.value+ "type: "+this.type+")";
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
