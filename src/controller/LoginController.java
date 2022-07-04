package controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.CRL;
import java.security.cert.CRLException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateFactory;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.X509Certificate;

import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import util.BCrypt;

public class LoginController {

	@FXML
	private TextField username;
	@FXML
	private TextField certificate;
	@FXML
	private PasswordField password;
	
	@FXML
	private Button loginBtn;
	@FXML
	private Button registerBtn;
	
	@FXML
	public void login(ActionEvent event) throws CRLException
	{
		String loginUser = username.getText() + ".txt";
		String loginPassWrd = password.getText();
		File loginFile=new File(System.getProperty("user.dir") + File.separator + "Users" + File.separator + loginUser);
		boolean allGood=false;

		if (checkCertificate(certificate.getText())) {
		if (loginFile.exists() && certificate.getText().startsWith(username.getText())) 
		
		{
			try {
				BufferedReader br= new BufferedReader(new  FileReader(loginFile));
				String hashedPassword=br.readLine();
				br.close();
				
				if (BCrypt.checkpw(loginPassWrd, hashedPassword))
					{
					System.out.println("Iste sifre");
					allGood=true;
					Main.currentUser=username.getText();
					extractPubKey(certificate.getText());
					Parent root;
			        try {
			            root = FXMLLoader.load(getClass().getClassLoader().getResource("view/MainScreen.fxml"));
			           
			            Main.stage.setTitle("Main");
			            Main.stage.setScene(new Scene(root, 500, 550));
			            Main.stage.show();
			            
			        }
			        catch (IOException e) {
			            e.printStackTrace();
			        }
					}
				else System.out.println("Pogresna lozinka");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (CertificateException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} 
		}
		else System.out.println("Ne postoji username!");
	}
		else System.out.println("Certifikat nije validan!");
 
	}
	
	@FXML
	public void register(ActionEvent event)
	{
		System.out.println(System.getProperty("user.dir"));
		String fileName = username.getText() + ".txt";
		File file=new File(System.getProperty("user.dir") + File.separator + "Users" + File.separator + fileName);
		if (file.exists()) {
			System.out.println("Username vec postoiji!");
			return;
		}
		try {
			file.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String passWrd = password.getText();
		String hashedPassword= BCrypt.hashpw(passWrd, BCrypt.gensalt(10));
		
		FileWriter fileWriter=null;
		try {
			fileWriter = new FileWriter(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PrintWriter printWriter = new PrintWriter(fileWriter);
		printWriter.print(hashedPassword);
		printWriter.close();
		

		File userDir=new File(System.getProperty("user.dir") + File.separator + "UserDirectories" + File.separator + username.getText());
		File infoDir=new File(System.getProperty("user.dir") + File.separator + "EncInfo" + File.separator + username.getText());
		File shareInfoDir=new File(System.getProperty("user.dir") + File.separator + "ShareInfo" + File.separator + username.getText());
		File hashDir=new File(System.getProperty("user.dir") + File.separator + "HashDirectory" + File.separator + username.getText());
		File pubDir=new File(System.getProperty("user.dir") + File.separator + "PublicKey" + File.separator + username.getText());
		userDir.mkdir();
		infoDir.mkdir();
		shareInfoDir.mkdir();
		hashDir.mkdir();
		pubDir.mkdir();
		
	}
	
	public boolean checkCertificate(String certName) throws CRLException
	{
		
		File certFile=new File(System.getProperty("user.dir") + File.separator + "openssl" + File.separator + "certs" + File.separator + certName);
		File crlFile =new File(System.getProperty("user.dir") + File.separator + "openssl" + File.separator + "crl" + File.separator + "crl.pem");
		try {
			InputStream inStream = new FileInputStream(certFile);
			InputStream crlStream= new FileInputStream(crlFile);
		    CertificateFactory cf = CertificateFactory.getInstance("X.509");
		    X509Certificate cert = (X509Certificate)cf.generateCertificate(inStream);
		    CRL crl=cf.generateCRL(crlStream);
		    try {cert.checkValidity();}
		    catch (CertificateExpiredException e) {return false;}
		    catch(CertificateNotYetValidException e) {return false;}
		    if(crl.isRevoked(cert)) return false;
		   
		    String issuer= cert.getIssuerX500Principal().getName();
		    if(!(issuer.contains("#160e70726f6a656b746e696361406361"))) return false;
		    
		    
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CertificateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	    return true;
	}
	
	public void extractPubKey(String certName) throws CertificateException, IOException
	{
		
		File certFile=new File(System.getProperty("user.dir") + File.separator + "openssl" + File.separator + "certs" + File.separator + certName);
		File privateKeyFile=new File(System.getProperty("user.dir") + File.separator + "openssl" + File.separator + "private" + File.separator + Main.currentUser + ".key");
		File currentUserPrivate=new File(System.getProperty("user.dir") + File.separator + "CurrentUserKey" + File.separator + Main.currentUser + ".key");
		Files.copy(privateKeyFile.toPath(), currentUserPrivate.toPath());
		currentUserPrivate.deleteOnExit();
		InputStream inStream = new FileInputStream(certFile);
		CertificateFactory cf = CertificateFactory.getInstance("X.509");
		X509Certificate cert = (X509Certificate)cf.generateCertificate(inStream);
		PublicKey pubKey=cert.getPublicKey();
		String pubKeyName="pubkey.txt";
		File pubKeyFile=new File(System.getProperty("user.dir") + File.separator + "PublicKey" + File.separator + Main.currentUser + File.separator + pubKeyName);
		FileWriter fileWriter=null;
		try {
			fileWriter = new FileWriter(pubKeyFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PrintWriter printWriter = new PrintWriter(fileWriter);
		printWriter.print(pubKey);
		printWriter.close();    
	}
	
	
	
	// TODO Implementirati certifikate i uraditi zastitu lozinki
}
