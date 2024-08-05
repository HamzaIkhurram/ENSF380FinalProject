package com.example.demo.utils;

import com.example.demo.models.Station;
import com.example.demo.models.Train;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Utility class for outputting train positions to a CSV file.
 */
public class OutputTrainsPosition {

    /**
     * Prints the positions of the trains and writes them to a CSV file.
     *
     * @param trains        the list of trains
     * @param stations      the list of stations
     * @param outputFolder  the folder to save the output CSV file
     */
    public static void printTrainPositions(List<Train> trains, List<Station> stations, String outputFolder) {
        if (outputFolder == null || outputFolder.trim().isEmpty()) {
            outputFolder = "src/main/output";
        }

        System.out.println("OutputFolder is : " + outputFolder);

        // Create the output directory if it doesn't exist
        File outputDir = new File(outputFolder);
        if (!outputDir.exists()) {
            boolean dirCreated = outputDir.mkdirs();
            if (!dirCreated) {
                System.err.println("Failed to create output directory: " + outputFolder);
                return; // or throw an exception if needed
            }
        }

        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String fileName = outputFolder + "/Trains_" + timestamp + ".csv";

        System.out.println("OutputFolder is : " + outputFolder);

        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write("LineName,TrainNumber,StationCode,Direction,Destination\n");
            for (Train train : trains) {
                Station currentStation = train.getCurrentStation(stations);
                Station destinationStation = train.getDirection().equals("forward")
                    ? stations.get(stations.size() - 1)
                    : stations.get(0);

                String lineName = train.getLine();
                String trainNumber = train.getId().substring(1);
                String stationCode = currentStation.getStationCode();
                String direction = train.getDirection();
                String destination = destinationStation.getStationCode();

                String position = String.format("%s,%s,%s,%s,%s", lineName, trainNumber, stationCode, direction, destination);
                writer.write(position + "\n");
                System.out.println(position);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
