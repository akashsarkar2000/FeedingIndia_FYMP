package com.example.feedingindia_semi.donor.datamodels;

public class TrustedDonorByCharityData {

    String message;
    String uid;
    String datetime;
    String email;

    public TrustedDonorByCharityData(){}

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    public String getUid() {
        return uid;
    }
    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getDatetime() {
        return datetime;
    }
    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
}