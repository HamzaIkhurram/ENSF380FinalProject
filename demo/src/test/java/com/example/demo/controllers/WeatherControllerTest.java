package com.example.demo.controllers;

import com.example.demo.WeatherService;
import com.example.demo.WeatherParser;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.control.Label;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class WeatherControllerTest {

    private WeatherController weatherController;
    private Label timeWeatherLabel;
    private Label temp;
    private Label addTemp;
    private Label windSpeed;

    static {
        // Initialize JavaFX
        new JFXPanel();
        Platform.setImplicitExit(false);
    }

    @Before
    public void setUp() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            try {
                weatherController = new WeatherController();
                timeWeatherLabel = new Label();
                temp = new Label();
                addTemp = new Label();
                windSpeed = new Label();
            } finally {
                latch.countDown();
            }
        });
        latch.await(5, TimeUnit.SECONDS);
    }

    @Test
    public void testFetchTimeAndWeatherSuccess() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            try {
                weatherController.fetchTimeAndWeather(timeWeatherLabel, temp, addTemp, windSpeed);
                // Wait for the weather data to be fetched and processed
                assertTrue(waitForText(temp, "Temperature: +"));
                assertFalse(temp.getText().isEmpty());
                assertFalse(addTemp.getText().isEmpty());
                assertFalse(windSpeed.getText().isEmpty());
                System.out.println("Done");
            } finally {
                latch.countDown();
            }
        });
        latch.await(30, TimeUnit.SECONDS);  // Increased the wait time for the actual API call.
    }

    @Test
    public void testFetchTimeAndWeatherError() throws Exception {
        // Simulate an error by providing an invalid URL
//        WeatherService.setWeatherUrl("https://invalid-url");
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            try {
                weatherController.fetchTimeAndWeather(timeWeatherLabel, temp, addTemp, windSpeed);
                // Wait for the error message to be displayed
                assertTrue(waitForText(timeWeatherLabel, "Error fetching weather data"));
            } finally {
                latch.countDown();
            }
        });
        latch.await(30, TimeUnit.SECONDS);  // Increased the wait time for the actual API call.
    }

    private boolean waitForText(Label label, String expectedText) {
        for (int i = 0; i < 100; i++) {
            if (label.getText().contains(expectedText)) {
                return true;
            }
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        return false;
    }
}
