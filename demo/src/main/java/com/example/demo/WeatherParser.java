package com.example.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utility class for parsing weather data from HTML content.
 */
public class WeatherParser {

    /**
     * Parses weather data from the provided HTML content.
     *
     * @param htmlContent the HTML content containing weather data
     * @return a list of parsed weather data (temperature, additional temperature, wind direction, wind speed)
     */
    public static List<String> parseWeather(String htmlContent) {
        Pattern pattern = Pattern.compile("(\\+\\d+°C)(C\\+\\d+°C)(↗|↘|↑|↓|↙|↖|↘|↗)?(\\d+km/h)");
        Matcher matcher = pattern.matcher(htmlContent.trim());

        List<String> weatherData = new ArrayList<>();

        if (matcher.find()) {
            String temperature = matcher.group(1);
            weatherData.add(temperature);
            String additionalTemperature = matcher.group(2);
            weatherData.add(additionalTemperature);
            String windDirection = matcher.group(3) != null ? matcher.group(3) : "";

            String windSpeed = matcher.group(4);
            weatherData.add(windSpeed);
            weatherData.add(windDirection);
            return weatherData;
        }
        return null;
    }
}
