package com.example.demo.models;

public class Station {
    private String line;
    private String stationCode;
    private String stationName;
    private double x;
    private double y;
    private String commonStations;

    // Constructor
    public Station(String line, String stationCode, String stationName, double x, double y, String commonStations) {
        this.line = line;
        this.stationCode = stationCode;
        this.stationName = stationName;
        this.x = x;
        this.y = y;
        this.commonStations = commonStations;
    }

    public String getLine() {
        return line;
    }

    public String getStationCode() {
        return stationCode;
    }

    public String getStationName() {
        return stationName;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public String getCommonStations() {
        return commonStations;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public void setStationCode(String stationCode) {
        this.stationCode = stationCode;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setCommonStations(String commonStations) {
        this.commonStations = commonStations;
    }
}