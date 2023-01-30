module idv.tools.simplespammer {
    requires javafx.controls;
    requires javafx.fxml;

    opens idv.tools.simplespammer to javafx.fxml;
    exports idv.tools.simplespammer;
}