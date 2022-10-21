package hr.fer.oprpp1.custom.scripting.elems;

/**
 * Parent class of all elements.
 * 
 * @author Toni Polanec
 */
public class Element {

	/**<p> Returns an empty String. </p>*/
	public String asText() {
		return "";
	}
	
	/** Checks if this and given element are equal.
	 * @param element we want to compare
	 * @return <code>true</code> if equal, <code>false</code> otherwise
	 */
	public boolean equals(Element element) {
		throw new UnsupportedOperationException("Error at Element equals()!");
	}

}
