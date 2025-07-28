package com.example.nepaltourism.Controllers;

import com.example.nepaltourism.classes.Festival;
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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class FestivalDiscountViewController implements Initializable {
    @FXML private TableView<Festival> festivalTable;
    @FXML private TableColumn<Festival, Integer> colId;
    @FXML private TableColumn<Festival, String> colName;
    @FXML private TableColumn<Festival, String> colStartDate;
    @FXML private TableColumn<Festival, String> colEndDate;
    @FXML private TableColumn<Festival, Double> colDiscount;

    private final ObservableList<Festival> festivalList = FXCollections.observableArrayList();

    public VBox vbox;

    public void goAlerts(ActionEvent actionEvent) throws IOException{
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
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colStartDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        colEndDate.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        colDiscount.setCellValueFactory(new PropertyValueFactory<>("discountRate"));


        festivalTable.setItems(festivalList);
        try {
            loadFestivals(); // You will replace this with actual backend fetch
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadFestivals() throws IOException {
        List<Festival> festivals= FileHandling.AllFestival();

        festivalList.setAll(festivals);
    }
}
