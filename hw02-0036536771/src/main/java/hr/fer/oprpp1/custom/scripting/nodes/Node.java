package hr.fer.oprpp1.custom.scripting.nodes;

import hr.fer.oprpp1.custom.collections.ArrayIndexedCollection;
import hr.fer.oprpp1.custom.scripting.parser.SmartScriptParserException;

/** Parent class for all nodes in document model.
 * 
 * @author Toni Polanec
 */
public class Node {

	/**<p> Collection of all children nodes. </p>*/
	private ArrayIndexedCollection children;
	
	
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
		if (children == null) return 0;
		
		return children.size();
	}
	
	/** Returns selected child by index.
	 * @param index of wanted child
	 * @exception SmartScriptParserException if given index is lower than zero 
	 * 		or greater or equals than size of children collection
	 */
	public Node getChild(int index) {
		try {
			return (Node) children.get(index);
		} catch (IndexOutOfBoundsException ex) {
			throw new SmartScriptParserException("Error with getting node child!");
		}
		
	}
	
	
	/** Returns string representation of a node.
	 * @return string
	 */
	public String toString() {
		return null;
	}

	/** Checks if this and given node are equal.
	 * @param node we want to compare
	 * @return <code>true</code> if equal, <code>false</code> otherwise
	 */
	public boolean equals(Node node) {
		if(node instanceof Node) {
			Node otherNode = (Node) node;
			
			if (numberOfChildren() != otherNode.numberOfChildren()) return false;
			
			for(int i=0; i<numberOfChildren(); i++) {
				if (!getChild(i).equals(otherNode.getChild(i)))
					return false;
			}
			return true;
		}
		
		return false;
	}
	
	
}
