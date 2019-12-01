package net.neferett.zuul.handlers;

import javafx.stage.Stage;
import lombok.Data;
import lombok.experimental.Delegate;
import net.neferett.zuul.Zuul;
import net.neferett.zuul.application.View;
import net.neferett.zuul.gui.Controller;
import net.neferett.zuul.interpreter.Output;

import java.util.HashMap;

@Data
public class ViewHandler {

    public static ViewHandler getInstance() {
        return Zuul.getInstance().getViewHandler();
    }

    private Stage window;

    @Delegate
    private HashMap<String, View> views = new HashMap<>();

    /**
     * Set new page from view instance
     * @param name Name of view
     */
    public void setPage(String name) {
        View view = this.get(name);

        if (view == null) {
            Output.getInstance().print("View " + name + " not found");
            return;
        }

        ((Controller)view.getFxmlLoader().getController()).refresh();

        this.window.setScene(view.getScene());
    }

}
