package language.jaha.nodes;

public abstract class GeneralObject implements Node{

	private String type;
	private Object value;
	
	
	public abstract Object eval();
	
	public abstract String getType();

	public abstract void setType(String type);

	public abstract Object getValue();

	public abstract void setValue(Node value);

}
