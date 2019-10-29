package net.neferett.zuul.commands;

import net.neferett.zuul.Zuul;
import net.neferett.zuul.handlers.Command;
import net.neferett.zuul.handlers.ExtendableCommand;
import net.neferett.zuul.player.Player;

@Command(name = "quit", argsLength = 0, desc = "Quit the game", help = "quit")
public class quitCommand extends ExtendableCommand {

    @Override
    public boolean onCommand(Player player, String... args) {
        System.out.println("Stopping the game..");
        // Stopping thread loop
        Zuul.getInstance().getInterpreter().setActivated(false);
        return true;
    }

}
