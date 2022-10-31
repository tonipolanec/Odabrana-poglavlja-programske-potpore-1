package hr.fer.oprpp1.hw04.db;

/** Function interface for filter with method <code>accepts(StudentRecord)</code>.
 * 
 * @author Toni Polanec
 */
@FunctionalInterface
public interface IFilter {
	public boolean accepts(StudentRecord record);
}
