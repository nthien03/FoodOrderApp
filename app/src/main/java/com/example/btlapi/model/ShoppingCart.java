package com.example.btlapi.model;

import java.util.Date;

public class ShoppingCart {
    private String idShoppingCart;
    private Date createAt;
    private NumberTable numberTable;

/*    public ShoppingCart(String idShoppingCart) {
        this.idShoppingCart = idShoppingCart;
    }*/

    public ShoppingCart(String idShoppingCart, Date createAt, NumberTable numberTable) {
        this.idShoppingCart = idShoppingCart;
        this.createAt = createAt;
        this.numberTable = numberTable;
    }
/*    public ShoppingCart(String idShoppingCart, Date createAt, String idTable) {
        this.idShoppingCart = idShoppingCart;
        this.createAt = createAt;
        this.numberTable = new NumberTable(idTable);
    }*/



    public String getIdShoppingCart() {
        return idShoppingCart;
    }

    public void setIdShoppingCart(String idShoppingCart) {
        this.idShoppingCart = idShoppingCart;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public NumberTable getNumberTable() {
        return numberTable;
    }

    public void setNumberTable(NumberTable numberTable) {
        this.numberTable = numberTable;
    }
}
