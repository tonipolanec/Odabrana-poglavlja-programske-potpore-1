package hr.fer.oprpp1.hw04.db;

/** Functional interface for field value getter with method <code>get(StudentRecord)</code>.
 * 
 * @author Toni Polanec
 */
@FunctionalInterface
public interface IFieldValueGetter {
	public String get(StudentRecord record);
}
