package hr.fer.oprpp1.custom.collections;

import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

/**
 * <p>Implementation of linked list collection.</p>
 * <p>Duplicate elements are allowed, storage of null references is not allowed.</p>
 * 
 * @author Toni Polanec
 * 
 * @see hr.fer.oprpp1.custom.collections.Collection
 * @see hr.fer.oprpp1.custom.collections.ArrayIndexedCollection
 */
public class LinkedListIndexedCollection<E> implements List<E> {
	
	/** <p>Current numbers of elements in collection.</p>*/
	int size;
	
	/** <p>Reference to the first node of the linked list.</p>*/
	ListNode<E> first;
	
	/** <p>Reference to the last node of the linked list.</p>*/
	ListNode<E> last;
	
	/** <p>Number used to check if collection was modified in mean time.</p>*/
	long modificationCount;
	
	
	/**<p>Local class for individual nodes in collection.</p>*/
	private static class ListNode<E>{
		/**<p>Value of object we want to store.</p>*/
		E value;
		
		/**<p>Pointer to previous list node.</p>*/
		ListNode<E> prev;
		
		/**<p>Pointer to next list node.</p>*/
		ListNode<E> next;
	}
	
	/**<p>Initializes empty LinkedListIndexedCollection</p>
	 */
	public LinkedListIndexedCollection() {
		// first=last=null
		first = null;
		last = null;
		size = 0;
		modificationCount = 0;
	}
	
	/**Initializes LinkedListIndexedCollection and copies other collection.
	 * 
	 * @param other collection we want to copy in this new one
	 * @exception NullPointerException if given null collection to copy
	 */
	public LinkedListIndexedCollection(Collection<E> other) {
		if (other == null)
			throw new NullPointerException();
		
		addAll(other);
		modificationCount = 0;
	}
	
	
	/** Returns the number of currently stored objects in this collections. */
	@Override
	public int size() {
		return size;
	}
	
	
	/** Adds the given object into this collection at the end of collection.
	 *  
	 * @param value
	 * @exception NullPointerExcpetion if given null as an argument
	 * */
	@Override
	public void add(E value) {
		if (value == null)
			throw new NullPointerException();
		
		ListNode<E> node = new ListNode<>();
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
		modificationCount++;
	}

	/** Returns true only if the collection contains given value, as determined by equals method. 
	 * 
	 * @param value
	 * @return <code>true</code> if it is in collection,
	 * 		<code>false</code> otherwise 
	 */
	@Override
	public boolean contains(Object value) {
		if (value == null)
			return false;
		
		ListNode<E> node = first;
		
		for(int i=0; i<size; i++) {
			if (node.value.equals(value)) 
				return true;
			node = node.next;
		}
		
		return false;
	}

	/** Returns true only if the list contains given value as determined by 
	 * equals method and removes one occurrence of it. 
	 * 
	 * @param value we want to remove from linked list
	 * @return <code>true</code> if it was successfully removed, 
	 * 		<code>false</code> if it doesn't contain given value
	 */
	@Override
	public boolean remove(E value) {
		if (!contains(value))
			return false;
		
		remove(indexOf(value));
		return true;
	}

	/** Allocates new array with size equals to the size of this collections, 
	 * fills it with collection content and returns the array.
	 * 
	 * @return array of an collection
	 */
	@Override
	public Object[] toArray() {
		ListNode<E> node = first;
		Object[] array = new Object[this.size()];
		
		for(int i=0; i<size; i++) {
			array[i] = node.value;
			node = node.next;
		}
				
		return array;
	}
	
	/** Allocates new array with size equals to the size of this collections, 
	 * fills it with collection content and returns the array.
	 * 
	 * @return array of an collection of type E
	 */
	@Override
	@SuppressWarnings("unchecked")
	public E[] toArray(E[] a) {
		
		 if (a.length < size)
             return (E[]) Arrays.copyOfRange(toArray(), 0, size, a.getClass());
         System.arraycopy(toArray(), 0, a, 0, size);
         if (a.length > size)
             a[size] = null;
         return a;
		
	}



	/** Removes all elements from this collection. */
	@Override
	public void clear() {
		first = null;
		last = null;
		size = 0;
		modificationCount++;
	}
	

