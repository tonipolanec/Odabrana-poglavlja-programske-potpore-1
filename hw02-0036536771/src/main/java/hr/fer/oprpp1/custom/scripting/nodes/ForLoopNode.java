package hr.fer.oprpp1.custom.scripting.nodes;

import hr.fer.oprpp1.custom.scripting.elems.*;

public class ForLoopNode extends Node {

	private ElementTag name;
	private ElementVariable variable;
	private Element startExpression;
	private Element endExpression;
	private Element stepExpression; // can be NULL
	
	public ForLoopNode(ElementTag name, ElementVariable var, Element start, Element end, Element step) {
		this.name = name;
		variable = var;
		startExpression = start;
		endExpression = end;
		stepExpression = step;
		
	}
	public ForLoopNode(ElementTag name, ElementVariable var, Element start, Element end) {
		this(name, var, start, end, null);
	}

	public ElementTag getName() {
		return name;
	}
	
	public ElementVariable getVariable() {
		return variable;
	}

	public Element getStartExpression() {
		return startExpression;
	}

	public Element getEndExpression() {
		return endExpression;
	}

	public Element getStepExpression() {
		return stepExpression;
	}
	
	
	@Override
	public String toString() {
		String string = "{$ " + name.asText() + " ";
		string += variable.asText() + " " + startExpression.asText() + " " + endExpression.asText();
		
		if (stepExpression != null)
			string += " " + stepExpression.asText();
		string += " $}\n";
		
		for(int i=0; i<numberOfChildren(); i++) {
			string += getChild(i).toString() + "";
		}
		
		string += "{$END$}\n";
		
		return string;
	}
	
	
}
