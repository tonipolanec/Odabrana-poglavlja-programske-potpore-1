package hr.fer.oprpp1.custom.scripting.nodes;

import hr.fer.oprpp1.custom.scripting.elems.*;

public class EchoNode extends Node {

	private Element[] elements;
	
	public EchoNode(Element... elems) {
		elements = elems;
	}
	
	
	@Override
	public String toString() {
		String echoString = "{$= ";
		
		for(int i=0; i<elements.length; i++) {
			echoString += elements[i].toString() + " ";
		}
		
		return echoString + "$}";
	}
	
	
	

}
