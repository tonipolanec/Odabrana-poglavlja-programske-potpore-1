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
	
	@Override
	public boolean equals(Element element) {
		if (element instanceof ElementConstantDouble) {
			ElementConstantDouble e = (ElementConstantDouble) element;
			
			if(value != e.value) return false;
			return true;
		}
		return false;
	}

}
