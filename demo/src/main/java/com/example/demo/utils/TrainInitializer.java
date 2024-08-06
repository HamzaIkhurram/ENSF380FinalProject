package com.example.demo.utils;

import com.example.demo.models.Train;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for initializing trains.
 */
public class TrainInitializer {
    private static final int NUM_TRAINS_PER_LINE = 4;

    /**
     * Initializes trains for the given coordinates of red, green, and blue lines.
     *
     * @param redCoordinates   the coordinates for the red line
     * @param greenCoordinates the coordinates for the green line
     * @param blueCoordinates  the coordinates for the blue line
     * @return a list of initialized trains
     */
    public static List<Train> initializeTrains(List<double[]> redCoordinates, List<double[]> greenCoordinates, List<double[]> blueCoordinates) {
        List<Train> trains = new ArrayList<>();

        if (redCoordinates == null || greenCoordinates == null || blueCoordinates == null  ) {
            return null;
        }

        if (redCoordinates.isEmpty() || greenCoordinates.isEmpty() || blueCoordinates.isEmpty()) {
            return trains;
        }




        for (int i = 0; i < NUM_TRAINS_PER_LINE; i++) {
            int index = i * 4;
            String direction = (i % 2 == 0) ? "forward" : "backward"; // Alternating directions

            trains.add(new Train("R" + (i + 1), "R", index, direction, redCoordinates.get(index)[0], redCoordinates.get(index)[1]));
            trains.add(new Train("G" + (i + 1), "G", index, direction, greenCoordinates.get(index)[0], greenCoordinates.get(index)[1]));
            trains.add(new Train("B" + (i + 1), "B", index, direction, blueCoordinates.get(index)[0], blueCoordinates.get(index)[1]));
        }

        System.out.println("The Size of Trains are : "+ trains.size());
        return trains;
    }
}
