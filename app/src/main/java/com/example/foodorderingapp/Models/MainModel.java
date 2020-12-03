package com.example.foodorderingapp.Models;

public class MainModel {
    int mainImage;
    String mainName, mainPrice, mainDescription;

    // Constructor
    public MainModel(int mainImage, String mainName, String mainPrice, String mainDescription) {
        this.mainImage = mainImage;
        this.mainName = mainName;
        this.mainPrice = mainPrice;
        this.mainDescription = mainDescription;
    }

    // Getters and Setters methods
    public int getMainImage() {
        return mainImage;
    }

    public void setMainImage(int mainImage) {
        this.mainImage = mainImage;
    }

    public String getMainName() {
        return mainName;
    }

    public void setMainName(String mainName) {
        this.mainName = mainName;
    }

    public String getMainPrice() {
        return mainPrice;
    }

    public void setMainPrice(String mainPrice) {
        this.mainPrice = mainPrice;
    }

    public String getMainDescription() {
        return mainDescription;
    }

    public void setMainDescription(String mainDescription) {
        this.mainDescription = mainDescription;
    }
}
