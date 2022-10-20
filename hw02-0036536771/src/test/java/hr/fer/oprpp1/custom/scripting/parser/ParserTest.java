package hr.fer.oprpp1.custom.scripting.parser;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

import hr.fer.oprpp1.custom.scripting.nodes.DocumentNode;
import hr.fer.oprpp1.custom.scripting.nodes.EchoNode;
import hr.fer.oprpp1.custom.scripting.nodes.Node;
import hr.fer.oprpp1.custom.scripting.nodes.TextNode;

public class ParserTest {
	
	/** Helper method for parsing and returning DocumentNode */
	private DocumentNode getDocumentNode(String text) {
		SmartScriptParser parser = new SmartScriptParser(text);
		return parser.getDocumentNode();
	}
	
	
	@Test
	public void testExampleInPDF() {
		String text = "This is sample text.\r\n"
				+ "{$ FOR i 1 10 1 $}\r\n"
				+ "This is {$= i $}-th time this message is generated.\r\n"
				+ "{$END$}\r\n"
				+ "{$FOR i 0 10 2 $}\r\n"
				+ "sin({$=i$}^2) = {$= i i * @sin \"0.000\" @decfmt $}\r\n"
				+ "{$END$}";
		
		DocumentNode dN = getDocumentNode(text);
		
		// 4 children of DocumentNode
		assertEquals(4, dN.numberOfChildren());
		
		// 2 children of DocumentNode are TextNodes
		assertEquals(2, numOfTextNodes(dN));
		
		// child[1] is ForLoopNode and it has 2 TextNodes 
		assertEquals(2, numOfTextNodes(dN.getChild(1)));
		
		// child[3] is ForLoopNode and it has 3 TextNodes 
		assertEquals(3, numOfTextNodes(dN.getChild(3)));
	}
	
	
	@Test
	public void testForLoopNode1() { // 3 is not variable name
		String text = "{$ FOR 3 1 10 1 $}{$end$}";
		assertThrows(SmartScriptParserException.class, () -> getDocumentNode(text));
	}
	@Test
	public void testForLoopNode2() { // * is not variable name
		String text = "{$ FOR * \"1\" -10 \"1\" $}{$end$}";
		assertThrows(SmartScriptParserException.class, () -> getDocumentNode(text));
	}
	@Test
	public void testForLoopNode3() { // @sin function element
		String text = "{$ FOR year @sin 10 $}{$end$}";
		assertThrows(SmartScriptParserException.class, () -> getDocumentNode(text));
	}
	@Test
	public void testForLoopNode4() { // too many arguments
		String text = "{$ FOR year 1 10 \"1\" \"10\" $}{$end$}";
		assertThrows(SmartScriptParserException.class, () -> getDocumentNode(text));
	}
	@Test
	public void testForLoopNode5() { // too few arguments
		String text = "{$ FOR year $}{$end$}";
		assertThrows(SmartScriptParserException.class, () -> getDocumentNode(text));
	}
	@Test
	public void testForLoopNode6() { // too many arguments
		String text = "{$ FOR year 1 10 1 3 $}{$end$}";
		assertThrows(SmartScriptParserException.class, () -> getDocumentNode(text));
	}
	@Test
	public void testForLoopNode7() { // too many arguments
		String text = "{$ FOR i-1.35bbb\"1\" $}{$end$}";
		assertDoesNotThrow(() -> getDocumentNode(text));
	}
	
	
	
	@Test 
	public void testEscapesInOutOfTag() {
		DocumentNode dN = getDocumentNode("Example \\{$=1$}. Now actually write one {$=1$}");
		
		// Two children only; TextNode and EchoNode
		assertEquals(2, dN.numberOfChildren());
		// EchoNode with only one element
		assertEquals(1, ((EchoNode)dN.getChild(1)).getSize());
	}
	
	
	
	
	@Test
	public void testExample1() {
		String text = readExample(1);
		assertEquals(1, numOfTextNodes(getDocumentNode(text)));
	}
	@Test
	public void testExample2() {
		String text = readExample(2);
		assertEquals(1, numOfTextNodes(getDocumentNode(text)));
	}
	@Test
	public void testExample3() {
		String text = readExample(3);
		assertEquals(1, numOfTextNodes(getDocumentNode(text)));
	}
	@Test
	public void testExample4() {
		String text = readExample(4);
		assertThrows(SmartScriptParserException.class, () -> getDocumentNode(text));
	}
	@Test
	public void testExample5() {
		String text = readExample(5);
		assertThrows(SmartScriptParserException.class, () -> getDocumentNode(text));
	}
	@Test
	public void testExample6() {
		String text = readExample(6);
		DocumentNode dN = getDocumentNode(text);
		
		// One String in EchoNode
		assertEquals(1, ((EchoNode)dN.getChild(1)).getSize());
	}
	@Test
	public void testExample7() {
		String text = readExample(7);
		DocumentNode dN = getDocumentNode(text);
		
		// One String in EchoNode
		assertEquals(1, ((EchoNode)dN.getChild(1)).getSize());
	}
	@Test
	public void testExample8() {
		String text = readExample(8);
		assertThrows(SmartScriptParserException.class, () -> getDocumentNode(text));
	}
	@Test
	public void testExample9() {
		String text = readExample(9);
		assertThrows(SmartScriptParserException.class, () -> getDocumentNode(text));
	}
	
	/**<p> Helper method for reading given examples </p>*/
	private String readExample(int n) {
		try(InputStream is = this.getClass().getClassLoader().getResourceAsStream("extra/primjer"+n+".txt")) {
			if(is==null) throw new RuntimeException("File extra/primjer"+n+".txt is unavailable.");
			
		    byte[] data = is.readAllBytes();
		    String text = new String(data, StandardCharsets.UTF_8);
		    return text;
		} catch(IOException ex) {
			throw new RuntimeException("Error with file reading!", ex);
		}
	}
	
	
	/** Helper method for counting number of TextNodes directly beneath given node */
	private int numOfTextNodes(Node node) {
		int num=0;
		for(int i=0; i<node.numberOfChildren(); i++) {
			if (node.getChild(i) instanceof TextNode) 
				num++;
		}
		return num;
	}
	
	
}
