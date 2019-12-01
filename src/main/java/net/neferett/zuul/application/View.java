package net.neferett.zuul.application;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import lombok.Data;

@Data
public class View {

    private final FXMLLoader fxmlLoader;

    private final Parent parent;

    private final Scene scene;

}
