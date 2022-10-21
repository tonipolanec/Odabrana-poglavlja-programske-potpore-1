package hr.fer.oprpp1.custom.scripting.nodes;

/** Class for Text nodes in document model.
 * 
 * @author Toni Polanec
 */
public class TextNode extends Node {

	/**<p> Text of TextNode </p>*/
	private String text;
	
	
	public TextNode(String text) {
		this.text = text;
	}

	/** Returns variable text
	 *  @return text
	 */
	public String getText() {
		return text;
	}
	
	
	@Override
	public String toString() {
		// We need to add escapes back in
		String stringWithEscapes = getText().replace("\\", "\\\\").replace("{", "\\{");
		return stringWithEscapes;
	}
	
	@Override
 	public boolean equals(Node node) {
 		if(node instanceof TextNode) {
 			TextNode otherNode = (TextNode) node;
	 		if (!super.equals(otherNode)) return false;
	 		
	 		if (!text.equals(otherNode.text)) return false;
	 		
	 		return true;
 		}
 		
 		return false;
 	}
}
