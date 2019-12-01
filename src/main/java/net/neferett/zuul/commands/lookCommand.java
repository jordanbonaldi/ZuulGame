package net.neferett.zuul.commands;

import net.neferett.zuul.handlers.Command;
import net.neferett.zuul.handlers.ExtendableCommand;
import net.neferett.zuul.player.Character;
import net.neferett.zuul.player.Player;

@Command(name = "look", argsLength = 0, desc = "Look room", help = "look", gui = false)
public class lookCommand extends ExtendableCommand {

    @Override
    public boolean onCommand(Character player, String... args) {

        // Just printing room information
        player.getCurrentRoom().printRoomInformation();

        return true;
    }

}
