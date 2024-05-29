package com.example.btlapi.manager;

import com.example.btlapi.model.NumberTable;



public class TableManager {
    private static TableManager instance;
    private NumberTable numberTable;

    private TableManager() {
        // Khởi tạo một bàn mới
        numberTable = new NumberTable();
    }

    public static synchronized TableManager getInstance() {
        if (instance == null) {
            instance = new TableManager();
        }
        return instance;
    }

    public NumberTable getNumberTable() {
        return numberTable;
    }

    public void setNumberTable(NumberTable numberTable) {
        this.numberTable = numberTable;
    }

    // Các phương thức khác để quản lý thông tin của bàn
}


