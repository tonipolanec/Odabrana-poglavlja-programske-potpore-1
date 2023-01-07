package hr.fer.zemris.java.gui.charts;

import java.util.List;

public class BarChart {
	
	/** List of XYValues. */
	private List<XYValue> values;
	
	/** Text on x axis. */
	private String xDescription;
	/** Text on y axis. */
	private String yDescription;
	
	/** Minimal y that is showing on graph. */
	private double minY;
	/** Maximal y that is showing on graph. */
	private double maxY;
	/** Space between two values on y axis. */
	private double spaceY;
	

	public BarChart(List<XYValue> values, String xDescription, String yDescription, double minY, double maxY, double spaceY) {
		this.values = values;
		this.xDescription = xDescription;
		this.yDescription = yDescription;
		this.minY = minY;
		this.maxY = maxY;
		this.spaceY = spaceY;
		
		for(XYValue value : values) 
			if (value.getY() < minY) throw new IllegalArgumentException("Y value in list is less then minimal y!");
		
	}
	
	
	
	

}
