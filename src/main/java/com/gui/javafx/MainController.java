package com.gui.javafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController extends Application
{
    private static AppController appController;

    public static AppController getAppController() {
        return appController;
    }

    public static void main(String[] args)
    {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().
                getResource("App.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        appController = fxmlLoader.getController();
        stage.setTitle("Геометрические фигуры");
        stage.show();

    }
}