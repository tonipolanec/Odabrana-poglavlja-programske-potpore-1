package hr.fer.zemris.java.gui.layouts;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager2;

public class CalcLayout implements LayoutManager2 {

	int gap;

	public CalcLayout(int gap) {
		this.gap = gap;
	}
	public CalcLayout() {
		this(0);
	}

	@Override
	public void addLayoutComponent(String name, Component comp) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void removeLayoutComponent(Component comp) {

	}

	@Override
	public Dimension preferredLayoutSize(Container parent) {
		return null;
	}

	@Override
	public Dimension minimumLayoutSize(Container parent) {
		return null;
	}
	
	@Override
	public Dimension maximumLayoutSize(Container target) {
		return null;
	}
	
	@Override
	public void layoutContainer(Container parent) {

	}

	@Override
	public void addLayoutComponent(Component comp, Object constraints) {
		if(comp == null || constraints == null)
			throw new NullPointerException();
		if (!(constraints instanceof RCPosition) && !(constraints instanceof String))
			throw new IllegalArgumentException();
		
		RCPosition position;
		if(constraints instanceof String) {
			try {
				position = RCPosition.parse((String)constraints);
			} catch(CalcLayoutException e) {
				throw new IllegalArgumentException();
			}
		} else {
			position = (RCPosition) constraints;
		}
		
		

		
	}
	
	@Override
	public float getLayoutAlignmentX(Container target) {
		return 0;
	}

	@Override
	public float getLayoutAlignmentY(Container target) {
		return 0;
	}

	@Override
	public void invalidateLayout(Container target) {

	}

	public static void main(String[] args) {

	}

}
