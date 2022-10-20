package hr.fer.oprpp1.custom.scripting.parser;

import hr.fer.oprpp1.custom.collections.EmptyStackException;
import hr.fer.oprpp1.custom.collections.ObjectStack;
import hr.fer.oprpp1.custom.scripting.elems.*;
import hr.fer.oprpp1.custom.scripting.lexer.*;
import hr.fer.oprpp1.custom.scripting.nodes.*;

public class SmartScriptParser {

	//private String document;
	private Lexer lexer;
	DocumentNode documentNode;
	
	public SmartScriptParser(String document) {
		//this.document = document;
		lexer = new Lexer(document);
		parse();
	}
	
	/** Parse document using initialized lexer.
	 * 
	 * @throws SmartScriptParserException if parsing gone wrong
	 */
	public void parse() {
		try {
			
			documentNode = new DocumentNode();
			Element element;
			Node node;
			int flag = 0; // default state
			
			ObjectStack stack = new ObjectStack();
			stack.push(documentNode);
			
			while(lexer.checkNextElement()) {
				element = lexer.getElement();
				
				// ELEMENT STRING
				if (element instanceof ElementString) {
					node = new TextNode(element.asText());
					((Node) stack.peek()).addChildNode(node);
				}
				
				
				else if (element instanceof ElementTag) {
					if (element.asText() == "for") {
						Element start = lexer.getElement();
						Element end = lexer.getElement();
						if (lexer.getState() == LexerState.TAG) {
							Element step = lexer.getElement();
							
							node = new ForLoopNode(new ElementVariable(element.asText()), start, end, step);
						} else {
							node = new ForLoopNode(new ElementVariable(element.asText()), start, end, null);
						}
						
						try {
							((Node) stack.peek()).addChildNode(node);
						} catch(EmptyStackException ex) {
							throw new SmartScriptParserException("Cannot add child node to nothing!");
						}
						
						
					} else if (element.asText() == "end") {
						try {
							stack.pop();
						} catch (EmptyStackException ex) {
							throw new SmartScriptParserException("Empty stack exception! (Too much END tags)");
						}
					
					} else if (element.asText() == "=") {
						EchoNode echoNode = new EchoNode();
						
						while (lexer.getState() == LexerState.TAG) {
							Element e;
							if (lexer.checkNextElement()) {
								e = lexer.getElement();
								echoNode.addElement(e);
							}
						}
						
						try {
							((Node) stack.peek()).addChildNode(echoNode);
						} catch(EmptyStackException ex) {
							throw new SmartScriptParserException("Cannot add child node to nothing!");
						}
					}
					
				}
				
					
			}
			
			try { // After all there must be document node on stack!
				if(!(stack.pop() instanceof DocumentNode)) {
					throw new SmartScriptParserException("Didn't close all open tags!");
				}
			} catch (EmptyStackException ex) {
				throw new SmartScriptParserException("Wrong closings of open tags!");
			}
			
			
		} catch (LexerException ex) {
			throw new SmartScriptParserException(ex.getMessage());
		} catch (Exception ex) {
			throw new SmartScriptParserException("Unknown error!");
		}
		
		
	}
	
	/**<p> Returns document node </p>*/
 	public DocumentNode getDocumentNode() {
		return documentNode;
	}

}
