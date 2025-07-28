module com.example.nepaltourism {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;

    // Open packages for FXML reflection
    opens com.example.nepaltourism to javafx.fxml;
    opens com.example.nepaltourism.Controllers to javafx.fxml;
    opens com.example.nepaltourism.adminSubControllers to javafx.fxml;
    opens com.example.nepaltourism.classes to javafx.base;

    // Export main package
    exports com.example.nepaltourism;
}
