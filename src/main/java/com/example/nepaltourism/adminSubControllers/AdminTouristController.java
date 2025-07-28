package com.example.nepaltourism.adminSubControllers;

import com.example.nepaltourism.classes.Tourist;
import com.example.nepaltourism.classes.User;
import com.example.nepaltourism.classes.enums.LANGUAGES;
import com.example.nepaltourism.classes.enums.NAVIGATIONS;
import com.example.nepaltourism.classes.enums.USERTYPE;
import com.example.nepaltourism.handlers.FileHandling;
import com.example.nepaltourism.handlers.Navigator;
import com.example.nepaltourism.handlers.SessionHandler;
import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.List;

public class AdminTouristController {

    @FXML private VBox vbox;

    @FXML private TableView<Tourist> touristsTable;
    @FXML private TableColumn<Tourist, Integer> idColumn;
    @FXML private TableColumn<Tourist, String> nameColumn;
    @FXML private TableColumn<Tourist, String> emailColumn;
    @FXML private TableColumn<Tourist, String> phoneColumn;
    @FXML private TableColumn<Tourist, String> languageColumn;
    @FXML private TableColumn<Tourist, Void> actionsColumn;

    @FXML private TextField nameField;
    @FXML private TextField emailField;
    @FXML private TextField phoneField;
    @FXML private ComboBox<LANGUAGES> languageComboBox;
    @FXML private TextField passwordField;

    private final ObservableList<Tourist> touristList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Bind columns to Tourist properties
        touristsTable.setPlaceholder(new Label("No Tourist available"));
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        languageColumn.setCellValueFactory(new PropertyValueFactory<>("languagePref"));

        setupActionsColumn();

        touristsTable.setItems(touristList);

        // Populate languageComboBox with enum values dynamically
        languageComboBox.setItems(FXCollections.observableArrayList(LANGUAGES.values()));

        loadTourists();
    }

    private void setupActionsColumn() {
        actionsColumn.setCellFactory(col -> new TableCell<>() {
            private final Button deleteBtn = new Button("Delete");

            {
                deleteBtn.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-font-weight: bold;");
                deleteBtn.setOnAction(event -> {
                    Tourist tourist = getTableView().getItems().get(getIndex());
                    removeTourist(tourist);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || getIndex() >= touristList.size()) {
                    setGraphic(null);
                } else {
                    setGraphic(deleteBtn);
                }
            }
        });
    }

    private void loadTourists() {
        try {
            touristList.clear();
            List<User> data = FileHandling.AllUsers(USERTYPE.Tourist);
            for (User user : data) {
                if (user instanceof Tourist) {
                    touristList.add((Tourist) user);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Unable to load tourists data.", ButtonType.OK);
            alert.show();
        }
    }

    private void removeTourist(Tourist tourist) {
        try {
            FileHandling.removeUser(USERTYPE.Tourist,tourist.getId());
            touristList.remove(tourist);
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Failed to delete tourist.", ButtonType.OK);
            alert.show();
        }
    }

    // Called when Add Tourist button is clicked
    @FXML
    private void onAddTourist(ActionEvent event) {
        String name = nameField.getText();
        String email = emailField.getText();
        String phone = phoneField.getText();
        LANGUAGES language = languageComboBox.getValue();
        String password = passwordField.getText();

        if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || language == null || password.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Please fill in all fields.", ButtonType.OK);
            alert.show();
            return;
        }

        Tourist newTourist = new Tourist(FileHandling.getNextId(FileHandling.TouristFile),name, email, phone,  password,language);
        FileHandling.WriteUser(USERTYPE.Tourist, newTourist);  // You implement this method to save user
        touristList.add(newTourist);
        clearForm();
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Tourist added successfully.", ButtonType.OK);
        alert.show();
    }

    private void clearForm() {
        nameField.clear();
        emailField.clear();
        phoneField.clear();
        languageComboBox.getSelectionModel().clearSelection();
        passwordField.clear();
    }

    @FXML
    private void onCancel(ActionEvent event) {
        clearForm();
    }

    // Navigation and session management methods

    @FXML
    public void onClickLogout() throws IOException {
        SessionHandler.getInstance().endSession();
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Logged Out", ButtonType.OK);
        alert.show();
        PauseTransition delay = new PauseTransition(Duration.seconds(5));
        delay.setOnFinished(e -> alert.close());
        delay.play();
        Navigator.Navigate(NAVIGATIONS.LOGINPAGE, (Stage) vbox.getScene().getWindow());
    }

    @FXML
    public void onHome(ActionEvent event) throws IOException {
        Navigator.Navigate(NAVIGATIONS.ADMINPAGE, (Stage) vbox.getScene().getWindow());
    }

    @FXML
    public void onAttractions(ActionEvent event) throws IOException {
        Navigator.Navigate(NAVIGATIONS.ATTRACTIONCONTROLPAGE, (Stage) vbox.getScene().getWindow());
    }

    @FXML
    public void onBookings(ActionEvent event) throws IOException {
        Navigator.Navigate(NAVIGATIONS.BOOKINGCONTROLPAGE, (Stage) vbox.getScene().getWindow());
    }

    @FXML
    public void onTourists(ActionEvent event) throws IOException {
        Navigator.Navigate(NAVIGATIONS.TOURISTCONTROLPAGE, (Stage) vbox.getScene().getWindow());
    }

    @FXML
    public void onGuides(ActionEvent event) throws IOException {
        Navigator.Navigate(NAVIGATIONS.GUIDECONTROLPAGE, (Stage) vbox.getScene().getWindow());
    }

    @FXML
    public void profileEdit(ActionEvent event) throws IOException {
        Navigator.Navigate(NAVIGATIONS.ADMINUSERPAGE, (Stage) vbox.getScene().getWindow());
    }
}