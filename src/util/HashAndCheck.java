package util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.security.DigestInputStream;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.Scanner;

import org.apache.commons.codec.digest.*;

import application.Main;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class HashAndCheck {
	
	public static void hash(File inputFile, String Algo) throws InvalidKeyException, CertificateException, IllegalBlockSizeException, BadPaddingException, NoSuchPaddingException
	    {
		
		try {
			checkFile(inputFile);
			MessageDigest md = MessageDigest.getInstance(Algo);
			String hdigest = new DigestUtils(md).digestAsHex(inputFile);
			String filePath = inputFile.getAbsolutePath();
			String fileName=filePath.substring(filePath.lastIndexOf("\\")+1);
			File file=new File(System.getProperty("user.dir") + File.separator + "HashDirectory" + File.separator + Main.currentUser + File.separator + fileName);
			FileWriter fileWriter=null;
    		try {
    			fileWriter = new FileWriter(file);
    		} catch (IOException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    		PrintWriter printWriter = new PrintWriter(fileWriter);
    		printWriter.print(hdigest);
    		printWriter.close();
    		RSAEnD.encrypt(file, false);

		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		


		
	    }
	 
	public static boolean checkHash(File inputFile) throws Exception
	    {
	
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		MessageDigest md256 = MessageDigest.getInstance("SHA256");
		MessageDigest md512 = MessageDigest.getInstance("SHA512");
		String hdigest5 = new DigestUtils(md5).digestAsHex(inputFile);
		String hdigest256 = new DigestUtils(md256).digestAsHex(inputFile);
		String hdigest512 = new DigestUtils(md512).digestAsHex(inputFile);
		
		String filePath=inputFile.getAbsolutePath();
		String fileName=filePath.substring(filePath.lastIndexOf("\\")+1);
		File file=new File(System.getProperty("user.dir") + File.separator + "HashDirectory" + File.separator + Main.currentUser + File.separator + fileName);
		RSAEnD.decrypt(file, false);
		Scanner scanner = new Scanner(file);
    	String hashString = scanner.nextLine();
    	
    	if (hdigest5.equals(hashString))   {RSAEnD.encrypt(file, false); return true;}
    	if (hdigest256.equals(hashString)) {RSAEnD.encrypt(file, false); return true;}
    	if (hdigest512.equals(hashString)) {RSAEnD.encrypt(file, false); return true;}
    	RSAEnD.encrypt(file, false);
		return false;
	    }
	public static void checkFile(File inputFile) 
    {
		String filePath = inputFile.getAbsolutePath();
		String fileName=filePath.substring(filePath.lastIndexOf("\\")+1);
		File file=new File(System.getProperty("user.dir") + File.separator + "HashDirectory" + File.separator + Main.currentUser + File.separator + fileName);
		if (file.exists()) file.delete();
    }

}
