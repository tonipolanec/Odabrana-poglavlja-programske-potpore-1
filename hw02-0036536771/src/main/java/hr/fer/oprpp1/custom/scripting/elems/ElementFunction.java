package hr.fer.oprpp1.custom.scripting.elems;

/**
 * Class for representing all function elements.
 * 
 * @author Toni Polanec
 */
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
		return "@" + name;
	}
	
	
	@Override
	public boolean equals(Element element) {
		if (element instanceof ElementFunction) {
			ElementFunction e = (ElementFunction) element;
			
			if(!name.equals(e.name)) return false;
			return true;
		}
		return false;
	}
}
