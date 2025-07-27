package com.example.nepaltourism.handlers;

import com.example.nepaltourism.classes.Alerts;
import com.example.nepaltourism.classes.Attraction;
import com.example.nepaltourism.classes.Booking;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminDashboardHandler {

    public static Map<Integer,Integer> getAttractionBookingMap() throws IOException{
        List<Booking> allBookings=FileHandling.AllBookings();
        Map<Integer,Integer> attractionCountMap=new HashMap<>();
        Attraction attraction;
        for(Booking booking:allBookings){
            if(!booking.getIsCancelled()&&booking.getAid()!=0){
                attraction=ObjectFinder.getAttraction(booking.getAid());
                if(attraction==null) continue;
                attractionCountMap.put(attraction.getId(),attractionCountMap.getOrDefault(attraction.getId(),0)+1);
            }
        }
        return attractionCountMap;
    }

    public static List<Alerts> getAlerts()throws IOException {
        return FileHandling.AllAlerts();
    }
}