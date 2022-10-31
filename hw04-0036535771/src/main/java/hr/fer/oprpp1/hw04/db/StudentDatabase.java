package hr.fer.oprpp1.hw04.db;

import java.util.*;

public class StudentDatabase {

	private List<String> lines;
	private List<StudentRecord> records;
	private Map<String, StudentRecord> index;
	
	public StudentDatabase(List<String> rows) {
		lines = rows;
		getRecords();
	}

	
	/** Gets all record from given lines. Creates index for searching records.
	 * 
	 * @throws StudentRecordException if there are duplicate jmbags, or final grade is in wrong format or range
	 */
	private void getRecords() {
		records = new ArrayList<>();
		index = new HashMap<>();
		
		List<String> jmbags = new ArrayList<>();
		
		
		String jmbag, last, first;
		int grade; 
		
		for (String line : lines) {
			String[] elements = line.split("\t", 0);
			jmbag = elements[0];
			last = elements[1];
			first = elements[2];
			
			try {
				grade = Integer.parseInt(elements[3]);
			} catch (NumberFormatException e) {
				throw new StudentRecordException("Grade not in number format!");
			}
			if (grade < 1 || grade > 5)
				throw new StudentRecordException("Grade not in range [1, 5]!");
			
			
			if (jmbags.contains(jmbag))
				throw new StudentRecordException("Duplicate JMBAGs!");
			jmbags.add(jmbag);
			
			
			StudentRecord record = new StudentRecord(jmbag, last, first, grade);
			records.add(record);
			index.put(jmbag, record);
		}
		
	}
	
	public StudentRecord forJMBAG(String jmbag) {
		return index.get(jmbag);
	}
	
	
	public List<StudentRecord> filter(IFilter filter){
		List<StudentRecord> acceptedRecords = new ArrayList<>();
		
		for (StudentRecord record : records) {
			if (filter.accepts(record))
				acceptedRecords.add(record);
		}
		
		return acceptedRecords;
	}
	

}
