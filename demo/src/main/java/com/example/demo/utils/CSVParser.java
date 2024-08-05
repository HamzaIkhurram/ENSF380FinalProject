package com.example.demo.utils;

import com.example.demo.models.Station;
import com.example.demo.models.Train;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for parsing CSV files.
 */
public class CSVParser {

    /**
     * Parses a CSV file to extract station data.
     *
     * @param filePath the path to the CSV file
     * @return a list of stations
     */
    public static List<Station> parseStations(String filePath) {
        List<Station> stations = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine(); // Skip header
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                // Assuming CSV format: Row, Line, StationNumber, StationCode, StationName, X, Y, Common Stations
                stations.add(new Station(values[1], values[3], values[4], Double.parseDouble(values[5]), Double.parseDouble(values[6]), values.length > 7 ? values[7] : ""));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stations;
    }

    /**
     * Parses a CSV file to extract coordinate data.
     *
     * @param filePath the path to the CSV file
     * @return a list of coordinates as double arrays
     */
    public static List<double[]> parseCoordinates(String filePath) {
        List<double[]> coordinates = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine(); // Skip header
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                coordinates.add(new double[]{Double.parseDouble(values[0]), Double.parseDouble(values[1])});
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return coordinates;
    }
}
