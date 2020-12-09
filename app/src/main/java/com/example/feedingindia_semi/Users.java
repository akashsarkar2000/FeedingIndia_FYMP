package com.example.feedingindia_semi;

public class Users {

    public String donor_name;
    public String image;
    public String profession;
    public String thumb_image;

    public Users() {
    }


    public Users(String name, String image, String status, String thumb_image) {
        this.donor_name = name;
        this.image = image;
        this.profession = status;
        this.thumb_image = thumb_image;
    }

    public void setName(String name) {
        this.donor_name = name;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setStatus(String status) {
        this.profession = profession;
    }

    public String getName() {
        return donor_name;
    }

    public String getImage() {
        return image;
    }

    public String getStatus() {
        return profession;
    }

    public void setThumb_image(String thumb_image) {
        this.thumb_image = thumb_image;
    }

    public String getThumb_image() {
        return thumb_image;
    }


}