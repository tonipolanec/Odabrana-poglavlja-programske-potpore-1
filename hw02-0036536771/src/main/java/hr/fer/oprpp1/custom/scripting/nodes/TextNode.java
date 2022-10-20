package hr.fer.oprpp1.custom.scripting.nodes;

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
		return getText() + "";
	}
	
	@Override
 	public boolean equals(Object other) {
 		if(other instanceof TextNode) {
 			TextNode otherNode = (TextNode) other;
	 		if (!super.equals(otherNode)) return false;
	 		
	 		if (!text.equals(otherNode.text)) return false;
	 		
	 		return true;
 		}
 		
 		return false;
 	}
}
