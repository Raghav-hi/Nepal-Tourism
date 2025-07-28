package com.example.nepaltourism.adminSubControllers;

import com.example.nepaltourism.classes.Booking;
import com.example.nepaltourism.classes.Guide;
import com.example.nepaltourism.classes.User;
import com.example.nepaltourism.classes.enums.LANGUAGES;
import com.example.nepaltourism.classes.enums.NAVIGATIONS;
import com.example.nepaltourism.classes.enums.USERTYPE;
import com.example.nepaltourism.handlers.FileHandling;
import com.example.nepaltourism.handlers.Navigator;
import com.example.nepaltourism.handlers.ObjectFinder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class subBookingGuideAssignController implements Initializable {


    @FXML
    private TableView<Guide> guideTable;

    @FXML private TableColumn<Guide, Integer> idColumn;
    @FXML private TableColumn<Guide, String> nameColumn;
    @FXML private TableColumn<Guide, String> emailColumn;
    @FXML private TableColumn<Guide, String> phoneNumberColumn;
    @FXML private TableColumn<Guide,Integer> yearsOfExperience;
    @FXML private TableColumn<Guide, LANGUAGES> languagePrefColumn;
    @FXML private TableColumn<Guide, Void> assignColumn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        languagePrefColumn.setCellValueFactory(new PropertyValueFactory<>("languageSpoken"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        yearsOfExperience.setCellValueFactory(new PropertyValueFactory<>("yearsOfExperience"));

        try {
            List<User> users = FileHandling.AllUsers(USERTYPE.Guide);
            ObservableList<Guide> guides = FXCollections.observableArrayList();

            assert users != null;
            for (User user : users) {
                if (user instanceof Guide && ((Guide) user).getAvailability()) {
                    guides.add((Guide) user);
                }
            }
            guideTable.setItems(guides);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        setupAssignButton();

        centerColumn(idColumn);
        centerColumn(nameColumn);
        centerColumn(languagePrefColumn);
        centerColumn(emailColumn);
        centerColumn(phoneNumberColumn);
        centerColumn(yearsOfExperience);
    }

    private void setupAssignButton() {
        assignColumn.setCellFactory(new Callback<>() {
            @Override
            public TableCell<Guide, Void> call(final TableColumn<Guide, Void> param) {
                return new TableCell<>() {
                    private final Button assignButton = new Button("Assign");

                    {
                        assignButton.setOnAction(event -> {
                            Guide guide = getTableView().getItems().get(getIndex());
                            try {
                                Booking booking= ObjectFinder.getBooking(BookingSingleton.getId());
                                assert booking != null;
                                booking.setGid(guide.getId());

                                FileHandling.editBooking(booking.getBookingId(),booking);
                                Navigator.Navigate(NAVIGATIONS.ADMINPAGE,(Stage) guideTable.getScene().getWindow());

                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }

                        });
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(assignButton);
                        }
                    }
                };
            }
        });
    }

    private <T> void centerColumn(TableColumn<Guide, T> column) {
        column.setCellFactory(col -> {
            TableCell<Guide, T> cell = new TableCell<>() {
                @Override
                protected void updateItem(T item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty || item == null ? null : item.toString());
                    setAlignment(javafx.geometry.Pos.CENTER);
                }
            };
            return cell;
        });
    }
}