package language.jaha.nodes;

import java.util.ArrayList;
import java.util.List;

public class FunctionCallNode implements Node{

	private String functionName;
	private List<Node> arguments;
	
	
	public FunctionCallNode(){
		arguments=new ArrayList<> ();
	}
	
	@Override
	public Object eval() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String diplayTree() {
		String paramsStr="";
		if(arguments.size()!=0) {
			for(int i=0;i<arguments.size();i++) {
				paramsStr+=arguments.get(i).diplayTree();
			}
		}
		return functionName+"( "+paramsStr+" )";
	}

	public String getFunctionName() {
		return functionName;
	}

	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}

	public void addArguments(Node arg) {
		arguments.add(arg);
	}
	
	public List<Node> getArguments() {
		return arguments;
	}

	public void setArgumenents(List<Node> arguments) {
		this.arguments = arguments;
	}

}
