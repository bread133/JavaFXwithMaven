package com.gui.javafx;

import com.example.testfigure.IShape;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static com.gui.javafx.AlertController.Fail;
import static com.gui.javafx.AlertController.SquareSuccess;

public class SquareController implements Initializable {
    public ChoiceBox<String> listOfShapes;
    public ArrayList<IShape> shapes = AppController.shapes;

    public void Square() throws Exception {
        MainController.getAppController().ReDraw(shapes);
        int i = listOfShapes.getSelectionModel().getSelectedIndex();
        if(i < 0)
            throw Fail("Не выбрана фигура.");
        MainController.getAppController().Draw(shapes.get(i), Color.RED);
        switch (MainController.getAppController().What) {
            case "Square" -> MainController.getAppController().view.
                    setText(String.valueOf(shapes.get(i).square()));
            case "Length" -> MainController.getAppController().view.
                    setText(String.valueOf(shapes.get(i).length()));
        }
        SquareSuccess();
        Exit();
    }

    public void Exit() {
        Stage stage = (Stage) listOfShapes.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        for (IShape shape : shapes) {
            listOfShapes.getItems().addAll(shape.toString());
        }
    }
}
