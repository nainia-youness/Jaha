package language.jaha.nodes;

public class ReturnNode implements Node {

	Node returnedNode;

	@Override
	public Object eval() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String diplayTree() {
		return "( return "+returnedNode.diplayTree()+" )";
	}
	
	public Node getReturnedNode() {
		return returnedNode;
	}

	public void setReturnedNode(Node returnedNode) {
		this.returnedNode = returnedNode;
	}
	
}
