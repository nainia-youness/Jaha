package language.jaha.lexer;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lexer {
	
	List<Token> listOfTokens;
	
	final List<List<String>> language=Arrays.asList(
	Arrays.asList("si","Keyword_if"),
	Arrays.asList("sinon","Keyword_else"),
	Arrays.asList("pour","Keyword_for"),
	Arrays.asList("montre","Keyword_print"),
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
	Arrays.asList("!","Op_not"),
	Arrays.asList("&","Op_and"),
	Arrays.asList("&","Op_or"),
	Arrays.asList("++","Op_increment"),
	Arrays.asList("--","Op_negativeincrement"),
	Arrays.asList("<","Op_less"),
	Arrays.asList(">","Op_greater"),
	Arrays.asList("<=","Op_lessequal"),
	Arrays.asList(">=","Op_greaterequal"),
	Arrays.asList("(","LeftParen"),
	Arrays.asList(")","RightParen"),
	Arrays.asList("{","LeftBrace"),
	Arrays.asList("}","RightBrace"),
	Arrays.asList("\"","Quote"),
	Arrays.asList("&&","Op_and"),
	Arrays.asList("||","Op_or")
	);
	
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
		    while (myReader.hasNextLine()) {
		       String data = myReader.nextLine();
		       tokenizeLine(data);
		    }
		    myReader.close();
		}
		catch (FileNotFoundException e) {
		    System.out.println("An error occurred.");
		    e.printStackTrace();
		}
	}
	
	public Object[] isInLanguage(String s) {
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
	
	public Object[] containSpecialChar(String specialChar) {
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
	
	public void tokenizeLine(String line) {
		//remove comments
	    Pattern patternInlineCom = Pattern.compile("//", Pattern.CASE_INSENSITIVE);
	    Matcher InlineComMatcher = patternInlineCom.matcher(line);
	    boolean patternInlineComFound = InlineComMatcher.find();
	    if(patternInlineComFound) {
	      line = line.substring(0, InlineComMatcher.end()-2);
	    } 
	    //
	
		
		String token="";
		boolean isContinue;
		for(int i=0;i<line.length();i++) {
			token+=line.charAt(i);
			isContinue=false;
			List<Object> specialCharList=Arrays.asList(containSpecialChar(line.substring(i,i+1)));
			String specialChar;
			if((boolean) specialCharList.get(0)) {//is line[i] special char
					if(i<line.length()-1 && (boolean) containSpecialChar(line.substring(i,i+2))[0])//is line[i]+line[i+1] a spetial char
					{
						specialCharList=Arrays.asList(containSpecialChar(line.substring(i,i+2)));
						specialChar=line.substring(i,i+2);
						isContinue=true;
					}
					else {
						specialChar=line.substring(i,i+1);
					}
					if((boolean) containSpecialChar(token)[0]) {//is all the token a special catacter
						specialCharList=Arrays.asList(containSpecialChar(token));
						specialChar=token;
						System.out.println("spacial char: "+specialChar+" type: "+specialCharList.get(1));
						token="";
						continue;
					}
					else {
						token = token.substring(0, token.length() - 1);
					}
				if((boolean) isInLanguage(token)[0]) {
					List<Object> keyWord=Arrays.asList(isInLanguage(token));
					System.out.println("key word: "+token+ " type: "+keyWord.get(1));
				}
				else {
					if(isTokenNumber(token)) {//numbers
						if(isInt(token)) {
							System.out.println("int: "+token);
						}
						else {
							System.out.println("float: "+token);
						}
					}
					else {//vars and string
						System.out.println("Identifier: "+token+" type: Identifier");
					}
				}
				System.out.println("spacial char: "+specialChar+" type: "+specialCharList.get(1));
				token="";
				if(isContinue) {
					i++;
				}
			}
		}
	}
}
