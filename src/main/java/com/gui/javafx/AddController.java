package com.gui.javafx;

import com.example.testfigure.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

import static com.gui.javafx.AlertController.*;

public class AddController implements Initializable {
    @FXML
    public ChoiceBox<String> ListOfFigure;
    public ChoiceBox<Integer> ValOfVers;
    public TextField x1;
    public TextField x2;
    public TextField x3;
    public TextField x4;
    public TextField x5;
    public TextField x6;
    public TextField x7;
    public TextField x8;
    public TextField x9;
    public TextField x10;
    public TextField x11;
    public TextField x12;
    public TextField x13;
    public TextField x14;
    public TextField x15;
    public TextField x16;
    public TextField y1;
    public TextField y2;
    public TextField y3;
    public TextField y4;
    public TextField y5;
    public TextField y6;
    public TextField y7;
    public TextField y8;
    public TextField y9;
    public TextField y10;
    public TextField y11;
    public TextField y12;
    public TextField y13;
    public TextField y14;
    public TextField y15;
    public TextField y16;
    public Label labelX1;
    public Label labelX2;
    public Label labelX3;
    public Label labelX4;
    public Label labelX5;
    public Label labelX6;
    public Label labelX7;
    public Label labelX8;
    public Label labelX9;
    public Label labelX10;
    public Label labelX11;
    public Label labelX12;
    public Label labelX13;
    public Label labelX14;
    public Label labelX15;
    public Label labelX16;
    public Label labelY1;
    public Label labelY2;
    public Label labelY3;
    public Label labelY4;
    public Label labelY5;
    public Label labelY6;
    public Label labelY7;
    public Label labelY8;
    public Label labelY9;
    public Label labelY10;
    public Label labelY11;
    public Label labelY12;
    public Label labelY13;
    public Label labelY14;
    public Label labelY15;
    public Label labelY16;
    public Label phi;
    public Label labelR;
    public ArrayList<IShape> shapes = AppController.shapes;
    private String selectedFigure = null;
    private Integer selectedVers = -1;

    public void XYCloseOpen(int i, boolean f) {
        Label[] LabelX = new Label[] {
                labelX1, labelX2, labelX3, labelX4,
                labelX5, labelX6, labelX7, labelX8,
                labelX9, labelX10, labelX11, labelX12,
                labelX13, labelX14, labelX15, labelX16 };

        Label[] LabelY = new Label[] {
                labelY1, labelY2, labelY3, labelY4,
                labelY5, labelY6, labelY7, labelY8,
                labelY9, labelY10, labelY11, labelY12,
                labelY13, labelY14, labelY15, labelY16 };

        TextField[] x = new TextField[] {
                x1, x2, x3, x4, x5, x6, x7, x8, x9, x10,
                x11, x12, x13, x14, x15, x16 };

        TextField[] y = new TextField[] {
                y1, y2, y3, y4, y5, y6, y7, y8, y9, y10,
                y11, y12, y13, y14, y15, y16 };

        for (int j = 0; j < i; j++) {
            x[j].setVisible(f);
            y[j].setVisible(f);
            LabelX[j].setVisible(f);
            LabelY[j].setVisible(f);
        }
    }

    private boolean AlertHelp(int n) {
        TextField[] x = new TextField[] {
                x1, x2, x3, x4, x5, x6, x7, x8, x9, x10,
                x11, x12, x13, x14, x15, x16 };

        TextField[] y = new TextField[] {
                y1, y2, y3, y4, y5, y6, y7, y8, y9, y10,
                y11, y12, y13, y14, y15, y16 };

        for (int i = 0; i < n; i++) {
            if (x1.getText().equals("") || y1.getText().equals(""))
                return false;
        }
        return true;
    }

