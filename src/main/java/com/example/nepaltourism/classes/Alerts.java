package com.example.nepaltourism.classes;


public class Alerts {
    private int id;
    private String message;
    private int monthsActive;
    public Alerts(int id,String message,int monthsActive){
        this.id=id;
        this.message=message;
        this.monthsActive=monthsActive;
    }
    public String getDetails(){
        return this.id+","+this.message+","+this.monthsActive;
    }
    public int getId(){return this.id;}
    public String getMessage(){return this.message;}
    public int getMonthsActive(){return this.monthsActive;}
}