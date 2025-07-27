package com.example.nepaltourism.classes;


public class Attraction {
    private int id;
    private String name;
    private String location;
    private String description;
    public Attraction(int id, String name,String location,
                      String desc){
        this.id=id;
        this.name=name;
        this.location=location;
        this.description =desc;
    }

    public String getDetails(){
        return this.id+","+this.name+","+this.location+","+this.description;

    }
    public int getId(){return this.id;}
    public String getName(){return this.name;}
    public String getLocation(){return this.location;}
    public String getDescription(){return this.description;}
}