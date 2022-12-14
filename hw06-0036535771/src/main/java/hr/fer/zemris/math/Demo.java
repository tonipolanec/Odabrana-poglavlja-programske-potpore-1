package hr.fer.zemris.math;


import java.util.List;

public class Demo {


	public static void main(String[] args) {
		ComplexRootedPolynomial crp = new ComplexRootedPolynomial(
				new Complex(1,0), Complex.ONE, Complex.ONE_NEG, Complex.IM, Complex.IM_NEG);
		
		ComplexPolynomial cp = crp.toComplexPolynom();
		
		System.out.println(crp);
		System.out.println(cp);
		System.out.println(cp.derive());
		
		
//		List<Complex> ans = (new Complex(2, 1)).root(2);
//		
//		for(@SuppressWarnings("unused") Complex c : ans) {
//			//System.out.println(c.toString());
//		}

	}

}
