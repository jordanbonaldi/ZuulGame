package net.neferett.zuul.player;

import lombok.Data;
import lombok.EqualsAndHashCode;
import net.neferett.zuul.gamemiscs.DefaultExit;
import net.neferett.zuul.gamemiscs.Room;
import net.neferett.zuul.handlers.CommandsHandler;
import net.neferett.zuul.handlers.ExtendableCommand;
import net.neferett.zuul.handlers.RoomsHandler;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@EqualsAndHashCode(callSuper = true)
@Data
public class AI extends Character {

    private AI(String name, Room currentRoom) {
        super(name, currentRoom);
    }

    /**
     * Initialise a player only by its name
     *
     * Selecting a random room to spawn
     *
     * @param name Player Name
     */
    public AI(String name) {
        this(name,
                RoomsHandler.getInstance().get(
                        new Random().nextInt(
                                RoomsHandler.getInstance().getRooms().size()
                        )
                )
        );
    }

    /**
     * Possible allowed command to type for this AI
     * @param roomEmpty Check if room is empty
     * @return List<ExtendableCommand>
     */
    private List<ExtendableCommand> AIPossibleChoicesCommands(boolean roomEmpty) {
        return CommandsHandler.getInstance().getManager().stream()
                .filter(e ->
                        e.getCommand().name().contains("go") ||
                        roomEmpty && e.getCommand().name().contains("take")
                ).collect(Collectors.toList());
    }

    /**
     * Arguments to send to Abstract command
     * @param command Abstract command
     */
    private void handleChoices(ExtendableCommand command) {
        if (command.getCommand().name().contains("go"))
            command.onCommand(this,
                    this.currentRoom.getExits().get(new Random().nextInt(this.currentRoom.getExits().size())).getName()
            );
        else
            command.onCommand(this,
                    this.currentRoom.getItems().get(new Random().nextInt(this.currentRoom.getItems().size())).getName()
            );
    }

    @Override
    /*
     * Ai choice handling
     */
    public void nextRound() {
        List<ExtendableCommand> commands = this.AIPossibleChoicesCommands(this.currentRoom.getItems().size() > 1);
        int maxChoice = commands.size();

        ExtendableCommand command = commands.get(new Random().nextInt(maxChoice));

        this.handleChoices(command);
    }
}
