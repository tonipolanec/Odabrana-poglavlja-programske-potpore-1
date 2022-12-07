package hr.fer.oprpp1.hw05.crypto;


import java.io.*;
import java.nio.file.*;
import java.security.*;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Scanner;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


public class Crypto {

	public Crypto() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws InvalidKeyException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchPaddingException {
		Scanner scanner = new Scanner(System.in);
		
		
		String action = args[0];	// checksha, encrypt, decrypt 	
		
		if (action.equals("checksha")) {
			checksha(args[1]);
		
		
		} else {
			
			if (!(action.equals("encrypt") || action.equals("decrypt"))) {
				System.out.println("Invalid arguments given!");
				scanner.close();
				return;
			}
				
			
			System.out.println("Please provide password as hex-encoded text (16 bytes, i.e. 32 hex-digits):");
			System.out.print("> ");
			String keyText = scanner.nextLine();
			
			System.out.println("Please provide initialization vector as hex-encoded text (32 hex-digits):");
			System.out.print("> ");
			String ivText = scanner.nextLine();
			
			scanner.close();
			
			boolean encrypt = action.equals("encrypt");
			
			
			SecretKeySpec keySpec = new SecretKeySpec(Util.hextobyte(keyText), "AES");
			AlgorithmParameterSpec paramSpec = new IvParameterSpec(Util.hextobyte(ivText));
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(encrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, keySpec, paramSpec);
			
			
			try {
				encrypt_decrypt(cipher, Paths.get(args[1]), Paths.get(args[2]));
				
				if (encrypt)
					System.out.println("Encyption completed. Generated file "+ args[2] +" based on file "+ args[1] +".");
				else
					System.out.println("Decryption completed. Generated file "+ args[2] +" based on file "+ args[1] +".");
				
			} catch (IllegalBlockSizeException | BadPaddingException | IOException e) {
				e.printStackTrace();
			}

			
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
		
		
		try(InputStream inputStream = new BufferedInputStream(Files.newInputStream(Paths.get(fileName)), 1024)) {
			
			while(inputStream.available() > 0) {
				byte[] someData = inputStream.readNBytes(1024);
				
				for(int i=0; i<someData.length; i++) {
					sha.update(someData[i]);
				}
			}
		} catch (IOException e) {
			System.out.println("Error while reading file.");
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
	
	
	

	private static void encrypt_decrypt(Cipher cipher, Path oldFile, Path newFile) throws IOException, IllegalBlockSizeException, BadPaddingException {
		
		InputStream inputStream = new BufferedInputStream(Files.newInputStream(oldFile), 1024);
		OutputStream outputStream = new BufferedOutputStream(Files.newOutputStream(newFile));
		
		
		while(inputStream.available() > 1024) {
			byte[] someData = inputStream.readNBytes(1024);
			byte[] cryptedData = cipher.update(someData);
			
			outputStream.write(cryptedData);
		}
		
		byte[] cryptedData = cipher.doFinal(inputStream.readNBytes(1024));
		outputStream.write(cryptedData);
		
		inputStream.close();
		outputStream.close();
		
		
		
	}
	
	
//	private static void decrypt(Cipher cipher, Path cryptedFile, Path newFile) throws IOException, IllegalBlockSizeException, BadPaddingException {
//		
//		InputStream inputStream = new BufferedInputStream(Files.newInputStream(cryptedFile), 1024);
//		OutputStream outputStream = new BufferedOutputStream(Files.newOutputStream(newFile));
//		
//		while(inputStream.available() > 1024) {
//			byte[] someData = inputStream.readNBytes(1024);
//			byte[] cryptedData = cipher.update(someData);
//			
//			outputStream.write(cryptedData);
//		}
//		
//		byte[] cryptedData = cipher.doFinal(inputStream.readNBytes(1024));
//		outputStream.write(cryptedData);
//		
//		inputStream.close();
//		outputStream.close();
//		
//	}

}
