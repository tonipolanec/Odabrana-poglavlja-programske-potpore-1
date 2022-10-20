package hr.fer.oprpp1.custom.scripting.nodes;

public class DocumentNode extends Node {

	public DocumentNode() {
		super();
	}
	
	
	@Override
	public String toString() {
		String docBody = "";
		for(int i=0; i<numberOfChildren(); i++) {
			docBody += getChild(i).toString();
		}
		
		return docBody;
	}

}
