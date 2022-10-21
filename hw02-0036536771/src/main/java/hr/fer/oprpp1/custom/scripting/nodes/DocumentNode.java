package hr.fer.oprpp1.custom.scripting.nodes;

/** Class for representing DocumentNode in document model.
 * 
 * @author Toni Polanec
 */
public class DocumentNode extends Node {
	
	
	@Override
	public String toString() {
		String docBody = "";
		for(int i=0; i<numberOfChildren(); i++) {
			docBody += getChild(i).toString();
		}
		
		return docBody;
	}
	

}
