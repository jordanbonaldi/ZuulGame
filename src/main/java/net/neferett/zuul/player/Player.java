package net.neferett.zuul.player;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.SneakyThrows;
import net.neferett.zuul.Zuul;
import net.neferett.zuul.gamemiscs.Room;
import net.neferett.zuul.interpreter.Interpreter;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@EqualsAndHashCode(callSuper = true)
@Data
public class Player extends Character {

    public Player(String name, Room currentRoom) {
        super(name, currentRoom);
    }

    /**
     * Initialise a player only by its name
     * @param name Player Name
     */
    public Player(String name) {
        this(name, null);
    }

    /**
     * Reading input 0 (System.in) until carriage return
     */
    @Override
    @SneakyThrows
    public void nextRound() {
        Interpreter interpreter = Zuul.getInstance().getInterpreter();
        /*
            Getting player's input
         */
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        interpreter.handleCommand(this, reader.readLine());
    }
}
