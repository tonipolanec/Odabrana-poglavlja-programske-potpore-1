package hr.fer.zemris.java.gui.calc;

import java.awt.Checkbox;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.function.DoubleBinaryOperator;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import hr.fer.zemris.java.gui.calc.buttons.CalculatorButton;
import hr.fer.zemris.java.gui.calc.buttons.InvertibleButton;
import hr.fer.zemris.java.gui.calc.model.*;
import hr.fer.zemris.java.gui.layouts.CalcLayout;
import hr.fer.zemris.java.gui.layouts.RCPosition;

public class Calculator extends JFrame {
	private static final long serialVersionUID = 9123592806395228283L;
	
	/** Model of calculator (all needed methods for functionalities). */
	private CalcModelImpl model;
	
	/** Map of all buttons which can be inverted. */
	private Map<RCPosition, InvertibleButton> invertedButtons;
	
	/** Map of all digit buttons */
	//private Map<RCPosition, Calc>
	
	private Stack<Double> stack;
	
	
	public Calculator() {
		model = new CalcModelImpl();
		
		invertedButtons = new HashMap<>();
		stack = new Stack<>();
		
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
		cp.add(newButton("7"), new RCPosition(2,3));
		cp.add(newButton("8"), new RCPosition(2,4));
		cp.add(newButton("9"), new RCPosition(2,5));
		cp.add(newButton("/"), new RCPosition(2,6));
		cp.add(newButton("reset"), new RCPosition(2,7));
		
		// ROW 3
		
		cp.add(newButton("4"), new RCPosition(3,3));
		cp.add(newButton("5"), new RCPosition(3,4));
		cp.add(newButton("6"), new RCPosition(3,5));
		cp.add(newButton("*"), new RCPosition(3,6));
		cp.add(newButton("push"), new RCPosition(3,7));
		
		// ROW 4
		
		cp.add(newButton("1"), new RCPosition(4,3));
		cp.add(newButton("2"), new RCPosition(4,4));
		cp.add(newButton("3"), new RCPosition(4,5));
		cp.add(newButton("-"), new RCPosition(4,6));
		cp.add(newButton("pop"), new RCPosition(4,7));

		// ROW 5
		
		cp.add(newButton("0"), new RCPosition(5,3));
		cp.add(newButton("+/-"), new RCPosition(5,4));
		cp.add(newButton("."), new RCPosition(5,5));
		cp.add(newButton("+"), new RCPosition(5,6));
		cp.add(newCheckbox("Inv", cp), new RCPosition(5, 7));
		
		// INVERTED BUTTONS
		initializeInvertedButtons();
		for (Map.Entry<RCPosition,InvertibleButton> invButton : invertedButtons.entrySet()) {
			cp.add(invButton.getValue().getJButton(), invButton.getKey());
		}

		
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
			al = e -> {
				if(model.hasFrozenValue()) {
					model.unfreezeValue();
					model.clear();
				}
				model.insertDigit(Integer.parseInt(text));
			};
		
		// OPERATIONS		
		} else if(text.equals("+") || text.equals("-") || text.equals("*") || text.equals("/")){	
			al = e -> {  
				
				model.freezeValue(model.toString());
				
				if(model.isActiveOperandSet()) {
					double result = model.getPendingBinaryOperation().applyAsDouble(model.getActiveOperand(), model.getValue());
					model.setValue(result);
					model.setActiveOperand(result);
					
				
				} else {
					model.setActiveOperand(model.getValue());
				}
						
				switch(text) {
					case "+":
						model.setPendingBinaryOperation((a,b) -> a+b);
					break;
					case "-":
						// if not then is unary operation
						if(!model.isActiveOperandSet())
							model.swapSign();
						else
							model.setPendingBinaryOperation((a,b) -> a-b);	
					break;
					case "*":
						model.setPendingBinaryOperation((a,b) -> a*b);
					break;
					case "/":
						model.setPendingBinaryOperation((a,b) -> a/b);
					break;
				}
				
				
				//model.clear();
				
			};
		
		// EQUALS
		} else if(text.equals("=")) {
			al = e -> {
				double result = model.getPendingBinaryOperation().applyAsDouble(model.getActiveOperand(), model.getValue());
				model.setValue(result);
				model.clearActiveOperand();
			};
		
		// 1/X
		} else if(text.equals("1/x")) {
			al = e -> {
				double result = 1. / model.getValue();
				model.setValue(result);
				model.setActiveOperand(result);
			};
		
		// DECIMAL POINT .
		} else if(text.equals(".")) {
			al = e -> {
				model.insertDecimalPoint();
			};
		
		// SWAP SIGN +/-
		} else if(text.equals(".")) {
			al = e -> {
				model.swapSign();
			};
		
		// PUSH or POP
		} else if(text.equals("push") || text.equals("pop")) {
			al = e -> {
				if(text.equals("push")) {
					stack.add(model.getValue());
					
				} else {
					if(!stack.empty()) {
						double popped = stack.pop();
						model.setValue(popped);
					}
				}
			};
		
		
		// CLEAR
		} else if(text.equals("clr")) {
			al = e -> {
				model.clear();
			};
		
		
		// RESET
		} else if(text.equals("reset")) {
			al = e -> {
				model.clearAll();
			};
		}
		
