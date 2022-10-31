package hr.fer.oprpp1.hw04.db;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.junit.jupiter.api.Test;

public class StudentDatabaseTest {

	List<String> lines;
	StudentDatabase sdb;
	
	public void init() {
		lines = new ArrayList<>();
		
		lines.add("0000000001	Akšamović	Marin	2");
		lines.add("0000000015	Glavinić Pecotić	Kristijan	4");
		lines.add("0000000031	Krušelj Posavec	Bojan	5");
	}
	
	@Test
	public void testConstructorGradeHigherThan5() {
		init();
		lines.add("0000000029	Kos-Grabar	Ivo	10");
	
		assertThrows(StudentRecordException.class, () -> new StudentDatabase(lines));
	}
	@Test
	public void testConstructorGradeLowerThan1() {
		init();
		lines.add("0000000029	Kos-Grabar	Ivo	0");
	
		assertThrows(StudentRecordException.class, () -> new StudentDatabase(lines));
	}
	@Test
	public void testConstructorDuplicateJMBAGs() {
		init();
		lines.add("0000000001	Kos-Grabar	Ivo	4");
	
		assertThrows(StudentRecordException.class, () -> new StudentDatabase(lines));
	}
	
	
	@Test
	public void testForJMBAG() {
		init();
		sdb = new StudentDatabase(lines);
		
		StudentRecord record = sdb.forJMBAG("0000000031");
		
		assertEquals(Objects.hash("0000000031"), record.hashCode());
	}

	@Test
	public void testFilterAllTrue() {
		init();
		sdb = new StudentDatabase(lines);
		
		List<StudentRecord> records = sdb.filter((record) -> true);
		
		assertEquals(3, records.size());	
	}
	@Test
	public void testFilterAllFalse() {
		init();
		sdb = new StudentDatabase(lines);
		
		List<StudentRecord> records = sdb.filter((record) -> false);
		
		assertEquals(0, records.size());	
	}
	
	
	
	

}
