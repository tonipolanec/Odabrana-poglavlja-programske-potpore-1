package hr.fer.oprpp1.hw04.db;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class QueryParserTest {

	QueryParser qp;
	
	
	@Test
	public void testSize() {
		assertThrows(ParserException.class, () -> new QueryParser("query firstName"));
		assertThrows(ParserException.class, () -> new QueryParser("query firstName ="));
		assertDoesNotThrow(() -> new QueryParser("query firstName = \"Toni\""));
		assertThrows(ParserException.class, () -> new QueryParser("query firstName = \"Toni\" AND"));
		assertThrows(ParserException.class, () -> new QueryParser("query firstName = \"Toni\" AND lastName"));
		assertThrows(ParserException.class, () -> new QueryParser("query firstName = \"Toni\" AND lastName ="));
		assertDoesNotThrow(() -> new QueryParser("query firstName = \"Toni\" AND lastName = \"Polanec\""));
	}
	
	@Test
	public void testValidAttributes() {
		assertDoesNotThrow(() -> new QueryParser("query jmbag=\"string\""));
		assertDoesNotThrow(() -> new QueryParser("query firstName=\"string\""));
		assertDoesNotThrow(() -> new QueryParser("query lastName=\"string\""));
	}
	@Test
	public void testInvalidAttributes() {
		assertThrows(ParserException.class, () -> new QueryParser("query attribute=\"string\""));
		assertThrows(ParserException.class, () -> new QueryParser("query jmbbbbag=\"string\""));
		assertThrows(ParserException.class, () -> new QueryParser("query first_name=\"string\""));
		assertThrows(ParserException.class, () -> new QueryParser("query lAstNaMe=\"string\""));
	}
	
	@Test
	public void testInvalidSeparation() {
		assertThrows(ParserException.class, () -> new QueryParser("query firstName = \"Toni\" lastName = \"Polanec\" ="));
	}
	
	@Test
	public void testDemo1() {
		qp = new QueryParser("query jmbag \t =\"0123456789\" ");
		
		assertTrue(qp.isDirectQuery());
		assertEquals("0123456789", qp.getQueriedJMBAG());
		assertEquals(1, qp.getQuery().size());
	}
	
	@Test
	public void testDemo2() {
		qp = new QueryParser("query jmbag=\"0123456789\" and lastName>\"J\"");
		
		assertFalse(qp.isDirectQuery());
		assertThrows(IllegalStateException.class, () -> qp.getQueriedJMBAG());
		assertEquals(2, qp.getQuery().size());
	}
	
	
	
	
	
}
