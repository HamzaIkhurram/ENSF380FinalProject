package com.example.demo.controllers;

import com.example.demo.services.NewsService;
import com.example.demo.utils.NewsParser;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.util.Duration;

import java.util.List;

/**
 * Controller for handling news-related operations.
 */
public class NewsController {

    private List<String> articles;

    /**
     * Fetches and displays news based on the provided keyword.
     * Sets up a timeline to periodically refresh the news.
     *
     * @param newsTimeline  the timeline to schedule periodic news fetching
     * @param newsService   the service used to fetch news data
     * @param newsParser    the parser used to extract news articles from the fetched data
     * @param newsLabel     the label to display the news
     * @param newsKeyword   the keyword to filter news articles
     * @param newsScrollPane the scroll pane containing the news label
     */
    public void fetchAndDisplayNews(Timeline newsTimeline, NewsService newsService, NewsParser newsParser, Label newsLabel, String newsKeyword, ScrollPane newsScrollPane) {

        // Fetch and display news immediately
        try {
            String newsData = newsService.fetchNewsData(newsKeyword);
            this.articles = newsParser.extractNewsArticles(newsData);
            displayNews(this.articles, newsLabel, newsScrollPane);
        } catch (Exception e) {
            newsLabel.setText("Error fetching news data");
            e.printStackTrace();
        }

        // Set up the timeline to fetch and display news every 20 minutes
        newsTimeline = new Timeline(
                new KeyFrame(Duration.minutes(20), event -> {
                    try {
                        String newsData = newsService.fetchNewsData(newsKeyword);
                        this.articles = newsParser.extractNewsArticles(newsData);
                        displayNews(this.articles, newsLabel, newsScrollPane);
                    } catch (Exception e) {
                        newsLabel.setText("Error fetching news data");
                        e.printStackTrace();
                    }
                })
        );
        newsTimeline.setCycleCount(Timeline.INDEFINITE);
        newsTimeline.play();
    }

    /**
     * Displays the fetched news articles one by one with a delay between each.
     *
     * @param articles      the list of news articles to display
     * @param newsLabel     the label to display the news
     * @param newsScrollPane the scroll pane containing the news label
     */
    private void displayNews(List<String> articles, Label newsLabel, ScrollPane newsScrollPane) {
        if (articles == null || articles.isEmpty()) {
            newsLabel.setText("No news articles found");
            newsLabel.setWrapText(true);
            newsScrollPane.setContent(newsLabel);
            return;
        }

        Timeline articleTimeline = new Timeline();
        articleTimeline.setCycleCount(articles.size());

        for (int i = 0; i < articles.size(); i++) {
            final int index = i;
            KeyFrame keyFrame = new KeyFrame(Duration.seconds(7 * (i + 1)), event -> {
                newsLabel.setText(articles.get(index));
                newsLabel.setWrapText(true);
                newsScrollPane.setContent(newsLabel);
            });
            articleTimeline.getKeyFrames().add(keyFrame);
        }

        articleTimeline.play();
    }
}
