package com.example.nepaltourism.Controllers;

import com.example.nepaltourism.classes.enums.NAVIGATIONS;
import com.example.nepaltourism.handlers.AdminDashboardHandler;
import com.example.nepaltourism.handlers.Navigator;
import com.example.nepaltourism.handlers.ObjectFinder;
import com.example.nepaltourism.handlers.SessionHandler;
import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;

public class AdminController implements Initializable {
    public Button homebtn;
    public Button attractionsbtn;
    public Button logoutbtn;
    public Button userbtn;
    public Button bookingsbtn;
    public Button touristbtn;
    public Button guidesbtn;
    public Button festivalbtn;
    public Button alertbtn;


    @FXML PieChart statsPieChart;
    @FXML
    public void onClickLogout() throws IOException {
        SessionHandler.getInstance().endSession();
        Alert alert=new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Logged Out");
        alert.show();
        PauseTransition delay = new PauseTransition(Duration.seconds(2));
        delay.setOnFinished(e -> alert.close());
        delay.play();
        Navigator.Navigate(NAVIGATIONS.LOGINPAGE,(Stage)logoutbtn.getScene().getWindow());
    }

    public void onClickUserbtn(ActionEvent actionEvent) throws IOException {
        Navigator.Navigate(NAVIGATIONS.ADMINUSERPAGE,(Stage)userbtn.getScene().getWindow());

    }

    public void onClickguidesbtn(ActionEvent actionEvent) throws IOException {
        Navigator.Navigate(NAVIGATIONS.GUIDECONTROLPAGE,(Stage)guidesbtn.getScene().getWindow());
    }

    public void onClickfestivalbtn(ActionEvent actionEvent) throws IOException {
        Navigator.Navigate(NAVIGATIONS.FESTIVALCONTROLPAGE,(Stage)festivalbtn.getScene().getWindow());
    }

    public void onClickalertbtn(ActionEvent actionEvent) throws IOException {
        Navigator.Navigate(NAVIGATIONS.ALERTEDITPAGE,(Stage)alertbtn.getScene().getWindow());
    }

    public void onClickhomebtn(ActionEvent actionEvent) throws IOException {
        Navigator.Navigate(NAVIGATIONS.ADMINPAGE,(Stage)homebtn.getScene().getWindow());
    }

    public void onClickattractionsbtn(ActionEvent actionEvent) throws IOException {
        Navigator.Navigate(NAVIGATIONS.ATTRACTIONCONTROLPAGE,(Stage)attractionsbtn.getScene().getWindow());
    }

    public void onClickbookingsbtn(ActionEvent actionEvent) throws IOException {
        Navigator.Navigate(NAVIGATIONS.BOOKINGCONTROLPAGE,(Stage)bookingsbtn.getScene().getWindow());
    }

    public void onClicktouristbtn(ActionEvent actionEvent) throws IOException {
        Navigator.Navigate(NAVIGATIONS.TOURISTCONTROLPAGE,(Stage)touristbtn.getScene().getWindow());
    }


    public void updatePieChart(Map<Integer, Integer> dataMap) throws IOException {
        ObservableList<PieChart.Data> chartData = FXCollections.observableArrayList();
        String name;
        for (Map.Entry<Integer, Integer> entry : dataMap.entrySet()) {
            name= Objects.requireNonNull(ObjectFinder.getAttraction(entry.getKey())).getName();
            chartData.add(new PieChart.Data(name, entry.getValue()));
        }
        statsPieChart.setData(chartData);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            updatePieChart(AdminDashboardHandler.getAttractionBookingMap());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}



