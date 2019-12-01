package net.neferett.zuul.commands;

import net.neferett.zuul.gamemiscs.Exit;
import net.neferett.zuul.gamemiscs.Room;
import net.neferett.zuul.handlers.Command;
import net.neferett.zuul.handlers.ExtendableCommand;
import net.neferett.zuul.interpreter.Output;
import net.neferett.zuul.player.Character;
import net.neferett.zuul.player.Player;

@Command(name = "go", argsLength = 1, desc = "Go to room", help = "go <location>")
public class goCommand extends ExtendableCommand {

    @Override
    public boolean onCommand(Character player, String... args) {
        // Args can't overbound commandsHandler handle it
        Room current = player.getCurrentRoom();
        Exit exit = current.getExit(args[0]);

        if (exit == null) {
            Output.getInstance().print(args[0] + " doesn't exists");
            return false;
        } else if (current.getExits().size() == 0) { // if there's no exits
            Output.getInstance().print("There is no door!");
            return false;
        }

        // Set the current room and print new room information
        player.getCurrentRoom().getCharacters().remove(player);
        player.setCurrentRoom(exit.getRoom());

        exit.getRoom().getCharacters().add(player);
        exit.getRoom().printRoomInformation();

        return true;
    }

}
