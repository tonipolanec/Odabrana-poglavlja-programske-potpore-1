package hr.fer.oprpp1.custom.collections;

import java.lang.reflect.Array;

/** Implementation of hashtable. 
 * 
 * @author Toni Polanec
 *
 * @param <K>
 * @param <V>
 */
public class SimpleHashtable<K, V> {

	/**<p> Table for entries. </p>*/
	private TableEntry<K, V>[] table;
	
	/**<p> Number of entries in hashtable. </p>*/
	private int size;

	
	public SimpleHashtable() {
		this(16);
	}
	
	@SuppressWarnings("unchecked")
	public SimpleHashtable(int initialCapacity) {
		if (initialCapacity < 1) throw new IllegalArgumentException();
		
		table = (TableEntry<K,V>[]) Array.newInstance((new TableEntry<K,V>()).getClass(), initialCapacity);	
		size = 0;
	}
	
	public static class TableEntry<K, V>{	
		
		private K key;
		private V value;
		private TableEntry<K,V> next;
		
		private TableEntry() {}
		
		private TableEntry(K key, V value) {
			if (key == null)
				throw new IllegalArgumentException("Key cannot be null!");
			this.key = key;
			this.value = value;
		}

		/**<p> Public getter for key. </p>*/
		public K getKey() {
			return key;
		}
		/**<p> Public getter for value. </p>*/
		public V getValue() {
			return value;
		}
		/**<p> Public setter for value. </p>*/
		public void setValue(V value) {
			this.value = value;
		}
		
	}
	
	/** Adds new entry in hashtable. 
	 * 
	 * @return <code>null</code> if key didn't already exist in collection or old value of given key
	 * @throws NullPointerException if given <code>null</code> as key
	 */
	public V put(K key, V value) {
		if (key == null) throw new NullPointerException();
		
		int slot = Math.abs(key.hashCode()) % table.length;
		
		// If key doesn't exist we don't need to overwrite
		if (!containsKey(key)) {
			
			if (fillRatio() >= 0.75) {
				doubleTableSize();
				slot = Math.abs(key.hashCode()) % table.length;
			}
			
			TableEntry<K,V> newEntry = new TableEntry<>(key, value);
			
			//Check if slot is empty
			if (table[slot] == null) {
				// Puts normally in
				table[slot] = newEntry;
				
			} else {
				// If it's not iterate through to the end of line and there add new entry
				TableEntry<K,V> prevEntry = table[slot];
				while(prevEntry.next != null) {
					prevEntry = prevEntry.next;		
				}		
				prevEntry.next = newEntry;
			}	
			
			size++;
			return null;
		
		// Entry with that key already exists, we need to overwrite and return old value
		} else {
			V oldValue = get(key);
			
			// We know in which slot old key is
			// We just need to iterate to the correct entry.
			TableEntry<K,V> oldEntry = table[slot];
			while(!oldEntry.key.equals(key)) {
				oldEntry = oldEntry.next;
			}
			
			// Overwrite old value
			oldEntry.setValue(value);
						
			return oldValue;
		}
	}
	
	/** Gets value from given key.
	 * 
	 * @param key we want value of
	 * @return value of key or <code>null</code> if given key doesn't exist in hashtable
	 */
	public V get(Object key) {
		if (key == null) return null;
		if (!containsKey(key)) return null;
		
		int slot = Math.abs(key.hashCode()) % table.length;
		TableEntry<K,V> entry = table[slot];
		
		while(!entry.key.equals(key)) {
			entry = entry.next;
		}
		
		return entry.getValue();
	}
	
	/**<p> Returns the number of entries in hashtable. </p>*/
	public int size() {
		return size;
	}
	
	/** Checks if given key is in hashtable or not.
	 * 
	 * @param key we want to check if it's in hashtable
	 * @return <code>true</code> if key exists, <code>false</code> otherwise
	 */
	public boolean containsKey(Object key) {
		if (key == null) return false;
		
		TableEntry<K,V> entry;
		for(int i=0; i<table.length; i++) {
			if (table[i] == null) continue;
			
			entry = table[i];
			
			while(entry != null) {
				if(entry.getKey().equals(key)) return true;
				
				entry = entry.next;
			}

		}
		
		return false;
	}
	
