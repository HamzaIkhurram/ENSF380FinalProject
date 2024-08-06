package com.example.demo.controllers;

import com.example.demo.models.Train;
import com.example.demo.services.NewsService;
import com.example.demo.utils.NewsParser;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class MainControllerTest {

    private MainController mainController;

    @BeforeClass
    public static void initToolkit() {
        // Initialize JavaFX environment
        new JFXPanel();
    }

    @org.junit.Before
    public void setUp() throws Exception {
        // Initialize JavaFX components
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            mainController = new MainController();

            mainController.setTemperatureLabel(new Label());
            mainController.setAddTemperatureLabel(new Label());
            mainController.setWindSpeedLabel(new Label());
            mainController.setCurrentStationInfo(new VBox());
            mainController.setTrainStationInfo(new HBox());
            mainController.setCurrentStationLabel(new Label());
            mainController.setTrainIDLabel(new Label());
            mainController.setNextStationLabel(new Label());
            mainController.setTrainDirectionLabel(new Label());
            mainController.setNextStationsInfo(new HBox());
            mainController.setPrevStation(new Label());
            mainController.setNextStation1(new Label());
            mainController.setNextStation2(new Label());
            mainController.setNextStation3(new Label());
            mainController.setNextStation4(new Label());
            mainController.setTopSection(new HBox());
            mainController.setAdSection(new VBox());
            mainController.setAdLabel(new Label());
            mainController.setWeatherSection(new VBox());
            mainController.setTimeWeatherLabel(new Label());
            mainController.setTrainInfoLabel(new Label());
            mainController.setBottomSection(new VBox());
            mainController.setTrainInfoSection(new HBox());
            mainController.setNewsScrollPane(new ScrollPane());
            mainController.setNewsSection(new HBox());
            mainController.setNewsLabel(new Label());
            mainController.setTrainInfoLeft(new HBox());
            mainController.setTrainDestinationLabel(new Label());
            mainController.setTrainTimeLabel(new Label());
            mainController.setTrainInfoRight(new HBox());
            mainController.setTrainNextStopLabel(new Label());
            mainController.setTrainNextTimeLabel(new Label());
            mainController.setMapCanvas(new Canvas());
            mainController.setGc(mainController.getMapCanvas().getGraphicsContext2D());

            latch.countDown();
        });
        latch.await(5, TimeUnit.SECONDS);
    }

    @Test
    public void testLoadNews() {
        Platform.runLater(() -> {
            mainController.loadNews();
            assertNotNull(mainController.getNewsLabel().getText());
        });
    }

    @Test
    public void testLoadWeather() {
        Platform.runLater(() -> {
            mainController.loadWeather();
            assertNotNull(mainController.getTemperatureLabel().getText());
            assertNotNull(mainController.getAddTemperatureLabel().getText());
            assertNotNull(mainController.getWindSpeedLabel().getText());
        });
    }

    @Test
    public void testInsertSampleAdvertisements() {
        Platform.runLater(() -> {
            mainController.insertSampleAdvertisements();
            assertFalse(mainController.getAdMediaPaths().isEmpty());
        });
    }

    @Test
    public void testInitializeTrainSimulation() {
        Platform.runLater(() -> {
            mainController.initializeTrainSimulation();
            assertNotNull(mainController.getTrains());
            assertNotNull(mainController.getStations());
            assertFalse(mainController.getTrains().isEmpty());
            assertFalse(mainController.getStations().isEmpty());
        });
    }

    @Test
    public void testDisplayNextAdvertisement() {
        Platform.runLater(() -> {
            mainController.insertSampleAdvertisements();
            mainController.loadAdvertisements();
            mainController.displayNextAdvertisement();
            assertTrue(mainController.getGc().getCanvas().isVisible());
        });
    }

    @Test
    public void testDrawSubwayLines() {
        Platform.runLater(() -> {
            mainController.initializeTrainSimulation();
            mainController.displayMapImage();
            assertNotNull(mainController.getGc());
        });
    }

    @Test
    public void testDrawTrains() {
        Platform.runLater(() -> {
            mainController.initializeTrainSimulation();
            mainController.displayMapImage();
            assertNotNull(mainController.getTrains());
            assertFalse(mainController.getTrains().isEmpty());
        });
    }

    @Test
    public void testUpdateUIForTrain() {
        Platform.runLater(() -> {
            mainController.initializeTrainSimulation();
            Train testTrain = mainController.getTrains().get(0);
            mainController.updateUIForTrain(testTrain);
            assertNotNull(mainController.getTrainIDLabel().getText());
            assertNotNull(mainController.getCurrentStationLabel().getText());
            assertNotNull(mainController.getNextStationLabel().getText());
            assertNotNull(mainController.getTrainDirectionLabel().getText());
        });
    }
}