    public void AddShapeOnCanvas() throws Exception {
        if(selectedFigure == null) {
            throw Fail("Не выбран тип фигуры.");
        }
        IShape addShape = null;
        switch (selectedFigure) {
            case "Окружность" -> {
                if (x1.getText().equals("") || y1.getText().equals("") ||
                        y2.getText().equals(""))
                    throw Fail("Не все поля заполнены.");

                addShape = new Circle(new Point2D(new double[]
                            {Double.parseDouble(x1.getText()),
                                    Double.parseDouble(y1.getText())}),
                            Double.parseDouble(y2.getText()));
            }
            case "Отрезок" -> {
                if (!AlertHelp(2)) throw Fail("Не все поля заполнены.");
                addShape = new Segment(new Point2D(new double[]
                        { Double.parseDouble(x1.getText()),
                                Double.parseDouble(y1.getText())}),
                        new Point2D(new double[]{Double.parseDouble
                                (x2.getText()), Double.parseDouble(y2.getText())}));
            }
            case "Ломаная", "Многоугольник" -> {
                if (selectedVers == -1)
                    throw Fail("Не выбрано количество вершин.");
                if (!AlertHelp(selectedVers)) throw Fail("Не все поля заполнены.");
                TextField[] x = new TextField[]{
                        x1, x2, x3, x4, x5, x6, x7, x8, x9, x10,
                        x11, x12, x13, x14, x15, x16};
                TextField[] y = new TextField[]{
                        y1, y2, y3, y4, y5, y6, y7, y8, y9, y10,
                        y11, y12, y13, y14, y15, y16};
                Point2D[] points = new Point2D[selectedVers];
                for (int i = 0; i < selectedVers; i++) {
                    points[i] = new Point2D(new double[]{
                            Double.parseDouble(x[i].getText()),
                            Double.parseDouble(y[i].getText())});
                }
                if (Objects.equals(selectedFigure, "Ломаная")) {
                    addShape = new Polyline(points);
                } else {
                    addShape = new NGon(points);
                }
            }
            case "Треугольник" -> {
                if (!AlertHelp(3))
                    throw Fail("Не все поля заполнены.");
                addShape = new TGon(new Point2D[]{new Point2D(new double[]
                        { Double.parseDouble(x1.getText()),
                                Double.parseDouble(y1.getText())}),
                        new Point2D(new double[]{ Double.parseDouble
                                (x2.getText()), Double.parseDouble(y2.getText())}),
                        new Point2D(new double[]{ Double.parseDouble
                                (x3.getText()), Double.parseDouble(y3.getText())})});
            }
            case "Трапеция" -> {
                if (!AlertHelp(4))
                        throw Fail("Не все поля заполнены.");
                    addShape = new Trapeze(new Point2D[]{new Point2D(new double[]
                            {Double.parseDouble(x1.getText()),
                                    Double.parseDouble(y1.getText())}),
                            new Point2D(new double[]{Double.parseDouble
                                    (x2.getText()), Double.parseDouble(y2.getText())}),
                            new Point2D(new double[]{Double.parseDouble
                                    (x3.getText()), Double.parseDouble(y3.getText())}),
                            new Point2D(new double[]{Double.parseDouble
                                    (x4.getText()), Double.parseDouble(y4.getText())})});
            }
            case "Прямоугольник" -> {
                if (!AlertHelp(4)) throw Fail("Не все поля заполнены.");
                addShape = new Rectangle(new Point2D[]{new Point2D(new double[]
                        {Double.parseDouble(x1.getText()),
                                Double.parseDouble(y1.getText())}),
                        new Point2D(new double[]{Double.parseDouble
                                (x2.getText()), Double.parseDouble(y2.getText())}),
                        new Point2D(new double[]{Double.parseDouble
                                (x3.getText()), Double.parseDouble(y3.getText())}),
                        new Point2D(new double[]{Double.parseDouble
                                (x4.getText()), Double.parseDouble(y4.getText())})});
            }
            case "Четырехугольник" -> {
                if (!AlertHelp(4)) throw Fail("Не все поля заполнены.");
                addShape = new QGon(new Point2D[]{new Point2D(new double[]
                        {Double.parseDouble(x1.getText()),
                                Double.parseDouble(y1.getText())}),
                        new Point2D(new double[]{Double.parseDouble
                                (x2.getText()), Double.parseDouble(y2.getText())}),
                        new Point2D(new double[]{Double.parseDouble
                                (x3.getText()), Double.parseDouble(y3.getText())}),
                        new Point2D(new double[]{Double.parseDouble
                                (x4.getText()), Double.parseDouble(y4.getText())})});
            }
        }

        if (addShape != null) {
            shapes.add(addShape);
            MainController.getAppController().ReDraw(shapes);
            Exit();
            AddSuccess();
        }
    }

    public void Exit() {
        Stage stage = (Stage) ListOfFigure.getScene().getWindow();
        stage.close();
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
        ValOfVers.setVisible(false);
        labelR.setVisible(false);
        XYCloseOpen(16, false);

        ListOfFigure.setItems(FXCollections.observableArrayList(
                "Окружность", "Отрезок", "Ломаная",
                "Треугольник", "Трапеция", "Прямоугольник",
                "Четырехугольник", "Многоугольник"));

        ValOfVers.setItems(FXCollections.observableArrayList(
                3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14,
                15, 16 ));

        ListOfFigure.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                selectedFigure = ListOfFigure.getItems().get((Integer) t1);
                switch (selectedFigure) {
                    case "Окружность" -> {
                        XYCloseOpen(16, false);
                        labelR.setVisible(true);
                        y2.setVisible(true);
                        XYCloseOpen(1, true);
                    }
                    case "Отрезок" -> {
                        XYCloseOpen(16, false);
                        labelR.setVisible(false);
                        XYCloseOpen(2, true);
                    }
                    case "Ломаная", "Многоугольник" -> {
                        XYCloseOpen(16, false);
                        labelR.setVisible(false);
                        ValOfVers.setVisible(true);
                    }
                    case "Треугольник" -> {
                        XYCloseOpen(16, false);
                        labelR.setVisible(false);
                        XYCloseOpen(3, true);
                    }
                    case "Трапеция", "Прямоугольник", "Четырехугольник" -> {
                        XYCloseOpen(16, false);
                        labelR.setVisible(false);
                        XYCloseOpen(4, true);
                    }
                }
            }
        });

        ValOfVers.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                selectedVers = ValOfVers.getItems().get((Integer) t1);
                XYCloseOpen(16, false);
                XYCloseOpen(selectedVers, true);
            }
        });
    }
}
