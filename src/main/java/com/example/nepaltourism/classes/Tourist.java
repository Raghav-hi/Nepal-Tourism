package com.example.nepaltourism.classes;

import com.example.nepaltourism.classes.enums.LANGUAGES;

public class Tourist extends User {
    private LANGUAGES languagePref;


    public Tourist(int id, String name, String email, String phone,String password,LANGUAGES languagePref) {
        super(id, name, email, phone,password);

        this.languagePref=languagePref ;

    }


    public LANGUAGES getLanguagePref(){return this.languagePref;}


    @Override
    public String getDetails(){
        String tourist=this.getId()+","+this.getName()+","+this.getEmail()+","+this.getPhoneNumber()+","+this.getPassword()
                +","+this.getLanguagePref();
        System.out.println(tourist);
        return tourist;
    }




}