		return al;
	}
	
	/**
	 * Creates new checkbox for calculator.	
	 * 
	 * @param text
	 * @return instance of <code>JCheckbox</code>
	 */
	private JCheckBox newCheckbox(String text, Container cp) {
		JCheckBox c = new JCheckBox(text);
		c.addActionListener(e ->{
			for (Map.Entry<RCPosition,InvertibleButton> invButton : invertedButtons.entrySet()) {
				invButton.getValue().removeActionListener(invButton.getValue().getActionListener());
				invButton.getValue().invert();
				invButton.getValue().addActionListener(invButton.getValue().getActionListener());
			}
			cp.repaint();
			pack();
			setMinimumSize(this.getSize());
		});
		
		return c;
	}
	
	
	/**
	 * Initializes all buttons that can be inverted with checkbox.
	 */
	private void initializeInvertedButtons() {
		
		// sin, arcsin
		invertedButtons.put(new RCPosition(2,2), 
				new InvertibleButton("sin", "arcsin", 
						e -> model.setValue(Math.sin(model.getValue())),
						e -> model.setValue(Math.asin(model.getValue()))
						));
		// log, 10^x
		invertedButtons.put(new RCPosition(3,1), 
				new InvertibleButton("log", "10^x", 
						e -> model.setValue(Math.log10(model.getValue())),
						e -> model.setValue(Math.pow(10, model.getValue()))
						));

		// cos, arccos
		invertedButtons.put(new RCPosition(3,2), 
				new InvertibleButton("cos", "arccos", 
						e -> model.setValue(Math.cos(model.getValue())),
						e -> model.setValue(Math.acos(model.getValue()))
						));
		
		// ln, e^x
		invertedButtons.put(new RCPosition(4,1), 
				new InvertibleButton("ln", "e^x", 
						e -> model.setValue(Math.log(model.getValue())),
						e -> model.setValue(Math.pow(Math.E, model.getValue()))
						));
		
		// tan, arctan
		invertedButtons.put(new RCPosition(4,2), 
				new InvertibleButton("tan", "arctan", 
						e -> model.setValue(Math.tan(model.getValue())),
						e -> model.setValue(Math.atan(model.getValue()))
						));
		
		// x^n, x^(1/n)
		invertedButtons.put(new RCPosition(5,1), 
				new InvertibleButton("x^n", "x^(1/n)", 
						e -> model.setValue(Math.tan(model.getValue())),
						e -> model.setValue(Math.atan(model.getValue()))
						));
		
		// ctg, arcctg
		invertedButtons.put(new RCPosition(5,2), 
				new InvertibleButton("ctg", "arcctg", 
						e -> model.setValue(1. / Math.tan(model.getValue())),
						e -> model.setValue(Math.atan(1. / (1. / Math.tan(model.getValue()))))
						));
	}

	
	
	public static void main(String[] args) {

		SwingUtilities.invokeLater(() -> {
			JFrame frame = new Calculator();
			frame.pack();
			frame.setVisible(true);
		});
	}

}
