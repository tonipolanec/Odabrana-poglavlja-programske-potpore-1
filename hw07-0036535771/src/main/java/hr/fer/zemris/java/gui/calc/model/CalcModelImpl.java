package hr.fer.zemris.java.gui.calc.model;

import java.util.ArrayList;
import java.util.List;
import java.util.function.DoubleBinaryOperator;


public class CalcModelImpl implements CalcModel {
		

	/** Flag is <code>true</code> if we can add inputs. */
	private boolean editable;
	/** Flag for negative values. */
	private boolean negative;
	/** String representation of an input. */
	private String input;
	/** Double representation of an input. */
	private double number;
	/** String representation of an input we froze for display purposes. */
	private String frozenInput;	
	
	/** Stores currently active operand. */
	private double activeOperand;
	/** Flag if active operand is set. */
	private boolean setActiveOperand;
	/** Stores operation waiting to finish. */
	private DoubleBinaryOperator pendingOperation;
	
	/** List of Event Listeners for changes in states. */
	private final List<CalcValueListener> listeners;

	public CalcModelImpl() {
		editable = true;
		negative = false;
		input = "";
		number = 0;
		frozenInput = null;
		
		setActiveOperand = false;
		pendingOperation = null;
		
		listeners = new ArrayList<>();
	}

	@Override
	public void addCalcValueListener(CalcValueListener l) {
		if(l != null) listeners.add(l);		
	}

	@Override
	public void removeCalcValueListener(CalcValueListener l) {
		listeners.remove(l);		
	}
	
	public void notifyCalcValueListeners() {
		listeners.forEach(l -> l.valueChanged(this));
	}
	
	public String toString() {
		if(frozenInput != null) return frozenInput;
		if(input == "")	return negative ? "-0" : "0";
		
		String str = (number % 1 == 0) ? ""+(int)number : ""+number;
		
		//return negative ? "-"+frozenInput : frozenInput;
		return negative ? "-"+str : str;	
	}

	@Override
	public double getValue() {
		return negative ? -number : number;
	}

	@Override
	public void setValue(double value) {		
		number = value < 0 ? -value : value;
		input = ""+ value;
		
		negative = value < 0 ? true : false;			
		
		editable = false;		
		
		notifyCalcValueListeners();
	}

	@Override
	public boolean isEditable() {
		return editable;
	}

	@Override
	public void clear() {
		negative = false;
		input = "";
		number = 0;
		editable = true;
		
		notifyCalcValueListeners();
	}

	@Override
	public void clearAll() {
		negative = false;
		input = "";
		number = 0;
		editable = true;
		
		clearActiveOperand();
		pendingOperation = null; 
		
		notifyCalcValueListeners();		
	}

	@Override
	public void swapSign() throws CalculatorInputException {
		if(!editable) throw new CalculatorInputException("Model is currently not editable!");
		frozenInput = null;
		
		negative = !negative;
		
		//input = "-" + input;
		
		notifyCalcValueListeners();
		
//		number = -1 * number;
//		input = ""+ number;		
	}

	@Override
	public void insertDecimalPoint() throws CalculatorInputException {
		if(!editable) throw new CalculatorInputException("Model is currently not editable!");
		if(number % 1 != 0) throw new CalculatorInputException("Decimal point already exists!");
		//if (input.isEmpty()) throw new CalculatorInputException("No number had been entered!");
		
		input += input.isBlank() ? "0." : ".";
		
		try {
			number = Double.parseDouble(input);
			frozenInput = null;
			
			notifyCalcValueListeners();
			
		} catch(NumberFormatException e) {
			throw new CalculatorInputException("Cannot parse!");
		}
		

	}

	@Override
	public void insertDigit(int digit) throws CalculatorInputException, IllegalArgumentException {
		if(!isEditable()) throw new CalculatorInputException("Model is currently not editable!");
		if(input.length() == 308) throw new CalculatorInputException("Number is too big!");
			
		input += digit;
		try {
			number = Double.parseDouble(input);
			frozenInput = null;
			
			notifyCalcValueListeners();
			
		} catch(NumberFormatException e) {
			throw new IllegalArgumentException("Cannot parse!");
		}
		
	}

	@Override
	public boolean isActiveOperandSet() {
		return setActiveOperand;
	}

	@Override
	public double getActiveOperand() throws IllegalStateException {
		if(!isActiveOperandSet()) throw new IllegalStateException("Active operand is not set!");
		
		return activeOperand;
	}

	@Override
	public void setActiveOperand(double activeOperand) {
		this.activeOperand = activeOperand;
		setActiveOperand = true;
		
		notifyCalcValueListeners();
	}

	@Override
	public void clearActiveOperand() {
		setActiveOperand = false;
		
		notifyCalcValueListeners();
	}

	@Override
	public DoubleBinaryOperator getPendingBinaryOperation() {
		return pendingOperation;
	}

	@Override
	public void setPendingBinaryOperation(DoubleBinaryOperator op) {
		pendingOperation = op;	
		
		notifyCalcValueListeners();
	}
	
	public void freezeValue(String value) {
		frozenInput = value;
	}
	
	public void unfreezeValue() {
		frozenInput = null;
	}
	
	public boolean hasFrozenValue() {
		return frozenInput != null;
	}
	
	public String getInput() {
		return input;
	}
	
	

}