package com.fm.model;

import java.sql.Timestamp;

public class M3Missing {

    private int id;

    private String wmsOrderNo;

    private Timestamp date;

    private String itemCode;

    private double qty;

    private String storerCode;


    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public M3Missing() {
    }


    // =========================================================
    // ID
    // =========================================================

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    // =========================================================
    // WMS ORDER NO
    // =========================================================

    public String getWmsOrderNo() {
        return wmsOrderNo;
    }

    public void setWmsOrderNo(String wmsOrderNo) {
        this.wmsOrderNo = wmsOrderNo;
    }


    // =========================================================
    // DATE
    // =========================================================

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }


    // =========================================================
    // ITEM CODE
    // =========================================================

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }


    // =========================================================
    // QTY
    // =========================================================

    public double getQty() {
        return qty;
    }

    public void setQty(double qty) {
        this.qty = qty;
    }


    // =========================================================
    // STORER CODE
    // =========================================================

    public String getStorerCode() {
        return storerCode;
    }

    public void setStorerCode(String storerCode) {
        this.storerCode = storerCode;
    }

}
