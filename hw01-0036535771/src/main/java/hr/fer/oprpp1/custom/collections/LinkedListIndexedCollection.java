package hr.fer.oprpp1.custom.collections;

public class LinkedListIndexedCollection extends Collection {
	// Duplicate elements are allowed, storage of null references is not allowed.
	
	private static class ListNode{
		Object value;
		ListNode prev;
		ListNode next;
		
	}
	
	int size;
	ListNode first;
	ListNode last;

	public LinkedListIndexedCollection() {
		// first=last=null
		first = null;
		last = null;
		size = 0;
	}
	public LinkedListIndexedCollection(Collection other) {
		// Copy elements from other
	}
	
	
	/** Returns the number of currently stored objects in this collections. */
	public int size() {
		return size;
	}
	
	
	/** Adds the given object into this collection at the end of collection. */
	public void add(Object value) {
		if (value == null)
			throw new NullPointerException();
		
		ListNode node = new ListNode();
		node.value = value;
		
		// If first node
		if (size == 0) {
			first = node;
			last = node;
			
		}else {
			last.next = node;
			node.prev = last;
			
			last = node;
		}
		
		size++;
	}
	
	
	/** Returns the object that is stored in linked list at position index. */
	public Object get(int index) {
		if (index < 0 || index >= size) 
			throw new IndexOutOfBoundsException();
		
		ListNode currNode = new ListNode();
		if (index < size/2) {
			// Start from beginning
			currNode = first;
			for(int i=0; i<index; i++) {
				currNode = currNode.next;
			}
		}else {
			// Start from  end
			currNode = last;
			for(int i=size-1; i>index; i--) {
				currNode = currNode.prev;
			}
		}
		
		return currNode.value;
	}
	
	
	/** Removes all elements from this collection. */
	public void clear() {
		first = null;
		last = null;
	}
	
	
	/** Inserts (does not overwrite) the given value at the given position in linked-list. */
	public void insert(Object value, int position) {
		if (position < 0 || position > size) 
			throw new IndexOutOfBoundsException();
				
	}
	
	
	/** Searches the collection and returns the index of the first occurrence of the given value 
	 * or -1 if the value is not found. */
	public int indexOf(Object value) {

	}
	
	
	
	
	/** Returns true only if the collection contains given value, as determined by equals method. */
	public boolean contains(Object value) {
		// Value CAN be null
		// Implement it here to always return false. 
		return false;
	}
	
	
	/** Returns true only if the collection contains given value as determined by 
	 * equals method and removes one occurrence of it 
	 * (in this class it is not specified which one). */
	public boolean remove(Object value) {
		// Implement it here to always return false.
		return false;
	}
	
	
	/** Allocates new array with size equals to the size of this collections, 
	 * fills it with collection content and returns the array. */
	public Object[] toArray() {
		// Object array = new Object[this.size()];
		// Fills the array with collection content
		// return array;
		
		// Implement it here to throw UnsupportedOperationException.
		throw new UnsupportedOperationException();
	}
	
	
	
	/** Method calls processor.process(.) for each element of this collection. 
	 * The order in which elements will be sent is undefined in this class. */
	public void forEach(Processor processor) {

	}
	
	
	

}
