package hr.fer.oprpp1.custom.scripting.elems;

public class ElementConstantDouble extends Element {

	/**<p> Value of ElementConstantDouble </p>*/
	private double value;
	
	public ElementConstantDouble(double value) {
		this.value = value;
	}
	
	/** Returns string representation of private variable value 
	 * @return value
	 */
	@Override
	public String asText() {
		return value +"";
	}

	/** Returns variable value
	 *  @return value
	 */
	public double getValue() {
		return value;
	}

}
