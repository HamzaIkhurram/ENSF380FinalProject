package com.example.demo.services;

import com.example.demo.config.DatabaseHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Service class for handling advertisement-related operations.
 */
public class AdvertisementService implements AdvertisementInterface {

    /**
     * {@inheritDoc}
     */
    public int insertAdvertisement(String title, String startDate, String endDate, String createdAt) {
        String sql = "INSERT INTO advertisements(title, start_date, end_date, created_at) VALUES(?, ?, ?, ?)";
        int advertisementId = -1;

        try (Connection conn = DatabaseHelper.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, title);
            pstmt.setString(2, startDate);
            pstmt.setString(3, endDate);
            pstmt.setString(4, createdAt);
            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                advertisementId = rs.getInt(1);
            }
            System.out.println("Advertisement has been inserted with ID: " + advertisementId);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return advertisementId;
    }

    /**
     * {@inheritDoc}
     */
    public void insertMedia(int advertisementId, String filePath, String fileType) {
        String sql = "INSERT INTO media(advertisement_id, file_path, file_type) VALUES(?, ?, ?)";

        try (Connection conn = DatabaseHelper.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, advertisementId);
            pstmt.setString(2, filePath);
            pstmt.setString(3, fileType);
            pstmt.executeUpdate();
            System.out.println("Media has been inserted.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * {@inheritDoc}
     */
    public List<String> getMediaForAdvertisement(int advertisementId) {
        String sql = "SELECT file_path FROM media WHERE advertisement_id = ?";
        List<String> mediaPaths = new ArrayList<>();

        try (Connection conn = DatabaseHelper.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, advertisementId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                mediaPaths.add(rs.getString("file_path"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return mediaPaths;
    }
}
