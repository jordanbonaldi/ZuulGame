module ZuulFX {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;

    opens net.neferett.zuul.application to javafx.fxml;
    opens net.neferett.zuul.gui to javafx.fxml;
    exports net.neferett.zuul.application;
    exports net.neferett.zuul.gui;
}