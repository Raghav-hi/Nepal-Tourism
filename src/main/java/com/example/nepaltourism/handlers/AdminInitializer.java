package com.example.nepaltourism.handlers;

import com.example.nepaltourism.classes.Admin;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class AdminInitializer {
    public static void init() throws IOException {
        File adminFile=new File(FileHandling.AdminFile);
        if(adminFile.exists()){
            adminFile.delete();
        }
        else {
            adminFile.createNewFile();
        }
        Admin roshan=new Admin(1,"Raghav Shrestha","raghav@gmail.com",
                "9813603771","raghav");
        Admin safal=new Admin(2,"Roshan Chau","chgroshan6@gmail.com",
                "9823168078","roshan");

        try(BufferedWriter bw=new BufferedWriter(new FileWriter(FileHandling.AdminFile,true))){
            bw.write(roshan.getDetails());
            bw.newLine();
            bw.write(safal.getDetails());
        }

    }
}
