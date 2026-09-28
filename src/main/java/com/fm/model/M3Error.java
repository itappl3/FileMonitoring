package com.fm.model;

import java.sql.Timestamp;

public class M3Error {

    private int id;
    private String fileName;
    private String filePath;
    private Timestamp detectedTime;
    private String statusFlag;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Timestamp getDetectedTime() {
        return detectedTime;
    }

    public void setDetectedTime(Timestamp detectedTime) {
        this.detectedTime = detectedTime;
    }

    public String getStatusFlag() {
        return statusFlag;
    }

    public void setStatusFlag(String statusFlag) {
        this.statusFlag = statusFlag;
    }
}
