package language.jaha.lexer;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Arrays;
import java.util.List;

public class Lexer {
	
	List<Token> listOfTokens;
	final List<String> language=Arrays.asList(
    //key words
	"si",
	"pour",
	"montre"
	);
	
	final List<Character> specialChars=Arrays.asList(
	' ',
	';',
	'=',
	'+',
	'-',
	'*',
	'/',
	'(',
	')',
	'"'
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
	
	public boolean isInLanguage(String s) {
		for(int i=0;i<this.language.size();i++) {
			if(s.equals(this.language.get(i))) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isSpecialChar(char c) {
		for(int i=0;i<this.specialChars.size();i++) {
			if(c==this.specialChars.get(i)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isTokenNumber(String token) {
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
		String token="";

		for(int i=0;i<line.length();i++) {
			token+=line.charAt(i);
			if(isSpecialChar(line.charAt(i))) {
				token = token.substring(0, token.length() - 1);
				char specialChar=line.charAt(i);
				if(isInLanguage(token)) {
					System.out.println("key word: "+token);
				}
				else {
					if(isTokenNumber(token)) {
						if(isInt(token)) {
							System.out.println("int: "+token);
						}
						else {
							System.out.println("float: "+token);
						}
					}
					else {
						System.out.println("variable: "+token);
					}
				}
				System.out.println("spacial char: "+specialChar);
				token="";
			}
		}
	}
}
