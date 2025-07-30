package com.example.nepaltourism;

import com.example.nepaltourism.classes.Guide;
import com.example.nepaltourism.classes.Tourist;
import com.example.nepaltourism.classes.enums.LANGUAGES;
import com.example.nepaltourism.classes.enums.NAVIGATIONS;
import com.example.nepaltourism.classes.enums.USERTYPE;
import com.example.nepaltourism.handlers.FileHandling;
import com.example.nepaltourism.handlers.Navigator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class  RegisterController implements Initializable {

    @FXML
    public Button RegisterBtn;
    @FXML
    private TextField fullNameField;
    @FXML
    private TextField phoneField;
    @FXML
    private TextField EmailField;
    @FXML
    private TextField passwordField;
    @FXML
    private ComboBox<USERTYPE> registerAsCombo;
    @FXML
    private ComboBox<LANGUAGES> languageCombo;
    @FXML
    private Label expLabel;
    @FXML
    private TextField expField;  // Changed to match FXML id

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        languageCombo.getItems().setAll(LANGUAGES.values());

        registerAsCombo.getItems().clear();
        registerAsCombo.getItems().addAll(USERTYPE.Tourist, USERTYPE.Guide);

        toggleExperienceField(USERTYPE.Tourist);  // Hide experience by default

        registerAsCombo.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                toggleExperienceField(newValue);
            }
        });
    }

    @FXML
    private void toggleExperienceField(USERTYPE userType) {
        boolean isGuide = userType.equals(USERTYPE.Guide);

        expLabel.setVisible(isGuide);
        expLabel.setManaged(isGuide);
        expField.setVisible(isGuide);
        expField.setManaged(isGuide);

        if (!isGuide) {
            expField.clear();
        }
    }

    @FXML
    private void handleRegister() throws IOException {
        // Get inputs
        USERTYPE userType = registerAsCombo.getValue();
        String name = fullNameField.getText().trim();
        String email = EmailField.getText().trim();
        String phoneNumber = phoneField.getText().replaceAll("\\s", "");  // remove all spaces
        String password = passwordField.getText().trim();
        LANGUAGES language = (languageCombo.getValue() == null) ? LANGUAGES.English : languageCombo.getValue();

        // Basic validations
        if (userType == null) {
            showAlert("Select a user type.");
            return;
        }

        if (name.isEmpty() || email.isEmpty() || phoneNumber.isEmpty() || password.isEmpty()) {
            showAlert("All fields must be filled.");
            return;
        }

        if (!name.matches("[A-Za-z\\s]+")) {
            showAlert("Name must contain only letters and spaces.");
            return;
        }

        if (!email.matches("[\\w.%+-]+@[\\w.-]+\\.[A-Za-z]{2,6}")) {
            showAlert("Invalid email format.");
            return;
        }

        if (FileHandling.emailExists(userType, email)) {
            showAlert("This email already exists.");
            return;
        }

        if (!phoneNumber.matches("\\d{10}")) {
            showAlert("Phone number must be exactly 10 digits.");
            return;
        }


        if (password.length() < 8 || !password.matches(".*[!@#$%^&()].*")) {
            showAlert("Password must be at least 8 characters and should include a special character (!@#$%^&*).");
            return;
        }

        // Experience validation (Guide only)
        int YOE = 0;
        if (userType.equals(USERTYPE.Guide)) {
            String yoeText = expField.getText().trim();
            if (yoeText.isEmpty() || !yoeText.matches("\\d+")) {
                showAlert("Experience must be a valid number.");
                return;
            }
            YOE = Integer.parseInt(yoeText);

            Guide guide = new Guide(FileHandling.getNextId(FileHandling.GuideFile), name, email, phoneNumber, password, language, YOE);
            FileHandling.WriteUser(USERTYPE.Guide, guide);
        } else {
            Tourist tourist = new Tourist(FileHandling.getNextId(FileHandling.TouristFile), name, email, phoneNumber, password, language);
            FileHandling.WriteUser(USERTYPE.Tourist, tourist);
        }

        // Success - navigate to login
        Navigator.Navigate(NAVIGATIONS.LOGINPAGE, (Stage) RegisterBtn.getScene().getWindow());
    }

    @FXML
    private void handleLoginLink(ActionEvent event) throws IOException {
        Navigator.Navigate(NAVIGATIONS.LOGINPAGE, (Stage) ((Node) event.getSource()).getScene().getWindow());
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Registration Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
}
}