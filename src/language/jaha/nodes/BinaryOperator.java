package language.jaha.nodes;

public class BinaryOperator extends ExpressionNode{
	private String type;
	private String operator;
	private ExpressionNode rightNode;//GeneralObject or BinaryOperator or a Unary operator
	private ExpressionNode leftNode;
	private Object value;
	
	public BinaryOperator(String type, String operator, ExpressionNode leftNode ,ExpressionNode rightNode) {
		this.type = type;
		this.operator = operator;
		this.rightNode = rightNode;
		this.leftNode = leftNode;
	}
	
	public String diplayTree() {
		return "(right Node: "+this.rightNode.diplayTree()+" "+this.operator+ " left Node: "+this.leftNode.diplayTree()+")";
	}
	
	public Object eval() {
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
			return null;
		case "-":
			if(this.leftNode.getType().equals("Integer") && this.rightNode.getType().equals("Integer"))
				return (Integer)this.leftNode.eval()-(Integer)this.rightNode.eval();
			else if(this.leftNode.getType().equals("Double") && this.rightNode.getType().equals("Double"))
				return (Double)this.leftNode.eval()-(Double)this.rightNode.eval();
			else if(this.leftNode.getType().equals("Double") && this.rightNode.getType().equals("Integer"))
				return (Double)this.leftNode.eval()-(Integer)this.rightNode.eval();
			else if(this.leftNode.getType().equals("Integer") && this.rightNode.getType().equals("Double"))
				return (Integer)this.leftNode.eval()-(Double)this.rightNode.eval();
			return null;
		case "*":
			if(this.leftNode.getType().equals("Integer") && this.rightNode.getType().equals("Integer"))
				return (Integer)this.leftNode.eval()*(Integer)this.rightNode.eval();
			else if(this.leftNode.getType().equals("Double") && this.rightNode.getType().equals("Double"))
				return (Double)this.leftNode.eval()*(Double)this.rightNode.eval();
			else if(this.leftNode.getType().equals("Double") && this.rightNode.getType().equals("Integer"))
				return (Double)this.leftNode.eval()*(Integer)this.rightNode.eval();
			else if(this.leftNode.getType().equals("Integer") && this.rightNode.getType().equals("Double"))
				return (Integer)this.leftNode.eval()*(Double)this.rightNode.eval();
			return null;
		case "/":
			if(this.leftNode.getType().equals("Integer") && this.rightNode.getType().equals("Integer"))
				return (Integer)(this.leftNode.eval())*1.00/(Integer)this.rightNode.eval();
			else if(this.leftNode.getType().equals("Double") && this.rightNode.getType().equals("Double"))
				return (Double)this.leftNode.eval()/(Double)this.rightNode.eval();
			else if(this.leftNode.getType().equals("Double") && this.rightNode.getType().equals("Integer"))
				return (Double)this.leftNode.eval()/(Integer)this.rightNode.eval();
			else if(this.leftNode.getType().equals("Integer") && this.rightNode.getType().equals("Double"))
				return (Integer)this.leftNode.eval()/(Double)this.rightNode.eval();
			return null;
		case "%":
			if(this.leftNode.getType().equals("Integer") && this.rightNode.getType().equals("Integer"))
				return (Integer)this.leftNode.eval()%(Integer)this.rightNode.eval();
			return null;
		case "==":
			if(this.leftNode.getType().equals("Integer") && this.rightNode.getType().equals("Integer"))
				return (Boolean)((Integer)this.leftNode.eval()==(Integer)this.rightNode.eval());
			else if(this.leftNode.getType().equals("Double") && this.rightNode.getType().equals("Double"))
				return (Boolean)((Double)this.leftNode.eval()==(Double)this.rightNode.eval());
			else if(this.leftNode.getType().equals("String") && this.rightNode.getType().equals("String"))
				return (Boolean)(((String)this.leftNode.eval()).equals((String)this.rightNode.eval()));
			return null;
		case "!=":
			if(this.leftNode.getType().equals("Integer") && this.rightNode.getType().equals("Integer"))
				return (Boolean)((Integer)this.leftNode.eval()!=(Integer)this.rightNode.eval());
			else if(this.leftNode.getType().equals("Double") && this.rightNode.getType().equals("Double"))
				return (Boolean)((Double)this.leftNode.eval()!=(Double)this.rightNode.eval());
			else if(this.leftNode.getType().equals("String") && this.rightNode.getType().equals("String"))
				return (Boolean)(!((String)this.leftNode.eval()).equals((String)this.rightNode.eval()));
			return null;
		case ">":
			if(this.leftNode.getType().equals("Integer") && this.rightNode.getType().equals("Integer"))
				return (Boolean)((Integer)this.leftNode.eval()>(Integer)this.rightNode.eval());
			else if(this.leftNode.getType().equals("Double") && this.rightNode.getType().equals("Double"))
				return (Boolean)((Double)this.leftNode.eval()>(Double)this.rightNode.eval());
			return null;
		case "<":
			if(this.leftNode.getType().equals("Integer") && this.rightNode.getType().equals("Integer"))
				return (Boolean)((Integer)this.leftNode.eval()<(Integer)this.rightNode.eval());
			else if(this.leftNode.getType().equals("Double") && this.rightNode.getType().equals("Double"))
				return (Boolean)((Double)this.leftNode.eval()<(Double)this.rightNode.eval());	
			return null;
		case ">=":
			if(this.leftNode.getType().equals("Integer") && this.rightNode.getType().equals("Integer"))
				return (Boolean)((Integer)this.leftNode.eval()>=(Integer)this.rightNode.eval());
			else if(this.leftNode.getType().equals("Double") && this.rightNode.getType().equals("Double"))
				return (Boolean)((Double)this.leftNode.eval()>=(Double)this.rightNode.eval());
			return null;
		case "<=":
			if(this.leftNode.getType().equals("Integer") && this.rightNode.getType().equals("Integer"))
				return (Boolean)((Integer)this.leftNode.eval()<=(Integer)this.rightNode.eval());
			else if(this.leftNode.getType().equals("Double") && this.rightNode.getType().equals("Double"))
				return (Boolean)((Double)this.leftNode.eval()<=(Double)this.rightNode.eval());	
			return null;
		case "&&":
			if(this.leftNode.getType().equals("Boolean") && this.rightNode.getType().equals("Boolean"))
				return (Boolean)this.leftNode.eval()&&(Boolean)this.rightNode.eval();			
		case "||":
			if(this.leftNode.getType().equals("Boolean") && this.rightNode.getType().equals("Boolean"))
				return (Boolean)this.leftNode.eval()||(Boolean)this.rightNode.eval();		
			return null;
		case "=":
			if(this.rightNode.getType().equals("Integer")) {
				leftNode.setValue((Object)this.rightNode.eval());
				leftNode.setType("Integer");
				return (Integer)this.rightNode.eval();
			}
			else if(this.rightNode.getType().equals("Double")) {
				leftNode.setValue((Object)this.rightNode.eval());
				leftNode.setType("Double");
				return (Double)this.rightNode.eval();
			}
			else if(this.rightNode.getType().equals("String")) {
				leftNode.setValue((Object)this.rightNode.eval());
				leftNode.setType("String");
				return (String)this.rightNode.eval();
			}
			else if(this.rightNode.getType().equals("Boolean")) {
				leftNode.setValue((Object)this.rightNode.eval());
				leftNode.setType("Boolean");
				return (Boolean)this.rightNode.eval();
			}	
			return null;
		case "+=":
			if(this.rightNode.getType().equals("Integer")) {
				leftNode.setValue((Integer)leftNode.getValue()+(Integer)this.rightNode.eval());
				return (Integer)this.rightNode.eval();
			}
			else if(this.rightNode.getType().equals("Double")) {
				leftNode.setValue((Double)leftNode.getValue()+(Double)this.rightNode.eval());
				return (Double)this.rightNode.eval();
			}
			else if(this.rightNode.getType().equals("String")) {
				leftNode.setValue((String)leftNode.getValue()+(String)this.rightNode.eval());
				return (String)this.rightNode.eval();
			}
			return null;
		case "-=":
			if(this.rightNode.getType().equals("Integer")) {
				leftNode.setValue((Integer)leftNode.getValue()-(Integer)this.rightNode.eval());
				return (Integer)this.rightNode.eval();
			}
			else if(this.rightNode.getType().equals("Double")) {
				leftNode.setValue((Double)leftNode.getValue()-(Double)this.rightNode.eval());
				return (Double)this.rightNode.eval();
			}
			return null;
		case "*=":
			if(this.rightNode.getType().equals("Integer")) {
				leftNode.setValue((Integer)leftNode.getValue()*(Integer)this.rightNode.eval());
				return (Integer)this.rightNode.eval();
			}
			else if(this.rightNode.getType().equals("Double")) {
				leftNode.setValue((Double)leftNode.getValue()*(Double)this.rightNode.eval());
				return (Double)this.rightNode.eval();
			}
			return null;
		case "/=":
			if(this.rightNode.getType().equals("Integer")) {
				leftNode.setValue((Integer)leftNode.getValue()/(Integer)this.rightNode.eval());
				return (Integer)this.rightNode.eval();
			}
			else if(this.rightNode.getType().equals("Double")) {
				leftNode.setValue((Double)leftNode.getValue()/(Double)this.rightNode.eval());
				return (Double)this.rightNode.eval();
			}
			return null;
		case "%=":
			if(this.rightNode.getType().equals("Integer")) {
				leftNode.setValue((Integer)leftNode.getValue()%(Integer)this.rightNode.eval());
				return (Integer)this.rightNode.eval();
			}
			return null;
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

	public ExpressionNode getRightNode() {
		return rightNode;
	}

	public void setRightNode(ExpressionNode rightNode) {
		this.rightNode = rightNode;
	}

	public ExpressionNode getLeftNode() {
		return leftNode;
	}

	public void setLeftNode(ExpressionNode leftNode) {
		this.leftNode = leftNode;
	}
	
	public Object getValue() {
		return this.eval();
	}

	public void setValue(Object value) {
		this.value = value;
	}
	
}
