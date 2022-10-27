package hr.fer.oprpp1.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class ParameterizedLinkedListCollectionTest {

	LinkedListIndexedCollection<String> llic;
	public void  init() {
		llic = new LinkedListIndexedCollection<>();
		llic.add("one"); llic.add("two"); llic.add("three");
	}
	
//	@Test
//	public void testAddWrongType() {
//		llic.add(2);	//Compiler already doesn't let this go through
//	}
	
	@Test
	public void testContainsWrongType1() {
		init();
		assertFalse(llic.contains(3));
	}
	@Test
	public void testContainsWrongType2() {
		init();
		assertFalse(llic.contains(new ArrayIndexedCollection<Number>()));
	}
	
	@Test
	public void testContainsRightType() {
		init();
		assertTrue(llic.contains("one"));
	}
	
	
	@Test
	public void testToArrayNew() {
		init();
		String[] expected = {"one", "two", "three"};
		
		assertArrayEquals(expected, llic.toArray(new String[0]));	
	}
	

}
