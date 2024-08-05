package com.example.demo.services;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

/**
 * Service class for handling news-related operations.
 */
public class NewsService {

    private final String apiKey = "da167ab91ac545208c95b169ad56b74d";

    /**
     * Fetches news data from the news API based on the provided keyword.
     *
     * @param newsKeyword the keyword to filter news articles
     * @return the news data as a string
     * @throws Exception if an error occurs while fetching the news data
     */
    public String fetchNewsData(String newsKeyword) throws Exception {

        // Get the current date
        LocalDate currentDate = LocalDate.now();

        // Format the date as "yyyy-MM-dd"
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = currentDate.format(formatter);

        String apiUrl = "https://newsapi.org/v2/everything?q=" + newsKeyword + "&from=2024-07-04&sortBy=publishedAt&apiKey=" + this.apiKey;

        // Open a connection to the URL
        URL url = new URL(apiUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        // Add request headers
        conn.setRequestProperty("User-Agent", "Mozilla/5.0");
        conn.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
        conn.setRequestProperty("Upgrade", "HTTP/2.0");

        // Check the response code and throw an exception if it's not 200 (OK)
        int responseCode = conn.getResponseCode();
        if (responseCode != HttpURLConnection.HTTP_OK) {
            throw new RuntimeException("HTTP GET Request Failed with Error code : " + responseCode);
        }

        // Read the response from the input stream
        Scanner scanner = new Scanner(new InputStreamReader(conn.getInputStream()));
        StringBuilder response = new StringBuilder();
        while (scanner.hasNext()) {
            response.append(scanner.nextLine());
        }
        scanner.close();

        return response.toString();
    }
}
