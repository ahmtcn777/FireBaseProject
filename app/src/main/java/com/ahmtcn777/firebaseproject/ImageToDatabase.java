package com.ahmtcn777.firebaseproject;

public class ImageToDatabase {
    public String userEmail;
    public String imageName;
    public String time;

    public ImageToDatabase(){

    }

    public ImageToDatabase(String userEmail, String imageName, String time){
        this.userEmail=userEmail;
        this.imageName=imageName;
        this.time = time;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getImageName() {
        return imageName;
    }
}
