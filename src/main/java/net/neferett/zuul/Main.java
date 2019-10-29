package net.neferett.zuul;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author rej
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Zuul zuul = new Zuul();

        zuul.initialiseRooms();
        if (args.length == 0) {
            zuul.launchZuul();

            return;
        }

        zuul.addPlayers(args);
        zuul.play();
    }
}