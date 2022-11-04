package hr.fer.oprpp1.hw04.db;

import java.util.ArrayList;
import java.util.List;

/**
 * Lexer gets all tokens to parser from some text (query).
 * 
 * @author Toni Polanec
 *
 */
public class Lexer {

	private String query;
	private char[] input;
	private int index;
	private List<Token> tokens;
	private int startIndex, endIndex;

	public Lexer(String query) {
		this.query = query;
		tokens = new ArrayList<>();
		index = 0;
		
		processQuery();
	}

	public List<Token> getTokens() {
		return tokens;
	}

	
	/**
	 * Recognizes all tokens and storage them in private list <code>tokens</code>.
	 */
	private void processQuery() {
		
		input = query.toCharArray();
		
		startIndex = index;
		endIndex = index;
		String value = "";
		TokenType currentType = TokenType.NULL;
		
		while(index < input.length) {
			skipWhitespaces();
			if (!(index < input.length))
				return;
			
			// attribute
			if (Character.isLetter(input[index]) && currentType != TokenType.STRING) {
				// goes as long there is letters to read
				// stops when it gets to space ' ', '\t' or start of an operator '<', '>', '=', '!'
				currentType = TokenType.ATTRIBUTE;
				
				if (index + 1 == input.length || 
					input[index+1] == '<' || input[index+1] == '>' || input[index+1] == '!' || input[index+1] == '=' || 
					input[index+1] == ' ' || input[index+1] == '\t' || input[index+1] == '"') {
					
					endIndex = index+1;	
					value = query.substring(startIndex, endIndex);
					
					
					// we need to check for LIKE operator and AND operator
					if (value.equals("LIKE")) {
						Token t = new Token(value, TokenType.OPERATOR);
						tokens.add(t);
						
					} else if (value.toLowerCase().equals("and")) {
						//value = value.toLowerCase();
						Token t = new Token(value, TokenType.OPERATOR_AND);
						tokens.add(t);
						
						
					} else {
//						This is parser's job!
//						if (!value.equals("jmbag") && !value.equals("firstName") && !value.equals("lastName"))
//							throw new ParserException("Invalid attribute!");
							
						Token t = new Token(value, currentType);
						tokens.add(t);
					}
						
					startIndex = endIndex;
//					break;
				}
				index++;
			
			// string literal
			}else if (input[index] == '"' || currentType == TokenType.STRING) {
				// goes until closing "
				
				currentType = TokenType.STRING;
				
				if (index == input.length-1 || input[index+1] == '"') {
					if (index == input.length-1)
						endIndex = index + 1;
					else 
						endIndex = ++index + 1; 
					value = query.substring(startIndex, endIndex);
					
					if (value.charAt(0) != '"' || value.charAt(value.length()-1) != '"')
						throw new ParserException("Invalid string literal!");
					
					Token t = new Token(value, currentType);
					tokens.add(t);
					
					currentType = TokenType.NULL;
					startIndex = endIndex;
//					break;
				}
				index++;
				
			// operators >, <, >=, <=, =, !=
			} else if (input[index] == '<' || input[index] == '>' || 
					input[index] == '!' || input[index] == '=') {
				currentType = TokenType.OPERATOR;
				 
				// gets <, >, <=, >=
				if (input[index] == '<' || input[index] == '>') {
					if (index+1 >= input.length || input[index+1] != '=')
						endIndex = ++index;
					else if (input[index+1] == '=') {
						endIndex = index +2;
						index += 2;
					}	
					
					value = query.substring(startIndex, endIndex);
					
					Token t = new Token(value, currentType);
					tokens.add(t);
					
					startIndex = index;
//					break;
				}
				
				// gets =
				else if (input[index] == '=') {
					endIndex = ++index;
					
					value = query.substring(startIndex, endIndex);
					
					Token t = new Token(value, currentType);
					tokens.add(t);
					
					startIndex = index;
//					break;
				}
				
				// gets !=
				else if (input[index] == '!') {
					
					if (index+1 >= input.length || input[index+1] != '=') {
						throw new ParserException("Wrong operator !=.");
					
					} else if (input[index+1] != '=') {
						endIndex = index+2;
						index += 2;
						
						value = query.substring(startIndex, endIndex);
						
						Token t = new Token(value, currentType);
						tokens.add(t);
						
						startIndex = index;
//						break;
					
					} else {
						throw new ParserException("Wrong operator !=.");
					}
				}
				
			} else {
				throw new ParserException("Invalid query!");
			}
			
			
		}
		
		
	}
	
	
	/**<p> Skips all whitespaces up to next element. </p>*/
	private void skipWhitespaces() {
		boolean skipped = false;
		
		while(index < input.length) {
			char c = input[index];
			
			if (c=='\t' || c==' ') {
				index++;
				skipped = true;
				continue;
			}
			if (skipped)
				startIndex = index;
			break;
		}
	}
	
	
	
	
	

}
