module com.example.plifeterminaldesktop {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires json.simple;

    opens com.example.plifeterminaldesktop to javafx.fxml;
    exports com.example.plifeterminaldesktop;
}