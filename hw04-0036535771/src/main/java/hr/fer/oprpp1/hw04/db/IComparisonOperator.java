package hr.fer.oprpp1.hw04.db;

/**
 * Functional interface for comparison operator with method
 * <code>satisfied(String, String)</code>.
 * 
 * @author Toni Polanec
 */
@FunctionalInterface
public interface IComparisonOperator {
	public boolean satisfied(String value1, String value2);
}
