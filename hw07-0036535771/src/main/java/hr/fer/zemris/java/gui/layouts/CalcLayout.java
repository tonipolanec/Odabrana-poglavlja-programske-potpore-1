package hr.fer.zemris.java.gui.layouts;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager2;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * CalcLayout represents a layout used for making calculator applications.
 * 
 * @author Toni Polanec
 */
public class CalcLayout implements LayoutManager2 {
	
    /** Number of rows in layout. */
    private static final int ROWS = 5;
    /** Number of columns in layout. */
    private static final int COLUMNS = 7;
	
	/** Map in which will be stored all components of layout. */
	private Map<RCPosition, Component> components;
	
	/** Gap (in pixels) between rows and columns. */
	private int gap;
	
	/** Creates CalcLayout instance with given gap. 
	 * @param gap in pixels
	 */
	public CalcLayout(int gap) {
		if (gap < 0) throw new CalcLayoutException("Gap cannot be negative!");
		
		this.gap = gap;
		components = new HashMap<>();
	}
	
	/** Creates CalcLayout instance with gap equals 0. */
	public CalcLayout() {
		this(0);
	}

	@Override
	public void addLayoutComponent(String name, Component comp) {
		throw new UnsupportedOperationException();
	}

	/**
     * Removes the specified component from the layout.
     * @param comp the component to be removed
     */
	@Override
	public void removeLayoutComponent(Component comp) {
		Objects.requireNonNull(comp, "The given component cannot be null!");
		components.values().remove(comp);
	}

    /**
     * Calculates the preferred size dimensions for the specified
     * container, given the components it contains.
     *
     * @param  parent the container to be laid out
     * @return the preferred dimension for the container
     */
	@Override
	public Dimension preferredLayoutSize(Container parent) {
		return getSizes(parent, "PREFERRED");
	}

    /**
     * Calculates the minimum size dimensions for the specified
     * container, given the components it contains.
     *
     * @param  parent the component to be laid out
     * @return the minimum dimension for the container
     */
	@Override
	public Dimension minimumLayoutSize(Container parent) {
		return getSizes(parent, "MIN");
	}
	
	/**
     * Calculates the maximum size dimensions for the specified container,
     * given the components it contains.
     *
     * @param  target the target container
     * @return the maximum size of the container
     */
	@Override
	public Dimension maximumLayoutSize(Container target) {
		return getSizes(target, "MAX");
	}
	
	/**
     * Lays out the specified container.
     * @param parent the container to be laid out
     */
	@Override
	public void layoutContainer(Container parent) {
		Insets ins = parent.getInsets();
		
		Dimension parentDimension = parent.getSize();
		
		double usableWidth = parentDimension.width - ins.left - ins.right;
		double usableHeight = parentDimension.height - ins.top - ins.bottom;
		
		int[] componentsWidth = getComponentsSize(usableWidth, COLUMNS);
		int[] componentsHeight = getComponentsSize(usableHeight, ROWS);
		
		for(Map.Entry<RCPosition, Component> entry : components.entrySet()) {
			RCPosition position = entry.getKey();
			int row = position.getRow();
			int column = position.getColumn();
			
			Component component = entry.getValue();
			
			if(row == 1 && column == 1) {
				int width = 0;
				for(int i=1; i<=5; i++) width += componentsWidth[i];
				component.setBounds(ins.left, ins.top, width + gap*4, componentsHeight[1]);
			
			} else {
				int widthOffset = ins.left;
				for(int i=1; i<column; i++) widthOffset += componentsWidth[i] + gap;
				int heightOffset = ins.top;
				for(int i=1; i<row; i++) heightOffset += componentsHeight[i] + gap;
				
				component.setBounds(widthOffset, heightOffset, componentsWidth[column], componentsHeight[row]);
			}
						
		}
	
	}

