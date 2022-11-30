module com.gui.javafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.swing;
    requires java.desktop;
    requires TestFigure;

    opens com.gui.javafx to javafx.fxml;
    exports com.gui.javafx;
}