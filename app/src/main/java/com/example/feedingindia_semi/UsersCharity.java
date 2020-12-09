package com.example.feedingindia_semi;

public class UsersCharity {

    public String charity_name;
    public String image;
    public String charity_address;
    public String thumb_image;

    public UsersCharity() {
    }

    public UsersCharity(String name, String image, String status, String thumb_image) {
        this.charity_name = name;
        this.image = image;
        this.charity_address = status;
        this.thumb_image = thumb_image;
    }

    public void setName(String name) {
        this.charity_name = name;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setStatus(String status) {
        this.charity_address = charity_address;
    }

    public String getName() {
        return charity_name;
    }

    public String getImage() {
        return image;
    }

    public String getStatus() {
        return charity_address;
    }

    public void setThumb_image(String thumb_image) {
        this.thumb_image = thumb_image;
    }

    public String getThumb_image() {
        return thumb_image;
    }


}