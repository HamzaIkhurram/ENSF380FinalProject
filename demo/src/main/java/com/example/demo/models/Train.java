package com.example.demo.models;

import java.util.ArrayList;
import java.util.List;

public class Train {
    private String id;
    private String line;
    private int currentStationIndex;
    private String direction;
    private double x;
    private double y;

    // Constructor
    public Train(String id, String line, int currentStationIndex, String direction, double x, double y) {
        this.id = id;
        this.line = line;
        this.currentStationIndex = currentStationIndex;
        this.direction = direction;
        this.x = x;
        this.y = y;
    }

    // Getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public int getCurrentStationIndex() {
        return currentStationIndex;
    }

    public void setCurrentStationIndex(int currentStationIndex) {
        this.currentStationIndex = currentStationIndex;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

   public void move(int numberOfStations, List<Station> stations) {
       if (stations == null || stations.size() == 0) {
           System.out.println("No stations available for line: " + line);
           return;
       }

       if (direction.equals("forward")) {
           currentStationIndex = (currentStationIndex + numberOfStations) % stations.size();
       } else {
           currentStationIndex = (currentStationIndex - numberOfStations + stations.size()) % stations.size();
       }
       Station station = stations.get(currentStationIndex);
       this.x = station.getX();
       this.y = station.getY();
   }

    public Station getCurrentStation(List<Station> stations) {
        return stations.get(currentStationIndex);
    }

    public Station getNextStation(List<Station> stations) {
        int nextIndex = (currentStationIndex + 1) % stations.size();
        return stations.get(nextIndex);
    }

    public List<Station> getFutureStations(List<Station> stations, int count) {
        List<Station> futureStations = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            int index = (currentStationIndex + i) % stations.size();
            futureStations.add(stations.get(index));
        }
        return futureStations;
    }

    public Station getPastStation(List<Station> stations) {
        int pastIndex = (currentStationIndex - 1 + stations.size()) % stations.size();
        return stations.get(pastIndex);
    }
}
