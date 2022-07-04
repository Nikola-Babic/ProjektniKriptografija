package controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class DeleteController {
	
	
	@FXML
	private TextField deletePath;
	@FXML
	private Button deleteBtn;
	
	@FXML
	public void deleteFile(ActionEvent event)
	{
		
		String fileName= deletePath.getText();
		String filePath = System.getProperty("user.dir") + File.separator + "UserDirectories" + File.separator + Main.currentUser + File.separator + fileName;
		String hashPath = System.getProperty("user.dir") + File.separator + "HashDirectory" + File.separator + Main.currentUser + File.separator + fileName;
		File file=new File(filePath);
		File hashFile=new File(hashPath);
		file.delete();
		hashFile.delete();
	
		
	}

}
