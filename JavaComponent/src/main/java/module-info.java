module org.example.javacomponent {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires opencsv;
    requires java.desktop;

    opens org.example.javacomponent to javafx.fxml;
    exports org.example.javacomponent;
    exports org.example.javacomponent.controllers;
    opens org.example.javacomponent.controllers to javafx.fxml;
}