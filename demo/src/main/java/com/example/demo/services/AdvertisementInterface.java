package com.example.demo.services;

import java.util.List;

/**
 * Interface for advertisement-related operations.
 */
public interface AdvertisementInterface {

    /**
     * Inserts a new advertisement into the database.
     *
     * @param title     the title of the advertisement
     * @param startDate the start date of the advertisement
     * @param endDate   the end date of the advertisement
     * @param createdAt the creation date of the advertisement
     * @return the ID of the inserted advertisement
     */
    public int insertAdvertisement(String title, String startDate, String endDate, String createdAt);

    /**
     * Inserts media associated with an advertisement into the database.
     *
     * @param advertisementId the ID of the advertisement
     * @param filePath        the file path of the media
     * @param fileType        the file type of the media
     */
    public void insertMedia(int advertisementId, String filePath, String fileType);

    /**
     * Retrieves the media file paths for a given advertisement.
     *
     * @param advertisementId the ID of the advertisement
     * @return a list of media file paths associated with the advertisement
     */
    public List<String> getMediaForAdvertisement(int advertisementId);
}
