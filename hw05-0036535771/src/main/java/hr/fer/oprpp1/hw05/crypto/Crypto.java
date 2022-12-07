package hr.fer.oprpp1.hw05.crypto;


import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Scanner;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


public class Crypto {

	public Crypto() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		
		// checksha, encrypt, decrypt 
		String action = args[0];

		
		
		switch(action) {
		
			case "checksha":
				checksha(args[1]);
				break;
				
			case "encrypt":
				try {
					encrypt(Paths.get(args[1]), Paths.get(args[2]));
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
				
			case "decrypt":
				decrypt(Paths.get(args[1]), Paths.get(args[2]));
				break;
				
			default:
				System.out.println("Wrong input!");
		}
		
		
		

		
		
		
		
		
		

		
		


	}
	


	private static void checksha(String fileName) {
		Scanner scanner = new Scanner(System.in);
		MessageDigest sha;
		
		try {
			sha = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e1) {
			e1.printStackTrace();
			scanner.close();
			return;
		}
		
		System.out.println("Please provide expected sha-256 digest for " + fileName);
		System.out.print("> ");
		
		String digest = scanner.nextLine();
		scanner.close();
		
		String hexUserDigest = Util.bytetohex(Util.hextobyte(digest));
		
		InputStream inputStream;
		try {
			inputStream = Files.newInputStream(Paths.get(fileName));
			
			while(inputStream.available() > 0) {
				byte[] someData = inputStream.readNBytes(1024);
				
				for(int i=0; i<someData.length; i++) {
					sha.update(someData[i]);
				}
			}
			inputStream.close();
			
		} catch (IOException e) {
			//e.printStackTrace();
			System.out.println("Error while reading file.");
			return;
		}
		
		
		byte[] computedDigest = sha.digest();
		String hexComputedDigest = Util.bytetohex(computedDigest);
		
		
		if (hexComputedDigest.equals(hexUserDigest)) {
			System.out.println("Digesting completed. Digest of "+ fileName +" matches expected digest.");
		
		} else {
			System.out.println("Digesting completed. Digest of "+ fileName +" does not match the expected digest.");
			System.out.println("Digest was: "+ hexComputedDigest);
		}
	}
	
	
	

	private static void encrypt(Path origFile, Path newCyptedFile) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException {
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("Please provide password as hex-encoded text (16 bytes, i.e. 32 hex-digits):");
		System.out.print("> ");
		String keyText = scanner.nextLine();
		
		System.out.println("Please provide initialization vector as hex-encoded text (32 hex-digits):");
		System.out.print("> ");
		String ivText = scanner.nextLine();
		
		scanner.close();
		
		
		SecretKeySpec keySpec = new SecretKeySpec(Util.hextobyte(keyText), "AES");
		AlgorithmParameterSpec paramSpec = new IvParameterSpec(Util.hextobyte(ivText));
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, keySpec, paramSpec);

		
		InputStream inputStream;
		try {
			inputStream = Files.newInputStream(origFile);
			
			while(inputStream.available() > 0) {
				byte[] someData = inputStream.readNBytes(1024);
				cipher.update(someData);
			}
			inputStream.close();
			
		} catch (IOException e) {
			System.out.println("Error while reading file.");
			return;
		}
		
		
		
		
		
		
	}
	
	
	private static void decrypt(Path cryptedFile, Path newFile) {
		// TODO Auto-generated method stub
		
	}

}
