package net.neferett.zuul.interpreter.csv;

import lombok.Data;
import net.neferett.zuul.gamemiscs.DefaultExit;
import net.neferett.zuul.gamemiscs.Exit;
import net.neferett.zuul.gamemiscs.Item;
import net.neferett.zuul.gamemiscs.Room;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Data
public class CSVInterpreter {

    private final FileLoader file;

    private List<Room> rooms = new ArrayList<>();

    public CSVInterpreter(FileLoader loader) {
        this.file = loader;

        this.file.open();
    }


    /**
     * Building List of items from a list string that we cut into a map of String String
     * @param itemsFeatures items
     * @return List<Item></Item>
     */
    private List<Item> buildItems(List<String> itemsFeatures) {
        // List to Map implementation
        Map<String, String> hashFeatures = IntStream.range(0, itemsFeatures.size() / 2).boxed()
                .collect(Collectors.toMap(i -> itemsFeatures.get(i * 2), i -> itemsFeatures.get(i * 2 + 1)));

        // Thus creating a list of item from this map
        return hashFeatures.entrySet().stream().map((a) ->
                new Item(a.getKey().trim(), Integer.parseInt(a.getValue().trim()))).collect(Collectors.toList());
    }

    /**
     *  Building our room from List<String>
     * @param roomsFeatures List of rooms
     * @return Room
     */
    private Room buildRoom(List<String> roomsFeatures) {
        // Getting exits
        List<Exit> exits = IntStream.range(0, 4).boxed()
                .map(i -> new Exit(DefaultExit.getByNumber(i).name(), roomsFeatures.subList(2, roomsFeatures.size()).get(i).trim()))
                .collect(Collectors.toList()).stream().filter(e -> !e.getRoomName().equalsIgnoreCase("null")).collect(Collectors.toList());

        Room room = new Room(roomsFeatures.get(0).trim(), roomsFeatures.get(1).trim(), exits.size());

        // Assigning exits
        exits.forEach(room::addExits);

        return room;
    }

    /**
     * File reader to call different above builders
     * @return CSVInterpreter
     */
    public CSVInterpreter interpretRooms() {
        this.file.getLines().forEach(line -> {
            List<String> lineFeatures = Arrays.asList(line.split(","));

            Room room = this.buildRoom(lineFeatures.subList(0, 6));

            this.buildItems(lineFeatures.subList(6, lineFeatures.size())).forEach(room::addItem);

            this.rooms.add(room);
        });

        return this;
    }

    /**
     * Closing file
     */
    public void close() {
        this.file.close();
    }
}
