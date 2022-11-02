package hr.fer.oprpp1.hw04.db;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

public class LexerTest {


	@Test
	public void testSimple() {
		Lexer l = new Lexer(" attribute=\"string\"");
		List<Token> tokens = l.getTokens();

		assertEquals("attribute", tokens.get(0).getValue());
		assertEquals("=", tokens.get(1).getValue());
		assertEquals("\"string\"", tokens.get(2).getValue());

		assertEquals(TokenType.ATTRIBUTE, tokens.get(0).getType());
		assertEquals(TokenType.OPERATOR, tokens.get(1).getType());
		assertEquals(TokenType.STRING, tokens.get(2).getType());

	}

	@Test
	public void testTabs() {
		Lexer l = new Lexer("\tlastName\t=\t\"Blazic\"");
		List<Token> tokens = l.getTokens();

		assertEquals("lastName", tokens.get(0).getValue());
		assertEquals("=", tokens.get(1).getValue());
		assertEquals("\"Blazic\"", tokens.get(2).getValue());

		assertEquals(TokenType.ATTRIBUTE, tokens.get(0).getType());
		assertEquals(TokenType.OPERATOR, tokens.get(1).getType());
		assertEquals(TokenType.STRING, tokens.get(2).getType());

	}

	@Test
	public void testAnd() {
		Lexer l = new Lexer(" firstName>\"A\" aNd jmbag<\"000002101\"");
		List<Token> tokens = l.getTokens();

		assertEquals("firstName", tokens.get(0).getValue());
		assertEquals(">", tokens.get(1).getValue());
		assertEquals("\"A\"", tokens.get(2).getValue());
		assertEquals("aNd", tokens.get(3).getValue());
		assertEquals("jmbag", tokens.get(4).getValue());
		assertEquals("<", tokens.get(5).getValue());
		assertEquals("\"000002101\"", tokens.get(6).getValue());

		assertEquals(TokenType.ATTRIBUTE, tokens.get(0).getType());
		assertEquals(TokenType.OPERATOR, tokens.get(1).getType());
		assertEquals(TokenType.STRING, tokens.get(2).getType());
		assertEquals(TokenType.OPERATOR_AND, tokens.get(3).getType());
		assertEquals(TokenType.ATTRIBUTE, tokens.get(4).getType());
		assertEquals(TokenType.OPERATOR, tokens.get(5).getType());
		assertEquals(TokenType.STRING, tokens.get(6).getType());
	}
	
	@Test
	public void testLike() {
		Lexer l = new Lexer(" firstName LIKE\"A*\"");
		List<Token> tokens = l.getTokens();

		assertEquals("firstName", tokens.get(0).getValue());
		assertEquals("LIKE", tokens.get(1).getValue());
		assertEquals("\"A*\"", tokens.get(2).getValue());


		assertEquals(TokenType.ATTRIBUTE, tokens.get(0).getType());
		assertEquals(TokenType.OPERATOR, tokens.get(1).getType());
		assertEquals(TokenType.STRING, tokens.get(2).getType());
	}
	
}
