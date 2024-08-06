package com.example.demo.services;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class WeatherServiceTest {

    @Test
    public void testFetchWeatherData() throws Exception {
        // Call the method to fetch weather data
        String weatherData = WeatherService.fetchWeatherData();

        // Check if the response is not null
        assertNotNull("Weather data should not be null", weatherData);

        // Optional: Print the weather data to the console (for debugging purposes)
        System.out.println(weatherData);
    }
}
