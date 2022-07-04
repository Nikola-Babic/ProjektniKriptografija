package controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.cert.CertificateException;

import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import util.CryptoException;
import util.EncryptDecrypt;
import application.Main;

public class ShareController {
	
	@FXML
	private TextField fileName;
	@FXML
	private TextField username;
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
	public void shareFile(ActionEvent event) throws CertificateException
	{
		
		String filePath = fileName.getText();
		System.out.println(filePath);
		String shareFileName=filePath.substring(filePath.lastIndexOf("\\")+1);
		System.out.println(shareFileName);
		Main.shareUser=username.getText();
		String cypherMethod=combo1.getSelectionModel().getSelectedItem().toString();
		String hashMethod=combo2.getSelectionModel().getSelectedItem().toString();
		File file=new File(System.getProperty("user.dir") + File.separator + "UserDirectories" + File.separator + Main.currentUser + File.separator + filePath);
		File shareFile=new File(System.getProperty("user.dir") + File.separator + "ShareDirectory" + File.separator + shareFileName);
		/*try {
			Files.copy(file.toPath(),shareFile.toPath());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}*/
		try {
			EncryptDecrypt.decrypt(file, shareFile);
		} catch (CryptoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			EncryptDecrypt.encrypt(shareFile, shareFile,cypherMethod);
		} catch (CryptoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO: DODATI KRIPTOVANJE NOVOG FAJLA
 catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	

}