	/** 
	 * Calculates how much space each element takes up and returns it as an array.
	 * 
	 * @param space we have
	 * @param number of elements
	 * @return array of component dimensions
	 */
    private int[] getComponentsSize(double space, int number) {
    	int[] array = new int[number+1];
    	
    	space -= (number-1) * gap;
		
    	int normal = (int)Math.floor(space / number);
    	int residue = (int)space - normal*number;
    	
    	if(residue == 0) {
    		for (int i = 1; i <= number; i++) 
    			array[i] = normal;
    		return array;
    	}
    	
    	for (int i = 1; i <= number; i++) {
    		array[i] = (int)Math.round(space * i * 1. / number);
    		for (int j = i - 1; j >= 1; j--) 
    			array[i] -= array[j];
    	}
    	
    	return array;
	}

	/**
     * Adds the specified component to the layout, using the specified
     * constraint object.
     * 
     * @param comp the component to be added
     * @param constraints  where/how the component is added to the layout.
     */
	@Override
	public void addLayoutComponent(Component comp, Object constraints) {
		Objects.requireNonNull(comp, "Given component cannot be null!");
		Objects.requireNonNull(constraints, "Given constraint cannot be null!");
		if (!(constraints instanceof RCPosition) && !(constraints instanceof String))
			throw new IllegalArgumentException("Wrong argument types!");
		
		RCPosition position;
		if(constraints instanceof String) {
			try {
				position = RCPosition.parse((String)constraints);
			} catch(CalcLayoutException e) {
				throw new IllegalArgumentException();
			}
		} else 
			position = (RCPosition) constraints;
		
		int row = position.getRow();
		int column = position.getColumn();
		
		if(row < 1 || row > 5) 
			throw new CalcLayoutException("Number of rows cannot must be between 1 and 5!");
		if(column < 1 || column > 7) 
			throw new CalcLayoutException("Number of columns must be between 1 and 7!");
		if(row == 1 && (column > 1 && column <6)) 
			throw new CalcLayoutException("Cannot access that field!");
		if(components.containsKey(position)) 
			throw new CalcLayoutException("Cannot put more than 1 component on same position!");
		
		components.put(position, comp);
		
	}
	
	@Override
	public float getLayoutAlignmentX(Container target) {
		return 0;
	}

	@Override
	public float getLayoutAlignmentY(Container target) {
		return 0;
	}

    /**
     * Invalidates the layout, indicating that if the layout manager
     * has cached information it should be discarded.
     * @param  target the target container
     */
	@Override
	public void invalidateLayout(Container target) {
		if(target == null)
			throw new CalcLayoutException("Given container cannot be null!");
	}

	/**
	 * Returns calculated dimension of container
	 * 
	 * @param parent container
	 * @param type of size we need [minimum, maximum, preferred]
	 * @return dimension of container in given type
	 */
	private Dimension getSizes(Container parent, String type) {
        Dimension d = null;
        double compWidth = 0, compHeight = 0;
        
        for(Map.Entry<RCPosition, Component> entry : components.entrySet()) {
        	RCPosition position = entry.getKey();			
			Component component = entry.getValue();
			
			if (component.isVisible()) {
            	switch(type) {
            		case "MIN":
            			d = component.getMinimumSize();
            			break;
            		case "MAX":
            			d = component.getMaximumSize();
            			break;
            		default: 
            			d = component.getPreferredSize();	
            	}
            	if(d != null) {
            		if(position.equals(new RCPosition(1, 1))) 
            			compWidth = Math.max(compWidth, (d.getWidth() - 4*gap) / 5);	
					else 
        				compWidth = Math.max(compWidth, d.getWidth());
        			
            		compHeight = Math.max(compHeight, d.getHeight());
            	}
            }	
        }
                
        Insets ins = parent.getInsets();
        int parentWidth = ins.left + ins.right + (int)compWidth * COLUMNS + gap * (COLUMNS-1);
        int parentHeight = ins.top + ins.bottom + (int)compHeight * ROWS + gap * (ROWS-1);
        
        return new Dimension(parentWidth, parentHeight);  
    }
	
	
	
	
	
}
