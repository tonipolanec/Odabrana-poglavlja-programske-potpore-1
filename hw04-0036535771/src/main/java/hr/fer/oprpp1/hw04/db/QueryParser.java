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
		if (!directQuery)
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

		Lexer lexer = new Lexer(query);
		List<Token> tokens = lexer.getTokens();
		
		if (!getPossibleSizes().contains(tokens.size()))
			throw new ParserException("Invalid query! Wrong number of tokens!");
		
		
		// Only one expression, maybe a direct query
		if (tokens.size() == 3) {
			ConditionalExpression expr = getExpression(tokens, 0);
			expressions.add(expr);
			
			// Check for direct query
			if (expr.getFieldGetter() == FieldValueGetters.JMBAG &&
				expr.getComparisonOperator() == ComparisonOperators.EQUALS) {
				directQuery = true;
			}
	
		// More than 3 tokens aka more then 1 expression
		} else {
			int index = 0;
			int maxIndex = tokens.size();
			
			while (index <= maxIndex-3) {
				ConditionalExpression expr = getExpression(tokens, index);
				expressions.add(expr);
				
				index += 3;
				
				if (index < maxIndex)
					if (tokens.get(index).getType() != TokenType.OPERATOR_AND)
						throw new ParserException("Invalid query! Expressions must be separated by AND operator!");
					
				index++;
				
			}
			
			
		}
		
		
		

	}
	
	
	/** Returns possible sizes for tokens list. */
	private List<Integer> getPossibleSizes(){
		List<Integer> sizes = new ArrayList<>();
		for(int i=1; i<1000; i++) {
			sizes.add(3*i+(i-1));
		}
		return sizes;
	}
	
	
	/** Gets immediately next expression from tokens from given index.
	 * 
	 * @param tokens
	 * @param index
	 * @return ConditionalExpression
	 */
	private ConditionalExpression getExpression(List<Token> tokens, int index) {
		
		// Attribute (index)
		IFieldValueGetter fieldGetter;
		
		// String literal (index+2)
		String stringLiteral;
		
		// Operator (index+1)
		IComparisonOperator comparisonOperator;
		
		
		// Getting fieldGetter
		switch (tokens.get(index).getValue()) {
		case "jmbag": {
			fieldGetter = FieldValueGetters.JMBAG;
			break;
		}
		case "firstName": {
			fieldGetter = FieldValueGetters.FIRST_NAME;
			break;
		}
		case "lastName": {
			fieldGetter = FieldValueGetters.LAST_NAME;
			break;
		}
		default:
			throw new ParserException("Invalid attribute: " + tokens.get(index).getValue());
		}
		
		
		
		// Getting comparison operator
		switch (tokens.get(index+1).getValue()) {
		case "<": {
			comparisonOperator = ComparisonOperators.LESS;
			break;
		}
		case "<=": {
			comparisonOperator = ComparisonOperators.LESS_OR_EQUALS;
			break;
		}
		case ">": {
			comparisonOperator = ComparisonOperators.GREATER;
			break;
		}
		case ">=": {
			comparisonOperator = ComparisonOperators.GREATER_OR_EQUALS;
			break;
		}
		case "=": {
			comparisonOperator = ComparisonOperators.EQUALS;
			break;
		}
		case "!=": {
			comparisonOperator = ComparisonOperators.NOT_EQUALS;
			break;
		}
		case "LIKE": {
			comparisonOperator = ComparisonOperators.LIKE;
			break;
		}
		default:
			throw new ParserException("Invalid operator: " +  tokens.get(index+1).getValue());
		}
		
		
		// Getting string literal
		String stringInToken = tokens.get(index+2).getValue();
		stringLiteral = stringInToken.substring(1, stringInToken.length()-1);
		
		

		return new ConditionalExpression(fieldGetter, stringLiteral, comparisonOperator);
	}
	
	
	
	
	

}
