package com.example.demo.models;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TrainTest {

    private Train train;
    private List<Station> stations;

    @Before
    public void setUp() {
        train = new Train("T01", "Red", 0, "forward", 123.45, 678.90);

        stations = new ArrayList<>();
        stations.add(new Station("Red", "R01", "Central", 123.45, 678.90, "R01,R02,R03"));
        stations.add(new Station("Red", "R02", "North", 223.45, 778.90, "R01,R02,R03"));
        stations.add(new Station("Red", "R03", "South", 323.45, 878.90, "R01,R02,R03"));
    }

    @Test
    public void testConstructorAndGetters() {
        // Test the constructor and getters
        assertEquals("T01", train.getId());
        assertEquals("Red", train.getLine());
        assertEquals(0, train.getCurrentStationIndex());
        assertEquals("forward", train.getDirection());
        assertEquals(123.45, train.getX(), 0.001);
        assertEquals(678.90, train.getY(), 0.001);
    }

    @Test
    public void testSetters() {
        // Test the setters
        train.setId("T02");
        train.setLine("Blue");
        train.setCurrentStationIndex(1);
        train.setDirection("backward");
        train.setX(543.21);
        train.setY(987.65);

        // Verify the updated values using the getters
        assertEquals("T02", train.getId());
        assertEquals("Blue", train.getLine());
        assertEquals(1, train.getCurrentStationIndex());
        assertEquals("backward", train.getDirection());
        assertEquals(543.21, train.getX(), 0.001);
        assertEquals(987.65, train.getY(), 0.001);
    }

    @Test
    public void testMoveForward() {
        train.move(1, stations);
        assertEquals(1, train.getCurrentStationIndex());
        assertEquals(223.45, train.getX(), 0.001);
        assertEquals(778.90, train.getY(), 0.001);
    }

    @Test
    public void testMoveBackward() {
        train.setDirection("backward");
        train.move(1, stations);
        assertEquals(2, train.getCurrentStationIndex());
        assertEquals(323.45, train.getX(), 0.001);
        assertEquals(878.90, train.getY(), 0.001);
    }

    @Test
    public void testGetCurrentStation() {
        Station currentStation = train.getCurrentStation(stations);
        assertNotNull(currentStation);
        assertEquals("Central", currentStation.getStationName());
    }

    @Test
    public void testGetNextStation() {
        Station nextStation = train.getNextStation(stations);
        assertNotNull(nextStation);
        assertEquals("North", nextStation.getStationName());
    }

    @Test
    public void testGetFutureStations() {
        List<Station> futureStations = train.getFutureStations(stations, 2);
        assertEquals(2, futureStations.size());
        assertEquals("North", futureStations.get(0).getStationName());
        assertEquals("South", futureStations.get(1).getStationName());
    }

    @Test
    public void testGetPastStation() {
        Station pastStation = train.getPastStation(stations);
        assertNotNull(pastStation);
        assertEquals("South", pastStation.getStationName());
    }
}
