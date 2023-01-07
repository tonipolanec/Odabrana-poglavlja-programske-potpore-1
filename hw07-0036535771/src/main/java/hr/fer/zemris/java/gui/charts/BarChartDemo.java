package hr.fer.zemris.java.gui.charts;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.LayoutManager;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;


public class BarChartDemo extends JFrame {
	private static final long serialVersionUID = 4717757099598296182L;
	
	private BarChart model;
	private static String path;
	
	public BarChartDemo(BarChart model) {
		
		this.model = model;	
		//model = readModelFromFile()
				/*new BarChart(
				Arrays.asList(
						new XYValue(1,8), new XYValue(2,20), new XYValue(3,22),
						new XYValue(4,10), new XYValue(5,4)),
				"Number of people in the car", 		// x description
				"Frequency", 						// y description
				0, 									// minY
				22, 								// maxY
				2									// spaceY
			); */
		
		setLocation(300, 100);
		setSize(400, 400);
		setTitle("Bar chart DEMO");
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		
		initGUI();
	}
	

	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		
		cp.add(new JLabel(path), BorderLayout.PAGE_START);
		cp.add(new BarChartComponent(model));
		
	}


	public static void main(String[] args) {
		path = args[0];
		BarChart model = getModelFromFile(path);
		
		SwingUtilities.invokeLater(() -> {
			JFrame frame = new BarChartDemo(model);
			frame.setVisible(true);
		});

	}


	private static BarChart getModelFromFile(String path) {
		
		File file = new File(path);
		file = new File("D:\\FER\\5. semestar\\Odabrana poglavlje razvojne programske potpore 1\\Odabrana-poglavlja-programske-potpore-1\\hw07-0036535771\\src\\main\\resources\\chart.txt");
		Scanner sc;
	    try {
			sc = new Scanner(file);
			sc.useDelimiter("\\n");
		} catch (FileNotFoundException e) {
			System.out.println("Error while reading file!"); 
			System.exit(0);
			return null;
		}
	   
	    String xDescription = sc.next();
	    String yDescription = sc.next();
	    String XYValuesString = sc.next();
	    int minY, maxY, spaceY;
	    try {
//	    	String sMinY = sc.next().strip();
//	    	String sMaxY = sc.next().strip();
//	    	String sSpaceY = sc.next().strip();
//	    	
//	    	System.out.println(sMinY+ "" + sMaxY + "" + sSpaceY);
	    	
	    	minY = Integer.parseInt(sc.next().strip());//.substring(0, sMinY.length() - 1));
	    	maxY = Integer.parseInt(sc.next().strip());//.substring(0, sMaxY.length() - 1));
	    	spaceY = Integer.parseInt(sc.next().strip());//.substring(0, sSpaceY.length() - 1));
	    	
	    	sc.close();
	    } catch (NumberFormatException e) {
	    	System.out.println("Error with model propeties in file! (minY, maxY, spaceY)");
			System.exit(0);
			sc.close();
	    	return null;
	    }
	    
	    
	    List<XYValue> XYValues = new ArrayList<>();
	    
	    XYValuesString = XYValuesString.replaceAll("\\s+", " ").trim();
	    String[] XYValuesStringSplit = XYValuesString.split(" ");
	    for(String s : XYValuesStringSplit) {
	    	try {
	    		String[] xy = s.split(",");
	    		int x = Integer.parseInt(xy[0]);
	    		int y = Integer.parseInt(xy[1]);
	    		
	    		XYValues.add(new XYValue(x, y));
	    		
	    	} catch (NumberFormatException e) {
		    	System.out.println("Error with model propeties in file! XYValues are faulty!");
				System.exit(0);
		    	return null;
		    }
	    }
	    
	    	    
		return new BarChart(XYValues, xDescription, yDescription, minY, maxY, spaceY);
	}

	
	
	
}
