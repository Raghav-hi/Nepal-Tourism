package com.example.nepaltourism;

import com.example.nepaltourism.classes.enums.NAVIGATIONS;
import com.example.nepaltourism.classes.enums.USERTYPE;
import com.example.nepaltourism.handlers.FileHandling;
import com.example.nepaltourism.handlers.Navigator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    public ComboBox<USERTYPE> UserSelectComboBox;
    @FXML
    public TextField EmailField;
    @FXML
    public TextField PasswordField;
    @FXML
    public Button loginBtn;

    @Override
    public void initialize(URL URL, ResourceBundle resourceBundle) {
        UserSelectComboBox.getItems().addAll(USERTYPE.values());
    }

    @FXML
    public void onLoginClick() throws IOException {
        String email = EmailField.getText().trim();
        String password = PasswordField.getText().trim();
        USERTYPE usertype = UserSelectComboBox.getValue();

        // Check for empty fields
        if (email.isEmpty() || password.isEmpty() || usertype == null) {
            showAlert("Validation Error", "All fields are required!");
            return;
        }

        // Validate email
        if (!FileHandling.isEmail(email)) {
            showAlert("Invalid Email", "Please enter a valid email address.");
            return;
        }
        switch(usertype){
            case Admin -> {
                if(FileHandling.authenticate(USERTYPE.Admin,email,password)){
                    System.out.println("Login Attempt: " + email + ", " + password + ", " + usertype);
                    Navigator.Navigate(NAVIGATIONS.ADMINPAGE,(Stage) loginBtn.getScene().getWindow());
                    return;
                }}
            case Guide -> {
                if(FileHandling.authenticate(USERTYPE.Guide,email,password)){
                    System.out.println("Login Attempt: " + email + ", " + password + ", " + usertype);
                    Navigator.Navigate(NAVIGATIONS.GUIDEPAGE,(Stage) loginBtn.getScene().getWindow());
                    return;
                }
            }
            case Tourist ->{
                if(FileHandling.authenticate(USERTYPE.Tourist,email,password)){
                    System.out.println("Login Attempt: " + email + ", " + password + ", " + usertype);
                    Navigator.Navigate(NAVIGATIONS.TOURISTPAGE,(Stage) loginBtn.getScene().getWindow());
                    return;
                }
            }
        }

        showAlert("Login Failed","Invalid email or password!");
    }


    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }



    public void onRegisterClick(ActionEvent actionEvent) throws IOException {
        Navigator.Navigate(NAVIGATIONS.REGISTERPAGE,(Stage)EmailField.getScene().getWindow());
    }
}
