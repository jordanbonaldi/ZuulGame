package net.neferett.zuul.gamemiscs;

import lombok.Data;
import net.neferett.zuul.handlers.RoomsHandler;

@Data
public class Exit {

    /**
     * Exit name | Field constructor
     */
    private final String name;

    /**
     * Room name linked| Field constructor
     */
    private final String roomName;

    public Room getRoom() {
        return RoomsHandler.getInstance().getRoom(this.roomName);
    }

}
