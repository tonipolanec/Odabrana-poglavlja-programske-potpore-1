package hr.fer.oprpp1.hw04.db;

import java.util.List;

/**
 * Accept records which satisfy given query.
 * 
 * @author Toni Polanec
 */
public class QueryFilter implements IFilter {

	List<ConditionalExpression> expressions;

	public QueryFilter(List<ConditionalExpression> expressions) {
		this.expressions = expressions;
	}

	@Override
	public boolean accepts(StudentRecord record) {
				
		for (ConditionalExpression expr : expressions) {
			
			String value = expr.getFieldGetter().get(record);
			String literal  = expr.getStringLiteral();
			IComparisonOperator operator = expr.getComparisonOperator();
		
			if (!operator.satisfied(value, literal)) {
				return false;
			}
		}
		
		return true;
		
		
	}


}
