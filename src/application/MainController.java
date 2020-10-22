package application;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

public class MainController implements Initializable{
	@FXML
	private TextField textField; 
	@FXML
	private Label emailText;
	@FXML
	private Label statusText; 

	private String mailAddress;
	
	public void genrerateRandom(ActionEvent event){
		
		mailAddress = textField.getText();
		Validate_code_source code_source = new Validate_code_source();
		boolean status = false;
		
		emailText.setText(mailAddress);
		statusText.setText("Checking .. .. ..");
		textField.setText("");
		
		if(mailAddress!="") 
			status = code_source.checkEmail(mailAddress);
		
		if(status){
			statusText.setText(" Valid ");
			emailText.setTextFill(Color.web("#0076a3"));
			statusText.setTextFill(Color.web("#0076a3"));
		}else{
			statusText.setText(" INVALID ");
			emailText.setTextFill(Color.web("#ff0000"));
			statusText.setTextFill(Color.web("#ff0000"));
		}

	}
	
	@FXML
    void openBrowser(ActionEvent actionEvent){
				
		    if(Desktop.isDesktopSupported())
		    {
		        try {
		            Desktop.getDesktop().browse(new URI("http://technogearup.com/"));
		        } catch (IOException e1) {
		            e1.printStackTrace();
		        } catch (URISyntaxException e1) {
		            e1.printStackTrace();
		        }
		    }

    }	
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
	}

}
