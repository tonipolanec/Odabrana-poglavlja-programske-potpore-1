package hr.fer.oprpp1.custom.collections;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;


public class ArrayIndexedCollectionTest {
	
	ArrayIndexedCollection aic = new ArrayIndexedCollection();

	
	// CONSTRUCTORS
	
	@Test
	public void testInitialCapacity() {
		assertThrows(IllegalArgumentException.class, () -> new ArrayIndexedCollection(-1));
		
	}
	@Test
	public void testOtherCollection() {
		assertThrows(NullPointerException.class, () -> new ArrayIndexedCollection(null));
	}
	
	
	
	// METHOD add(Object value)
	
	@Test
	public void testAddNull() {
		assertThrows(NullPointerException.class, () -> aic.add(null));
	}
	@Test
	public void testAddWhenFull() {
		ArrayIndexedCollection aic = new ArrayIndexedCollection(2);
		aic.add(2); aic.add(4);
		aic.add(6);
		
		assertEquals(6, aic.get(2));
	}
	@Test
	public void testAddSizeIncrement() {
		ArrayIndexedCollection aic = new ArrayIndexedCollection();
		aic.add(2);
		
		assertEquals(1, aic.size());
	}
	
	
	
	// METHOD get(int index)
	
	@Test
	public void testGetIndexLowerThanZero() {
		assertThrows(IndexOutOfBoundsException.class, () -> aic.get(-1));
	}
	@Test
	public void testGetIndexHigherThanSize() {
		assertThrows(IndexOutOfBoundsException.class, () -> aic.get(aic.size()));
	}
	@Test
	public void testGetReturn() {
		ArrayIndexedCollection aic = new ArrayIndexedCollection();
		aic.add(2);
		assertEquals(2, aic.get(0));
	}
	
	
	// METHOD clear()
	
	@Test
	public void testClear() {
		ArrayIndexedCollection aic = new ArrayIndexedCollection();
		aic.add(2); aic.add(2); aic.add(2); aic.add(2);
		aic.clear();
		assertEquals(0, aic.size());
	}
	
	
	
	// METHOD insert(Object value, int position)
	
	@Test
	public void testInsertPositionLowerThanZero() {
		assertThrows(IndexOutOfBoundsException.class, () -> aic.insert(2, -1));
	}
	@Test
	public void testInsertPositionHigherThanSize() {
		assertThrows(IndexOutOfBoundsException.class, () -> aic.insert(2, aic.size()+1));
	}
	@Test
	public void testInsertOnSizePosition() {
		ArrayIndexedCollection aic = new ArrayIndexedCollection(2);
		aic.add(1); aic.add(2);
		aic.insert(3, 2);
		Object[] expected = {1,2,3};
		
		assertArrayEquals(expected, aic.toArray());
	}
	@Test
	public void testInsertOnFirstPositionWithEmptySpaces() {
		ArrayIndexedCollection aic = new ArrayIndexedCollection(5);
		aic.add(1); aic.add(2);
		aic.insert(3, 0);
		Object[] expected = {3,1,2};
		
		assertArrayEquals(expected, aic.toArray());
	}
	@Test
	public void testInsertOnMiddlePositionWithEmptySpaces() {
		ArrayIndexedCollection aic = new ArrayIndexedCollection(5);
		aic.add(1); aic.add(2);
		aic.insert(3, 1);
		Object[] expected = {1,3,2};
		
		assertArrayEquals(expected, aic.toArray());
	}
	@Test
	public void testInsertOnFirstPositionWithFullArray() {
		ArrayIndexedCollection aic = new ArrayIndexedCollection(2);
		aic.add(1); aic.add(2);
		aic.insert(3, 0);
		Object[] expected = {3,1,2};
		
		assertArrayEquals(expected, aic.toArray());
	}
	@Test
	public void testInsertOnMiddlePositionWithFullArray() {
		ArrayIndexedCollection aic = new ArrayIndexedCollection(2);
		aic.add(1); aic.add(2);
		aic.insert(3, 1);
		Object[] expected = {1,3,2};
		
		assertArrayEquals(expected, aic.toArray());
	}
	
	
	
	// METHOD indexOf(Object value)
	
	@Test
	public void testIndexOfValueNull(){
		assertEquals(-1, aic.indexOf(null));
	}
	@Test
	public void testIndexOfIfDuplicates(){
		ArrayIndexedCollection aic = new ArrayIndexedCollection();
		aic.add(2); aic.add(1); aic.add(1);
		assertEquals(1, aic.indexOf(1));
	}
	@Test
	public void testIndexOfIfNotFound(){
		ArrayIndexedCollection aic = new ArrayIndexedCollection();
		aic.add(1); aic.add(2);
		assertEquals(-1, aic.indexOf(555));
	}
	
	
	
	// METHOD remove(int index)
	
	@Test
	public void testRemoveIndexLowerThanZero() {
		assertThrows(IndexOutOfBoundsException.class, () -> aic.remove(-1));
	}
	@Test
	public void testRemoveIndexHigherThanSize() {
		assertThrows(IndexOutOfBoundsException.class, () -> aic.get(aic.size()));
	}
	@Test
	public void testRemoveSizeDecrement() {
		ArrayIndexedCollection aic = new ArrayIndexedCollection(3);
		aic.add(1); aic.add(2); aic.add(3);
		aic.remove(0);
		
		assertEquals(2, aic.size());
	}
	@Test
	public void testRemoveFromFirstPosition() {
		ArrayIndexedCollection aic = new ArrayIndexedCollection(3);
		aic.add(1); aic.add(2); aic.add(3);
		aic.remove(0);
		Object[] expected = {2,3};
		
		assertArrayEquals(expected, aic.toArray());
	}
	@Test
	public void testRemoveFromMiddlePosition() {
		ArrayIndexedCollection aic = new ArrayIndexedCollection(3);
		aic.add(1); aic.add(2); aic.add(3);
		aic.remove(1);
		Object[] expected = {1,3};
		
		assertArrayEquals(expected, aic.toArray());
	}
	@Test
	public void testRemoveFromLastPosition() {
		ArrayIndexedCollection aic = new ArrayIndexedCollection(3);
		aic.add(1); aic.add(2); aic.add(3);
		aic.remove(2);
		Object[] expected = {1,2};
		
		assertArrayEquals(expected, aic.toArray());
	}
	
	
	
	// METHOD toArray()
	
	@Test
	public void testToArray() {
		ArrayIndexedCollection aic = new ArrayIndexedCollection(3);
		aic.add(1); aic.add(2); aic.add(3);
		aic.insert(5, 1);
		Object[] expected = {1,5,2,3};
		
		assertArrayEquals(expected, aic.toArray());
	}
	
	
	
	
	
	
}
