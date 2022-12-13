package hr.fer.zemris.math;

import java.util.ArrayList;
import java.util.List;

public class ComplexRootedPolynomial {

	Complex constant;
	Complex[] roots;
	
	// constructor
	public ComplexRootedPolynomial(Complex constant, Complex ...roots) {
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
		int order = roots.length;
		
		Complex[] factors = new Complex[order+1];
		boolean poz = true;
		
		for(int i=order; i>=0; i--) {
			Complex currentFactor;
			if (poz) {
				currentFactor = Complex.ONE;
				poz = false;		
			} else {
				currentFactor = Complex.ONE_NEG;
				poz = true;		
			}
			
			currentFactor = currentFactor.multiply(constant);
			
			Complex[] multipliedCombs = giveMultipliedCombs(i, roots);
			
			for(int j=0; j<multipliedCombs.length; j++) {
				currentFactor = currentFactor.multiply(multipliedCombs[j]);
			}
			
			factors[i] = currentFactor;
		}
				
		return new ComplexPolynomial(factors);
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

	
	
	private Complex[] giveMultipliedCombs(int r, Complex[] numbers) {
		int n = numbers.length;

		List<int[]> combinations = generate(n, r);
		Complex[] multipliedCombs = new Complex[combinations.size()];		
		
		for(int i=0; i<combinations.size(); i++) {
			Complex currentMult = Complex.ONE;
			for(int j=0; j<combinations.get(i).length; j++) {
				currentMult = currentMult.multiply(numbers[combinations.get(i)[j]]);
			}
			multipliedCombs[i] = currentMult;
		}
		
		return multipliedCombs;
	}
	
	
	// help: https://www.baeldung.com/java-combinations-algorithm
	private List<int[]> generate(int n, int r) {
	    List<int[]> combinations = new ArrayList<>();
	    helper(combinations, new int[r], 0, n-1, 0);
	    return combinations;
	}
	// help: https://www.baeldung.com/java-combinations-algorithm
	private void helper(List<int[]> combinations, int data[], int start, int end, int index) {
	    if (index == data.length) {
	        int[] combination = data.clone();
	        combinations.add(combination);
	    } else if (start <= end) {
	        data[index] = start;
	        helper(combinations, data, start + 1, end, index + 1);
	        helper(combinations, data, start + 1, end, index);
	    }
	}
	
	
	
	
	
//	private int factoriel(int n) {
//		if(n==0)
//			return 1;
//	    int res = 1;
//	    for (int i=2; i<=n; i++)
//	    	res = res * i;
//	    return res;
//	}
//	private int nCr(int n, int r) {
//		return factoriel(n) / ((factoriel(r) * factoriel(n - r)));
//	}
//	
	
	
	
}
