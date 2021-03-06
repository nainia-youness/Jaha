package language.jaha.lexer;

public class Token {
	
	private String type;
	private int line;
	private int column;
	private String symbol;
	
	public Token(String type, int line, int column, String symbol) {
		this.type = type;
		this.line = line;
		this.column = column;
		this.symbol = symbol;
	}
	
	public void showToken() {
		System.out.println("type: "+this.type+ " line: "+this.line+" column: "+this.column+" symbol: "+this.symbol);
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getLine() {
		return line;
	}
	public void setLine(int line) {
		this.line = line;
	}
	public int getColumn() {
		return column;
	}
	public void setColumn(int column) {
		this.column = column;
	}
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	
}
