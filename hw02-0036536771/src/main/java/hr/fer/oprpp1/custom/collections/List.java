package hr.fer.oprpp1.custom.collections;

public interface List extends Collection {

	/** Gets element at given index 
	 * 
	 * @param index of the element we want to get
	 * @return element at given index
	 */
	Object get(int index);
	
	
	/** Inserts element at given position 
	 * 
	 * @param value of an object we want to add to collection
	 * @param position we want to put given object in
	 */
	void insert(Object value, int position);
	
	
	/** Finds and returns index of an given object 
	 * 
	 * @param value of an object we want to find
	 * @return index of given value, -1 if it doesn't exist
	 */
	int indexOf(Object value);
	
	
	/** Removes element at a given index 
	 * 
	 * @param index of an element we want to remove
	 * @exception IndexOutOfBoundsException if given index
	 * 		is lower than zero or greater or equals than size of collection
	 */
	void remove(int index);
	
}
