package com.example.nepaltourism.adminSubControllers;

import com.example.nepaltourism.classes.Festival;
import com.example.nepaltourism.classes.enums.NAVIGATIONS;
import com.example.nepaltourism.handlers.FileHandling;
import com.example.nepaltourism.handlers.Navigator;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Objects;

public class festivalEditController {


    @FXML
    private TextField nameField;
    @FXML
    private DatePicker startDateField;
    @FXML
    private DatePicker endDateField;
    @FXML
    private TextField discountField;


    @FXML
    public void initialize() throws IOException {

        startDateField.setValue(LocalDate.now());
        endDateField.setValue(LocalDate.now());
        discountField.setText("0");
        discountField.textProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal.matches("-?\\d*(\\.\\d*)?")) {
                discountField.setText(oldVal);
            }
        });

    }



    @FXML
    private void handleCancel() throws IOException {
        Navigator.Navigate(NAVIGATIONS.ADMINPAGE,(Stage) nameField.getScene().getWindow());
    }

    @FXML
    private void handleConfirmEdit() throws IOException {
        String name = nameField.getText();
        LocalDate startDate=startDateField.getValue();
        LocalDate endDate=endDateField.getValue();
        if(discountField.getText().trim().isEmpty()){discountField.setText("0");}
        double discount=Double.parseDouble(discountField.getText());


        int id;
        if (name.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Empty field/s");
            alert.showAndWait();
            return;
        }
        id = FileHandling.getNextId(FileHandling.FestivalsFile);
        Festival festival=new Festival(id,name,startDate,endDate,discount);
        FileHandling.addFestival(festival);
        Navigator.Navigate(NAVIGATIONS.ADMINPAGE,(Stage) nameField.getScene().getWindow());
    }
}