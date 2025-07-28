package com.example.nepaltourism.adminSubControllers;

import com.example.nepaltourism.classes.Booking;
import com.example.nepaltourism.classes.enums.NAVIGATIONS;
import com.example.nepaltourism.handlers.FileHandling;
import com.example.nepaltourism.handlers.Navigator;
import com.example.nepaltourism.handlers.SessionHandler;
import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class AdminBookingController implements Initializable {
    @FXML BorderPane mainpane;
    @FXML private VBox vbox;
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

    @FXML
    private TableView<Booking> bookingsTable;

    @FXML private TableColumn<Booking, Integer> idColumn;
    @FXML private TableColumn<Booking, Integer> uidColumn;
    @FXML private TableColumn<Booking, Integer> gidColumn;
    @FXML private TableColumn<Booking, Integer> aidColumn;
    @FXML private TableColumn<Booking,Integer> fidColumn;
    @FXML private TableColumn<Booking, Date> dateColumn;
    @FXML private TableColumn<Booking, Boolean> cancelledColumn;
    @FXML private TableColumn<Booking, Double> discountColumn;
    @FXML private TableColumn<Booking, Void> actionsColumn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("bookingId"));
        fidColumn.setCellValueFactory(new PropertyValueFactory<>("fid"));
        gidColumn.setCellValueFactory(new PropertyValueFactory<>("guideId"));
        uidColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));
        aidColumn.setCellValueFactory(new PropertyValueFactory<>("aid"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("bookingDate"));
        cancelledColumn.setCellValueFactory(new PropertyValueFactory<>("isCancelled"));
        discountColumn.setCellValueFactory(new PropertyValueFactory<>("discount"));

        try {
            List<Booking> bookings = FileHandling.AllBookings();
            ObservableList<Booking> bookingList = FXCollections.observableArrayList();

            for (Booking booking : bookings) {
                if (booking!=null) {
                    bookingList.add((Booking) booking);
                }
            }
            bookingsTable.setItems(bookingList);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        setupActionButtons();

        centerColumn(idColumn);
        centerColumn(uidColumn);
        centerColumn(gidColumn);
        centerColumn(aidColumn);
        centerColumn(fidColumn);
        centerColumn(dateColumn);
        centerColumn(discountColumn);
        centerColumn(cancelledColumn);
    }

    private void setupActionButtons() {
        actionsColumn.setCellFactory(new Callback<>() {
            @Override
            public TableCell<Booking, Void> call(final TableColumn<Booking, Void> param) {
                return new TableCell<>() {
                    private final Button guideAssign = new Button("Assign Guide");
                    private final Button deleteButton = new Button("Delete");
                    private final HBox pane = new HBox(10, guideAssign, deleteButton);

                    {
                        guideAssign.setOnAction(event -> {
                            Booking booking = getTableView().getItems().get(getIndex());
                            BookingSingleton.setId(booking.getBookingId());
                            PaneSingleton.setPane(mainpane);
                            BorderPane borderPane = PaneSingleton.getPane();
                            Node allBookingsNode = null;
                            try {
                                allBookingsNode = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/nepaltourism/guideAssign.fxml")));
                                borderPane.setCenter(allBookingsNode);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        });

                        deleteButton.setOnAction(event -> {
                            Booking booking = getTableView().getItems().get(getIndex());
                            getTableView().getItems().remove(booking);
                            try {
                                FileHandling.removeBooking(booking.getBookingId());
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
                            setGraphic(pane);
                        }
                    }
                };
            }
        });
    }

    private <T> void centerColumn(TableColumn<Booking, T> column) {
        column.setCellFactory(col -> {
            TableCell<Booking, T> cell = new TableCell<>() {
                @Override
                protected void updateItem(T item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty || item == null ? null : item.toString());
                    setAlignment(Pos.CENTER);
                }
            };
            return cell;
        });
    }

}
