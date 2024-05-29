package com.example.btlapi.post;

import com.example.btlapi.model.Bill;
import com.example.btlapi.model.Food;

public class DetailBillPost {
    private String idBill;
    private int amount;
    private String idFood;

    public DetailBillPost(String idBill, int amount, String idFood) {
        this.idBill = idBill;
        this.amount = amount;
        this.idFood = idFood;
    }
}
