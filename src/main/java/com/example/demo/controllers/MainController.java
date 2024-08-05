package com.example.demo.controllers;

import com.example.demo.models.Station;
import com.example.demo.models.Train;
import com.example.demo.services.AdvertisementService;
import com.example.demo.services.NewsService;
import com.example.demo.services.WeatherService;
import com.example.demo.utils.*;
import com.example.demo.WeatherParser;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class MainController {

    public Label temperatureLabel;
    public Label addTemperatureLabel;
    public Label windSpeedLabel;
    public VBox currentStationInfo;
    public HBox trainStationInfo;
    public Label currentStationLabel;
    public Label trainIDLabel;
    public Label nextStationLabel;
    public Label trainDirectionLabel;
    public HBox nextStationsInfo;
    public Label prevStation;
    public Label nextStation1;
    public Label nextStation2;
    public Label nextStation3;
    public Label nextStation4;
    private int currentAdIndex = 0;
    private List<String> adMediaPaths;
    public HBox topSection;
    public VBox adSection;
    public Label adLabel;
    public VBox weatherSection;
    public Label timeWeatherLabel;
    public Label trainInfoLabel;
    public VBox bottomSection;
    public HBox trainInfoSection;
    public ScrollPane newsScrollPane;
    public HBox newsSection;
    public Label newsLabel;
    public HBox trainInfoLeft;
    public Label trainDestinationLabel;
    public Label trainTimeLabel;
    public HBox trainInfoRight;
    public Label trainNextStopLabel;
    public Label trainNextTimeLabel;
    @FXML
    private Canvas mapCanvas;
    private GraphicsContext gc;

    private final NewsService newsService = new NewsService();
    private final NewsParser newsParser = new NewsParser();
    private final WeatherService weatherService = new WeatherService();
    private final WeatherParser weatherParser = new WeatherParser();
    private final AdvertisementService advertisementService = new AdvertisementService();

    private Timeline trainSimulationTimeline;
    private Timeline uiUpdateTimeline;
    private List<Train> trains;
    private List<Station> stations;
    private Image mapImage;

    private Timeline newsTimeline;
    private String newsKeyword;
    private String inputFile;
    private String outputFolder;

    @FXML
    public void initialize() {
        gc = mapCanvas.getGraphicsContext2D();
        loadNews();
        insertSampleAdvertisements();
        loadAdvertisements();
        loadWeather();
        initializeTrainSimulation();
    }

    private void loadWeather() {
        System.out.println("Loading weather data...");
        WeatherController weatherController = new WeatherController();
        weatherController.fetchTimeAndWeather(timeWeatherLabel, temperatureLabel, addTemperatureLabel, windSpeedLabel, weatherService, weatherParser);
    }

    private void setupDummyData() {
        trainDestinationLabel.setText("TUSCANY");
        trainTimeLabel.setText("1 min");
        trainNextStopLabel.setText("69 ST");
        trainNextTimeLabel.setText("1 min");
    }

    private void loadNews() {
        System.out.println("Loading news data...");
        NewsController newsController = new NewsController();
        newsController.fetchAndDisplayNews(newsTimeline, newsService, newsParser, newsLabel, newsKeyword, newsScrollPane);
    }

    private void insertSampleAdvertisements() {
        int adId1 = advertisementService.insertAdvertisement("Ad 1", "2024-01-01", "2024-12-31", "2024-01-01");
        if (adId1 != -1) {
            advertisementService.insertMedia(adId1, "/com/example/demo/images/advertisement_1.jpg", "JPEG");
        }
    }

    private void loadAdvertisements() {
        adMediaPaths = advertisementService.getMediaForAdvertisement(1);
        if (adMediaPaths != null && !adMediaPaths.isEmpty()) {
            displayNextAdvertisement();
        } else {
            System.out.println("No advertisements found in the database.");
        }
    }

    private void displayNextAdvertisement() {
        if (adMediaPaths != null && !adMediaPaths.isEmpty()) {
            if (currentAdIndex >= adMediaPaths.size()) {
                currentAdIndex = 0;
            }

            String mediaPath = adMediaPaths.get(currentAdIndex);
            System.out.println("Media Path is: " + mediaPath);
            // Draw the advertisement image directly on the canvas
            Image adImage = new Image(getClass().getResourceAsStream(mediaPath));
            gc.drawImage(adImage, 0, 0, mapCanvas.getWidth(), mapCanvas.getHeight());

            currentAdIndex++;

            // Schedule the next advertisement change
            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(10), event -> {
                // After 10 seconds, switch to the map for 5 seconds
                displayMapImage();
                // Schedule to switch back to the next advertisement after 5 seconds
                new Timeline(new KeyFrame(Duration.seconds(5), event2 -> displayNextAdvertisement())).play();
            }));
            timeline.setCycleCount(1);
            timeline.play();
        }
    }

    private void displayMapImage() {
        // Clear the canvas
        gc.clearRect(0, 0, mapCanvas.getWidth(), mapCanvas.getHeight());

        // Draw the subway lines
        drawSubwayLines();

        // Draw the trains on the map
        drawTrains();
    }

    private void drawSubwayLines() {
        // Assuming we have functions getRedLineCoordinates(), getGreenLineCoordinates(), and getBlueLineCoordinates() that return lists of coordinates for each line
        List<double[]> redLineCoordinates = CSVParser.parseCoordinates("src/main/resources/com/example/demo/data/map/Red.csv");
        List<double[]> greenLineCoordinates = CSVParser.parseCoordinates("src/main/resources/com/example/demo/data/map/Green.csv");
        List<double[]> blueLineCoordinates = CSVParser.parseCoordinates("src/main/resources/com/example/demo/data/map/Blue.csv");

        // Draw red line
        gc.setStroke(Color.RED);
        gc.setLineWidth(2);
        drawLine(redLineCoordinates);

        // Draw green line
        gc.setStroke(Color.GREEN);
        gc.setLineWidth(2);
        drawLine(greenLineCoordinates);

        // Draw blue line
        gc.setStroke(Color.BLUE);
        gc.setLineWidth(2);
        drawLine(blueLineCoordinates);
    }

    private void drawLine(List<double[]> coordinates) {
        if (coordinates.isEmpty()) return;
        double[] start = scaleCoordinates(coordinates.get(0));
        for (int i = 1; i < coordinates.size(); i++) {
            double[] end = scaleCoordinates(coordinates.get(i));
            gc.strokeLine(start[0], start[1], end[0], end[1]);
            start = end;
        }
    }

    private double[] scaleCoordinates(double[] coord) {
        double scaleX = mapCanvas.getWidth() / 1149.6964416503906; // Assuming 1149.6964416503906 is the max X coordinate
        double scaleY = mapCanvas.getHeight() / 669.8231658935547; // Assuming 669.8231658935547 is the max Y coordinate
        return new double[]{coord[0] * scaleX, coord[1] * scaleY};
    }

    private void drawTrains() {
        for (Train train : trains) {
            double[] scaledCoords = scaleCoordinates(new double[]{train.getX(), train.getY()});
            gc.setFill(getColorForLine(train.getLine()));
            double x = scaledCoords[0];
            double y = scaledCoords[1];
            gc.fillOval(x - 5, y - 5, 10, 10); // Draw the train as a circle
            gc.setFill(Color.BLACK);
            gc.fillText(train.getId(), x + 5, y - 5); // Draw the train ID next to the circle
        }
    }

    private Color getColorForLine(String line) {
        switch (line) {
            case "R":
                return Color.RED;
            case "G":
                return Color.GREEN;
            case "B":
                return Color.BLUE;
            default:
                return Color.BLACK;
        }
    }

    public void setDefaultNewKeyWord(String defaultNewsKeyWord) {
        this.newsKeyword = defaultNewsKeyWord;
    }

    private void initializeTrainSimulation() {

        if (inputFile == null || inputFile.trim().isEmpty()) {
            inputFile = "src/main/resources/com/example/demo/data/simulator/subway.csv";
        }
        System.out.println("Initializing train simulation...");
        stations = CSVParser.parseStations(inputFile);
        List<double[]> redCoordinates = CSVParser.parseCoordinates("src/main/resources/com/example/demo/data/map/Red.csv");
        List<double[]> greenCoordinates = CSVParser.parseCoordinates("src/main/resources/com/example/demo/data/map/Green.csv");
        List<double[]> blueCoordinates = CSVParser.parseCoordinates("src/main/resources/com/example/demo/data/map/Blue.csv");

        trains = TrainInitializer.initializeTrains(redCoordinates, greenCoordinates, blueCoordinates);

        // Timeline for simulating train movements every 10 seconds
        trainSimulationTimeline = new Timeline(new KeyFrame(Duration.seconds(10), event -> simulateTrainMovements()));
        trainSimulationTimeline.setCycleCount(Timeline.INDEFINITE);
        trainSimulationTimeline.play();

        // Timeline for updating the UI every 10 seconds, cycling through each train
        uiUpdateTimeline = new Timeline(new KeyFrame(Duration.seconds(10), new EventHandler<ActionEvent>() {
            private int currentTrainIndex = 0;

            @Override
            public void handle(ActionEvent event) {
                if (trains.isEmpty()) return;

                // Get the current train and update the UI
                Train currentTrain = trains.get(currentTrainIndex);
                updateUIForTrain(currentTrain);

                // Move to the next train, cycling back to the first train if necessary
                currentTrainIndex = (currentTrainIndex + 1) % trains.size();
            }
        }));
        uiUpdateTimeline.setCycleCount(Timeline.INDEFINITE);
        uiUpdateTimeline.play();
    }

    private void simulateTrainMovements() {
        for (Train train : trains) {
            train.move(1, getStationsByLine(stations, train.getLine()));
        }
        OutputTrainsPosition.printTrainPositions(trains, stations, outputFolder);
    }

    private void updateUIForTrain(Train train) {
        updateTrainInfoUI(train);
        updateStationInfoUI(train);
        TrainAnnouncement.announceNextStation(train, getStationsByLine(stations, train.getLine()));
    }

    private List<Station> getStationsByLine(List<Station> stations, String line) {
        List<Station> lineStations = new ArrayList<>();
        for (Station station : stations) {
            if (station.getLine().equals(line)) {
                lineStations.add(station);
            }
        }
        return lineStations;
    }

    private void updateTrainInfoUI(Train train) {
        List<Station> lineStations = getStationsByLine(stations, train.getLine());
        Station pastStation = train.getPastStation(lineStations);
        Station currentStation = train.getCurrentStation(lineStations);
        List<Station> futureStations = train.getFutureStations(lineStations, 4);

        trainDestinationLabel.setText(pastStation.getStationName());
        trainTimeLabel.setText("1 min");
        trainNextStopLabel.setText(futureStations.get(0).getStationName());
        trainNextTimeLabel.setText("1 min");
    }

    private void updateStationInfoUI(Train train) {
        List<Station> lineStations = getStationsByLine(stations, train.getLine());
        Station pastStation = train.getPastStation(lineStations);
        Station currentStation = train.getCurrentStation(lineStations);
        List<Station> futureStations = train.getFutureStations(lineStations, 4);

        trainIDLabel.setText("Train ID: " + train.getId());
        currentStationLabel.setText("Current: " + (currentStation != null ? currentStation.getStationName() : "N/A"));
        nextStationLabel.setText("Next: " + (futureStations.size() > 0 ? futureStations.get(0).getStationName() : "N/A"));
        trainDirectionLabel.setText("Direction: " + train.getDirection());

        prevStation.setText(pastStation != null ? pastStation.getStationName() : "N/A");
        nextStation1.setText(futureStations.size() > 0 ? futureStations.get(0).getStationName() : "N/A");
        nextStation2.setText(futureStations.size() > 1 ? futureStations.get(1).getStationName() : "N/A");
        nextStation3.setText(futureStations.size() > 2 ? futureStations.get(2).getStationName() : "N/A");
        nextStation4.setText(futureStations.size() > 3 ? futureStations.get(3).getStationName() : "N/A");
    }

    public String getNewsKeyword() {
        return newsKeyword;
    }

    public void setNewsKeyword(String newsKeyword) {
        this.newsKeyword = newsKeyword;
    }

    public String getInputFile() {
        return inputFile;
    }

    public void setInputFile(String inputFile) {
        this.inputFile = inputFile;
    }

    public String getOutputFolder() {
        return outputFolder;
    }

    public void setOutputFolder(String outputFolder) {
        this.outputFolder = outputFolder;
    }
}
