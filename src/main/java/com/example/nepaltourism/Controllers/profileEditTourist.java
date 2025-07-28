package com.example.nepaltourism.Controllers;

import com.example.nepaltourism.classes.Tourist;
import com.example.nepaltourism.classes.enums.LANGUAGES;
import com.example.nepaltourism.classes.enums.NAVIGATIONS;
import com.example.nepaltourism.classes.enums.USERTYPE;
import com.example.nepaltourism.handlers.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class profileEditTourist implements Initializable {

    @FXML public VBox vbox;
    @FXML
    private TextField nameField, emailField, phoneField;

    @FXML
    private TextField newPasswordField, oldPasswordField;

    @FXML
    private ComboBox<LANGUAGES> languageChoiceBox;

    private Tourist currentTourist;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        int id = SessionHandler.getInstance().getUserId();
        try {
            currentTourist = (Tourist) ObjectFinder.getUser(id, USERTYPE.Tourist);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (currentTourist != null) {
            nameField.setText(currentTourist.getName());
            emailField.setText(currentTourist.getEmail());
            phoneField.setText(currentTourist.getPhoneNumber());
            languageChoiceBox.getItems().setAll(LANGUAGES.values());
            languageChoiceBox.setValue(currentTourist.getLanguagePref());
        }
    }

    @FXML
    private void saveTouristEdits() throws IOException {
        if (currentTourist == null) {
            showAlert("Error", "Tourist not found.");
            return;
        }

        String enteredOldPassword = oldPasswordField.getText();
        if (!enteredOldPassword.equals(currentTourist.getPassword())) {
            showAlert("Incorrect Password", "The old password you entered is incorrect.");
            return;
        }

        String phone = phoneField.getText();

        if (!isValidPhone(phone)) {
            showAlert("Invalid Phone", "Phone number must be exactly 10 digits and numeric.");
            return;
        }


        String name = nameField.getText();
        String email = emailField.getText();

        if(!FileHandling.isEmail(email)){
            showAlert("Invalid Email","Invalid Email Value!!");
            return;
        }
        String password = newPasswordField.getText().isEmpty() ? currentTourist.getPassword() : newPasswordField.getText();
        LANGUAGES languagePref = languageChoiceBox.getValue();

        Tourist updatedTourist = new Tourist(
                currentTourist.getId(),
                name,
                email,
                phone,
                password,
                languagePref
        );

        FileHandling.editUser(USERTYPE.Tourist, currentTourist.getId(), updatedTourist);
        showAlert("Success", "Your information has been updated successfully.");
    }

    private boolean isValidPhone(String phone) {
        return phone != null && phone.matches("\\d{10}");
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    public void onLogout(ActionEvent actionEvent) throws IOException {
        SessionHandler.getInstance().endSession();
        Navigator.Navigate(NAVIGATIONS.LOGINPAGE,(Stage) nameField.getScene().getWindow());
    }

    @FXML
    public void onDeleteAccount(ActionEvent actionEvent)throws IOException {
        int id=SessionHandler.getInstance().getUserId();
        FileHandling.removeUser(USERTYPE.Tourist,id);
        DeletionHandler.onUserDelete(id,USERTYPE.Tourist);
        SessionHandler.getInstance().endSession();
        CacheHandler.ClearCache();
        Navigator.Navigate(NAVIGATIONS.LOGINPAGE,(Stage) nameField.getScene().getWindow());
    }

    public void goHome(ActionEvent actionEvent) throws IOException{
        Navigator.Navigate(NAVIGATIONS.TOURISTPAGE,(Stage) vbox.getScene().getWindow());
    }

    public void goAttraction(ActionEvent actionEvent) throws IOException{
        Navigator.Navigate(NAVIGATIONS.ATRRACTIONPAGE,(Stage) vbox.getScene().getWindow());
    }

    public void goBookings(ActionEvent actionEvent) throws IOException{
        Navigator.Navigate(NAVIGATIONS.MYBOOKINGPAGE,(Stage) vbox.getScene().getWindow());
    }

    public void goUserEdit(ActionEvent actionEvent) throws IOException{
        Navigator.Navigate(NAVIGATIONS.TOURISTUSERPAGE,(Stage)vbox.getScene().getWindow());
    }
}