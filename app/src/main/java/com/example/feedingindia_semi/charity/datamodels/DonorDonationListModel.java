package com.example.feedingindia_semi.charity.datamodels;

public class DonorDonationListModel {

    String name;
    String thumb_image;
    String profession;
    String email;

    DonorDonationListModel() {
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public void setThumb_image(String thumb_image) {
        this.thumb_image = thumb_image;
    }
    public String getThumb_image() {
        return thumb_image;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }
    public String getProfession() {
        return profession;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getEmail() {
        return email;
    }
}