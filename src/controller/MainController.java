package controller;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import util.CryptoException;
import util.EncryptDecrypt;
import util.HashAndCheck;

public class MainController {
	
	@FXML
	private Button newBtn;
	@FXML
	private Button uploadBtn;
	@FXML
	private Button downloadBtn;
	@FXML
	private Button editBtn;
	@FXML
	private Button deleteBtn;
	@FXML
	private Button shareBtn;
	@FXML
	private Button viewBtn;
	@FXML
	private Button openBtn;
	@FXML
	private Button showBtn;
	@FXML
	private Button openFileBtn;
	@FXML
	private Button showDirectoryBtn;
	@FXML
	private Button createDirectoryBtn;
	@FXML
	private TextField showDirectoryTXT;
	@FXML
	private TextField fileOpen;
	@FXML
	private ListView<String> lista=new ListView<String>();
	

	@FXML
	public void newFile (ActionEvent event)
	{
	
		 Parent root;
	        try {
	            root = FXMLLoader.load(getClass().getClassLoader().getResource("view/NewPopup.fxml"));
	            Stage stage = new Stage();
	            stage.setTitle("New Text File");
	            stage.setScene(new Scene(root, 450, 300));
	            stage.show();
	           
	        }
	        catch (IOException e) {
	            e.printStackTrace();
	        }
		
	}
	
	@FXML
	public void upload (ActionEvent event)
	{
	
		 Parent root;
	        try {
	            root = FXMLLoader.load(getClass().getClassLoader().getResource("view/UploadPopup.fxml"));
	            Stage stage = new Stage();
	            stage.setTitle("Upload a File");
	            stage.setScene(new Scene(root, 300, 300));
	            stage.show();
	           
	        }
	        catch (IOException e) {
	            e.printStackTrace();
	        }
		
	}
	
	@FXML
	public void download (ActionEvent event)
	{
	
		 Parent root;
	        try {
	            root = FXMLLoader.load(getClass().getClassLoader().getResource("view/DownloadPopup.fxml"));
	            Stage stage = new Stage();
	            stage.setTitle("Download a File");
	            stage.setScene(new Scene(root, 300, 300));
	            stage.show();
	           
	        }
	        catch (IOException e) {
	            e.printStackTrace();
	        }
		
	}
	@FXML
	public void delete (ActionEvent event)
	{
	
		 Parent root;
	        try {
	            root = FXMLLoader.load(getClass().getClassLoader().getResource("view/DeletePopup.fxml"));
	            Stage stage = new Stage();
	            stage.setTitle("Delete a File");
	            stage.setScene(new Scene(root, 300, 300));
	            stage.show();
	           
	        }
	        catch (IOException e) {
	            e.printStackTrace();
	        }
		
	}
	@FXML
	public void editTXT (ActionEvent event)
	{
	
		 Parent root;
	        try {
	            root = FXMLLoader.load(getClass().getClassLoader().getResource("view/EditPopup.fxml"));
	            Stage stage = new Stage();
	            stage.setTitle("Edit a Text File");
	            stage.setScene(new Scene(root, 450, 450));
	            stage.show();
	           
	        }
	        catch (IOException e) {
	            e.printStackTrace();
	        }
		
	}
	@FXML
	public void shareFile (ActionEvent event)
	{
	
		 Parent root;
	        try {
	            root = FXMLLoader.load(getClass().getClassLoader().getResource("view/SharePopup.fxml"));
	            Stage stage = new Stage();
	            stage.setTitle("Share a File");
	            stage.setScene(new Scene(root, 450, 300));
	            stage.show();
	           
	        }
	        catch (IOException e) {
	            e.printStackTrace();
	        }
		
	}
	@FXML
	public void viewShare (ActionEvent event)
	{
	
		 Parent root;
	        try {
	            root = FXMLLoader.load(getClass().getClassLoader().getResource("view/ViewSharePopup.fxml"));
	            Stage stage = new Stage();
	            stage.setTitle("Share Folder");
	            stage.setScene(new Scene(root, 450, 450));
	            stage.show();
	           
	        }
	        catch (IOException e) {
	            e.printStackTrace();
	        }
	  
		
		
	}
	@FXML
	public void openFile (ActionEvent event) throws CertificateException
	{
	  
		String fileOpenName=fileOpen.getText();
		String tmpFileName="tmp"+fileOpen.getText();
		File fileToOpen=new File(System.getProperty("user.dir") + File.separator + "UserDirectories" + File.separator + Main.currentUser + File.separator + fileOpenName);
		File tmpToOpen=new File(System.getProperty("user.dir") + File.separator + "UserDirectories" + File.separator + Main.currentUser + File.separator + tmpFileName);
		
		try {
			if (HashAndCheck.checkHash(fileToOpen)) 
			{
			EncryptDecrypt.decrypt(fileToOpen, tmpToOpen);
			Desktop.getDesktop().open(tmpToOpen);
			}
			else System.out.println("HASH NE ODGOVARA!");
		} catch (CryptoException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	@FXML
	public void openDirectory (ActionEvent event)
	{
	  
		String directoryName=showDirectoryTXT.getText();
		File directoryToOpen=new File(System.getProperty("user.dir") + File.separator + "UserDirectories" + File.separator + Main.currentUser + File.separator + directoryName);
		File[] fileList = directoryToOpen.listFiles();
		lista.getItems().clear();
		for (File f : fileList) 
		    {
		    	String fileName=f.getName();
		    	System.out.println(fileName);
		    	lista.getItems().add(fileName);
		    }
	}
	@FXML
	public void createDirectory (ActionEvent event)
	{
	  
		String directoryName=showDirectoryTXT.getText();
		File directoryToCreate=new File(System.getProperty("user.dir") + File.separator + "UserDirectories" + File.separator + Main.currentUser + File.separator + directoryName);
		directoryToCreate.mkdir();
	}
	
	@FXML
	public void showDirectoryContent(ActionEvent event)
	{
		File userDirectory=new File(System.getProperty("user.dir") + File.separator + "UserDirectories" + File.separator + Main.currentUser);
	    File[] fileList = userDirectory.listFiles();
		lista.getItems().clear();
		for (File f : fileList) 
	    {
	    	String fileName=f.getName();
	    	if (fileName.startsWith("tmp")) f.delete();
	    }
		for (File f : fileList) 
		    {
		    	String fileName=f.getName();
		    	lista.getItems().add(fileName);
		    }
		
	}
	
	
	
	
}
