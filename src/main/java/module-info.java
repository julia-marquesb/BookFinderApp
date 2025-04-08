module org.example.bookfinderapp {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires com.google.gson;
    requires java.desktop;

    opens org.example.bookfinderapp to javafx.fxml;
    exports org.example.bookfinderapp;
}