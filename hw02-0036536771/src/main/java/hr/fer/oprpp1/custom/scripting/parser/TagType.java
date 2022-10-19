package hr.fer.oprpp1.custom.scripting.parser;

public enum TagType {

	/**<p> Starts by letter and after follows zero or more letters, digits or underscores</p>
	 * <p> valid: A7_bb, counter, tmp_34 <br> invalid: _a21, 32, 3s_ee </p>
	 */
	VARIABLE,
	
	/**<p> Starts with @ after which follows a letter and after than
	 *  can follow zero or more letters, digits or underscores<p>
	 */
	FUNCTION,
	
	OPERATOR_PLUS,	// +
	OPERATOR_MINUS,	// -
	OPERATOR_MULTI,	// *
	OPERATOR_DIV,	// /
	OPERATOR_POW,	// ^
	
	/**<p> Valid tag names are “=”, or variable name. So = is valid tag name (but not valid variable name)</p>
	 * <p> valid: =, VARIABLE </p> 
	 */
	TAG
	
}
