package hr.fer.oprpp1.custom.scripting.nodes;

import hr.fer.oprpp1.custom.scripting.elems.*;
import hr.fer.oprpp1.custom.scripting.parser.SmartScriptParserException;

public class ForLoopNode extends Node {

	private ElementTag name;
	private ElementVariable variable;
	private Element startExpression;
	private Element endExpression;
	private Element stepExpression; // can be NULL
	
	public ForLoopNode(ElementTag name, ElementVariable var, Element start, Element end, Element step) {
		if(start instanceof ElementFunction || start instanceof ElementOperator)
			throw new SmartScriptParserException("Wrong FOR elements!");
		if(end instanceof ElementFunction || end instanceof ElementOperator)
			throw new SmartScriptParserException("Wrong FOR elements!");
		if(step != null)
			if(step instanceof ElementFunction || step instanceof ElementOperator)
				throw new SmartScriptParserException("Wrong FOR elements!");
		
		this.name = name;
		variable = var;
		startExpression = start;
		endExpression = end;
		stepExpression = step;
		
	}
	public ForLoopNode(ElementTag name, ElementVariable var, Element start, Element end) {
		this(name, var, start, end, null);
	}
	
	
	@Override
	public String toString() {
		String string = "{$ " + name.asText() + " ";
		string += variable.asText() + " ";
		
		if (startExpression instanceof ElementString)
			string += "\"" + startExpression.asText() + "\" ";
		else
			string += startExpression.asText() + " ";
		
		if (endExpression instanceof ElementString)
			string += "\"" + endExpression.asText() + "\" ";
		else
			string += endExpression.asText() + " ";
		
		
		if (stepExpression != null) {
			if (stepExpression instanceof ElementString)
				string += "\"" + stepExpression.asText() + "\" ";
			else
				string += stepExpression.asText() + " ";
		}
		
		string += " $}";
		
		for(int i=0; i<numberOfChildren(); i++) {
			string += getChild(i).toString() + "";
		}
		
		string += "{$END$}";
		
		return string;
	}
	
	
	@Override
 	public boolean equals(Object other) {
 		if(other instanceof ForLoopNode) {
 			ForLoopNode otherNode = (ForLoopNode) other;
	 		if (!super.equals(otherNode)) return false;
	 		
	 		if (!name.equals(otherNode.name)) 						return false;
	 		if (!variable.equals(otherNode.variable)) 				return false;
	 		if (!startExpression.equals(otherNode.startExpression)) return false;
	 		if (!endExpression.equals(otherNode.endExpression)) 	return false;
	 		
	 		
	 		if (stepExpression != null && otherNode.stepExpression != null)
	 			if (!stepExpression.equals(otherNode.stepExpression))	return false;
	 		
	 		if (startExpression == null)
	 			if (otherNode.startExpression != null) return false;
	 		if (otherNode.stepExpression == null)
	 			if (startExpression != null) return false;
	 		
	 		
	 		
	 		return true;
 		}
 		
 		return false;
 	}
	
	
}
