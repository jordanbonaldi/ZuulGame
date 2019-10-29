package net.neferett.zuul;

import lombok.Data;
import net.neferett.zuul.gamemiscs.Exit;
import net.neferett.zuul.gamemiscs.Item;
import net.neferett.zuul.handlers.CommandsHandler;
import net.neferett.zuul.handlers.PlayersHandler;
import net.neferett.zuul.handlers.RoomsHandler;
import net.neferett.zuul.interpreter.Interpreter;
import net.neferett.zuul.interpreter.ThreadedInterpreter;
import net.neferett.zuul.player.Player;

@Data
public class Zuul {

    private static Zuul instance;

    private CommandsHandler commandsHandler;

    private Interpreter interpreter;

    private Thread threadInterpreter;

    private PlayersHandler playersHandler;

    private RoomsHandler roomsHandler;

    private int maxWeight;

    /**
     * Zuul Constructor (package private)
     */
    Zuul() {

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
        this.playersHandler = new PlayersHandler();

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
    }

    /**
     * Creating room and assigning exits
     */
    void initialiseRooms() {
        this.roomsHandler.createRoom("outside", "outside the main entrance of the university", 3);
        this.roomsHandler.createRoom("theatre", "in a lecture theatre", 3);
        this.roomsHandler.createRoom("lab", "in a computing lab", 3);
        this.roomsHandler.createRoom("pub", "in the campus pub", 3);

        this.roomsHandler.addExits("outside", new Exit("north","lab"), new Exit("east", "theatre"), new Exit("west", "pub"));
        this.roomsHandler.addExits("theatre", new Exit("south", "outside"));
        this.roomsHandler.addExits("lab", new Exit("north", "outside"));
        this.roomsHandler.addExits("pub", new Exit("east", "outside"), new Exit("west", "theatre"));

        this.roomsHandler.addItemOnRoom("lab", new Item("toto", 2));
    }

    /**
     * Adding players by args
     * @param args Array of player names''
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

                this.playersHandler.createPlayer(args[i], args[i + 1]);
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(0);
            }
        }
    }

    /**
     * Launching zuul with default character creation
     */
    void launchZuul() {
        this.playersHandler.createPlayer("PlayerA", "lab");

        // You can create any player you want here

        //this.playersHandler.createPlayer("PlayerB", "outside");

        this.play();
    }

    /**
     * Launching commands interpreter thread
     */
    void play() {

        Player firstPlayer = this.playersHandler.getPlayers().get(0);

        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type 'help' if you need help.");
        System.out.println();

        firstPlayer.getCurrentRoom().printRoomInformation();

        this.threadInterpreter.start();
    }

    public static Zuul getInstance() {
        return instance;
    }
}
