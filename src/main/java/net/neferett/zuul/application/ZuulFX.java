package net.neferett.zuul.application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.SneakyThrows;
import net.neferett.zuul.Zuul;
import net.neferett.zuul.handlers.ViewHandler;

import java.io.File;
import java.util.Arrays;
import java.util.Objects;

@EqualsAndHashCode(callSuper = true)
@Data
public class ZuulFX extends Application {

    public static ZuulFX getInstance() {
        return Zuul.getInstance().getZuulFX();
    }

    /*
        Creating an instance of a view.fxml wrapper
     */
    @SneakyThrows
    private View fileToView(File file) {
        FXMLLoader loader = new FXMLLoader(file.toURL());
        Parent root = loader.load();

        return new View(loader, root, new Scene(root));
    }

    /*
     *  Loading dynamically every .fxml files
     */
    private void loadAllViews() {
        Arrays.stream(Objects.requireNonNull(
                new File(getClass().getResource("/application/views/").getFile()).listFiles()))
                .forEach(file ->
                    ViewHandler.getInstance().put(file.getName().replaceAll(".fxml", ""), this.fileToView(file))
                );
    }

    /**
     * Starting main window
     *
     * @param primaryStage main window
     */
    @Override
    public void start(Stage primaryStage) {
        ViewHandler.getInstance().setWindow(primaryStage);

        this.loadAllViews();

        ViewHandler.getInstance().setPage("mapView");

        primaryStage.setTitle("Zuul Game");
        primaryStage.show();

        Zuul.getInstance().setZuulFX(this);
    }

}
