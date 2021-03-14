package language.jaha.nodes;

public class BinaryOperator implements Node{
	private String type;
	private String operator;
	private Node rightNode;//GeneralObject or BinaryOperator or a Unary operator
	private Node leftNode;
	
	public BinaryOperator(String type, String operator, Node leftNode ,Node rightNode) {
		this.type = type;
		this.operator = operator;
		this.rightNode = rightNode;
		this.leftNode = leftNode;
	}
	
	public String diplayTree() {
		return "(right Node: "+this.rightNode.diplayTree()+" "+this.operator+ " left Node: "+this.leftNode.diplayTree()+")";
	}
	
	public Object eval() {
		//return null;
		switch(operator) {
		case "+":
			if(this.leftNode.getType().equals("Integer") && this.rightNode.getType().equals("Integer"))
				return (Integer)this.leftNode.eval()+(Integer)this.rightNode.eval();
			else if(this.leftNode.getType().equals("Double") && this.rightNode.getType().equals("Double"))
				return (Double)this.leftNode.eval()+(Double)this.rightNode.eval();
			else if(this.leftNode.getType().equals("Double") && this.rightNode.getType().equals("Integer"))
				return (Double)this.leftNode.eval()+(Integer)this.rightNode.eval();
			else if(this.leftNode.getType().equals("Integer") && this.rightNode.getType().equals("Double"))
				return (Integer)this.leftNode.eval()+(Double)this.rightNode.eval();
			else if(this.leftNode.getType().equals("String") && this.rightNode.getType().equals("String"))
				return (String)this.leftNode.eval()+(String)this.rightNode.eval();
		case "-":
			if(this.leftNode.getType().equals("Integer") && this.rightNode.getType().equals("Integer"))
				return (Integer)this.leftNode.eval()-(Integer)this.rightNode.eval();
			else if(this.leftNode.getType().equals("Double") && this.rightNode.getType().equals("Double"))
				return (Double)this.leftNode.eval()-(Double)this.rightNode.eval();
			else if(this.leftNode.getType().equals("Double") && this.rightNode.getType().equals("Integer"))
				return (Double)this.leftNode.eval()-(Integer)this.rightNode.eval();
			else if(this.leftNode.getType().equals("Integer") && this.rightNode.getType().equals("Double"))
				return (Integer)this.leftNode.eval()-(Double)this.rightNode.eval();
		case "*":
			if(this.leftNode.getType().equals("Integer") && this.rightNode.getType().equals("Integer"))
				return (Integer)this.leftNode.eval()*(Integer)this.rightNode.eval();
			else if(this.leftNode.getType().equals("Double") && this.rightNode.getType().equals("Double"))
				return (Double)this.leftNode.eval()*(Double)this.rightNode.eval();
			else if(this.leftNode.getType().equals("Double") && this.rightNode.getType().equals("Integer"))
				return (Double)this.leftNode.eval()*(Integer)this.rightNode.eval();
			else if(this.leftNode.getType().equals("Integer") && this.rightNode.getType().equals("Double"))
				return (Integer)this.leftNode.eval()*(Double)this.rightNode.eval();
		case "/":
			if(this.leftNode.getType().equals("Integer") && this.rightNode.getType().equals("Integer"))
				return (Integer)(this.leftNode.eval())*1.00/(Integer)this.rightNode.eval();
			else if(this.leftNode.getType().equals("Double") && this.rightNode.getType().equals("Double"))
				return (Double)this.leftNode.eval()/(Double)this.rightNode.eval();
			else if(this.leftNode.getType().equals("Double") && this.rightNode.getType().equals("Integer"))
				return (Double)this.leftNode.eval()/(Integer)this.rightNode.eval();
			else if(this.leftNode.getType().equals("Integer") && this.rightNode.getType().equals("Double"))
				return (Integer)this.leftNode.eval()/(Double)this.rightNode.eval();
		case "%":
			if(this.leftNode.getType().equals("Integer") && this.rightNode.getType().equals("Integer"))
				return (Integer)this.leftNode.eval()%(Integer)this.rightNode.eval();
		case "==":
			if(this.leftNode.getType().equals("Integer") && this.rightNode.getType().equals("Integer"))
				return (Boolean)((Integer)this.leftNode.eval()==(Integer)this.rightNode.eval());
			else if(this.leftNode.getType().equals("Double") && this.rightNode.getType().equals("Double"))
				return (Boolean)((Double)this.leftNode.eval()==(Double)this.rightNode.eval());
			else if(this.leftNode.getType().equals("String") && this.rightNode.getType().equals("String"))
				return (Boolean)(((String)this.leftNode.eval()).equals((String)this.rightNode.eval()));
		case "!=":
			if(this.leftNode.getType().equals("Integer") && this.rightNode.getType().equals("Integer"))
				return (Boolean)((Integer)this.leftNode.eval()!=(Integer)this.rightNode.eval());
			else if(this.leftNode.getType().equals("Double") && this.rightNode.getType().equals("Double"))
				return (Boolean)((Double)this.leftNode.eval()!=(Double)this.rightNode.eval());
			else if(this.leftNode.getType().equals("String") && this.rightNode.getType().equals("String"))
				return (Boolean)(!((String)this.leftNode.eval()).equals((String)this.rightNode.eval()));
		case ">":
			if(this.leftNode.getType().equals("Integer") && this.rightNode.getType().equals("Integer"))
				return (Boolean)((Integer)this.leftNode.eval()>(Integer)this.rightNode.eval());
			else if(this.leftNode.getType().equals("Double") && this.rightNode.getType().equals("Double"))
				return (Boolean)((Double)this.leftNode.eval()>(Double)this.rightNode.eval());
		case "<":
			if(this.leftNode.getType().equals("Integer") && this.rightNode.getType().equals("Integer"))
				return (Boolean)((Integer)this.leftNode.eval()<(Integer)this.rightNode.eval());
			else if(this.leftNode.getType().equals("Double") && this.rightNode.getType().equals("Double"))
				return (Boolean)((Double)this.leftNode.eval()<(Double)this.rightNode.eval());	
		case ">=":
			if(this.leftNode.getType().equals("Integer") && this.rightNode.getType().equals("Integer"))
				return (Boolean)((Integer)this.leftNode.eval()>=(Integer)this.rightNode.eval());
			else if(this.leftNode.getType().equals("Double") && this.rightNode.getType().equals("Double"))
				return (Boolean)((Double)this.leftNode.eval()>=(Double)this.rightNode.eval());
		case "<=":
			if(this.leftNode.getType().equals("Integer") && this.rightNode.getType().equals("Integer"))
				return (Boolean)((Integer)this.leftNode.eval()<=(Integer)this.rightNode.eval());
			else if(this.leftNode.getType().equals("Double") && this.rightNode.getType().equals("Double"))
				return (Boolean)((Double)this.leftNode.eval()<=(Double)this.rightNode.eval());	
		case "&&":
			if(this.leftNode.getType().equals("Boolean") && this.rightNode.getType().equals("Boolean"))
				return (Boolean)this.leftNode.eval()&&(Boolean)this.rightNode.eval();			
		case "||":
			if(this.leftNode.getType().equals("Boolean") && this.rightNode.getType().equals("Boolean"))
				return (Boolean)this.leftNode.eval()||(Boolean)this.rightNode.eval();			
		case "=":
			if(this.rightNode.getType().equals("Integer"))
				return (Integer)this.rightNode.eval();
			else if(this.rightNode.getType().equals("Double"))
				return (Double)this.rightNode.eval();
			else if(this.rightNode.getType().equals("String"))
				return (String)this.rightNode.eval();
			else if(this.rightNode.getType().equals("Boolean"))
				return (Boolean)this.rightNode.eval();
		default:
			return null;
		}	
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public Object getRightNode() {
		return rightNode;
	}

	public void setRightNode(Node rightNode) {
		this.rightNode = rightNode;
	}

	public Object getLeftNode() {
		return leftNode;
	}

	public void setLeftNode(Node leftNode) {
		this.leftNode = leftNode;
	}
}
