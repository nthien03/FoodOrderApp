package com.example.btlapi.post;

import com.example.btlapi.model.Food;
import com.example.btlapi.model.ShoppingCart;

public class DetailShoppingCartPost {
    private String idShoppingCart;

    private int amount;
    private String idFood;
    private Boolean status;

    public DetailShoppingCartPost(String idShoppingCart, int amount, String idFood, Boolean status) {
        this.idShoppingCart = idShoppingCart;
        this.amount = amount;
        this.idFood = idFood;
        this.status = status;
    }
}
