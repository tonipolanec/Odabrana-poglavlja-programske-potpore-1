package hr.fer.zemris.java.gui.charts;

import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import hr.fer.zemris.java.gui.calc.Calculator;

public class BarChartDemo extends JFrame {
	
	private BarChart model;
	
	public BarChartDemo() {
		model = new BarChart(
				Arrays.asList(
						new XYValue(1,8), new XYValue(2,20), new XYValue(3,22),
						new XYValue(4,10), new XYValue(5,4)),
				"Number of people in the car",
				"Frequency",
				0, // y-os kreće od 0
				22, // y-os ide do 22
				2
			);
		
		setLocation(20, 50);
		setSize(300, 300);
		setTitle("Bar chart DEMO");
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		
		
	}
	

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			JFrame frame = new BarChartDemo();
			frame.pack();
			frame.setVisible(true);
		});

	}

}
