package com.example.btlapi.model;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.List;

public class Food implements Serializable {
    private String idFood;
    private String images;
    private String nameFood;
    private double salePrice;
    private double cost;
    private int usage;
    private String detailFood;
    private String intro;

    private boolean status;
    private float rating;

    public Food(String idFood, String images, String nameFood, double salePrice, double cost, int usage, String detailFood, String intro, boolean status, float rating) {
        this.idFood = idFood;
        this.images = images;
        this.nameFood = nameFood;
        this.salePrice = salePrice;
        this.cost = cost;
        this.usage = usage;
        this.detailFood = detailFood;
        this.intro = intro;
        this.status = status;
        this.rating = rating;
    }

/*    public Food(String idFood) {
        this.idFood = idFood;
    }*/



    public String getIdFood() {
        return idFood;
    }

    public void setIdFood(String idFood) {
        this.idFood = idFood;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getNameFood() {
        return nameFood;
    }

    public void setNameFood(String nameFood) {
        this.nameFood = nameFood;
    }

    public double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(double salePrice) {
        this.salePrice = salePrice;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public int getUsage() {
        return usage;
    }

    public void setUsage(int usage) {
        this.usage = usage;
    }

    public String getDetailFood() {
        return detailFood;
    }

    public void setDetailFood(String detailFood) {
        this.detailFood = detailFood;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }



}
