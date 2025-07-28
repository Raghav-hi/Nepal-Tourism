package com.example.nepaltourism.adminSubControllers;

public class BookingSingleton {
    private static int id=0;
    public static void reset(){id=0;}
    public static void setId(int bid){id=bid;}
    public static int getId(){return id;}
}
