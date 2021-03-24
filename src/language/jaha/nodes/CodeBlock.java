package language.jaha.nodes;

import java.util.ArrayList;
import java.util.List;

public class CodeBlock implements Node{

	
	private List<Node> expressions=new ArrayList<> ();
	
	@Override
	public Object eval() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String diplayTree() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public List<Node> getExpressions() {
		return expressions;
	}

	public void setExpressions(List<Node> expressions) {
		this.expressions = expressions;
	}
}
