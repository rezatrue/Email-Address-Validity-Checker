package application;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

import api.ApiClient;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Pair;
import models.ProfileEmail;

public class MainController implements Initializable{
	
	public static final String prefsDb = "EVDB"; 
	public static String uid;
	private Preferences prefs = null;
	private ApiClient apiClient;
	private ProfileEmail profileEmail;
	
	@FXML
	private TextField profileLinkField;
	@FXML
	private TextField emailField; 
	@FXML
	private Label emailText;
	@FXML
	private Label statusText; 
	@FXML
	private Button checkBtn;
	
	
	@FXML
	public void genrerateRandom(ActionEvent event){
		profileEmail = new ProfileEmail();
		profileEmail.setUserId(uid);
		profileEmail.setProfileLink(profileLinkField.getText());
		profileEmail.setEmailAddress(emailField.getText());

		if(profileEmail.getProfileLink().length() < 1 || profileEmail.getEmailAddress().length() < 1 ) 
			return;
		
		Validate_code_source code_source = new Validate_code_source();
		boolean status = false;
		
		emailText.setText(profileEmail.getEmailAddress());
		statusText.setText("Checking .. .. ..");
		emailField.setText("");
		profileLinkField.setText("");
		
		//if(mailAddress!="") 
		status = code_source.checkEmail(profileEmail.getEmailAddress());
		
		if(status){
			statusText.setText(" Valid ");
			emailText.setTextFill(Color.web("#0076a3"));
			statusText.setTextFill(Color.web("#0076a3"));
			profileEmail.setEmailStatus("Valid");
		}else{
			statusText.setText(" INVALID ");
			emailText.setTextFill(Color.web("#ff0000"));
			statusText.setTextFill(Color.web("#ff0000"));
			profileEmail.setEmailStatus("Invalid");
		}
		
		apiClient.upload(profileEmail);
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
	
	@FXML
    void openSetting(ActionEvent actionEvent){
		System.out.println("Setting Button");
		try {
			Parent parent = FXMLLoader.load(getClass().getResource("/application/Settings.fxml"));
			Stage stage = new Stage();
			stage.setTitle("Settings");
			stage.setScene(new Scene(parent));
			stage.setResizable(false);
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		//checkBtn = new Button();
		String msg = loginDialoag();

//		msg = "welcome test";
		if (!msg.toLowerCase().contains("welcome")) {
			System.out.println(msg); 
			checkBtn.setDisable(true);
			//Platform.exit();
		}
			
	}
	

	private String loginDialoag() {
		prefs = Preferences.userRoot().node(prefsDb);
		apiClient = new ApiClient();
		String msg = "";

		// Create the custom dialog.
		Dialog<Pair<String, String>> dialog = new Dialog<>();
		dialog.setTitle("Email Varifier Login");
		dialog.setHeaderText("Please Enter your Credentials");
		
		File file = new File("assets/img/login.png");
        Image imageLock = new Image(file.toURI().toString());
        ImageView lockView = new ImageView();
        lockView.setImage(imageLock);
        lockView.setFitHeight(75);
        lockView.setFitWidth(75);
        dialog.setGraphic(lockView);
		
		
		// Set the button types.
		ButtonType loginButtonType = new ButtonType("Login", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

		// Create the username and password labels and fields.
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));

		TextField username = new TextField();
		username.setPromptText("Username");
		username.setText(prefs.get("evuser", ""));
		PasswordField password = new PasswordField();
		password.setPromptText("password");
		password.setText(prefs.get("evpassword", ""));

		grid.add(new Label("Username:"), 0, 0);
		grid.add(username, 1, 0);
		grid.add(new Label("Password:"), 0, 1);
		grid.add(password, 1, 1);

		// Enable/Disable login button depending on whether a username was
		// entered.
		Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
		loginButton.setDisable(true);

		// Do some validation (using the Java 8 lambda syntax).
		username.textProperty().addListener((observable, oldValue, newValue) -> {
			loginButton.setDisable(newValue.trim().isEmpty());
		});

		dialog.getDialogPane().setContent(grid);

		// Request focus on the username field by default.
		Platform.runLater(() -> username.requestFocus());

		// Convert the result to a username-password-pair when the login button
		// is clicked.
		dialog.setResultConverter(dialogButton -> {
			if (dialogButton == loginButtonType) {
				return new Pair<>(username.getText(), password.getText());
			}
			return null;
		});

		Optional<Pair<String, String>> result = dialog.showAndWait();

		result.ifPresent(usernamePassword -> {
			System.out.println("Username=" + usernamePassword.getKey() + ", Password=" + usernamePassword.getValue());
		});

		return msg = apiClient.userAuth(username.getText(), password.getText());

	}

}
