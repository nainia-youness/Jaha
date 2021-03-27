package language.jaha.nodes;

public class WhileNode implements Node{
	
	Node condition;
	Node childNode;
	
	@Override
	public Object eval() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String diplayTree() {
		return "(While ( "+condition.diplayTree()+" ){ \n"+childNode.diplayTree()+"\n})";
	}
	
	public Node getCondition() {
		return condition;
	}

	public void setCondition(Node condition) {
		this.condition = condition;
	}

	public Node getChildNode() {
		return childNode;
	}

	public void setChildNode(Node childNode) {
		this.childNode = childNode;
	}

}
