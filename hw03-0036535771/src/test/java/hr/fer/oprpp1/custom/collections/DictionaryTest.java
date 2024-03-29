package hr.fer.oprpp1.custom.collections;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class DictionaryTest {

	Dictionary<Integer, String> dict;
	public void init() {
		dict = new Dictionary<>();
		dict.put(1, "one");
		dict.put(2, "two");
		dict.put(3, "three");
	}
	
	@Test
	public void testIsEmptyTrue() {
		dict = new Dictionary<>();
		assertTrue(dict.isEmpty());
	}
	@Test
	public void testIsEmptyFalse() {
		init();	
		assertFalse(dict.isEmpty());
	}
	
	@Test
	public void testSizeZero() {
		dict = new Dictionary<>();
		assertEquals(0, dict.size());
	}
	@Test
	public void testSizeThree() {
		init();
		assertEquals(3, dict.size());
	}
	
	@Test
	public void testClear() {
		init();
		dict.clear();
		assertEquals(0, dict.size());
	}
	
	@Test
	public void testPutNew() {
		init();
		dict.put(4, "four");
		
		assertEquals("four", dict.get(4));	
	}
	@Test
	public void testPutOverwrite() {
		init();
		dict.put(2, "TWO");
		
		assertEquals("TWO", dict.get(2));	
	}
	
	@Test
	public void testGetNull() {
		init();
		
		assertNull(dict.get(555));
	}
	@Test
	public void testGetNormal() {
		init();
		
		assertEquals("one", dict.get(1));
	}
	
	@Test
	public void testRemoveNull() {
		init();
		
		assertNull(dict.remove(555));
	}
	@Test
	public void testRemoveNormal() {
		init();
		dict.remove(2);
		
		assertNull(dict.get(2));
		assertEquals(2, dict.size());
	}
	@Test
	public void testRemoveReturn() {
		init();
		String value = dict.remove(2);
		
		assertEquals("two", value);
	}
	
	
	
}
