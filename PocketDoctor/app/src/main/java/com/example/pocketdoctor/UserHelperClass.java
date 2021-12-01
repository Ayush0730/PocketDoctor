package com.example.pocketdoctor;

public class UserHelperClass {

    String firstname,lastname,email,phoneNo,password;

    public UserHelperClass() {
    }

    public UserHelperClass(String firstname, String lastname, String email, String phoneNo, String password) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.phoneNo = phoneNo;
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
