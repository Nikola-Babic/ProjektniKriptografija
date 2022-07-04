package controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.cert.CertificateException;

import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import util.CryptoException;
import util.EncryptDecrypt;
import util.HashAndCheck;

public class NewController {
	

	
	@FXML
	private TextField fileName;
	@FXML
	private TextArea fileText;
	@FXML
	private Button saveBtn;
	@FXML 
	private ComboBox combo1;
	@FXML 
	private ComboBox combo2;
	
	ObservableList<String> encryptList = FXCollections.observableArrayList("AES", "DES", "DESede");
	ObservableList<String> hashList = FXCollections.observableArrayList("MD5", "SHA256", "SHA512");
	
	@FXML
	private void initialize() {
		
		combo1.setValue("AES");
		combo1.setItems(encryptList);
		
		combo2.setValue("SHA256");
		combo2.setItems(hashList);
	}
	

	
	
	@FXML
	public void saveFile(ActionEvent event)
	{
		
		String newFileName = fileName.getText() + ".txt";
		File file=new File(System.getProperty("user.dir") + File.separator + "UserDirectories" + File.separator + Main.currentUser + File.separator + newFileName);
		String cypherMethod=combo1.getSelectionModel().getSelectedItem().toString();
		String hashMethod=combo2.getSelectionModel().getSelectedItem().toString();
		try {
			file.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String newFileText = fileText.getText();
		FileWriter fileWriter=null;
		try {
			fileWriter = new FileWriter(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PrintWriter printWriter = new PrintWriter(fileWriter);
		printWriter.print(newFileText);
		printWriter.close();
		try {
			EncryptDecrypt.encrypt(file, file,cypherMethod);
			HashAndCheck.hash(file, hashMethod);
		} catch (CryptoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CertificateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		// TODO: DODATI KRIPTOVANJE NOVOG FAJLA
		
		
	}


}
