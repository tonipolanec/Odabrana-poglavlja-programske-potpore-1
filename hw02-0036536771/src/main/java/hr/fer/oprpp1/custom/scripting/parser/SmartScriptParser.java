package hr.fer.oprpp1.custom.scripting.parser;

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
						
						((Node) stack.peek()).addChildNode(node);
					
					} 
					
				}
				
					
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
