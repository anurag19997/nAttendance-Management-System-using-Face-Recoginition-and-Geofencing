package com.example.myapplication.Models;
//
//import com.activeandroid.Model;
//import com.activeandroid.annotation.Column;
//import com.activeandroid.annotation.Table;
//

public class Lead {

    public String companyName;


    public String leadOwner;

    public String firstName;

    public String lastName;

    public String designation;

    public String email;

    public String phoneNo;

    public String leadSourse;

    public String nextActivity;

    public String socialNetwork;

    public String adress;

    public String spancoStatus;

    public String date;

    public String time;

    public String remarks;

    public String PanCardNo;

    public String GSTINNo;

    public String AdharCardNo;

//    public String getPanCardNo() {
//        return PanCardNo;
//    }
//
//    public void setPanCardNo(String panCardNo) {
//        PanCardNo = panCardNo;
//    }
//
//    public String getGSTINNo() {
//        return GSTINNo;
//    }
//
//    public void setGSTINNo(String GSTINNo) {
//        this.GSTINNo = GSTINNo;
//    }
//
//    public String getAdharCardNo() {
//        return AdharCardNo;
//    }
//
//    public void setAdharCardNo(String adharCardNo) {
//        AdharCardNo = adharCardNo;
//    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getSpancoStatus() {
        return spancoStatus;
    }

    public void setSpancoStatus(String spancoStatus) {
        this.spancoStatus = spancoStatus;
    }

    public Lead() {
        super();
    }

    public Lead(String companyName, String leadOwner, String firstName, String lastName, String designation, String email, String phoneNo, String leadSourse, String nextActivity, String socialNetwork, String adress, String spancoStatus) {

        this.companyName = companyName;
        this.leadOwner = leadOwner;
        this.firstName = firstName;
        this.lastName = lastName;
        this.designation = designation;
        this.email = email;
        this.phoneNo = phoneNo;
        this.leadSourse = leadSourse;
        this.nextActivity = nextActivity;
        this.socialNetwork = socialNetwork;
        this.adress = adress;
        this.spancoStatus = spancoStatus;
    }

    public Lead(String companyName, String leadOwner, String firstName, String lastName, String designation, String email, String phoneNo, String leadSourse, String nextActivity, String socialNetwork, String adress, String spancoStatus, String panCardNo, String GSTINNo, String adharCardNo) {
        this.companyName = companyName;
        this.leadOwner = leadOwner;
        this.firstName = firstName;
        this.lastName = lastName;
        this.designation = designation;
        this.email = email;
        this.phoneNo = phoneNo;
        this.leadSourse = leadSourse;
        this.nextActivity = nextActivity;
        this.socialNetwork = socialNetwork;
        this.adress = adress;
        this.spancoStatus = spancoStatus;
        PanCardNo = panCardNo;
        this.GSTINNo = GSTINNo;
        AdharCardNo = adharCardNo;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getLeadOwner() {
        return leadOwner;
    }

    public void setLeadOwner(String leadOwner) {
        this.leadOwner = leadOwner;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getLeadSourse() {
        return leadSourse;
    }

    public void setLeadSourse(String leadSourse) {
        this.leadSourse = leadSourse;
    }

    public String getNextActivity() {
        return nextActivity;
    }

    public void setNextActivity(String nextActivity) {
        this.nextActivity = nextActivity;
    }

    public String getSocialNetwork() {
        return socialNetwork;
    }

    public void setSocialNetwork(String socialNetwork) {
        this.socialNetwork = socialNetwork;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }
}
