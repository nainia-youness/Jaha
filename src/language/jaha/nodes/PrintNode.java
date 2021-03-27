package language.jaha.nodes;

public class PrintNode implements Node{

	Node childNode;
	
	@Override
	public Object eval() {
		return childNode.eval();
	}

	@Override
	public String diplayTree() {
		return "print( "+childNode.diplayTree()+" )";
	}
	
	public Node getChildNode() {
		return childNode;
	}

	public void setChildNode(Node childNode) {
		this.childNode = childNode;
	}

}
