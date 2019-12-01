package net.neferett.zuul.handlers;

import lombok.Data;
import net.neferett.zuul.player.Character;

@Data
public abstract class ExtendableCommand {

    /**
     * Command annotation allowing the process to know:
     * Arguments, Description, Help, and Command Name
     */
    private Command command;

    /**
     *
     * Return false on command error or true and successfull execution
     * Takes in param the whole command
     *
     * @param character Character Object
     * @param args Arguments of the command
     * @return boolean
     */
    public abstract boolean onCommand(Character character, String ... args);

}