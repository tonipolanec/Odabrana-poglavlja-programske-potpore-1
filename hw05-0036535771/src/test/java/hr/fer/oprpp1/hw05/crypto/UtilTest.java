package hr.fer.oprpp1.hw05.crypto;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class UtilTest {

	@Test
	public void testHextobyteExample() {
		byte[] expected = {1, -82, 34};
		assertArrayEquals(expected, Util.hextobyte("01Ae22"));
	}
	
	
	@Test
	public void testHextobyteInvalidSymbol1() {
		assertThrows(IllegalArgumentException.class, () -> Util.hextobyte("01az22"));
	}
	@Test
	public void testHextobyteInvalidSymbol2() {
		assertThrows(IllegalArgumentException.class, () -> Util.hextobyte("Gb22"));
	}
	@Test
	public void testHextobyteInvalidSymbol3() {
		assertThrows(IllegalArgumentException.class, () -> Util.hextobyte("01aC2P"));
	}
	@Test
	public void testHextobyteOddSymbols1() {
		assertThrows(IllegalArgumentException.class, () -> Util.hextobyte("ac2"));
	}
	@Test
	public void testHextobyteOddSymbols2() {
		assertThrows(IllegalArgumentException.class, () -> Util.hextobyte("1"));
	}
	
	@Test
	public void testHextobyteZeroLengthString() {
		assertEquals(0, Util.hextobyte("").length);
	}
	
	
	
	
	@Test
	public void testBytetohexExample() {
		String expected = "01ae22";
		assertEquals(expected, Util.bytetohex(new byte[] {1, -82, 34}));
	}
	
	@Test
	public void testBytetohexZeroLengthArray() {
		assertEquals("", Util.bytetohex(new byte[] {}));
	}

}
