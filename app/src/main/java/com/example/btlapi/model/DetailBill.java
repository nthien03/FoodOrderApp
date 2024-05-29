package com.example.btlapi.model;

import java.util.Date;

public class DetailBill {
    private Bill bill;
    private int amount;
    private Food food;

    public DetailBill(Bill bill, int amount, Food food) {
        this.bill = bill;
        this.amount = amount;
        this.food = food;
    }

    public Bill getBill() {
        return bill;
    }

    public void setBill(Bill bill) {
        this.bill = bill;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }
}
