module com.southpaw.calculator {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.southpaw.calculator to javafx.fxml;
    exports com.southpaw.calculator;
	exports com.southpaw.calculator.controller;
	opens com.southpaw.calculator.controller to javafx.fxml;
}