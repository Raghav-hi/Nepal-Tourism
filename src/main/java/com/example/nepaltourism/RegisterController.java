package com.example.nepaltourism;

import com.example.nepaltourism.classes.enums.NAVIGATIONS;
import com.example.nepaltourism.handlers.Navigator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class RegisterController {
    @FXML public Button RegisterBtn;
    @FXML private TextField fullNameField;
    @FXML private TextField phoneField;
    @FXML private TextField EmailField;
    @FXML private PasswordField passwordField;
    @FXML private ComboBox<String> registerAsCombo;
    @FXML private ComboBox<String> languageCombo;
    @FXML private Label expLabel;
    @FXML private TextField expField;  // Changed to match FXML id

    @FXML
    public void initialize() {
        // Set default value for registerAsCombo
        registerAsCombo.getSelectionModel().selectFirst();

        // Initialize experience field as hidden
        toggleExperienceField("Tourist");

        // Add listener to registerAsCombo
        registerAsCombo.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldVal, newVal) -> toggleExperienceField(newVal)
        );
    }

    private void toggleExperienceField(String userType) {
        boolean isGuide = "Guide".equals(userType);

        // Set both visibility and managed properties
        expLabel.setVisible(isGuide);
        expLabel.setManaged(isGuide);
        expField.setVisible(isGuide);
        expField.setManaged(isGuide);

        // Clear field when hidden
        if (!isGuide) {
            expField.clear();
        }
    }

    @FXML
    private void handleRegister() {
        // Registration logic here
        System.out.println("Registering...");
        System.out.println("User Type: " + registerAsCombo.getValue());
        if ("Guide".equals(registerAsCombo.getValue())) {
            System.out.println("Experience: " + expField.getText());
        }
    }

    @FXML
    private void handleLoginLink(ActionEvent event) throws IOException {
        // Login screen transition logic
        System.out.println("Opening login screen...");
        Navigator.Navigate(NAVIGATIONS.LOGINPAGE, (Stage) ((Node) event.getSource()).getScene().getWindow());
    }
}