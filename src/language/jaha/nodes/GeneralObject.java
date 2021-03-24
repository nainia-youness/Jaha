package language.jaha.nodes;

public abstract class GeneralObject extends ExpressionNode{

	private String type;
	private Object value;
	
	
	public abstract Object eval();
	
	public abstract String getType();

	public abstract void setType(String type);

	public abstract Object getValue();

	public abstract void setValue(Object value);

}
