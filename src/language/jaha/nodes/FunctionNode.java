package language.jaha.nodes;

import java.util.List;

public class FunctionNode implements Node{

	private List<Node> parameters;
	private Node childNode;
	private String FunctionName;
	
	
	@Override
	public Object eval() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String diplayTree() {
		return "function "+FunctionName+" ";
	}

	public Node getChildNode() {
		return childNode;
	}

	public void setChildNode(Node childNode) {
		this.childNode = childNode;
	}
	
	public String getFunctionName() {
		return FunctionName;
	}

	public void setFunctionName(String functionName) {
		FunctionName = functionName;
	}
	
	public List<Node> getParameters() {
		return parameters;
	}

	public void setParameters(List<Node> parameters) {
		this.parameters = parameters;
	}


}
