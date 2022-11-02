package hr.fer.oprpp1.hw04.db;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class QueryParserTest {

	QueryParser qp;
	
	
	@Test
	public void testSize() {
		assertThrows(ParserException.class, () -> new QueryParser(" firstName"));
		assertThrows(ParserException.class, () -> new QueryParser(" firstName ="));
		assertDoesNotThrow(() -> new QueryParser(" firstName = \"Toni\""));
		assertThrows(ParserException.class, () -> new QueryParser(" firstName = \"Toni\" AND"));
		assertThrows(ParserException.class, () -> new QueryParser(" firstName = \"Toni\" AND lastName"));
		assertThrows(ParserException.class, () -> new QueryParser(" firstName = \"Toni\" AND lastName ="));
		assertDoesNotThrow(() -> new QueryParser(" firstName = \"Toni\" AND lastName = \"Polanec\""));
	}
	
	@Test
	public void testValidAttributes() {
		assertDoesNotThrow(() -> new QueryParser(" jmbag=\"string\""));
		assertDoesNotThrow(() -> new QueryParser(" firstName=\"string\""));
		assertDoesNotThrow(() -> new QueryParser(" lastName=\"string\""));
	}
	@Test
	public void testInvalidAttributes() {
		assertThrows(ParserException.class, () -> new QueryParser(" attribute=\"string\""));
		assertThrows(ParserException.class, () -> new QueryParser(" jmbbbbag=\"string\""));
		assertThrows(ParserException.class, () -> new QueryParser(" first_name=\"string\""));
		assertThrows(ParserException.class, () -> new QueryParser(" lAstNaMe=\"string\""));
	}
	
	@Test
	public void testInvalidSeparation() {
		assertThrows(ParserException.class, () -> new QueryParser(" firstName = \"Toni\" lastName = \"Polanec\" ="));
	}
	
	@Test
	public void testDemo1() {
		qp = new QueryParser(" jmbag \t =\"0123456789\" ");
		
		assertTrue(qp.isDirectQuery());
		assertEquals("0123456789", qp.getQueriedJMBAG());
		assertEquals(1, qp.getQuery().size());
	}
	
	@Test
	public void testDemo2() {
		qp = new QueryParser("jmbag=\"0123456789\" and lastName>\"J\"");
		
		assertFalse(qp.isDirectQuery());
		assertThrows(IllegalStateException.class, () -> qp.getQueriedJMBAG());
		assertEquals(2, qp.getQuery().size());
	}
	
	
	
	
	
}
