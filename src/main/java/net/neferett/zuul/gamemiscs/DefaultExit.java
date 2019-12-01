package net.neferett.zuul.gamemiscs;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Arrays;

@AllArgsConstructor
public enum DefaultExit {

    NORTH(0, "north"),
    EAST(1, "east"),
    SOUTH(2, "south"),
    WEST(3, "west");

    private final int number;
    private final String name;

    public static DefaultExit getByNumber(int number) {
        return Arrays.stream(values()).filter(e -> e.number == number).findFirst().orElse(null);
    }

}
