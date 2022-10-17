package hr.fer.oprpp1.custom.collections;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class CollectionTest {
	
	public void testAddNull(Collection c) {
		assertThrows(NullPointerException.class, () -> c.add(null));
	}

}
