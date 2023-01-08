package hr.fer.zemris.java.gui.prim;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ListModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

public class PrimListModel implements ListModel<Integer>{
	
	List<Integer> primNumbers;
	private int last;
	
	private List<ListDataListener> listeners = new ArrayList<>();

	public PrimListModel() {		
		primNumbers = new ArrayList<>();
		primNumbers.add(1);
		last = 1;
	}
	
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
	
	
	
	public static void main(String[] args) {
	
		
//		PrimListModel list = new PrimListModel();
//		list.next();
//		list.next();
//		list.next();
//		list.next();
//		
//		for(Integer n : list.getList()) 
//			System.out.println(n);
	}

}
