package hr.fer.zemris.math;

import java.util.ArrayList;
import java.util.List;

public class Complex {
	// help with mathematical expressions: https://algs4.cs.princeton.edu/99scientific/Complex.java.html
	
	
	public static final Complex ZERO 	= new Complex(0, 0);
	public static final Complex ONE 	= new Complex(1, 0);
	public static final Complex ONE_NEG = new Complex(-1,0);
	public static final Complex IM 		= new Complex(0, 1);
	public static final Complex IM_NEG 	= new Complex(0,-1);
	
	
	/** Real part of complex number*/
	private double re;
	/** Imaginary part of complex number*/
	private double im;
	
	
	public Complex() {
		this(0,0);
	}
	
	public Complex(double re, double im) {
		this.re = re;
		this.im = im;
	}
	
	
	public double getRe() {
		return re;
	}
	public double getIm() {
		return im;
	}
	
	/** Returns module of complex number */
	public double module() {
		return Math.sqrt(re*re + im*im);
	}
	
	/** Returns this*c */
	public Complex multiply(Complex c) {
		double re = this.re * c.re - this.im * c.im;
		double im = this.re * c.im + this.im * c.re;
		
		return new Complex(re, im);
	}
	
	/** Returns this/c */
	public Complex divide(Complex c) {		
		double scale = c.re*c.re + c.im*c.im; 
		Complex cReciprocal = new Complex(re / scale, -im / scale);
		
		double re = this.re * cReciprocal.re - this.im * cReciprocal.im;
	    double im = this.re * cReciprocal.im + this.im * cReciprocal.re;
	    
	    return new Complex(re, im);	
	}
	
	/** Returns this+c */
	public Complex add(Complex c) {
		double re = this.re + c.re;
	    double im = this.im + c.im;
	    
	    return new Complex(re, im);
	}
	
	/** Returns this-c */
	public Complex sub(Complex c) {
		double re = this.re - c.re;
	    double im = this.im - c.im;
	    
	    return new Complex(re, im);
	}
	
	/** Returns -this */
	public Complex negate() {
		return new Complex(-this.re, -this.im);
	}
	
	/** Returns this^n, n is non-negative integer */
	public Complex power(int n) {
		Complex copyCurrent = new Complex(this.re, this.im);
		
		Complex result = new Complex(1, 0);
		for(int i=0; i<n; i++)
			result = result.multiply(copyCurrent);
		
		return result;				
	}
	
	/** Returns n-th root of this, n is positive integer */
	public List<Complex> root(int n) {
		// help: https://socratic.org/questions/how-do-i-find-the-nth-root-of-a-complex-number
		
		double r = Math.sqrt(this.re*this.re + this.im*this.im);
        double theta = Math.atan2(this.im, this.re);
        
        double nthRootR = Math.round(Math.pow(r, 1.0 / n)); // https://www.baeldung.com/java-nth-root
        int k = n-1;
        double nthRootTheta = (theta + 2*k*Math.PI)/ n;
        
        List<Complex> results = new ArrayList<>();
        
        Complex first = new Complex(nthRootR * Math.cos(nthRootTheta), nthRootR * Math.sin(nthRootTheta));
        Complex second = new Complex(first.re, -first.im);
        
        results.add(first);
        results.add(second);     
       
        return results;
	}
	
	@Override
	public String toString() {
		if (im == 0) return re + "i0.0";
        if (re == 0) return "0.0+i" +im;
        if (im <  0) return re + "-i" + (-im);
        return re + "+i" + im;
	}
	
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Complex)) 
            return false;
	
		Complex c = (Complex) o;
		if(this.re == c.re && this.im == c.im) 
			return true;
		return false;
	}

}
