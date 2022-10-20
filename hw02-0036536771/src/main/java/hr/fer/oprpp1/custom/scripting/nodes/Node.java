package hr.fer.oprpp1.custom.scripting.nodes;

import hr.fer.oprpp1.custom.collections.ArrayIndexedCollection;

public class Node {

	/**<p> Collection of all children nodes. </p>*/
	private ArrayIndexedCollection children;
	
	public Node() {
	}
	
	/** Ads child node to children collection.
	 * @param child we want to add to children collection
	 */
	public void addChildNode(Node child) {
		if (children == null)
			children = new ArrayIndexedCollection();
		
		children.add(child);
	}
	
	/** Returns number of children directly beneath this node. 
	 * @return number of children
	 */
	public int numberOfChildren() { 
		return children.size();
	}
	
	/** Returns selected child by index 
	 * @param index of wanted child
	 * @exception IndexOutOfBoundsException if given index is lower than zero 
	 * 		or greater or equals than size of children collection
	 */
	public Node getChild(int index) {
		return (Node) children.get(index);
	}
	
	
	/** Returns string representation of node 
	 * @return string of node
	 */
	public String toString() {
		return null;
	}
	
	
}
