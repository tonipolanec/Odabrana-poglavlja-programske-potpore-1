package hr.fer.oprpp1.custom.scripting.nodes;

import hr.fer.oprpp1.custom.scripting.elems.*;

public class ForLoopNode extends Node {

	private ElementVariable variable;

	private Element startExpression;
	private Element endExpression;
	private Element stepExpression; // can be NULL
	
	public ForLoopNode() {
		// TODO Auto-generated constructor stub
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
}
