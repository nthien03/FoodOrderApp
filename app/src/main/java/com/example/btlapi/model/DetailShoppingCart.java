package com.example.btlapi.model;

public class DetailShoppingCart {
    private ShoppingCart shoppingCart;
    private Food food;
    private int amount;
    private boolean status;


/*    public DetailShoppingCart(String idShoppingCart, int amount, String idFood, int status) {
        this.shoppingCart = new ShoppingCart(idShoppingCart);
        // Tạo một đối tượng Food từ idFood
        this.food = new Food(idFood); // Giả sử có một constructor cho Food nhận một idFood
        this.amount = amount;
        if(status == 1){
            this.status = true;
        } else if (status == 0) {
            this.status = false;
        }

    }*/


    public DetailShoppingCart(ShoppingCart shoppingCart, Food food, int amount, boolean status) {
        this.shoppingCart = shoppingCart;
        this.food = food;
        this.amount = amount;
        this.status = status;
    }

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
