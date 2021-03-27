package language.jaha.lexer;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lexer {
	
	private List<Token> listOfTokens=new ArrayList<>();
	
	final List<List<String>> language=Arrays.asList(
	Arrays.asList("si","Keyword_if"),
	Arrays.asList("sinon","Keyword_else"),
	Arrays.asList("pour","Keyword_for"),
	Arrays.asList("ecrire","Keyword_print"),
	Arrays.asList("quand","Keyword_while"),
	Arrays.asList("fonction","Keyword_function"),
	Arrays.asList("retourne","Keyword_return"),
	Arrays.asList("vecteur","Keyword_array")
	);
	
	final List<List<String>> specialChars=Arrays.asList(
	Arrays.asList("	","Tab"),		
	Arrays.asList(" ","Space"),
	Arrays.asList(";","Semicolon"),
	Arrays.asList(",","Comma"),
	Arrays.asList("=","Op_assign"),
	Arrays.asList("==","Op_equal"),
	Arrays.asList("!=","Op_notequal"),
	Arrays.asList("+","Op_add"),
	Arrays.asList("-","Op_subtract"),
	Arrays.asList("*","Op_multiply"),
	Arrays.asList("/","Op_divide"),
	Arrays.asList("%","Op_mod"),
	Arrays.asList("!","Unary_Op_not"),
	Arrays.asList("&","error"),
	Arrays.asList("|","error"),
	Arrays.asList("++","Op_increment"),
	Arrays.asList("--","Op_decrement"),
	Arrays.asList("<","Op_less"),
	Arrays.asList(">","Op_greater"),
	Arrays.asList("<=","Op_lessequal"),
	Arrays.asList(">=","Op_greaterequal"),
	Arrays.asList("(","LeftParen"),
	Arrays.asList(")","RightParen"),
	Arrays.asList("{","LeftBrace"),
	Arrays.asList("}","RightBrace"),
	Arrays.asList("&&","Op_and"),
	Arrays.asList("||","Op_or"),
	Arrays.asList("+=","Op_add_assign"),
	Arrays.asList("-=","Op_subtract_assign"),
	Arrays.asList("*=","Op_multiply_assign"),
	Arrays.asList("%=","Op_Op_mod_assign")
	);
	
	
	public List<Token> getListOfTokens(){
		return this.listOfTokens;
	}
	
	public void printCode() {
		try {
			File myObj = new File("src/code.txt");
		    Scanner myReader = new Scanner(myObj);
		    while (myReader.hasNextLine()) {
		       String data = myReader.nextLine();
		       System.out.println(data);
		    }
		    myReader.close();
		}
		catch (FileNotFoundException e) {
		    System.out.println("An error occurred.");
		    e.printStackTrace();
		}
	}
	
	public void tokenize() {
		try {
			File myObj = new File("src/code.txt");
		    Scanner myReader = new Scanner(myObj);
		    int row=1;
		    while (myReader.hasNextLine()) {
		       String data = myReader.nextLine();
		       tokenizeLine(data,row);
		       row++;
		    }
		    myReader.close();
		}
		catch (FileNotFoundException e) {
		    System.out.println("An error occurred.");
		    e.printStackTrace();
		}
	}
	
	private Object[] isInLanguage(String s) {
		Object[] keyWord=new Object[2];  
		if(s.equals("")) {
			keyWord[0]=false;
			return keyWord; 
		}
		for(int i=0;i<this.language.size();i++) {
			if(s.equals(this.language.get(i).get(0))) {
				keyWord[0]=true;
				keyWord[1]=this.language.get(i).get(1);
				return keyWord; 
			}
		}
		keyWord[0]=false;
		return keyWord; 
	}
	
	private Object[] containSpecialChar(String specialChar) {
		Object[] sc=new Object[2];  
		String c;
		for(int i=0;i<this.specialChars.size();i++) {
			c=this.specialChars.get(i).get(0);
			if(specialChar.equals(c)) {
				sc[0]=true;
				sc[1]=this.specialChars.get(i).get(1);
				return sc;
			}
		}
		sc[0]=false;
		return sc;
	}
	
	
	public boolean isSpecialChar(String token) {
		for(int i=0;i<this.specialChars.size();i++) {
			if(token.charAt(0)==this.specialChars.get(i).get(0).charAt(0))
				return true;
		}
		return false;
	}
	
	public boolean isTokenNumber(String token) {
		if(token.equals(""))
			return false;
		String numbers="0123456789.";
		boolean isNumber;
	    for(int i=0;i<token.length();i++) {
	    	isNumber=false;
	    	for(int j=0;j<numbers.length();j++) {
		    	if(token.charAt(i)==numbers.charAt(j)) {
		    		isNumber=true;
		    	}
	    	}
	    	if(isNumber==false) {
	    		return false;
	    	}
	    }
	    return true;
	}
	
	public boolean isInt(String token) {
		for(int i=0;i<token.length();i++) {
			if(token.charAt(i)=='.') {
				return false;
			}
		}
		return true;
	}
	
	public void tokenizeLine(String line,int row) {
		//remove comments
	    Pattern patternInlineCom = Pattern.compile("//", Pattern.CASE_INSENSITIVE);
	    Matcher InlineComMatcher = patternInlineCom.matcher(line);
	    boolean patternInlineComFound = InlineComMatcher.find();
	    if(patternInlineComFound) {
	      line = line.substring(0, InlineComMatcher.end()-2);
	    } 
		String token="";
		for(int i=0;i<line.length();i++) {
			token+=line.charAt(i);
			List<Object> specialCharList=Arrays.asList(containSpecialChar(line.substring(i,i+1)));
			String specialChar;
			if((boolean) specialCharList.get(0)) {//is line[i] special char
					if(i<line.length()-1 && (boolean) containSpecialChar(line.substring(i,i+2))[0])//is line[i]+line[i+1] a special char
					{
						specialCharList=Arrays.asList(containSpecialChar(line.substring(i,i+2)));
						specialChar=line.substring(i,i+2);
						token = token.substring(0, token.length() - 1);
						i++;
					}
					else {
						specialChar=line.substring(i,i+1);
						token = token.substring(0, token.length() - 1);
					}
				if((boolean) isInLanguage(token)[0]) {
					List<Object> keyWord=Arrays.asList(isInLanguage(token));
					Token t=new Token((String)keyWord.get(1),row,i,token);
					listOfTokens.add(t);
				}
				else {
					if(isTokenNumber(token)) {//numbers
						if(isInt(token)) {
							Token t=new Token("Integer",row,i,token);
							listOfTokens.add(t);
						}
						else {
							Token t=new Token("Double",row,i,token);
							listOfTokens.add(t);
						}
					}
					else {//Idenfiers and boolean
						if(token.equals("true"))
						{
							Token t=new Token("Boolean",row,i,"true");
							listOfTokens.add(t);
						}
						else if(token.equals("false"))
						{
							Token t=new Token("Boolean",row,i,"false");
							listOfTokens.add(t);
						}
						else {
							if(!token.equals(" ") && !token.equals("	") && !token.equals("")) {
								System.out.println("Identifier="+token);
								Token t=new Token("Identifier",row,i,token);
								listOfTokens.add(t);
							}
						}
					}
				}
				if(!specialCharList.get(1).equals("Tab") && !specialCharList.get(1).equals("Space"))
				{
					Token t=new Token((String)specialCharList.get(1),row,i+1,specialChar);
					listOfTokens.add(t);
				}
				token="";
			}
			else {//for the strings
				if(line.substring(i,i+1).equals("\"")) {
				    Pattern stringPattern= Pattern.compile("\".*?\"", Pattern.CASE_INSENSITIVE);
				    Matcher stringMatcher = stringPattern.matcher(line);
				    boolean stringPatternFound = stringMatcher.find();
				    if(stringPatternFound) {
						Token t=new Token("String",row,i+1,line.substring(stringMatcher.start()+1,stringMatcher.end()-1));
						listOfTokens.add(t);
				    	line = line.substring(0,stringMatcher.start())+line.substring(stringMatcher.end(),line.length());
				    	token = token.substring(0, token.length() - 1);
				    	i--;
				    } 
				}
			}
		}
	}
	
	
	public void showTokens() {
		for (int i = 0; i < this.listOfTokens.size(); i++) {
			this.listOfTokens.get(i).showToken();
		}
	}
}