	/** Returns the object that is stored in linked list at position index. 
	 * 
	 * @param index
	 * @exception IndexOutOfBoundsException if given index is lower than zero 
	 * 		or greater or equals than size of collection
	 */
	public E get(int index) {
		if (index < 0 || index >= size) 
			throw new IndexOutOfBoundsException();
		
		ListNode<E> currNode = new ListNode<>();
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

	/** Inserts (does not overwrite) the given value at the given position in linked-list. 
	 * 
	 * @param value of an object we want to add to collection
	 * @param position we want to put given object in
	 * 
	 * @exception NullPointerException if given null as an argument
	 * @exception IndexOutOfBoundsException if given index is lower than zero or greater than size of collection
	 */
	public void insert(E value, int position) {
		if (value == null)
			throw new NullPointerException();
		if (position < 0 || position > size) 
			throw new IndexOutOfBoundsException();
		
		ListNode<E> node = new ListNode<>();
		node.value = value;
		
		if (position == 0) {
			first.prev = node;
			node.next = first;
			first = node;
			
			size++;
			modificationCount++;
		
		}else if (position == size) {
			// We are adding to the tail of collection so we can use add()
			add(value);
			
		}else {
			ListNode<E> currNode = first;
			ListNode<E> nextNode;
			for(int i=0; i<position-1; i++) {
				currNode = currNode.next;
			}
			nextNode = currNode.next;
			
			currNode.next = node;
			node.prev = currNode;
			node.next = nextNode;
			nextNode.prev = node;
	
			size++;
			modificationCount++;
		}

			
	}
	
	
	/** Searches the collection and returns the index of the first occurrence of the given value.
	 * 
	 * @param value
	 * @return index at which is given value or -1 if it doesn't exists in collection
	 */
	public int indexOf(E value) {
		if (value == null)
			return -1;
		
		ListNode<E> node = first;
		
		for(int i=0; i<size; i++) {
			if (node.value.equals(value)) 
				return i;
			node = node.next;
		}
		
		return -1;
	}
	
	
	
	
	/** Removes element at specified index from collection. 
	 * 
	 * @param index
	 * @exception IndexOutOfBoundsException if given index is lower than zero or greater or equals than size of collection
	 */
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
			ListNode<E> node = first;
			for(int i=0; i<index; i++) {
				node = node.next;
			}
			ListNode<E> prevNode = node.prev;
			ListNode<E> nextNode = node.next;
			
			prevNode.next = nextNode;
			nextNode.prev = prevNode;
		}
				
		size--;
		modificationCount++;
	}
	
	
	
	/** Creates and returns new ElementsGetter for LinkedListIndexedCollection
	 * 
	 * @return new ElementsGetter for collection*/
	@Override
	public ElementsGetter<E> createElementsGetter() {
		return new ElementsGetterLinkedList<E>(this);
	}
	
	
	/**
	 * Private static class used for getting elements from collection.
	 * 
	 * @author Toni Polanec
	 */
	private static class ElementsGetterLinkedList<E> implements ElementsGetter<E> {
		
		/**<p>Reference to upper class <code>LinkedListIndexedCollection</code> </p>*/
		LinkedListIndexedCollection<E> llic;
		
		/**<p>Local reference to node we are currently getting. </p>*/
		ListNode<E> currNode;
		
		/**<p>Copy of <code>modificationCount</code> at the time of defining <code>ElementsGetterArray</code> </p>*/
		long savedModificationCount;
		
		public ElementsGetterLinkedList(LinkedListIndexedCollection<E> coll) {
			llic = coll;
			currNode = coll.first;
			savedModificationCount = coll.modificationCount;
		}
		
		/** Checks if collection has more elements to get. 
		 * 
		 * @return <code>true</code> if more elements available, <code>false</code> otherwise
		 * @throws ConcurrentModificationException if collection was modified 
		 * 		and ElementsGetter refers to old collection
		 */
		public boolean hasNextElement() {
			if (savedModificationCount != llic.modificationCount)
				throw new ConcurrentModificationException();
			
			if (currNode == null) return false;
			
			return true;
		}
		
		/** Returns next object in collection.
		 * 
		 * @return element at the next index
		 * @throws NoSuchElementException if no more elements to get
		 * @throws ConcurrentModificationException if collection was modified
		 * 		and ElementsGetter refers to old collection
		 */
		public E getNextElement() {	
			if (savedModificationCount != llic.modificationCount)
				throw new ConcurrentModificationException();
			
			if(!hasNextElement()) throw new NoSuchElementException();
			
			ListNode<E> current = currNode;
			currNode = currNode.next;
			return current.value;			
		}
		
		
	}
	
	
	
	
	

}
