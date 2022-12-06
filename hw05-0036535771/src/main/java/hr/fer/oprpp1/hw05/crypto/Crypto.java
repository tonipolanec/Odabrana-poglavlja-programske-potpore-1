package hr.fer.oprpp1.hw05.crypto;


import java.util.Scanner;


public class Crypto {

	public Crypto() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		
//		String action = args[0];
//		String fileName = args[1];
		
//		String keyText = "";//… what user provided for password …
//		String ivText = "";//… what user provided for initialization vector …
//		SecretKeySpec keySpec = new SecretKeySpec(Util.hextobyte(keyText), "AES");
//		AlgorithmParameterSpec paramSpec = new IvParameterSpec(hextobyte(ivText));
//		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
//		cipher.init(encrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, keySpec, paramSpec);
		
		
//		String hex = scanner.nextLine();
//		
//		byte[] bajts = Util.hextobyte(hex);
//		
//		for(int i=0; i< bajts.length; i++)
//			System.out.println(bajts[i]);
		
		
		String hex = Util.bytetohex(new byte[] {1, -82, 34});
		System.out.println(hex);
		

	}

}
