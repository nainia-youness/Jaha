package language.jaha.nodes;

public class IfNode implements Node{

	private  ExpressionNode condition;
	private CodeBlock ifCodeblock;
	private CodeBlock elseCodeblock;
	
	
	public IfNode() {}

	@Override
	public Object eval() {
		if((boolean)condition.eval())
			return ifCodeblock.eval();
		else
			return elseCodeblock.eval();
	}

	@Override
	public String diplayTree() {
		String displayTree="\nif( "+condition.diplayTree()+" ) do \n{ "+ifCodeblock.diplayTree()+" }";
		if(elseCodeblock!=null){
			displayTree+="\nelse do\n{ "+elseCodeblock.diplayTree()+" }";
		}
		return  displayTree;
	}
	
	public CodeBlock getIfCodeblock() {
		return ifCodeblock;
	}

	public void setIfCodeblock(CodeBlock ifCodeblock) {
		this.ifCodeblock = ifCodeblock;
	}


	public CodeBlock getElseCodeblock() {
		return elseCodeblock;
	}

	public void setElseCodeblock(CodeBlock elseCodeblock) {
		this.elseCodeblock = elseCodeblock;
	}
	
	public ExpressionNode getCondition() {
		return condition;
	}

	public void setCondition(ExpressionNode condition) {
		this.condition = condition;
	}
}
