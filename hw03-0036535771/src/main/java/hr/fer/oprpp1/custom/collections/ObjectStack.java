package hr.fer.oprpp1.custom.collections;

/**
 * Implementation of LIFO stack.
 * 
 * @see hr.fer.oprpp1.custom.collections.ArrayIndexedCollection
 * @author Toni Polanec
 * @param <E>
 */
public class ObjectStack<E> {
	
	/**<p>ArrayIndexedCollection we use to implement stack.</p>*/
	ArrayIndexedCollection<E> aic;

	/**<p> Initializes new ArrayIndexedCollection.</p>*/
	public ObjectStack() {
		aic = new ArrayIndexedCollection<>();
	}

	/** Returns true if stack is empty, false otherwise.*/
	public boolean isEmpty() {
		return aic.isEmpty();
	}
	
	/** Returns the number of currently stored objects in this collections. */
	public int size() {
		return aic.size();
	}
	
	/** Push value on top of the stack.
	 * @param value
	 * @exception NullPointerException if given null as an argument
	 */
	public void push(E value) {
		aic.add(value);
	}
	
	/** Pops the object at top of the stack.
	 * @exception EmptyStackException if stack is empty
	 * @return object at the top of the stack
	 */
	public E pop() {
		E poppedObject;
		try {
			poppedObject = aic.get(aic.size()-1);
			aic.remove(aic.size()-1);
		} catch (IndexOutOfBoundsException ex) {
			throw new EmptyStackException();
		}
		
		return poppedObject;
	}
	
	/** Peek at the object at top of the stack, it doesn't delete it from stack.
	 * @exception EmptyStackException if stack is empty
	 * @return object at the top of the stack
	 */
	public E peek() {
		E poppedObject;
		try {
			poppedObject = aic.get(aic.size()-1);
		} catch (IndexOutOfBoundsException ex) {
			throw new EmptyStackException();
		}
		
		return poppedObject;	
	}
	
	/** Removes all elements from stack. */
	public void clear() {
		aic.clear();
	}
	
	
	
}
