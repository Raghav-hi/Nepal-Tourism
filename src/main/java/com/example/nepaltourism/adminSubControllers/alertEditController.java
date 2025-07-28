package com.example.nepaltourism.adminSubControllers;

import com.example.nepaltourism.classes.Alerts;
import com.example.nepaltourism.classes.enums.NAVIGATIONS;
import com.example.nepaltourism.handlers.FileHandling;
import com.example.nepaltourism.handlers.Navigator;
import com.example.nepaltourism.handlers.ObjectFinder;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class alertEditController {

    @FXML private TextField monthsActiveField;
    @FXML private TextField messageField;



    @FXML
    public void initialize() throws IOException {
        monthsActiveField.textProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal.matches("[1-9]\\d*")) {
                monthsActiveField.setText(oldVal);
            }
        });
    }

    @FXML
    private void handleCancel() throws IOException {
        Navigator.Navigate(NAVIGATIONS.ADMINPAGE,(Stage) messageField.getScene().getWindow());
    }

    @FXML
    private void handleConfirmEdit() throws IOException {

        String message = messageField.getText();
        if(monthsActiveField.getText().trim().isEmpty()){monthsActiveField.setText("0");}
        int monthsActive=Integer.parseInt(monthsActiveField.getText());
        int id;
        if(message.isEmpty()){
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Empty field/s");
            alert.showAndWait();
            return;
        }
            id = FileHandling.getNextId(FileHandling.AlertsFile);
            Alerts alert=new Alerts(id,message,monthsActive);
            FileHandling.AddAlerts(alert);
        Navigator.Navigate(NAVIGATIONS.ADMINPAGE,(Stage) messageField.getScene().getWindow());
        }


}