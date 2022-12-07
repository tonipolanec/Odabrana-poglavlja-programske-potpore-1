package hr.fer.oprpp1.hw05.crypto;

public class Util {
	
	/** 
	 * Method for converting hexadecimal numbers in byte array.
	 * 
	 * @param hexString hexadecimal number we want to convert to byte[]
	 * @return byte array converted from hexString
	 * @throws IllegalArgumentException if string not valid
	 */
	public static byte[] hextobyte(String hexString) {	
		if (hexString.length() % 2 != 0)
			throw new IllegalArgumentException("Invalid hexadecimal number format!");
		
		byte[] byteArray = new byte[hexString.length() / 2];
		
		for(int i=0; i<hexString.length(); i+=2) {
			char h1 = hexString.charAt(i);
			char h2 = hexString.charAt(i+1);
			
			h1 = checkSymbol(h1);
			h2 = checkSymbol(h2);
			
			int b1 = giveBits(h1);
			int b2 = giveBits(h2);
			
			b1 = b1 << 4;
			int b = b1 | b2;
			
			// we need to do 2 complement
			if (b > 127) {
				b = ~b;
				b++;
				b *= -1;
			}
			
			byteArray[i/2] = (byte) b;		
		}
		
		return byteArray;
	}
	
	
	private static char checkSymbol(char c) {	
		int cint = (int) c;
		
		// c is number: GOOD
		if (cint >= '0' && cint <= '9') {
			return c;
			
		}else { // c is alphabetical
			if (cint < 'a')	{
				c += 32;
				cint = (int) c;
			}
			
			if(cint >= 'a' && cint <= 'f') 
				return c;	// c is alphabetical: a,b,c,d,e,f GOOD
		}
		
		throw new IllegalArgumentException("Invalid symbols in hexadecimal number!");
	}
	
	
	private static int giveBits(char c) {		
		int cint = (int) c;	
		
		// c is numeric
		if (cint <= '9') 
			return cint - '0';
		// c is alpha
		return cint - 'a' + 10;
	}
	
	
	
	
	
	
	/**
	 * Method for converting byte array into hexadecimal numbers.
	 * 
	 * @param bytearray we want to convert into hexadecimal number
	 * @return hexadecimal number converted from byte array
	 */
	public static String bytetohex(byte[] bytearray) {
		String hex = "";
		
		for(int i=0; i<bytearray.length; i++) {
			int byteInt = (int) bytearray[i];
			
			int b1 = byteInt & 0xF0;
			b1 = b1 >>> 4;
			int b2 = byteInt & 0x0F;
			
			
			if(b1 < 10) hex += "" + b1;
			else hex += "" + (char)('a' + b1 - 10); 
			
			if(b2 < 10) hex += "" + b2;
			else hex += "" + (char)('a' + b2 - 10); 
		}
		
		return hex;
	}
	
	
	
	
	

}
