package com.gui.javafx;

import javafx.scene.control.Alert;

public class AlertController extends Exception{
    public static void AddSuccess() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION,
                "Фигура успешно добавлена.");
        alert.setTitle("Успех");
        alert.setHeaderText("Добавление завершено");
        alert.show();
    }

    public static void CrossSuccess() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION,
                "Пересечение определено.");
        alert.setTitle("Успех");
        alert.setHeaderText("Операция завершена");
        alert.show();
    }

    public static void DeleteSuccess() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION,
                "Удаление завершено.");
        alert.setTitle("Успех");
        alert.setHeaderText("Операция завершена");
        alert.show();
    }

    public static void ShiftSuccess() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION,
                "Фигура перемещена.");
        alert.setTitle("Успех");
        alert.setHeaderText("Операция завершена");
        alert.show();
    }

    public static void SquareSuccess() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION,
                "Значение вычислено.");
        alert.setTitle("Успех");
        alert.setHeaderText("Операция завершена");
        alert.show();
    }

    public static Exception Fail(String Cause) throws Exception {
        Alert alert = new Alert(Alert.AlertType.WARNING, Cause);
        alert.setTitle("Ошибка");
        alert.setHeaderText("Возникла следующая ошибка");
        alert.show();
        throw new Exception(Cause);
    }
}
