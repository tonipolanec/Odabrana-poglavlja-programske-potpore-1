package hr.fer.oprpp1.hw04.db;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class IComparisonOperatorsTest {

	IComparisonOperator oper;

	@Test
	public void testLess() {
		oper = ComparisonOperators.LESS;

		assertTrue(oper.satisfied("Ana", "Jasna"));
		assertFalse(oper.satisfied("Jasna", "Ana"));
	}

	@Test
	public void testGreater() {
		oper = ComparisonOperators.GREATER;

		assertFalse(oper.satisfied("Ana", "Jasna"));
		assertTrue(oper.satisfied("Jasna", "Ana"));
	}

	@Test
	public void testLessEquals() {
		oper = ComparisonOperators.LESS_OR_EQUALS;

		assertTrue(oper.satisfied("Ana", "Jasna"));
		assertTrue(oper.satisfied("Ana", "Ana"));
	}

	@Test
	public void testGreaterEquals() {
		oper = ComparisonOperators.GREATER_OR_EQUALS;

		assertTrue(oper.satisfied("Jasna", "Ana"));
		assertTrue(oper.satisfied("Ana", "Ana"));
	}

	@Test
	public void testEquals() {
		oper = ComparisonOperators.EQUALS;

		assertFalse(oper.satisfied("Jasna", "Ana"));
		assertTrue(oper.satisfied("Ana", "Ana"));
	}

	@Test
	public void testNotEquals() {
		oper = ComparisonOperators.NOT_EQUALS;

		assertTrue(oper.satisfied("Jasna", "Ana"));
		assertFalse(oper.satisfied("Ana", "Ana"));
	}

	@Test
	public void testLike() {
		oper = ComparisonOperators.LIKE;

		// beginning
		assertFalse(oper.satisfied("BAAAB", "*AA"));
		assertTrue(oper.satisfied("BBAAAA", "*AA"));

		// middle
		assertFalse(oper.satisfied("AAA", "AA*AA"));
		assertTrue(oper.satisfied("AAAA", "AA*AA"));
		assertTrue(oper.satisfied("AABBBAA", "AA*AA"));

		// end
		assertFalse(oper.satisfied("Zagreb", "Aba*"));
	}

}
