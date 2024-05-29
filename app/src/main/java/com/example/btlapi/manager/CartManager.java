package com.example.btlapi.manager;

import android.util.Log;

import com.example.btlapi.model.DetailShoppingCart;
import com.example.btlapi.model.Food;

import java.util.ArrayList;
import java.util.List;

public class CartManager {
    private static CartManager instance;
    private ArrayList<DetailShoppingCart> detailList;

    private CartManager() {
        detailList = new ArrayList<>();
    }

    public static synchronized CartManager getInstance() {
        if (instance == null) {
            instance = new CartManager();
        }
        return instance;
    }

    public void addDetail(DetailShoppingCart detailShoppingCart) {
        detailList.add(detailShoppingCart);
    }

    public ArrayList<DetailShoppingCart> getDetailList() {
        return detailList;
    }

    public void updateDetailAmount(int position, int newAmount) {
        if (position >= 0 && position < detailList.size()) {
            DetailShoppingCart detail = detailList.get(position);
            detail.setAmount(newAmount);
            detailList.set(position, detail);
        } else {
            // Ghi log thông báo lỗi về vị trí không hợp lệ được cung cấp để cập nhật chi tiết
            Log.e("CartManager", "Vị trí không hợp lệ được cung cấp để cập nhật chi tiết.");
        }
    }
    public double calculateTotalPrice() {
        double totalPrice = 0.0;
        for (DetailShoppingCart detail : detailList) {
            totalPrice += detail.getFood().getSalePrice() * detail.getAmount();
        }
        return totalPrice;
    }

    public boolean foodExists(String foodCodeToCheck) {
        for (DetailShoppingCart detail : detailList) {
            if (detail.getFood().getIdFood().equals(foodCodeToCheck)) {
                return true;
            }
        }
        return false;
    }
    public void increaseAmountForFood(String foodCodeToUpdate) {
        for (DetailShoppingCart detail : detailList) {
            if (detail.getFood().getIdFood().equals(foodCodeToUpdate)) {
                // Tăng số lượng của chi tiết đó lên 1
                detail.setAmount(detail.getAmount() + 1);
                return; // Đã tìm thấy và cập nhật số lượng, nên thoát khỏi phương thức
            }
        }
        // Nếu không tìm thấy thực phẩm trong danh sách chi tiết, bạn có thể xử lý tại đây
        Log.e("CartManager", "Thực phẩm không tồn tại trong giỏ hàng.");
    }
    public ArrayList<String> getFoodIds() {
        ArrayList<String> foodIds = new ArrayList<>();
        for (DetailShoppingCart detail : detailList) {
            foodIds.add(detail.getFood().getIdFood());
        }
        return foodIds;
    }
    public void clearCart() {
        detailList.clear();
    }


}

