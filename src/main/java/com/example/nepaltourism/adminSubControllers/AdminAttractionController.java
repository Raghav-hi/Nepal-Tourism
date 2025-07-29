package com.example.nepaltourism.adminSubControllers;

import com.example.nepaltourism.classes.Attraction;
import com.example.nepaltourism.classes.enums.NAVIGATIONS;
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

public class AdminAttractionController {

    @FXML private VBox vbox;
    @FXML private TextField nameField;
    @FXML private TextArea descriptionArea;
    @FXML private TableView<Attraction> attractionsTable;
    @FXML private TableColumn<Attraction, Integer> idColumn;
    @FXML private TableColumn<Attraction, String> nameColumn;
    @FXML private TableColumn<Attraction, String> descriptionColumn;
    @FXML private TableColumn<Attraction, Void> actionsColumn;

    private final ObservableList<Attraction> attractionList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        setupTableColumns();
        loadAttractions();
        addActionButtonsToTable();
    }

    private void setupTableColumns() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        idColumn.setPrefWidth(50);

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameColumn.setPrefWidth(200);

        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        descriptionColumn.setPrefWidth(400);

        attractionsTable.setItems(attractionList);
    }

    private void loadAttractions() {
        try {
            attractionList.clear();
            List<Attraction> attractionsFromFile = FileHandling.AllAttraction();
            attractionList.addAll(attractionsFromFile);
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Unable to load attractions data.", ButtonType.OK);
            alert.show();
        }
    }

    private void addActionButtonsToTable() {
        actionsColumn.setCellFactory(col -> new TableCell<>() {
            private final Button removeBtn = new Button("Remove");
            {
                removeBtn.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-font-weight: bold;");
                removeBtn.setOnAction(event -> {
                    Attraction attraction = getTableView().getItems().get(getIndex());
                    System.out.println("Remove button clicked for ID: " + attraction.getId()); // DEBUG
                    try {
                        FileHandling.removeAttraction(attraction.getId());
                        Navigator.Navigate(NAVIGATIONS.ATTRACTIONCONTROLPAGE,(Stage) vbox.getScene().getWindow());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || getTableView().getItems().isEmpty()) {
                    setGraphic(null);
                } else {
                    setGraphic(removeBtn);
                }
            }
        });
    }

    @FXML
    public void onAddAttraction(ActionEvent event) throws IOException {
        String name = nameField.getText().trim();
        String description = descriptionArea.getText().trim();

        if (name.isEmpty() || description.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Please fill all fields.", ButtonType.OK);
            alert.show();
            return;
        }

        Attraction newAttraction = new Attraction(
                FileHandling.getNextId(FileHandling.AttractionsFile),
                name,
                name, // Assuming second name is placeholder, e.g. image name
                description
        );

        FileHandling.AddAttraction(newAttraction);
        attractionList.add(newAttraction);

        // Clear form after adding
        nameField.clear();
        descriptionArea.clear();
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
