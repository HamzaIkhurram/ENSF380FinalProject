package com.example.demo;
// WeatherService.java
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherService {

    private static final String WEATHER_URL = "https://wttr.in/Calgary,CA?format=%tC%t%w"; // Custom format

    public static String fetchWeatherData() throws Exception {
        URL url = new URL(WEATHER_URL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setInstanceFollowRedirects(true); // Follow redirects

        connection.setRequestMethod("GET");

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder result = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            result.append(line).append("\n");
        }
        reader.close();
        System.out.println(result.toString());
        return result.toString();
    }
}
