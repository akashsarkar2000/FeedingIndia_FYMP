package com.example.feedingindia_semi.charity.datamodels;

public class DonorDonationData {

    String donor_donation_name;
    String donor_donation_phone;
    String food_name;
    String food_quantity_kg;
    String food_expiry_date;
    String food_donation_date;
    String donation_donor_email;
    String money_amount;
    String money_purpose;
    String requirement_date;
    String requirements_items;
    String uid;

    public DonorDonationData(){}

    public String getDonorName() { return donor_donation_name; }
    public void setDonorName(String donor_donation_name) { this.donor_donation_name = donor_donation_name; }

    public String getDonorPhone() { return donor_donation_phone; }
    public void setDonorPhone(String donor_donation_phone) { this.donor_donation_phone = donor_donation_phone; }

    public String getFoodName() { return food_name; }
    public void setFoodName(String food_name) { this.food_name = food_name; }


    public String getFoodQuantityKg() { return food_quantity_kg; }
    public void setFoodQuantityKg(String food_quantity_kg) { this.food_quantity_kg = food_quantity_kg; }


    public String getFoodExpiryDate() { return food_expiry_date; }
    public void setFoodExpiryDate(String food_expiry_date) { this.food_expiry_date = food_expiry_date; }


    public String getFoodDonationDate() { return food_donation_date; }
    public void setFoodDonationDate(String food_donation_date) { this.food_donation_date = food_donation_date; }


    public String getDonationDonorEmail() { return donation_donor_email; }
    public void setDonationDonorEmail(String donation_donor_email) { this.donation_donor_email = donation_donor_email; }


    public String getMoneyPurpose() { return money_purpose; }
    public void setMoneyPurpose(String money_purpose) { this.money_purpose = money_purpose; }


    public String getRequirementsDate() { return requirement_date; }
    public void setRequirementsDate(String requirement_date) { this.requirement_date = requirement_date; }


    public String getRequirementsItems() { return requirements_items; }
    public void setRequirementsItems(String requirements_items) { this.requirements_items = requirements_items; }


    public String getMoneyAmount() { return money_amount; }
    public void setMoneyAmount(String money_amount) { this.money_amount = money_amount; }


    public String getUid() {
        return uid;
    }
    public void setUid(String uid) {
        this.uid = uid;
    }


}
