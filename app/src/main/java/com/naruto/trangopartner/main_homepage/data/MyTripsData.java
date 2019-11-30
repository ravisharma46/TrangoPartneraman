package com.naruto.trangopartner.main_homepage.data;

public class MyTripsData {

    private String date, distance, travelTime, referenceNumber;
    boolean download = false;

    public MyTripsData(String date, String distance, String travelTime, String referenceNumber, boolean download) {
        this.date = date;
        this.distance = distance;
        this.travelTime = travelTime;
        this. referenceNumber = referenceNumber;
        this.download = download;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getTravelTime() {
        return travelTime;
    }

    public void setTravelTime(String travelTime) {
        this.travelTime = travelTime;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public boolean isDownload() {
        return download;
    }

    public void setDownload(boolean download) {
        this.download = download;
    }
}
