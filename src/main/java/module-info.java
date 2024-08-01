module com.c01survivor {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;

    requires java.desktop;

    opens com.c01survivor to javafx.fxml;
    exports com.c01survivor;
}
