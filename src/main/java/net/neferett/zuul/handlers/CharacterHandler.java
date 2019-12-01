package net.neferett.zuul.handlers;

import lombok.Data;
import lombok.experimental.Delegate;
import net.neferett.zuul.Zuul;
import net.neferett.zuul.player.AI;
import net.neferett.zuul.player.Character;
import net.neferett.zuul.player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class CharacterHandler {

    /**
     * Delegating Players to the handlerÒ
     */
    @Delegate
    List<Character> characters = new ArrayList<>();

    /**
     * Getting handler instance
     * @return PlayersHandler
     */
    public static CharacterHandler getInstance() {
        return Zuul.getInstance().getCharacterHandler();
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
        return (Player) this.characters.stream().filter(e -> e.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    /**
     *
     * Get all players inside a room
     *
     * @param room Name of room
     * @return ArrayList
     */
    public List<Character> getCharacterByRoom(String room) {
        return this.characters.stream()
                .filter(e -> e.getCurrentRoom().getName().equalsIgnoreCase(RoomsHandler.getInstance().getRoom(room).getName()))
                .collect(Collectors.toList());
    }

    public List<Player> getPlayers() {
        return this.characters.stream().filter(e -> e instanceof Player).map(e -> (Player)e).collect(Collectors.toList());
    }

    public List<AI> getAI() {
        return this.characters.stream().filter(e -> e instanceof AI).map(e -> (AI)e).collect(Collectors.toList());
    }

    /**
     * Create a new player
     * @param name Name of player
     * @param room Name of room to spawn player
     */
    public void createPlayer(String name, String room) {
        this.add(new Player(name, RoomsHandler.getInstance().getRoom(room)));
    }

    public void createAI(String name) {
        this.add(new AI(name));
    }

}
