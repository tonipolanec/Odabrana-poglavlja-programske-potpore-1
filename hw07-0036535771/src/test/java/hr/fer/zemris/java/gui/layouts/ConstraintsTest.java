package hr.fer.zemris.java.gui.layouts;

import static org.junit.jupiter.api.Assertions.assertThrows;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.junit.jupiter.api.Test;

public class ConstraintsTest {

	@Test
	public void testOutOfBounds() { 
		JPanel p = new JPanel(new CalcLayout());
		// r<1
		assertThrows(CalcLayoutException.class, () -> p.add(new JLabel("x"), "-1,2"));
		
		// r>5 or s<1
		assertThrows(CalcLayoutException.class, () -> p.add(new JLabel("x"), "6,2"));
		assertThrows(CalcLayoutException.class, () -> p.add(new JLabel("x"), "2,-1"));
		
		//s>7
		assertThrows(CalcLayoutException.class, () -> p.add(new JLabel("x"), "2,8"));
	}
	
	@Test
	public void test1commaX() { 
		JPanel p = new JPanel(new CalcLayout());
		// (1,s) where s>1 && s<6
		assertThrows(CalcLayoutException.class, () -> p.add(new JLabel("x"), "1,2"));
		assertThrows(CalcLayoutException.class, () -> p.add(new JLabel("x"), "1,3"));
		assertThrows(CalcLayoutException.class, () -> p.add(new JLabel("x"), "1,4"));
		assertThrows(CalcLayoutException.class, () -> p.add(new JLabel("x"), "1,5"));
	}

	@Test
	public void testSameConstraint() { 
		JPanel p = new JPanel(new CalcLayout());
		RCPosition pos = new RCPosition(2, 2);
		
		p.add(new JLabel("x"), pos);
		assertThrows(CalcLayoutException.class, () -> p.add(new JLabel("y"), pos));

	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
