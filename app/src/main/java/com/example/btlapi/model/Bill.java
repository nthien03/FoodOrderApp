package com.example.btlapi.model;

import java.util.Date;

public class Bill {
    private String idBill;
    private Date createAt;
    private boolean status;
    private double total;

    public Bill(String idBill, Date createAt, boolean status, double total) {
        this.idBill = idBill;
        this.createAt = createAt;
        this.status = status;
        this.total = total;
    }

    public String getIdBill() {
        return idBill;
    }

    public void setIdBill(String idBill) {
        this.idBill = idBill;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
