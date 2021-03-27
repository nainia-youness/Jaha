package language.jaha.nodes;

public class ForNode implements Node{
	
	Node initialization;
	Node termination;
	Node increment;
	Node childNode;
	
	@Override
	public Object eval() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String diplayTree() {
		return "( For( "+initialization.diplayTree()+" ; "+termination.diplayTree()+" ; "+increment.diplayTree()+" ) {\n "+childNode.diplayTree()+"} )";
	}
	
	public Node getTermination() {
		return termination;
	}

	public void setTermination(Node termination) {
		this.termination = termination;
	}

	public Node getInitialization() {
		return initialization;
	}

	public void setInitialization(Node initialization) {
		this.initialization = initialization;
	}

	public Node getIncrement() {
		return increment;
	}

	public void setIncrement(Node increment) {
		this.increment = increment;
	}

}
