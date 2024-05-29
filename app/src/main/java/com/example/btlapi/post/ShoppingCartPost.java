package com.example.btlapi.post;

import com.example.btlapi.model.NumberTable;

import java.util.Date;

public class ShoppingCartPost {
    private String idShoppingCart;
    private Date createAt;
    private String idTable;

    public ShoppingCartPost(String idShoppingCart, Date createAt, String idTable) {
        this.idShoppingCart = idShoppingCart;
        this.createAt = createAt;
        this.idTable = idTable;
    }

}
