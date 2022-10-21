package hr.fer.oprpp1.custom.scripting.nodes;

import hr.fer.oprpp1.custom.scripting.elems.*;

/** Class for Echo nodes in document model.
 * 
 * @author Toni Polanec
 */
public class EchoNode extends Node {

	/**<p> Array of elements in this node </p>*/
	private Element[] elements;
	
	/**<p> Number of elements in this node </p>*/
	private int size;
	
	public EchoNode() {
		elements = new Element[5];
		size = 0;
	}


	/** Adds element to array of elements
	 * @param element we want to add
	 */
 	public void addElement(Element element) {
		if (size < elements.length) {
			elements[size++] = element;
			
		} else if (size <= elements.length) {
			// Reallocating new bigger array
			Element[] newArray = new Element[elements.length*2];
			for(int i=0; i<size; i++) {
				newArray[i] = elements[i];
			}
			newArray[size++] = element;
			
			elements = newArray;
		}
		
	}
 	
 	/**<p> Returns number of elements in node </p>*/
 	public int getSize() {
 		return size;
 	}
 	
 	@Override
	public String toString() {
		String echoString = "{$= ";
		
		for(int i=0; i<size; i++) {
			if (elements[i] instanceof ElementString)
				echoString += "\"" + elements[i].asText() + "\" ";
			else
				echoString += elements[i].asText() + " ";
		}
		
		return echoString + "$}";
	}
	
 	@Override
 	public boolean equals(Node node) {
 		if(node instanceof EchoNode) {
	 		EchoNode otherNode = (EchoNode) node;
	 		
	 		if (!super.equals(otherNode)) return false;
	 		if (size != otherNode.size) return false;
	 		
	 		for(int i=0; i<size; i++) {
	 			if(!elements[i].equals(otherNode.elements[i])) return false;
	 		}
	 		
	 		return true;
 		}
 		
 		return false;
 	}
	

}
