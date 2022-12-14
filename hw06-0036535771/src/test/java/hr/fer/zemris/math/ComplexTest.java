package hr.fer.zemris.math;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;


public class ComplexTest {
	
	// https://www.symbolab.com/solver/complex-numbers-calculator

	@Test
	public void testModule() {
		Complex num = new Complex(3, -4);
		assertEquals(5, num.module());
		
		num = new Complex(-3, 4);
		assertEquals(5, num.module());
		
		num = new Complex(3, 0);
		assertEquals(3, num.module());
		
		num = new Complex(0, -3);
		assertEquals(3, num.module());
	}
	
	@Test
	public void testMultiply() {
		Complex num1 = new Complex(3, -4);
		Complex num2 = new Complex(-5, 3);
		
		Complex expected = new Complex(-3, 29);
		assertEquals(expected, num1.multiply(num2));
	}
	
	@Test
	public void testDivide() {
		Complex num1 = new Complex(3, -4);
		Complex num2 = new Complex(-5, 3);
		double e1 = -27.0/34;
		double e2 = +11.0/34;
		Complex expected = new Complex(e1,e2);
		assertEquals(expected, num1.divide(num2));
		
		num1 = new Complex(-18, 0);
		num2 = new Complex(0, 3);
		e1 = 0;
		e2 = 6;
		expected = new Complex(e1, e2);
		assertEquals(expected, num1.divide(num2));
		
		num1 = new Complex(-18, 14);
		num2 = new Complex(22, -3);
		e1 = -438.0/493;
		e2 = 254.0/493;
		expected = new Complex(e1, e2);
		assertEquals(expected, num1.divide(num2));
	}
	
	@Test
	public void testAdd() {
		Complex num1 = new Complex(3, -4);
		Complex num2 = new Complex(-5, 3);
		
		Complex expected = new Complex(-2, -1);
		assertEquals(expected, num1.add(num2));
	}
	
	@Test
	public void testSub() {
		Complex num1 = new Complex(3, -4);
		Complex num2 = new Complex(-5, 3);
		
		Complex expected = new Complex(8, -7);
		assertEquals(expected, num1.sub(num2));
	}
	
	@Test
	public void testNegate() {
		Complex num1 = new Complex(3, -4);
		Complex num2 = new Complex(-5, 3);
		
		Complex expected = new Complex(-3, 4);
		assertEquals(expected, num1.negate());
		
		expected = new Complex(5, -3);
		assertEquals(expected, num2.negate());
	}
	
	@Test
	public void testPower() {
		Complex num = new Complex(3, -4);
		
		Complex expected = new Complex(-7, -24);
		assertEquals(expected, num.power(2));
		
		expected = new Complex(11753, 10296);
		assertEquals(expected, num.power(6));
	}
	
	@Test
	public void testRoot() {
		// https://www.emathhelp.net/calculators/algebra-2/nth-roots-of-complex-number-calculator
		Complex num = new Complex(3, -4);
		
		List<Complex> ans = num.root(2);
		if(!ans.contains(new Complex(2, -1)))
			fail("Nema 2-i");
		if(!ans.contains(new Complex(-2, 1)))
			fail("Nema -2+i");
		
		List<Complex> ans2 = num.root(4);
		if(!ans2.contains(new Complex(1.455346690225355, -0.343560749722512)))
			fail("Nema 1");
		if(!ans2.contains(new Complex(0.343560749722512, 1.455346690225355)))
			fail("Nema 2");
		if(!ans2.contains(new Complex(-1.455346690225355, 0.343560749722512)))
			fail("Nema 3");
		if(!ans2.contains(new Complex(-0.343560749722512, -1.455346690225355)))
			fail("Nema 4");
	}
	
	
	
	
	
	
}
