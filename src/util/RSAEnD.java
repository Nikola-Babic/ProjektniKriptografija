package util;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import application.Main;

public class RSAEnD {
	
	public static void encrypt(File inputFile,boolean share) throws CertificateException, IOException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {
		File certFile=null;
		if (share) {
			certFile=new File(System.getProperty("user.dir") + File.separator + "openssl" + File.separator + "certs" + File.separator + Main.shareUser + ".crt");
		}
		else { 
			 certFile=new File(System.getProperty("user.dir") + File.separator + "openssl" + File.separator + "certs" + File.separator + Main.currentUser + ".crt");
		}
		InputStream inStream = new FileInputStream(certFile);
		CertificateFactory cf = CertificateFactory.getInstance("X.509");
		X509Certificate cert = (X509Certificate)cf.generateCertificate(inStream);
		PublicKey pubKey=cert.getPublicKey();
		Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);
        FileInputStream inputStream = new FileInputStream(inputFile);
        byte[] inputBytes = new byte[(int) inputFile.length()];
        inputStream.read(inputBytes);
         
        byte[] outputBytes = cipher.doFinal(inputBytes);
        FileOutputStream outputStream = new FileOutputStream(inputFile);
        outputStream.write(outputBytes);
        inputStream.close();
        outputStream.close();
		
	}
	
	public static void decrypt(File inputFile,boolean share) throws Exception {
		
		File keyFile=new File(System.getProperty("user.dir") + File.separator + "CurrentUserKey" + File.separator  + Main.currentUser + ".key");
		String key = new String(Files.readAllBytes(keyFile.toPath()), Charset.defaultCharset());
		String privateKeyPEM = key.replace("-----BEGIN PRIVATE KEY-----","").replaceAll("\n", "").replace("-----END PRIVATE KEY-----","");;
	
		byte[] encoded = Base64.getDecoder().decode(privateKeyPEM);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encoded);
		PrivateKey theKey= keyFactory.generatePrivate(keySpec);
		 
		Cipher cipher = Cipher.getInstance("RSA");
	    cipher.init(Cipher.DECRYPT_MODE, theKey);
	    FileInputStream inputStream = new FileInputStream(inputFile);
	    byte[] inputBytes = new byte[(int) inputFile.length()];
	    inputStream.read(inputBytes);
	         
	    byte[] outputBytes = cipher.doFinal(inputBytes);
	    FileOutputStream outputStream = new FileOutputStream(inputFile);
	    outputStream.write(outputBytes);
	    inputStream.close();
	    outputStream.close();
		 
	}
	
	

}
