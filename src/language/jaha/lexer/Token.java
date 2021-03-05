package language.jaha.lexer;

public class Token {
	
	private int id;
	private String type;
	private int line;
	private int column;
	private String symbol;
	
	public Token(int id, String type, int line, int column, String symbol) {
		this.id = id;
		this.type = type;
		this.line = line;
		this.column = column;
		this.symbol = symbol;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
