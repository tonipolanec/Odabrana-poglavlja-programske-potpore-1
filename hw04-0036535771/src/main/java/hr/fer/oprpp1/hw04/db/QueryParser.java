package hr.fer.oprpp1.hw04.db;

import java.util.ArrayList;
import java.util.List;

/** Class for query parser.
 * 
 * @author Toni Polanec
 */
public class QueryParser {
	
	

	public QueryParser(String query) {
		parseQuery(query);
	}
	
	/** Checks if this query is direct query.
	 * 
	 * @return <code>true</code> if this is direct query, <code>false</code> otherwise
	 */
	public boolean isDirectQuery() {
		
		return false;
	}
	
	/** Returns JMBAG that was asked in query.
	 * 
	 * @throws IllegalStateException if this isn't direct query
	 */
	public String getQueriedJMBAG() {
		
		
		return "";
	}
	
	/** Gets list of conditional expressions from query.
	 * 
	 * @return list of conditional expressions
	 */
	public List<ConditionalExpression> getQuery(){
		
		return new ArrayList<ConditionalExpression>();
	}
	
	
	/**<p> Parse query and disect it into elements for further processing.</p>*/
	private void parseQuery(String query) {
		// TODO Auto-generated method stub
		
	}

}
