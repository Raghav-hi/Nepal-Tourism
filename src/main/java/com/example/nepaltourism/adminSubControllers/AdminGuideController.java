package com.example.nepaltourism.adminSubControllers;

import com.example.nepaltourism.classes.Guide;
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

public class AdminGuideController {

    @FXML private VBox vbox;

    @FXML private TableView<Guide> guidesTable;
    @FXML private TableColumn<Guide, Integer> idColumn;
    @FXML private TableColumn<Guide, String> nameColumn;
    @FXML private TableColumn<Guide, String> emailColumn;
    @FXML private TableColumn<Guide, String> phoneColumn;
    @FXML private TableColumn<Guide, LANGUAGES> languageColumn;
    @FXML private TableColumn<Guide, Integer> experienceColumn;
    @FXML private TableColumn<Guide, Boolean> availabilityColumn;
    @FXML private TableColumn<Guide, Void> actionsColumn;

    @FXML private TextField nameField;
    @FXML private TextField emailField;
    @FXML private TextField phoneField;
    @FXML private ComboBox<LANGUAGES> languageComboBox;
    @FXML private TextField experienceField;
    @FXML private CheckBox availabilityCheckBox;
    @FXML private TextField passwordField;

    private final ObservableList<Guide> guideList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        guidesTable.setPlaceholder(new Label("No guides available"));
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        languageColumn.setCellValueFactory(new PropertyValueFactory<>("languageSpoken"));
        experienceColumn.setCellValueFactory(new PropertyValueFactory<>("yearsOfExperience"));
        availabilityColumn.setCellValueFactory(new PropertyValueFactory<>("availability"));

        setupActionsColumn();

        guidesTable.setItems(guideList);

        // Populate languageComboBox with enum values dynamically
        languageComboBox.setItems(FXCollections.observableArrayList(LANGUAGES.values()));

        loadGuides();
    }

    private void setupActionsColumn() {
        actionsColumn.setCellFactory(col -> new TableCell<>() {
            private final Button deleteBtn = new Button("Delete");

            {
                deleteBtn.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-font-weight: bold;");
                deleteBtn.setOnAction(event -> {
                    Guide guide = getTableView().getItems().get(getIndex());
                    removeGuide(guide);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || getIndex() >= guideList.size()) {
                    setGraphic(null);
                } else {
                    setGraphic(deleteBtn);
                }
            }
        });
    }

    private void loadGuides() {
        try {
            guideList.clear();
            List<User> data = FileHandling.AllUsers(USERTYPE.Guide);
            for (User user : data) {
                if (user instanceof Guide) {
                    guideList.add((Guide) user);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Unable to load guides data.", ButtonType.OK);
            alert.show();
        }
    }

    private void removeGuide(Guide guide) {
        try {
            FileHandling.removeUser(USERTYPE.Guide, guide.getId());
            guideList.remove(guide);
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Failed to delete guide.", ButtonType.OK);
            alert.show();
        }
    }

    @FXML
    private void onAddGuide(ActionEvent event) {
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String phone = phoneField.getText().trim();
        LANGUAGES language = languageComboBox.getValue();
        String experienceText = experienceField.getText().trim();
        boolean availability = availabilityCheckBox.isSelected();
        String password=passwordField.getText();

        if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || language == null || experienceText.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Please fill in all fields.", ButtonType.OK);
            alert.show();
            return;
        }

        int yearsOfExperience;
        try {
            yearsOfExperience = Integer.parseInt(experienceText);
            if (yearsOfExperience < 0) {
                throw new NumberFormatException("Negative value");
            }
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Please enter a valid non-negative number for years of experience.", ButtonType.OK);
            alert.show();
            return;
        }

        int newId = FileHandling.getNextId(FileHandling.GuideFile);
        Guide newGuide = new Guide(newId, name, email, phone, password,language, yearsOfExperience);
        newGuide.updateAvailability(availability);
        FileHandling.WriteUser(USERTYPE.Guide, newGuide);
        guideList.add(newGuide);
        clearForm();
    }

    private void clearForm() {
        nameField.clear();
        emailField.clear();
        phoneField.clear();
        languageComboBox.getSelectionModel().clearSelection();
        experienceField.clear();
        availabilityCheckBox.setSelected(false);
    }

    @FXML
    private void onCancel(ActionEvent event) {
        clearForm();
    }


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
