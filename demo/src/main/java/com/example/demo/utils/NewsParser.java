package com.example.demo.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for parsing news data.
 */
public class NewsParser {

    /**
     * Extracts news articles from the provided news data.
     *
     * @param newsData the news data as a string
     * @return a list of news articles
     */
    public List<String> extractNewsArticles(String newsData) {
        List<String> articles = new ArrayList<>();
        String[] items = newsData.split("\"articles\":\\[")[1].split("\\],")[0].split("\\},\\{");

        for (String item : items) {
            String title = extractJsonValue(item, "title");
            String description = extractJsonValue(item, "description");
            String url = extractJsonValue(item, "url");

            if (title.isEmpty() || description.isEmpty() || url.isEmpty() || title.equals("[Removed]") || description.equals("[Removed]") || url.equals("[Removed]")) {
                continue;
            }

            articles.add(title);
        }

        return articles;
    }

    /**
     * Extracts the value for a given key from a JSON string.
     *
     * @param json the JSON string
     * @param key  the key to extract the value for
     * @return the extracted value
     */
    public String extractJsonValue(String json, String key) {
        String[] parts = json.split("\"" + key + "\":\"");
        if (parts.length > 1) {
            return parts[1].split("\",")[0];
        }
        return "";
    }
}
