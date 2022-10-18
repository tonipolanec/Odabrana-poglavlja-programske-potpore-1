package hr.fer.oprpp1.custom.collections;

@FunctionalInterface
public interface Tester {
	
	/** Tests object if it is acceptable.
	 * @param obj we want to test
	 * @return <code>true</code> if object is acceptable, <code>false</code> otherwise
	 */
	boolean test(Object obj);
	
}
