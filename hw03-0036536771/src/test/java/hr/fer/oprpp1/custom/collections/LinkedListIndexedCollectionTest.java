package hr.fer.oprpp1.custom.collections;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class LinkedListIndexedCollectionTest {
	
	LinkedListIndexedCollection llic = new LinkedListIndexedCollection();

	// CONSTRUCTORS
	
	@Test
	public void testOtherCollection() {
		assertThrows(NullPointerException.class, () -> new LinkedListIndexedCollection(null));
	}
	
	
	// METHOD add(Object value)
	
	@Test
	public void testAddNull() {
		assertThrows(NullPointerException.class, () -> llic.add(null));
	}
	
	@Test
	public void testAddExample() {
		LinkedListIndexedCollection llic = new LinkedListIndexedCollection();
		llic.add(1);llic.add(2);
		Object[] expected = {1,2};
		
		assertArrayEquals(expected, llic.toArray());
	}
	
	
	// METHOD get(int index)
	
	@Test
	public void testGetIndexLowerThanZero() {
		assertThrows(IndexOutOfBoundsException.class, () -> llic.get(-1));
	}
	@Test
	public void testGetIndexHigherThanSize() {
		assertThrows(IndexOutOfBoundsException.class, () -> llic.get(llic.size()));
	}
	@Test 
	public void testGetExample() {
		LinkedListIndexedCollection llic = new LinkedListIndexedCollection();
		llic.add(1); llic.add(2); llic.add(3); llic.add(4);
		
		assertEquals(3, llic.get(2));
	}
	
	
	// METHOD clear()
	
	@Test
	public void testClear() {
		LinkedListIndexedCollection llic = new LinkedListIndexedCollection();
		llic.add(1); llic.add(2); llic.add(3); llic.add(4);
		llic.clear();
		
		assertEquals(0, llic.size());
	}
	
	
	// METHOD insert(Object value, int position)
	
	@Test
	public void testInsertNull() {
		assertThrows(NullPointerException.class, () -> llic.insert(null, 0));
	}
	@Test
	public void testInsertIndexLowerThanZero() {
		assertThrows(IndexOutOfBoundsException.class, () -> llic.insert(1, -1));
	}
	@Test
	public void testInsertIndexHigherThanSize() {
		assertThrows(IndexOutOfBoundsException.class, () -> llic.insert(1, llic.size()+1));
	}
	@Test 
	public void testInsertExamplePositionZero() {
		LinkedListIndexedCollection llic = new LinkedListIndexedCollection();
		llic.add(1); llic.add(2); llic.add(3); llic.add(4);
		llic.insert(5, 0);
		
		Object[] expected = {5,1,2,3,4};
		assertArrayEquals(expected, llic.toArray());
	}
	@Test 
	public void testInsertExamplePositionSize() {
		LinkedListIndexedCollection llic = new LinkedListIndexedCollection();
		llic.add(1); llic.add(2); llic.add(3); llic.add(4);
		llic.insert(5, 4);
		
		Object[] expected = {1,2,3,4,5};
		assertArrayEquals(expected, llic.toArray());
	}
	@Test 
	public void testInsertExamplePositionMiddle() {
		LinkedListIndexedCollection llic = new LinkedListIndexedCollection();
		llic.add(1); llic.add(2); llic.add(3); llic.add(4);
		llic.insert(5, 2);
		
		Object[] expected = {1,2,5,3,4};
		assertArrayEquals(expected, llic.toArray());
	}
	
	
	
	// METHOD indexOf(Object value)
	
	@Test
	public void testIndexOfNull() {
		assertEquals(-1, llic.indexOf(null));
	}
	
	@Test
	public void testIndexOfExampleFirst() {
		LinkedListIndexedCollection llic = new LinkedListIndexedCollection();
		llic.add(1); llic.add(2); llic.add(3); llic.add(4);
		
		assertEquals(0, llic.indexOf(1));
	}
	@Test
	public void testIndexOfExampleMiddle() {
		LinkedListIndexedCollection llic = new LinkedListIndexedCollection();
		llic.add(1); llic.add(2); llic.add(3); llic.add(4);
		
		assertEquals(2, llic.indexOf(3));
	}
	@Test
	public void testIndexOfExampleLast() {
		LinkedListIndexedCollection llic = new LinkedListIndexedCollection();
		llic.add(1); llic.add(2); llic.add(3); llic.add(4);
		
		assertEquals(3, llic.indexOf(4));
	}
	
	
	
	// METHOD remove(int index)
	
	@Test
	public void testRemoveIndexLowerThanZero() {
		assertThrows(IndexOutOfBoundsException.class, () -> llic.remove(-1));
	}
	@Test
	public void testRemoveIndexHigherThanSize() {
		assertThrows(IndexOutOfBoundsException.class, () -> llic.remove(llic.size()));
	}
	@Test 
	public void testRemoveFirst(){
		LinkedListIndexedCollection llic = new LinkedListIndexedCollection();
		llic.add(1); llic.add(2); llic.add(3); llic.add(4);
		llic.remove(0);
		
		Object[] expected = {2,3,4};
		assertArrayEquals(expected, llic.toArray());		
	}
	@Test 
	public void testRemoveMiddle(){
		LinkedListIndexedCollection llic = new LinkedListIndexedCollection();
		llic.add(1); llic.add(2); llic.add(3); llic.add(4);
		llic.remove(2);
		
		Object[] expected = {1,2,4};
		assertArrayEquals(expected, llic.toArray());
	}
	@Test 
	public void testRemoveLast(){
		LinkedListIndexedCollection llic = new LinkedListIndexedCollection();
		llic.add(1); llic.add(2); llic.add(3); llic.add(4);
		llic.remove(3);
		
		Object[] expected = {1,2,3};
		assertArrayEquals(expected, llic.toArray());
	}
	
	
	// METHOD toArray()
	
	@Test
	public void testToArray() {
		LinkedListIndexedCollection llic = new LinkedListIndexedCollection();
		llic.add(2); llic.add(3); llic.add(4); llic.add(1);
		
		Object[] expected = {2,3,4,1};
		assertArrayEquals(expected, llic.toArray());
		
	}
	
	

}
