package net.neferett.zuul.interpreter;

import lombok.Data;
import net.neferett.zuul.Zuul;

import java.io.PrintStream;

@Data
public class Output {

    public static Output getInstance() {
        return Zuul.getInstance().getOutput();
    }

    private boolean activated = true;
    private PrintStream stream = System.out;

    /**
     * Printing message only if output stream is activated
     * @param name message
     */
    public void print(String name) {
        if (this.activated)
            stream.println(name);
    }

    public void printWithoutCarriageReturn(String name) {
        if (this.activated)
            stream.print(name);
    }
}
