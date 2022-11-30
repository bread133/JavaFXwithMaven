package com.gui.javafx;

import com.example.testfigure.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static com.gui.javafx.AlertController.CrossSuccess;
import static com.gui.javafx.AlertController.Fail;

public class CrossController implements Initializable {
    String selectedFigure = null;
    public ArrayList<IShape> shapes = AppController.shapes;
    @FXML
    private ChoiceBox<String> listOfFigures;
    @FXML
    private ChoiceBox<IShape> listOfShapes1;
    @FXML
    private ChoiceBox<IShape> listOfShapes2;
    @FXML
    void Cross() throws Exception {
        MainController.getAppController().ReDraw(shapes);
        IShape first = listOfShapes1.getSelectionModel().getSelectedItem();
        IShape second = listOfShapes2.getSelectionModel().getSelectedItem();
        if(first == null || second == null)
            throw Fail("Фигуры не выбраны.");
        if(first.cross(second)) {
            MainController.getAppController().view.setText("Пересекаются");
        }
        else {
            MainController.getAppController().view.setText("Не пересекаются");
        }
        MainController.getAppController().Draw(first, Color.RED);
        MainController.getAppController().Draw(second, Color.RED);
        Exit();
        CrossSuccess();
    }

    @FXML
    void Exit() {
        Stage stage = (Stage) listOfFigures.getScene().getWindow();
        stage.close();
    }

    private void helpInit(String shapeString) {
        for (IShape shape : shapes) {
            if(AppController.getShape(shape).equals(shapeString)) {
                listOfShapes1.getItems().addAll(shape);
                listOfShapes2.getItems().addAll(shape);
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listOfFigures.setItems(FXCollections.observableArrayList(
                "Окружность", "Отрезок", "Ломаная",
                "Треугольник", "Трапеция", "Прямоугольник",
                "Четырехугольник", "Многоугольник"));
        listOfFigures.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                selectedFigure = listOfFigures.getItems().get((Integer) t1);
                switch (selectedFigure) {
                    case "Окружность" -> {
                        listOfShapes1.getItems().clear();
                        listOfShapes2.getItems().clear();
                        helpInit("Circle");
                    }
                    case "Отрезок" -> {
                        listOfShapes1.getItems().clear();
                        listOfShapes2.getItems().clear();
                        helpInit("Segment");
                    }
                    case "Ломаная" -> {
                        listOfShapes1.getItems().clear();
                        listOfShapes2.getItems().clear();
                            helpInit("Polyline");
                    }
                    case "Многоугольник" -> {
                        listOfShapes1.getItems().clear();
                        listOfShapes2.getItems().clear();
                        helpInit("NGon");
                    }
                    case "Треугольник" -> {
                        listOfShapes1.getItems().clear();
                        listOfShapes2.getItems().clear();
                        helpInit("TGon");
                    }
                    case "Трапеция" -> {
                        listOfShapes1.getItems().clear();
                        listOfShapes2.getItems().clear();
                        helpInit("Trapeze");
                    }
                    case "Прямоугольник" -> {
                        listOfShapes1.getItems().clear();
                        listOfShapes2.getItems().clear();
                        helpInit("Rectangle");
                    }
                    case "Четырехугольник" -> {
                        listOfShapes1.getItems().clear();
                        listOfShapes2.getItems().clear();
                        helpInit("QGon");
                    }
                }
            }
        });
    }
}
