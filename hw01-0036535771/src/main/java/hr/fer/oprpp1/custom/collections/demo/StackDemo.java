package hr.fer.oprpp1.custom.collections.demo;

import java.util.Arrays;
import hr.fer.oprpp1.custom.collections.ObjectStack;


/** 
 * Shows postfix evaluation of expressions using stack.
 * 
 * @see hr.fer.oprpp1.custom.collections.ObjectStack
 * @author Toni Polanec
 */
public class StackDemo {

	public static void main(String[] args) {
		
		String expression = args[0];
		String[] elements = expression.split("\\s+");
	
		ObjectStack stack = new ObjectStack();
		boolean error = false;
		
		for(int i=0; i<elements.length; i++) {
			
			try {
				int num = Integer.parseInt(elements[i]);
				stack.push(num);
			
			}catch (NumberFormatException ex) { // So its not a number
				int b = (Integer) stack.pop();
				int a = (Integer) stack.pop();
				int res;
				
				switch (elements[i]) {
				case "+":
					res = a+b;
					stack.push(res);
					break;
				case "-":
					res = a-b;
					stack.push(res);
					break;
				case "*":
					res = a*b;
					stack.push(res);
					break;
				case "/":
					if (b == 0) {
						error = true;
						break;
					}	
					res = a/b;
					stack.push(res);
					break;
				case "%":
					if (b == 0) {
						error = true;
						break;
					}
					res = a%b;
					stack.push(res);
					break;
				default:
					
				}				
			}
		}
		
		if (error)
			System.out.println("ERROR: Division by zero");
		else if (stack.size() != 1)
			System.out.println("ERROR: Expression is invalid");
		else
			System.out.println(stack.pop());
		
	}

}
