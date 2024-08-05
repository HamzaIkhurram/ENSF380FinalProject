package com.example.demo;

import com.example.demo.config.DatabaseHelper;
import com.example.demo.controllers.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Main class for the Subway Screen Project application.
 * Initializes the application, loads the main FXML view, and starts the JavaFX application.
 */
public class Main extends Application {

    private static String defaultNewsKeyWord = "tesla";
    private static String inputFilePath = null;
    private static String outputFolderPath = null;

    /**
     * Starts the JavaFX application.
     *
     * @param primaryStage the primary stage for this application
     * @throws Exception if there is an error during application startup
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Initialize database and create table
        DatabaseHelper.createNewTables();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo/views/main.fxml"));
        BorderPane root = loader.load();
        MainController controller = loader.getController();
        controller.setDefaultNewKeyWord(defaultNewsKeyWord);
        controller.setInputFile(inputFilePath);
        controller.setOutputFolder(outputFolderPath);

        Scene scene = new Scene(root, 1000, 900);
        primaryStage.setTitle("Subway Screen Project");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * The main method for launching the JavaFX application.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        if (args.length > 0) {
            defaultNewsKeyWord = args[0];
        }
        parseArguments(args);
        launch(args);
    }


    /**
     * Parses command line arguments.
     *
     * @param args the command line arguments
     */
    private static void parseArguments(String[] args) {
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "--in":
                    if (i + 1 < args.length) {
                        inputFilePath = args[++i];
                    }
                    break;
                case "--out":
                    if (i + 1 < args.length) {
                        outputFolderPath = args[++i];
                    }
                    break;
                default:
                    break;
            }
        }
    }
}
