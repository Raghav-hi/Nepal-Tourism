package com.example.nepaltourism.Controllers;

import com.example.nepaltourism.classes.enums.NAVIGATIONS;
import com.example.nepaltourism.handlers.Navigator;
import com.example.nepaltourism.handlers.SessionHandler;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class AlertViewController {
    @FXML
    private VBox vbox;
    @FXML
    public void onClickLogout() throws IOException {
        SessionHandler.getInstance().endSession();
        Alert alert=new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Logged Out");
        alert.show();
        PauseTransition delay = new PauseTransition(Duration.seconds(5));
        delay.setOnFinished(e -> alert.close());
        delay.play();
        Navigator.Navigate(NAVIGATIONS.LOGINPAGE,(Stage)vbox.getScene().getWindow());
    }

    public void onHome(ActionEvent actionEvent) throws IOException {
        Navigator.Navigate(NAVIGATIONS.ADMINPAGE,(Stage) vbox.getScene().getWindow());
    }

    public void onAttractions(ActionEvent actionEvent) throws IOException {
        Navigator.Navigate(NAVIGATIONS.ATTRACTIONCONTROLPAGE,(Stage) vbox.getScene().getWindow());
    }

    public void onBookings(ActionEvent actionEvent) throws IOException{
        Navigator.Navigate(NAVIGATIONS.BOOKINGCONTROLPAGE,(Stage) vbox.getScene().getWindow());

    }

    public void onTourists(ActionEvent actionEvent) throws IOException{
        Navigator.Navigate(NAVIGATIONS.TOURISTCONTROLPAGE,(Stage) vbox.getScene().getWindow());

    }

    public void onGuides(ActionEvent actionEvent) throws IOException{
        Navigator.Navigate(NAVIGATIONS.GUIDECONTROLPAGE,(Stage) vbox.getScene().getWindow());

    }

    public void profileEdit(ActionEvent actionEvent) throws IOException{
        Navigator.Navigate(NAVIGATIONS.ADMINUSERPAGE,(Stage) vbox.getScene().getWindow());

    }
}
