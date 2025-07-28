package com.example.nepaltourism.Controllers;

public class ActiveAttractionSingleton {
    private static int aid =0;
    public static void reset(){
        aid =0;
    }
    public static int getAid(){
        return aid;
    }
    public static void setAid(int id){
        aid =id;
    }
}
