package com.example.demo.utils;

import com.example.demo.models.Station;
import com.example.demo.models.Train;

import java.util.List;

/**
 * Utility class for making train announcements.
 */
public class TrainAnnouncement {

    /**
     * Announces the next station for the given train.
     *
     * @param train    the train object
     * @param stations the list of stations
     */
    public static void announceNextStation(Train train, List<Station> stations) {

        TextToSpeech textToSpeech = new TextToSpeech();
        Station nextStation = train.getNextStation(stations);
        String announcement = "Next stop: " + nextStation.getStationName();
        if (nextStation.getCommonStations() != null && !nextStation.getCommonStations().isEmpty()) {
            announcement += ", you can change your train to line " + nextStation.getCommonStations();
        }
        System.out.println(announcement);
        textToSpeech.speak(announcement);
        textToSpeech.deallocate();
    }
}
