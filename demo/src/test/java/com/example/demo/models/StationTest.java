package com.example.demo.models;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class StationTest {

    @Test
    public void testConstructorAndGetters() {
        // Create a Station object using the constructor
        Station station = new Station("Red", "R01", "Central", 123.45, 678.90, "R01,R02,R03");

        // Test the getters
        assertEquals("Red", station.getLine());
        assertEquals("R01", station.getStationCode());
        assertEquals("Central", station.getStationName());
        assertEquals(123.45, station.getX(), 0.001);
        assertEquals(678.90, station.getY(), 0.001);
        assertEquals("R01,R02,R03", station.getCommonStations());
    }

    @Test
    public void testSetters() {
        // Create a Station object using the constructor
        Station station = new Station("Red", "R01", "Central", 123.45, 678.90, "R01,R02,R03");

        // Test the setters
        station.setLine("Blue");
        station.setStationCode("B01");
        station.setStationName("North");
        station.setX(543.21);
        station.setY(987.65);
        station.setCommonStations("B01,B02,B03");

        // Verify the updated values using the getters
        assertEquals("Blue", station.getLine());
        assertEquals("B01", station.getStationCode());
        assertEquals("North", station.getStationName());
        assertEquals(543.21, station.getX(), 0.001);
        assertEquals(987.65, station.getY(), 0.001);
        assertEquals("B01,B02,B03", station.getCommonStations());
    }

    @Test
    public void testSettersAndGetters() {
        // Create a Station object with default values
        Station station = new Station(null, null, null, 0, 0, null);

        // Set new values using setters
        station.setLine("Green");
        station.setStationCode("G01");
        station.setStationName("East");
        station.setX(111.11);
        station.setY(222.22);
        station.setCommonStations("G01,G02,G03");

        // Verify the values using getters
        assertEquals("Green", station.getLine());
        assertEquals("G01", station.getStationCode());
        assertEquals("East", station.getStationName());
        assertEquals(111.11, station.getX(), 0.001);
        assertEquals(222.22, station.getY(), 0.001);
        assertEquals("G01,G02,G03", station.getCommonStations());
    }
}
