package com.example.demo.utils;

import java.io.File;

/**
 * Utility class for checking file paths.
 */
public class FilePathChecker {

    /**
     * Checks the file path and prints whether the file exists and its absolute path.
     */
    public void filePathChecker() {
        File file = new File("./Map.csv");
        System.out.println("File exists: " + file.exists());
        System.out.println("Absolute path: " + file.getAbsolutePath());
    }
}
