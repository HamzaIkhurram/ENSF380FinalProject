package com.example.demo.utils;

import com.example.demo.models.Station;
import com.example.demo.models.Train;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

public class OutputTrainsPositionTest {

    private Path tempDir;

    @Before
    public void setUp() throws Exception {
        // Create a temporary directory for the test
        tempDir = Files.createTempDirectory("testOutput");
    }

    @After
    public void tearDown() throws Exception {
        // Delete the temporary directory and its contents
        Files.walk(tempDir)
            .map(Path::toFile)
            .forEach(File::delete);
    }

    @Test
    public void testPrintTrainPositions() throws Exception {
        // Test data
        List<Station> stations = Arrays.asList(
            new Station("Line1", "A", "Station1", 0.0, 0.0, "B"),
            new Station("Line1", "B", "Station2", 1.0, 1.0, "A,C"),
            new Station("Line1", "C", "Station3", 2.0, 2.0, "B,D"),
            new Station("Line1", "D", "Station4", 3.0, 3.0, "C,E"),
            new Station("Line1", "E", "Station5", 4.0, 4.0, "D")
        );

        List<Train> trains = Arrays.asList(
            new Train("T1", "Line1", 0, "forward", 0.0, 0.0),
            new Train("T2", "Line1", 1, "backward", 1.0, 1.0),
            new Train("T3", "Line1", 2, "forward", 2.0, 2.0),
            new Train("T4", "Line1", 3, "backward", 3.0, 3.0),
            new Train("T5", "Line1", 4, "forward", 4.0, 4.0)
        );

        // Run the method
        OutputTrainsPosition.printTrainPositions(trains, stations, tempDir.toString());

        // Find the created CSV file
        File[] files = tempDir.toFile().listFiles((dir, name) -> name.endsWith(".csv"));
        assertTrue("CSV file should be created", files != null && files.length > 0);

        File csvFile = files[0];

        // Read and verify the content of the CSV file
        try (BufferedReader reader = new BufferedReader(new FileReader(csvFile))) {
            String header = reader.readLine();
            assertEquals("LineName,TrainNumber,StationCode,Direction,Destination", header);

            String line1 = reader.readLine();
            assertEquals("Line1,1,A,forward,E", line1);

            String line2 = reader.readLine();
            assertEquals("Line1,2,B,backward,A", line2);

            String line3 = reader.readLine();
            assertEquals("Line1,3,C,forward,E", line3);

            String line4 = reader.readLine();
            assertEquals("Line1,4,D,backward,A", line4);

            String line5 = reader.readLine();
            assertEquals("Line1,5,E,forward,E", line5);
        }
    }
}
