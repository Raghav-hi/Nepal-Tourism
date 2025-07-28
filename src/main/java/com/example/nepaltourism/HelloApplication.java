package com.example.nepaltourism;

import com.example.nepaltourism.Controllers.AdminController;
import com.example.nepaltourism.handlers.CacheHandler;
import com.example.nepaltourism.handlers.FileHandling;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("loginPage.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 740);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) throws IOException {
        FileHandling.init();
        CacheHandler.initCache();

        launch();
    }
}