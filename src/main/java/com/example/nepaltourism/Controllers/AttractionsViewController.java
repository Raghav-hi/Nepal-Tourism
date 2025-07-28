package com.example.nepaltourism.Controllers;

import com.example.nepaltourism.LocaleStorageSingleton;
import com.example.nepaltourism.classes.Attraction;
import com.example.nepaltourism.classes.enums.NAVIGATIONS;
import com.example.nepaltourism.handlers.CacheHandler;
import com.example.nepaltourism.handlers.FileHandling;
import com.example.nepaltourism.handlers.Navigator;
import com.example.nepaltourism.handlers.SessionHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AttractionsViewController implements Initializable {
    public VBox vbox;

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

    @FXML
    private FlowPane attractionContainer;


    private List<Attraction> attractions;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            attractions = FileHandling.AllAttraction();
            assert attractions!=null;
            displayAttractions(attractions);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void displayAttractions(List<Attraction> list) {
        ResourceBundle bundle = ResourceBundle.getBundle("languages.language", LocaleStorageSingleton.getLocale());
        attractionContainer.setPadding(new Insets(30));
        attractionContainer.getChildren().clear();
        for (Attraction a : list) {
            VBox card = new VBox(6);
            card.setPadding(new Insets(10));
            card.setStyle("-fx-background-color: white;" +
                    "    -fx-background-radius: 10;" +
                    "    -fx-border-color: #ddd;" +
                    "    -fx-border-radius: 10;" +
                    "    -fx-padding: 12px;" +
                    "    -fx-min-width: 260px;" +
                    "    -fx-max-width: 260px;" +
                    "    -fx-spacing: 8px;" +
                    "    -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 5, 0, 0, 0);");
            card.getStyleClass().add("attraction-card");

            card.getChildren().addAll(
                    new Label(bundle.getString("nameAttractionCard") + ": " + a.getName()),
                    new Label(bundle.getString("locationAttractionCard") + ": " + a.getLocation()),
                    new Label(bundle.getString("descriptionAttractionCard")+": "+(a.getDescription()))
            );

            Button bookBtn = new Button("Book");
            bookBtn.setStyle("-fx-background-color: green;");
            bookBtn.getStyleClass().add("book-button");
            bookBtn.setOnAction(e -> {
                try {
                    handleBooking(a);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });

            card.getChildren().add(bookBtn);
            attractionContainer.getChildren().add(card);

        }
    }

    private void handleBooking(Attraction attraction) throws IOException {
        ActiveAttractionSingleton.reset();
        System.out.println("Booking attraction: " + attraction.getName());
        ActiveAttractionSingleton.setAid(attraction.getId());
        Navigator.Navigate(NAVIGATIONS.BOOKINGPAGE,(Stage) attractionContainer.getScene().getWindow());
    }
}
