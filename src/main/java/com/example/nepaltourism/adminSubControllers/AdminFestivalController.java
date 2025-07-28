package com.example.nepaltourism.adminSubControllers;

import com.example.nepaltourism.classes.Festival;
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

public class AdminFestivalController implements Initializable {
    @FXML private TableView<Festival> festivalTable;
    @FXML private TableColumn<Festival, Integer> colId;
    @FXML private TableColumn<Festival, String> colName;
    @FXML private TableColumn<Festival, String> colStartDate;
    @FXML private TableColumn<Festival, String> colEndDate;
    @FXML private TableColumn<Festival, Double> colDiscount;
    @FXML private TableColumn<Festival, Void> colActions;

    private final ObservableList<Festival> festivalList = FXCollections.observableArrayList();
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

    public void addFestival(ActionEvent actionEvent) throws IOException{
        Navigator.Navigate(NAVIGATIONS.FESTIVALEDIT,(Stage) vbox.getScene().getWindow());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colStartDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        colEndDate.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        colDiscount.setCellValueFactory(new PropertyValueFactory<>("discountRate"));

        setupActionsColumn();

        festivalTable.setItems(festivalList);
        try {
            loadFestivals(); // You will replace this with actual backend fetch
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void setupActionsColumn() {
        colActions.setCellFactory(param -> new TableCell<>() {
            private final Button deleteButton = new Button("Delete");

            {
                deleteButton.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-font-weight: bold;");
                deleteButton.setOnAction(event -> {
                    Festival festival = getTableView().getItems().get(getIndex());
                    festivalList.remove(festival);
                    try {
                        FileHandling.removeFestival(festival.getId());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || getIndex() >= festivalList.size()) {
                    setGraphic(null);
                } else {
                    setGraphic(deleteButton);
                }
            }
        });
    }
    private void loadFestivals() throws IOException {
        List<Festival>festivals= FileHandling.AllFestival();

        festivalList.setAll(festivals);
    }
}
