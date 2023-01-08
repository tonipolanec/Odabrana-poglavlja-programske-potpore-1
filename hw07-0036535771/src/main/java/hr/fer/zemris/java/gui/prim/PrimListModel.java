package hr.fer.zemris.java.gui.prim;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/**
 * List model implementation for list with prime numbers.
 * 
 * @author Toni Polanec
 */
public class PrimListModel implements ListModel<Integer>{
	
	/** List for storing prime numbers. */
	List<Integer> primNumbers;
	/** Last generated prime number. */
	private int last;
	
	/** List for Listener for change in lists. */
	private List<ListDataListener> listeners = new ArrayList<>();

	public PrimListModel() {		
		primNumbers = new ArrayList<>();
		primNumbers.add(1);
		last = 1;
	}
	
	/**
	 * Generation of next prime number.
	 */
	public void next() {
		int current = last +1;
		
		boolean isDivisible = false;
		while(true) {
			isDivisible = false;
			for(int i=2; i<current; i++) {
				if(current % i == 0) isDivisible = true;
			}
			if(!isDivisible) break;
			current++;
		}
		
		
		int pos = primNumbers.size();
		primNumbers.add(current);
		last = current;
		
		ListDataEvent event = new ListDataEvent(this, ListDataEvent.INTERVAL_ADDED, pos, pos);
		for(ListDataListener l : listeners) {
			l.intervalAdded(event);
		}
	}
	
	public List<Integer> getList(){
		return primNumbers;
	}

	@Override
	public int getSize() {
		return primNumbers.size();
	}


	@Override
	public Integer getElementAt(int index) {
		return primNumbers.get(index);
	}

	@Override
	public void addListDataListener(ListDataListener l) {
		listeners.add(l);
	}

	@Override
	public void removeListDataListener(ListDataListener l) {
		listeners.remove(l);
	}


}
