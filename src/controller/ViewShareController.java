package controller;


import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.security.cert.CertificateException;

import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import util.CryptoException;
import util.EncryptDecrypt;


public class ViewShareController {
	
	@FXML
	private Button  showBtn;
	@FXML
	private Button openFileBtn;
	@FXML
	private TextField fileOpen;
	@FXML
	public ListView<String> shareList=new ListView<String>();
    
	File shareDirectory=new File(System.getProperty("user.dir") + File.separator + "ShareDirectory");
    File[] shareArray= shareDirectory.listFiles();
    boolean start=true;
    
    
	public void showShareContent(ActionEvent event)
	{
		File userDirectory=new File(System.getProperty("user.dir") + File.separator + "ShareDirectory");
	    File[] fileList = userDirectory.listFiles();
		shareList.getItems().clear();
		for (File f : fileList) 
	    {
	    	String fileName=f.getName();
	    	if (fileName.startsWith("tmp")) f.delete();
	    }
		for (File f : fileList) 
		    {
		    	String fileName=f.getName();
		    	shareList.getItems().add(fileName);
		    }
	}
	
	public void openFile (ActionEvent event)
	{
	  
		String fileOpenName=fileOpen.getText();
		String tmpFileName="tmp"+fileOpenName;
		File fileToOpen=new File(System.getProperty("user.dir") + File.separator + "ShareDirectory" + File.separator + fileOpenName);
		File fileToOpenTmp=new File(System.getProperty("user.dir") + File.separator + "ShareDirectory" + File.separator + tmpFileName);
		try {
			EncryptDecrypt.decrypt(fileToOpen, fileToOpenTmp);
		} catch (CryptoException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (CertificateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			Desktop.getDesktop().open(fileToOpenTmp);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}
