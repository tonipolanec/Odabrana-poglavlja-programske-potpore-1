package hr.fer.oprpp1.custom.collections;


/** Dictionary class, data structure with pairs [KEY, VALUE]
 * 
 * @author Toni Polanec
 *
 * @param <K>
 * @param <V>
 */
public class Dictionary<K, V> {

	private ArrayIndexedCollection<Pair<K, V>> aic;
	
	private static class Pair<K, V> {
		private K key;
		private V value;
		
		private Pair(K key, V value) {
			if (key == null)
				throw new IllegalArgumentException("Key cannot be null!");
			this.key = key;
			this.value = value;
		}

		
		public K getKey() {
			return key;
		}
		public V getValue() {
			return value;
		}
		public void setValue(V value) {
			this.value = value;
		}
		
		@Override
		public boolean equals(Object other) {
			if (!(other instanceof Pair<?,?>)) return false;
				
		    if (!((Pair<?,?>)other).getKey().equals(key)) return false;
		    
		    return true;			
		}
	}
	
	
	public Dictionary() {
		aic = new ArrayIndexedCollection<>();
	}
	
	/** Returns true if dictionary contains no pairs and false otherwise. 
	 * 
	 * @return <code>true</code> if dictionary has no pairs, <code>false</code> otherwise.
	 */
	public boolean isEmpty() {
		return aic.isEmpty();
	}
	
	/**<p> Returns the number of currently stored pairs in this dictionary. </p>*/
	public int size() {
		return aic.size();
	}
	
	/**<p> Removes all elements from this collection. </p>*/
	public void clear() {
		aic.clear();
	}
	
	
	
	/** Puts new pair in dictionary, if pair with that key exists overwrite value
	 * 
	 * @return old value if pair with that key existed, <code>null</code> otherwise
	 */
	public V put(K key, V value) {
		Pair<K,V> newPair = new Pair<>(key, value);
		
		// If key already exists overwrite value
		if (contains(key)) {
			int keyIndex = indexOf(key);
			
			V oldValue = aic.get(keyIndex).getValue();
			
			// Overwrite old value
			aic.get(keyIndex).setValue(value);
			
			return oldValue;
		
		} else {
			aic.add(newPair);
			
			return null;
		}
		
	}
	
	/** Gets pair in dictionary by key, if that key doesn't exist return <code>null</code>.
	 * 
	 * @return value of pair with that key, <code>null</code> if it doesn't exist
	 */
	public V get(Object key) {
		if (!contains(key)) return null;
		
		int keyIndex = indexOf(key);
		if (keyIndex == -1) return null;
		
		return aic.get(keyIndex).getValue();
	}
	
	
	/** Removes pair with given key and returns value of it.
	 *  If pair with that key didn't exist return <code>null</code>
	 *  
	 *  @param key of pair we want to remove
	 *  @return value of removed pair, <code>null</code> if it didn't exist
	 */
	public V remove(K key) {
		if (!contains(key)) return null;
		
		V value = get(key);
		aic.remove(indexOf(key));
		
		return value;
	}
	
	
	/**<p> Checks if given key exists in directory. </p>*/
	private boolean contains(Object key) {
		ElementsGetter<Pair<K,V>> getter = aic.createElementsGetter();
		
		while(getter.hasNextElement()) {
			Pair<K,V> element = getter.getNextElement();
			if(element.key == key) return true;
		}
		
		return false;
	}
	
	/**<p> Returns index of given key, -1 if it doesn't exist. </p>*/
	private int indexOf(Object key) {
		int index = 0;
		ElementsGetter<Pair<K,V>> getter = aic.createElementsGetter();
		while(getter.hasNextElement()) {
			Pair<K,V> element = getter.getNextElement();
			
			if(element.key == key) return index;
			index++;
		}
		
		return -1;
	}
	
	
	
	
	
	

}
