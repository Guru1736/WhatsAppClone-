package com.guruprasad.whatsappclone.Models;

public class users {

    String profile_pic  , fullname , mail , password ,userID , lastmessage ;

    public users(String profile_pic, String fullname, String mail, String password, String userID, String lastmessage) {
        this.profile_pic = profile_pic;
        this.fullname = fullname;
        this.mail = mail;
        this.password = password;
        this.userID = userID;
        this.lastmessage = lastmessage;
    }

    public users() {
    }

    // signUP constructor

    public users( String fullname, String mail, String password) {

        this.fullname = fullname;
        this.mail = mail;
        this.password = password;

    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getProfile_pic() {
        return profile_pic;
    }

    public void setProfile_pic(String profile_pic) {
        this.profile_pic = profile_pic;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



    public String getLastmessage() {
        return lastmessage;
    }

    public void setLastmessage(String lastmessage) {
        this.lastmessage = lastmessage;
    }
}