	/** Checks if given value is in hashtable or not.
	 * 
	 * @param value we want to check if it's in hashtable
	 * @return <code>true</code> if value exists, <code>false</code> otherwise
	 */
	public boolean containsValue(Object value){
		
		TableEntry<K,V> entry;
		for(int i=0; i<table.length; i++) {
			if (table[i] == null) continue;
			
			entry = table[i];
			
			while(entry != null) {
				if (value == null && entry.getValue() == null) return true;
				if(entry.getValue().equals(value)) return true;
				
				entry = entry.next;
			}

		}
		
		return false;
	}
	
	/** Removes entry with given key from hashtable.
	 * 
	 * @param key we want to remove
	 * @return value of removed entry, <code>null</code> if key doesn't exist (nothing removed)
	 */
	public V remove(Object key) {
		if (!containsKey(key)) return null;
		
		V value = get(key);
		TableEntry<K,V> entry;
		
		int slot = Math.abs(key.hashCode()) % table.length;
		
		// Only one entry in that slot
		if(table[slot].next == null) {
			table[slot] = null;
		
		} else {
		
			// Not the only one in slot
			entry = table[slot];
				
			// First one in slot is the one we want to remove
			if (entry.getKey().equals(key)) {
				table[slot] = entry.next;
			
			} else {
				
				TableEntry<K,V> prev = entry;
				entry = entry.next;
				
				// Iterate to entry with given key
				while(!entry.getKey().equals(key)) {
					prev = entry;
					entry = entry.next;
				}
				
				// Now in variable entry is entry we want to remove
				if (entry.next == null) {
					// Pointer from previous entry points to null (removed entry)
					prev.next = null;
				
				} else {
					// We point previous entry to the one after removed one
					prev.next = entry.next;
				}		
				
			}
		}
		
		
		size--;
		return value;
	}
	
	/**<p> Returns <code>true</code> if hashtable is empty (0 entries), <code>false</code> otherwise. </p>*/
	public boolean isEmpty() {
		return size == 0;
	}
	
	/**<p> Returns string representation of hashtable. </p>*/
	public String toString() {
		if (size == 0) return "[]";
		String string = "[";
		
		TableEntry<K,V> entry;
		for(int i=0; i<table.length; i++) {
			if (table[i] == null) continue;
			
			entry = table[i];
			
			while(entry != null) {
				string += entry.getKey().toString() + "=" + entry.getValue().toString() + ", ";
				entry = entry.next;
			}
		}
		
		string = string.substring(0, string.length() - 2); // Removes ", "
		string += "]";
		
		return string;		
	}
	
	
	/**<p> Returns array of all entries. </p>*/
	@SuppressWarnings("unchecked")
	public TableEntry<K,V>[] toArray(){
		TableEntry<K,V>[] arr = (TableEntry<K,V>[]) Array.newInstance((new TableEntry<K,V>()).getClass(), size);	
		int index = 0;
		
		TableEntry<K,V> entry;
		for(int i=0; i<table.length; i++) {
			if (table[i] == null) continue;
			
			entry = table[i];
			
			while(entry != null) {
				arr[index++] = entry;
				entry = entry.next;
			}
		}
		
		return arr;
	}
	
	
	
	public void clear() {
		TableEntry<K, V>[] arr = toArray();
		for(int i=0; i<arr.length; i++) {
			remove(arr[i].getKey());
		}		
		size = 0;
	}
	
	
	
	/**<p> Returns fill ratio of hashtable. </p>*/
	private double fillRatio() {
		double DSize = size;
		return DSize / table.length;
	}
	
	/**<p> Doubles the internal table size. </p>*/
	@SuppressWarnings("unchecked")
	private void doubleTableSize() {
		
		TableEntry<K, V>[] array = toArray();
		
		int oldTableSize = table.length;
		
		table = (TableEntry<K,V>[]) Array.newInstance((new TableEntry<K,V>()).getClass(), oldTableSize*2);
		size = 0;
		
		for(int i=0; i<array.length; i++) {
			put(array[i].getKey(), array[i].getValue());
		}

	}
	
	
	
	
	
	
	
}
