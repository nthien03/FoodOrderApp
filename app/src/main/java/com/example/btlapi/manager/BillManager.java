package com.example.btlapi.manager;

import android.util.Log;

import com.example.btlapi.model.DetailBill;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BillManager {
    private static BillManager instance;
    private ArrayList<DetailBill> detailBills;

    private BillManager() {
        detailBills = new ArrayList<>();
    }

    public static synchronized BillManager getInstance() {
        if (instance == null) {
            instance = new BillManager();
        }
        return instance;
    }

    public void addDetailBill(DetailBill detailBill) {
        detailBills.add(detailBill);
    }

    public ArrayList<DetailBill> getDetailBills() {
        return detailBills;
    }

    public double calculateTotalBill() {
        double totalPrice = 0.0;
        for (DetailBill detail : detailBills) {
            totalPrice += detail.getFood().getSalePrice() * detail.getAmount();
        }
        return totalPrice;
    }
    public void mergeItemsWithSameID() {
        Map<String, DetailBill> map = new HashMap<>();
        for (DetailBill detail : detailBills) {
            String foodID = detail.getFood().getIdFood();
            if (map.containsKey(foodID)) {
                DetailBill existingDetail = map.get(foodID);
                existingDetail.setAmount(existingDetail.getAmount() + detail.getAmount());
            } else {
                map.put(foodID, detail);
            }
        }
        detailBills.clear();
        detailBills.addAll(map.values());
    }

    public void clearBill() {
        detailBills.clear();
    }

}

