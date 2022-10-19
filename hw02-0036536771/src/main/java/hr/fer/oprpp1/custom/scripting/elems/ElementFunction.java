package hr.fer.oprpp1.custom.scripting.elems;

public class ElementFunction extends Element {

	/**<p> Name of a object ElementFunction </p>*/
	private String name;
	
	public ElementFunction(String name) {
		this.name = name;
	}
	
	/** Returns private variable name 
	 * @return name
	 */
	@Override
	public String asText() {
		return name;
	}
	
	/** Returns variable name
	 *  @return name
	 */
	public String getName() {
		return name;
	}
}
