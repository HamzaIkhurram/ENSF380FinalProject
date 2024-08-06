package com.example.demo.services;

import com.example.demo.config.DatabaseHelper;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.Statement;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class AdvertisementServiceTest {

    private AdvertisementService advertisementService;

    @Before
    public void setUp() throws Exception {
        advertisementService = new AdvertisementService();

        // Create tables in the test database
        DatabaseHelper.createNewTables();

        // Clear any existing data
        try (Connection connection = DatabaseHelper.connect();
             Statement statement = connection.createStatement()) {
            statement.execute("DELETE FROM media");
            statement.execute("DELETE FROM advertisements");
        }
    }

    @Test
    public void testInsertAdvertisement() {
        String title = "Sample Ad";
        String startDate = "2024-01-01";
        String endDate = "2024-01-31";
        String createdAt = "2023-12-01";

        int advertisementId = advertisementService.insertAdvertisement(title, startDate, endDate, createdAt);

        assertEquals(1, advertisementId);
    }

    @Test
    public void testInsertMedia() {
        String title = "Sample Ad";
        String startDate = "2024-01-01";
        String endDate = "2024-01-31";
        String createdAt = "2023-12-01";

        int advertisementId = advertisementService.insertAdvertisement(title, startDate, endDate, createdAt);
        assertEquals(1, advertisementId);

        String filePath = "src/test/resources/images/advertisement_1.jpg";
        String fileType = "image/jpeg";

        advertisementService.insertMedia(advertisementId, filePath, fileType);

        // Retrieve media to check if it was inserted correctly
        List<String> mediaPaths = advertisementService.getMediaForAdvertisement(advertisementId);
        assertEquals(1, mediaPaths.size());
        assertEquals(filePath, mediaPaths.get(0));
    }

    @Test
    public void testGetMediaForAdvertisement() {
        String title = "Sample Ad";
        String startDate = "2024-01-01";
        String endDate = "2024-01-31";
        String createdAt = "2023-12-01";

        int advertisementId = advertisementService.insertAdvertisement(title, startDate, endDate, createdAt);
        assertEquals(1, advertisementId);

        String filePath1 = "src/test/resources/images/advertisement_1.jpg";
        String fileType1 = "image/jpeg";
        String filePath2 = "src/test/resources/images/advertisement_2.jpg";
        String fileType2 = "image/png";

        advertisementService.insertMedia(advertisementId, filePath1, fileType1);
        advertisementService.insertMedia(advertisementId, filePath2, fileType2);

        // Retrieve media to check if it was inserted correctly
        List<String> mediaPaths = advertisementService.getMediaForAdvertisement(advertisementId);
        assertEquals(2, mediaPaths.size());
        assertEquals(filePath1, mediaPaths.get(0));
        assertEquals(filePath2, mediaPaths.get(1));
    }
}
