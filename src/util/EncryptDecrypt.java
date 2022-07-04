package util;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.util.Base64;
import java.util.Scanner;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import application.Main;



public class EncryptDecrypt {
	 
	    public static void encrypt(File inputFile, File outputFile,String Algo)
	            throws Exception {
	        doCrypto(Cipher.ENCRYPT_MODE,inputFile, outputFile, Algo);
	    }
	 
	    public static void decrypt(File inputFile, File outputFile)
	            throws Exception {
	        doCrypto(Cipher.DECRYPT_MODE, inputFile, outputFile, "AES");
	    }
	 
	    private static void doCrypto(int cipherMode,File inputFile,
	            File outputFile, String Algo) throws Exception {
	        try {
	        	Key key = null;
	        	File encInfo=null;
	        	if (cipherMode==Cipher.ENCRYPT_MODE)
	        	{
	        	
	        	SecureRandom rand = new SecureRandom();
	        	KeyGenerator generator = KeyGenerator.getInstance(Algo);
	        	if (Algo.equals("AES")) generator.init(256, rand);
	        	if (Algo.equals("DES")) generator.init(56, rand);
	        	if (Algo.equals("DESede")) generator.init(168, rand);
	        	key = generator.generateKey();
	        	String FileName=inputFile.getName();
	        	int dotIndex=FileName.indexOf(".");
	        	String customName=FileName.substring(0, dotIndex)+".txt";
	        	String keyEnc=Base64.getEncoder().encodeToString(key.getEncoded());
	        	if (outputFile.getAbsolutePath().contains("ShareDirectory")) {
	        	encInfo=new File(System.getProperty("user.dir") + File.separator + "ShareInfo" + File.separator + Main.shareUser + File.separator + customName);
	        	}
	        	else {
	        	encInfo=new File(System.getProperty("user.dir") + File.separator + "EncInfo" + File.separator + Main.currentUser + File.separator + customName);
	        	}
	        	
	        	FileWriter fileWriter=null;
	    		try {
	    			fileWriter = new FileWriter(encInfo);
	    		} catch (IOException e) {
	    			// TODO Auto-generated catch block
	    			e.printStackTrace();
	    		}
	    		PrintWriter printWriter = new PrintWriter(fileWriter);
	    		printWriter.print(Algo);
	    		printWriter.print("\n");
	    		printWriter.print(keyEnc);
	    		printWriter.close();
	    		if (outputFile.getAbsolutePath().contains("ShareDirectory")) RSAEnD.encrypt(encInfo, true);
	    		else RSAEnD.encrypt(encInfo, false);
	        	}
	        	
	        	else 
	        	{
	        		String FileName=inputFile.getName();
		        	int dotIndex=FileName.indexOf(".");
		        	String customName=FileName.substring(0, dotIndex)+".txt";
		        	if (inputFile.getAbsolutePath().contains("ShareDirectory")) {
			        	encInfo=new File(System.getProperty("user.dir") + File.separator + "ShareInfo" + File.separator + Main.currentUser + File.separator + customName);
			        	Main.shareUser=Main.currentUser;
			        }
			        else{
			        	encInfo=new File(System.getProperty("user.dir") + File.separator + "EncInfo" + File.separator + Main.currentUser + File.separator + customName);
			        }
		        	RSAEnD.decrypt(encInfo, true);
		        	Scanner scanner = new Scanner(encInfo);
		        	Algo = scanner.nextLine();
		        	String keyEnc=scanner.nextLine();
		        	byte[] decodedKey = Base64.getDecoder().decode(keyEnc);
		        	key = new SecretKeySpec(decodedKey, 0, decodedKey.length, Algo); 
		        	if (inputFile.getAbsolutePath().contains("ShareDirectory")) RSAEnD.encrypt(encInfo, true);
		    		else RSAEnD.encrypt(encInfo, false);

		        	
	        		
	        	}
	            Cipher cipher = Cipher.getInstance(Algo);
	            cipher.init(cipherMode, key);
	            FileInputStream inputStream = new FileInputStream(inputFile);
	            byte[] inputBytes = new byte[(int) inputFile.length()];
	            inputStream.read(inputBytes);
	             
	            byte[] outputBytes = cipher.doFinal(inputBytes);
	             
	            FileOutputStream outputStream = new FileOutputStream(outputFile);
	            outputStream.write(outputBytes);
	             
	            inputStream.close();
	            outputStream.close();
	             
	        } catch (NoSuchPaddingException | NoSuchAlgorithmException
	                | InvalidKeyException | BadPaddingException
	                | IllegalBlockSizeException | IOException ex) {
	            throw new CryptoException("Error encrypting/decrypting file", ex);
	        }
	    }


}