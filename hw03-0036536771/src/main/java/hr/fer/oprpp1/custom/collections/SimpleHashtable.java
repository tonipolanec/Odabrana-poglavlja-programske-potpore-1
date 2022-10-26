package hr.fer.oprpp1.custom.collections;

/** 
 * @author Toni Polanec
 *
 * @param <K>
 * @param <V>
 */
public class SimpleHashtable<K, V> {

	private TableEntry<K, V>[] table;
	private int size;
	
	
	public SimpleHashtable() {
		this(16);
	}
	
	@SuppressWarnings("unchecked")
	public SimpleHashtable(int initialCapacity) {
		if (initialCapacity < 1) throw new IllegalArgumentException();
		
		table = (TableEntry<K,V>[])new Object[initialCapacity];	
	}
	
	public static class TableEntry<K, V>{	
		
		private K key;
		private V value;
		private TableEntry<K,V> next;
		
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
	
	
	
	
	
	
	
	
}
