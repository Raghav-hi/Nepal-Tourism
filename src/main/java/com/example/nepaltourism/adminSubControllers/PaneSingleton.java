package com.example.nepaltourism.adminSubControllers;

import javafx.scene.layout.BorderPane;

public class PaneSingleton {
    private static BorderPane pane=null;
    public static void reset(){pane=null;}
    public static BorderPane getPane(){return pane;}
    public static void setPane(BorderPane p){
        pane=p;
    }

}
