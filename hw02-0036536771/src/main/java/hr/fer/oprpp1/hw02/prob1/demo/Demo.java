package hr.fer.oprpp1.hw02.prob1.demo;

import  hr.fer.oprpp1.hw02.prob1.*;

public class Demo {

	public static void main(String[] args) {
		
		String ulaz = "Ovo je 123ica, ab57.\nKraj";
		
		Lexer lexer = new Lexer(ulaz);
		
		while(true) {
			try {
				Token t = lexer.nextToken();
				System.out.println(String.format("(%s, %s)", t.getType(), t.getValue()));
				
			} catch (LexerException ex) {
				System.out.println(ex.getMessage());
				break;
			}
		}

	}

}
