package hr.fer.zemris.math;

public class ComplexRootedPolynomial {

	Complex constant;
	Complex[] roots;
	
	// constructor
	public ComplexRootedPolynomial(Complex constant, Complex ... roots) {
		this.constant = constant;
		this.roots = roots;
	}
	
	// computes polynomial value at given point z
	public Complex apply(Complex z) {
		Complex res = constant;
		
		for(int i=0; i<roots.length; i++) {
			res = res.multiply(z.sub(roots[i]));
		}
		return res;
	}
	
	// converts this representation to ComplexPolynomial type
	public ComplexPolynomial toComplexPolynom() {
		
	}
	
	@Override
	public String toString() {
		String cRootedPolyString = "(" + constant + ")";
		
		for(int i=0; i<roots.length; i++) {
			cRootedPolyString += "+(z-(" + roots[i] + "))";
		}
		return cRootedPolyString;
	}
	
	// finds index of closest root for given complex number z that is within
	// treshold; if there is no such root, returns -1
	// first root has index 0, second index 1, etc
	public int indexOfClosestRootFor(Complex z, double treshold) {
		int minIndex = -1;
		double minDistance = treshold +1;
		
		for(int i=0; i<roots.length; i++) {
			double reDist = Math.abs(z.getRe() - roots[i].getRe());
			double imDist = Math.abs(z.getIm() - roots[i].getIm());
			double distance = Math.sqrt(reDist*reDist + imDist*imDist);
			
			if(distance <= treshold && distance < minDistance) {
				minDistance = distance;
				minIndex = i;
			}			
		}
		
		return minIndex;
	}

	
	
	
	
	
}
