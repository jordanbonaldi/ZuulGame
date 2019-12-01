package net.neferett.zuul.interpreter;

import lombok.Data;
import lombok.SneakyThrows;
import net.neferett.zuul.handlers.CharacterHandler;
import net.neferett.zuul.player.Character;

@Data
public class ThreadedInterpreter implements Runnable{

    /**
     * Parameters in constructor thanks to @Data
     *
     * Auto initialised (@Data)
     */
    private final Interpreter interpreter;

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
            for (Character character : CharacterHandler.getInstance().getCharacters()) {
                Output.getInstance().print(character.getName() + "'s turn");
                character.nextRound();
            }
        }
    }
}
