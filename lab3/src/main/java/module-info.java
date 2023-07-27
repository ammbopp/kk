module ku.cs {
    requires javafx.controls;
    requires javafx.fxml;


    opens ku.cs.lab3 to javafx.fxml;
    exports ku.cs.lab3;
    exports ku.cs.controllers;
    opens ku.cs.controllers to javafx.fxml;
}