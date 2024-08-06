package com.example.demo.controllers;

import com.example.demo.services.NewsService;
import com.example.demo.utils.NewsParser;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class NewsControllerTest {

    private NewsController newsController;
    private NewsService newsService;
    private NewsParser newsParser;
    private Label newsLabel;
    private ScrollPane newsScrollPane;
    private Timeline newsTimeline;

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
                newsController = new NewsController();
                newsService = new NewsService();
                newsParser = new NewsParser();
                newsLabel = new Label();
                newsScrollPane = new ScrollPane(newsLabel);
                newsTimeline = new Timeline();
            } finally {
                latch.countDown();
            }
        });
        latch.await(5, TimeUnit.SECONDS);
    }

    @Test
    public void testFetchAndDisplayNewsSuccess() throws Exception {
        String newsKeyword = "technology";

        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            try {
                newsController.fetchAndDisplayNews(newsTimeline, newsService, newsParser, newsLabel, newsKeyword, newsScrollPane);
                // Since the API call is real, we need to assert that some data has been fetched.
                assertTrue(waitForText(newsLabel));
                assertFalse(newsLabel.getText().isEmpty());
            } finally {
                latch.countDown();
            }
        });
        latch.await(15, TimeUnit.SECONDS);  // Increased the wait time for the actual API call.
    }

    @Test
    public void testFetchAndDisplayNewsError() throws Exception {
        String newsKeyword = ""; // Intentionally invalid keyword

        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            try {
                newsController.fetchAndDisplayNews(newsTimeline, newsService, newsParser, newsLabel, newsKeyword, newsScrollPane);
                // Since the API call is real, we need to assert that the error message is shown.
                assertTrue(waitForText(newsLabel));
                assertEquals("Error fetching news data", newsLabel.getText());
            } finally {
                latch.countDown();
            }
        });
        latch.await(15, TimeUnit.SECONDS);
    }

    @Test
    public void testDisplayNewsWithNoArticles() throws InterruptedException {
        List<String> articles = List.of();

        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            try {
                newsController.displayNews(articles, newsLabel, newsScrollPane);
                assertEquals("No news articles found", newsLabel.getText());
            } finally {
                latch.countDown();
            }
        });
        latch.await(5, TimeUnit.SECONDS);
    }

    @Test
    public void testDisplayNewsWithArticles() throws InterruptedException {
        List<String> articles = List.of("Article 1", "Article 2");

        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            try {
                newsController.displayNews(articles, newsLabel, newsScrollPane);
                assertTrue(waitForText(newsLabel));
                assertEquals("Article 1", newsLabel.getText());
            } finally {
                latch.countDown();
            }
        });
        latch.await(15, TimeUnit.SECONDS);
    }

    private boolean waitForText(Label label) {
        for (int i = 0; i < 100; i++) {
            if (!label.getText().isEmpty()) {
                return true;
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        return false;
    }
}
