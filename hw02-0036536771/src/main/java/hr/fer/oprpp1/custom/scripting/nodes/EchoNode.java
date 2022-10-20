package hr.fer.oprpp1.custom.scripting.nodes;

import hr.fer.oprpp1.custom.scripting.elems.*;

public class EchoNode extends Node {

	private Element[] elements;
	private int size;
	
	public EchoNode() {
		elements = new Element[5];
		size = 0;
	}
	public EchoNode(Element... elems) {
		elements = elems;
		size = elems.length;
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
	
	/**<p> Adds element to array of elements </p>*/
 	public void addElement(Element e) {
		if (size < elements.length) {
			elements[size++] = e;
			
		} else if (size <= elements.length) {
			// Reallocating new bigger array
			Element[] newArray = new Element[elements.length*2];
			for(int i=0; i<size; i++) {
				newArray[i] = elements[i];
			}
			newArray[size++] = e;
			
			elements = newArray;
		}
		
	}
	
 	@Override
 	public boolean equals(Object other) {
 		if(other instanceof EchoNode) {
	 		EchoNode otherNode = (EchoNode) other;
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
