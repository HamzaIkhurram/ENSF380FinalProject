package com.example.demo.utils;

import com.example.demo.models.Station;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CSVParserTest {

    @Test
    public void testParseStations() {
        String filePath = "src/test/resources/data/simulator/subway.csv";
        List<Station> stations = CSVParser.parseStations(filePath);

        assertNotNull(stations);
        assertEquals(120, stations.size());

        Station station1 = stations.get(0);
        assertEquals("R", station1.getLine());
        assertEquals("R01", station1.getStationCode());
        assertEquals("Maplewood Station", station1.getStationName().trim());
        assertEquals(8.756969333, station1.getX(), 0.01);
        assertEquals(14.79016876, station1.getY(), 0.01);
        assertEquals("", station1.getCommonStations());

        Station station2 = stations.get(1);
        assertEquals("R", station2.getLine());
        assertEquals("R02", station2.getStationCode());
        assertEquals("Lakeview Heights Station", station2.getStationName().trim());
        assertEquals(35.30510521, station2.getX(), 0.01);
        assertEquals(38.3885107, station2.getY(), 0.01);
        assertEquals("", station2.getCommonStations());

        Station station3 = stations.get(2);
        assertEquals("R", station3.getLine());
        assertEquals("R03", station3.getStationCode());
        assertEquals("Green Hills Station", station3.getStationName().trim());
        assertEquals(64.06567669, station3.getX(), 0.01);
        assertEquals(56.37714005, station3.getY(), 0.01);
        assertEquals("", station3.getCommonStations());
    }

    @Test
    public void testParseCoordinates() {
        String filePath = "src/test/resources/data/map/Blue.csv";
        List<double[]> coordinates = CSVParser.parseCoordinates(filePath);

        assertNotNull(coordinates);
        assertEquals(40, coordinates.size());

        double[][] expectedCoordinates = {
                {7.895084381103516, 669.8231658935547},
                {38.60073184967041, 646.0697326660156},
                {61.51604747772217, 616.2509307861328},
                {77.27171993255615, 581.0547409057617},
                {116.08906364440918, 581.5792999267578},
                {154.90771865844727, 581.9108734130859},
                {193.7286148071289, 581.9108734130859},
                {222.53331756591797, 567.6107559204102},
                {243.7357292175293, 538.6774978637695},
                {279.514461517334, 523.6128005981445}
        };

        for (int i = 0; i < 10; i++) {
            assertArrayEquals(expectedCoordinates[i], coordinates.get(i), 0.01);
        }
    }
    }

