package hr.fer.oprpp1.custom.scripting.nodes;

import hr.fer.oprpp1.custom.scripting.elems.*;

public class EchoNode extends Node {

	private Element[] elements;
	private int size;
	
	public EchoNode() {
		elements = new Element[10];
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
			echoString += elements[i].toString() + " ";
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
	
	

}
