package net.neferett.zuul.gui;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import net.neferett.zuul.gamemiscs.Room;
import net.neferett.zuul.handlers.CharacterHandler;
import net.neferett.zuul.handlers.CommandsHandler;
import net.neferett.zuul.handlers.ExtendableCommand;
import net.neferett.zuul.player.AI;
import net.neferett.zuul.player.Character;
import net.neferett.zuul.player.Player;

import java.util.stream.Collectors;

public class GameController implements Controller{

    @FXML
    private ListView<String> items;

    @FXML
    private ListView<String> directions;

    @FXML
    private ListView<String> playerInRoom;

    @FXML
    private ListView<String> itemsInRoom;

    @FXML
    private HBox hBox;

    @FXML
    private Label playerInfo;

    @FXML
    private Label roomDesc;

    private Player player;

    /**
     * Creating same event for every commands thanks to generic abstract
     *
     * @param command Abstract
     * @param listView List
     * @return EventHandler
     */
    private EventHandler createEvent(ExtendableCommand command, ListView<String> listView) {
        return (event) -> {
            if (listView.getItems().size() == 0)
                return;

            command.onCommand(this.player,
                    listView.getSelectionModel().getSelectedItem().replaceAll("\\(", "-").trim().split("-")[0].trim()
            );

            CharacterHandler.getInstance().getAI().forEach(AI::nextRound);

            this.refresh();
        };
    }

    /**
     *
     * Creating an instance of button from command abstract
     *
     * @param command Abstract
     * @return Button
     */
    private Button createButton(ExtendableCommand command) {
        String name = command.getCommand().name();
        Button button = new Button(name);

        button.setPrefSize(100, 100);

        button.setOnMouseClicked(this.createEvent(command,
                name.equalsIgnoreCase("go") ? this.directions :
                        name.equalsIgnoreCase("take") ? this.itemsInRoom : this.items
        ));

        return button;
    }

    /**
     * Loading main button from Commands allowing GUI
     */
    public void initialize() {
        this.hBox.getChildren().addAll(
                CommandsHandler.getInstance().getManager().stream().filter(e -> e.getCommand().gui())
                        .map(this::createButton).collect(Collectors.toList())
        );

        this.hBox.setSpacing(50);
    }

    /**
     * Clearing list before filling them with new data
     */
    private void clearAll() {
        this.items.getItems().clear();
        this.directions.getItems().clear();
        this.itemsInRoom.getItems().clear();
        this.playerInRoom.getItems().clear();
    }

    /**
     * Refreshing player inventory from a specific player
     * @param player Specific player
     */
    private void refreshItemsOfPlayer(Player player) {
        this.items.getItems().addAll(
                player.getItems()
                        .stream().map(e -> e.getName() + " - " + e.getWeight()).collect(Collectors.toList())
        );

        if (this.items.getItems().size() > 0)
            this.items.getSelectionModel().selectFirst();
    }

    /**
     * Refreshing exit of a specific room
     * @param room Room information
     */
    private void refreshExitsFromRoom(Room room) {
        this.directions.getItems().addAll(
                room.getExits().stream().map(e -> e.getName() + "(" + e.getRoomName() + ")").collect(Collectors.toList())
        );

        if (this.directions.getItems().size() > 0)
            this.directions.getSelectionModel().selectFirst();
    }

    /**
     * Refreshing room items and players
     * @param room Room to get information
     */
    private void refreshRoomInformation(Room room) {
        this.playerInRoom.getItems().addAll(
                room.getCharacters().stream().filter(e -> !e.getName().equalsIgnoreCase(this.player.getName())).map(Character::getName).collect(Collectors.toList())
        );

        this.itemsInRoom.getItems().addAll(
                room.getItems().stream().map(e -> e.getName() + "(" + e.getWeight() + ")").collect(Collectors.toList())
        );

        if (this.itemsInRoom.getItems().size() > 0)
            this.itemsInRoom.getSelectionModel().selectFirst();
    }

    /**
     * Refreshing room information and player's inventory
     */
    public void refresh() {
        this.clearAll();

        this.player = CharacterHandler.getInstance().getPlayers().get(0);

        this.playerInfo.setText("Player " + player.getName() + " - Room: " + player.getCurrentRoom().getName());
        this.roomDesc.setText(this.player.getCurrentRoom().getDescription());

        this.refreshItemsOfPlayer(player);
        this.refreshExitsFromRoom(player.getCurrentRoom());

        this.refreshRoomInformation(player.getCurrentRoom());
    }
}