package hr.fer.zemris.java.gui.calc;

import java.awt.Checkbox;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.DoubleBinaryOperator;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import hr.fer.zemris.java.gui.calc.model.*;
import hr.fer.zemris.java.gui.layouts.CalcLayout;
import hr.fer.zemris.java.gui.layouts.RCPosition;

public class Calculator extends JFrame {
	private static final long serialVersionUID = 9123592806395228283L;
	
	/** Model of calculator (all needed methods for functionalities). */
	CalcModelImpl model;
	
	public Calculator() {
		model = new CalcModelImpl();
		
		setLocation(20, 50);
		//setSize(300, 200);
		setTitle("Calculator");
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		
		
		
		initGUI();
	}

	/**
	 * Initializes GUI of calculator.
	 * Adds all components to the layout.
	 */
	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new CalcLayout(5));
		
		// ROW 1
		cp.add(screen("0"), new RCPosition(1,1));
		cp.add(newButton("="), new RCPosition(1,6));
		cp.add(newButton("clr"), new RCPosition(1,7));
		
		// ROW 2
		cp.add(newButton("1/x"), new RCPosition(2,1));
		cp.add(newButton("sin"), new RCPosition(2,2));
		cp.add(newButton("7"), new RCPosition(2,3));
		cp.add(newButton("8"), new RCPosition(2,4));
		cp.add(newButton("9"), new RCPosition(2,5));
		cp.add(newButton("/"), new RCPosition(2,6));
		cp.add(newButton("reset"), new RCPosition(2,7));
		
		// ROW 3
		cp.add(newButton("log"), new RCPosition(3,1));
		cp.add(newButton("cos"), new RCPosition(3,2));
		cp.add(newButton("4"), new RCPosition(3,3));
		cp.add(newButton("5"), new RCPosition(3,4));
		cp.add(newButton("6"), new RCPosition(3,5));
		cp.add(newButton("*"), new RCPosition(3,6));
		cp.add(newButton("push"), new RCPosition(3,7));
		
		// ROW 4
		cp.add(newButton("ln"), new RCPosition(4,1));
		cp.add(newButton("tan"), new RCPosition(4,2));
		cp.add(newButton("1"), new RCPosition(4,3));
		cp.add(newButton("2"), new RCPosition(4,4));
		cp.add(newButton("3"), new RCPosition(4,5));
		cp.add(newButton("-"), new RCPosition(4,6));
		cp.add(newButton("pop"), new RCPosition(4,7));

		// ROW 5
		cp.add(newButton("x^n"), new RCPosition(5,1));
		cp.add(newButton("ctg"), new RCPosition(5,2));
		cp.add(newButton("0"), new RCPosition(5,3));
		cp.add(newButton("+/-"), new RCPosition(5,4));
		cp.add(newButton("."), new RCPosition(5,5));
		cp.add(newButton("+"), new RCPosition(5,6));
		cp.add(new Checkbox("Inv"), new RCPosition(5, 7));
	}
	
	/**
	 * Creates new label with properties for screen.
	 * 
	 * @param text of label
	 * @return instance of <code>JLabel</code>
	 */
	private JLabel screen(String text) {
		JLabel l = new JLabel(text, SwingConstants.RIGHT);
		l.setBorder(BorderFactory.createLineBorder(new Color(99, 143, 146), 1));
		l.setFont(l.getFont().deriveFont(30f));
		l.setBackground(new Color(204, 219, 220));
		l.setOpaque(true);
		
		model.addCalcValueListener(new CalcValueListener() {
			@Override
			public void valueChanged(CalcModel m) {
				CalcModelImpl model = (CalcModelImpl) m;
				l.setText(model.getInput());				
			}
		});
		
		return l;
	}
	
	/**
	 * Creates new button for calculator with given text.
	 * If given text is digit, it bolds it for prettier display.
	 * 
	 * @param text of button
	 * @return instance of <code>JButton</code>
	 */
	private JButton newButton(String text) {
		JButton b = new JButton(text);
		boolean isDigit = text.length() == 1 && Character.isDigit(text.charAt(0));
		if(isDigit)
			b.setFont(b.getFont().deriveFont(30f));
		b.setBackground(new Color(128, 206, 215));
		b.setOpaque(true);
		
		b.addActionListener(getActionListenerForButton(text));
		
		return b;
	}
	
	/**
	 * Creates and return <code>ActionListener</code> based on given text.
	 * 
	 * @param text we want to make <code>ActionListener</code> for
	 * @return <code>ActionListener</code>
	 */
	private ActionListener getActionListenerForButton(String text) {
		ActionListener al = e -> System.out.println("");;
		
		// DIGIT
		if(text.length() == 1 && Character.isDigit(text.charAt(0))) {
			al = new ActionListener(){  
					public void actionPerformed(ActionEvent e){  
						if(model.hasFrozenValue())
							model.setValue(0);
						model.insertDigit(Integer.parseInt(text));  
					}  
				};
		
		// OPERATIONS		
		} else if(text.equals("+") || text.equals("-") || text.equals("*") || text.equals("/")){	
			al = new ActionListener(){  
				public void actionPerformed(ActionEvent e){  
					model.freezeValue(model.toString());
					model.setActiveOperand(model.getValue());
							
					switch(text) {
						case "+":
							model.setPendingBinaryOperation((a,b) -> a+b);
						break;
						case "-":
							model.setPendingBinaryOperation((a,b) -> a-b);	
						break;
						case "*":
							model.setPendingBinaryOperation((a,b) -> a*b);
						break;
						case "/":
							model.setPendingBinaryOperation((a,b) -> a/b);
						break;
					}
					
					model.clear();
				} 
			};
		}
		
		
		
		return al;
	}
	
	/**
	 * Creates new checkbox for calculator.	
	 * 
	 * @param text
	 * @return instance of <code>Checkbox</code>
	 */
	private Checkbox getCheckBox(String text) {
		Checkbox c = new Checkbox(text);
		
		return c;
	}
	
//	private class insertDigitActionListener implements ActionListener{
//		@Override
//		public void actionPerformed(ActionEvent e) {
//			
//		}	
//	}
	


	public static void main(String[] args) {

		SwingUtilities.invokeLater(() -> {
			JFrame frame = new Calculator();
			frame.pack();
			frame.setVisible(true);
		});
	}

}
