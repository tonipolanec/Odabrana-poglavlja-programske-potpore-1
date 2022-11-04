package hr.fer.oprpp1.hw04.db;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;


public class QueryFilterTest {

	QueryFilter filter;
	StudentDatabase db;
	
	private void initDB() throws IOException {
		Path filePath = Paths.get("src/test/resources/database.txt");
		db = new StudentDatabase(Files.readAllLines(filePath));
		
	}
	

	
	
	@Test
	public void testDirect() throws IOException {
		initDB();
		
		List<ConditionalExpression> list = new ArrayList<>();
		list.add(new ConditionalExpression(FieldValueGetters.JMBAG, "0000000010", ComparisonOperators.EQUALS));
	
		List<StudentRecord> records = db.filter(new QueryFilter(list));

		assertEquals(1, records.size());
		
		assertEquals("Dokleja", records.get(0).getLastName());
	
	}
	
	@Test
	public void testLess() throws IOException {
		initDB();
		
		List<ConditionalExpression> list = new ArrayList<>();
		list.add(new ConditionalExpression(FieldValueGetters.JMBAG, "0000000010", ComparisonOperators.LESS));
	
		List<StudentRecord> records = db.filter(new QueryFilter(list));

		assertEquals(9, records.size());
	}
	
	@Test
	public void testGreaterOrEquals() throws IOException {
		initDB();
		
		List<ConditionalExpression> list = new ArrayList<>();
		list.add(new ConditionalExpression(FieldValueGetters.JMBAG, "0000000062", ComparisonOperators.GREATER_OR_EQUALS));
	
		List<StudentRecord> records = db.filter(new QueryFilter(list));

		assertEquals(2, records.size());
	}
	
	@Test
	public void testEqualsFirstName() throws IOException {
		initDB();
		
		List<ConditionalExpression> list = new ArrayList<>();
		list.add(new ConditionalExpression(FieldValueGetters.FIRST_NAME, "Luka", ComparisonOperators.EQUALS));
	
		List<StudentRecord> records = db.filter(new QueryFilter(list));

		assertEquals(2, records.size());
	}
	@Test
	public void testNotEqualsFirstName() throws IOException {
		initDB();
		
		List<ConditionalExpression> list = new ArrayList<>();
		list.add(new ConditionalExpression(FieldValueGetters.FIRST_NAME, "Luka", ComparisonOperators.NOT_EQUALS));
	
		List<StudentRecord> records = db.filter(new QueryFilter(list));

		assertEquals(61, records.size());
	}
	
	
	
	@Test
	public void testMoreExpressions() throws IOException {
		initDB();
		
		List<ConditionalExpression> list = new ArrayList<>();
		list.add(new ConditionalExpression(FieldValueGetters.JMBAG, "0000000010", ComparisonOperators.LESS));
		list.add(new ConditionalExpression(FieldValueGetters.FIRST_NAME, "Jusufadis", ComparisonOperators.EQUALS));
	
		List<StudentRecord> records = db.filter(new QueryFilter(list));

		assertEquals(1, records.size());
	}
	
	@Test
	public void testMoreExpressions2() throws IOException {
		initDB();
		
		List<ConditionalExpression> list = new ArrayList<>();
		list.add(new ConditionalExpression(FieldValueGetters.JMBAG, "0000000010", ComparisonOperators.LESS));
		list.add(new ConditionalExpression(FieldValueGetters.FIRST_NAME, "Jusufadis", ComparisonOperators.NOT_EQUALS));
	
		List<StudentRecord> records = db.filter(new QueryFilter(list));

		assertEquals(8, records.size());
	}
	

}
