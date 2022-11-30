package com.gui.javafx;

import com.example.testfigure.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.embed.swing.SwingFXUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import static com.gui.javafx.AlertController.DeleteSuccess;
import static com.gui.javafx.AlertController.Fail;

public class AppController {
    public TextField view;
    @FXML
    public Canvas canvas;
    String What = null;

    public static ArrayList<IShape> shapes = new ArrayList<>();
    FileChooser.ExtensionFilter ex1 = new
            FileChooser.ExtensionFilter("Text Files", "*.txt");
    FileChooser.ExtensionFilter ex2 = new
            FileChooser.ExtensionFilter("all Files", "*.*");

    public void AddShape() throws IOException {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        FXMLLoader fxmlLoader = new FXMLLoader(
                getClass().getResource("Add.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        window.setScene(scene);
        window.setTitle("Создание новой фигуры");
        window.showAndWait();
    }

    public void SaveFile() throws Exception {
        FileChooser fileChooser = new FileChooser();
        StringBuilder content = new StringBuilder();
        content.append(shapes.size()).append('\n');
        for (IShape shape : shapes) {
            content.append(shape
                    .toString()).append('\n');
        }

        fileChooser.getExtensionFilters().addAll(ex1, ex2);
        fileChooser.setTitle("Сохранить файл");
        fileChooser.setInitialDirectory(new File(
                "C:\\Users\\bread\\IdeaProjects\\Point_class"));
        File file = fileChooser.showSaveDialog(new Stage());
        if(file != null) {
            SaveSystem(String.valueOf(content), file);
        }
        else
            throw Fail("Не выбран файл");
    }

    public void SaveSystem(String content, File file) throws Exception {
        try {
            PrintWriter printWriter = new PrintWriter(file);
            printWriter.write(content);
            printWriter.close();
        } catch (FileNotFoundException e) {
            throw Fail("Не выбран файл");
        }
    }

    public void DownloadFile() throws Exception {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(ex1, ex2);
        fileChooser.setTitle("Открыть файл");
        fileChooser.setInitialDirectory(new File(
                "C:\\Users\\bread\\IdeaProjects\\Point_class"));
        File file = fileChooser.showOpenDialog(new Stage());
        if(file != null) {
            try {
                Scanner from = new Scanner(file);
                int n = from.nextInt();
                String temp = from.nextLine();
                for (int i = 0; i < n; i++) {
                    shapes.add(Down(from));
                    Draw(shapes.get(shapes.size() - 1), Color.BLACK);
                }

                for (IShape shape : shapes) {
                    System.out.println(shape);
                }
            } catch (FileNotFoundException e) {
                Fail("Не выбран файл");
                throw new RuntimeException(e);
            }
        }
        else
            Fail("Не выбран файл");
    }

    private IShape Down(Scanner in) throws Exception {
        String figure = in.nextLine();
        //System.out.println(figure);
        String cord = in.nextLine();
        String[] splits;
        switch (figure) {
            case "Circle" -> {
                splits = cord.split("[()Centr,: ;Radius]+");
                double x = Double.parseDouble(splits[1]);
                double y = Double.parseDouble(splits[2]);
                double r = Double.parseDouble(splits[3]);
                return new Circle(new Point2D(new double[]{x, y}), r);

            }
            case "Segment" -> {
                splits = cord.split("[(, );]+");
                double x1 = Double.parseDouble(splits[1]);
                double y1 = Double.parseDouble(splits[2]);
                double x2 = Double.parseDouble(splits[3]);
                double y2 = Double.parseDouble(splits[4]);
                return new Segment(new Point2D(new double[]{x1, y1}),
                        new Point2D(new double[]{x2, y2}));
            }
            case "Polyline" -> {
                splits = cord.split("[(, );]+");
                Point2D[] points = new Point2D[splits.length / 2 - 1];
                for (int i = 0; i < splits.length / 2 - 1; i++) {
                    double p_x = Double.parseDouble(splits[(i + 1) * 2 - 1]);
                    double p_y = Double.parseDouble(splits[(i + 1) * 2]);
                    points[i] = new Point2D(new double[]{p_x, p_y});
                }
                return new Polyline(points);
            }
            case "NGon", "QGon", "Rectangle", "TGon", "Trapeze" -> {
                splits = cord.split("[(, );{}]+");
                Point2D[] points1 = new Point2D[(splits.length - 1) / 2];
                for (int i = 0; i < (splits.length - 1) / 2; i++) {
                    double p_x = Double.parseDouble(splits[i * 2 + 1]);
                    double p_y = Double.parseDouble(splits[(i + 1) * 2]);
                    points1[i] = new Point2D(new double[]{p_x, p_y});
                }
                return nGons(figure, points1);
            }
            default -> {
                Fail("Неверная фигура. Проверьте корректность " +
                        "текстового документа.");
                throw new Exception("Неверная фигура.");
            }
        }
    }

    public static String getShape(IShape i) {
        if(i instanceof Segment)
            return "Segment";
        if(i instanceof Polyline)
            return "Polyline";
        if(i instanceof TGon)
            return "TGon";
        if(i instanceof Rectangle)
            return "Rectangle";
        if(i instanceof Trapeze)
            return "Trapeze";
        if(i instanceof QGon)
            return "QGon";
        if(i instanceof NGon)
            return "NGon";
        if(i instanceof Circle)
            return "Circle";
        return "Error";
    }

    public void ReDraw(ArrayList<IShape> shapes) throws Exception {
        HelpClear(canvas.getGraphicsContext2D());
        for (IShape shape : shapes) {
            Draw(shape, Color.BLACK);
        }
    }
    public void Draw(IShape figure, Color color) throws Exception {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        double CenterX = canvas.getWidth() / 2;
        double CenterY = canvas.getHeight() / 2;
        gc.setStroke(color);
        switch (getShape(figure)) {
            case "Circle" -> {
                double r = figure.getSegment(0).getFinish().getX(0);
                double x = figure.getSegment(0).getStart().getX(0);
                double y = figure.getSegment(0).getStart().getX(1);
                gc.strokeOval(CenterX + x - r, CenterY - y - r, r * 2, r * 2);

            }
            case "Segment" -> {
                gc.strokeLine(CenterX + figure.getSegment(0).getStart().getX(0),
                        CenterY - figure.getSegment(0).getStart().getX(1),
                        CenterX + figure.getSegment(0).getFinish().getX(0),
                        CenterY - figure.getSegment(0).getFinish().getX(1));
            }
            case "Polyline" -> {
                for (int i = 0; i < figure.getShapeN() - 1; i++) {
                    Draw(figure.getSegment(i), color);
                }
            }
            case "NGon", "QGon", "Rectangle", "TGon", "Trapeze" -> {
                for (int i = 0; i < figure.getShapeN(); i++) {
                    Draw(figure.getSegment(i), color);
                }
            }
        }
    }

    private static IShape nGons(String ngon, Point2D[] points) throws Exception {
        return switch (ngon) {
            case "NGon" -> new NGon(points);
            case "QGon" -> new QGon(points);
            case "TGon" -> new TGon(points);
            case "Rectangle" -> new Rectangle(points);
            case "Trapeze" -> new Trapeze(points);
            default -> throw new Exception("Неверная фигура.");
        };
    }

    public void SaveImage() throws Exception {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PNG File", "*.png"),
                new FileChooser.ExtensionFilter("All Images", "*.*"));
        File file = fileChooser.showSaveDialog(new Stage());
        if(file != null) {
            BufferImage(file);
        }
        else
            throw Fail("Не выбран файл");
    }

    private void BufferImage(File file) throws Exception {
        SnapshotParameters sp = new SnapshotParameters();
        sp.setFill(Color.TRANSPARENT);
        WritableImage writableImage = new WritableImage((int)canvas.getWidth(),
                (int)canvas.getHeight());
        Image img = canvas.snapshot(null, writableImage);

        BufferedImage bufferedImage = SwingFXUtils.fromFXImage(img, null);
        try {
            ImageIO.write(bufferedImage, "png", file);
        } catch (IOException e) {
            throw Fail("Не выбран файл");
        }
    }

    public void ShiftShape() throws IOException {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        FXMLLoader fxmlLoader = new FXMLLoader(
                getClass().getResource(
                        "Shift.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        window.setScene(scene);
        window.setTitle("Движение фигуры");
        window.showAndWait();
    }

    public void DeleteShape() throws IOException {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        FXMLLoader fxmlLoader = new FXMLLoader(
                getClass().getResource(
                        "Delete.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        window.setScene(scene);
        window.setTitle("Удаление фигуры");
        window.showAndWait();
    }

    public void Clear() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        shapes.clear();
        HelpClear(gc);
        DeleteSuccess();
    }

    public void HelpClear(GraphicsContext gc) {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    public void SquareShape() throws IOException {
        What = "Square";
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        FXMLLoader fxmlLoader = new FXMLLoader(
                getClass().getResource(
                        "SquareLength.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        window.setScene(scene);
        window.setTitle("Площадь фигуры");
        window.showAndWait();
    }

    public void LengthShape() throws IOException {
        What = "Length";
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        FXMLLoader fxmlLoader = new FXMLLoader(
                getClass().getResource(
                        "SquareLength.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        window.setScene(scene);
        window.setTitle("Периметр фигуры");
        window.showAndWait();
    }

    public void CrossShape() throws IOException {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        FXMLLoader fxmlLoader = new FXMLLoader(
                getClass().getResource(
                        "Cross.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        window.setScene(scene);
        window.setTitle("Пересечение фигур");
        window.showAndWait();
    }
}
