package application;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

public class MainController implements Initializable{
	@FXML
	private TextField textField; 
	@FXML
	private Label labelText; 

	private String mailAddress;
	
	public void genrerateRandom(ActionEvent event){
		labelText.setText("checking .. .. .. ..");
		boolean status = false;		
		Validate_code_source code_source = new Validate_code_source();
		mailAddress = textField.getText();
		
		if(mailAddress!="") 
			status = code_source.checkEmail(mailAddress);
		
		if(status){
			labelText.setText(mailAddress +" is valid");
			labelText.setTextFill(Color.web("#0076a3"));
		}else{
			labelText.setText(mailAddress +" is INVALID");
			labelText.setTextFill(Color.web("#ff0000"));
		}

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
	}

}
