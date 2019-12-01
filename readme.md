## **World of Zuul Game**

> **Importation of project**
	
To modify this project you have to import the project as:

	Java with gradle with netbeans
or

	gradle project with intellij/eclipse

You will be able to run it directly with the IDE run button (play button)

> **Installation**

 - Linux and MacOS
				
		./gradlew jar or (GUI Version) ./gradlew run
 - Windows
 
	    gradlew.bat jar or (GUI Version) gradlew.bat run

> **How to launch the game ?**

- Without arguments

		java -jar <zuul_jar>.jar <game_file>

> **Creating room and items**

To be able to create new rooms you have to use the RoomHandler instance inside the **Zuul.java** class.

Thus you can use this function as follows:

	this.roomsHandler.createRoom("<room_name>", "<room_descritpion>", maximum_exits);

You can also add items directly while creating the room as follows:

	this.roomsHandler.createRoom("<room_name>", "<room_descritpion>", maximum_exits, new Item("<item_name>", item_weight), ... new Item("toto", item_weight));

You can also add items after room creation with the **addItemOnRoom** function:

	this.roomsHandler.addItemOnRoom("<room_name>", new Item("<item_name>", item_weight), ... new Item("toto", item_weight));

Both function are using var args so you can put infinite number of items inside the function.

> **Creating exits**

By also using the **RoomHandler** instance you can create and link exits to a room

	this.roomsHandler.addExits("<room_name>", new Exit("<exit_name>","<exit_room_link>"), ... new Exit("<exit_name>", "<exit_room_link>"));

This function is using var args so you can put infinite number of exits inside the function,
however you can't add more than the maximum amount of exits!

> **Creating players**

Ultimately you can use **PlayerHandler** instance to create new players and directly spawn them inside a defined room.

	this.playersHandler.createPlayer("<player_name>", "<room_name>");

> **Creating a command**

To be able to create a command you have to create a  class inside **commands package** in order that Reflections utils will locate the class.

This class must extends **ExtendableCommand** and Implement abstract function:

	public boolean onCommand(Player player, String... args);

The class must contains **Command** annotation command parameters.
	
	package net.neferett.zuul.commands;    

	import net.neferett.zuul.gamemiscs.Item;  
	import net.neferett.zuul.gamemiscs.Room;  
	import net.neferett.zuul.handlers.Command;  
	import net.neferett.zuul.handlers.ExtendableCommand;  
	import net.neferett.zuul.player.Player;  
	  
	@Command(name = "test", argsLength = 1, desc = "test command", help = "test <player>")  
	public class testCommand extends ExtendableCommand {  
	 
		  @Override  
		  public boolean onCommand(Player player, String... args) {  
			  // args[0] can't overbound it's handler in command handler  
			  // Getting player with first args
			  Item item = player.getItem(args[0]);  
			  // Getting player's room
			  Room room = player.getCurrentRoom();  
			  
			  // Checking if item is on player's inventory  
			  if (item == null) {  
				  Output.getInstance().print(args[0] + "is not in your inventory");  
				  
				  return false;
			  }  
				  
			  Output.getInstance().print("Item: " + item.getName());  
			  
			  return true;
		 }  
	}


> **Game miscs**

To change players max weight of inventory you can change the value inside **Zuul.java constructor**

	this.maxWeight = <max_weight>;

> **Other informations**

This project is using **Lombock** dependency, it allows to create after compilation every Getter Setter and ArrayList Delegation using annotation:

	@Getter @Setter @Data @Delegate...

More information: [https://projectlombok.org/](https://projectlombok.org/)