package language.jaha.nodes;

public abstract class ExpressionNode implements Node{
	
	public abstract Object eval();

	public abstract String diplayTree();


	
	public abstract Object getValue();

	public abstract void setValue(Object value);
	
	public abstract void setType(String type);
	
	public abstract  String getType();
}
