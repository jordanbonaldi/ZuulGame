package net.neferett.zuul.interpreter;

import lombok.Data;
import lombok.SneakyThrows;
import net.neferett.zuul.handlers.PlayersHandler;
import net.neferett.zuul.player.Player;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@Data
public class ThreadedInterpreter implements Runnable{

    /**
     * Parameters in constructor thanks to @Data
     *
     * Auto initialised (@Data)
     */
    private final Interpreter interpreter;

    /**
     * Reading input 0 (System.in) until carriage return
     * @param player Player object to read input on
     */
    @SneakyThrows
    private void readBufferedReader(Player player) {
        /*
            Putting players name in console if more than one player
         */
        if (PlayersHandler.getInstance().getPlayers().size() > 1)
         System.out.println(player.getName() + "'s turn:");

        /*
            Getting player's input
         */
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        this.interpreter.handleCommand(player, reader.readLine());
    }

    @Override
    @SneakyThrows
    public void run() {

        /*
         * Looping while isActivated is not set to false
         * thanks to quitCommand or control-C(d)
         */

        while (this.interpreter.isActivated()) {

            /*
             * Looping over player's to take their commands
             */
            for (Player p : PlayersHandler.getInstance().getPlayers())
                if (this.interpreter.isActivated())
                    this.readBufferedReader(p);
        }
    }
}
