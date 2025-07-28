package com.example.nepaltourism.Controllers;

import com.example.nepaltourism.classes.enums.NAVIGATIONS;
import com.example.nepaltourism.handlers.CacheHandler;
import com.example.nepaltourism.handlers.Navigator;
import com.example.nepaltourism.handlers.SessionHandler;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AttractionsViewController implements Initializable {
    public VBox vbox;

    public void goAlerts(ActionEvent actionEvent) throws IOException {
        Navigator.Navigate(NAVIGATIONS.ALERTPAGE,(Stage) vbox.getScene().getWindow());

    }

    public void onLogout(ActionEvent actionEvent) throws IOException {
        SessionHandler.getInstance().endSession();
        CacheHandler.ClearCache();
        Navigator.Navigate(NAVIGATIONS.LOGINPAGE,(Stage)vbox.getScene().getWindow());
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

    public void goFestivals(ActionEvent actionEvent) throws IOException{
        Navigator.Navigate(NAVIGATIONS.FESTIVALPAGE,(Stage) vbox.getScene().getWindow());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
