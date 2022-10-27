package hr.fer.oprpp1.custom.collections;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import hr.fer.oprpp1.custom.collections.SimpleHashtable.TableEntry;

public class SimpleHashtableTest {
	
	SimpleHashtable<String, Integer> hashtable;
	
	public void init() {
		hashtable = new SimpleHashtable<>(2);
		hashtable.put("one", 1); 
		hashtable.put("two", 2); 
		hashtable.put("three", 3); 
	}
	
	@Test
	public void testPutNormal() {
		init();	
		
		assertEquals(3, hashtable.get("three"));
		
		// Returns null when putting new key in
		assertEquals(null, hashtable.put("four", 4));
	}
	@Test
	public void testPutAlreadyInKey() {
		init();	
		hashtable.put("one", 111);
		
		assertEquals(111, hashtable.get("one"));
		
		// Returns old value
		assertEquals(111, hashtable.put("one", 1));
	}
	@Test
	public void testPutNullKey() {
		init();	
		
		assertThrows(NullPointerException.class, () -> hashtable.put(null, 0));
	}
	@Test
	public void testPutNullValue() {
		init();	
		
		assertDoesNotThrow(() -> hashtable.put("null", null));
	}
	
	
	@Test
	public void testGetNonExistent() {
		init();	
		
		if (hashtable.get("fivehundredfiftyfive") != null)
			fail();	
	}
	@Test
	public void testGetNormal() {
		init();	
		
		assertEquals(1, hashtable.get("one"));
	}
	
	@Test
	public void testRemoveNormal() {
		init();
		
		hashtable.remove("one");
		if (hashtable.get("one") != null)
			fail();
		
		if (hashtable.remove("two") != 2)
			fail();
	}
	@Test
	public void testRemoveNonExistent() {
		init();
		
		if (hashtable.remove("fivehundredfiftyfive") != null)
			fail();
	}
	@Test
	public void testRemoveNull() {
		init();
		
		if (hashtable.remove(null) != null)
			fail();
	}
	
	
	@Test
	public void testContainsKeyNull() {
		init();
		
		assertFalse(hashtable.containsKey(null));
	}
	@Test
	public void testContainsKeyFalse() {
		init();
		
		assertFalse(hashtable.containsKey("fivehundredfiftyfive"));
	}
	@Test
	public void testContainsKeyTrue() {
		init();
		
		assertTrue(hashtable.containsKey("three"));
	}
	
	
	@Test
	public void testContainsValueNullFalse() {
		init();
		
		assertFalse(hashtable.containsValue(null));
	}
	@Test
	public void testContainsValueNullTrue() {
		init();
		
		hashtable.put("null", null);
		assertTrue(hashtable.containsValue(null));
	}
	@Test
	public void testContainsValueFalse() {
		init();
		
		assertFalse(hashtable.containsValue(555));
	}
	@Test
	public void testContainsValueTrue() {
		init();
		
		assertTrue(hashtable.containsValue(3));
	}
	
	
	@Test
	public void testSize() {
		init();
		
		assertEquals(3, hashtable.size());
		
		// When we put but we don't add new entry
		hashtable.put("one", 111);
		assertEquals(3, hashtable.size());
		
		// When we put but we add new entry
		hashtable.put("four", 4);
		assertEquals(4, hashtable.size());
		
		// Removing non-existent key (size stays the same)
		hashtable.remove(hashtable.remove("null"));
		assertEquals(4, hashtable.size());
		
		// Removing entry
		hashtable.remove(hashtable.remove("one"));
		assertEquals(3, hashtable.size());
	}
	
	
	@Test 
	public void testToString() {
		init();
		String expected = "[two=2, one=1, three=3]";
		
		// It may break if in future hashCode is computed differently
		assertEquals(expected, hashtable.toString());
	}
	
	@Test 
	public void testToArray() {
		//init();
		hashtable = new SimpleHashtable<>(10);
		hashtable.put("one", 1); 
		hashtable.put("two", 2); 
		hashtable.put("three", 3); 	
		hashtable.put("four", 4); hashtable.put("five", 5);
		
		TableEntry<String, Integer>[] arr = hashtable.toArray();
		
		// Test length
		assertEquals(5, arr.length);
		assertEquals(hashtable.size(), arr.length);
		
		// It may break if in future hashCode is computed differently
		assertEquals(4, arr[1].getValue());
	}
	
	
	@Test
	public void testClear() {
		init();
		hashtable.clear();
		
		// Check if entry one=1 deleted
		assertNull(hashtable.get("one"));
		
		// Check if all entries deleted
		assertEquals(0, hashtable.size());
	}
	
	
	
	
	
	
	

}
