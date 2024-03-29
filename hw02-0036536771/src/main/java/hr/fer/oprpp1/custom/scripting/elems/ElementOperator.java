package hr.fer.oprpp1.custom.scripting.elems;

/**
 * Class for representing all operator elements.
 * 
 * @author Toni Polanec
 */
public class ElementOperator extends Element {

	/**<p> Symbol of a object ElementOperator </p>*/
	private String symbol;
	
	public ElementOperator(String symbol) {
		this.symbol = symbol;
	}
	
	/** Returns private variable symbol 
	 * @return symbol
	 */
	@Override
	public String asText() {
		return symbol;
	}
	
	@Override
	public boolean equals(Element element) {
		if (element instanceof ElementOperator) {
			ElementOperator e = (ElementOperator) element;
			
			if(!symbol.equals(e.symbol)) return false;
			return true;
		}
		return false;
	}

}
