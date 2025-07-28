package com.example.nepaltourism.Controllers;

import com.example.nepaltourism.classes.Alerts;
import com.example.nepaltourism.classes.enums.NAVIGATIONS;
import com.example.nepaltourism.handlers.CacheHandler;
import com.example.nepaltourism.handlers.FileHandling;
import com.example.nepaltourism.handlers.Navigator;
import com.example.nepaltourism.handlers.SessionHandler;
import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AlertViewController implements Initializable {
    @FXML public TableColumn<Alerts,Integer> colId;
    @FXML public TableColumn<Alerts,String> colMessage;
    @FXML public TableColumn<Alerts,Integer> colMonthsActive;
    @FXML public TableView<Alerts> alertTable;
    private final ObservableList<Alerts> alertList = FXCollections.observableArrayList();

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
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colMessage.setCellValueFactory(new PropertyValueFactory<>("message"));
        colMonthsActive.setCellValueFactory(new PropertyValueFactory<>("monthsActive"));

        alertTable.setItems(alertList);

        // Load alerts (simulate or fetch from backend)
        try {
            loadAlerts();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadAlerts() throws IOException {
        List<Alerts> alerts= FileHandling.AllAlerts();

        alertList.setAll(alerts);
    }

}
