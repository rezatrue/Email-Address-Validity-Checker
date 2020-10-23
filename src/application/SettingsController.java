package application;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SettingsController implements Initializable {
	@FXML
	private TextField txtuserName;
	@FXML
	private PasswordField txtpassword;
	@FXML
	private PasswordField txtdbServer;
	@FXML
	private Button saveBtn;

	private Preferences prefs;

	@FXML
	public void saveBtnAction() {
		prefs.put("evuser", txtuserName.getText());
		prefs.put("evpassword", txtpassword.getText());

		prefs.put("evdataserver", txtdbServer.getText());

		Stage stage = (Stage) txtuserName.getScene().getWindow();
		stage.close();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		prefs = Preferences.userRoot().node(MainController.prefsDb);
		txtuserName.setText(prefs.get("evuser", ""));
		txtpassword.setText(prefs.get("evpassword", ""));
		txtdbServer.setText(prefs.get("evdataserver", ""));		
	}

}
