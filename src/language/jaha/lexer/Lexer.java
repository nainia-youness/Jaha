package language.jaha.lexer;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Lexer {
	
	
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
	
	
}
