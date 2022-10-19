package hr.fer.oprpp1.custom.scripting.elems;

public class ElementConstantInteger extends Element {

	/**<p> Value of ElementConstantInteger </p>*/
	private int value;
	
	public ElementConstantInteger(int value) {
		this.value = value;
	}
	
	/** Returns string representation of private variable value 
	 * @return value
	 */
	@Override
	public String asText() {
		return value +"";
	}

	/** Returns variable name
	 *  @return name
	 */
	public int getValue() {
		return value;
	}

}
