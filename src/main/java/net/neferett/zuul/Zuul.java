package net.neferett.zuul;

import lombok.Data;
import net.neferett.zuul.application.ZuulFX;
import net.neferett.zuul.handlers.CharacterHandler;
import net.neferett.zuul.handlers.CommandsHandler;
import net.neferett.zuul.handlers.RoomsHandler;
import net.neferett.zuul.handlers.ViewHandler;
import net.neferett.zuul.interpreter.Interpreter;
import net.neferett.zuul.interpreter.Output;
import net.neferett.zuul.interpreter.ThreadedInterpreter;
import net.neferett.zuul.interpreter.csv.CSVInterpreter;
import net.neferett.zuul.interpreter.csv.FileLoader;
import net.neferett.zuul.player.Player;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Data
public class Zuul{

    private static Zuul instance;

    /**
     * Interpreter instances
     */
    private FileLoader fileLoader;
    private CSVInterpreter csvInterpreter;
    private Interpreter interpreter;
    private Thread threadInterpreter;

    /**
     * Handlers instances
     */
    private CommandsHandler commandsHandler;
    private CharacterHandler characterHandler;
    private RoomsHandler roomsHandler;
    private ViewHandler viewHandler;

    /**
     * General attributes instances
     */
    private List<File> maps;
    private int maxWeight;
    private ZuulFX zuulFX;
    private Output output;
    private boolean javaCompilation;

    /**
     * Zuul Constructor (package private)
     */
    Zuul(boolean javaCompilation, File ... files) throws URISyntaxException, IOException {

        /*
            Output Stream
         */
        this.output = new Output();

        /*
         * Playable only gui
         */
        this.javaCompilation = javaCompilation;

        /*
         * View modifier
         */
        this.viewHandler = new ViewHandler();

        /*
         * Creating main instance, to allow getInstance and allowing
         * handlers to be accessible
         */
        instance = this;

        /*
         * Max weight that player can carry
         */
        this.maxWeight = 10;

        /*
         * Creating the Player Handler
         */
        this.characterHandler = new CharacterHandler();

        /*
         * Rooms handler
         */
        this.roomsHandler = new RoomsHandler();

        /*
         * Creating commands manager and searching
         * all the subpackages containing @Command and ExtendableCommand
         */
        this.commandsHandler = new CommandsHandler();
        this.commandsHandler.searchCommandsInSubPackages();

        /*
         * Creating interpreter scanning words
         * and check them inside commands manager
         *
         * Finally activating him
         */
        this.interpreter = new Interpreter(this.commandsHandler);
        this.interpreter.setActivated(true);

        /*
         * Initiating the thread which loop until interpret is set to false
         * This thread allows multiple tasking at the same time
         */
        this.threadInterpreter = new Thread(new ThreadedInterpreter(this.interpreter));

        if (!javaCompilation)
            this.maps = Arrays.asList(files);
        else
            this.maps = Arrays.asList(Objects.requireNonNull(new File(getClass().getResource("/maps/").getFile()).listFiles()));
    }

    /**
     * Creating room and assigning exits
     */
    private void initialiseRooms() {
        this.csvInterpreter.getRooms().forEach(this.roomsHandler::add);
    }

    /**
     * Adding players by args
     * @param args Array of player names''
     *
     * @deprecated
     */
    void addPlayers(String... args) {
        for (int i = 0; i < args.length; i += 2) {
            try {
                /*
                    If room doesn't exists we end program
                 */
                if (this.roomsHandler.getRoom(args[i + 1]) == null) {
                    throw new Exception("Room " + args[i + 1] + " doesn't exists");
                }

                this.characterHandler.createPlayer(args[i], args[i + 1]);
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(0);
            }
        }
    }

    /**
     * Generic file loader selected by user
     *
     * @param file Game file
     */
    public void mapLoader(File file) {
        /*
         * FileLoader for game file "file"
         */
        this.fileLoader = new FileLoader(file);
        this.csvInterpreter = new CSVInterpreter(this.fileLoader).interpretRooms();

        this.csvInterpreter.close();

        this.initialiseRooms();
    }

    /**
     * Creating zuul with default character
     */
    public void createPlayer() {
        this.characterHandler.createPlayer("PlayerA", this.roomsHandler.getRooms().get(0).getName());
        this.characterHandler.createAI("AI 1");
    }


    /**
     * Launching commands interpreter thread
     */
    public void play() {

        Player firstPlayer = this.characterHandler.getPlayers().get(0);

        this.output.print("");
        this.output.print("Welcome to the World of Zuul!");
        this.output.print("World of Zuul is a new, incredibly boring adventure game.");
        this.output.print("Type 'help' if you need help.");
        this.output.print("");

        firstPlayer.getCurrentRoom().printRoomInformation();

        if (!this.javaCompilation)
            this.threadInterpreter.start();
        else
            ViewHandler.getInstance().setPage("gameView");
    }

    public static Zuul getInstance() {
        return instance;
    }
}
