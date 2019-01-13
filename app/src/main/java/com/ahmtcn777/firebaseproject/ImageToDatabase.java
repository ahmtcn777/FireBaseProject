package com.ahmtcn777.firebaseproject;

public class ImageToDatabase {
    public String userEmail;
    public String imageName;

    public ImageToDatabase(){

    }

    public ImageToDatabase(String userEmail, String imageName){
        this.userEmail=userEmail;
        this.imageName=imageName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getImageName() {
        return imageName;
    }
}
