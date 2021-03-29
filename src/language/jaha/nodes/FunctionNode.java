package language.jaha.nodes;

import java.util.ArrayList;
import java.util.List;

public class FunctionNode implements Node{

	private List<Node> parameters;
	private Node childNode;
	private String functionName;
	
	public FunctionNode() {
		parameters=new ArrayList<> ();
	}
	
	@Override
	public Object eval() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String diplayTree() {
		String paramsStr="";
		if(parameters.size()!=0) {
			for(int i=0;i<parameters.size();i++) {
				paramsStr+=parameters.get(i).diplayTree();
			}
		}
		return "function "+functionName+"( "+paramsStr+" ) {\n"+childNode.diplayTree()+" \n}";
	}

	public Node getChildNode() {
		return childNode;
	}

	public void setChildNode(Node childNode) {
		this.childNode = childNode;
	}
	
	public String getFunctionName() {
		return functionName;
	}

	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}
	
	public List<Node> getParameters() {
		return parameters;
	}

	public void setParameters(List<Node> parameters) {
		this.parameters = parameters;
	}

	public void addParameters(Node parameter) {
		parameters.add(parameter);
	}

}
