package hr.fer.oprpp1.custom.scripting.nodes;

import hr.fer.oprpp1.custom.scripting.elems.*;

public class ForLoopNode extends Node {

	private ElementVariable variable;

	private Element startExpression;
	private Element endExpression;
	private Element stepExpression; // can be NULL
	
	public ForLoopNode(ElementVariable var, Element start, Element end, Element step) {
		variable = var;
		startExpression = start;
		endExpression = end;
		stepExpression = step;
		
	}
	public ForLoopNode(ElementVariable var, Element start, Element end) {
		this(var, start, end, null);
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
		String echoString = "{$ ";
		echoString += variable.toString() + " " + startExpression.toString() + " " + endExpression.toString();
		
		if (stepExpression != null)
			echoString += stepExpression.toString();
		
		
		return echoString + " $}";
	}
	
	
}
