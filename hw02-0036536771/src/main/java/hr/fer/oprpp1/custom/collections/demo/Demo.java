package hr.fer.oprpp1.custom.collections.demo;

import hr.fer.oprpp1.custom.collections.*;
import hr.fer.oprpp1.custom.collections.ArrayIndexedCollection;

public class Demo {

	public static void main(String[] args) {
		
//		Collection col = new ArrayIndexedCollection();
//		
//		col.add("Ivo");
//		col.add("Ana");
//		col.add("Jasna");
//		
//		ElementsGetter getter = col.createElementsGetter();
//		
//		System.out.println("Ima nepredanih elemenata: " + getter.hasNextElement());
//		System.out.println("Jedan element: " + getter.getNextElement());
//		
//		System.out.println("Ima nepredanih elemenata: " + getter.hasNextElement());
//		System.out.println("Jedan element: " + getter.getNextElement());
//		
//		System.out.println("Ima nepredanih elemenata: " + getter.hasNextElement());
//		System.out.println("Jedan element: " + getter.getNextElement());
//		
//		System.out.println("Ima nepredanih elemenata: " + getter.hasNextElement());
//		System.out.println("Jedan element: " + getter.getNextElement());
		
		
//      ---------------------------------------------------------------------------
		
		
//		//Collection col = new ArrayIndexedCollection();
//		Collection col = new LinkedListIndexedCollection();
//		
//		col.add("Ivo");
//		col.add("Ana");
//		col.add("Jasna");
//		
//		ElementsGetter getter = col.createElementsGetter();
//		
//		System.out.println("Ima nepredanih elemenata: " + getter.hasNextElement());
//		System.out.println("Ima nepredanih elemenata: " + getter.hasNextElement());
//		System.out.println("Ima nepredanih elemenata: " + getter.hasNextElement());
//		System.out.println("Ima nepredanih elemenata: " + getter.hasNextElement());
//		System.out.println("Ima nepredanih elemenata: " + getter.hasNextElement());
//		System.out.println("Jedan element: " + getter.getNextElement());
//		
//		System.out.println("Ima nepredanih elemenata: " + getter.hasNextElement());
//		System.out.println("Ima nepredanih elemenata: " + getter.hasNextElement());
//		System.out.println("Jedan element: " + getter.getNextElement());
//		
//		System.out.println("Ima nepredanih elemenata: " + getter.hasNextElement());
//		System.out.println("Ima nepredanih elemenata: " + getter.hasNextElement());
//		System.out.println("Ima nepredanih elemenata: " + getter.hasNextElement());
//		System.out.println("Jedan element: " + getter.getNextElement());
//		
//		System.out.println("Ima nepredanih elemenata: " + getter.hasNextElement());
//		System.out.println("Ima nepredanih elemenata: " + getter.hasNextElement());
		
		
//      ---------------------------------------------------------------------------	
		
		
//		//Collection col = new ArrayIndexedCollection();
//		Collection col = new LinkedListIndexedCollection();
//		
//		col.add("Ivo");
//		col.add("Ana");
//		col.add("Jasna");
//		
//		ElementsGetter getter = col.createElementsGetter();
//		System.out.println("Jedan element: " + getter.getNextElement());
//		System.out.println("Jedan element: " + getter.getNextElement());
//		System.out.println("Jedan element: " + getter.getNextElement());
//		System.out.println("Jedan element: " + getter.getNextElement());
		
		
//      ---------------------------------------------------------------------------
		
		
//		//Collection col = new ArrayIndexedCollection();
// 		Collection col = new LinkedListIndexedCollection();
//		
//		col.add("Ivo");
//		col.add("Ana");
//		col.add("Jasna");
//		
//		ElementsGetter getter1 = col.createElementsGetter();
//		ElementsGetter getter2 = col.createElementsGetter();
//		
//		System.out.println("Jedan element: " + getter1.getNextElement());
//		System.out.println("Jedan element: " + getter1.getNextElement());
//		System.out.println("Jedan element: " + getter2.getNextElement());
//		System.out.println("Jedan element: " + getter1.getNextElement());
//		System.out.println("Jedan element: " + getter2.getNextElement());
		
		
		
//      ---------------------------------------------------------------------------	
		
		
		
		//Collection col1 = new ArrayIndexedCollection();
		//Collection col2 = new ArrayIndexedCollection();
		Collection col1 = new LinkedListIndexedCollection();
		Collection col2 = new LinkedListIndexedCollection();
		
		col1.add("Ivo");
		col1.add("Ana");
		col1.add("Jasna");
		col2.add("Jasmina");
		col2.add("Stefanija");
		col2.add("Karmela");
		
		ElementsGetter getter1 = col1.createElementsGetter();
		ElementsGetter getter2 = col1.createElementsGetter();
		ElementsGetter getter3 = col2.createElementsGetter();
		
		System.out.println("Jedan element: " + getter1.getNextElement());
		System.out.println("Jedan element: " + getter1.getNextElement());
		System.out.println("Jedan element: " + getter2.getNextElement());
		System.out.println("Jedan element: " + getter3.getNextElement());
		System.out.println("Jedan element: " + getter3.getNextElement());
		
		
	}

}
