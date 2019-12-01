package net.neferett.zuul.gui;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.stage.FileChooser;
import net.neferett.zuul.Zuul;
import net.neferett.zuul.handlers.ViewHandler;
import net.neferett.zuul.interpreter.Output;

import java.io.File;
import java.util.Map;
import java.util.stream.Collectors;

public class MapController implements Controller{

    @FXML
    private ListView<String> gameNames;

    private Map<String, File> maps;

    /**
     * Getting all maps files to print them
     * @return Map<String File>
     */
    private Map<String, File> loadFiles() {
        return Zuul.getInstance().getMaps().stream().collect(Collectors.toMap(File::getName, file -> file));
    }

    /**
     * File chooser creator allowing the user to import any .csv file
     */
    public void importMap() {
        final FileChooser fileChooser = new FileChooser();

        File file = fileChooser.showOpenDialog(ViewHandler.getInstance().getWindow());

        if (file.getName().contains(".csv")) {
            this.gameNames.getItems().add(file.getName().replaceAll(".csv", ""));
            this.maps.put(file.getName(), file);
        }
    }

    /**
     * Initializing ListView and loading maps
     */
    public void initialize() {
        this.maps = this.loadFiles();
        this.gameNames.getItems().addAll(this.maps.keySet().stream().map(e -> e.replaceAll(".csv", "")).collect(Collectors.toList()));
        this.gameNames.getSelectionModel().selectFirst();
    }

    /**
     * Exit button
     */
    public void exitProgram() {
        System.exit(0);
    }

    /**
     * Continue button with game launcher
     */
    public void launchGameWithMap() {
        Output.getInstance().setActivated(false);

        Zuul.getInstance().mapLoader(this.maps.get(this.gameNames.getSelectionModel().getSelectedItems().get(0) + ".csv"));
        Zuul.getInstance().createPlayer();
        Zuul.getInstance().play();
    }

    @Override
    public void refresh() { }
}