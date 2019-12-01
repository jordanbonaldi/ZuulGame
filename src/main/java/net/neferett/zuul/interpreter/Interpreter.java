package net.neferett.zuul.interpreter;

import lombok.Data;
import lombok.SneakyThrows;
import net.neferett.zuul.handlers.Command;
import net.neferett.zuul.handlers.CommandsHandler;
import net.neferett.zuul.handlers.ExtendableCommand;
import net.neferett.zuul.player.Player;

import java.util.Arrays;

@Data
public class Interpreter {

    /**
     * Parameters in constructor thanks to @Data
     *
     * Auto initialised (@Data)
     */
    private final CommandsHandler commandsManager;

    /**
     * This boolean stop the game and thread if set to false
     */
    private boolean activated;

    /**
     *
     * Get the first words and ignore the rest of the expression
     *
     * @param command Command name
     * @return String
     */
    private String getCommandName(String command) {
        return command.contains(" ") ? command.split(" ")[0] : command;
    }

    /**
     *
     * Returning command and its arguments
     * By splitting by space
     *
     * @param command commands args to parse
     * @return String[]
     */
    private String[] getCommandsArgs(String command) {
        // If there isn't any arguments, we return an empty array
        if (!command.contains(" "))
            return new String[]{};

        String[] arguments = command.split(" ");

        // We remove the first word which is the command name

        // Faster processing than copyOfRange and less memory allocation
        if (arguments.length == 2)
            return new String[]{arguments[1]};

        return Arrays.copyOfRange(arguments, 1, arguments.length);
    }

    /**
     * Package private function allowing the process
     * to interpret commands thanks to CommandsManager
     *
     * @param command Command name
     * @param player Player Object
     */
    @SneakyThrows
    public void handleCommand(Player player, String command) {
        String commandName = this.getCommandName(command);
        String[] commandArgs = this.getCommandsArgs(command);

        /*
         * Trying to find "commandName" inside commandsManager list
         *
         * We take the first command or set ExtendableCommand to null
         */
        ExtendableCommand extendableCommand = this.commandsManager.getManager().stream()
                .filter(e -> e.getCommand().name().equalsIgnoreCase(commandName))
                .findFirst().orElse(null);

        if (null == extendableCommand) {
            Output.getInstance().print("Command " + commandName + " not found. Type 'help' for help.");
            return;
        }

        // Retrieving the command annotation and its parameters
        Command cmd = extendableCommand.getCommand();

        /*
         * If the commands doesn't correspond to the attached parameters
         *  minLength, argsLength
         * We print the help parameter
         */
        if ((cmd.minLength() == 0 && cmd.argsLength() != commandArgs.length) ||
                (cmd.minLength() > 0 && commandArgs.length < cmd.minLength())
        ){
            Output.getInstance().print("Command " + cmd.name() + " mismatch args length");
            Output.getInstance().print(cmd.help());
            return;
        }


        // If command returns false we print an error
        if (!extendableCommand.onCommand(player, commandArgs))
            Output.getInstance().print("Error while performing command");
    }

}

