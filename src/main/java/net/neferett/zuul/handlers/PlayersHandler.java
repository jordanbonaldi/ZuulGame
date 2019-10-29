package net.neferett.zuul.handlers;

import lombok.Data;
import lombok.experimental.Delegate;
import net.neferett.zuul.Zuul;
import net.neferett.zuul.player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class PlayersHandler {

    /**
     * Delegating Players to the handlerÒ
     */
    @Delegate
    List<Player> players = new ArrayList<>();

    /**
     * Getting handler instance
     * @return PlayersHandler
     */
    public static PlayersHandler getInstance() {
        return Zuul.getInstance().getPlayersHandler();
    }

    /**
     * Get Player By Name
     *
     * Create a new player if not found
     *
     * @param name Name of player
     * @return Player
     */
    public Player getPlayerByName(String name) {
        /*
         * Filtering player by name and taking the first or setting player to null
         */
        return this.players.stream().filter(e -> e.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    /**
     *
     * Get all players inside a room
     *
     * @param room Name of room
     * @return ArrayList
     */
    public List<Player> getPlayerByRoom(String room) {
        return this.players.stream().filter(e -> e.getCurrentRoom().getName().equalsIgnoreCase(RoomsHandler.getInstance().getRoom(room).getName())).collect(Collectors.toList());
    }

    /**
     * Create a new player
     * @param name Name of player
     * @param room Name of room to spawn player
     * @return Player
     */
    public Player createPlayer(String name, String room) {
        Player player = new Player(name, RoomsHandler.getInstance().getRoom(room));

        this.add(player);

        return player;
    }

}
