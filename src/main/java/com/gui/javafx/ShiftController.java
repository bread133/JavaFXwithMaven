package com.gui.javafx;

import com.example.testfigure.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static com.gui.javafx.AlertController.Fail;
import static com.gui.javafx.AlertController.ShiftSuccess;

public class ShiftController implements Initializable {
    public ChoiceBox<String> listOfShapes;
    public Button exit;
    public ChoiceBox<String> listOfMove;
    public TextField x;
    public TextField y;
    public Label labelX;
    public Label labelY;
    public Label symmetry;
    public Label labelPhi;
    public ChoiceBox<String> listOfSymmetry;
    Integer j = null;
    public ArrayList<IShape> shapes = AppController.shapes;

    String selectedFigure = null;

    public void NotVisible() {
        x.setVisible(false);
        y.setVisible(false);
        labelX.setVisible(false);
        labelY.setVisible(false);
        symmetry.setVisible(false);
        listOfSymmetry.setVisible(false);
        labelPhi.setVisible(false);
    }

    public void Shift() throws Exception {
        int i = listOfShapes.getSelectionModel().getSelectedIndex();
        if(i == -1)
            throw Fail("Не выбрана фигура.");
        j = listOfSymmetry.getSelectionModel().getSelectedIndex();
        if (selectedFigure == null)
            throw Fail("Не выбран тип движения.");
        switch (selectedFigure) {
            case "Сдвиг" -> {
                if (x.getText().equals("") || y.getText().equals(""))
                    throw Fail("Не все поля заполнены.");
                shapes.get(i).shift(new Point2D(new double[]{
                        Double.parseDouble(x.getText()),
                        Double.parseDouble(y.getText())}));
            }
            case "Симметрия" -> {
                if (j == -1)
                    throw Fail("Не выбрана ось.");
                shapes.get(i).symAxis(j);
            }
            case "Поворот" -> {
                if (y.getText().equals(""))
                    throw Fail("Не все поля заполнены.");
                shapes.get(i).rot(Double.parseDouble(y.getText()));
            }
        }
        MainController.getAppController().ReDraw(shapes);
        ShiftSuccess();
        Exit();
    }

    public void Exit() {
        Stage stage = (Stage) listOfSymmetry.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        NotVisible();
        for (IShape shape : shapes) {
            listOfShapes.getItems().addAll(shape.toString());
        }
        listOfSymmetry.setItems(FXCollections.observableArrayList(
                "x", "y"));
        listOfMove.setItems(FXCollections.observableArrayList(
                "Сдвиг", "Симметрия", "Поворот"));

        listOfMove.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                selectedFigure = listOfMove.getItems().get((Integer) t1);
                switch (selectedFigure) {
                    case "Сдвиг" -> {
                        NotVisible();
                        x.setVisible(true);
                        y.setVisible(true);
                        labelX.setVisible(true);
                        labelY.setVisible(true);
                    }
                    case "Симметрия" -> {
                        NotVisible();
                        symmetry.setVisible(true);
                        listOfSymmetry.setVisible(true);
                    }
                    case "Поворот" -> {
                        NotVisible();
                        y.setVisible(true);
                        labelPhi.setVisible(true);
                    }
                }
            }
        });
    }
}
