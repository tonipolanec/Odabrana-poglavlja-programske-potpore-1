package hr.fer.zemris.java.fractals;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;


import hr.fer.zemris.java.fractals.viewer.*;
import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;

public class Newton {

	
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("Welcome to Newton-Raphson iteration-based fractal viewer.\n" +
				"Please enter at least two roots, one root per line. Enter 'done' when done.");
		
		List<Complex> listC = new ArrayList<>();
		int ind = 1;
		String input = "";
		do {
			System.out.print("Root " + ind++ + "> ");
			input = scanner.nextLine().trim();
			if(input.equals("done"))
				break;
			
			listC.add(stringToComplex(input));
				
		} while(true);
		scanner.close();
			
		if(listC.size() < 2) {
			System.out.println("Needed at least 2 roots!");
			return;
		}
		
		
		Complex[] arrayC = new Complex[listC.size()];
		for(int i=0; i<listC.size(); i++)
			arrayC[i] = listC.get(i);
		
		System.out.println("Image of fractal will appear shortly. Thank you.");
		ComplexRootedPolynomial crp = new ComplexRootedPolynomial(new Complex(2, 0), arrayC);
		FractalViewer.show(new MyProducer(crp));
	}
	
	
	
	public static class MyProducer implements IFractalProducer {
		
		private ComplexRootedPolynomial crp;
		
		public MyProducer(ComplexRootedPolynomial crp) {
			this.crp = crp;
		}
		
		
		@Override
		public void produce(double reMin, double reMax, double imMin, double imMax,
				int width, int height, long requestNo, IFractalResultObserver observer, AtomicBoolean cancel) {
			
			System.out.println("Zapocinjem izracun...");

			int maxIter = 16*16*16;
			short[] data = new short[width * height];
			int offset = 0;
					
			double rootTreshold = 0.002;
			double convergenceTreshold = 0.001;
			
			ComplexPolynomial polynom = crp.toComplexPolynom();
			
			for(int y = 0; y < height; y++) {
				if(cancel.get()) break;
				for(int x = 0; x < width; x++) {
					
					double cre = x / (width-1.0) * (reMax - reMin) + reMin;
					double cim = (height-1.0-y) / (height-1) * (imMax - imMin) + imMin;
					Complex c = new Complex(cre, cim);
					Complex zn = c;
					double module;
					int iter = 0;
					do{
						Complex numerator = polynom.apply(zn);
						Complex denominator = polynom.derive().apply(zn);
						Complex znold = zn;
						Complex fraction = numerator.divide(denominator);
						zn = zn.sub(fraction);
						module = znold.sub(zn).module();
						
						iter++;
					} while(module > convergenceTreshold && iter < maxIter);
					
					int index = crp.indexOfClosestRootFor(zn, rootTreshold);
					data[offset++] = (short) (index+1);
				}
			}
			
			System.out.println("Racunanje gotovo. Idem obavijestiti promatraca tj. GUI!");
			observer.acceptResult(data, (short)(polynom.order()+1), requestNo);
		}
	}
	
	
	
	public static Complex stringToComplex(String input) {
		
		double real, imag;
		int indOfSpace = input.indexOf(' ');
		
		if(indOfSpace == -1) { // one part is 0
			
			boolean hasI = input.contains("i");
			
			if(!hasI) { // imaginary part is 0
				real = Double.parseDouble(input);
				return new Complex(real, 0);
				
			
			} else { // real part is 0
				imag = 1;
				int startingChar = 1;
				if(input.charAt(0) == '-') {
					imag = -1;
					startingChar++;
				}
				
				try {
					imag = imag * Double.parseDouble(input.substring(startingChar));
				} catch(NumberFormatException e) {
				}
				
				return new Complex(0, imag);
			}

		} else {
			real = Double.parseDouble(input.substring(0, indOfSpace));
			
			boolean imagNeg = input.charAt(indOfSpace+1) == '-' ? true : false;
			try {
				imag = Double.parseDouble(input.substring(indOfSpace+4));
			} catch(NumberFormatException e) {
				imag = 1;
			}
			if(imagNeg)
				imag *= -1;
			
			return new Complex(real, imag);
			
		}
		
	}
	
	

	

}
