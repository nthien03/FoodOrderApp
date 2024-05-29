package com.example.btlapi.model;

import java.util.Date;

public class Rate {
    private Date createAt;
    private String comment;
    private String idFood;
    private int valueRate;

    public Rate(String idFood, String comment, int valueRate) {
        this.idFood = idFood;
        this.comment = comment;
        this.valueRate = valueRate;
    }

    public Rate( Date createAt, String comment, String idFood, int valueRate) {
        this.createAt = createAt;
        this.comment = comment;
        this.idFood = idFood;
        this.valueRate = valueRate;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getIdFood() {
        return idFood;
    }

    public void setIdFood(String idFood) {
        this.idFood = idFood;
    }

    public int getValueRate() {
        return valueRate;
    }

    public void setValueRate(int valueRate) {
        this.valueRate = valueRate;
    }
}
