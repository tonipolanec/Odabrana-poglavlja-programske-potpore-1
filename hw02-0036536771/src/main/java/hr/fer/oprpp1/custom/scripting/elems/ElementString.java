package hr.fer.oprpp1.custom.scripting.elems;

/**
 * Class for representing all string elements.
 * 
 * @author Toni Polanec
 */
public class ElementString extends Element {

	/**<p> Value of ElementString </p>*/
	private String value;
	
	public ElementString(String value) {
		this.value = value;
	}
	
	/** Returns string representation of private variable value. 
	 * @return value
	 */
	@Override
	public String asText() {
		// We need to again escape characters like "
		String escapedString = value.replace("\"", "\\\"");
		
		return escapedString;
	}
	
	/**<p> Returns private variable value </p>*/
	public String getValue() {
		return value;
	}
	
	/**<p> Static function for printing all characters in string. </p>*/
	public static String getPlainText(String string) {
		return unEscapeString(string);
	}
	

	/** Prints all characters in string, even '\n', '\r' and similar 
	 * @param string we want to print
	 * 
	 * @author Vlad (https://stackoverflow.com/users/469220/vlad)
	 * @see https://stackoverflow.com/questions/7888004/how-do-i-print-escape-characters-in-java
	 */
	private static String unEscapeString(String string) {
		StringBuilder sb = new StringBuilder();
	    for (int i=0; i<string.length(); i++)
	        switch (string.charAt(i)){
	            case '\n': sb.append("\\n"); break;
	            case '\t': sb.append("\\t"); break;
	            case '\r': sb.append("\\r"); break;
	            default: sb.append(string.charAt(i));
	        }
	    return sb.toString();
	}
	
	@Override
	public boolean equals(Element element) {
		if (element instanceof ElementString) {
			ElementString e = (ElementString) element;
			
			if(!value.equals(e.value)) return false;
			return true;
		}
		return false;
	}

}
