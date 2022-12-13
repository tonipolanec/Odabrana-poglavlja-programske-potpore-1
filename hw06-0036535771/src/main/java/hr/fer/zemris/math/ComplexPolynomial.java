package hr.fer.zemris.math;

public class ComplexPolynomial {

	Complex[] factors;
	
	// constructor
	public ComplexPolynomial(Complex ...factors) {
		this.factors = factors;
	}
	
	// returns order of this polynom; eg. For (7+2i)z^3+2z^2+5z+1 returns 3
	public short order() {
		return (short) (factors.length-1);
	}
	
	// computes a new polynomial this*p
	public ComplexPolynomial multiply(ComplexPolynomial p) {
		Complex[] newFactors = new Complex[this.factors.length * p.factors.length];
		
		for(int i=0; i<newFactors.length; i++) {
			newFactors[i] = Complex.ZERO;
			
			for(int x=0; x<=this.order(); x++) {
				for(int y=0; y<=p.order(); y++) {
					if(x+y == i) {
						if(this.factors[x].equals(Complex.ZERO)) 
							newFactors[i].add(p.factors[y]);
						else if(p.factors[y].equals(Complex.ZERO)) 
							newFactors[i].add(this.factors[x]);
						else
							newFactors[i].add(this.factors[x].multiply(p.factors[y]));	
					}
				}
			}
		}
		
		return new ComplexPolynomial(newFactors);
	}
	
	// computes first derivative of this polynomial; for example, for
	// (7+2i)z^3+2z^2+5z+1 returns (21+6i)z^2+4z+5
	public ComplexPolynomial derive() {
		Complex[] newFactors = new Complex[factors.length-1];
		
		for(int i=1; i<factors.length; i++) {
			Complex currentFactor = factors[i].multiply(new Complex(i, 0));
			newFactors[i-1] = currentFactor;
		}
		return new ComplexPolynomial(newFactors);
	}
	
	// computes polynomial value at given point z
	public Complex apply(Complex z) {
		Complex res = Complex.ONE;
		for(int i=0; i<factors.length; i++) {
			res = res.add(factors[i].multiply(z.power(i)));
		}
		return res;
	}
	
	@Override
	public String toString() {
		String cPolyString = "";
		for(int i=factors.length-1; i>=0; i--) {
			if(i > 0)
				cPolyString += "(" + factors[i].toString() + ")*z^" + i + "+";
			else 
				cPolyString += "(" + factors[i].toString() + ")";
		}
		
		return cPolyString;
	}
	
	
	
	

}
