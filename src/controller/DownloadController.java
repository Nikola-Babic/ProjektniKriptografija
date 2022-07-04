package controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.cert.CertificateException;

import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import util.CryptoException;
import util.EncryptDecrypt;

public class DownloadController {
	
	
	@FXML
	private TextField fileNameBox;
	@FXML
	private TextField destinationPath;
	@FXML
	private Button saveBtn;
	
	@FXML
	public void downloadFile(ActionEvent event) throws Exception
	{
		
		String newFilePath = destinationPath.getText();
		String fileName= fileNameBox.getText();
		String filePath = System.getProperty("user.dir") + File.separator + "UserDirectories" + File.separator + Main.currentUser + File.separator + fileName;
		File file=new File(filePath);
		File newfile=new File(newFilePath + File.separator + fileName);
		try {
			EncryptDecrypt.decrypt(file, newfile);
		} catch (CryptoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CertificateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		

		
		// TODO: DEKRIPTOVANJE NOVOG FAJLA
		
		
	}
	

}
