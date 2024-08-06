package com.example.demo.services;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class NewsServiceTest {

    @Test
    public void testFetchNewsData() throws Exception {
        NewsService newsService = new NewsService();
        String newsKeyword = "technology";

        // Fetch news data from the actual API
        String response = newsService.fetchNewsData(newsKeyword);

        // Check if the response contains expected data
        assertTrue(response.contains("\"status\":\"ok\""));
        assertTrue(response.contains("\"totalResults\""));
        assertTrue(response.contains("\"articles\""));
    }
}
