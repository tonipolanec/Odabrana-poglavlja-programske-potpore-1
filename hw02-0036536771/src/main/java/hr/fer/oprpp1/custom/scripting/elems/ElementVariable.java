package hr.fer.oprpp1.custom.scripting.elems;

public class ElementVariable extends Element {

	/**<p> Name of a object ElementVariable </p>*/
	private String name;
	
	public ElementVariable(String name) {
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
