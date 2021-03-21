package language.jaha.nodes;

public class Identifier extends GeneralObject{
	
	private String type;
	private Object value;
	private String symbol;

	public Identifier(String type, Object value,String symbol) {
		this.type = type;
		this.value = value;
		this.symbol=symbol;
	}
	
	public String diplayTree() {
		return "(identifier "+symbol+" /type: "+type+" value= "+value+")";//+this.value;
	}
	
	public Object eval() {
		return this.value;
	}
	
	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
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

	public void setValue(Object value) {
		this.value = value;
	}

	
}
