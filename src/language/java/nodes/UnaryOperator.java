package language.java.nodes;



public class UnaryOperator {
	private Object value;
	private String type;
	private String operator;
	
	public UnaryOperator(Object value, String type, String operator) {
		this.value = value;
		this.type = type;
		this.operator = operator;
	}

	
	
	/*Object evaluate(Object value) {
		switch(operator) {
			case "!":
				if(value.getClass().getName()=="java.lang.Boolean")
					return !(boolean)value;
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
		}
	}
	*/
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
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
