package com.example.myapplication.Models;

public class Registration {
    private String EmployeeCode;
    private String Name;
    private String ContactNo;
    private String TimeStamp;
    private String EmailAddress;
    private String Password;
    private String EmployeeType;
    private String DOB;



    public Registration() {
    }

    public String getEmployeeType() {
        return EmployeeType;
    }

    public void setEmployeeType(String employeeType) {
        EmployeeType = employeeType;
    }

    public Registration(String employeeId, String name, String contactNo, String timeStamp, String emailAddress, String password, String dob) {
        EmployeeCode = employeeId;
        Name = name;
        ContactNo = contactNo;
        TimeStamp = timeStamp;
        EmailAddress = emailAddress;
        Password = password;
        DOB = dob;
    }

    public String getEmployeeCode() {
        return EmployeeCode;
    }

    public void setEmployeeCode(String employeeId) {
        EmployeeCode = employeeId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getContactNo() {
        return ContactNo;
    }

    public void setContactNo(String contactNo) {
        ContactNo = contactNo;
    }

    public String getTimeStamp() {
        return TimeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        TimeStamp = timeStamp;
    }

    public String getEmailAddress() {
        return EmailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        EmailAddress = emailAddress;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
