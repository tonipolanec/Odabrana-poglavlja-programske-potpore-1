package hr.fer.zemris.java.gui.calc;

import java.util.function.DoubleBinaryOperator;

import hr.fer.zemris.java.gui.calc.model.CalcModel;
import hr.fer.zemris.java.gui.calc.model.CalcValueListener;
import hr.fer.zemris.java.gui.calc.model.CalculatorInputException;

public class CalcModelImpl implements CalcModel {
	
	public static void main(String args[]) {
		CalcModel model = new CalcModelImpl();
		
		model.insertDigit(1);
		model.insertDigit(1);
		model.insertDigit(9);
		//model.insertDecimalPoint();
		//model.insertDigit(3);
		//model.insertDigit(2);
		
		double d = model.getValue();
		System.out.println(d);
	}
	

	/** Flag is <code>true</code> if we can add inputs. */
	private boolean editable;
	/** Flag for negative values. */
	private boolean negative;
	/** String representation of an input. */
	private String input;
	/** Double representation of an input. */
	private double number;
	/** String representation of an input we froze for display purposes. */
	private String frozenInput = null;	
	
	/** Stores currently active operand. */
	private double activeOperand;
	/** Flag if active operand is set. */
	private boolean setActiveOperand;
	/** Stores operation waiting to finish. */
	private DoubleBinaryOperator pendingOperation;

	public CalcModelImpl() {
		editable = true;
		negative = false;
		input = "";
		number = 0;
		frozenInput = null;
		
		setActiveOperand = false;
		pendingOperation = null;
	}

	@Override
	public void addCalcValueListener(CalcValueListener l) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeCalcValueListener(CalcValueListener l) {
		// TODO Auto-generated method stub
		
	}
	
	public String toString() {
		if(frozenInput != null) return frozenInput;
		if(input == "")	return negative ? "-0" : "0";
		
		String str = (number % 1 == 0) ? ""+(int)number : ""+number;
		
		return negative ? "-"+str : str;
		
		//return negative ? "-"+frozenInput : frozenInput;
	}

	@Override
	public double getValue() {
		return negative ? -number : number;
	}

	@Override
	public void setValue(double value) {		
		number = value < 0 ? -value : value;
		input = ""+ number;
		
		negative = value < 0 ? true : false;			
		
		editable = false;		
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
	}

	@Override
	public void clearAll() {
		negative = false;
		input = "";
		number = 0;
		editable = true;
		
		clearActiveOperand();
		pendingOperation = null; 
		
	}

	@Override
	public void swapSign() throws CalculatorInputException {
		if(!editable) throw new CalculatorInputException("Model is currently not editable!");
		frozenInput = null;
		
		negative = !negative;
		
//		number = -1 * number;
//		input = ""+ number;		
	}

	@Override
	public void insertDecimalPoint() throws CalculatorInputException {
		if(!editable) throw new CalculatorInputException("Model is currently not editable!");
		if(number % 1 != 0) throw new CalculatorInputException("Decimal point already exists!");
		if (input.isEmpty()) throw new CalculatorInputException("No number had been entered!");
		
		input += input.isBlank() ? "0." : ".";
		
		try {
			number = Double.parseDouble(input);
			frozenInput = null;
			
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
		if(!isActiveOperandSet()) throw new IllegalStateException("Active operand is yet not set!");
		
		return activeOperand;
	}

	@Override
	public void setActiveOperand(double activeOperand) {
		this.activeOperand = activeOperand;
		setActiveOperand = true;
	}

	@Override
	public void clearActiveOperand() {
		setActiveOperand = false;
	}

	@Override
	public DoubleBinaryOperator getPendingBinaryOperation() {
		return pendingOperation;
	}

	@Override
	public void setPendingBinaryOperation(DoubleBinaryOperator op) {
		pendingOperation = op;		
	}
	
	public void freezeValue(String value) {
		frozenInput = value;
	}
	
	public boolean hasFrozenValue() {
		return frozenInput != null;
	}
	
	

}