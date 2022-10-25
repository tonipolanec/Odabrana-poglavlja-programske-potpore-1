package hr.fer.oprpp1.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class ParameterizedCollectionsTest {

	class P implements Processor<String> {
		public void process(String o) {
				;//System.out.println(o + "O");
		}
	};
//	Processor<Integer> p = new P(); // Doesn't work
	Processor<String> p = new P(); // Works
	
	@Test
	public void testProcessor() {
		ArrayIndexedCollection<String> aic = new ArrayIndexedCollection<>();
		aic.add("jedan"); aic.add("dva"); aic.add("tri");
		aic.forEach(p);
		
		assertEquals("dva", aic.get(1));
	}
	
	
	ObjectStack<Double> stack = new ObjectStack<>();
	
	@Test
	public void testStack() {
//		stack.push("string"); // Doesn't work
		stack.push(21.1); // Works
		stack.push(11.11);
		
		assertEquals(11.11, stack.pop());
		assertEquals(1, stack.size());
	}
	
	

	class T implements Tester<String> {
		public boolean test(String o) {
				return true;
		}
	};
	
	
	
	
	
	
	
	
	
}
