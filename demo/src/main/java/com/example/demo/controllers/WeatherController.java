package com.example.demo.controllers;

import com.example.demo.WeatherService;
import com.example.demo.WeatherParser;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.util.Duration;

import java.util.List;

/**
 * Controller for handling weather-related operations.
 */
public class WeatherController {

    /**
     * Fetches and updates the time and weather data periodically.
     *
     * @param timeWeatherLabel the label to display the time and weather information
     * @param temp             the label to display the temperature
     * @param addTemp          the label to display additional temperature information
     * @param windSpeed        the label to display wind speed
     */
    public void fetchTimeAndWeather(Label timeWeatherLabel, Label temp, Label addTemp, Label windSpeed) {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(10), event -> {
                    try {
                        String weatherData = WeatherService.fetchWeatherData();
                        List<String> parsedWeather = WeatherParser.parseWeather(weatherData);

                        assert parsedWeather != null;
                        temp.setText("Temperature: " + parsedWeather.get(0));
                        addTemp.setText("Additional Temp: " + parsedWeather.get(1));
                        windSpeed.setText("Wind Speed: " + parsedWeather.get(2) + " Direction: " + parsedWeather.get(3));

//                        timeWeatherLabel.setText("Time: " + java.time.LocalTime.now() + "\n" + parsedWeather);
                    } catch (Exception e) {
                        timeWeatherLabel.setText("Error fetching weather data");
                        e.printStackTrace();
                    }
                })
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }
}
