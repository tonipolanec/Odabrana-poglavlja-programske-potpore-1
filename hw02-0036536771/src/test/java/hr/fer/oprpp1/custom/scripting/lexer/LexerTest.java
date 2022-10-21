package hr.fer.oprpp1.custom.scripting.lexer;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import hr.fer.oprpp1.custom.scripting.elems.Element;
import hr.fer.oprpp1.custom.scripting.elems.ElementString;


public class LexerTest {
	
	/** Helper method for getting next element from lexer */
	private Element getElement(String text) {
		Lexer lexer = new Lexer(text);
		return lexer.getElement();
	}

	
	@Test
	public void testCorrectVariable1() { 
		String text = "{$A7_bb 1 10 1 $}{$end$}";
		assertDoesNotThrow(() -> getElement(text));
	}
	@Test
	public void testCorrectVariable2() {
		String text = "{$               tmp_34 3 1 10 1 $}{$end$}";
		assertDoesNotThrow(() -> getElement(text));
	}
	@Test
	public void testWrongVariable1() { // 3 is not variable name
		String text = "{$ * 3 1 10 1 $}{$end$}";
		assertThrows(LexerException.class, () -> getElement(text));
	}
	@Test
	public void testWrongVariable2() { // _a21 is not variable name
		String text = "{$ _a21 3 1 10 1 $}{$end$}";
		assertThrows(LexerException.class, () -> getElement(text));
	}
	@Test
	public void testWrongVariable3() { // 32 is not variable name
		String text = "{$ 32 3 1 10 1 $}{$end$}";
		assertThrows(LexerException.class, () -> getElement(text));
	}
	@Test
	public void testWrongVariable4() { // 3s_ee is not variable name
		String text = "{$ 3s_ee 3 1 10 1 $}{$end$}";
		assertThrows(LexerException.class, () -> getElement(text));
	}
	@Test
	public void testVariable5() { // $ is not variable name
		String text = "{$ $ 3 1 10 1 $}{$end$}";
		assertThrows(LexerException.class, () -> getElement(text));
	}
	
	
	
	
	/** Helper method for getting two next element from lexer */
	private Element getSecondElement(String text) {
		// Using for checking function names so we need to get '=' first, then test function name
		Lexer lexer = new Lexer(text);
		
		@SuppressWarnings("unused")
		Element e = lexer.getElement();
		return lexer.getElement();
	}
	
	
	@Test
	public void testCorrectFunction1() {
		String text = "{$= @as $}";
		assertDoesNotThrow(() -> getElement(text));
	}
	@Test
	public void testCorrectFunction2() { 
		String text = "{$= @sds_a1 $}";
		assertDoesNotThrow(() -> getElement(text));
	}
	@Test
	public void testWrongFunction1() { // blank is not function name
		String text = "{$= @ $}";
		assertThrows(LexerException.class, () -> getSecondElement(text));
	}
	@Test
	public void testWrongFunction2() { // 1 is not function name
		String text = "{$= @1 $}";
		assertThrows(LexerException.class, () -> getSecondElement(text));
	}
	@Test
	public void testWrongFunction3() { // * is not function name
		String text = "{$= @* $}";
		assertThrows(LexerException.class, () -> getSecondElement(text));
	}
	
	
	
	@Test
	public void testCorrectTag1() {
		String text = "{$=  $}";
		assertDoesNotThrow(() -> getElement(text));
	}
	@Test
	public void testCorrectTag2() {
		String text = "{$    fOr  $}";
		assertDoesNotThrow(() -> getElement(text));
	}
	@Test
	public void testCorrectTag3() { 
		String text = "{$f__2a  $}";
		assertDoesNotThrow(() -> getElement(text));
	}
	@Test
	public void testWrongTag1() { // - is not good tag
		String text = "{$-  $}";
		assertThrows(LexerException.class, () -> getElement(text));
	}
	@Test
	public void testWrongTag2() { // * is not good tag
		String text = "{$*  $}";
		assertThrows(LexerException.class, () -> getElement(text));
	}
	@Test
	public void testWrongTag3() { // 2for is not good tag
		String text = "{$2for  $}";
		assertThrows(LexerException.class, () -> getElement(text));
	}
	@Test
	public void testWrongTag4() { // _a3s is not good tag
		String text = "{$     _a3s  $}";
		assertThrows(LexerException.class, () -> getElement(text));
	}

	
	
	@Test
	public void testEscape() {
		String text = "{$= \"Joe \\\"Long\\\" Smith\"$}";

		assertDoesNotThrow(() -> getSecondElement(text));
		
		Element e = getSecondElement(text);
		if (!(e instanceof ElementString))
			fail();
		
		assertEquals("Joe \"Long\" Smith" , e.asText());
	}
	
	
	
}
