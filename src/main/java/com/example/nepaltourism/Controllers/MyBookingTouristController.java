package com.example.nepaltourism.Controllers;

import com.example.nepaltourism.LocaleStorageSingleton;
import com.example.nepaltourism.classes.Booking;
import com.example.nepaltourism.classes.User;
import com.example.nepaltourism.classes.enums.NAVIGATIONS;
import com.example.nepaltourism.classes.enums.USERTYPE;
import com.example.nepaltourism.handlers.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Function;

public class MyBookingTouristController implements Initializable {
    private List<Booking> bookings;

    private Function<Integer, String> guideNameResolver;

    @FXML
    private FlowPane bookingContainer;

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
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<Booking> bookings = null;
        try {
            bookings = CacheHandler.getBookingsCache();
            this.bookings = bookings;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        guideNameResolver = guideId -> {
            if (guideId == 0) {
                return "Not Assigned";
            }
            User guide = null;
            try {
                guide = ObjectFinder.getUser(guideId, USERTYPE.Guide);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return guide != null ? guide.getName() : "Not Assigned";
        };
        initializeBookings(this.bookings);
    }

    public void setBookings(List<Booking> bookings, Function<Integer, String> guideNameResolver) {
        ResourceBundle bundle = ResourceBundle.getBundle("languages.language", LocaleStorageSingleton.getLocale());
        this.bookings = bookings;
        this.guideNameResolver = guideNameResolver;

        bookingContainer.getChildren().clear();
        bookingContainer.setPadding(new Insets(30));
        if(bookings==null){
            return;
        }
        for (Booking booking : bookings) {
            VBox card = new VBox(8);
            card.setPadding(new Insets(15));
            card.setPrefWidth(220);
            card.setStyle("""
                    -fx-background-color: white;
                    -fx-background-radius: 12;
                    -fx-border-color: #dddddd;
                    -fx-border-radius: 12;
                    -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 5, 0, 0, 4);
                    """);

            String attractionName;
            try {
                var attraction = ObjectFinder.getAttraction(booking.getAid());
                attractionName = (attraction != null) ? attraction.getName() : "Removed Attraction";
            } catch (IOException e) {
                attractionName = "Removed Attraction";
            }
            Label attraction = new Label(bundle.getString("myBookingAttractionName")+ attractionName);

            Label guide = new Label();
            guide.setUserData("guideLabel_" + booking.getBookingId());
            guide.setText(bundle.getString("myBookingGuideName") + guideNameResolver.apply(booking.getGuideId()));

            Label date = new Label(bundle.getString("myBookingDate")+ booking.getBookingDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            Label discount = new Label(bundle.getString("myBookingDiscount")+ booking.getDiscount() + "%");
            Label description = new Label(bundle.getString("myBookingDesc"));
            description.setWrapText(true);
            Button cancelBtn = new Button(bundle.getString("myBookingCancel"));
            cancelBtn.setStyle("-fx-background-color: #e63946; -fx-text-fill: white; -fx-background-radius: 20;");
            cancelBtn.setOnAction(e -> {
                try {
                    handleCancel(booking);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });

            card.getChildren().addAll(attraction, guide, date, discount, description, cancelBtn);
            bookingContainer.getChildren().add(card);
        }
    }


    private void handleCancel(Booking booking) throws IOException{
        booking.cancel();
        FileHandling.editBooking(booking.getBookingId(),booking);
        CacheHandler.ClearCache();
        Navigator.Navigate(NAVIGATIONS.ATRRACTIONPAGE,(Stage) bookingContainer.getScene().getWindow());
    }

    public void initializeBookings(List<Booking> bookings) {
        setBookings(bookings, guideNameResolver);
    }



}
