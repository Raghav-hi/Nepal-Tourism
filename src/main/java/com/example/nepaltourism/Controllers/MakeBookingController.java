package com.example.nepaltourism.Controllers;

import com.example.nepaltourism.classes.Attraction;
import com.example.nepaltourism.classes.Booking;
import com.example.nepaltourism.classes.Festival;
import com.example.nepaltourism.classes.enums.NAVIGATIONS;
import com.example.nepaltourism.handlers.*;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class MakeBookingController implements Initializable {
    @FXML public Text attractionDescLabel;
    @FXML public Label attractionNameLabel;
    @FXML public ComboBox<Festival> festivalComboBox;
    @FXML public Label discountLabel;



    @FXML private DatePicker bookingDatePicker;
    private List<Festival> activeFestivals;

    private Attraction selectedAttraction;
    private Festival selectedFestival;
    @FXML
    VBox vbox;
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

    public void onSubmit(ActionEvent actionEvent) throws IOException {
        LocalDate bookingDate=bookingDatePicker.getValue();
        if(bookingDate==null){
            showAlert("Invalid Booking Date");
            return;
        }
        Festival festivalBoxValue=festivalComboBox.getValue();
        double festivalDiscountRate=0;
        int fid=0;
        double price;
        if(festivalBoxValue!=null){
            fid=festivalBoxValue.getId();
            festivalDiscountRate=festivalBoxValue.getDiscountRate();
        }


        Booking booking=new Booking(
                FileHandling.getNextId(FileHandling.BookingsFile),
                SessionHandler.getInstance().getUserId(),
                0,
                selectedAttraction.getId(),
                bookingDate,
                festivalDiscountRate,
                false,
                fid
        );
        FileHandling.MakeBooking(booking);
        CacheHandler.ClearCache();
        ActiveAttractionSingleton.reset();
        Navigator.Navigate(NAVIGATIONS.ATRRACTIONPAGE,(Stage) discountLabel.getScene().getWindow());
    }

    public void onCancel(ActionEvent actionEvent) throws IOException {
        Navigator.Navigate(NAVIGATIONS.ATRRACTIONPAGE,(Stage) discountLabel.getScene().getWindow());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(ActiveAttractionSingleton.getAid()==0){
            showAlert("No Attraction Selected");
            try {
                Navigator.Navigate(NAVIGATIONS.ATRRACTIONPAGE,(Stage) discountLabel.getScene().getWindow());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            selectedAttraction= ObjectFinder.getAttraction(ActiveAttractionSingleton.getAid());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if(selectedAttraction==null){
            showAlert("Selected Attraction not found");
            return;
        }

        attractionNameLabel.setText(selectedAttraction.getName());
        attractionDescLabel.setText(selectedAttraction.getDescription());
        try {
            activeFestivals= ObjectFinder.getFestivalForCurrent(LocalDate.now());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if(activeFestivals==null){
            festivalComboBox.setValue(null);
            discountLabel.setText("0%");
        }
        festivalComboBox.getItems().addAll(activeFestivals);
        discountLabel.textProperty().bind(Bindings.createStringBinding(() -> {
            Festival selected = festivalComboBox.getValue();
            return selected != null ? selected.getDiscountRate() + "%" : "0%";
        }, festivalComboBox.valueProperty()));
    }

    public void showAlert(String message){
        Alert alert=new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Wrong input");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
