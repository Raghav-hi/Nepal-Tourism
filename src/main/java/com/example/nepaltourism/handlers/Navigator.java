package com.example.nepaltourism.handlers;

import com.example.nepaltourism.LocaleStorageSingleton;
import com.example.nepaltourism.classes.Guide;
import com.example.nepaltourism.classes.Tourist;
import com.example.nepaltourism.classes.enums.LANGUAGES;
import com.example.nepaltourism.classes.enums.NAVIGATIONS;
import com.example.nepaltourism.classes.enums.USERTYPE;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ResourceBundle;

public class Navigator {
    public static FXMLLoader activeLoader;
    public static String activeTitle;
    public static boolean resizable;
    @FXML
    public static void Navigate(NAVIGATIONS nav, Stage stage) throws IOException {
        ResourceBundle bundle = ResourceBundle.getBundle("languages.language", LocaleStorageSingleton.getLocale());
        switch(nav){
            case LOGINPAGE -> {
                activeLoader=new FXMLLoader(Navigator.class.getResource("/com/example/nepaltourism/loginPage.fxml"));
                activeTitle="Login Page";
                resizable=false;
            }
            case REGISTERPAGE -> {
                activeLoader=new FXMLLoader(Navigator.class.getResource("/com/example/nepaltourism/registerPage.fxml"));
                activeTitle="Register Page";
                resizable=false;
            }
            case TOURISTPAGE -> {
                Tourist tourist=(Tourist) ObjectFinder.getUser(SessionHandler.getInstance().getUserId(), USERTYPE.Tourist);
                assert tourist != null;
                if(tourist.getLanguagePref()== LANGUAGES.Nepali){
                    LocaleStorageSingleton.setLocaleNp();
                }
                else{
                    LocaleStorageSingleton.setLocaleEn();
                }
                bundle = ResourceBundle.getBundle("languages.language", LocaleStorageSingleton.getLocale());

                activeLoader=new FXMLLoader(Navigator.class.getResource("/com/example/nepaltourism/touristPage.fxml"),bundle);
                activeTitle="Tourist Page";
                resizable=false;
            }
            case  GUIDEPAGE-> {
                Guide guide = (Guide) ObjectFinder.getUser(SessionHandler.getInstance().getUserId(), USERTYPE.Guide);
                assert guide != null;
                if (guide.getLanguageSpoken() == LANGUAGES.Nepali) {
                    LocaleStorageSingleton.setLocaleNp();
                } else {
                    LocaleStorageSingleton.setLocaleEn();
                }
                bundle = ResourceBundle.getBundle("languages.language", LocaleStorageSingleton.getLocale());
                activeLoader=new FXMLLoader(Navigator.class.getResource("/com/example/nepaltourism/guidePage.fxml"),bundle);
                activeTitle="Guide Page";
                resizable=false;
            }
            case  ADMINPAGE-> {
                activeLoader=new FXMLLoader(Navigator.class.getResource("/com/example/nepaltourism/adminPage.fxml"));
                activeTitle="Admin Page";
                resizable=false;
            }
            case  MYBOOKINGPAGE-> {
                Tourist tourist=(Tourist) ObjectFinder.getUser(SessionHandler.getInstance().getUserId(), USERTYPE.Tourist);
                assert tourist != null;
                if(tourist.getLanguagePref()== LANGUAGES.Nepali){
                    LocaleStorageSingleton.setLocaleNp();
                }
                else{
                    LocaleStorageSingleton.setLocaleEn();
                }
                bundle = ResourceBundle.getBundle("languages.language", LocaleStorageSingleton.getLocale());
                activeLoader=new FXMLLoader(Navigator.class.getResource("/com/example/nepaltourism/mybookingPage.fxml"),bundle );
                activeTitle="Tourist Mybooking Page";
                resizable=false;
            }
            case  ATRRACTIONPAGE-> {
                Tourist tourist=(Tourist) ObjectFinder.getUser(SessionHandler.getInstance().getUserId(), USERTYPE.Tourist);
                assert tourist != null;
                if(tourist.getLanguagePref()== LANGUAGES.Nepali){
                    LocaleStorageSingleton.setLocaleNp();
                }
                else{
                    LocaleStorageSingleton.setLocaleEn();
                }
                bundle = ResourceBundle.getBundle("languages.language", LocaleStorageSingleton.getLocale());
                activeLoader=new FXMLLoader(Navigator.class.getResource("/com/example/nepaltourism/attractionPage.fxml"),bundle);
                activeTitle="Tourist Attraction Page";
                resizable=false;
            }
            case  GUIDEMYBOOKINGPAGE-> {
                Guide guide = (Guide) ObjectFinder.getUser(SessionHandler.getInstance().getUserId(), USERTYPE.Guide);
                assert guide != null;
                if (guide.getLanguageSpoken() == LANGUAGES.Nepali) {
                    LocaleStorageSingleton.setLocaleNp();
                } else {
                    LocaleStorageSingleton.setLocaleEn();
                }
                bundle = ResourceBundle.getBundle("languages.language", LocaleStorageSingleton.getLocale());
                activeLoader=new FXMLLoader(Navigator.class.getResource("/com/example/nepaltourism/mybookingguidePage.fxml"),bundle);
                activeTitle="Guide MyBooking Page";
                resizable=false;
            }
            case FESTIVALPAGE -> {
                Tourist tourist=(Tourist) ObjectFinder.getUser(SessionHandler.getInstance().getUserId(), USERTYPE.Tourist);
                assert tourist != null;
                if(tourist.getLanguagePref()== LANGUAGES.Nepali){
                    LocaleStorageSingleton.setLocaleNp();
                }
                else{
                    LocaleStorageSingleton.setLocaleEn();
                }
                bundle = ResourceBundle.getBundle("languages.language", LocaleStorageSingleton.getLocale());
                activeLoader=new FXMLLoader(Navigator.class.getResource("/com/example/nepaltourism/festivaldiscoutviewPage.fxml"),bundle);
                activeTitle="Tourist Festival Discount Page";
                resizable=false;
            }
            case TOURISTUSERPAGE -> {
                Tourist tourist=(Tourist) ObjectFinder.getUser(SessionHandler.getInstance().getUserId(), USERTYPE.Tourist);
                assert tourist != null;
                if(tourist.getLanguagePref()== LANGUAGES.Nepali){
                    LocaleStorageSingleton.setLocaleNp();
                }
                else{
                    LocaleStorageSingleton.setLocaleEn();
                }
                bundle = ResourceBundle.getBundle("languages.language", LocaleStorageSingleton.getLocale());
                activeLoader=new FXMLLoader(Navigator.class.getResource("/com/example/nepaltourism/profileEditTourist.fxml"),bundle);
                activeTitle="Tourist Edit Page";
                resizable=false;
            }
            case  GUIDEUSERPAGE-> {
                Guide guide=(Guide) ObjectFinder.getUser(SessionHandler.getInstance().getUserId(), USERTYPE.Guide);
                assert guide != null;
                if(guide.getLanguageSpoken()== LANGUAGES.Nepali){
                    LocaleStorageSingleton.setLocaleNp();
                }
                else{
                    LocaleStorageSingleton.setLocaleEn();
                }
                bundle = ResourceBundle.getBundle("languages.language", LocaleStorageSingleton.getLocale());
                activeLoader=new FXMLLoader(Navigator.class.getResource("/com/example/nepaltourism/profileEditGuide.fxml"),bundle);
                activeTitle="Guide Edit Page";
                resizable=false;
            }
            case  BOOKINGPAGE-> {
                Tourist tourist=(Tourist) ObjectFinder.getUser(SessionHandler.getInstance().getUserId(), USERTYPE.Tourist);
                assert tourist != null;
                if(tourist.getLanguagePref()== LANGUAGES.Nepali){
                    LocaleStorageSingleton.setLocaleNp();
                }
                else{
                    LocaleStorageSingleton.setLocaleEn();
                }
                bundle = ResourceBundle.getBundle("languages.language", LocaleStorageSingleton.getLocale());

                activeLoader=new FXMLLoader(Navigator.class.getResource("/com/example/nepaltourism/makebookingPage.fxml"),bundle);
                activeTitle="Make Booking Page";
                resizable=false;
            }
            case  ALERTPAGE-> {
                Tourist tourist=(Tourist) ObjectFinder.getUser(SessionHandler.getInstance().getUserId(), USERTYPE.Tourist);
                assert tourist != null;
                if(tourist.getLanguagePref()== LANGUAGES.Nepali){
                    LocaleStorageSingleton.setLocaleNp();
                }
                else{
                    LocaleStorageSingleton.setLocaleEn();
                }
                bundle = ResourceBundle.getBundle("languages.language", LocaleStorageSingleton.getLocale());

                activeLoader=new FXMLLoader(Navigator.class.getResource("/com/example/nepaltourism/alertviewPage.fxml"),bundle);
                activeTitle="Tourist Alert View Page";
                resizable=false;
            }
            case  GUIDEALERTPAGE-> {
                Guide guide = (Guide) ObjectFinder.getUser(SessionHandler.getInstance().getUserId(), USERTYPE.Guide);
                assert guide != null;
                if (guide.getLanguageSpoken() == LANGUAGES.Nepali) {
                    LocaleStorageSingleton.setLocaleNp();
                } else {
                    LocaleStorageSingleton.setLocaleEn();
                }
                bundle = ResourceBundle.getBundle("languages.language", LocaleStorageSingleton.getLocale());
                activeLoader=new FXMLLoader(Navigator.class.getResource("/com/example/nepaltourism/alertviewguidePage.fxml"),bundle);
                activeTitle="Guide Alert View Page";
                resizable=false;
            }
            case  ALERTEDITPAGE-> {
                activeLoader=new FXMLLoader(Navigator.class.getResource("/com/example/nepaltourism/adminalertPage.fxml"));
                activeTitle="Admin Alert Control Page";
                resizable=false;
            }
            case  BOOKINGCONTROLPAGE-> {
                activeLoader=new FXMLLoader(Navigator.class.getResource("/com/example/nepaltourism/adminbookingPage.fxml"));
                activeTitle="Admin Booking Control Page";
                resizable=false;
            }
            case  ATTRACTIONCONTROLPAGE-> {
                activeLoader=new FXMLLoader(Navigator.class.getResource("/com/example/nepaltourism/adminattractionPage.fxml"));
                activeTitle="Admin Attraction Control Page";
                resizable=false;
            }
            case  FESTIVALCONTROLPAGE-> {
                activeLoader=new FXMLLoader(Navigator.class.getResource("/com/example/nepaltourism/adminfestivalPage.fxml"));
                activeTitle="Admin Festival Control Page";
                resizable=false;
            }
            case  FESTIVALEDIT-> {
                activeLoader=new FXMLLoader(Navigator.class.getResource("/com/example/nepaltourism/festivalEdit.fxml"));
                activeTitle="Admin Festival Control Page";
                resizable=false;
            }
            case  ALERTEDIT-> {
                activeLoader=new FXMLLoader(Navigator.class.getResource("/com/example/nepaltourism/alertControl.fxml"));
                activeTitle="Admin Alert Control Page";
                resizable=false;
            }
            case    GUIDECONTROLPAGE-> {
                activeLoader=new FXMLLoader(Navigator.class.getResource("/com/example/nepaltourism/adminguidePage.fxml"));
                activeTitle="Admin Guide Control Page";
                resizable=false;
            }
            case    TOURISTCONTROLPAGE-> {
                activeLoader=new FXMLLoader(Navigator.class.getResource("/com/example/nepaltourism/admintouristPage.fxml"));
                activeTitle="Admin Tourist Control Page";
                resizable=false;
            }
        }
            Parent root=activeLoader.load();
            Scene scene=new Scene(root,1200,740);
            stage.setTitle(activeTitle);

            stage.setResizable(resizable);
            stage.setScene(scene);
            stage.show();
        }
}
