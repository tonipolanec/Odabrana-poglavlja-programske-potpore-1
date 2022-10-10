import org.apache.commons.math3.linear.*;

public class VectorAngle {
	
	public static void main(String ... args) {
		
		RealVector v = new ArrayRealVector(new double[] {3.5, 2.17, 1.4}, false);
		
		System.out.println(v.dotProduct(v));
		
	}
	
}
