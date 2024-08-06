package com.example.demo.utils;

import com.example.demo.services.NewsService;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class NewsParserTest {

    private NewsService newsService;
    private NewsParser newsParser;

    @Before
    public void setUp() {
        newsService = mock(NewsService.class);
        newsParser = new NewsParser();
    }

    @Test
    public void testExtractNewsArticles() throws Exception {
        // Mock the response from the NewsService
        String mockNewsData = "{\"articles\":[{\"title\":\"Article 1\",\"description\":\"Description 1\",\"url\":\"http://example.com/1\"},{\"title\":\"Article 2\",\"description\":\"Description 2\",\"url\":\"http://example.com/2\"}]}";
        when(newsService.fetchNewsData("technology")).thenReturn(mockNewsData);

        // Fetch news data using the mocked NewsService
        String newsData = newsService.fetchNewsData("technology");

        // Extract articles from the news data
        List<String> articles = newsParser.extractNewsArticles(newsData);

        // Check that the articles list is not empty
        assertFalse("The articles list should not be empty", articles.isEmpty());

        // Print articles for manual verification
        articles.forEach(System.out::println);

        // Check that the first article has a non-empty title
        assertFalse("The first article title should not be empty", articles.get(0).isEmpty());
    }

    @Test
    public void testExtractNewsArticlesWithEmptyData() {
        String emptyNewsData = "{\"articles\":[]}";

        List<String> articles = newsParser.extractNewsArticles(emptyNewsData);

        assertTrue("The articles list should be empty", articles.isEmpty());
    }

    @Test
    public void testExtractNewsArticlesWithInvalidData() {
        String invalidNewsData = "{}";

        try {
            newsParser.extractNewsArticles(invalidNewsData);
            fail("Expected an ArrayIndexOutOfBoundsException to be thrown");
        } catch (ArrayIndexOutOfBoundsException e) {
            // Expected exception
        }
    }
}
