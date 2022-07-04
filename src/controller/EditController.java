package controller;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import util.CryptoException;
import util.EncryptDecrypt;
import util.HashAndCheck;


public class EditController {
	
	
	@FXML
	private TextField fileNameBox;
	@FXML
	private TextArea fileText;
	@FXML
	private Button saveBtn;
	@FXML
	private Button openBtn;
	
	
	@FXML 
	public void openFile(ActionEvent event) throws Exception 
	{
		String fileName= fileNameBox.getText();
		String filePath = System.getProperty("user.dir") + File.separator + "UserDirectories" + File.separator + Main.currentUser + File.separator + fileName;
		File file=new File(filePath);
		if (HashAndCheck.checkHash(file))
		{
		try {
			EncryptDecrypt.decrypt(file, file);
		} catch (CryptoException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			String content = Files.readString(file.toPath(), StandardCharsets.US_ASCII);
			fileText.setText(content);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
	}
	
	@FXML
	public void editTXT(ActionEvent event) throws CertificateException
	{
		String newContent=fileText.getText();
		String fileName= fileNameBox.getText();
		String filePath = System.getProperty("user.dir") + File.separator + "UserDirectories" + File.separator + Main.currentUser + File.separator + fileName;
		File file=new File(filePath);
		try {
			Files.writeString(file.toPath(), newContent);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		try {
			EncryptDecrypt.encrypt(file, file, "AES");
			HashAndCheck.hash(file,"SHA256");
		} catch (CryptoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Enkripcija i Dekripcija kod edita
 catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	

}
