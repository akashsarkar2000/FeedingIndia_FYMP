package com.example.feedingindia_semi.charity.datamodels;

public class DonorModel {
    String donor_name;
    String profession;
    String thumb_image;

    public DonorModel (){}

    public String getDonor_name() {
        return donor_name;
    }

    public void setDonor_name(String donor_name) {
        this.donor_name = donor_name;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }
    public String getProfession() {
        return profession;
    }

    public void setThumb_image(String thumb_image) {
        this.thumb_image = thumb_image;
    }

    public String getThumb_image() {
        return thumb_image;
    }

}
