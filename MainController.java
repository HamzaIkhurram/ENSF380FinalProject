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

    // FXML elements linked with the UI components
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

    // Services and utilities used by the controller
    private final NewsService newsService = new NewsService();
    private final NewsParser newsParser = new NewsParser();
    private final WeatherService weatherService = new WeatherService();
    private final WeatherParser weatherParser = new WeatherParser();
    private final AdvertisementService advertisementService = new AdvertisementService();

    // Timelines for train simulation and UI updates
    private Timeline trainSimulationTimeline;
    private Timeline uiUpdateTimeline;
    private List<Train> trains;
    private List<Station> stations;
    private Image mapImage;

    private Timeline newsTimeline;
    private String newsKeyword;
    private String inputFile;
    private String outputFolder;
    private TextToSpeech textToSpeech = TextToSpeech.getInstance();

    @FXML
    public void initialize() {
        // Initialize graphics context for the canvas
        gc = mapCanvas.getGraphicsContext2D();
        loadNews();
        insertSampleAdvertisements();
        loadAdvertisements();
        loadWeather();
        initializeTrainSimulation();
    }

    // Load and display weather data
    void loadWeather() {
        System.out.println("Loading weather data...");
        WeatherController weatherController = new WeatherController();
        weatherController.fetchTimeAndWeather(timeWeatherLabel, temperatureLabel, addTemperatureLabel, windSpeedLabel);
    }

    // Set up dummy data for demonstration purposes
    private void setupDummyData() {
        trainDestinationLabel.setText("TUSCANY");
        trainTimeLabel.setText("1 min");
        trainNextStopLabel.setText("69 ST");
        trainNextTimeLabel.setText("1 min");
    }

    // Load and display news data
    void loadNews() {
        System.out.println("Loading news data...");
        NewsController newsController = new NewsController();
        newsController.fetchAndDisplayNews(newsTimeline, newsService, newsParser, newsLabel, newsKeyword, newsScrollPane);
    }

    // Insert sample advertisements into the database
    void insertSampleAdvertisements() {
        int adId1 = advertisementService.insertAdvertisement("Ad 1", "2024-01-01", "2024-12-31", "2024-01-01");
        if (adId1 != -1) {
            advertisementService.insertMedia(adId1, "/com/example/demo/images/advertisement_1.jpg", "JPEG");
        }
    }

    // Load and display advertisements
    void loadAdvertisements() {
        adMediaPaths = advertisementService.getMediaForAdvertisement(1);
        if (adMediaPaths != null && !adMediaPaths.isEmpty()) {
            displayNextAdvertisement();
        } else {
            System.out.println("No advertisements found in the database.");
        }
    }

    // Display the next advertisement
    void displayNextAdvertisement() {
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

    // Display the map image on the canvas
    void displayMapImage() {
        // Clear the canvas
        gc.clearRect(0, 0, mapCanvas.getWidth(), mapCanvas.getHeight());

        // Draw the subway lines
        drawSubwayLines();

        // Draw the trains on the map
        drawTrains();
    }

    // Draw the subway lines on the map
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

    // Draw a line given a list of coordinates
    private void drawLine(List<double[]> coordinates) {
        if (coordinates.isEmpty()) return;
        double[] start = scaleCoordinates(coordinates.get(0));
        for (int i = 1; i < coordinates.size(); i++) {
            double[] end = scaleCoordinates(coordinates.get(i));
            gc.strokeLine(start[0], start[1], end[0], end[1]);
            start = end;
        }
    }

    // Scale coordinates to fit the canvas size
    private double[] scaleCoordinates(double[] coord) {
        double scaleX = mapCanvas.getWidth() / 1149.6964416503906; // Assuming 1149.6964416503906 is the max X coordinate
        double scaleY = mapCanvas.getHeight() / 669.8231658935547; // Assuming 669.8231658935547 is the max Y coordinate
        return new double[]{coord[0] * scaleX, coord[1] * scaleY};
    }

    // Draw trains on the map
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

    // Get color for a subway line
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

    // Initialize train simulation
    void initializeTrainSimulation() {

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

    // Simulate train movements
    private void simulateTrainMovements() {
        for (Train train : trains) {
            train.move(1, getStationsByLine(stations, train.getLine()));
        }
        OutputTrainsPosition.printTrainPositions(trains, stations, outputFolder);
    }

    // Update UI for a specific train
    void updateUIForTrain(Train train) {
        updateTrainInfoUI(train);
        updateStationInfoUI(train);
        TrainAnnouncement.announceNextStation(train, getStationsByLine(stations, train.getLine()), TextToSpeech.getInstance());
    }

    // Get stations for a specific line
    private List<Station> getStationsByLine(List<Station> stations, String line) {
        List<Station> lineStations = new ArrayList<>();
        for (Station station : stations) {
            if (station.getLine().equals(line)) {
                lineStations.add(station);
            }
        }
        return lineStations;
    }

    // Update train information UI
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

    // Update station information UI
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

    public Label getTemperatureLabel() {
        return temperatureLabel;
    }

    public Label getAddTemperatureLabel() {
        return addTemperatureLabel;
    }

    public Label getWindSpeedLabel() {
        return windSpeedLabel;
    }

    public VBox getCurrentStationInfo() {
        return currentStationInfo;
    }

    public HBox getTrainStationInfo() {
        return trainStationInfo;
    }

    public Label getCurrentStationLabel() {
        return currentStationLabel;
    }

    public Label getTrainIDLabel() {
        return trainIDLabel;
    }

    public Label getNextStationLabel() {
        return nextStationLabel;
    }

    public Label getTrainDirectionLabel() {
        return trainDirectionLabel;
    }

    public HBox getNextStationsInfo() {
        return nextStationsInfo;
    }

    public Label getPrevStation() {
        return prevStation;
    }

    public Label getNextStation1() {
        return nextStation1;
    }

    public Label getNextStation2() {
        return nextStation2;
    }

    public Label getNextStation3() {
        return nextStation3;
    }

    public Label getNextStation4() {
        return nextStation4;
    }

    public int getCurrentAdIndex() {
        return currentAdIndex;
    }

    public List<String> getAdMediaPaths() {
        return adMediaPaths;
    }

    public HBox getTopSection() {
        return topSection;
    }

    public VBox getAdSection() {
        return adSection;
    }

    public Label getAdLabel() {
        return adLabel;
    }

    public VBox getWeatherSection() {
        return weatherSection;
    }

    public Label getTimeWeatherLabel() {
        return timeWeatherLabel;
    }

    public Label getTrainInfoLabel() {
        return trainInfoLabel;
    }

    public VBox getBottomSection() {
        return bottomSection;
    }

    public HBox getTrainInfoSection() {
        return trainInfoSection;
    }

    public ScrollPane getNewsScrollPane() {
        return newsScrollPane;
    }

    public HBox getNewsSection() {
        return newsSection;
    }

    public Label getNewsLabel() {
        return newsLabel;
    }

    public HBox getTrainInfoLeft() {
        return trainInfoLeft;
    }

    public Label getTrainDestinationLabel() {
        return trainDestinationLabel;
    }

    public Label getTrainTimeLabel() {
        return trainTimeLabel;
    }

    public HBox getTrainInfoRight() {
        return trainInfoRight;
    }

    public Label getTrainNextStopLabel() {
        return trainNextStopLabel;
    }

    public Label getTrainNextTimeLabel() {
        return trainNextTimeLabel;
    }

    public Canvas getMapCanvas() {
        return mapCanvas;
    }

    public GraphicsContext getGc() {
        return gc;
    }

    public NewsService getNewsService() {
        return newsService;
    }

    public NewsParser getNewsParser() {
        return newsParser;
    }

    public WeatherService getWeatherService() {
        return weatherService;
    }

    public WeatherParser getWeatherParser() {
        return weatherParser;
    }

    public AdvertisementService getAdvertisementService() {
        return advertisementService;
    }

    public Timeline getTrainSimulationTimeline() {
        return trainSimulationTimeline;
    }

    public Timeline getUiUpdateTimeline() {
        return uiUpdateTimeline;
    }

    public List<Train> getTrains() {
        return trains;
    }

    public List<Station> getStations() {
        return stations;
    }

    public Image getMapImage() {
        return mapImage;
    }

    public Timeline getNewsTimeline() {
        return newsTimeline;
    }

    public void setupCanvas(Canvas canvas) {
        this.mapCanvas = canvas;
    }

    public void setTemperatureLabel(Label temperatureLabel) {
        this.temperatureLabel = temperatureLabel;
    }

    public void setAddTemperatureLabel(Label addTemperatureLabel) {
        this.addTemperatureLabel = addTemperatureLabel;
    }

    public void setWindSpeedLabel(Label windSpeedLabel) {
        this.windSpeedLabel = windSpeedLabel;
    }

    public void setCurrentStationInfo(VBox currentStationInfo) {
        this.currentStationInfo = currentStationInfo;
    }

    public void setTrainStationInfo(HBox trainStationInfo) {
        this.trainStationInfo = trainStationInfo;
    }

    public void setCurrentStationLabel(Label currentStationLabel) {
        this.currentStationLabel = currentStationLabel;
    }

    public void setTrainIDLabel(Label trainIDLabel) {
        this.trainIDLabel = trainIDLabel;
    }

    public void setNextStationLabel(Label nextStationLabel) {
        this.nextStationLabel = nextStationLabel;
    }

    public void setTrainDirectionLabel(Label trainDirectionLabel) {
        this.trainDirectionLabel = trainDirectionLabel;
    }

    public void setNextStationsInfo(HBox nextStationsInfo) {
        this.nextStationsInfo = nextStationsInfo;
    }

    public void setPrevStation(Label prevStation) {
        this.prevStation = prevStation;
    }

    public void setNextStation1(Label nextStation1) {
        this.nextStation1 = nextStation1;
    }

    public void setNextStation2(Label nextStation2) {
        this.nextStation2 = nextStation2;
    }

    public void setNextStation3(Label nextStation3) {
        this.nextStation3 = nextStation3;
    }

    public void setNextStation4(Label nextStation4) {
        this.nextStation4 = nextStation4;
    }

    public void setCurrentAdIndex(int currentAdIndex) {
        this.currentAdIndex = currentAdIndex;
    }

    public void setAdMediaPaths(List<String> adMediaPaths) {
        this.adMediaPaths = adMediaPaths;
    }

    public void setTopSection(HBox topSection) {
        this.topSection = topSection;
    }

    public void setAdSection(VBox adSection) {
        this.adSection = adSection;
    }

    public void setAdLabel(Label adLabel) {
        this.adLabel = adLabel;
    }

    public void setWeatherSection(VBox weatherSection) {
        this.weatherSection = weatherSection;
    }

    public void setTimeWeatherLabel(Label timeWeatherLabel) {
        this.timeWeatherLabel = timeWeatherLabel;
    }

    public void setTrainInfoLabel(Label trainInfoLabel) {
        this.trainInfoLabel = trainInfoLabel;
    }

    public void setBottomSection(VBox bottomSection) {
        this.bottomSection = bottomSection;
    }

    public void setTrainInfoSection(HBox trainInfoSection) {
        this.trainInfoSection = trainInfoSection;
    }

    public void setNewsScrollPane(ScrollPane newsScrollPane) {
        this.newsScrollPane = newsScrollPane;
    }

    public void setNewsSection(HBox newsSection) {
        this.newsSection = newsSection;
    }

    public void setNewsLabel(Label newsLabel) {
        this.newsLabel = newsLabel;
    }

    public void setTrainInfoLeft(HBox trainInfoLeft) {
        this.trainInfoLeft = trainInfoLeft;
    }

    public void setTrainDestinationLabel(Label trainDestinationLabel) {
        this.trainDestinationLabel = trainDestinationLabel;
    }

    public void setTrainTimeLabel(Label trainTimeLabel) {
        this.trainTimeLabel = trainTimeLabel;
    }

    public void setTrainInfoRight(HBox trainInfoRight) {
        this.trainInfoRight = trainInfoRight;
    }

    public void setTrainNextStopLabel(Label trainNextStopLabel) {
        this.trainNextStopLabel = trainNextStopLabel;
    }

    public void setTrainNextTimeLabel(Label trainNextTimeLabel) {
        this.trainNextTimeLabel = trainNextTimeLabel;
    }

    public void setMapCanvas(Canvas mapCanvas) {
        this.mapCanvas = mapCanvas;
    }

    public void setGc(GraphicsContext gc) {
        this.gc = gc;
    }

    public void setTrainSimulationTimeline(Timeline trainSimulationTimeline) {
        this.trainSimulationTimeline = trainSimulationTimeline;
    }

    public void setUiUpdateTimeline(Timeline uiUpdateTimeline) {
        this.uiUpdateTimeline = uiUpdateTimeline;
    }

    public void setTrains(List<Train> trains) {
        this.trains = trains;
    }

    public void setStations(List<Station> stations) {
        this.stations = stations;
    }

    public void setMapImage(Image mapImage) {
        this.mapImage = mapImage;
    }

    public void setNewsTimeline(Timeline newsTimeline) {
        this.newsTimeline = newsTimeline;
    }
}

