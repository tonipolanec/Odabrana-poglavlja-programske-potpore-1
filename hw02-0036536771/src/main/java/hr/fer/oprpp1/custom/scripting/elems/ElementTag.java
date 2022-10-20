package hr.fer.oprpp1.custom.scripting.elems;

public class ElementTag extends Element {

	/**<p> Name of a object ElementTag </p>*/
	private String name;
	
	public ElementTag(String name) {
		this.name = name.toLowerCase();
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
	
	@Override
	public boolean equals(Element element) {
		if (element instanceof ElementTag) {
			ElementTag e = (ElementTag) element;
			
			if(!name.equals(e.name)) return false;
			return true;
		}
		return false;
	}

}
