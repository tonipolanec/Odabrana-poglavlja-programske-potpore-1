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
		addAll(other);
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
		size = 0;
	}
	
	
	/** Inserts (does not overwrite) the given value at the given position in linked-list. */
	public void insert(Object value, int position) {
		if (position < 0 || position > size) 
			throw new IndexOutOfBoundsException();
		
		ListNode node = new ListNode();
		node.value = value;
		
		ListNode currNode = new ListNode();
		ListNode nextNode;
		for(int i=0; i<position; i++) {
			currNode = currNode.next;
		}
		nextNode = currNode.next;
		
		currNode.next = node;
		node.prev = currNode;
		node.next = nextNode;
		nextNode.prev = node;
		
		size++;				
	}
	
	
	/** Searches the collection and returns the index of the first occurrence of the given value 
	 * or -1 if the value is not found. */
	public int indexOf(Object value) {
		if (value == null)
			return -1;
		
		ListNode node = first;
		
		for(int i=0; i<size; i++) {
			if (node.value.equals(value)) 
				return i;
			node = node.next;
		}
		
		return -1;
	}
	
	
	
	
	/** Returns true only if the collection contains given value, as determined by equals method. */
	public boolean contains(Object value) {
		ListNode node = first;
		
		for(int i=0; i<size; i++) {
			if (node.value.equals(value)) 
				return true;
			node = node.next;
		}
		
		return false;
	}
	
	
	
	/** Removes element at specified index from collection.*/
	public void remove(int index) {
		if (index < 0 || index >= size) 
			throw new IndexOutOfBoundsException();
		
		
		if (index == 0) {
			first = first.next;
			first.prev = null;

		}else if (index == size-1) {
			last = last.prev;
			last.next = null;
		
		}else {	
			ListNode node = first;
			for(int i=0; i<index; i++) {
				node = node.next;
			}
			ListNode prevNode = node.prev;
			ListNode nextNode = node.next;
			
			prevNode.next = nextNode;
			nextNode.prev = prevNode;
		}
				
		size--;
	}
	
	
	/** Allocates new array with size equals to the size of this collections, 
	 * fills it with collection content and returns the array. */
	public Object[] toArray() {
		ListNode node = first;
		Object[] array = new Object[this.size()];
		
		for(int i=0; i<size; i++) {
			array[i] = node.value;
			node = node.next;
		}
				
		return array;
	}
	
	
	
	/** Method calls processor.process(.) for each element of this collection. 
	 * The order in which elements will be sent is undefined in this class. */
	public void forEach(Processor processor) {
		ListNode node = first;
		
		for(int i=0; i<size; i++) {
			processor.process(node.value);
			node = node.next;
		}	
	}
	
	
	

}
