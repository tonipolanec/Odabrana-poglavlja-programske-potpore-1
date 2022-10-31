package hr.fer.oprpp1.hw04.db;

import java.util.Objects;

public class StudentRecord {
	
	private String jmbag;
	private String lastName;
	private String firstName;
	private int finalGrade;
	
	public StudentRecord(String jmbag, String lastName, String firstName, int finalGrade) {
		this.jmbag = jmbag;
		this.lastName = lastName;
		this.firstName = firstName;
		
		if (finalGrade < 1 && finalGrade > 5)
			throw new StudentRecordException("Final grade error!");
		
		this.finalGrade = finalGrade;
	}
	
	

	public String getJmbag() {
		return jmbag;
	}

	public String getLastName() {
		return lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public int getFinalGrade() {
		return finalGrade;
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StudentRecord other = (StudentRecord) obj;
		return Objects.equals(jmbag, other.jmbag);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(jmbag);
	}

}
