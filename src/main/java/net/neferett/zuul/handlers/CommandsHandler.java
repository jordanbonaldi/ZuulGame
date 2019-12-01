package net.neferett.zuul.handlers;

import lombok.Data;
import lombok.SneakyThrows;
import lombok.experimental.Delegate;
import net.neferett.zuul.Zuul;

import java.lang.reflect.Constructor;
import java.net.URI;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Data
public class CommandsHandler {

    private final String packageName;

    private final URI packageURI;

    @SneakyThrows
    public CommandsHandler() {
        this.packageName = "net.neferett.zuul.commands".replace('.', '/');
        this.packageURI = Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource(this.packageName)).toURI();
    }

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

    @SneakyThrows
    private Path pathFinder() {
        Path path = null;

        if (this.packageURI.toString().startsWith("jar:")) {
            try {
                path = FileSystems.getFileSystem(this.packageURI).getPath(this.packageName);
            } catch (final FileSystemNotFoundException e) {
                path = FileSystems.newFileSystem(this.packageURI, Collections.emptyMap()).getPath(this.packageName);
            }
        }

        return path == null ? Paths.get(this.packageURI) : path;
    }

    @SneakyThrows
    private Class<? extends ExtendableCommand> pathToClazz(Path file) {
        final String path = file.toString().replace('/', '.');
        final String name = path.substring(path.indexOf(this.packageName.replace('/', '.')),
                path.length() - ".class".length());

        return (Class<? extends ExtendableCommand>) Class.forName(name);
    }

    /**
     * Scanning every classes inside a specific package
     * Checking if it extends ExtendableCommand and has annotation Command.class
     * And send them to the createCommand function
     */
    @SneakyThrows
    public void searchCommandsInSubPackages() {
        final Path root = this.pathFinder();

        Files.walk(root).filter(Files::isRegularFile).map(this::pathToClazz).forEach(this::createCommand);
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
