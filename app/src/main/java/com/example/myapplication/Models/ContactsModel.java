package com.example.myapplication.Models;

class ContactsModel {

    String name;
    String image_id;
    String contact_number;
    String time_to_call;

    public ContactsModel(String name, String contact_number) {
        this.name = name;
        this.contact_number = contact_number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact_number() {
        return contact_number;
    }

    public void setTime_to_call(String time_to_call) {
        this.time_to_call = time_to_call;
    }

    public String getTime_to_call() {
        return time_to_call;
    }

    public void setContact_number(String contact_number) {
        this.contact_number = contact_number;
    }

}
