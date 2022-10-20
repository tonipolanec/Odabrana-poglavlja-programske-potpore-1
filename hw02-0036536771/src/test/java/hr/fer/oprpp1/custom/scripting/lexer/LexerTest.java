package hr.fer.oprpp1.custom.scripting.lexer;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class LexerTest {

	

	
	
	/**<p> Helper method for reading examples </p>*/
	private String readExample(int n) {
		try(InputStream is = this.getClass().getClassLoader().getResourceAsStream("extra/primjer"+n+".txt")) {
			if(is==null) throw new RuntimeException("Datoteka extra/primjer"+n+".txt je nedostupna.");
			
		    byte[] data = is.readAllBytes();
		    String text = new String(data, StandardCharsets.UTF_8);
		    return text;
		} catch(IOException ex) {
			throw new RuntimeException("Error with file reading!", ex);
		  }
		}
}
