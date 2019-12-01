package net.neferett.zuul;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import javafx.application.Application;
import javafx.stage.Stage;
import net.neferett.zuul.application.ZuulFX;

import java.io.File;
import java.net.URISyntaxException;

/**
 *
 * @author rej
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        Zuul zuul = new Zuul(args.length != 1, new File(args[0]));

        if (args.length > 1)
            Application.launch(ZuulFX.class);
        else {
            zuul.mapLoader(zuul.getMaps().get(0));
            zuul.createPlayer();
            zuul.play();
        }

    }
}