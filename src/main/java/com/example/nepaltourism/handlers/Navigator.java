package com.example.nepaltourism.handlers;

import com.example.nepaltourism.classes.enums.NAVIGATIONS;
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
        }
            Parent root=activeLoader.load();
            Scene scene=new Scene(root,1200,740);
            stage.setTitle(activeTitle);

            stage.setResizable(resizable);
            stage.setScene(scene);
            stage.show();
        }
    }