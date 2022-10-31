package hr.fer.oprpp1.hw04.db;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class FieldValueGettersTest {

	StudentRecord record;

	public void init() {
		record = new StudentRecord("0036535771", "Polanec", "Toni", 4);
	}

	@Test
	public void testFirstName() {
		init();

		assertArrayEquals("Toni".toCharArray(), FieldValueGetters.FIRST_NAME.get(record).toCharArray());
	}

	@Test
	public void testLastName() {
		init();

		assertArrayEquals("Polanec".toCharArray(), FieldValueGetters.LAST_NAME.get(record).toCharArray());
	}

	@Test
	public void testJmbag() {
		init();

		assertArrayEquals("0036535771".toCharArray(), FieldValueGetters.JMBAG.get(record).toCharArray());
	}

}
