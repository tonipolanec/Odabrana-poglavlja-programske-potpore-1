package hr.fer.oprpp1.hw04.db;

/** 
 * Class for all comparison operators with static final variables: <br>
 * <code>LESS</code>, <code>LESS_OR_EQUALS</code>, <code>GREATER</code>, <code>GREATER_OR_EQUALS</code>, 
 * <code>EQUALS</code>, <code>NOT_EQUALS</code>, <code>LIKE</code>
 * 
 * @author Toni Polanec
 */
public class ComparisonOperators {

	
	public static final IComparisonOperator LESS = 
			(value1, value2) -> value1.compareTo(value2) < 0 ? true : false;

	public static final IComparisonOperator LESS_OR_EQUALS = 
			(value1, value2) -> value1.compareTo(value2) <= 0 ? true : false;
			

	public static final IComparisonOperator GREATER = 
			(value1, value2) -> value1.compareTo(value2) > 0 ? true : false;

	public static final IComparisonOperator GREATER_OR_EQUALS = 
			(value1, value2) -> value1.compareTo(value2) >= 0 ? true : false;
	
			
	public static final IComparisonOperator EQUALS = 
			(value1, value2) -> value1.compareTo(value2) == 0 ? true : false;
		
	public static final IComparisonOperator NOT_EQUALS = 
			(value1, value2) -> value1.compareTo(value2) == 0 ? false : true;
			
	public static final IComparisonOperator LIKE = (value1, value2) -> {
		int indexOfWildcard = value2.indexOf('*');
		
		// Checks for multiple *
		if (indexOfWildcard != value2.lastIndexOf('*'))
			throw new StudentRecordException("Multiple wildcards!");
		
		if (indexOfWildcard == -1)
			return value1.compareTo(value2) == 0 ? false : true;
		
		
		// Wildcard at the beginning
		if (indexOfWildcard == 0) {
			return value1.endsWith(value2.substring(1));
		
		// Wildcard at the end
		} else if (indexOfWildcard == value2.length()-1) {
			return value1.startsWith(value2.substring(0, value2.length()-1));
		
		// Wildcard in the middle
		} else {
			String prefix = value2.substring(0, indexOfWildcard);
			String sufix = value2.substring(indexOfWildcard+1);
			
			
			boolean prefixGood = value1.startsWith(prefix);
			boolean sufixGood = value1.endsWith(sufix);
			
			return prefixGood && sufixGood && (prefix.length() + sufix.length() <= value1.length());
		}
			
		
	};
			
}
