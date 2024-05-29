package com.example.btlapi.model;

public class NumberTable {
    private String idTable;
    private int numberTable;
    private Boolean status;

    public NumberTable() {
    }

/*    public NumberTable(String idTable) {
        this.idTable = idTable;
    }*/

    public NumberTable(String idTable, int numberTable, Boolean status) {
        this.idTable = idTable;
        this.numberTable = numberTable;
        this.status = status;
    }

    public String getIdTable() {
        return idTable;
    }

    public void setIdTable(String idTable) {
        this.idTable = idTable;
    }

    public int getNumberTable() {
        return numberTable;
    }

    public void setNumberTable(int numberTable) {
        this.numberTable = numberTable;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
