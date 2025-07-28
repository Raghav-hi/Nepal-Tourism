package com.example.nepaltourism.adminSubControllers;

import com.example.nepaltourism.classes.Alerts;
import com.example.nepaltourism.classes.enums.NAVIGATIONS;
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

public class AdminAlertController implements Initializable {
    @FXML private TableView<Alerts> alertTable;
    @FXML private TableColumn<Alerts, Integer> colId;
    @FXML private TableColumn<Alerts, String> colMessage;
    @FXML private TableColumn<Alerts, Integer> colMonthsActive;
    @FXML private TableColumn<Alerts, Void> colActions;

    private final ObservableList<Alerts> alertList = FXCollections.observableArrayList();
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

    public void addAlert(ActionEvent actionEvent) throws IOException{
        Navigator.Navigate(NAVIGATIONS.ALERTEDIT,(Stage) vbox.getScene().getWindow());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colMessage.setCellValueFactory(new PropertyValueFactory<>("message"));
        colMonthsActive.setCellValueFactory(new PropertyValueFactory<>("monthsActive"));

        setupActionsColumn();

        alertTable.setItems(alertList);

        // Load alerts (simulate or fetch from backend)
        try {
            loadAlerts();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadAlerts()throws IOException {
        List<Alerts> alerts= FileHandling.AllAlerts();

        alertList.setAll(alerts);
    }

    private void setupActionsColumn() {
        colActions.setCellFactory(param -> new TableCell<>() {
            private final Button deleteButton = new Button("Delete");

            {
                deleteButton.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-font-weight: bold;");
                deleteButton.setOnAction(event -> {
                    Alerts alert = getTableView().getItems().get(getIndex());
                    alertList.remove(alert);
                    try {
                        FileHandling.removeAlerts(alert.getId());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || getIndex() >= alertList.size()) {
                    setGraphic(null);
                } else {
                    setGraphic(deleteButton);
                }
            }
        });
    }
}
