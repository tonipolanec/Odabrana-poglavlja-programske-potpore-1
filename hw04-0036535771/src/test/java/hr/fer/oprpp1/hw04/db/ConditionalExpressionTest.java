package hr.fer.oprpp1.hw04.db;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class ConditionalExpressionTest {

	
	
	@Test
	public void testConditionalExpressionFalse() {
		ConditionalExpression expr = new ConditionalExpression(
				FieldValueGetters.LAST_NAME,
				"Bos*",
				ComparisonOperators.LIKE );
		
		StudentRecord record = new StudentRecord("0036535771", "Polanec", "Toni", 4);
		
		boolean recordSatisfies = expr.getComparisonOperator().satisfied(
				expr.getFieldGetter().get(record),
				expr.getStringLiteral() );
		
		assertFalse(recordSatisfies);
	}
	
	@Test
	public void testConditionalExpressionTrue() {
		ConditionalExpression expr = new ConditionalExpression(
				FieldValueGetters.LAST_NAME,
				"Pol*ec",
				ComparisonOperators.LIKE );
		
		StudentRecord record = new StudentRecord("0036535771", "Polanec", "Toni", 4);
		
		boolean recordSatisfies = expr.getComparisonOperator().satisfied(
				expr.getFieldGetter().get(record),
				expr.getStringLiteral() );
		
		assertTrue(recordSatisfies);
	}

}
