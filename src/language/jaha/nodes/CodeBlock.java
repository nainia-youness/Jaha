package language.jaha.nodes;

import java.util.ArrayList;
import java.util.List;

public class CodeBlock implements Node{

	
	private List<Node> expressions=new ArrayList<> ();
	
	public CodeBlock() {}

	@Override
	public Object eval() {
		if(expressions.size()==0)
			return null;
		return true;
	}

	@Override
	public String diplayTree() {
		String tree="";
		for(int i=0;i<expressions.size();i++) {
			tree+="(Exp: "+expressions.get(i).diplayTree()+" )";
		}
		return tree;
	}
	
	public List<Node> getExpressions() {
		return expressions;
	}

	public void setExpressions(List<Node> expressions) {
		this.expressions = expressions;
	}
	
	public void addExpression(Node expression) {
		expressions.add(expression);
	}
}
