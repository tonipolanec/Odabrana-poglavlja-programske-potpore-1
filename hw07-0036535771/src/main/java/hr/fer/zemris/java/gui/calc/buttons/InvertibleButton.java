package hr.fer.zemris.java.gui.calc.buttons;

import java.awt.Color;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class InvertibleButton extends CalculatorButton {
	private static final long serialVersionUID = -7655213419711799270L;
	
	private String text;
	private String invText;
	
	private ActionListener normalActionListener;
	private ActionListener invertedActionListener;
	
	private boolean inverted;

	public InvertibleButton(String text, String invText, ActionListener normalActionListener, ActionListener invertedActionListener) {	
		this.text = text;
		this.invText = invText;
		this.normalActionListener = normalActionListener;
		this.invertedActionListener = invertedActionListener;
		
		inverted = false;
	}
	
	public InvertibleButton invert() {
		inverted = !inverted;
		return this;
	}
	
	public String getText() {
		if(!inverted)
			return text;
		return invText;
	}
	
	public ActionListener getActionListener() {
		if(!inverted)
			return normalActionListener;
		return invertedActionListener;
	}
	
	
	public JButton getJButton() {
		this.setBackground(new Color(128, 206, 215));
		this.setOpaque(true);
		
		this.addActionListener(getActionListener());
		
		return this;
	
	}
		
	
	
	
	
	
	
	
	
	
		
}
