package hr.fer.oprpp1.hw04.db;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for query parser.
 * 
 * @author Toni Polanec
 */
public class QueryParser {

	private List<ConditionalExpression> expressions;
	private boolean directQuery;

	public QueryParser(String query) {
		expressions = new ArrayList<>();
		directQuery = false;
		parseQuery(query);
	}

	/**
	 * Checks if this query is direct query.
	 * 
	 * @return <code>true</code> if this is direct query, <code>false</code>
	 *         otherwise
	 */
	public boolean isDirectQuery() {
		return directQuery;
	}

	/**
	 * Returns JMBAG that was asked in query.
	 * 
	 * @throws IllegalStateException if this isn't direct query
	 */
	public String getQueriedJMBAG() {
		if (directQuery)
			throw new IllegalStateException("This was not direct query!");

		return expressions.get(0).getStringLiteral();
	}

	/**
	 * Gets list of conditional expressions from query.
	 * 
	 * @return list of conditional expressions
	 */
	public List<ConditionalExpression> getQuery() {
		return expressions;
	}

	/**
	 * Parse query and disect it into elements for further processing.
	 * 
	 * @throws ParserException if error occurred
	 */
	private void parseQuery(String query) {

		if (!query.startsWith("query "))
			throw new ParserException("Invalid command!");

		query = query.substring(5); // removes "query"
		int index = 0;
		int maxIndex = query.length()-1;
		
		while(index <= maxIndex) {
			skipWhitespaces(query, index, maxIndex);
			
			String attribute = getAtribute(query, index, maxIndex);
			index += attribute.length();
		}
		
		
		
		
		
		
		
		

	}
	
	private String getAtribute(String query, int index, int maxIndex) {
		int startIndex = index;
		while(index <= maxIndex) {
			char c = query.toCharArray()[index];
			
			if (c=='\t' || c==' ') {
				index++;
				continue;
			}
		}
		return index;
	}

	/**<p> Skips all whitespaces up to next element. </p>*/
	private int skipWhitespaces(String query, int index, int maxIndex) {
		while(index <= maxIndex) {
			char c = query.toCharArray()[index];
			
			if (c=='\t' || c==' ') {
				index++;
				continue;
			}
		}
		return index;
	}

}
