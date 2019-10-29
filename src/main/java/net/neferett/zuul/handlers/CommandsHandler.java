package net.neferett.zuul.handlers;

import lombok.Data;
import lombok.SneakyThrows;
import lombok.experimental.Delegate;
import net.neferett.zuul.Zuul;
import org.reflections.Reflections;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

@Data
public class CommandsHandler {

    /**
     * Getting handler instance
     * @return CommandsHandler
     */
    public static CommandsHandler getInstance() {
        return Zuul.getInstance().getCommandsHandler();
    }

    /**
     * Allowing us to delegate this List and incorporate it to CommandsManager
     * Thus the function this.add or this.size... can be used inside this class
     */
    @Delegate
    public List<ExtendableCommand> manager = new ArrayList<>();

    /**
     * Scanning every classes inside a specific package
     * Checking if it extends ExtendableCommand and has annotation Command.class
     * And send them to the createCommand function
     */
    public void searchCommandsInSubPackages() {
        Reflections reflection = new Reflections("net.neferett.zuul.commands");

        reflection.getSubTypesOf(ExtendableCommand.class).stream()
                .filter(e -> e.isAnnotationPresent(Command.class))
                .forEach(this::createCommand);
    }

    /**
     * SneakyThrows handle any error
     * if any error occurs, process will stop normally
     *
     * @param clazz Clazz to instantiate
     */
    @SneakyThrows
    private void createCommand(Class<? extends ExtendableCommand> clazz) {
        // Getting clazz constructor (public one)
        Constructor constructor = clazz.getDeclaredConstructors()[0];

        // Creating a new instance of this clazz
        ExtendableCommand extendableCommand = (ExtendableCommand) constructor.newInstance();
        // Finally retrieving the annotation
        extendableCommand.setCommand(clazz.getAnnotation(Command.class));

        this.add(extendableCommand);
    }
}
