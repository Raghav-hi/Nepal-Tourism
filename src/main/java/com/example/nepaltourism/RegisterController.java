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
        // Populate language combo
        languageCombo.getItems().setAll(LANGUAGES.values());

        // Add only Tourist and Guide to registerAsCombo
        registerAsCombo.getItems().clear();
        registerAsCombo.getItems().addAll(USERTYPE.Tourist, USERTYPE.Guide);

        // Default: Hide experience field (assuming Tourist is default)
        toggleExperienceField(USERTYPE.Tourist);

        // Add listener to show/hide YOE field based on selected user type
        registerAsCombo.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                toggleExperienceField(newValue);
            }
        });
    }

    @FXML
    private void toggleExperienceField(USERTYPE userType) {
        boolean isGuide =userType.equals(USERTYPE.Guide);

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
        USERTYPE userType=registerAsCombo.getValue();
        if(userType==null){
        showAlert("Select a user type !!");
        return;
        }
        String name=fullNameField.getText();
        String email=EmailField.getText();
        String phoneNumber=phoneField.getText();
        String password=passwordField.getText();
        LANGUAGES language=(languageCombo.getValue()==null)?LANGUAGES.English:languageCombo.getValue();
        if(name.trim().isEmpty()|| email.trim().isEmpty()||phoneNumber.trim().isEmpty()||password.trim().isEmpty()){
            showAlert("Empty Fields");
            return;
        }
        if(FileHandling.emailExists(userType,EmailField.getText())){
            showAlert("This email Already Exists!!");
            return;
        }
        if(userType.equals(USERTYPE.Guide)){
         int YOE=(expField.getText().trim().isEmpty())?0:Integer.parseInt(expField.getText());
            Guide guide= new Guide(FileHandling.getNextId(FileHandling.GuideFile), name, email, phoneNumber, password, language, YOE);
            FileHandling.WriteUser(USERTYPE.Guide,guide);


        }
        else{
            Tourist tourist=new Tourist(FileHandling.getNextId(FileHandling.TouristFile),
                    name,email,phoneNumber,password,language);
            FileHandling.WriteUser(USERTYPE.Tourist,tourist);
        }
        Navigator.Navigate(NAVIGATIONS.LOGINPAGE,(Stage) RegisterBtn.getScene().getWindow());
    }







    @FXML
    private void handleLoginLink(ActionEvent event) throws IOException {
        // Login screen transition logic
        System.out.println("Opening login screen...");
        Navigator.Navigate(NAVIGATIONS.LOGINPAGE, (Stage) ((Node) event.getSource()).getScene().getWindow());
    }

    public void showAlert(String message){
        Alert alert=new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Register Error");
        alert.setContentText(message);
        alert.showAndWait();
    }
}

