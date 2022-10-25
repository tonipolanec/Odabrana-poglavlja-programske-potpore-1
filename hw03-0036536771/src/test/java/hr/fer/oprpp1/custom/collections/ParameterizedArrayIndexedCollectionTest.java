package hr.fer.oprpp1.custom.collections;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class ParameterizedArrayIndexedCollectionTest {

	ArrayIndexedCollection<String> aic;
	public void  init() {
		aic = new ArrayIndexedCollection<>();
		aic.add("jedan"); aic.add("dva"); aic.add("tri");
	}
	
//	@Test
//	public void testAddWrongType() {
//		aic.add(2);	//Compiler already doesn't let this go through
//	}
	
	@Test
	public void testContainsWrongType1() {
		init();
		assertFalse(aic.contains(3));
	}
	@Test
	public void testContainsWrongType2() {
		init();
		assertFalse(aic.contains(new ArrayIndexedCollection<Number>()));
	}
	
	@Test
	public void testContainsRightType() {
		init();
		assertTrue(aic.contains("jedan"));
	}
	
	@Test
	public void testToArrayNew() {
		init();
		String[] expected = {"jedan", "dva", "tri"};
		
		assertArrayEquals(expected, aic.toArray(new String[0]));	
	}
	
	
	
}
